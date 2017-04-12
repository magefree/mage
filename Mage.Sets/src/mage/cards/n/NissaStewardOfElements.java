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
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class NissaStewardOfElements extends CardImpl {

    public NissaStewardOfElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{X}{G}{U}");

        this.subtype.add("Nissa");

        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.LOYALTY.createInstance())));

        // +2: Scry 2.
        this.addAbility(new LoyaltyAbility(new ScryEffect(2), 2));

        // 0: Look at the top card of your library. If it's a land card or a creature card with converted mana cost less than or equal
        // to the number of loyalty counters on Nissa, Steward of Elements, you may put that card onto the battlefield.
        this.addAbility(new LoyaltyAbility(new NissaStewardOfElementsEffect(), 0));

        // -6: Untap up to two target lands you control. They become 5/5 Elemental creatures with flying and haste until end of turn. They're still lands.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapTargetEffect(), 6);
        ability.addEffect(new BecomesCreatureTargetEffect(new NissaStewardOfElementsToken(), false, true, Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(0, 2, new FilterControlledLandPermanent(), false));
        this.addAbility(ability);
    }

    public NissaStewardOfElements(final NissaStewardOfElements card) {
        super(card);
    }

    @Override
    public NissaStewardOfElements copy() {
        return new NissaStewardOfElements(this);
    }
}

class NissaStewardOfElementsEffect extends OneShotEffect {

    public NissaStewardOfElementsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "look at the top card of your library. If it's a land card or a creature card with converted mana cost less than or equal"
                + "to the number of loyalty counters on {this}, you may put that card onto the battlefield";
    }

    public NissaStewardOfElementsEffect(final NissaStewardOfElementsEffect effect) {
        super(effect);
    }

    @Override
    public NissaStewardOfElementsEffect copy() {
        return new NissaStewardOfElementsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = 1 + new CountersSourceCount(CounterType.LOYALTY).calculate(game, source, this);
        FilterPermanentCard filter = new FilterPermanentCard();
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, count));
        Card card = player.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl();
            cards.add(card);
            player.lookAtCards("Nissa, Steward of Elements", cards, game);
            if (filter.match(card, game)) {
                String message = "Put " + card.getName() + " onto the battlefield?";
                if (player.chooseUse(outcome, message, source, game)) {
                    return card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId(), false);
                }
            }
        }
        return true;
    }
}

class NissaStewardOfElementsToken extends Token {

    public NissaStewardOfElementsToken() {
        super("", "5/5 Elemental creature with flying and haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Elemental");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }
}
