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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class LightningReaver extends CardImpl<LightningReaver> {

    public LightningReaver(UUID ownerId) {
        super(ownerId, 42, "Lightning Reaver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Zombie");
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.color.setBlack(true);
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

class DamageOpponentsEffect extends OneShotEffect<DamageOpponentsEffect> {

    public DamageOpponentsEffect() {
        super(Outcome.Damage);
        staticText = "Lightning Reaver deals damage equal to the number of charge counters on it to each opponent";
    }

    public DamageOpponentsEffect(final DamageOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue amount = new CountersCount(CounterType.CHARGE);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(amount.calculate(game, source), source.getId(), game, false, true);
            }
        }
        return true;
    }

    @Override
    public DamageOpponentsEffect copy() {
        return new DamageOpponentsEffect(this);
    }
}
