package com.htec.task.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.htec.task.enums.GroupName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Table stats transfer object
 */

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableStatsDTO extends AbstractLeagueStatsDTO {

    /**
     * Standing for table
     *
     * @List {@link StandingDTO}
     */
    @NotNull(message = "Standing is required")
    private List<StandingDTO> standing;

    @Builder
    public TableStatsDTO(String leagueTitle, int matchday, GroupName group, List<StandingDTO> standing) {
        super(leagueTitle, matchday, group);
        this.standing = standing;
    }
}
