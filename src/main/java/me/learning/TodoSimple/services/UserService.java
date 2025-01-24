package me.learning.TodoSimple.services;

import jakarta.validation.Valid;
import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.models.dto.UserCreateDTO;
import me.learning.TodoSimple.models.dto.UserUpdateDTO;
import me.learning.TodoSimple.models.enums.ProfileEnum;
import me.learning.TodoSimple.repositories.IUserRepository;
import me.learning.TodoSimple.security.UserSpringSecurity;
import me.learning.TodoSimple.services.exceptions.AuthorizationException;
import me.learning.TodoSimple.services.exceptions.DataBindingViolationException;
import me.learning.TodoSimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private IUserRepository userRepository;

    public User findById(Long id){
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getUser().getId())) {
            throw new AuthorizationException("Acesso negado!");
        }

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("User id: " + id +" don't exists"));
    }

    @Transactional
    public User create(User obj){
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = this.findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword((this.bCryptPasswordEncoder.encode(obj.getPassword())));
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

    public static UserSpringSecurity authenticated(){
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public User fromDTO(@Valid UserCreateDTO obj){
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());
        return user;
    }
    public User fromDTO(@Valid UserUpdateDTO obj){
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());
        return user;
    }
}
