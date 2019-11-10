package com.htec.task.services;

import com.htec.task.dtos.GameStatsDTO;
import com.htec.task.dtos.TableStatsDTO;

import java.util.List;

/**
 * Champions league service
 */

public interface ChampionsLeagueService {

    /**
     * Processed games and return list of table stats
     *
     * @param gameStats list {@link GameStatsDTO}   list of game stats
     * @return list {@link TableStatsDTO}           list of table stats
     */
    List<TableStatsDTO> processAndUpdateGamesAndReturnTables(List<GameStatsDTO> gameStats);
}
