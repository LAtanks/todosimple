package me.learning.TodoSimple.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");
    
    private final Integer code;
    private final String description;
    
    @SuppressWarnings({"5.1.9", "9.6.4.%"})
    public static ProfileEnum toEnum(Integer code){
        
        if(Objects.isNull(code)){
            return null;
        }
        
        for(ProfileEnum x : ProfileEnum.values()){
            if(code.equals(x.getCode()))
                return x;
        }
        
        throw new IllegalArgumentException("invalid code: " + code);
    }
}
