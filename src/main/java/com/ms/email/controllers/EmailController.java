package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class EmailController {

    Logger logger = LogManager.getLogger(EmailController.class);

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-java-email")
    public ResponseEntity<EmailModel> sendingJavaEmail(@RequestBody EmailDto emailDto) {
        return new ResponseEntity<>(emailService.sendJavaEmail(EmailModel.fromDto(emailDto)), HttpStatus.CREATED);
    }

    @PostMapping("/sending-ses-email")
    public ResponseEntity<EmailModel> sendingSesEmail(@RequestBody EmailDto emailDto) {
        return new ResponseEntity<>(emailService.sendSesEmail(EmailModel.fromDto(emailDto)), HttpStatus.CREATED);
    }

    @GetMapping("/emails")
    public ResponseEntity<Page<EmailModel>> getAllEmails(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size,
                                                         @RequestParam(defaultValue = "id") String sort,
                                                         @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARN");
        logger.error("ERROR");
        logger.fatal("FATAL");
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/emails/{id}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value = "id") UUID id) {
        Optional<EmailModel> emailModelOptional = emailService.findById(id);
        return emailModelOptional.<ResponseEntity<Object>>map(
                        emailModel -> ResponseEntity.status(HttpStatus.OK).body(emailModel))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found."));
    }

}
