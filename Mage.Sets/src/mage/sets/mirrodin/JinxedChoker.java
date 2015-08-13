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
package mage.sets.mirrodin;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.lang.Override;
import java.util.UUID;

/**
 *
 * @author andyfries
 */

public class JinxedChoker extends CardImpl {

    public JinxedChoker(UUID ownerId) {
        super(ownerId, 189, "Jinxed Choker", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "MRD";

        // At the beginning of your end step, target opponent gains control of Jinxed Choker and puts a charge counter on it.
        Ability endStepAbility = new BeginningOfYourEndStepTriggeredAbility(new JinxedChokerChangeControllerEffect(), false);
        endStepAbility.addTarget(new TargetOpponent());

        AddCountersSourceEffect addCountersSourceEffect = new AddCountersSourceEffect(CounterType.CHARGE.createInstance());
        addCountersSourceEffect.setText("");
        endStepAbility.addEffect(addCountersSourceEffect);
        this.addAbility(endStepAbility);

        // At the beginning of your upkeep, Jinxed Choker deals damage to you equal to the number of charge counters on it.
        Ability upkeepAbility = new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new DamageControllerEffect(new JinxedChokerDynamicValue()), false);
        this.addAbility(upkeepAbility);

        // {3}: Put a charge counter on Jinxed Choker or remove one from it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JinxedChokerCounterEffect(), new ManaCostsImpl("{3}"));
        this.addAbility(ability);
    }

    public JinxedChoker(final JinxedChoker card) {
        super(card);
    }

    @Override
    public JinxedChoker copy() {
        return new JinxedChoker(this);
    }
}

class JinxedChokerChangeControllerEffect extends ContinuousEffectImpl {

    public JinxedChokerChangeControllerEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this} and puts a charge counter on it";
    }

    public JinxedChokerChangeControllerEffect(final JinxedChokerChangeControllerEffect effect) {
        super(effect);
    }

    @Override
    public JinxedChokerChangeControllerEffect copy() {
        return new JinxedChokerChangeControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game);
        } else {
            discard();
        }
        return false;
    }

}

class JinxedChokerDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());

        int count = 0;
        if (permanent != null){
            count = permanent.getCounters().getCount(CounterType.CHARGE);
        }
        return count;
    }

    @Override
    public JinxedChokerDynamicValue copy() {
        return new JinxedChokerDynamicValue();
    }

    @Override
    public String getMessage() {
        return "charge counter on it";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class JinxedChokerCounterEffect extends OneShotEffect {

    public JinxedChokerCounterEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put a charge counter on {this} or remove one from it";
    }

    public JinxedChokerCounterEffect(final JinxedChokerCounterEffect effect) {
        super(effect);
    }

    @Override
    public JinxedChokerCounterEffect copy() {
        return new JinxedChokerCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (!sourcePermanent.getCounters().containsKey(CounterType.CHARGE) || controller.chooseUse(outcome, "Put a charge counter on? (No removes one)", source, game)) {
                return new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true).apply(game, source);
            } else {
                return new RemoveCounterSourceEffect(CounterType.CHARGE.createInstance()).apply(game, source);
            }
        }
        return false;
    }
}