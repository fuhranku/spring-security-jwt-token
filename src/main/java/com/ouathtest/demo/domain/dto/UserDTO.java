package com.ouathtest.demo.domain.dto;

import com.ouathtest.demo.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    public static UserDTO from(User user){
        return builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
