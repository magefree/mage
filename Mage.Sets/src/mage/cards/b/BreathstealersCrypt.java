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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class BreathstealersCrypt extends CardImpl {

    public BreathstealersCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");

        // If a player would draw a card, instead he or she draws a card and reveals it. If it's a creature card, that player discards it unless he or she pays 3 life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BreathstealersCryptEffect()));

    }

    public BreathstealersCrypt(final BreathstealersCrypt card) {
        super(card);
    }

    @Override
    public BreathstealersCrypt copy() {
        return new BreathstealersCrypt(this);
    }
}

class BreathstealersCryptEffect extends ReplacementEffectImpl {

    public BreathstealersCryptEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseLife);
        staticText = "If a player would draw a card, instead he or she draws a card and reveals it. If it's a creature card, that player discards it unless he or she pays 3 life";
    }

    public BreathstealersCryptEffect(final BreathstealersCryptEffect effect) {
        super(effect);
    }

    @Override
    public BreathstealersCryptEffect copy() {
        return new BreathstealersCryptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        PayLifeCost cost = new PayLifeCost(3);
        if (player != null) {
            Card cardDrawn = player.getLibrary().removeFromTop(game);
            if (cardDrawn != null) {
                player.moveCardToHandWithInfo(cardDrawn, source.getSourceId(), game);
                Cards cards = new CardsImpl();
                cards.add(cardDrawn);
                player.revealCards("The card drawn from " + player.getName() + "'s library", cards, game);
                if (cardDrawn.isCreature()) {
                    game.informPlayers("The card drawn by " + player.getName() + " is a creature card.  He/she must pay 3 life or that card gets discarded.");
                    if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                            && player.chooseUse(outcome, "Do you wish to pay 3 life to keep the drawn creature card?  If not, you discard it.", source, game)) {
                        return cost.pay(source, game, source.getSourceId(), player.getId(), true, cost);
                    } else {
                        game.informPlayers("The cost of 3 life was not paid by " + player.getName() + ", so the creature card will be discarded.");
                        return player.discard(cardDrawn, source, game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
