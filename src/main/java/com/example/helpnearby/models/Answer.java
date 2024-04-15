package com.example.helpnearby.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId ;
    @OneToOne(cascade = CascadeType.MERGE)
    private Order order ;
    @Column(nullable = false)
    private String status ;
    @ManyToOne
    @JoinColumn(nullable = false, name = "person_id")
    private User user;
}

