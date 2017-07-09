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
package mage.abilities.mana;

import java.util.List;
import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleManaAbility extends ActivatedManaAbilityImpl {

    private boolean predictable;

    public SimpleManaAbility(Zone zone, ManaEffect effect, Cost cost) {
        this(zone, effect, cost, true);
    }

    /**
     *
     * @param zone
     * @param effect
     * @param cost
     * @param predictable set to false if definig the mana type or amount needs
     * to reveal information and can't be predicted
     */
    public SimpleManaAbility(Zone zone, ManaEffect effect, Cost cost, boolean predictable) {
        super(zone, effect, cost);
        this.predictable = predictable;
    }

    public SimpleManaAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);
        this.netMana.add(mana.copy());
        this.predictable = true;
    }

    public SimpleManaAbility(final SimpleManaAbility ability) {
        super(ability);
        this.predictable = ability.predictable;
    }

    @Override
    public SimpleManaAbility copy() {
        return new SimpleManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        if (netMana.isEmpty() && predictable) {
            for (Effect effect : getEffects()) {
                if (effect instanceof ManaEffect) {
                    Mana effectMana = ((ManaEffect) effect).getMana(game, this);
                    if (effectMana != null) {
                        netMana.add(effectMana);
                    }
                }
            }
        }
        return netMana;
    }

}
