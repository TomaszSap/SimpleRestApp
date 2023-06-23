package com.tomaszsap.simplerestapp.service;

import com.tomaszsap.simplerestapp.exception.APIException;
import com.tomaszsap.simplerestapp.model.Person;
import com.tomaszsap.simplerestapp.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;
    PersonService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PersonServiceImpl(personRepository);
    }
    @Test
    void addPerson_shouldReturnTrueWhenPersonIsSaved() {
        //given
        Person person = new Person();
        person.setName("test");
        person.setSurname("test");
        person.setEmail("test@gmail.com");
        person.setMobileNumber("999999999");
        person.setPesel("11111111111");
        //when
        underTest.addPerson(person);
        //then
        ArgumentCaptor<Person> personArgumentCaptor=ArgumentCaptor.forClass(Person.class);
        verify(personRepository).save(personArgumentCaptor.capture());
        Person capturedPerson=personArgumentCaptor.getValue();
        assertThat(capturedPerson).isEqualTo(person);
    }
    @Test
    void addPerson_shouldReturnFalseWhenTryingSavePersonWithExistingEmail() {
        //given
        Person person = new Person();
        person.setName("test");
        person.setSurname("test");
        person.setEmail("test@gmail.com");
        person.setMobileNumber("999999999");
        person.setPesel("11111111111");
        given(personRepository.findByEmail(person.getEmail())).willReturn(person);
        assertThatThrownBy(()->underTest.addPerson(person)).isInstanceOf(APIException.class).hasMessage("Email is in usage!");
        when(personRepository.save(person)).thenReturn(null);
        var result = personRepository.save(person);
        assertNull(result);
        verify(personRepository, times(1)).save(person);
    }
    @Test
    void addPerson_shouldReturnFalseWhenTryingSavePerson() {
        //given
        Person person = new Person();
        when(personRepository.save(person)).thenReturn(null);

        var result = personRepository.save(person);

        assertNull(result);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void showAll_shouldReturnListOfPersons() {
        //when
        underTest.showAll();
        //then
        verify(personRepository).findAll();
    }

    @Test
    void deletePerson_shouldInvokeDeleteById() {
        int personId = 1;

        personRepository.deleteById(personId);

        verify(personRepository, times(1)).deleteById(personId);
    }

    @Test
    void updatePerson_shouldUpdateExistingPerson() {
        int personId = 0;
        Person existingPerson = new Person();
        existingPerson.setId(personId);
        Person updatedPerson = new Person();
        updatedPerson.setName("John");
        updatedPerson.setSurname("Doe");
        updatedPerson.setEmail("JohnDoe@gmail.com");
        updatedPerson.setMobileNumber("999999999");
        updatedPerson.setPesel("11111111111");
        Optional<Person> optionalPerson = Optional.of(existingPerson);
        when(personRepository.findById(personId)).thenReturn(optionalPerson);

        underTest.updatePerson(personId, updatedPerson);
        ArgumentCaptor<Person> personArgumentCaptor=ArgumentCaptor.forClass(Person.class);
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(personArgumentCaptor.capture());
        assertEquals(existingPerson.getId(), personArgumentCaptor.getValue().getId());
    }
}