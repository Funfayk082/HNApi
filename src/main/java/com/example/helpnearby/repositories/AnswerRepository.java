package com.example.helpnearby.repositories;

import com.example.helpnearby.models.Answer;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findAnswerByOrderId(Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Answer a SET a.status=:status WHERE a.id =:id")
    void updateAnswerById(@NonNull Long id, @NonNull String status);
}
