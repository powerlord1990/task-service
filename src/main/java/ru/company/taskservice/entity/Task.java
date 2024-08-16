package ru.company.taskservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.company.taskservice.entity.enums.TaskPriority;
import ru.company.taskservice.entity.enums.TaskStatus;

@Entity
@RequiredArgsConstructor
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference // предотвращает сериализацию поля author
    private User author;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    @JsonBackReference // предотвращает сериализацию поля assignee
    private User assignee;

}
