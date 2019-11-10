package com.htec.task.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Basic league information
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class LeagueInfos {

    /**
     * League title model field
     */
    @Column(name = "league_title", nullable = false)
    private String leagueTitle;

    /**
     * Matchday model field
     */
    @Column(name = "matchday", nullable = false)
    private int matchday;

}
