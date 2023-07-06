package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private final PersonService persons;

    @GetMapping("/")
    public List<Person> findAll() {
        return persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = persons.findById(id);
        return new ResponseEntity<>(person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Optional<Person> createdPerson = persons.create(person);
        ResponseEntity<Person> response = new ResponseEntity<>(HttpStatus.CONFLICT);
        if (createdPerson.isPresent()) {
            response = new ResponseEntity<>(createdPerson.get(), HttpStatus.CREATED);
        }
        return response;
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        boolean isUpdated = persons.update(person);
        if (!isUpdated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        boolean isDeleted = persons.delete(person);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
