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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class LightningStorm extends CardImpl {

    public LightningStorm(UUID ownerId) {
        super(ownerId, 89, "Lightning Storm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");
        this.expansionSetCode = "CSP";

        this.color.setRed(true);

        // Lightning Storm deals X damage to target creature or player, where X is 3 plus the number of charge counters on it.
        Effect effect = new DamageTargetEffect(new LightningStormCountCondition(CounterType.CHARGE));
        effect.setText("{this} deals X damage to target creature or player, where X is 3 plus the number of charge counters on it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        // Discard a land card: Put two charge counters on Lightning Storm. You may choose a new target for it. Any player may activate this ability but only if Lightning Storm is on the stack.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.STACK,
                new LightningStormAddCounterEffect() ,
                new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability but only if {this} is on the stack"));
        this.addAbility(ability);
    }

    public LightningStorm(final LightningStorm card) {
        super(card);
    }

    @Override
    public LightningStorm copy() {
        return new LightningStorm(this);
    }
}

class LightningStormCountCondition implements DynamicValue {
    private final CounterType counter;

    public LightningStormCountCondition(CounterType counter) {
        this.counter = counter;
    }

    public LightningStormCountCondition(final LightningStormCountCondition countersCount) {
        this.counter = countersCount.counter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = game.getStack().getSpell(sourceAbility.getSourceId());
        if (spell != null) {
            return spell.getCounters(game).getCount(counter) + 3;
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new LightningStormCountCondition(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "3 plus the number of charge counters on it";
    }
}

class LightningStormAddCounterEffect extends OneShotEffect {

    public LightningStormAddCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put two charge counters on {this}. You may choose a new target for it.";
    }

    public LightningStormAddCounterEffect(final LightningStormAddCounterEffect effect) {
        super(effect);
    }

    @Override
    public LightningStormAddCounterEffect copy() {
        return new LightningStormAddCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell != null) {
            spell.addCounters(CounterType.CHARGE.createInstance(2), game);
            return spell.chooseNewTargets(game, source.getControllerId(), false, false);
        }
        return false;
    }
}
