package com.htec.task.repositories;

import com.htec.task.models.Standing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Crud repository for {@link Standing}
 */

@Repository
public interface StandingRepository extends CrudRepository<Standing, Long> {

    /**
     * Find standing by team name, ignore case
     *
     * @param team name of the team
     * @return optional {@link Standing}     standing for requested team
     */
    Optional<Standing> findFirstByTeamIgnoreCase(String team);

    /**
     * Find standings for requested teams
     *
     * @param teams set {@link String}      set of the team names
     * @return list {@link Standing}        list of standings for requested teams
     */
    List<Standing> findByTeamIn(Set<String> teams);

}
