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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Backfir3
 */
public class CitanulFlute extends CardImpl<CitanulFlute> {

    public CitanulFlute(UUID ownerId) {
        super(ownerId, 289, "Citanul Flute", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "USG";

        // {X}, {T}: Search your library for a creature card with converted mana cost X or less, reveal it,
        // and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CitanulFluteSearchEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CitanulFlute(final CitanulFlute card) {
        super(card);
    }

    @Override
    public CitanulFlute copy() {
        return new CitanulFlute(this);
    }
}

class CitanulFluteSearchEffect extends OneShotEffect<CitanulFluteSearchEffect> {

    public CitanulFluteSearchEffect() {
        super(Outcome.DrawCard);
	staticText = "Search your library for a creature card with converted mana cost X or less, reveal it, and put it into your hand. Then shuffle your library";
    }

    public CitanulFluteSearchEffect(final CitanulFluteSearchEffect effect) {
        super(effect);
    }

    @Override
    public CitanulFluteSearchEffect copy() {
        return new CitanulFluteSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
		
        FilterCard filter = new FilterCard("creature card with converted mana cost X or less");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, source.getManaCostsToPay().getX() + 1));
		
	TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                Cards cards = new CardsImpl();
                if (card != null){
                    card.moveToZone(Zone.HAND, source.getId(), game, false);
                    cards.add(card);
                }
                String name = "Reveal";
                Card sourceCard = game.getCard(source.getSourceId());
                if (sourceCard != null) {
                    name = sourceCard.getName();
                }
                player.revealCards(name, cards, game);
            }
            player.shuffleLibrary(game);
            return true;
        }
	player.shuffleLibrary(game);
        return false;
    }
}
