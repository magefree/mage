package mage.server.managers;

public interface IMailClient {

    boolean sendMessage(String email, String subject, String text);

}
