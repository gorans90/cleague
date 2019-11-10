package com.htec.task.utils;

import com.htec.task.models.Standing;

import java.util.List;

/**
 * Sorting for {@link Standing}
 */

public class StandingSort {

    /**
     * Sort standing based on request
     *
     * @param standings list {@link Standing}       list of standings
     * @return list {@link Standing}               sorted list of standings
     */
    public static List<Standing> compareStandings(List<Standing> standings) {
        standings.sort((s1, s2) -> {
            int res = s2.getPoints().compareTo(s1.getPoints());
            if (res != 0) {
                return res;
            } else {
                res = s2.getGoals().compareTo(s1.getGoals());
                return res != 0 ? res : s2.getGoalDifference().compareTo(s1.getGoalDifference());
            }
        });

        return standings;
    }

    /**
     * Sorting standings based on request and rank update
     *
     * @param standings list {@link Standing}       list of standings
     * @return list {@link Standing}               sorted list of standings with updated ranks
     */
    public static List<Standing> compareStandingsAndRankUpdate(List<Standing> standings) {
        StandingSort.compareStandings(standings);
        int rank = 1;
        for (Standing standing : standings) {
            standing.setRank(rank++);
        }
        return standings;
    }

}
