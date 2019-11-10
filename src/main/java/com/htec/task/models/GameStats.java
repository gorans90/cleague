package com.htec.task.models;

import com.htec.task.enums.GroupName;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Game stats database model
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(schema = "cl", name = "game_stats")
@Immutable
public class GameStats extends LeagueInfos {

    /**
     * Game stats unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Group model field
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "group_name", nullable = false)
    private GroupName group;

    /**
     * Home team name model field
     */
    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    /**
     * Away team name model field
     */
    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    /**
     * Kickoff date and time model field
     */
    @Column(name = "kickoff_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date kickoffAt;

    /**
     * Score of the game model field
     */
    @Column(name = "score", nullable = false)
    private String score;

    @Builder
    public GameStats(String leagueTitle, Integer matchday, GroupName group, String homeTeam, String awayTeam, Date kickoffAt, String score) {
        super(leagueTitle, matchday);
        this.group = group;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.kickoffAt = kickoffAt;
        this.score = score;
    }
}
