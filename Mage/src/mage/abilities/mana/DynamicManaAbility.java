/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.mana;

import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.DynamicManaEffect;
import mage.game.Game;

/**
 *
 * @author North
 */
public class DynamicManaAbility extends ManaAbility<DynamicManaAbility> {

    private DynamicManaEffect manaEffect;

    /**
     * TapSourceCost added by default
     */
    public DynamicManaAbility(Mana mana, DynamicValue amount) {
        this(mana, amount, new TapSourceCost());
    }

    public DynamicManaAbility(Mana mana, DynamicValue amount, Cost cost) {
        super(Zone.BATTLEFIELD, new DynamicManaEffect(mana, amount), cost);
        manaEffect = (DynamicManaEffect) this.getEffects().get(0);
    }

    public DynamicManaAbility(final DynamicManaAbility ability) {
        super(ability);
		manaEffect = ability.manaEffect;
    }

    @Override
    public DynamicManaAbility copy() {
        return new DynamicManaAbility(this);
    }

    @Override
    public Mana getNetMana(Game game) {
        if (game == null) {
            return new Mana();
        }
        return new Mana(manaEffect.computeMana(game, this));
    }
}
