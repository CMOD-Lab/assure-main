package com.constructWeek3.assure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Hospitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;
    private String name;

    @ManyToOne
    private Location location;


    @OneToMany
    @JoinColumn(name="hospitals_hospital_Id")
    private List<Claim> claims = new ArrayList<>();

    public void addClaim(Claim claim) {
        this.claims.add(claim);
    }
}
