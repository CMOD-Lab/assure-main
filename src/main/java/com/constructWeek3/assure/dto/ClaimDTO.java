package com.constructWeek3.assure.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDTO {

    private Long id;
    private Long memberId;
    private String userName;
    private Date dateOfClaim;
    private Float amountToClaim;
    private Date submissionDate;
    private String nameOfMember;
    private String status;
    private String claimItem;
    private String policyBookingName;
}
