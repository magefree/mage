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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public class MoratoriumStone extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland card from a graveyard");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public MoratoriumStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {tap}: Exile target card from a graveyard.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // {2}{W}{B}, {tap}, Sacrifice Moratorium Stone: Exile target nonland card from a graveyard, all other cards from graveyards with the same name as that card, and all permanents with that name.
        ability = new SimpleActivatedAbility(new MoratoriumStoneEffect(), new ManaCostsImpl("{2}{W}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    public MoratoriumStone(final MoratoriumStone card) {
        super(card);
    }

    @Override
    public MoratoriumStone copy() {
        return new MoratoriumStone(this);
    }
}

class MoratoriumStoneEffect extends OneShotEffect {

    MoratoriumStoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target nonland card from a graveyard, all other cards from graveyards with the same name as that card, and all permanents with that name.";
    }

    MoratoriumStoneEffect(final MoratoriumStoneEffect effect) {
        super(effect);
    }

    @Override
    public MoratoriumStoneEffect copy() {
        return new MoratoriumStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        String cardName = card.getName();
        FilterCard filter1 = new FilterCard();
        filter1.add(new NamePredicate(cardName));
        FilterPermanent filter2 = new FilterPermanent();
        filter2.add(new NamePredicate(cardName));
        return new ExileGraveyardAllPlayersEffect(filter1).apply(game, source) && new ExileAllEffect(filter2).apply(game, source);
    }
}
