package me.learning.TodoSimple.services;

import me.learning.TodoSimple.models.Task;
import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.models.enums.ProfileEnum;
import me.learning.TodoSimple.models.projection.TaskProjection;
import me.learning.TodoSimple.repositories.ITaskRepository;
import me.learning.TodoSimple.security.UserSpringSecurity;
import me.learning.TodoSimple.services.exceptions.AuthorizationException;
import me.learning.TodoSimple.services.exceptions.DataBindingViolationException;
import me.learning.TodoSimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private UserService userService;
    public List<TaskProjection> findAllByUserId(Long userId) {

        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userId.equals(userSpringSecurity.getUser().getId())) {
            throw new AuthorizationException("Acesso negado!");
        }
        List<TaskProjection> task = this.taskRepository.findByUser_Id(userId);

        return task;
    }
    public Task findById(Long id){
        UserSpringSecurity userSpringSecurity = authenticated();


        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)
                && !this.findAllByUserId(userSpringSecurity.getUser().getId())
                .stream()
                .anyMatch(t -> t.getId().equals(id)))
        {
            System.out.println();
            throw new AuthorizationException("Acesso negado!");
        }
        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new ObjectNotFoundException("Task id: "+ id +" don't exists"));
    }

    @Transactional
    public Task create(Task obj){

        User user = Objects.requireNonNull(UserService.authenticated()).getUser();
        obj.setId(null);
        obj.setUser(user);

        LocalDateTime now = LocalDateTime.now();

        if(Objects.isNull(obj.getEndAt())){
           obj.setEndAt(obj.getEndAt());
        }

        if(obj.getEndAt().isBefore(now) || obj.getEndAt().isEqual(now)){
            throw new DataBindingViolationException("The end date must be greater than the current date.");
        }

        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = this.findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        newObj.setTitle(obj.getTitle());
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

    public static UserSpringSecurity authenticated(){
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
