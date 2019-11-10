package com.htec.task.mappers;

import com.htec.task.dtos.GameStatsDTO;
import com.htec.task.models.GameStats;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Game stats mapper class
 */

public class GameStatsMapper {

    /**
     * Map to model object from dto object
     *
     * @param gameStatsDTO {@link GameStatsDTO}     dto object
     * @return {@link GameStats}                    model object
     */
    public static GameStats fromDto(GameStatsDTO gameStatsDTO) {
        return GameStats.builder()
                .leagueTitle(gameStatsDTO.getLeagueTitle())
                .matchday(gameStatsDTO.getMatchday())
                .group(gameStatsDTO.getGroup())
                .homeTeam(gameStatsDTO.getHomeTeam())
                .awayTeam(gameStatsDTO.getAwayTeam())
                .kickoffAt(gameStatsDTO.getKickoffAt())
                .score(gameStatsDTO.getScore())
                .build();
    }

    /**
     * Map to list of model objects from list of dto objects
     *
     * @param gameStatsDTOS list {@link GameStatsDTO}        list of dto objects
     * @return list {@link GameStats}                        list of model objects
     */
    public static List<GameStats> fromDtoList(List<GameStatsDTO> gameStatsDTOS) {
        return gameStatsDTOS.stream().map(GameStatsMapper::fromDto).collect(Collectors.toList());
    }

    /**
     * Map to dto object from model object
     *
     * @param gameStats {@link GameStats}       model object
     * @return {@link GameStatsDTO}             dto object
     */
    public static GameStatsDTO toDto(GameStats gameStats) {
        return GameStatsDTO.builder()
                .awayTeam(gameStats.getAwayTeam())
                .homeTeam(gameStats.getHomeTeam())
                .leagueTitle(gameStats.getLeagueTitle())
                .matchday(gameStats.getMatchday())
                .group(gameStats.getGroup())
                .score(gameStats.getScore())
                .kickoffAt(gameStats.getKickoffAt())
                .build();
    }

    /**
     * Map to list of dto objects from list of model objects
     *
     * @param gamesStats list {@link GameStats}     list of model objects
     * @return list {@link GameStatsDTO}            list of dto objects
     */
    public static List<GameStatsDTO> toDtoList(List<GameStats> gamesStats) {
        return gamesStats.stream().map(GameStatsMapper::toDto).collect(Collectors.toList());
    }
}
