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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PrimitiveEtchings extends CardImpl {

    public PrimitiveEtchings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // Reveal the first card you draw each turn. Whenever you reveal a creature card this way, draw a card.
        this.addAbility(new PrimitiveEtchingsAbility());
    }

    public PrimitiveEtchings(final PrimitiveEtchings card) {
        super(card);
    }

    @Override
    public PrimitiveEtchings copy() {
        return new PrimitiveEtchings(this);
    }
}

class PrimitiveEtchingsAbility extends TriggeredAbilityImpl {

    private int lastTriggeredTurn;

    PrimitiveEtchingsAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect(""), false);
    }

    PrimitiveEtchingsAbility(final PrimitiveEtchingsAbility ability) {
        super(ability);
    }

    @Override
    public PrimitiveEtchingsAbility copy() {
        return new PrimitiveEtchingsAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            if (game.getActivePlayerId().equals(this.getControllerId()) && this.lastTriggeredTurn != game.getTurnNum()) {
                Card card = game.getCard(event.getTargetId());
                Player controller = game.getPlayer(this.getControllerId());
                Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(this.getSourceId());
                if (card != null && controller != null && sourcePermanent != null) {
                    lastTriggeredTurn = game.getTurnNum();
                    controller.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
                    this.getEffects().clear();
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        this.addEffect(new DrawCardSourceControllerEffect(1));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Reveal the first card you draw each turn. Whenever you reveal a creature card this way, draw a card.";
    }    
}
