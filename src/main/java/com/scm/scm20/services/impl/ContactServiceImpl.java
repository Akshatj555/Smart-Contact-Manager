package com.scm.scm20.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.helpers.ResourceNotFoundException;
import com.scm.scm20.repository.ContactRepo;
import com.scm.scm20.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public void deleteContact(String id) {
        Contact contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given Id " + id));
        contactRepo.delete(contact);            
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getContactById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given Id " + id));
    }

    @Override
    public List<Contact> getContactByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Contact saveContact(Contact contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public List<Contact> searchContact(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Contact updateContact(Contact contact) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Contact> getContactByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }

}
