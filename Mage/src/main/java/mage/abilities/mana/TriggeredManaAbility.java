/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DynamicManaEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;

/**
 * see 20110715 - 605.1b
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TriggeredManaAbility extends TriggeredAbilityImpl implements ManaAbility {

    protected List<Mana> netMana = new ArrayList<>();

    public TriggeredManaAbility(Zone zone, ManaEffect effect) {
        this(zone, effect, false);
    }

    public TriggeredManaAbility(Zone zone, ManaEffect effect, boolean optional) {
        super(zone, effect, optional);
        this.usesStack = false;
        this.abilityType = AbilityType.MANA;

    }

    public TriggeredManaAbility(final TriggeredManaAbility ability) {
        super(ability);
        this.netMana.addAll(ability.netMana);
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
        if (!getEffects().isEmpty()) {
            Effect effect = getEffects().get(0);
            if (effect != null && game != null) {
                ArrayList<Mana> newNetMana = new ArrayList<>();
                if (effect instanceof DynamicManaEffect) {

                    // TODO: effects from replacement effects like Mana Reflection are not considered yet
                    // TODO: effects that need a X payment (e.g. Mage-Ring Network) return always 0
                    newNetMana.add(((DynamicManaEffect) effect).computeMana(true, game, this));
                } else if (effect instanceof Effect) {
                    newNetMana.add(((ManaEffect) effect).getMana(game, this));
                }
                return newNetMana;
            }
        }
        return netMana;
    }

    /**
     * Used to check if the ability itself defines mana types it can produce.
     *
     * @return
     */
    @Override
    public boolean definesMana(Game game) {
        return !netMana.isEmpty();
    }
}
