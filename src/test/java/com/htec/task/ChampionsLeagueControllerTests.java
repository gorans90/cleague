package com.htec.task;

import com.htec.task.base.ControllerTests;
import com.htec.task.dtos.GameStatsDTO;
import com.htec.task.dtos.TableStatsDTO;
import com.htec.task.enums.GroupName;
import com.htec.task.util.ChampionsLeagueTestHelper;
import com.htec.task.utils.Endpoints;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

/**
 * Champions league controller integration test
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChampionsLeagueControllerTests extends ControllerTests {

    @Test
    public void aprocessGamesTest() throws ParseException {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.PROCESS);
        ResponseEntity<TableStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.POST, new HttpEntity<>(ChampionsLeagueTestHelper.mockListOfGamesForProcessing()), TableStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.OK.value(), response.getStatusCodeValue());
        Assert.assertEquals("Wrong number of groups", response.getBody().length, 2);

        ChampionsLeagueTestHelper.validatingProcessedTables(response.getBody());
    }

    @Test
    public void getTableByNameWithResultAllTablesTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.TABLE);
        ResponseEntity<TableStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, TableStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.OK.value(), response.getStatusCodeValue());

        ChampionsLeagueTestHelper.validatingProcessedTables(response.getBody());
    }

    @Test
    public void getTableByNameWithResultRequestedTableTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.TABLE, "?group=", GroupName.B);
        ResponseEntity<TableStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, TableStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.OK.value(), response.getStatusCodeValue());
        ChampionsLeagueTestHelper.assertsForProcessedTableB(response.getBody()[0]);
    }

    @Test
    public void getTableByNameWithoutResultTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.TABLE, "?group=", GroupName.C);
        ResponseEntity<TableStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, TableStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void getGamesByFiltersWithAllResultTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.GAMES);
        ResponseEntity<GameStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, GameStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.OK.value(), response.getStatusCodeValue());
        Assert.assertEquals("Wrong number of the games", response.getBody().length, 6);
    }

    @Test
    public void getGamesByFiltersWithResultTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.GAMES, "?group=", GroupName.B, "&team=Zvezda");
        ResponseEntity<GameStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, GameStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.OK.value(), response.getStatusCodeValue());
        Assert.assertEquals("Wrong number of the games", response.getBody().length, 2);
    }

    @Test
    public void getGamesByFiltersWithoutResultTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.GAMES, "?group=", GroupName.D);
        ResponseEntity<GameStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, GameStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void getGamesByFiltersWithParseDateFromExceptionTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.GAMES, "?dateFrom=2018/01/01");
        ResponseEntity<GameStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, GameStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void getGamesByFiltersWithParseDateToExceptionTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.GAMES, "?dateTo=2018/01/01");
        ResponseEntity<GameStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, GameStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void getGamesByFiltersWithoutResultSecondTest() {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.GAMES, "?dateFrom=2019-01-01");
        ResponseEntity<GameStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.GET, null, GameStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void updateAndProcessGamesTest() throws ParseException {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.PROCESS);
        ResponseEntity<TableStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.POST, new HttpEntity<>(ChampionsLeagueTestHelper.mockListOfGamesForUpdating()), TableStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.OK.value(), response.getStatusCodeValue());
        Assert.assertEquals("Wrong number of groups", response.getBody().length, 2);

        ChampionsLeagueTestHelper.validatingUpdatedTable(response.getBody());
    }

    @Test
    public void updateAndProcessDrawGamesTest() throws ParseException {
        String processGamesUrl = StringUtils.join(Endpoints.BASE, Endpoints.PROCESS);
        ResponseEntity<TableStatsDTO[]> response =
                restTemplate.exchange(processGamesUrl, HttpMethod.POST, new HttpEntity<>(ChampionsLeagueTestHelper.mockListOfGamesForUpdatingWithDrawResults()), TableStatsDTO[].class);
        Assert.assertEquals("Wrong response status", HttpStatus.OK.value(), response.getStatusCodeValue());
        Assert.assertEquals("Wrong number of groups", response.getBody().length, 2);

        ChampionsLeagueTestHelper.validatingUpdatedTableWithDrawResults(response.getBody());
    }
}
