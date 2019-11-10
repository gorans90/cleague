package com.htec.task.models;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Table stats database model
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(schema = "cl", name = "table_stats")
@Immutable
public class TableStats extends LeagueInfos {

    /**
     * Table stats unique identifier
     */
    @Id
    @Column(name = "statistics_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Table group name
     */
    @Column(name = "group_name", unique = true, nullable = false)
    protected String group;

    /**
     * List of standings for table
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ElementCollection
    private List<Standing> standings;

    @Builder
    public TableStats(String leagueTitle, Integer matchday, String group, List<Standing> standings) {
        super(leagueTitle, matchday);
        this.group = group;
        this.standings = standings;
    }
}
