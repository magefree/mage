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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class AetherbornMarauder extends CardImpl {

    public AetherbornMarauder(UUID ownerId) {
        super(ownerId, 71, "Aetherborn Marauder", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "KLD";
        this.subtype.add("Aetherborn");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // When Aetherborn Marauder enters the battlefield, move any number of +1/+1 counters from other permanents you control onto Aetherborn Marauder.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AetherbornMarauderEffect(), false));

    }

    public AetherbornMarauder(final AetherbornMarauder card) {
        super(card);
    }

    @Override
    public AetherbornMarauder copy() {
        return new AetherbornMarauder(this);
    }
}

class AetherbornMarauderEffect extends OneShotEffect {

    public AetherbornMarauderEffect() {
        super(Outcome.Benefit);
        this.staticText = "move any number of +1/+1 counters from other permanents you control onto {this}";
    }

    public AetherbornMarauderEffect(final AetherbornMarauderEffect effect) {
        super(effect);
    }

    @Override
    public AetherbornMarauderEffect copy() {
        return new AetherbornMarauderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (controller != null && sourceObject != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent("permanent you control to remove +1/+1 counters from");
            filter.add(new AnotherPredicate());
            filter.add(new CounterPredicate(CounterType.P1P1));
            boolean firstRun = true;
            while (game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
                if (controller.chooseUse(outcome, "Move " + (firstRun ? "any" : "more") + " +1/+1 counters from other permanents you control to " + sourceObject.getLogName() + "?", source, game)) {
                    firstRun = false;
                    TargetControlledPermanent target = new TargetControlledPermanent(filter);
                    target.setNotTarget(true);
                    if (target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), game)) {
                        Permanent fromPermanent = game.getPermanent(target.getFirstTarget());
                        if (fromPermanent != null) {
                            int numberOfCounters = fromPermanent.getCounters(game).getCount(CounterType.P1P1);
                            int numberToMove = 1;
                            if (numberOfCounters > 1) {
                                numberToMove = controller.getAmount(0, numberOfCounters, "How many +1/+1 counters do you want to move?", game);
                            }
                            if (numberToMove > 0) {
                                fromPermanent.removeCounters(CounterType.P1P1.createInstance(numberToMove), game);
                                sourceObject.addCounters(CounterType.P1P1.createInstance(numberToMove), game);
                            }
                        }
                    }
                } else {
                    break;
                }

            }
            return true;
        }
        return false;
    }
}
