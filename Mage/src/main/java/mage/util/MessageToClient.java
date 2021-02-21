package mage.util;

/**
 *
 * @author LevelX2
 */
public class MessageToClient {

    private String message;
    private String secondMessage;
    private String hintText;

    public MessageToClient(String message) {
        this(message, null);
    }

    public MessageToClient(String message, String secondMessage) {
        this(message, secondMessage, null);
    }

    public MessageToClient(String message, String secondMessage, String hintText) {
        this.message = message;
        this.secondMessage = secondMessage;
        this.hintText = hintText;
    }

    public String getMessage() {
        return message;
    }

    public String getSecondMessage() {
        return secondMessage;
    }

    public String getHintText() {
        return hintText;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSecondMessage(String secondMessage) {
        this.secondMessage = secondMessage;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

}
