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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class EternityVessel extends CardImpl<EternityVessel> {

    public EternityVessel(UUID ownerId) {
        super(ownerId, 200, "Eternity Vessel", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "ZEN";

        // Eternity Vessel enters the battlefield with X charge counters on it, where X is your life total.
        this.addAbility(new EntersBattlefieldAbility(new EternityVesselEffect()));
        
        // Landfall - Whenever a land enters the battlefield under your control, you may have your life total become the number of charge counters on Eternity Vessel.
        this.addAbility(new LandfallAbility(Constants.Zone.BATTLEFIELD, new EternityVesselEffect2(), true));
    }

    public EternityVessel(final EternityVessel card) {
        super(card);
    }

    @Override
    public EternityVessel copy() {
        return new EternityVessel(this);
    }
}

class EternityVesselEffect extends OneShotEffect<EternityVesselEffect> {
    public EternityVesselEffect() {
        super(Outcome.Benefit);
        staticText = "with X charge counters on it, where X is your life total";
    }

    public EternityVesselEffect(final EternityVesselEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent vessel = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        if (vessel != null && you != null) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility) {
                int amount = you.getLife();
                if (amount > 0) {
                    vessel.addCounters(CounterType.CHARGE.createInstance(amount), game);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public EternityVesselEffect copy() {
        return new EternityVesselEffect(this);
    }
}

class EternityVesselEffect2 extends OneShotEffect<EternityVesselEffect2> {
    public EternityVesselEffect2() {
        super(Outcome.Benefit);
        staticText = "you may have your life total become the number of charge counters on Eternity Vessel";
    }

    public EternityVesselEffect2(final EternityVesselEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent vessel = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        if (vessel != null && you != null) {
            you.setLife(vessel.getCounters().getCount(CounterType.CHARGE), game);
            return true;
        }
        return false;
    }

    @Override
    public EternityVesselEffect2 copy() {
        return new EternityVesselEffect2(this);
    }
}