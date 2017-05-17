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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.ManaEffect;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ActivatedManaAbilityImpl extends ActivatedAbilityImpl implements ManaAbility {

    protected List<Mana> netMana = new ArrayList<>();
    protected boolean undoPossible;

    public ActivatedManaAbilityImpl(Zone zone, ManaEffect effect, Cost cost) {
        super(AbilityType.MANA, zone);
        this.usesStack = false;
        this.undoPossible = true;
        if (effect != null) {
            this.addEffect(effect);
        }
        if (cost != null) {
            this.addCost(cost);
        }
    }

    public ActivatedManaAbilityImpl(final ActivatedManaAbilityImpl ability) {
        super(ability);
        this.netMana.addAll(ability.netMana);
        this.undoPossible = ability.undoPossible;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!controlsAbility(playerId, game)) {
            return false;
        }
        // check if player is in the process of playing spell costs and he is no longer allowed to use activated mana abilities (e.g. becaus he started to use improvise)
        //20091005 - 605.3a
        return costs.canPay(this, sourceId, controllerId, game);

    }

    /**
     * Used to check the possible mana production to determine which spells
     * and/or abilities can be used. (player.getPlayable()).
     *
     * @param game
     * @return
     */
    @Override
    public List<Mana> getNetMana(Game game) {
        return netMana;
    }

    /**
     * Used to check if the ability itself defines mana types it can produce.
     *
     * @return
     */
    @Override
    public boolean definesMana(Game game) {
        return !getNetMana(game).isEmpty();
    }

    /**
     * Is it allowed to undo the mana creation. It's e.g. not allowed if some
     * game revealing information is related (like reveal the top card of the
     * library)
     *
     * @return
     */
    public boolean isUndoPossible() {
        return undoPossible;
    }

    public void setUndoPossible(boolean undoPossible) {
        this.undoPossible = undoPossible;
    }

}
