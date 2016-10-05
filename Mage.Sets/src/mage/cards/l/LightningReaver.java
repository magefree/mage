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
package mage.cards.l;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class LightningReaver extends CardImpl {

    public LightningReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}");
        this.subtype.add("Zombie");
        this.subtype.add("Beast");


        
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste; fear
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(FearAbility.getInstance());

        // Whenever Lightning Reaver deals combat damage to a player, put a charge counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), false));

        // At the beginning of your end step, Lightning Reaver deals damage equal to the number of charge counters on it to each opponent.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DamageOpponentsEffect(), TargetController.YOU, false));
    }

    public LightningReaver(final LightningReaver card) {
        super(card);
    }

    @Override
    public LightningReaver copy() {
        return new LightningReaver(this);
    }
}

class DamageOpponentsEffect extends OneShotEffect {

    public DamageOpponentsEffect() {
        super(Outcome.Damage);
        staticText = "Lightning Reaver deals damage equal to the number of charge counters on it to each opponent";
    }

    public DamageOpponentsEffect(final DamageOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue amount = new CountersSourceCount(CounterType.CHARGE);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
            }
        }
        return true;
    }

    @Override
    public DamageOpponentsEffect copy() {
        return new DamageOpponentsEffect(this);
    }
}
