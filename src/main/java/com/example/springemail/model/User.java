package com.example.springemail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonProperty("email_address")
    private String emailAddress;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("body")
    private String body;
}
