package com.htec.task.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.htec.task.enums.GroupName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Game stats transfer object
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameStatsDTO extends AbstractLeagueStatsDTO {

    /**
     * Home team name
     */
    @NotBlank(message = "Home team is required")
    private String homeTeam;

    /**
     * Away team name
     */
    @NotBlank(message = "Away team is required")
    private String awayTeam;

    /**
     * Date and time of the game start
     * e.g 2017-09-13T20:45:00
     */
    @NotNull(message = "Kickoff date and time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private Date kickoffAt;

    /**
     * Score of the game
     * e.g 0:0
     */
    @NotBlank(message = "Score of the game is required")
    @Pattern(regexp = "\\d+:\\d+", message = "Score format is not valid")
    private String score;

    @Builder
    public GameStatsDTO(String leagueTitle, int matchday, GroupName group, String homeTeam, String awayTeam, Date kickoffAt, String score) {
        super(leagueTitle, matchday, group);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.kickoffAt = kickoffAt;
        this.score = score;
    }
}
