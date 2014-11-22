/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

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
    private final String eventName;
    private final int number;
    private final int xPos;
    private final int yPos;

    public Event(Object source, String eventName) {
        this(source, eventName, 0);
    }
    
    public Event(Object source, String eventName, int number) {
        this.source = source;
        this.eventName = eventName;
        this.number = number;
        this.xPos = 0;
        this.yPos = 0;
        this.component = null;
    }

    public Event(Object source, String eventName, int xPos, int yPos, Component component) {
        this.source = source;
        this.eventName = eventName;
        this.number =0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.component = component;
    }

    public Object getSource() {
        return source;
    }

    public String getEventName() {
        return eventName;
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
