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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class WitherscaleWurm extends CardImpl {

    public WitherscaleWurm(UUID ownerId) {
        super(ownerId, 134, "Witherscale Wurm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Wurm");

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Whenever Witherscale Wurm blocks or becomes blocked by a creature, that creature gains wither until end of turn.
        Effect effect = new GainAbilityTargetEffect(WitherAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("that creature gains wither until end of turn");
        this.addAbility(new BlocksOrBecomesBlockedByCreatureTriggeredAbility(effect, false));

        // Whenever Witherscale Wurm deals damage to an opponent, remove all -1/-1 counters from it.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new WitherscaleWurmEffect(), false));

    }

    public WitherscaleWurm(final WitherscaleWurm card) {
        super(card);
    }

    @Override
    public WitherscaleWurm copy() {
        return new WitherscaleWurm(this);
    }
}

class WitherscaleWurmEffect extends OneShotEffect {

    public WitherscaleWurmEffect() {
        super(Outcome.Benefit);
        staticText = "remove all -1/-1 counters from it";
    }

    public WitherscaleWurmEffect(WitherscaleWurmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent witherscaleWurm = game.getPermanent(source.getSourceId());
        if (witherscaleWurm != null
                && witherscaleWurm.getCounters().containsKey(CounterType.M1M1)) {
            int M1M1Counters = witherscaleWurm.getCounters().getCount(CounterType.M1M1);
            witherscaleWurm.getCounters().removeCounter(CounterType.M1M1, M1M1Counters);
            return true;
        }
        return false;
    }

    @Override
    public WitherscaleWurmEffect copy() {
        return new WitherscaleWurmEffect(this);
    }
}
