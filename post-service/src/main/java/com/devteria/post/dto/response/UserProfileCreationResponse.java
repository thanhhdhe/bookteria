package com.devteria.post.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationResponse {
    String id;
    String username;
    String email;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String city;
}
