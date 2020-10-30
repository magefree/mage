package mage.server.managers;

public interface MailClient {

    boolean sendMessage(String email, String subject, String text);

}
