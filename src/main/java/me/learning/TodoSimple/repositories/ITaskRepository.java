package me.learning.TodoSimple.repositories;

import me.learning.TodoSimple.models.Task;
import me.learning.TodoSimple.models.projection.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    List<TaskProjection> findByUser_Id(@Param(("id")) Long id);
}
