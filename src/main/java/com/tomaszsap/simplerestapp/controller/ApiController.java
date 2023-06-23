package com.tomaszsap.simplerestapp.controller;

import com.tomaszsap.simplerestapp.model.Person;
import com.tomaszsap.simplerestapp.model.RequestCounterFilter;
import com.tomaszsap.simplerestapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api")
@RestController
public class ApiController {

    PersonService personService;

    @Autowired
    public ApiController(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    private RequestCounterFilter requestCounterFilter;

    @GetMapping("/getSortedBy")
    public List<Person> getllSortedBy(@RequestParam String sortBy) {
        return personService.showAllSortedBy(sortBy);
    }

    @GetMapping("/allRequests")
    public ResponseEntity<String> allRequests() {
        int requestCount = requestCounterFilter.getRequestCount();
        return ResponseEntity.ok("The number of requests : " + requestCount);
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody @Valid Person person) {
        boolean isSaved = personService.addPerson(person);
        return ResponseEntity.ok("Person saved successfully");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.showAll());
    }

    @PutMapping("/updateById")
    public void updateById(@RequestParam int id, @RequestBody Person update) {
        personService.updatePerson(id, update);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam int id) {
        personService.deletePerson(id);
    }
}
