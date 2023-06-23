package com.tomaszsap.simplerestapp.service;

import com.tomaszsap.simplerestapp.model.Person;

import java.util.List;

public interface PersonService {
    boolean addPerson(Person person);

    List<Person> showAll();

    void deletePerson(int id);

    void updatePerson(int personId, Person personUpdate);

    List<Person> showAllSortedBy(String type);
}
