package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import com.ms.email.utils.email.JavaEmailSender;
import com.ms.email.utils.email.SESEmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    Logger logger = LogManager.getLogger(EmailService.class);

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SesClient sesClient;

    @Transactional
    public EmailModel sendJavaEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = JavaEmailSender.sendEMail(emailModel);
            emailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);
            logger.info("Email enviado com java sucesso para: {} ", emailModel.getEmailTo());
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            logger.error("Email com erro Java: {} ", emailModel.toString());
        }

        emailModel = emailRepository.save(emailModel);
        logger.info("Email salvo com sucesso id: {} ", emailModel.getId());
        return emailModel;
    }

    @Transactional
    public EmailModel sendSesEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SendEmailRequest sendEmailRequest = SESEmailSender.sendEMail(emailModel);
            sesClient.sendEmail(sendEmailRequest);
            emailModel.setStatusEmail(StatusEmail.SENT);
            logger.info("Email enviado AWS SES com sucesso para: {} ", emailModel.getEmailTo());
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            logger.error("Email com erro AWS SES: {} ", emailModel.toString());
        }

        emailModel = emailRepository.save(emailModel);
        logger.info("Email salvo com sucesso id: {} ", emailModel.getId());
        return emailModel;
    }

    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }

    public Optional<EmailModel> findById(UUID id) {
        return emailRepository.findById(id);
    }
}
