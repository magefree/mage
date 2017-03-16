package mage.server;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.Gmail.Builder;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mage.server.util.ConfigSettings;
import org.apache.log4j.Logger;

public final class GmailClient {

    private static final Logger logger = Logger.getLogger(Main.class);
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".store/xmage");
    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    private static Credential credential;

    public static boolean initilize() {
        try {
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader("client_secrets.json"));
            if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                    || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
                logger.error("client_secrets.json not found");
                return false;
            }

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets,
                    Collections.singleton(GmailScopes.GMAIL_COMPOSE)).setDataStoreFactory(
                            dataStoreFactory).build();

            credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
            return true;
        } catch (IOException | GeneralSecurityException ex) {
            logger.error("Error initializing GmailClient", ex);
        }
        return false;
    }

    public static boolean sendMessage(String email, String subject, String text) {
        if (email.isEmpty()) {
            logger.info("Email is not sent because the address is empty");
            return false;
        }
        try {
            Gmail gmail = new Builder(httpTransport, JSON_FACTORY, credential).setApplicationName("XMage Server").build();

            MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()));
            mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mimeMessage.writeTo(baos);
            Message message = new Message();
            message.setRaw(Base64.encodeBase64URLSafeString(baos.toByteArray()));

            gmail.users().messages().send(ConfigSettings.instance.getGoogleAccount()
                    + (ConfigSettings.instance.getGoogleAccount().endsWith("@gmail.com") ? "" : "@gmail.com"), message).execute();
            return true;
        } catch (MessagingException | IOException ex) {
            logger.error("Error sending message", ex);
        }
        return false;
    }
}
