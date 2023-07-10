package ru.job4j.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.auth.domain.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByLogin(String login);
}
