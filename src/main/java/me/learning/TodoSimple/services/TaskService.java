package me.learning.TodoSimple.services;

import me.learning.TodoSimple.models.Task;
import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.repositories.ITaskRepository;
import me.learning.TodoSimple.services.exceptions.DataBindingViolationException;
import me.learning.TodoSimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new ObjectNotFoundException("Task id: "+ id +" don't exists"));
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
            throw new DataBindingViolationException("It is not possible to delete because there are related entities.");
        }
    }

    public List<Task> findAllByUserId(Long userId) {
        List<Task> task = this.taskRepository.findByUser_Id(userId);

        return task;
    }
}
