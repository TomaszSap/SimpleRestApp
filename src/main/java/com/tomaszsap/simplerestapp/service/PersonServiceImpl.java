package com.tomaszsap.simplerestapp.service;


import com.tomaszsap.simplerestapp.exception.APIException;
import com.tomaszsap.simplerestapp.model.Person;
import com.tomaszsap.simplerestapp.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.tomaszsap.simplerestapp.constant.AppConstant.getNullPropertyNames;

@Service
class PersonServiceImpl implements PersonService {
    PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;

    }

    @Override
    public boolean addPerson(Person person) {
        boolean isSaved = false;
        Optional<Person> isAccount = Optional.ofNullable(personRepository.findByEmail(person.getEmail()));
        if (isAccount.isEmpty()) {

            var saved = personRepository.save(person);
            if (saved != null && saved.getId() > 0)
                isSaved = true;
        } else {
            throw new APIException(404, "Email is in usage!");
        }
        return isSaved;
    }

    @Override
    public List<Person> showAll() {
        return personRepository.findAll();
    }

    public List<Person> showAllSortedBy(String type) {
        String typeLower = type.toLowerCase();
        List<Person> persons = showAll();
        switch (typeLower) {
            case "name" -> persons.sort(Comparator.comparing(Person::getName));
            case "surname" -> persons.sort(Comparator.comparing(Person::getSurname));
            case "email" -> persons.sort(Comparator.comparing(Person::getEmail));
            default -> throw new APIException(404, "Invalid sort value !");
        }
        return persons;
    }

    @Override
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    @Override
    public void updatePerson(int personId, Person update) {
        var personEntity = personRepository.findById(personId);
        if (personEntity.isPresent()) {
            BeanUtils.copyProperties(update, personEntity, getNullPropertyNames(update));
            personRepository.save(update);
        }
    }
}
