package com.htec.task.services.impl;

import com.htec.task.enums.GroupName;
import com.htec.task.models.TableStats;
import com.htec.task.repositories.TableStatsRepository;
import com.htec.task.services.TableStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for {@link TableStats} implements {@link TableStatsService}
 */

@Slf4j
@Service
public class TableStatsServiceImpl implements TableStatsService {

    @Lazy
    @Autowired
    private TableStatsRepository tableStatsRepository;

    /**
     * Get tables
     *
     * @param groupNames set {@link String}     table group names
     * @return list {@link TableStats}
     */
    @Override
    public List<TableStats> getTables(List<GroupName> groupNames) {
        return CollectionUtils.isEmpty(groupNames) ?
                getAllTables() : getRequestedTables(groupNames.stream().map(Enum::name).collect(Collectors.toSet()));
    }

    /**
     * Return all existing tables
     *
     * @return list {@link TableStats}
     */
    @Override
    public List<TableStats> getAllTables() {
        Iterable<TableStats> tableStatsIt = tableStatsRepository.findAll();
        List<TableStats> tableStats = new ArrayList<>();
        tableStatsIt.forEach(tableStats::add);
        return tableStats;
    }

    /**
     * Return requested tables
     *
     * @param groupNames set {@link String}     table group names
     * @return list {@link TableStats}          list of table stats
     */
    @Override
    public List<TableStats> getRequestedTables(Set<String> groupNames) {
        return tableStatsRepository.findByGroupIn(groupNames);
    }

}
