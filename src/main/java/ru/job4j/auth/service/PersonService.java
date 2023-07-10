package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;
    private static final Logger LOG = LoggerFactory.getLogger(PersonRepository.class);

    public Optional<Person> create(Person person) {
        Optional<Person> personOptional = Optional.empty();
        try {
            personRepository.save(person);
            personOptional = Optional.of(person);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return personOptional;
    }

    public boolean update(Person person) {
        boolean rsl = false;
        if (personRepository.existsById(person.getId())) {
            personRepository.save(person);
            rsl = true;
        }
        return rsl;
    }

    public boolean delete(Person person) {
        boolean rsl = false;
        if (personRepository.existsById(person.getId())) {
            personRepository.delete(person);
            rsl = true;
        }
        return rsl;
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public UserDetails loadUserByUsername(String login) {
        Optional<Person> userOptional = personRepository.findByLogin(login);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return new User(userOptional.get().getLogin(), userOptional.get().getPassword(), emptyList());
    }
}
