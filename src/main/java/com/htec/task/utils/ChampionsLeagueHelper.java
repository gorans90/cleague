package com.htec.task.utils;

import com.htec.task.dtos.AbstractLeagueStatsDTO;
import com.htec.task.dtos.GameStatsDTO;
import com.htec.task.enums.GroupName;
import com.htec.task.models.Standing;
import com.htec.task.services.ChampionsLeagueService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper methods for {@link ChampionsLeagueService}
 */

public class ChampionsLeagueHelper {

    /**
     * Populating common standing values
     *
     * @param homeTeam
     * @param awayTeam
     * @param homeTeamGoal
     * @param awayTeamGoal
     */
    public static void populateCommonStandingValues(Standing homeTeam, Standing awayTeam, int homeTeamGoal, int awayTeamGoal) {
        // populating away team standings
        homeTeam.setPlayedGames(homeTeam.getPlayedGames() + 1);

        // populating away team standings
        awayTeam.setPlayedGames(awayTeam.getPlayedGames() + 1);

        populateGoals(homeTeam, awayTeam, homeTeamGoal, awayTeamGoal);
    }

    /**
     * Populate goals for home and away team
     *
     * @param homeTeam
     * @param awayTeam
     * @param homeTeamGoal
     * @param awayTeamGoal
     */
    public static void populateGoals(Standing homeTeam, Standing awayTeam, int homeTeamGoal, int awayTeamGoal) {
        // populating away team goals
        homeTeam.setGoals(homeTeam.getGoals() + homeTeamGoal);
        homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayTeamGoal);
        homeTeam.setGoalDifference(homeTeam.getGoalDifference() + homeTeamGoal - awayTeamGoal);

        // populating away team goals
        awayTeam.setGoals(awayTeam.getGoals() + awayTeamGoal);
        awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeTeamGoal);
        awayTeam.setGoalDifference(awayTeam.getGoalDifference() + awayTeamGoal - homeTeamGoal);
    }

    /**
     * Collect distinct groups from games list
     *
     * @param gameStats list {@link GameStatsDTO} list of games
     * @return list {@link GroupName}            list of groups
     */
    public static List<GroupName> collectGroups(List<GameStatsDTO> gameStats) {
        return gameStats.stream().map(AbstractLeagueStatsDTO::getGroup).distinct().collect(Collectors.toList());
    }

    /**
     * Collect games for requested group
     *
     * @param gameStats list {@link GameStatsDTO}   list of games
     * @param group     {@link GroupName}              group name
     * @return list {@link GameStatsDTO}            list of grouped games
     */
    public static List<GameStatsDTO> collectGamesForGroup(List<GameStatsDTO> gameStats, GroupName group) {
        return gameStats.stream().filter(game -> game.getGroup().equals(group)).collect(Collectors.toList());
    }

    /**
     * Helper method for setting properties for team
     *
     * @param team
     * @param winNo
     * @param drawNo
     * @param loseNo
     * @param pointsNo
     */
    public static void updateStandingsForTeam(Standing team, int winNo, int drawNo, int loseNo, int pointsNo) {
        team.setWin(team.getWin() + winNo);
        team.setDraw(team.getDraw() + drawNo);
        team.setLose(team.getLose() + loseNo);
        team.setPoints(team.getPoints() + pointsNo);
    }
}
