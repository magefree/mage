package mage.client.util;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class Event implements Serializable {

    private final Object source; // can be null for non card events like popup from panel
    private final Component component; // component that raise the event (example: MageCard)
    private final ClientEventType eventType;
    private final int number;
    private final int xPos;
    private final int yPos;

    private final MouseEvent mouseEvent;
    // force alt mouse state for fake mouse event (example: card's selector can add card to deck by panel's button instead double click)
    private final boolean mouseAltDown;

    public Event(Object source, ClientEventType eventType) {
        this(source, eventType, 0);
    }

    public Event(Object source, ClientEventType eventType, int number) {
        this(source, eventType, number, 0, 0, null);
    }

    public Event(Object source, ClientEventType eventType, int number, int xPos, int yPos, Component component) {
        this(source, eventType, number, xPos, yPos, component, null, false);
    }

    public Event(Object source, ClientEventType eventType, int number, int xPos, int yPos, Component component,
                 MouseEvent mouseEvent, boolean mouseAltDown) {
        this.source = source;
        this.eventType = eventType;
        this.number = number;
        this.xPos = xPos;
        this.yPos = yPos;
        this.component = component;
        this.mouseEvent = mouseEvent;
        this.mouseAltDown = mouseAltDown;
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

    public MouseEvent getMouseEvent() {
        if (this.mouseEvent == null) {
            // GUI must catch a correct events, so look at stack trace and find a problem code with event generation
            throw new IllegalArgumentException("Error, found empty mouse event.");
        }

        return mouseEvent;
    }

    public boolean isMouseAltDown() {
        if (mouseEvent != null) {
            return mouseEvent.isAltDown();
        } else {
            return mouseAltDown;
        }
    }
}