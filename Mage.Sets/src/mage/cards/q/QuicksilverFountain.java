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
package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class QuicksilverFountain extends CardImpl {

    public QuicksilverFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each player's upkeep, that player puts a flood counter on target non-Island land he or she controls of his or her choice. That land is an Island for as long as it has a flood counter on it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new QuicksilverFountainEffect(), TargetController.ANY, false, true));

        // At the beginning of each end step, if all lands on the battlefield are Islands, remove all flood counters from them.
        Condition condition = new AllLandsAreSubtypeCondition(SubType.ISLAND);
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new QuicksilverFountainEffect2(), TargetController.ANY, condition, false));

    }

    public QuicksilverFountain(final QuicksilverFountain card) {
        super(card);
    }

    @Override
    public QuicksilverFountain copy() {
        return new QuicksilverFountain(this);
    }
}

class QuicksilverFountainEffect extends OneShotEffect {

    static final private FilterControlledLandPermanent filterNonIslandLand = new FilterControlledLandPermanent("non-Island land");

    static {
        filterNonIslandLand.add(Predicates.not(new SubtypePredicate(SubType.ISLAND)));
    }

    public QuicksilverFountainEffect() {
        super(Outcome.Neutral);
        staticText = "that player puts a flood counter on target non-Island land he or she controls of his or her choice. That land is an Island for as long as it has a flood counter on it";
    }

    public QuicksilverFountainEffect(final QuicksilverFountainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Target targetNonIslandLand = new TargetPermanent(filterNonIslandLand);
        if (player != null) {
            if (player.choose(Outcome.Neutral, targetNonIslandLand, source.getId(), game)) {
                Permanent landChosen = game.getPermanent(targetNonIslandLand.getFirstTarget());
                landChosen.addCounters(CounterType.FLOOD.createInstance(), source, game);
                ContinuousEffect becomesBasicLandTargetEffect = new BecomesBasicLandTargetEffect(Duration.OneUse, SubType.ISLAND);
                becomesBasicLandTargetEffect.addDependencyType(DependencyType.BecomeIsland);
                ConditionalContinuousEffect effect = new ConditionalContinuousEffect(becomesBasicLandTargetEffect, new LandHasFloodCounterCondition(this), staticText);
                this.setTargetPointer(new FixedTarget(landChosen, game));
                effect.setTargetPointer(new FixedTarget(landChosen, game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public QuicksilverFountainEffect copy() {
        return new QuicksilverFountainEffect(this);
    }
}

class QuicksilverFountainEffect2 extends OneShotEffect {

    public QuicksilverFountainEffect2() {
        super(Outcome.Neutral);
        staticText = "remove all flood counters from them";
    }

    public QuicksilverFountainEffect2(final QuicksilverFountainEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent land : game.getBattlefield().getAllActivePermanents(CardType.LAND)) {
            land.removeCounters(CounterType.FLOOD.createInstance(land.getCounters(game).getCount(CounterType.FLOOD)), game);
        }
        return true;
    }

    @Override
    public QuicksilverFountainEffect2 copy() {
        return new QuicksilverFountainEffect2(this);
    }
}

class AllLandsAreSubtypeCondition implements Condition {

    private final SubType subtype;

    public AllLandsAreSubtypeCondition(SubType subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterLandPermanent filterLand = new FilterLandPermanent();
        filterLand.add(new SubtypePredicate(subtype));
        int landCount = game.getBattlefield().getAllActivePermanents(CardType.LAND).size();
        return game.getBattlefield().getAllActivePermanents(filterLand, game).size() == landCount;
    }

    @Override
    public String toString() {
        return "if all lands on the battlefield are " + subtype + "s";
    }
}

class LandHasFloodCounterCondition implements Condition {

    private final Effect effect;

    public LandHasFloodCounterCondition(Effect effect) {
        this.effect = effect;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(effect.getTargetPointer().getFirst(game, source));
        return permanent != null
                && permanent.getCounters(game).getCount(CounterType.FLOOD) > 0;
    }
}
