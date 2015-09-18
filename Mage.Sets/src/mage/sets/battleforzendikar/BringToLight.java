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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class BringToLight extends CardImpl {

    public BringToLight(UUID ownerId) {
        super(ownerId, 209, "Bring to Light", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}{U}");
        this.expansionSetCode = "BFZ";

        // <i>Converge</i>-Search your library for a creature, instant, or sorcery card with converted mana
        // cost less than or equal to the number of colors of mana spent to cast Bring to Light, exile that card,
        // then shuffle your library. You may cast that card without paying its mana cost.
        this.getSpellAbility().addEffect(new BringToLightEffect());
    }

    public BringToLight(final BringToLight card) {
        super(card);
    }

    @Override
    public BringToLight copy() {
        return new BringToLight(this);
    }
}

class BringToLightEffect extends OneShotEffect {

    public BringToLightEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "<i>Converge</i> &mdash; Search your library for a creature, instant, or sorcery card with converted mana "
                + "cost less than or equal to the number of colors of mana spent to cast Bring to Light, exile that card, "
                + "then shuffle your library. You may cast that card without paying its mana cost";
    }

    public BringToLightEffect(final BringToLightEffect effect) {
        super(effect);
    }

    @Override
    public BringToLightEffect copy() {
        return new BringToLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numberColors = ColorsOfManaSpentToCastCount.getInstance().calculate(game, source, this);
            FilterCard filter = new FilterCard();
            filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, numberColors + 1));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            controller.searchLibrary(target, game);
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, null, Zone.EXILED, source, game);
            }
            controller.shuffleLibrary(game);
            if (card != null) {
                if (controller.chooseUse(outcome, "Cast " + card.getName() + " without paying its mana cost?", source, game)) {
                    controller.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
