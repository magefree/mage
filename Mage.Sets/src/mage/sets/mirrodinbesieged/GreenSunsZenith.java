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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author Loki
 */
public class GreenSunsZenith extends CardImpl {

    public GreenSunsZenith(UUID ownerId) {
        super(ownerId, 81, "Green Sun's Zenith", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{G}");
        this.expansionSetCode = "MBS";

        // Search your library for a green creature card with converted mana cost X or less,
        // put it onto the battlefield, then shuffle your library.
        // Shuffle Green Sun's Zenith into its owner's library.
        this.getSpellAbility().addEffect(new GreenSunsZenithSearchEffect());
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    public GreenSunsZenith(final GreenSunsZenith card) {
        super(card);
    }

    @Override
    public GreenSunsZenith copy() {
        return new GreenSunsZenith(this);
    }

}

class GreenSunsZenithSearchEffect extends OneShotEffect {

    GreenSunsZenithSearchEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Search your library for a green creature card with converted mana cost X or less, put it onto the battlefield, then shuffle your library";
    }

    GreenSunsZenithSearchEffect(final GreenSunsZenithSearchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        int xValue = source.getManaCostsToPay().getX() + 1;
        FilterCard filter = new FilterCard("green creature card with converted mana cost " + xValue + " or less");
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, xValue));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            controller.shuffleLibrary(game);
            return true;
        }
        controller.shuffleLibrary(game);
        return false;
    }

    @Override
    public GreenSunsZenithSearchEffect copy() {
        return new GreenSunsZenithSearchEffect(this);
    }

}
