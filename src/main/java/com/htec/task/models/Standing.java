package com.htec.task.models;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.*;

import javax.persistence.*;

/**
 * Standing database model
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(schema = "cl", name = "standing")
@Immutable
public class Standing {

    /**
     * Standing unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Team position on table
     */
    @OrderBy
    @Column(name = "rank", nullable = false)
    protected int rank;

    /**
     * Team name
     */
    @Column(name = "team", nullable = false)
    protected String team;

    /**
     * Number of played games for team
     */
    @Column(name = "played_games", nullable = false)
    protected int playedGames;

    /**
     * Number of collected points for team
     */
    @Column(name = "points", nullable = false)
    protected Integer points;

    /**
     * Number of scored goals
     */
    @Column(name = "goals", nullable = false)
    protected Integer goals;

    /**
     * Number of received goals
     */
    @Column(name = "goals_against", nullable = false)
    protected Integer goalsAgainst;

    /**
     * Goal difference
     * Calculate: goals - goalsAgainst
     */
    @Column(name = "goal_difference", nullable = false)
    protected Integer goalDifference;

    /**
     * Number of won games
     */
    @Column(name = "win", nullable = false)
    protected int win;

    /**
     * Number of lost games
     */
    @Column(name = "lose", nullable = false)
    protected int lose;

    /**
     * Number of draw games
     */
    @Column(name = "draw", nullable = false)
    protected int draw;

}
