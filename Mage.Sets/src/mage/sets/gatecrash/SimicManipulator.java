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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
* Gatecrash FAQ (01.2013)
*
* The power of the target creature is checked both as you target it and as the ability
* resolves. If the power of the target creature when the ability resolves is greater
* than the number of +1/+1 counters removed from Simic Manipulator, the ability will
* be countered and none of its effects will happen. You won't gain control of any
* creature, but the counters removed as a cost remain removed.
*
* @author LevelX2
*/
public class SimicManipulator extends CardImpl<SimicManipulator> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power less than or equal to the number of +1/+1 counters removed this way");

    public SimicManipulator(UUID ownerId) {
        super(ownerId, 50, "Simic Manipulator", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Mutant");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Evolve
        this.addAbility(new EvolveAbility());

        // {tap}, Remove one or more +1/+1 counters from Simic Manipulator: Gain control of target creature with power less than or equal to the number of +1/+1 counters removed this way.
        // TODO: Improve targeting, that only valid targets (power <= removed counters) can be choosen
        //       Disadvantage now is, that a creature can be targeted that couldn't be targeted by rules.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SimicManipulatorGainControlTargetEffect(Duration.Custom), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1.createInstance(),1));
        this.addAbility(ability);
    }

    public SimicManipulator(final SimicManipulator card) {
        super(card);
    }

    @Override
    public SimicManipulator copy() {
        return new SimicManipulator(this);
    }
}

class SimicManipulatorGainControlTargetEffect extends ContinuousEffectImpl<SimicManipulatorGainControlTargetEffect> {

    private boolean valid;

    public SimicManipulatorGainControlTargetEffect(Duration duration) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public SimicManipulatorGainControlTargetEffect(final SimicManipulatorGainControlTargetEffect effect) {
        super(effect);
        this.valid = effect.valid;
    }

    @Override
    public void init(Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            int maxPower = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    maxPower = ((RemoveVariableCountersSourceCost) cost).getAmount();
                    break;
                }
            }
            if (permanent.getPower().getValue() <= maxPower) {
                valid = true;
            }
        }
    }

    @Override
    public SimicManipulatorGainControlTargetEffect copy() {
        return new SimicManipulatorGainControlTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && valid) {
            return permanent.changeControllerId(source.getControllerId(), game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of target " + mode.getTargets().get(0).getTargetName() + " " + duration.toString();
    }
}
