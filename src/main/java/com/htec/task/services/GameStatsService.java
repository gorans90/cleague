package com.htec.task.services;

import com.htec.task.enums.GroupName;
import com.htec.task.models.GameStats;

import java.util.List;

/**
 * Service for {@link GameStats}
 */

public interface GameStatsService {

    /**
     * Get games
     *
     * @param dateFrom
     * @param dateTo
     * @param team
     * @param groupName
     * @return list {@link GameStats} list of games
     */
    List<GameStats> getGames(String dateFrom, String dateTo, String team, GroupName groupName);

    /**
     * Get all games
     *
     * @return list {@link GameStats}   list of games
     */
    List<GameStats> getAllGames();

    /**
     * Get games by search filters
     *
     * @param dateFrom
     * @param dateTo
     * @param team
     * @param groupName
     * @return list {@link GameStats} list of games
     */
    List<GameStats> getGameStatsByFilters(String dateFrom, String dateTo, String team, GroupName groupName);
}
