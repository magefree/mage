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

package mage.abilities.effects;

import mage.Constants.AsThoughEffectType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffects implements Serializable {

    //transient Continuous effects
    private ContinuousEffectsList<ContinuousEffect> layeredEffects = new ContinuousEffectsList<ContinuousEffect>();
    private ContinuousEffectsList<ReplacementEffect> replacementEffects = new ContinuousEffectsList<ReplacementEffect>();
    private ContinuousEffectsList<PreventionEffect> preventionEffects = new ContinuousEffectsList<PreventionEffect>();
    private ContinuousEffectsList<RequirementEffect> requirementEffects = new ContinuousEffectsList<RequirementEffect>();
    private ContinuousEffectsList<RestrictionEffect> restrictionEffects = new ContinuousEffectsList<RestrictionEffect>();
    private ContinuousEffectsList<AsThoughEffect> asThoughEffects = new ContinuousEffectsList<AsThoughEffect>();
    private ContinuousEffectsList<CostModificationEffect> costModificationEffects = new ContinuousEffectsList<CostModificationEffect>();

    private List<ContinuousEffectsList<?>> allEffectsLists = new ArrayList<ContinuousEffectsList<?>>();
    
    private final ApplyCountersEffect applyCounters;
    private final PlaneswalkerRedirectionEffect planeswalkerRedirectionEffect;
    private final AuraReplacementEffect auraReplacementEffect;

    private List<ContinuousEffect> previous = new ArrayList<ContinuousEffect>();
    
    private Map<Effect, UUID> sources = new HashMap<Effect, UUID>();

    public ContinuousEffects() {
        applyCounters = new ApplyCountersEffect();
        planeswalkerRedirectionEffect = new PlaneswalkerRedirectionEffect();
        auraReplacementEffect = new AuraReplacementEffect();
        collectAllEffects();
    }

    public ContinuousEffects(final ContinuousEffects effect) {
        this.applyCounters = effect.applyCounters.copy();
        this.planeswalkerRedirectionEffect = effect.planeswalkerRedirectionEffect.copy();
        this.auraReplacementEffect = effect.auraReplacementEffect.copy();
        layeredEffects = effect.layeredEffects.copy();
        replacementEffects = effect.replacementEffects.copy();
        preventionEffects = effect.preventionEffects.copy();
        requirementEffects = effect.requirementEffects.copy();
        restrictionEffects = effect.restrictionEffects.copy();
        asThoughEffects = effect.asThoughEffects.copy();
        costModificationEffects = effect.costModificationEffects.copy();
        for (Map.Entry<Effect, UUID> entry : effect.sources.entrySet()) {
            sources.put(entry.getKey(), entry.getValue());
        }
        collectAllEffects();
    }

    private void collectAllEffects() {
        allEffectsLists.add(layeredEffects);
        allEffectsLists.add(replacementEffects);
        allEffectsLists.add(preventionEffects);
        allEffectsLists.add(requirementEffects);
        allEffectsLists.add(restrictionEffects);
        allEffectsLists.add(asThoughEffects);
        allEffectsLists.add(costModificationEffects);
    }

    public ContinuousEffects copy() {
        return new ContinuousEffects(this);
    }

    public List<RequirementEffect> getRequirementEffects() {
        return requirementEffects;
    }

    public List<RestrictionEffect> getRestrictionEffects() {
        return restrictionEffects;
    }

    public Ability getAbility(UUID effectId) {
        Ability ability = layeredEffects.getAbility(effectId);
        if (ability == null)
            ability = replacementEffects.getAbility(effectId);
        if (ability == null)
            ability = preventionEffects.getAbility(effectId);
        if (ability == null)
            ability = requirementEffects.getAbility(effectId);
        if (ability == null)
            ability = restrictionEffects.getAbility(effectId);
        if (ability == null)
            ability = asThoughEffects.getAbility(effectId);
        if (ability == null)
            ability = costModificationEffects.getAbility(effectId);
        return ability;
    }

    public void removeEndOfTurnEffects() {
        layeredEffects.removeEndOfTurnEffects();
        replacementEffects.removeEndOfTurnEffects();
        preventionEffects.removeEndOfTurnEffects();
        requirementEffects.removeEndOfTurnEffects();
        restrictionEffects.removeEndOfTurnEffects();
        asThoughEffects.removeEndOfTurnEffects();
        costModificationEffects.removeEndOfTurnEffects();
    }

    public void removeInactiveEffects(Game game) {
        layeredEffects.removeInactiveEffects(game);
        replacementEffects.removeInactiveEffects(game);
        preventionEffects.removeInactiveEffects(game);
        requirementEffects.removeInactiveEffects(game);
        restrictionEffects.removeInactiveEffects(game);
        asThoughEffects.removeInactiveEffects(game);
        costModificationEffects.removeInactiveEffects(game);
    }

    public List<ContinuousEffect> getLayeredEffects(Game game) {
        List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>();
        for (ContinuousEffect effect: layeredEffects) {
            switch (effect.getDuration()) {
                case WhileOnBattlefield:
                case WhileOnStack:
                case WhileInGraveyard:
                    Ability ability = layeredEffects.getAbility(effect.getId());
                    if (ability.isInUseableZone(game, null, false))
                        layerEffects.add(effect);
                    break;
                default:
                    layerEffects.add(effect);
            }
        }

        updateTimestamps(layerEffects);

        Collections.sort(layerEffects, new TimestampSorter());
        return layerEffects;
    }

    /**
     * Initially effect timestamp is set when game starts in game.loadCard method.
     * After that timestamp should be updated whenever effect becomes "actual" meaning it becomes turned on
     * that is defined by Ability.#isInUseableZone(Game, boolean) method in #getLayeredEffects(Game).
     * @param layerEffects
     */
    private void updateTimestamps(List<ContinuousEffect> layerEffects) {
        for (ContinuousEffect continuousEffect : layerEffects) {
            // check if it's new, then set timestamp
            if (!previous.contains(continuousEffect)) {
                continuousEffect.setTimestamp();
            }
        }
        previous.clear();
        previous.addAll(layerEffects);
    }

    private List<ContinuousEffect> filterLayeredEffects(List<ContinuousEffect> effects, Layer layer) {
        List<ContinuousEffect> layerEffects = new ArrayList<ContinuousEffect>();
        for (ContinuousEffect effect: effects) {
            if (effect.hasLayer(layer))
                layerEffects.add(effect);
        }
        return layerEffects;
    }

    public List<RequirementEffect> getApplicableRequirementEffects(Permanent permanent, Game game) {
        List<RequirementEffect> effects = new ArrayList<RequirementEffect>();
        for (RequirementEffect effect: requirementEffects) {
            Ability ability = requirementEffects.getAbility(effect.getId());
            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, false)) {
                if (effect.applies(permanent, ability, game))
                    effects.add(effect);
            }
        }
        return effects;
    }

    public List<RestrictionEffect> getApplicableRestrictionEffects(Permanent permanent, Game game) {
        List<RestrictionEffect> effects = new ArrayList<RestrictionEffect>();
        for (RestrictionEffect effect: restrictionEffects) {
            Ability ability = restrictionEffects.getAbility(effect.getId());
            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, permanent, false)) {
                if (effect.applies(permanent, ability, game))
                    effects.add(effect);
            }
        }
        return effects;
    }

    /**
     *
     * @param event
     * @param game
     * @return a list of all {@link ReplacementEffect} that apply to the current event
     */
    private List<ReplacementEffect> getApplicableReplacementEffects(GameEvent event, Game game) {
        List<ReplacementEffect> replaceEffects = new ArrayList<ReplacementEffect>();
        if (planeswalkerRedirectionEffect.applies(event, null, game))
            replaceEffects.add(planeswalkerRedirectionEffect);
        if(auraReplacementEffect.applies(event, null, game))
            replaceEffects.add(auraReplacementEffect);
        //get all applicable transient Replacement effects
        for (ReplacementEffect effect: replacementEffects) {
            Ability ability = replacementEffects.getAbility(effect.getId());
            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, false)) {
                if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                    if (effect.applies(event, ability, game)) {
                        replaceEffects.add(effect);
                    }
                }
            }
        }
        for (PreventionEffect effect: preventionEffects) {
            Ability ability = preventionEffects.getAbility(effect.getId());
            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, false)) {
                if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                    if (effect.applies(event, ability, game)) {
                        replaceEffects.add(effect);
                    }
                }
            }
        }
        return replaceEffects;
    }

    /**
     * Filters out cost modification effects that are not active.
     *
     * @param game
     * @return
     */
    private List<CostModificationEffect> getApplicableCostModificationEffects(Game game) {
        List<CostModificationEffect> costEffects = new ArrayList<CostModificationEffect>();

        for (CostModificationEffect effect: costModificationEffects) {
            Ability ability = costModificationEffects.getAbility(effect.getId());
            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, false)) {
                if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                    costEffects.add(effect);
                }
            }
        }

        return costEffects;
    }

    public boolean asThough(UUID objectId, AsThoughEffectType type, Game game) {
        List<AsThoughEffect> asThoughEffectsList = getApplicableAsThoughEffects(game);

        for (AsThoughEffect effect: asThoughEffectsList) {
            if (effect.getAsThoughEffectType() == type) {
                if (effect.applies(objectId, asThoughEffects.getAbility(effect.getId()), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Filters out asThough effects that are not active.
     *
     * @param game
     * @return
     */
    private List<AsThoughEffect> getApplicableAsThoughEffects(Game game) {
        List<AsThoughEffect> asThoughEffectsList = new ArrayList<AsThoughEffect>();

        for (AsThoughEffect effect: asThoughEffects) {
            Ability ability = asThoughEffects.getAbility(effect.getId());
            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, false)) {
                if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                    asThoughEffectsList.add(effect);
                }
            }
        }

        return asThoughEffectsList;
    }

    /**
     * Inspects all {@link Permanent permanent's} {@link Ability abilities} on the battlefield
     * for {@link CostModificationEffect cost modification effects} and applies them if necessary.
     *
     * @param abilityToModify
     * @param game
     * @return
     */
    public void costModification ( Ability abilityToModify, Game game ) {
        List<CostModificationEffect> costEffects = getApplicableCostModificationEffects(game);

        for ( CostModificationEffect effect : costEffects) {
            if ( effect.applies(abilityToModify, costModificationEffects.getAbility(effect.getId()), game) ) {
                effect.apply(game, costModificationEffects.getAbility(effect.getId()), abilityToModify);
            }
        }
    }

    public boolean replaceEvent(GameEvent event, Game game) {
        boolean caught = false;
        List<UUID> consumed = new ArrayList<UUID>();
        do {
            List<ReplacementEffect> rEffects = getApplicableReplacementEffects(event, game);
            for (Iterator<ReplacementEffect> i = rEffects.iterator(); i.hasNext();) {
                ReplacementEffect entry = i.next();
                if (consumed.contains(entry.getId()))
                    i.remove();
            }
            if (rEffects.isEmpty())
                break;
            int index;
            if (rEffects.size() == 1) {
                index = 0;
            }
            else {
                //20100716 - 616.1c
                Player player = game.getPlayer(event.getPlayerId());
                index = player.chooseEffect(rEffects, game);
            }
            ReplacementEffect rEffect = rEffects.get(index);
            caught = rEffect.replaceEvent(event, this.getAbility(rEffect.getId()), game);
            if (caught)
                break;
            consumed.add(rEffect.getId());
            game.applyEffects();
        } while (true);
        return caught;
    }

    //20091005 - 613
    public void apply(Game game) {
        removeInactiveEffects(game);
        List<ContinuousEffect> layerEffects = getLayeredEffects(game);
        List<ContinuousEffect> layer = filterLayeredEffects(layerEffects, Layer.CopyEffects_1);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.CopyEffects_1, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
        }
        
        //Reload layerEffect
        layerEffects = getLayeredEffects(game);
        
        layer = filterLayeredEffects(layerEffects, Layer.ControlChangingEffects_2);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.ControlChangingEffects_2, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
        }
        layer = filterLayeredEffects(layerEffects, Layer.TextChangingEffects_3);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.TextChangingEffects_3, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
        }
        layer = filterLayeredEffects(layerEffects, Layer.TypeChangingEffects_4);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.TypeChangingEffects_4, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
        }
        layer = filterLayeredEffects(layerEffects, Layer.ColorChangingEffects_5);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.ColorChangingEffects_5, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
        }
        layer = filterLayeredEffects(layerEffects, Layer.AbilityAddingRemovingEffects_6);
        for (ContinuousEffect effect: layer) {
            layerEffects = getLayeredEffects(game);
            if (layerEffects.contains(effect)) {
                effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
            }
        }

        //Reload layerEffect
        layerEffects = getLayeredEffects(game);
        
        layer = filterLayeredEffects(layerEffects, Layer.PTChangingEffects_7);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.PTChangingEffects_7, SubLayer.SetPT_7b, layeredEffects.getAbility(effect.getId()), game);
        }
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, layeredEffects.getAbility(effect.getId()), game);
        }
        applyCounters.apply(Layer.PTChangingEffects_7, SubLayer.Counters_7d, null, game);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, layeredEffects.getAbility(effect.getId()), game);
        }
        layer = filterLayeredEffects(layerEffects, Layer.PlayerEffects);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.PlayerEffects, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
        }
        layer = filterLayeredEffects(layerEffects, Layer.RulesEffects);
        for (ContinuousEffect effect: layer) {
            effect.apply(Layer.RulesEffects, SubLayer.NA, layeredEffects.getAbility(effect.getId()), game);
        }
    }

    public void addEffect(ContinuousEffect effect, UUID sourceId, Ability source) {
        addEffect(effect, source);
        sources.put(effect, sourceId);
    }

    public void addEffect(ContinuousEffect effect, Ability source) {
        switch (effect.getEffectType()) {
            case REPLACEMENT:
                ReplacementEffect newReplacementEffect = (ReplacementEffect)effect;
                replacementEffects.addEffect(newReplacementEffect, source);
                break;
            case PREVENTION:
                PreventionEffect newPreventionEffect = (PreventionEffect)effect;
                preventionEffects.addEffect(newPreventionEffect, source);
                break;
            case RESTRICTION:
                RestrictionEffect newRestrictionEffect = (RestrictionEffect)effect;
                restrictionEffects.addEffect(newRestrictionEffect, source);
                break;
            case REQUIREMENT:
                RequirementEffect newRequirementEffect = (RequirementEffect)effect;
                requirementEffects.addEffect(newRequirementEffect, source);
                break;
            case ASTHOUGH:
                AsThoughEffect newAsThoughEffect = (AsThoughEffect)effect;
                asThoughEffects.addEffect(newAsThoughEffect, source);
                break;
            case COSTMODIFICATION:
                CostModificationEffect newCostModificationEffect = (CostModificationEffect)effect;
                costModificationEffects.addEffect(newCostModificationEffect, source);
                break;
            default:
                ContinuousEffect newEffect = (ContinuousEffect)effect;
                layeredEffects.addEffect(newEffect, source);
                break;
        }
    }

    public void setController(UUID cardId, UUID controllerId) {
        for (ContinuousEffectsList effectsList : allEffectsLists) {
            setControllerForEffect(effectsList, cardId, controllerId);
        }
    }

    private void setControllerForEffect(ContinuousEffectsList<?> effects, UUID cardId, UUID controllerId) {
        for (Effect effect : effects) {
            Ability ability = effects.getAbility(effect.getId());
            if (ability.getSourceId().equals(cardId)) {
                ability.setControllerId(controllerId);
            }
        }
    }

    public void clear() {
        for (ContinuousEffectsList effectsList : allEffectsLists) {
            effectsList.clear();
        }
        sources.clear();
    }

    /**
     * Removes all granted effects
     */
    public void removeGainedEffects() {
        for (Map.Entry<Effect, UUID> source : sources.entrySet()) {
            for (ContinuousEffectsList effectsList : allEffectsLists) {
                Ability ability = effectsList.getAbility(source.getKey().getId());
                if (ability != null && !ability.getSourceId().equals(source.getValue())) {
                    effectsList.removeEffect((ContinuousEffect) source.getKey());
                }
            }
        }
    }

    /**
     * Removes effects granted by sourceId
     *
     * @param sourceId
     */
    public List<Effect> removeGainedEffectsForSource(UUID sourceId) {
        List<Effect> effects = new ArrayList<Effect>();
        for (Map.Entry<Effect, UUID> source : sources.entrySet()) {
            if (sourceId.equals(source.getValue())) {
                for (ContinuousEffectsList effectsList : allEffectsLists) {
                    Ability ability = effectsList.getAbility(source.getKey().getId());
                    if (ability != null && !ability.getSourceId().equals(source.getValue())) {
                        effectsList.removeEffect((ContinuousEffect) source.getKey());
                        effects.add(source.getKey());
                    }
                }
            }
        }
        return effects;
    }

}
class TimestampSorter implements Comparator<ContinuousEffect> {
    @Override
    public int compare(ContinuousEffect one, ContinuousEffect two) {
        return one.getTimestamp().compareTo(two.getTimestamp());
    }
}
