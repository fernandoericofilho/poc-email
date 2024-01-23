package com.ms.email.utils.email;

import com.ms.email.models.EmailModel;
import software.amazon.awssdk.services.ses.model.*;

public class SESEmailSender {

    public static SendEmailRequest sendEMail(EmailModel emailModel) {
        return SendEmailRequest.builder()
                .source(emailModel.getEmailFrom())
                .destination(Destination.builder().toAddresses(emailModel.getEmailTo()).build())
                .message(Message.builder()
                        .subject(Content.builder().data(emailModel.getSubject()).build())
                        .body(Body.builder().text(Content.builder().data(emailModel.getText()).build()).build())
                        .build())
                .build();
    }
}