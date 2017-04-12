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
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class DistendedMindbender extends CardImpl {

    public DistendedMindbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add("Eldrazi");
        this.subtype.add("Insect");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Emerge {5}{B}{B}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{5}{B}{B}")));

        // When controller cast Distended Mindbender, target opponent reveals his or her hand. You choose from it a nonland card with converted mana cost 3 or less
        // and a card with converted mana cost 4 or greater. That player discards those cards.
        Ability ability = new CastSourceTriggeredAbility(new DistendedMindbenderEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DistendedMindbender(final DistendedMindbender card) {
        super(card);
    }

    @Override
    public DistendedMindbender copy() {
        return new DistendedMindbender(this);
    }
}

class DistendedMindbenderEffect extends OneShotEffect {

    private static final FilterCard filterFourOrGreater = new FilterCard("a card from it with converted mana cost 4 or greater");
    private static final FilterCard filterThreeOrLess = new FilterCard("a nonland card from it with converted mana cost 3 or less");

    static {
        filterFourOrGreater.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 3));
        filterThreeOrLess.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
        filterThreeOrLess.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public DistendedMindbenderEffect() {
        super(Outcome.Discard);
        this.staticText = "target opponent reveals his or her hand. You choose from it a nonland card with converted mana cost 3 or less and a card with "
                + "converted mana cost 4 or greater. That player discards those cards.";
    }

    public DistendedMindbenderEffect(final DistendedMindbenderEffect effect) {
        super(effect);
    }

    @Override
    public DistendedMindbenderEffect copy() {
        return new DistendedMindbenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            opponent.revealCards("Distended Mindbender", opponent.getHand(), game);
            TargetCard targetThreeOrLess = new TargetCard(1, Zone.HAND, filterThreeOrLess);
            TargetCard targetFourOrGreater = new TargetCard(1, Zone.HAND, filterFourOrGreater);
            if (controller.choose(Outcome.Benefit, opponent.getHand(), targetThreeOrLess, game)) {
                List<UUID> targets = targetThreeOrLess.getTargets();
                for (UUID targetId : targets) {
                    Card card = opponent.getHand().get(targetId, game);
                    if (card != null) {
                        opponent.discard(card, source, game);
                    }
                }
            }
            if (controller.choose(Outcome.Benefit, opponent.getHand(), targetFourOrGreater, game)) {
                List<UUID> targets = targetFourOrGreater.getTargets();
                for (UUID targetId : targets) {
                    Card card = opponent.getHand().get(targetId, game);
                    if (card != null) {
                        opponent.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
