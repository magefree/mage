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
package mage.abilities.effects.common;

import mage.abilities.Mode;
import mage.game.stack.Spell;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

import java.util.UUID;

import static mage.Constants.Outcome;
import static mage.Constants.Zone;

/**
 * @author nantuko, North
 */
public class CounterTargetWithReplacementEffect extends OneShotEffect<CounterTargetWithReplacementEffect> {

    private Zone targetZone;
    private boolean flag;

    public CounterTargetWithReplacementEffect(Zone targetZone) {
        this(targetZone, false);
    }

    /**
     *
     * @param targetZone
     * @param flag use to specify when moving card to library <ul><li>true = put on top</li><li>false = put on bottom</li></ul>
     */
    public CounterTargetWithReplacementEffect(Zone targetZone, boolean flag) {
        super(Outcome.Detriment);
        this.targetZone = targetZone;
        this.flag = flag;
    }

    public CounterTargetWithReplacementEffect(final CounterTargetWithReplacementEffect effect) {
        super(effect);
        this.targetZone = effect.targetZone;
        this.flag = effect.flag;
    }

    @Override
    public CounterTargetWithReplacementEffect copy() {
        return new CounterTargetWithReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID objectId = source.getFirstTarget();
        UUID sourceId = source.getSourceId();

        StackObject stackObject = game.getStack().getStackObject(objectId);
        if (stackObject != null && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, objectId, sourceId, stackObject.getControllerId()))) {
            if (stackObject instanceof Spell) {
                game.rememberLKI(objectId, Zone.STACK, (Spell) stackObject);
            }
            game.getStack().remove(stackObject);
            MageObject card = game.getObject(stackObject.getSourceId());
            if (card instanceof Card) {
                ((Card) card).moveToZone(targetZone, sourceId, game, flag);
            } else {
                game.informPlayers("Server: Couldn't move card to zone = " + targetZone + " as it has other than Card type.");
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, objectId, sourceId, stackObject.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("Counter target ");
        sb.append(mode.getTargets().get(0).getTargetName());
        sb.append(". If that spell is countered this way, ");
        if (targetZone.equals(Zone.EXILED)) {
            sb.append("exile it instead of putting it into its owner's graveyard");
        }
        if (targetZone.equals(Zone.HAND)) {
            sb.append("put it into its owner's hand instead of into that player's graveyard");
        }
        if (targetZone.equals(Zone.LIBRARY)) {
            sb.append("put it on ");
            sb.append(flag ? "top" : "the bottom");
            sb.append(" of its owner's library instead of into that player's graveyard");
        }

        return sb.toString();
    }
}
