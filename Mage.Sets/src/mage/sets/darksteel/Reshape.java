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
package mage.sets.darksteel;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
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
public class Reshape extends CardImpl<Reshape> {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public Reshape(UUID ownerId) {
        super(ownerId, 31, "Reshape", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");
        this.expansionSetCode = "DST";

        this.color.setBlue(true);

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

class ReshapeSearchEffect extends OneShotEffect<ReshapeSearchEffect> {

    ReshapeSearchEffect() {
        super(Constants.Outcome.PutCardInPlay);
        staticText = "Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library";
    }

    ReshapeSearchEffect(final ReshapeSearchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterCard filter = new FilterCard("artifact card with converted mana cost X or less");
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, source.getManaCostsToPay().getX() + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    card.putOntoBattlefield(game, Constants.Zone.LIBRARY, source.getId(), source.getControllerId());
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;
    }

    @Override
    public ReshapeSearchEffect copy() {
        return new ReshapeSearchEffect(this);
    }
}
