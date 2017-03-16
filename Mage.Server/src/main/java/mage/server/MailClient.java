package mage.server;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mage.server.util.ConfigSettings;
import org.apache.log4j.Logger;

public final class MailClient {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static boolean sendMessage(String email, String subject, String text) {
        if (email.isEmpty()) {
            logger.info("Email is not sent because the address is empty");
            return false;
        }
        ConfigSettings config = ConfigSettings.instance;

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtps.host", config.getMailSmtpHost());
        properties.setProperty("mail.smtps.port", config.getMailSmtpPort());
        properties.setProperty("mail.smtps.auth", "true");
        properties.setProperty("mail.user", config.getMailUser());
        properties.setProperty("mail.password", config.getMailPassword());

        Session session = Session.getDefaultInstance(properties);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getMailFromAddress()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(text);

            Transport trnsport = session.getTransport("smtps");
            trnsport.connect(null, properties.getProperty("mail.password"));
            message.saveChanges();
            trnsport.sendMessage(message, message.getAllRecipients());
            trnsport.close();

            return true;
        }catch (MessagingException ex) {
            logger.error("Error sending message to " + email, ex);
        }
        return false;
    }
}
