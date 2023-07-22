package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.PersonDTO;
import ru.job4j.auth.service.PersonService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/persons")
public class PersonController {
    private final PersonService persons;
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll() {
        List<Person> allPersons = persons.findAll();
        return new ResponseEntity<>(allPersons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = persons.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с id: " + id + " не найден"));

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Optional<Person> createdPerson = persons.create(person);
        ResponseEntity<Person> response = new ResponseEntity<>(HttpStatus.CONFLICT);
        if (createdPerson.isPresent()) {
            response = new ResponseEntity<>(createdPerson.get(), HttpStatus.CREATED);
        }
        return response;
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable int id, @Valid @RequestBody PersonDTO personDTO) {
        var person = persons.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с id: " + id + " не найден"));
        person.setPassword(personDTO.getPassword());
        if (!persons.update(person)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
