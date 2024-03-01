package com.example.demo.repos;


import com.example.demo.models.Event;
import com.example.demo.models.Register;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Register, Long> {
    // Add custom methods if needed
	 @Query("SELECT i FROM Register i WHERE i.email = :email AND i.password = :password")
	    Optional<Register> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	    @Query("SELECT i FROM Register i WHERE i.mobileNumber = :mobileNumber AND i.password = :password")
	    Optional<Register> findByMobileNumberAndPassword(@Param("mobileNumber") String mobileNumber, @Param("password") String password);
	    @Query("SELECT i FROM Register i WHERE i.email = :email OR i.mobileNumber = :mobileNumber")
	    Optional<Register> findByEmailOrMobileNumber(@Param("email") String email, @Param("mobileNumber") String mobileNumber);

		Optional<Register> findByEmail(String emailOrMobileNumber);

		Optional<Register> findByMobileNumber(String emailOrMobileNumber);

	

		
}

