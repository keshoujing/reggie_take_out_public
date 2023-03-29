package com.reggie.utils;

import com.azure.communication.email.models.*;
import com.azure.communication.email.*;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailUtils {

    @Value("${reggie.azure.connectionString}")
    private String connectionString;

    @Value("${reggie.azure.emailFrom}")
    private String emailFrom;

    public void sendEmail(String signName, String templateCode,String emailAddress,String param) {
        // You can get your connection string from your resource in the Azure portal.

        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        EmailMessage message = new EmailMessage()
                .setSenderAddress(emailFrom)
                .setToRecipients(emailAddress)
                .setSubject("Reggie take away verification code.")
                .setBodyPlainText("This is demo of reggie take away project, please ignore this message if you accidentally receive this.\n\n"
                                + "Your code: " + param);

        try {
            SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message, null);
            //PollResponse<EmailSendResult> response = poller.waitForCompletion();
            //System.out.println("Operation Id:" + response.getValue().getId());
            log.info("Email send successful.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
