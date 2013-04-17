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

import mage.Constants;
import mage.abilities.Ability;
import mage.game.Game;

import java.util.*;
import static mage.Constants.Duration.Custom;
import static mage.Constants.Duration.OneUse;
import static mage.Constants.Duration.WhileOnBattlefield;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffectsList<T extends ContinuousEffect> extends ArrayList<T> {

    // the effectAbilityMap holds for each effect all abilities that are connected (used) with this effect
    private final Map<UUID, HashSet<Ability>> effectAbilityMap = new HashMap<UUID, HashSet<Ability>>();

    public ContinuousEffectsList() { }

    public ContinuousEffectsList(final ContinuousEffectsList<T> effects) {
        this.ensureCapacity(effects.size());
        for (ContinuousEffect cost: effects) {
            this.add((T)cost.copy());
        }
        for (Map.Entry<UUID, HashSet<Ability>> entry: effects.effectAbilityMap.entrySet()) {
            HashSet<Ability> newSet = new HashSet<Ability>();
            for (Ability ability :(HashSet<Ability>)entry.getValue()) {
                newSet.add(ability.copy());
            }
            effectAbilityMap.put(entry.getKey(), newSet);
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
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    public void removeInactiveEffects(Game game) {
        for (Iterator<T> i = this.iterator(); i.hasNext();) {
            T entry = i.next();
            if (isInactive(entry, game)) {
                i.remove();
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    private boolean isInactive(T effect, Game game) {
        HashSet<Ability> set = effectAbilityMap.get(effect.getId());
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Ability ability = (Ability)it.next();
            if (ability == null) {
                it.remove();
            } else  if (effect.isDiscarded()) {
                it.remove();
            } else {
                switch(effect.getDuration()) {
                    case WhileOnBattlefield:
                        if (game.getObject(ability.getSourceId()) == null) {//TODO: does this really works?? object is returned across the game
                            it.remove();                                
                        }
                    case OneUse:
                        if (effect.isUsed()) {
                            it.remove();
                        }
                    case Custom:
                        if (effect.isInactive(ability , game)) {
                               it.remove();
                        }
                }
            }
        }
        return set.isEmpty();
    }

    /**
     * Adds an effect and its connected ability to the list.
     * For each effect will be stored, which abilities are connected to the effect.
     * So an effect can be connected to multiple abilities.
     *
     * @param effect - effect to add
     * @param source - connected ability
     */
    public void addEffect(T effect, Ability source) {
        if (effectAbilityMap.containsKey(effect.getId())) {
            HashSet<Ability> set = effectAbilityMap.get(effect.getId());
            for (Ability ability: set) {
                if (ability.getId().equals(source.getId()) && ability.getSourceId().equals(source.getSourceId()) ) {
                    return;
                }
            }
            set.add(source);
            return;
        }
        HashSet<Ability> set = new HashSet<Ability>();
        set.add(source);
        this.effectAbilityMap.put(effect.getId(), set);
        this.add(effect);
    }

    public HashSet<Ability> getAbility(UUID effectId) {
        return effectAbilityMap.get(effectId);
    }

    /**
     * Removes an effect and / or a connected ability.
     * If no ability for this effect is left in the effectAbilityMap, the effect will be removed.
     * Otherwise the effect won't be removed.
     *
     * @param effect - effect to remove if all abilities are removed
     * @param ability - ability to remove
     */

    public void removeEffect(T effect, Ability ability) {
        for (Iterator<T> i = this.iterator(); i.hasNext();) {
            T entry = i.next();
            if (entry.equals(effect)) {
                HashSet<Ability> abilities = effectAbilityMap.get(effect.getId());
                if (!abilities.isEmpty()) {
                    abilities.remove(ability);
                }
                if (abilities.isEmpty()) {
                    i.remove();
                }
            }
        }
    }

    public void removeEffectAbilityMap(UUID effectId) {
        effectAbilityMap.remove(effectId);
    }

    @Override
    public void clear() {
        super.clear();
        effectAbilityMap.clear();
    }
}
