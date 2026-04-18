package com.spring.openai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name="helpdesk_tickets")
@AllArgsConstructor
@NoArgsConstructor
public class HelpDeskTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String issue;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime eta;
}
