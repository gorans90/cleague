package com.htec.task.services.impl;

import com.htec.task.dtos.GameStatsDTO;
import com.htec.task.dtos.TableStatsDTO;
import com.htec.task.enums.GroupName;
import com.htec.task.exceptions.StandingException;
import com.htec.task.mappers.GameStatsMapper;
import com.htec.task.mappers.TableStatsMapper;
import com.htec.task.models.GameStats;
import com.htec.task.models.Standing;
import com.htec.task.models.TableStats;
import com.htec.task.repositories.GameStatsRepository;
import com.htec.task.repositories.StandingRepository;
import com.htec.task.repositories.TableStatsRepository;
import com.htec.task.services.ChampionsLeagueService;
import com.htec.task.utils.ChampionsLeagueHelper;
import com.htec.task.utils.StandingSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Champions league service implements {@link ChampionsLeagueService}
 */

@Slf4j
@Service
public class ChampionsLeagueServiceImpl implements ChampionsLeagueService {

    @Lazy
    @Autowired
    private GameStatsRepository gameStatsRepository;

    @Lazy
    @Autowired
    private StandingRepository standingRepository;

    @Lazy
    @Autowired
    private TableStatsRepository tableStatsRepository;

    /**
     * Processed games and return list of table stats
     *
     * @param gameStats list {@link GameStatsDTO}   list of game stats
     * @return list {@link TableStatsDTO}           list of table stats
     */
    @Override
    public List<TableStatsDTO> processAndUpdateGamesAndReturnTables(List<GameStatsDTO> gameStats) {
        List<TableStats> tableStats = new ArrayList<>();
        List<GameStatsDTO> gamesToPersist = new ArrayList<>();

        List<GroupName> groups = ChampionsLeagueHelper.collectGroups(gameStats);
        for (GroupName group : groups) {
            // fetch games for group
            List<GameStatsDTO> gamesByGroup = ChampionsLeagueHelper.collectGamesForGroup(gameStats, group);
            // fetch table for group name or create new one if table does not exists
            TableStats tableGroup = tableStatsRepository.findByGroupIgnoreCase(group.name())
                    .orElseGet(() -> createBlankTable(group, gamesByGroup.iterator().hasNext() ? gamesByGroup.iterator().next().getLeagueTitle() : ""));

            Set<String> teams = gamesByGroup.stream().map(GameStatsDTO::getHomeTeam).collect(Collectors.toSet());
            teams.addAll(gamesByGroup.stream().map(GameStatsDTO::getAwayTeam).collect(Collectors.toSet()));
            for (GameStatsDTO gameStatsDTO : gamesByGroup) {
                GameStatsDTO gameToPersist = calculateAndUpdateStanding(gameStatsDTO);
                if (gameToPersist != null) {
                    gamesToPersist.add(gameToPersist);
                }
            }
            List<Standing> standings = standingRepository.findByTeamIn(teams);

            // sorting standings and updating ranks and saving tables
            if (!CollectionUtils.isEmpty(tableGroup.getStandings())) {
                // update standings
                tableGroup.getStandings().removeAll(standings);
                tableGroup.getStandings().addAll(standings);
                tableGroup.setStandings(StandingSort.compareStandingsAndRankUpdate(tableGroup.getStandings()));
            } else {
                tableGroup.setStandings(StandingSort.compareStandingsAndRankUpdate(standings));
            }
            tableStats.add(tableGroup);
        }

        if (!CollectionUtils.isEmpty(gamesToPersist)) {
            gameStatsRepository.saveAll(GameStatsMapper.fromDtoList(gamesToPersist));
        }
        tableStatsRepository.saveAll(tableStats);
        return TableStatsMapper.toDtoList(tableStats);
    }

