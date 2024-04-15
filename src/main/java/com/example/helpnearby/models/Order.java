package com.example.helpnearby.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.annotations.processing.SQL;

@Data
@Entity
@Table(name = "orderTable")
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cityName;
    private String commentary;
    @Column(nullable = false)
    private String status;
    private String geolocation;
    private String address;
}
