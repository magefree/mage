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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jonubuu
 */
public class Reshape extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public Reshape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}");

        // As an additional cost to cast Reshape, sacrifice an artifact.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
        // Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new ReshapeSearchEffect());
    }

    public Reshape(final Reshape card) {
        super(card);
    }

    @Override
    public Reshape copy() {
        return new Reshape(this);
    }
}

class ReshapeSearchEffect extends OneShotEffect {

    ReshapeSearchEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library";
    }

    ReshapeSearchEffect(final ReshapeSearchEffect effect) {
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
        FilterCard filter = new FilterCard("artifact card with converted mana cost " + xValue + " or less");
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, xValue));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }

    @Override
    public ReshapeSearchEffect copy() {
        return new ReshapeSearchEffect(this);
    }
}
