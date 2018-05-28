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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Quercitron
 */
public final class AncestralMemories extends CardImpl {

    public AncestralMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}{U}");

        // Look at the top seven cards of your library. Put two of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(7), false, new StaticValue(2),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false, false, Zone.HAND, false));
    }

    public AncestralMemories(final AncestralMemories card) {
        super(card);
    }

    @Override
    public AncestralMemories copy() {
        return new AncestralMemories(this);
    }
}

class AncestralMemoriesEffect extends OneShotEffect {

    public AncestralMemoriesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top seven cards of your library. Put two of them into your hand and the rest into your graveyard";
    }

    public AncestralMemoriesEffect(final AncestralMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public AncestralMemoriesEffect copy() {
        return new AncestralMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
            if (!cards.isEmpty()) {
                controller.lookAtCards(source, null, cards, game);
                TargetCard target = new TargetCard(Math.min(2, cards.size()), Zone.LIBRARY, new FilterCard("two cards to put in your hand"));
                if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Cards toHand = new CardsImpl(target.getTargets());
                    controller.moveCards(cards, Zone.HAND, source, game);
                    cards.removeAll(toHand);
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
