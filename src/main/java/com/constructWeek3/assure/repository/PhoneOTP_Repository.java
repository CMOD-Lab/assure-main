package com.constructWeek3.assure.repository;

import com.constructWeek3.assure.entity.PhoneOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneOTP_Repository extends JpaRepository<PhoneOTP, Long> {
}
