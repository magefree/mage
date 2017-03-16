package mage.server;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MediaType;
import mage.server.util.ConfigSettings;
import org.apache.log4j.Logger;

public final class MailgunClient {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static boolean sendMessage(String email, String subject, String text) {
        if (email.isEmpty()) {
            logger.info("Email is not sent because the address is empty");
            return false;
        }
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", ConfigSettings.instance.getMailgunApiKey()));
        String domain = ConfigSettings.instance.getMailgunDomain();
        WebResource webResource = client.resource("https://api.mailgun.net/v3/" + domain + "/messages");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "XMage <postmaster@" + domain + '>');
        formData.add("to", email);
        formData.add("subject", subject);
        formData.add("text", text);
        ClientResponse response =  webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        boolean succeeded = response.getStatus() == 200;
        if (!succeeded) {
            logger.error("Error sending message to " + email + ". Status code: " + response.getStatus());
        }
        return succeeded;
    }
}
