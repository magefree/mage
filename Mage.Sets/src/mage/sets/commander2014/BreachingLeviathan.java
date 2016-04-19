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
package mage.sets.commander2014;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public class BreachingLeviathan extends CardImpl {

    public BreachingLeviathan(UUID ownerId) {
        super(ownerId, 12, "Breaching Leviathan", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{7}{U}{U}");
        this.expansionSetCode = "C14";
        this.subtype.add("Leviathan");

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // When Breaching Leviathan enters the battlefield, if you cast it from your hand, tap all nonblue creatures. Those creatures don't untap during their controllers' next untap steps.
        this.addAbility(new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BreachingLeviathanEffect(), false),
                new CastFromHandSourceCondition(),
                "When {this} enters the battlefield, if you cast it from your hand, tap all nonblue creatures. Those creatures don't untap during their controllers' next untap steps."),
                new CastFromHandWatcher());
    }

    public BreachingLeviathan(final BreachingLeviathan card) {
        super(card);
    }

    @Override
    public BreachingLeviathan copy() {
        return new BreachingLeviathan(this);
    }
}

class BreachingLeviathanEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblue creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLUE)));
    }

    public BreachingLeviathanEffect() {
        super(Outcome.Tap);
        this.staticText = "tap all nonblue creatures. Those creatures don't untap during their controllers' next untap steps";
    }

    public BreachingLeviathanEffect(final BreachingLeviathanEffect effect) {
        super(effect);
    }

    @Override
    public BreachingLeviathanEffect copy() {
        return new BreachingLeviathanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            creature.tap(game);
            doNotUntapNextUntapStep.add(creature);
        }
        if (!doNotUntapNextUntapStep.isEmpty()) {
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
            effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