    /**
     * updating game standing
     *
     * @param game         {@link GameStatsDTO}          new game stats
     * @param gameExisting {@link GameStats}     existing game stats
     * @throws StandingException
     */
    private void updateStandingsAndGame(GameStatsDTO game, GameStats gameExisting) throws StandingException {
        String[] score = game.getScore().split(":");
        final int homeTeamGoal = Integer.parseInt(score[0]);
        final int awayTeamGoal = Integer.parseInt(score[1]);
        final int goalDifference = homeTeamGoal - awayTeamGoal;

        String[] scoreExisting = gameExisting.getScore().split(":");
        final int homeTeamGoalExisting = Integer.parseInt(scoreExisting[0]);
        final int awayTeamGoalExisting = Integer.parseInt(scoreExisting[1]);
        final int goalDifferenceExisting = homeTeamGoalExisting - awayTeamGoalExisting;

        Standing homeTeam = standingRepository.findFirstByTeamIgnoreCase(game.getHomeTeam())
                .orElseThrow(() -> new StandingException("Standing not found for home team " + game.getHomeTeam(), HttpStatus.NOT_FOUND.value()));

        Standing awayTeam = standingRepository.findFirstByTeamIgnoreCase(game.getAwayTeam())
                .orElseThrow(() -> new StandingException("Standing not found for away team " + game.getAwayTeam(), HttpStatus.NOT_FOUND.value()));

        ChampionsLeagueHelper.populateGoals(homeTeam, awayTeam, homeTeamGoal - homeTeamGoalExisting, awayTeamGoal - awayTeamGoalExisting);
        int homeTeamWin = 0;
        int homeTeamDraw = 0;
        int homeTeamLose = 0;
        int homeTeamPoints = 0;
        int awayTeamWin = 0;
        int awayTeamDraw = 0;
        int awayTeamLose = 0;
        int awayTeamPoints = 0;
        if (goalDifference < 0) {
            if (goalDifferenceExisting == 0) {
                // homeTeam lose, but in the db is draw
                homeTeamDraw = -1;
                homeTeamPoints = -1;
                homeTeamLose = 1;
                awayTeamWin = 1;
                awayTeamDraw = -1;
                awayTeamPoints = 2;
            } else if (goalDifferenceExisting > 0) {
                // homeTeam lose, but in the db is win
                homeTeamWin = -1;
                homeTeamPoints = -3;
                homeTeamLose = 1;
                awayTeamWin = 1;
                awayTeamLose = -1;
                awayTeamPoints = 3;
            }
        } else if (goalDifference == 0) {
            if (goalDifferenceExisting < 0) {
                // homeTeam played draw, but in the db is lose
                homeTeamLose = -1;
                homeTeamPoints = 1;
                homeTeamDraw = 1;
                awayTeamDraw = 1;
                awayTeamWin = -1;
                awayTeamPoints = -2;
            } else if (goalDifferenceExisting > 0) {
                // homeTeam played draw, but in the db is win
                homeTeamWin = -1;
                homeTeamPoints = -2;
                homeTeamDraw = 1;
                awayTeamDraw = 1;
                awayTeamLose = -1;
                awayTeamPoints = 1;
            }
        } else if (goalDifference > 0) {
            if (goalDifferenceExisting == 0) {
                // homeTeam win, but in the db is draw
                homeTeamDraw = -1;
                homeTeamPoints = 2;
                homeTeamWin = 1;
                awayTeamLose = 1;
                awayTeamDraw = -1;
                awayTeamPoints = -1;
            } else if (goalDifferenceExisting < 0) {
                // homeTeam win, but in the db is lose
                homeTeamLose = -1;
                homeTeamPoints = 3;
                homeTeamWin = 1;
                awayTeamLose = 1;
                awayTeamWin = -1;
                awayTeamPoints = -3;
            }
        }
        ChampionsLeagueHelper.updateStandingsForTeam(homeTeam, homeTeamWin, homeTeamDraw, homeTeamLose, homeTeamPoints);
        ChampionsLeagueHelper.updateStandingsForTeam(awayTeam, awayTeamWin, awayTeamDraw, awayTeamLose, awayTeamPoints);

        standingRepository.saveAll(Arrays.asList(homeTeam, awayTeam));
        gameExisting.setScore(game.getScore());
        gameStatsRepository.save(gameExisting);
    }

    /**
     * Calculating standings based on rules
     *
     * @param game {@link GameStatsDTO}     game stats
     */
    private void calculateStanding(GameStatsDTO game) {
        // find standings for home and away team
        // if team standing does not exist create blank one
        Standing homeTeam = standingRepository.findFirstByTeamIgnoreCase(game.getHomeTeam())
                .orElseGet(() -> createBlankStanding(game.getHomeTeam()));
        Standing awayTeam = standingRepository.findFirstByTeamIgnoreCase(game.getAwayTeam())
                .orElseGet(() -> createBlankStanding(game.getAwayTeam()));

        // extract goals from score
        String[] score = game.getScore().split(":");
        final int homeTeamGoal = Integer.parseInt(score[0]);
        final int awayTeamGoal = Integer.parseInt(score[1]);

        // adding properties based on goals
        if (homeTeamGoal > awayTeamGoal) {
            homeTeam.setWin(homeTeam.getWin() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 3);
            awayTeam.setLose(awayTeam.getLose() + 1);
        } else if (homeTeamGoal < awayTeamGoal) {
            homeTeam.setLose(homeTeam.getLose() + 1);
            awayTeam.setWin(awayTeam.getWin() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 3);
        } else {
            homeTeam.setDraw(homeTeam.getDraw() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 1);
            awayTeam.setDraw(awayTeam.getDraw() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
        }

        // populating common standing values
        ChampionsLeagueHelper.populateCommonStandingValues(homeTeam, awayTeam, homeTeamGoal, awayTeamGoal);

        // saving standings for home and away teams
        standingRepository.saveAll(Arrays.asList(homeTeam, awayTeam));
    }

    /**
     * Creating blank standing
     *
     * @param team team name
     * @return {@link Standing}     created blank standing
     */
    private Standing createBlankStanding(String team) {
        return standingRepository.save(Standing.builder()
                .rank(1)
                .team(team)
                .draw(0)
                .win(0)
                .lose(0)
                .goalDifference(0)
                .goals(0)
                .goalsAgainst(0)
                .playedGames(0)
                .points(0)
                .build()
        );
    }

    /**
     * Create blank table for group
     *
     * @param groupName   {@link GroupName}       group name
     * @param leagueTitle {@link String}        league title
     * @return {@link TableStats}               created blank table stats
     */
    private TableStats createBlankTable(GroupName groupName, String leagueTitle) {
        return tableStatsRepository.save(TableStats.builder()
                .matchday(0)
                .leagueTitle(leagueTitle)
                .group(groupName.name())
                .build()
        );
    }

    private GameStatsDTO calculateAndUpdateStanding(GameStatsDTO game) {
        Optional<GameStats> gameFromDb = gameStatsRepository.findByHomeTeamAndAwayTeamAndKickoffAt(game.getHomeTeam(), game.getAwayTeam(), game.getKickoffAt());
        if (gameFromDb.isPresent()) {
            try {
                updateStandingsAndGame(game, gameFromDb.get());
            } catch (StandingException e) {
                log.error("Standing not found");
            }
        } else {
            calculateStanding(game);
            return game;
        }
        return null;
    }
}
