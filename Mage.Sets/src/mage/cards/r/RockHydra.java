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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public class RockHydra extends CardImpl {

    public RockHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}{R}");
        
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        
        // Rock Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
        // For each 1 damage that would be dealt to Rock Hydra, if it has a +1/+1 counter on it, remove a +1/+1 counter from it and prevent that 1 damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RockHydraEffect()));
        // {R}: Prevent the next 1 damage that would be dealt to Rock Hydra this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToSourceEffect(Duration.EndOfTurn, 1), new ManaCostsImpl("{R}")));
        // {R}{R}{R}: Put a +1/+1 counter on Rock Hydra. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new ManaCostsImpl("{R}{R}{R}"), new IsStepCondition(PhaseStep.UPKEEP), null));
    }

    public RockHydra(final RockHydra card) {
        super(card);
    }

    @Override
    public RockHydra copy() {
        return new RockHydra(this);
    }
    
    static class RockHydraEffect extends PreventionEffectImpl {

        public RockHydraEffect() {
            super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
            staticText = "For each 1 damage that would be dealt to {this}, if it has a +1/+1 counter on it, remove a +1/+1 counter from it and prevent that 1 damage.";
        }

        public RockHydraEffect(final RockHydraEffect effect) {
            super(effect);
        }

        @Override
        public RockHydraEffect copy() {
            return new RockHydraEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            int damage = event.getAmount();
            preventDamageAction(event, source, game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.removeCounters(CounterType.P1P1.createInstance(damage), game); //MTG ruling Rock Hydra loses counters even if the damage isn't prevented
            }
            return false;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if (super.applies(event, source, game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
            return false;
        }

    }
}