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
package mage.client.cards;

import java.awt.Component;
import java.io.Serializable;
import mage.client.util.Event;
import mage.client.util.EventDispatcher;
import mage.client.util.EventSource;
import mage.client.util.Listener;
import mage.view.SimpleCardView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardEventSource implements EventSource<Event>, Serializable {

    protected final EventDispatcher<Event> dispatcher = new EventDispatcher<Event>() {
    };

    @Override
    public void addListener(Listener<Event> listener) {
        dispatcher.addListener(listener);
    }

    public void setNumber(SimpleCardView card, String message, int number) {
        dispatcher.fireEvent(new Event(card, message, number));
    }

    public void doubleClick(SimpleCardView card, String message) {
        dispatcher.fireEvent(new Event(card, message));
    }

    public void altDoubleClick(SimpleCardView card, String message) {
        dispatcher.fireEvent(new Event(card, message));
    }

    public void removeFromMainEvent(String message) {
        dispatcher.fireEvent(new Event(null, message));
    }

    public void removeFromSideboardEvent(String message) {
        dispatcher.fireEvent(new Event(null, message));
    }

    public void showPopupMenuEvent(SimpleCardView card, Component component, int x, int y, String message) {
        dispatcher.fireEvent(new Event(card, message, x, y, component));
    }

    public void actionConsumedEvent(String message) {
        dispatcher.fireEvent(new Event(null, message));
    }

    @Override
    public void clearListeners() {
        dispatcher.clearListeners();
    }

}
