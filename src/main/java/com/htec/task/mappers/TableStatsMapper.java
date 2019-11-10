package com.htec.task.mappers;

import com.htec.task.dtos.AbstractLeagueStatsDTO;
import com.htec.task.dtos.TableStatsDTO;
import com.htec.task.enums.GroupName;
import com.htec.task.models.TableStats;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Table stats mapper class
 */

public class TableStatsMapper {

    /**
     * Map to model object from dto object
     *
     * @param tableStatsDTO {@link TableStatsDTO}   dto object
     * @return {@link TableStats}                   model object
     */
    public static TableStats fromDto(TableStatsDTO tableStatsDTO) {
        return TableStats.builder()
                .group(tableStatsDTO.getGroup().name())
                .leagueTitle(tableStatsDTO.getLeagueTitle())
                .matchday(tableStatsDTO.getMatchday())
                .standings(StandingMapper.fromDtoList(tableStatsDTO.getStanding()))
                .build();
    }

    /**
     * Map to list of model objects from list of dto objects
     *
     * @param tableStatsDTOS list {@link TableStatsDTO}     list of dto objects
     * @return list {@link TableStats}                      list of model objects
     */
    public static List<TableStats> fromDtoList(List<TableStatsDTO> tableStatsDTOS) {
        return tableStatsDTOS.stream().map(TableStatsMapper::fromDto).collect(Collectors.toList());
    }

    /**
     * Map to dto object from model object
     *
     * @param tableStats {@link TableStats}     model object
     * @return {@link TableStatsDTO}            dto object
     */
    public static TableStatsDTO toDto(TableStats tableStats) {
        return TableStatsDTO.builder()
                .leagueTitle(tableStats.getLeagueTitle())
                .group(GroupName.valueOf(tableStats.getGroup()))
                .matchday(tableStats.getMatchday())
                .standing(StandingMapper.toDtoList(tableStats.getStandings()))
                .build();
    }

    /**
     * Map to list of dto objects from list of model objects
     *
     * @param tableStats list {@link TableStats}    list of model objects
     * @return list {@link TableStatsDTO}           list of dto objects
     */
    public static List<TableStatsDTO> toDtoList(List<TableStats> tableStats) {
        return tableStats.stream().map(TableStatsMapper::toDto)
                .sorted(Comparator.comparing(AbstractLeagueStatsDTO::getGroup))
                .collect(Collectors.toList());
    }
}
