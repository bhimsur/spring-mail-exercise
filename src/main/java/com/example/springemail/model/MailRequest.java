package com.example.springemail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest implements Serializable {
    private static final long serialVersionUID = 6768158207961411104L;

    private String accountNumber;
    private String currency;
    private String interestRate;
    private String aroType;
    private String timePeriod;
    private String createDate;
    private String accountOwnerName;
    private String balance;
    private String amountWords;
}
