package com.htec.task.services.impl;

import com.htec.task.enums.GroupName;
import com.htec.task.models.GameStats;
import com.htec.task.repositories.GameStatsRepository;
import com.htec.task.services.GameStatsService;
import com.htec.task.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service for {@link GameStats} implements {@link GameStatsService}
 */

@Slf4j
@Service
public class GameStatsServiceImpl implements GameStatsService {

    @Lazy
    @Autowired
    private GameStatsRepository gameStatsRepository;

    /**
     * Get games
     *
     * @param dateFrom
     * @param dateTo
     * @param team
     * @param group
     * @return list {@link GameStats} list of games
     */
    @Override
    public List<GameStats> getGames(String dateFrom, String dateTo, String team, GroupName group) {
        List<GameStats> allGames;
        if (StringUtils.isEmpty(dateFrom) && StringUtils.isEmpty(dateTo) && group == null && StringUtils.isEmpty(team)) {
            allGames = getAllGames();
        } else {
            allGames = getGameStatsByFilters(dateFrom, dateTo, team, group);
        }
        return allGames;
    }

    /**
     * Get all games
     *
     * @return list {@link GameStats}   list of games
     */
    @Override
    public List<GameStats> getAllGames() {
        Iterable<GameStats> games = gameStatsRepository.findAll();
        List<GameStats> gameStats = new ArrayList<>();
        games.forEach(gameStats::add);
        return gameStats;
    }

    /**
     * Get games by search filters
     *
     * @param dateFrom
     * @param dateTo
     * @param team
     * @param groupName
     * @return list {@link GameStats} list of games
     */
    @Override
    public List<GameStats> getGameStatsByFilters(String dateFrom, String dateTo, String team, GroupName groupName) {
        return getAllGames().stream()
                .filter(groupPredicate(groupName))
                .filter(teamNamePredicate(team))
                .filter(kickoffDateFromPredicate(dateFrom))
                .filter(kickoffDateToPredicate(dateTo))
                .collect(Collectors.toList());
    }

    /**
     * Predicate for group name
     *
     * @param groupName {@link GroupName}
     * @return predicate {@link GameStats}
     */
    private Predicate<GameStats> groupPredicate(GroupName groupName) {
        return game -> groupName == null || game.getGroup().equals(groupName);
    }

    /**
     * Predicate for team
     *
     * @param team
     * @return predicate {@link GameStats}
     */
    private Predicate<GameStats> teamNamePredicate(String team) {
        return game -> StringUtils.isEmpty(team) || game.getAwayTeam().toLowerCase().contains(team.toLowerCase()) || game.getHomeTeam().toLowerCase().contains(team.toLowerCase());
    }

    /**
     * Predicate for kickoff date to
     *
     * @param dateTo
     * @return predicate {@link GameStats}
     */
    private Predicate<GameStats> kickoffDateToPredicate(String dateTo) {
        return game -> {
            try {
                return StringUtils.isEmpty(dateTo) || game.getKickoffAt().before(DateFormatter.parseDate(dateTo));
            } catch (ParseException e) {
                log.error("DateTo parsing exception: {}", e.getLocalizedMessage());
                return false;
            }
        };
    }

    /**
     * Predicate for kickoff date from
     *
     * @param dateFrom
     * @return predicate {@link GameStats}
     */
    private Predicate<GameStats> kickoffDateFromPredicate(String dateFrom) {
        return game -> {
            try {
                return StringUtils.isEmpty(dateFrom) || game.getKickoffAt().after(DateFormatter.parseDate(dateFrom));
            } catch (ParseException e) {
                log.error("DateFrom parsing exception: {}", e.getLocalizedMessage());
                return false;
            }
        };
    }
}
