package me.learning.TodoSimple.services;

import me.learning.TodoSimple.models.Task;
import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException("User id: "+ id +" don't exists"));
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = this.findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        this.findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("It is not possible to delete because there are related entities.");
        }
    }
}
