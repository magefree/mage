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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Quercitron
 */
public class Recall extends CardImpl {

    public Recall(UUID ownerId) {
        super(ownerId, 115, "Recall", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{X}{U}");
        this.expansionSetCode = "5ED";

        // Discard X cards, then return a card from your graveyard to your hand for each card discarded this way.
        this.getSpellAbility().addEffect(new RecallEffect());
        // Exile Recall.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public Recall(final Recall card) {
        super(card);
    }

    @Override
    public Recall copy() {
        return new Recall(this);
    }
}

class RecallEffect extends OneShotEffect {

    public RecallEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Discard X cards, then return a card from your graveyard to your hand for each card discarded this way";
    }

    public RecallEffect(final RecallEffect effect) {
        super(effect);
    }

    @Override
    public RecallEffect copy() {
        return new RecallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Discard X cards
            Cards cardsDiscarded = controller.discard(source.getManaCostsToPay().getX(), false, source, game);
            if (!cardsDiscarded.isEmpty()) {
                // then return a card from your graveyard to your hand for each card discarded this way
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(cardsDiscarded.size(), new FilterCard());
                target.setNotTarget(true);
                target.choose(Outcome.ReturnToHand, controller.getId(), source.getSourceId(), game);
                controller.moveCards(new CardsImpl(target.getTargets()), null, Zone.HAND, source, game);
            }

            return true;
        }
        return false;
    }

}
