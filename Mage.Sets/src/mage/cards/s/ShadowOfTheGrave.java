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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsCycledOrDiscardedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class ShadowOfTheGrave extends CardImpl {

    public ShadowOfTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Return to your hand all cards in your graveyard that you cycled or discarded this turn.
        this.getSpellAbility().addEffect(new ShadowOfTheGraveEffect());
        this.getSpellAbility().addWatcher(new CardsCycledOrDiscardedThisTurnWatcher());

    }

    public ShadowOfTheGrave(final ShadowOfTheGrave card) {
        super(card);
    }

    @Override
    public ShadowOfTheGrave copy() {
        return new ShadowOfTheGrave(this);
    }
}

class ShadowOfTheGraveEffect extends OneShotEffect {

    public ShadowOfTheGraveEffect() {
        super(Outcome.Benefit);
        staticText = "Return to your hand all cards in your graveyard that you cycled or discarded this turn";
    }

    public ShadowOfTheGraveEffect(final ShadowOfTheGraveEffect effect) {
        super(effect);
    }

    @Override
    public ShadowOfTheGraveEffect copy() {
        return new ShadowOfTheGraveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        CardsCycledOrDiscardedThisTurnWatcher watcher = (CardsCycledOrDiscardedThisTurnWatcher) game.getState().getWatchers().get("CardsCycledOrDiscardedThisTurnWatcher");
        if (controller != null
                && watcher != null
                && watcher.getCardsCycledOrDiscardedThisTurn(controller.getId()) != null) {
            for (Card card : watcher.getCardsCycledOrDiscardedThisTurn(controller.getId()).getCards(game)) {
                if (card != null
                        && game.getState().getZone(card.getId()) == Zone.GRAVEYARD //must come from their graveyard
                        && card.getOwnerId() == controller.getId()) {  //confirm ownership
                    controller.moveCardToHandWithInfo(card, source.getId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
