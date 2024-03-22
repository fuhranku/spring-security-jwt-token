package com.ouathtest.demo.domain.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String username;
    private String password;
}
