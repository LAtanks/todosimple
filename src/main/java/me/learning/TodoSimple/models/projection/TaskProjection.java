package me.learning.TodoSimple.models.projection;

import java.time.LocalDateTime;

public interface TaskProjection {
    public Long getId();
    public String getTitle();
    public String getDescription();
    public LocalDateTime getCreatedAt();
    public LocalDateTime getEndAt();
}
