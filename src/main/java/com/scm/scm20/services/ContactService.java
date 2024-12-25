package com.scm.scm20.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;

public interface ContactService {

    // save contacts
    Contact saveContact(Contact contact);

    // update contact
    Contact updateContact(Contact contact);

    // get contacts
    List<Contact> getAllContacts();

    // get contact by Id
    Contact getContactById(String id);

    // delete the contact
    void deleteContact(String id);

    // search contact
    Page<Contact> searchContactByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchContactByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchContactByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);

    // get contacts by userId
    List<Contact> getContactByUserId(String userId);

    Page<Contact> getContactByUser(User user, int page, int size, String sortField, String sortDirection);

}
