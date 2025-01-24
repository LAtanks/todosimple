package me.learning.TodoSimple.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = Task.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    public static final String TABLE_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "description", length = 255, nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null)
            return false;
        if(!(o instanceof  User))
            return false;
        Task other = (Task) o;

        if(this.id == null){
            if(other.id != null)
                return false;
            else if(!this.id.equals(other.id))
                return false;

        }

        return Objects.equals(this.id, other.id) && Objects.equals(this.user, other.user) && Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
