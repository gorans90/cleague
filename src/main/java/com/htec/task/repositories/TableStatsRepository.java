package com.htec.task.repositories;

import com.htec.task.models.TableStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Crud repository for {@link TableStats}
 */

@Repository
public interface TableStatsRepository extends CrudRepository<TableStats, Long> {

    /**
     * Find table stats by group name
     *
     * @param group {@link String}            group name
     * @return optional {@link TableStats}    optional table stats
     */
    Optional<TableStats> findByGroupIgnoreCase(String group);

    /**
     * Find table stats by set of group names
     *
     * @param groups set {@link String}       set of group names
     * @return list {@link TableStats}        list of table stats for groups
     */
    List<TableStats> findByGroupIn(Set<String> groups);

}
