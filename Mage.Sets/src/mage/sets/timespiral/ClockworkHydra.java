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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ClockworkHydra extends CardImpl<ClockworkHydra> {

    public ClockworkHydra(UUID ownerId) {
        super(ownerId, 253, "Clockwork Hydra", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Hydra");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Clockwork Hydra enters the battlefield with four +1/+1 counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(4));
        effect.setText("with four +1/+1 counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        // Whenever Clockwork Hydra attacks or blocks, remove a +1/+1 counter from it. If you do, Clockwork Hydra deals 1 damage to target creature or player.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new ClockworkHydraEffect(), false));

        // {tap}: Put a +1/+1 counter on Clockwork Hydra.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), new TapSourceCost()));
    }

    public ClockworkHydra(final ClockworkHydra card) {
        super(card);
    }

    @Override
    public ClockworkHydra copy() {
        return new ClockworkHydra(this);
    }

}

class ClockworkHydraEffect extends OneShotEffect<ClockworkHydraEffect> {

    public ClockworkHydraEffect() {
        super(Outcome.Damage);
        this.staticText = "remove a +1/+1 counter from it. If you do, {this} deals 1 damage to target creature or player";
    }

    public ClockworkHydraEffect(final ClockworkHydraEffect effect) {
        super(effect);
    }

    @Override
    public ClockworkHydraEffect copy() {
        return new ClockworkHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null && permanent.getCounters().getCount(CounterType.P1P1) > 0) {
            permanent.removeCounters(CounterType.P1P1.createInstance(), game);
            Target target = new TargetCreatureOrPlayer(true);
            if (controller.chooseTarget(outcome, target, source, game)) {
                Effect effect = new DamageTargetEffect(1);
                effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                return effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
