package ru.kiselev.lunchschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;



@NoRepositoryBean
@Transactional(readOnly = true)
public interface BaseRepository<T> extends JpaRepository<T, Integer> {



}