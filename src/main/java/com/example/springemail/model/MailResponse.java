package com.example.springemail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailResponse {
    @JsonProperty("is_success")
    private boolean isSuccess;
}
