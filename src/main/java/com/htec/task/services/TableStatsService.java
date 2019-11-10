package com.htec.task.services;

import com.htec.task.enums.GroupName;
import com.htec.task.models.TableStats;

import java.util.List;
import java.util.Set;

/**
 * Service for {@link TableStats}
 */

public interface TableStatsService {

    /**
     * Get tables
     *
     * @param groupNames list {@link GroupName}     table group names
     * @return list {@link TableStats}
     */
    List<TableStats> getTables(List<GroupName> groupNames);

    /**
     * Return all existing tables
     *
     * @return list {@link TableStats}
     */
    List<TableStats> getAllTables();

    /**
     * Return requested tables
     *
     * @param groupNames set {@link String}     table group names
     * @return list {@link TableStats}          list of table stats
     */
    List<TableStats> getRequestedTables(Set<String> groupNames);
}
