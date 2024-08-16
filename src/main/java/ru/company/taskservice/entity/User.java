package ru.company.taskservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.company.taskservice.entity.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // позволяет сериализовать поле createdTasks
    private List<Task> createdTasks = new ArrayList<>();

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // позволяет сериализовать поле createdTasks
    private List<Task> assignedTasks = new ArrayList<>();

    @ToString.Include(name = "password")
    @JsonManagedReference // позволяет сериализовать поле assignedTasks
    private String maskPassword(){return "**********";}
}
