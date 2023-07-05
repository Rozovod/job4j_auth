package ru.job4j.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.auth.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
