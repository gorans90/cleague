package com.htec.task.util;

import com.htec.task.dtos.GameStatsDTO;
import com.htec.task.dtos.TableStatsDTO;
import com.htec.task.enums.GroupName;
import com.htec.task.utils.DateFormatter;
import org.junit.Assert;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class ChampionsLeagueTestHelper {

    public static List<GameStatsDTO> mockListOfGamesForUpdating() throws ParseException {
        return Arrays.asList(
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("PSG")
                        .awayTeam("Arsenal")
                        .group(GroupName.A)
                        .kickoffAt(DateFormatter.parseDate("2017-09-13T20:45:00"))
                        .score("1:2")
                        .build(),
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("Liverpool")
                        .awayTeam("Crvena Zvezda")
                        .group(GroupName.B)
                        .kickoffAt(DateFormatter.parseDate("2017-11-28T20:45:00"))
                        .score("4:0")
                        .build()
                ,
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("Liverpool")
                        .awayTeam("Marsej")
                        .group(GroupName.B)
                        .kickoffAt(DateFormatter.parseDate("2017-11-28T20:45:00"))
                        .score("0:0")
                        .build()
        );
    }

    public static List<GameStatsDTO> mockListOfGamesForUpdatingWithDrawResults() throws ParseException {
        return Arrays.asList(
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("PSG")
                        .awayTeam("Arsenal")
                        .group(GroupName.A)
                        .kickoffAt(DateFormatter.parseDate("2017-09-13T20:45:00"))
                        .score("2:2")
                        .build(),
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("Liverpool")
                        .awayTeam("Crvena Zvezda")
                        .group(GroupName.B)
                        .kickoffAt(DateFormatter.parseDate("2017-11-28T20:45:00"))
                        .score("4:4")
                        .build()
        );
    }

    public static List<GameStatsDTO> mockListOfGamesForProcessing() throws ParseException {
        return Arrays.asList(
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("PSG")
                        .awayTeam("Arsenal")
                        .group(GroupName.A)
                        .kickoffAt(DateFormatter.parseDate("2017-09-13T20:45:00"))
                        .score("1:1")
                        .build(),
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("Basel")
                        .awayTeam("Ludogorets")
                        .group(GroupName.A)
                        .kickoffAt(DateFormatter.parseDate("2017-09-13T20:45:00"))
                        .score("1:1")
                        .build(),
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(2)
                        .homeTeam("Arsenal")
                        .awayTeam("Basel")
                        .group(GroupName.A)
                        .kickoffAt(DateFormatter.parseDate("2017-09-28T20:45:00"))
                        .score("2:0")
                        .build(),
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(2)
                        .homeTeam("Ludogorets")
                        .awayTeam("PSG")
                        .group(GroupName.A)
                        .kickoffAt(DateFormatter.parseDate("2017-09-28T20:45:00"))
                        .score("1:3")
                        .build(),
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(1)
                        .homeTeam("Liverpool")
                        .awayTeam("Crvena Zvezda")
                        .group(GroupName.B)
                        .kickoffAt(DateFormatter.parseDate("2017-11-28T20:45:00"))
                        .score("4:1")
                        .build(),
                GameStatsDTO.builder()
                        .leagueTitle("Champions league 2016/17")
                        .matchday(2)
                        .homeTeam("Crvena Zvezda")
                        .awayTeam("Marsej")
                        .group(GroupName.B)
                        .kickoffAt(DateFormatter.parseDate("2017-12-12T20:45:00"))
                        .score("3:3")
                        .build()
        );
    }

    public static void assertsForProcessedTableA(TableStatsDTO tableStats) {
        Assert.assertEquals("Wrong number of team", tableStats.getStanding().size(), 4);
        Assert.assertEquals("Wrong team on rank 1", tableStats.getStanding().get(0).getTeam(), "PSG");
        Assert.assertEquals("Wrong team on rank 2", tableStats.getStanding().get(1).getTeam(), "Arsenal");
        Assert.assertEquals("Wrong team on rank 3", tableStats.getStanding().get(2).getTeam(), "Ludogorets");
        Assert.assertEquals("Wrong team on rank 4", tableStats.getStanding().get(3).getTeam(), "Basel");

    }

    public static void assertsForProcessedTableB(TableStatsDTO tableStats) {
        Assert.assertEquals("Wrong number of team", tableStats.getStanding().size(), 3);
        Assert.assertEquals("Wrong team on rank 1", tableStats.getStanding().get(0).getTeam(), "Liverpool");
        Assert.assertEquals("Wrong team on rank 2", tableStats.getStanding().get(1).getTeam(), "Crvena Zvezda");
        Assert.assertEquals("Wrong team on rank 3", tableStats.getStanding().get(2).getTeam(), "Marsej");
    }

    public static void validatingProcessedTables(TableStatsDTO[] response) {
        for (TableStatsDTO tableStats : response) {
            if (GroupName.A.equals(tableStats.getGroup())) {
                ChampionsLeagueTestHelper.assertsForProcessedTableA(tableStats);
            } else if (GroupName.B.equals(tableStats.getGroup())) {
                ChampionsLeagueTestHelper.assertsForProcessedTableB(tableStats);
            }
        }
    }

    public static void assertsForUpdatedTableA(TableStatsDTO tableStats) {
        Assert.assertEquals("Wrong number of team", tableStats.getStanding().size(), 4);
        Assert.assertEquals("Wrong team on rank 1", tableStats.getStanding().get(0).getTeam(), "Arsenal");
        Assert.assertEquals("Wrong team on rank 2", tableStats.getStanding().get(1).getTeam(), "PSG");
        Assert.assertEquals("Wrong team on rank 3", tableStats.getStanding().get(2).getTeam(), "Ludogorets");
        Assert.assertEquals("Wrong team on rank 4", tableStats.getStanding().get(3).getTeam(), "Basel");

    }

    public static void assertsForUpdatedTableB(TableStatsDTO tableStats) {
        Assert.assertEquals("Wrong number of team", tableStats.getStanding().size(), 3);
        Assert.assertEquals("Wrong team on rank 1", tableStats.getStanding().get(0).getTeam(), "Liverpool");
        Assert.assertEquals("Wrong team on rank 2", tableStats.getStanding().get(1).getTeam(), "Marsej");
        Assert.assertEquals("Wrong team on rank 3", tableStats.getStanding().get(2).getTeam(), "Crvena Zvezda");
    }

    public static void validatingUpdatedTable(TableStatsDTO[] response) {
        for (TableStatsDTO tableStats : response) {
            if (GroupName.A.equals(tableStats.getGroup())) {
                ChampionsLeagueTestHelper.assertsForUpdatedTableA(tableStats);
            } else if (GroupName.B.equals(tableStats.getGroup())) {
                ChampionsLeagueTestHelper.assertsForUpdatedTableB(tableStats);
            }
        }
    }

    public static void validatingUpdatedTableWithDrawResults(TableStatsDTO[] response) {
        for (TableStatsDTO tableStats : response) {
            if (GroupName.A.equals(tableStats.getGroup())) {
                ChampionsLeagueTestHelper.assertsForProcessedTableA(tableStats);
            } else if (GroupName.B.equals(tableStats.getGroup())) {
                ChampionsLeagueTestHelper.assertsForUpdatedDrawTableB(tableStats);
            }
        }
    }

    public static void assertsForUpdatedDrawTableB(TableStatsDTO tableStats) {
        Assert.assertEquals("Wrong number of team", tableStats.getStanding().size(), 3);
        Assert.assertEquals("Wrong team on rank 1", tableStats.getStanding().get(0).getTeam(), "Crvena Zvezda");
        Assert.assertEquals("Wrong team on rank 2", tableStats.getStanding().get(1).getTeam(), "Liverpool");
        Assert.assertEquals("Wrong team on rank 3", tableStats.getStanding().get(2).getTeam(), "Marsej");
    }
}
