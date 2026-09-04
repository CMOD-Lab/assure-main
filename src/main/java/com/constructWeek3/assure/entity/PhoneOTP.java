package com.constructWeek3.assure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhoneOTP {

    @Id
    @GeneratedValue
    private Long id;

    private String userMobile;
    private String otp;

    public PhoneOTP(String userMobile, String otp) {
        this.userMobile = userMobile;
        this.otp = otp;
    }
}
