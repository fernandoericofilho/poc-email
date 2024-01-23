package com.ms.email.utils.email;

import com.ms.email.models.EmailModel;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public class JavaEmailSender {

    public static SimpleMailMessage sendEMail(EmailModel emailModel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailModel.getEmailFrom());
        message.setTo(emailModel.getEmailTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getText());
        message.setSentDate(new Date());

        return message;
    }
}
