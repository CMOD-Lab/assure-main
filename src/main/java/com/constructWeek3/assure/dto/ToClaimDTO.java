package com.constructWeek3.assure.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ToClaimDTO {
    private String type;
    private Long aadharNumber;
    private String nameOfMember;

    private String hospitalName;

    private Date dateOfTreatment;
    private Date submissionDate;
    private String status;
    private String claimItem;
    private Float amountToClaim;
    private Boolean preauthorizedConfirmation;
    private Boolean followUpVisits;
}
