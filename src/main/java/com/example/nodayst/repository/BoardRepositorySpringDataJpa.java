package com.example.nodayst.repository;

import com.example.nodayst.domain.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepositorySpringDataJpa extends JpaRepository<Board,Long> {
    boolean existsByTitle(String title);
    Optional<Board> findByTitle(String title);
}
