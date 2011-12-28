/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public class Hinder extends CardImpl<Hinder> {

    public Hinder(UUID ownerId) {
        super(ownerId, 65, "Hinder", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "CHK";

        this.color.setBlue(true);

        // Counter target spell. If that spell is countered this way, put that card on the top or bottom of its owner's library instead of into that player's graveyard.
        this.getSpellAbility().addEffect(new HinderEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Hinder(final Hinder card) {
        super(card);
    }

    @Override
    public Hinder copy() {
        return new Hinder(this);
    }
}

class HinderEffect extends OneShotEffect<HinderEffect> {

    public HinderEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If that spell is countered this way, put that card on the top or bottom of its owner's library instead of into that player's graveyard";
    }

    public HinderEffect(final HinderEffect effect) {
        super(effect);
    }

    @Override
    public HinderEffect copy() {
        return new HinderEffect(this);
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
            MageObject targetObject = game.getObject(stackObject.getSourceId());
            Zone targetZone = Zone.LIBRARY;
            if (targetObject instanceof Card) {
                Card card = (Card) targetObject;
                Player player = game.getPlayer(source.getControllerId());
                boolean top = player.chooseUse(Outcome.Neutral, "Put " + card.getName() + " on top of the library? Otherwise it will be put on the bottom.", game);
                card.moveToZone(targetZone, sourceId, game, top);
                game.informPlayers(player.getName() + " has put " + card.getName() + " on " + (top ? "top" : "the bottom") + " of the library.");
            } else {
                game.informPlayers("Server: Couldn't move card to zone = " + targetZone + " as it has other than Card type.");
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, objectId, sourceId, stackObject.getControllerId()));
            return true;
        }
        return false;
    }
}
