package com.htec.task.controllers;

import com.htec.task.configs.ApiResponseCodes;
import com.htec.task.dtos.GameStatsDTO;
import com.htec.task.dtos.TableStatsDTO;
import com.htec.task.enums.GroupName;
import com.htec.task.mappers.GameStatsMapper;
import com.htec.task.mappers.TableStatsMapper;
import com.htec.task.models.GameStats;
import com.htec.task.models.TableStats;
import com.htec.task.services.ChampionsLeagueService;
import com.htec.task.services.GameStatsService;
import com.htec.task.services.TableStatsService;
import com.htec.task.utils.Endpoints;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Champions league controller
 */

@Slf4j
@RestController
@Validated
@RequestMapping(Endpoints.BASE)
public class ChampionsLeagueController {

    @Lazy
    @Autowired
    private ChampionsLeagueService championsLeagueService;

    @Lazy
    @Autowired
    private TableStatsService tableStatsService;

    @Lazy
    @Autowired
    private GameStatsService gameStatsService;

    /**
     * Process games and return table rank
     *
     * @param gamesStats list {@link GameStatsDTO}    games that should be processed
     * @return list {@link TableStatsDTO}             final table sort
     */
    @PostMapping(value = Endpoints.PROCESS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Process and update games and return table rank",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "POST", code = 200, response = List.class)
    @ApiResponseCodes
    public ResponseEntity<List<TableStatsDTO>> processGames(@ApiParam(value = "Games stats are required", required = true)
                                                            @Valid @RequestBody List<GameStatsDTO> gamesStats) {
        return ResponseEntity.ok(championsLeagueService.processAndUpdateGamesAndReturnTables(gamesStats));
    }

    /**
     * Get table by name, or all
     *
     * @param groupNames group name that should be returned
     * @return list {@link TableStatsDTO}   tables
     */
    @GetMapping(value = Endpoints.TABLE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Table return",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "GET", code = 200, response = List.class)
    @ApiResponseCodes
    public ResponseEntity<List<TableStatsDTO>> getTableByName(@ApiParam(value = "List of group names. For all groups, you don't have to select anything")
                                                              @RequestParam(value = "group", required = false) List<GroupName> groupNames) {
        List<TableStats> tableStats = tableStatsService.getTables(groupNames);

        if (CollectionUtils.isEmpty(tableStats)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TableStatsMapper.toDtoList(tableStats));
    }

    @GetMapping(value = Endpoints.GAMES, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Games returned",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "GET", code = 200, response = List.class)
    @ApiResponseCodes
    public ResponseEntity<List<GameStatsDTO>> getGamesByFilters(
            @ApiParam(value = "Filter for date from. E.g 2018-01-11") @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @ApiParam(value = "Filter for date to. E.g 2018-01-11") @RequestParam(value = "dateTo", required = false) String dateTo,
            @ApiParam(value = "Filter for group") @RequestParam(value = "group", required = false) GroupName group,
            @ApiParam(value = "Filter for team name") @RequestParam(value = "team", required = false) String team) {

        List<GameStats> allGames = gameStatsService.getGames(dateFrom, dateTo, team, group);

        if (CollectionUtils.isEmpty(allGames)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(GameStatsMapper.toDtoList(allGames));
    }
}
