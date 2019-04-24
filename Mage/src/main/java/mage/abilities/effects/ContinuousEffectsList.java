
package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.constants.Duration;
import mage.game.Game;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffectsList<T extends ContinuousEffect> extends ArrayList<T> {

    private static final Logger logger = Logger.getLogger(ContinuousEffectsList.class);

    // the effectAbilityMap holds for each effect all abilities that are connected (used) with this effect
    private final Map<UUID, Set<Ability>> effectAbilityMap = new HashMap<>();

    public ContinuousEffectsList() {
    }

    public ContinuousEffectsList(final ContinuousEffectsList<T> effects) {
        this.ensureCapacity(effects.size());
        for (ContinuousEffect cost : effects) {
            this.add((T) cost.copy());
        }
        for (Map.Entry<UUID, Set<Ability>> entry : effects.effectAbilityMap.entrySet()) {
            Set<Ability> newSet = new HashSet<>();
            for (Ability ability : entry.getValue()) {
                newSet.add(ability.copy());
            }
            effectAbilityMap.put(entry.getKey(), newSet);
        }
    }

    public ContinuousEffectsList<T> copy() {
        return new ContinuousEffectsList<>(this);
    }

    public void removeEndOfTurnEffects() {
        for (Iterator<T> i = this.iterator(); i.hasNext(); ) {
            T entry = i.next();
            if (entry.getDuration() == Duration.EndOfTurn) {
                i.remove();
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    public void removeEndOfCombatEffects() {

        for (Iterator<T> i = this.iterator(); i.hasNext(); ) {
            T entry = i.next();
            if (entry.getDuration() == Duration.EndOfCombat) {
                i.remove();
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    public void removeInactiveEffects(Game game) {
        for (Iterator<T> i = this.iterator(); i.hasNext(); ) {
            T entry = i.next();
            if (isInactive(entry, game)) {
                i.remove();
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    private boolean isInactive(T effect, Game game) {
        Set<Ability> set = effectAbilityMap.get(effect.getId());
        if (set == null) {
            logger.debug("No abilities for effect found: " + effect.toString());
            return false;
        }
        Iterator<Ability> it = set.iterator();
        while (it.hasNext()) {
            Ability ability = it.next();
            if (ability == null) {
                it.remove();
            } else if (ability instanceof MageSingleton) {
                return false;
            } else if (effect.isDiscarded()) {
                it.remove();
            } else {
                switch (effect.getDuration()) {
                    case WhileOnBattlefield:
                    case WhileInGraveyard:
                    case WhileOnStack:
                        if (ability.getSourceId() != null && game.getObject(ability.getSourceId()) == null) { // Commander effects have no sourceId
                            it.remove(); // if the related source object does no longer exist in game - the effect has to be removed
                        }
                        break;
                    case OneUse:
                        if (effect.isUsed()) {
                            it.remove();
                        }
                        break;
                    case Custom:
                    case UntilYourNextTurn:
                        if (effect.isInactive(ability, game)) {
                            it.remove();
                        }
                }
            }
        }
        return set.isEmpty();
    }

    /**
     * Adds an effect and its connected ability to the list. For each effect
     * will be stored, which abilities are connected to the effect. So an effect
     * can be connected to multiple abilities.
     *
     * @param effect - effect to add
     * @param source - connected ability
     */
    public void addEffect(T effect, Ability source) {
        if (effectAbilityMap.containsKey(effect.getId())) {
            Set<Ability> set = effectAbilityMap.get(effect.getId());
            for (Ability ability : set) {
                if (ability.getId().equals(source.getId()) && ability.getSourceId().equals(source.getSourceId())) {
                    return;
                }
            }
            set.add(source);
            return;
        }
        Set<Ability> set = new HashSet<>();
        set.add(source);
        this.effectAbilityMap.put(effect.getId(), set);
        this.add(effect);
    }

    public Set<Ability> getAbility(UUID effectId) {
        return effectAbilityMap.getOrDefault(effectId, new HashSet<>());
    }

    public void removeEffects(UUID effectIdToRemove, Set<Ability> abilitiesToRemove) {
        Set<Ability> abilities = effectAbilityMap.get(effectIdToRemove);
        if (abilitiesToRemove != null && abilities != null) {
            abilities.removeAll(abilitiesToRemove);
        }
        if (abilities == null || abilities.isEmpty()) {
            for (Iterator<T> iterator = this.iterator(); iterator.hasNext(); ) {
                ContinuousEffect effect = iterator.next();
                if (effect.getId().equals(effectIdToRemove)) {
                    iterator.remove();
                    break;
                }
            }
            effectAbilityMap.remove(effectIdToRemove);
        }
    }

    @Override
    public void clear() {
        super.clear();
        effectAbilityMap.clear();
    }
}
