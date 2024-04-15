package com.example.helpnearby.repositories;

import com.example.helpnearby.models.Order;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCityName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status=:status WHERE o.id =:id")
    void updateOrderById(@NonNull Long id, @NonNull String status);
}
