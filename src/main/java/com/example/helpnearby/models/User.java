package com.example.helpnearby.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String cityName;
    @Column(nullable = false)
    private Long groupId;
    @Column(nullable = false)
    private String contactNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private AuthUser authUser;
    @Column(nullable = false)
    private String fbToken;
}
