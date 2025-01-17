package me.learning.TodoSimple.services;

import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.models.enums.ProfileEnum;
import me.learning.TodoSimple.repositories.ITaskRepository;
import me.learning.TodoSimple.repositories.IUserRepository;
import me.learning.TodoSimple.services.exceptions.DataBindingViolationException;
import me.learning.TodoSimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    //@Autowired
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("User id: " + id +" don't exists"));
    }

    @Transactional
    public User create(User obj){
        obj.setId(null);
        obj.setPassword(this.passwordEncoder().encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = this.findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword((this.passwordEncoder().encode(obj.getPassword())));
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        this.findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("It is not possible to delete, because there are related entities.");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
