package me.learning.TodoSimple.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {
    @NotBlank
    @Size(min = 2, max = 100)
    private String username;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

}
