/*
* Copyright 2012 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.effects;

import java.util.*;
import mage.Constants;
import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffectsList<T extends ContinuousEffect> extends ArrayList<T> {

    private final Map<UUID, Ability> abilityMap = new HashMap<UUID, Ability>();

    public ContinuousEffectsList() { }

    public ContinuousEffectsList(final ContinuousEffectsList<T> effects) {
        this.ensureCapacity(effects.size());
        for (ContinuousEffect cost: effects) {
            this.add((T)cost.copy());
        }
        for (Map.Entry<UUID, Ability> entry: effects.abilityMap.entrySet()) {
            abilityMap.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public ContinuousEffectsList copy() {
        return new ContinuousEffectsList(this);
    }

    public void removeEndOfTurnEffects() {
        for (Iterator<T> i = this.iterator(); i.hasNext();) {
            T entry = i.next();
            if (entry.getDuration() == Constants.Duration.EndOfTurn) {
                i.remove();
                abilityMap.remove(entry.getId());
            }
        }
    }

    public void removeInactiveEffects(Game game) {
        for (Iterator<T> i = this.iterator(); i.hasNext();) {
            T entry = i.next();
            if (isInactive(entry, game)) {
                i.remove();
                abilityMap.remove(entry.getId());
            }
        }
    }

    private boolean isInactive(T effect, Game game) {
        Ability ability = abilityMap.get(effect.getId());
        if (ability == null)
            return true;
        switch(effect.getDuration()) {
            case WhileOnBattlefield:
                if (game.getObject(ability.getSourceId()) == null)
                    return (true);
            case OneUse:
                return effect.isUsed();
            case Custom:
                return effect.isInactive(abilityMap.get(effect.getId()), game);
        }
        return false;
    }

    public void addEffect(T effect, Ability source) {
        if (abilityMap.containsKey(effect.getId()))
            return;
        this.add(effect);
        this.abilityMap.put(effect.getId(), source);
    }

    public Ability getAbility(UUID effectId) {
        return abilityMap.get(effectId);
    }

    @Override
    public void clear() {
        super.clear();
        abilityMap.clear();
    }
}
