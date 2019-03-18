

package mage.client.util;

import java.awt.Component;
import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Event implements Serializable {
    private final Object source;
    private final Component component;
    private final ClientEventType eventType;
    private final int number;
    private final int xPos;
    private final int yPos;

    public Event(Object source, ClientEventType eventType) {
        this(source, eventType, 0);
    }
    
    public Event(Object source, ClientEventType eventType, int number) {
        this.source = source;
        this.eventType = eventType;
        this.number = number;
        this.xPos = 0;
        this.yPos = 0;
        this.component = null;
    }

    public Event(Object source, ClientEventType eventType, int xPos, int yPos, Component component) {
        this.source = source;
        this.eventType = eventType;
        this.number =0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.component = component;
    }

    public Object getSource() {
        return source;
    }

    public ClientEventType getEventType() {
        return eventType;
    }

    public int getNumber() {
        return number;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Component getComponent() {
        return component;
    }
    
}
