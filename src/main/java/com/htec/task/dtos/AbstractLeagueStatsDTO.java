package com.htec.task.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.htec.task.enums.GroupName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Abstract league stats transfer object
 * Contains base information
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractLeagueStatsDTO {

    /**
     * Champions league title
     */
    @NotBlank(message = "League title is required")
    private String leagueTitle;

    /**
     * Match day, e.g round 1
     */
    @NotNull(message = "Matchday is required")
    private int matchday;

    /**
     * Group
     * {@link GroupName}
     */
    @NotNull(message = "Group is required")
    private GroupName group;

}
