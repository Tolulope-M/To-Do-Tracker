package com.morenike.activitytracker.Repository;

import com.morenike.activitytracker.Entity.Task;
import com.morenike.activitytracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<List<Task>> findAllByUser(User user);

    Optional<List<Task>> findTasksByUserAndStatus(User user, String status);

    Task findTasksByUserAndId(User user, int id);

    @Query(value = "SELECT * from tasks where title like %?1% or description like %?1%", nativeQuery = true)
    Optional<List<Task>> findTasksByTitle(String title);


}