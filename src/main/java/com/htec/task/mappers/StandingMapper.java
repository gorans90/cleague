package com.htec.task.mappers;

import com.htec.task.dtos.StandingDTO;
import com.htec.task.models.Standing;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Standing mapper class
 */

public class StandingMapper {

    /**
     * Map to model object from dto object
     *
     * @param standingDTO {@link StandingDTO}   dto object
     * @return {@link Standing}                 model object
     */
    public static Standing fromDto(StandingDTO standingDTO) {
        return Standing.builder()
                .goals(standingDTO.getGoals())
                .goalsAgainst(standingDTO.getGoalsAgainst())
                .goalDifference(standingDTO.getGoalDifference())
                .team(standingDTO.getTeam())
                .draw(standingDTO.getDraw())
                .win(standingDTO.getWin())
                .lose(standingDTO.getLose())
                .playedGames(standingDTO.getPlayedGames())
                .points(standingDTO.getPoints())
                .rank(standingDTO.getRank())
                .build();
    }

    /**
     * Map to list of model objects from list of dto objects
     *
     * @param standingDTOS list {@link StandingDTO}     list of dto objects
     * @return list {@link Standing}                    list of model objects
     */
    public static List<Standing> fromDtoList(List<StandingDTO> standingDTOS) {
        return standingDTOS.stream().map(StandingMapper::fromDto).collect(Collectors.toList());
    }

    /**
     * Map to dto object from model object
     *
     * @param standing {@link Standing}     model object
     * @return {@link StandingDTO}          dto object
     */
    public static StandingDTO toDto(Standing standing) {
        return StandingDTO.builder()
                .draw(standing.getDraw())
                .goalDifference(standing.getGoalDifference())
                .goals(standing.getGoals())
                .goalsAgainst(standing.getGoalsAgainst())
                .lose(standing.getLose())
                .playedGames(standing.getPlayedGames())
                .points(standing.getPoints())
                .rank(standing.getRank())
                .team(standing.getTeam())
                .win(standing.getWin())
                .build();
    }

    /**
     * Map to list of dto objects from list of model objects
     *
     * @param standings list {@link Standing}    list of model objects
     * @return list {@link StandingDTO}          list of dto objects
     */
    public static List<StandingDTO> toDtoList(List<Standing> standings) {
        return standings.stream().map(StandingMapper::toDto).collect(Collectors.toList());
    }
}
