package com.ms.email.models;

import com.ms.email.dtos.EmailDto;
import com.ms.email.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_EMAIL")
public class EmailModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;

    public static EmailModel fromDto(EmailDto emailDto) {
        return EmailModel.builder()
                .ownerRef(emailDto.getOwnerRef())
                .emailFrom(emailDto.getEmailFrom())
                .emailTo(emailDto.getEmailTo())
                .subject(emailDto.getSubject())
                .text(emailDto.getText())
                .build();
    }
}
