package ru.kiselev.lunchschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface BaseRepository<T> extends JpaRepository<T, Integer> {


    List<T> findAll();

    Optional<T> findById(int id);
}