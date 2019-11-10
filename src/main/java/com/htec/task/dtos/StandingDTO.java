package com.htec.task.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Standing transfer object
 */

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingDTO {

    /**
     * Rank present position on table
     */
    @NotNull(message = "Rank is required")
    private int rank;

    /**
     * Team name
     */
    @NotBlank(message = "Team is required")
    private String team;

    /**
     * Number of played games
     */
    @NotNull(message = "Number of played games is required")
    private int playedGames;

    /**
     * Points
     */
    @NotNull(message = "Points is required")
    private int points;

    /**
     * Goals
     */
    @NotNull(message = "Goals is required")
    private int goals;

    /**
     * Number of received goals
     */
    @NotNull(message = "Goals against is required")
    private int goalsAgainst;

    /**
     * Goal difference is the scored goals minus received goals
     */
    @NotNull(message = "Goal difference is required")
    private int goalDifference;

    /**
     * Number of won games
     */
    @NotNull(message = "Number of won games is required")
    private int win;

    /**
     * Number of lost games
     */
    @NotNull(message = "Number of lost games is required")
    private int lose;

    /**
     * Number of draw games
     */
    @NotNull(message = "Number of draw games is required")
    private int draw;

}
