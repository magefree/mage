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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Use this effect only (I guess) with EntersBattlefieldAbility like abilities
 *
 * @author LevelX2
 */
public class EntersBattlefieldWithXCountersEffect extends OneShotEffect {

    protected final Counter counter;

    public EntersBattlefieldWithXCountersEffect(Counter counter) {
        super(Outcome.BoostCreature);
        this.counter = counter;
        staticText = "with X " + counter.getName() + " counters on it";
    }

    public EntersBattlefieldWithXCountersEffect(final EntersBattlefieldWithXCountersEffect effect) {
        super(effect);
        this.counter = effect.counter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
                permanent = game.getPermanentEntering(source.getSourceId());
            }
        }
        if (permanent != null) {
            SpellAbility spellAbility = (SpellAbility) getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (spellAbility != null
                    && spellAbility.getSourceId().equals(source.getSourceId())
                    && permanent.getZoneChangeCounter(game) == spellAbility.getSourceObjectZoneChangeCounter()) {
                if (spellAbility.getSourceId().equals(source.getSourceId())) { // put into play by normal cast
                    int amount = spellAbility.getManaCostsToPay().getX();
                    if (amount > 0) {
                        Counter counterToAdd = counter.copy();
                        counterToAdd.add(amount - counter.getCount());
                        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
                        permanent.addCounters(counterToAdd, source, game, appliedEffects);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public EntersBattlefieldWithXCountersEffect copy() {
        return new EntersBattlefieldWithXCountersEffect(this);
    }

}
