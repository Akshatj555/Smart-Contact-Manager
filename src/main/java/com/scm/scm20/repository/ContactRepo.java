package com.scm.scm20.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;

@Repository
public interface ContactRepo extends JpaRepository <Contact, String>{

    //Custom finder method
    List<Contact> findByUser(User user);

    // Custom query method 
    @Query("SELECT c from Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

}
