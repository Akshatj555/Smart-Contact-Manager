package com.scm.scm20.services;

import java.util.List;

import com.scm.scm20.entities.Contact;

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
    List<Contact> searchContact(String name, String email, String phoneNumber);

    // get contacts by userId
    List<Contact> getContactByUserId(String userId);

}
