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
package mage.cards.b;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class BrainInAJar extends CardImpl {

    public BrainInAJar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {T}: Put a charge counter on Brain in a Jar, then you may cast an instant or sorcery card with converted mana costs equal to the number of charge counters on Brain in a Jar from your hand without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new GenericManaCost(1));
        ability.addEffect(new BrainInAJarCastEffect());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}, Remove X charge counters from Brain in a Jar: Scry X.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BrainInAJarScryEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability);
    }

    public BrainInAJar(final BrainInAJar card) {
        super(card);
    }

    @Override
    public BrainInAJar copy() {
        return new BrainInAJar(this);
    }
}

class BrainInAJarCastEffect extends OneShotEffect {

    public BrainInAJarCastEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then you may cast an instant or sorcery card with converted mana costs equal to the number of charge counters on {this} from your hand without paying its mana cost";
    }

    public BrainInAJarCastEffect(final BrainInAJarCastEffect effect) {
        super(effect);
    }

    @Override
    public BrainInAJarCastEffect copy() {
        return new BrainInAJarCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (controller != null && sourceObject != null) {
            int counters = sourceObject.getCounters(game).getCount(CounterType.CHARGE);
            FilterCard filter = new FilterInstantOrSorceryCard();
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, counters));
            int cardsToCast = controller.getHand().count(filter, source.getControllerId(), source.getSourceId(), game);
            if (cardsToCast > 0 && controller.chooseUse(outcome, "Cast an instant or sorcery card with converted mana costs of " + counters + " from your hand without paying its mana cost?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(filter);
                controller.chooseTarget(outcome, target, source, game);
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}

class BrainInAJarScryEffect extends OneShotEffect {

    public BrainInAJarScryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Scry X";
    }

    public BrainInAJarScryEffect(final BrainInAJarScryEffect effect) {
        super(effect);
    }

    @Override
    public BrainInAJarScryEffect copy() {
        return new BrainInAJarScryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int x = new RemovedCountersForCostValue().calculate(game, source, this);
            if (x > 0) {
                return controller.scry(x, source, game);
            }
            return true;
        }
        return false;
    }
}
