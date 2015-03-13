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
package mage.sets.seventhedition;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class Rowen extends CardImpl {

    public Rowen(UUID ownerId) {
        super(ownerId, 266, "Rowen", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");
        this.expansionSetCode = "7ED";

        this.color.setGreen(true);

        // Reveal the first card you draw each turn. Whenever you reveal a basic land card this way, draw a card.
        this.addAbility(new RowenAbility());
    }

    public Rowen(final Rowen card) {
        super(card);
    }

    @Override
    public Rowen copy() {
        return new Rowen(this);
    }
}

class RowenAbility extends TriggeredAbilityImpl {

    private int lastTriggeredTurn;

    RowenAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect(""), false);
    }

    RowenAbility(final RowenAbility ability) {
        super(ability);
    }

    @Override
    public RowenAbility copy() {
        return new RowenAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD && event.getPlayerId().equals(this.getControllerId())) {
            if (game.getActivePlayerId().equals(this.getControllerId()) && this.lastTriggeredTurn != game.getTurnNum()) {
                Card card = game.getCard(event.getTargetId());
                Player controller = game.getPlayer(this.getControllerId());
                Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(this.getSourceId());
                if (card != null && controller != null && sourcePermanent != null) {
                    lastTriggeredTurn = game.getTurnNum();
                    controller.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
                    this.getEffects().clear();
                    if (card.getCardType().contains(CardType.LAND) && card.getSupertype().contains("Basic")) {
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
        return "Reveal the first card you draw each turn. Whenever you reveal a basic land card this way, draw a card.";
    }
    
}
