package com.htec.task.repositories;

import com.htec.task.models.GameStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * Crud repository for {@link GameStats}
 */

@Repository
public interface GameStatsRepository extends CrudRepository<GameStats, Long> {

    /**
     * Find game by requested params
     *
     * @param homeTeam
     * @param awayTeam
     * @param kickoffAt
     * @return optional {@link GameStats}
     */
    Optional<GameStats> findByHomeTeamAndAwayTeamAndKickoffAt(String homeTeam, String awayTeam, Date kickoffAt);

}
