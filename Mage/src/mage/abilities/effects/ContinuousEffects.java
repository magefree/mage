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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffects implements Serializable {

    private static final transient Logger logger = Logger.getLogger(ContinuousEffects.class);

    private long order = 0;

    //transient Continuous effects
    private ContinuousEffectsList<ContinuousEffect> layeredEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<ContinuousRuleModifyingEffect> continuousRuleModifyingEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<ReplacementEffect> replacementEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<PreventionEffect> preventionEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<RequirementEffect> requirementEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<RestrictionEffect> restrictionEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<RestrictionUntapNotMoreThanEffect> restrictionUntapNotMoreThanEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<CostModificationEffect> costModificationEffects = new ContinuousEffectsList<>();
    private ContinuousEffectsList<SpliceCardEffect> spliceCardEffects = new ContinuousEffectsList<>();

    private final Map<AsThoughEffectType, ContinuousEffectsList<AsThoughEffect>> asThoughEffectsMap = new EnumMap<>(AsThoughEffectType.class);
    public final List<ContinuousEffectsList<?>> allEffectsLists = new ArrayList<>();
    private final ApplyCountersEffect applyCounters;
    private final PlaneswalkerRedirectionEffect planeswalkerRedirectionEffect;
    private final AuraReplacementEffect auraReplacementEffect;

    private final List<ContinuousEffect> previous = new ArrayList<>();

    // note all effect/abilities that were only added temporary
    private final Map<ContinuousEffect, Set<Ability>> temporaryEffects = new HashMap<>();

    private final ContinuousEffectSorter sorter = new ContinuousEffectSorter();

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
        continuousRuleModifyingEffects = effect.continuousRuleModifyingEffects.copy();
        replacementEffects = effect.replacementEffects.copy();
        preventionEffects = effect.preventionEffects.copy();
        requirementEffects = effect.requirementEffects.copy();
        restrictionEffects = effect.restrictionEffects.copy();
        restrictionUntapNotMoreThanEffects = effect.restrictionUntapNotMoreThanEffects.copy();
        for (Map.Entry<AsThoughEffectType, ContinuousEffectsList<AsThoughEffect>> entry : effect.asThoughEffectsMap.entrySet()) {
            asThoughEffectsMap.put(entry.getKey(), entry.getValue().copy());
        }

        costModificationEffects = effect.costModificationEffects.copy();
        spliceCardEffects = effect.spliceCardEffects.copy();
        temporaryEffects.putAll(effect.temporaryEffects);
        collectAllEffects();
        order = effect.order;
    }

    private void collectAllEffects() {
        allEffectsLists.add(layeredEffects);
        allEffectsLists.add(continuousRuleModifyingEffects);
        allEffectsLists.add(replacementEffects);
        allEffectsLists.add(preventionEffects);
        allEffectsLists.add(requirementEffects);
        allEffectsLists.add(restrictionEffects);
        allEffectsLists.add(restrictionUntapNotMoreThanEffects);
        allEffectsLists.add(costModificationEffects);
        allEffectsLists.add(spliceCardEffects);
        for (ContinuousEffectsList continuousEffectsList : asThoughEffectsMap.values()) {
            allEffectsLists.add(continuousEffectsList);
        }
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

    public void removeEndOfCombatEffects() {
        layeredEffects.removeEndOfCombatEffects();
        continuousRuleModifyingEffects.removeEndOfCombatEffects();
        replacementEffects.removeEndOfCombatEffects();
        preventionEffects.removeEndOfCombatEffects();
        requirementEffects.removeEndOfCombatEffects();
        restrictionEffects.removeEndOfCombatEffects();
        for (ContinuousEffectsList asThoughtlist : asThoughEffectsMap.values()) {
            asThoughtlist.removeEndOfCombatEffects();
        }
        costModificationEffects.removeEndOfCombatEffects();
        spliceCardEffects.removeEndOfCombatEffects();
    }

    public void removeEndOfTurnEffects() {
        layeredEffects.removeEndOfTurnEffects();
        continuousRuleModifyingEffects.removeEndOfTurnEffects();
        replacementEffects.removeEndOfTurnEffects();
        preventionEffects.removeEndOfTurnEffects();
        requirementEffects.removeEndOfTurnEffects();
        restrictionEffects.removeEndOfTurnEffects();
        for (ContinuousEffectsList asThoughtlist : asThoughEffectsMap.values()) {
            asThoughtlist.removeEndOfTurnEffects();
        }
        costModificationEffects.removeEndOfTurnEffects();
        spliceCardEffects.removeEndOfTurnEffects();
    }

    public void removeInactiveEffects(Game game) {
        layeredEffects.removeInactiveEffects(game);
        continuousRuleModifyingEffects.removeInactiveEffects(game);
        replacementEffects.removeInactiveEffects(game);
        preventionEffects.removeInactiveEffects(game);
        requirementEffects.removeInactiveEffects(game);
        restrictionEffects.removeInactiveEffects(game);
        restrictionUntapNotMoreThanEffects.removeInactiveEffects(game);
        for (ContinuousEffectsList asThoughtlist : asThoughEffectsMap.values()) {
            asThoughtlist.removeInactiveEffects(game);
        }
        costModificationEffects.removeInactiveEffects(game);
        spliceCardEffects.removeInactiveEffects(game);
    }

    public List<ContinuousEffect> getLayeredEffects(Game game) {
        List<ContinuousEffect> layerEffects = new ArrayList<>();
        for (ContinuousEffect effect : layeredEffects) {
            switch (effect.getDuration()) {
                case WhileOnBattlefield:
                case WhileOnStack:
                case WhileInGraveyard:
                    HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
                    if (abilities != null) {
                        for (Ability ability : abilities) {
                            // If e.g. triggerd abilities (non static) created the effect, the ability must not be in usable zone (e.g. Unearth giving Haste effect)
                            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, null)) {
                                layerEffects.add(effect);
                                break;
                            }
                        }
                    } else {
                        logger.error("No abilities for continuous effect: " + effect.toString());
                    }
                    break;
                default:
                    layerEffects.add(effect);
            }
        }

        updateTimestamps(layerEffects);

        Collections.sort(layerEffects, sorter);
        return layerEffects;
    }

    /**
     * Initially effect timestamp is set when game starts in game.loadCard
     * method. After that timestamp should be updated whenever effect becomes
     * "actual" meaning it becomes turned on that is defined by
     * Ability.#isInUseableZone(Game, boolean) method in
     * #getLayeredEffects(Game).
     *
     * @param layerEffects
     */
    private void updateTimestamps(List<ContinuousEffect> layerEffects) {
        for (ContinuousEffect continuousEffect : layerEffects) {
            // check if it's new, then set order
            if (!previous.contains(continuousEffect)) {
                setOrder(continuousEffect);
            }
        }
        previous.clear();
        previous.addAll(layerEffects);
    }

    public void setOrder(ContinuousEffect effect) {
        effect.setOrder(order++);
    }

    private List<ContinuousEffect> filterLayeredEffects(List<ContinuousEffect> effects, Layer layer) {
        List<ContinuousEffect> layerEffects = new ArrayList<>();
        for (ContinuousEffect effect : effects) {
            if (effect.hasLayer(layer)) {
                layerEffects.add(effect);
            }
        }
        return layerEffects;
    }

    public HashMap<RequirementEffect, HashSet<Ability>> getApplicableRequirementEffects(Permanent permanent, Game game) {
        HashMap<RequirementEffect, HashSet<Ability>> effects = new HashMap<>();
        for (RequirementEffect effect : requirementEffects) {
            HashSet<Ability> abilities = requirementEffects.getAbility(effect.getId());
            HashSet<Ability> applicableAbilities = new HashSet<>();
            for (Ability ability : abilities) {
                if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, ability instanceof MageSingleton ? permanent : null, null)) {
                    if (effect.applies(permanent, ability, game)) {
                        applicableAbilities.add(ability);
                    }
                }
            }
            if (!applicableAbilities.isEmpty()) {
                effects.put(effect, abilities);
            }
        }
        return effects;
    }

    public HashMap<RestrictionEffect, HashSet<Ability>> getApplicableRestrictionEffects(Permanent permanent, Game game) {
        HashMap<RestrictionEffect, HashSet<Ability>> effects = new HashMap<>();
        for (RestrictionEffect effect : restrictionEffects) {
            HashSet<Ability> abilities = restrictionEffects.getAbility(effect.getId());
            HashSet<Ability> applicableAbilities = new HashSet<>();
            for (Ability ability : abilities) {
                if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, ability instanceof MageSingleton ? permanent : null, null)) {
                    if (effect.applies(permanent, ability, game)) {
                        applicableAbilities.add(ability);
                    }
                }
            }
            if (!applicableAbilities.isEmpty()) {
                effects.put(effect, abilities);
            }
        }
        return effects;
    }

    public HashMap<RestrictionUntapNotMoreThanEffect, HashSet<Ability>> getApplicableRestrictionUntapNotMoreThanEffects(Player player, Game game) {
        HashMap<RestrictionUntapNotMoreThanEffect, HashSet<Ability>> effects = new HashMap<>();
        for (RestrictionUntapNotMoreThanEffect effect : restrictionUntapNotMoreThanEffects) {
            HashSet<Ability> abilities = restrictionUntapNotMoreThanEffects.getAbility(effect.getId());
            HashSet<Ability> applicableAbilities = new HashSet<>();
            for (Ability ability : abilities) {
                if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, null)) {
                    if (effect.applies(player, ability, game)) {
                        applicableAbilities.add(ability);
                    }
                }
            }
            if (!applicableAbilities.isEmpty()) {
                effects.put(effect, abilities);
            }
        }
        return effects;
    }

    public boolean checkIfThereArePayCostToAttackBlockEffects(GameEvent event, Game game) {
        for (ReplacementEffect effect : replacementEffects) {
            if (!effect.checksEventType(event, game)) {
                continue;
            }
            if (effect instanceof PayCostToAttackBlockEffect) {
                HashSet<Ability> abilities = replacementEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    // for replacment effects of static abilities do not use LKI to check if to apply
                    if (ability.getAbilityType() != AbilityType.STATIC || ability.isInUseableZone(game, null, event)) {
                        if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                            if (!game.getScopeRelevant() || effect.hasSelfScope() || !event.getTargetId().equals(ability.getSourceId())) {
                                if (effect.applies(event, ability, game)
                                        && !((PayCostToAttackBlockEffect) effect).isCostless(event, ability, game)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param event
     * @param game
     * @return a list of all {@link ReplacementEffect} that apply to the current
     * event
     */
    private HashMap<ReplacementEffect, HashSet<Ability>> getApplicableReplacementEffects(GameEvent event, Game game) {
        HashMap<ReplacementEffect, HashSet<Ability>> replaceEffects = new HashMap<>();
        if (planeswalkerRedirectionEffect.checksEventType(event, game) && planeswalkerRedirectionEffect.applies(event, null, game)) {
            replaceEffects.put(planeswalkerRedirectionEffect, null);
        }
        if (auraReplacementEffect.checksEventType(event, game) && auraReplacementEffect.applies(event, null, game)) {
            replaceEffects.put(auraReplacementEffect, null);
        }
        // boolean checkLKI = event.getType().equals(EventType.ZONE_CHANGE) || event.getType().equals(EventType.DESTROYED_PERMANENT);
        //get all applicable transient Replacement effects
        for (ReplacementEffect effect : replacementEffects) {
            if (!effect.checksEventType(event, game)) {
                continue;
            }
            if (event.getAppliedEffects() != null && event.getAppliedEffects().contains(effect.getId())) {
                // Effect already applied to this event, ignore it
                // TODO: Handle also gained effect that are connected to different abilities.
                continue;
            }
            HashSet<Ability> abilities = replacementEffects.getAbility(effect.getId());
            HashSet<Ability> applicableAbilities = new HashSet<>();
            for (Ability ability : abilities) {
                // for replacment effects of static abilities do not use LKI to check if to apply
                if (ability.getAbilityType() != AbilityType.STATIC || ability.isInUseableZone(game, null, event)) {
                    if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                        if (!game.getScopeRelevant() || effect.hasSelfScope() || !event.getTargetId().equals(ability.getSourceId())) {
                            if (effect.applies(event, ability, game)) {
                                applicableAbilities.add(ability);
                            }
                        }
                    }
                }
            }
            if (!applicableAbilities.isEmpty()) {
                replaceEffects.put(effect, applicableAbilities);
            }
        }
        for (PreventionEffect effect : preventionEffects) {
            if (!effect.checksEventType(event, game)) {
                continue;
            }
            if (event.getAppliedEffects() != null && event.getAppliedEffects().contains(effect.getId())) {
                // Effect already applied to this event, ignore it
                // TODO: Handle also gained effect that are connected to different abilities.
                continue;
            }
            HashSet<Ability> abilities = preventionEffects.getAbility(effect.getId());
            HashSet<Ability> applicableAbilities = new HashSet<>();
            for (Ability ability : abilities) {
                if (ability.getAbilityType() != AbilityType.STATIC || ability.isInUseableZone(game, null, event)) {
                    if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                        if (effect.applies(event, ability, game)) {
                            applicableAbilities.add(ability);
                        }
                    }
                }
            }
            if (!applicableAbilities.isEmpty()) {
                replaceEffects.put((ReplacementEffect) effect, applicableAbilities);
            }
        }
        return replaceEffects;
    }

    private boolean checkAbilityStillExists(Ability ability, ContinuousEffect effect, GameEvent event, Game game) {
        switch (effect.getDuration()) { // effects with fixed duration don't need an object with the source ability (e.g. a silence cast with isochronic Scepter has no more a card object
            case EndOfCombat:
            case EndOfGame:
            case EndOfStep:
            case EndOfTurn:
            case OneUse:
            case Custom:  // custom duration means the effect ends itself if needed
                return true;
        }
        if (ability.getSourceId() == null) { // commander replacement effect
            return true;
        }
        MageObject object;
        if (event.getType().equals(EventType.ZONE_CHANGE)
                && ((ZoneChangeEvent) event).getFromZone().equals(Zone.BATTLEFIELD)
                && event.getTargetId().equals(ability.getSourceId())) {
            object = ((ZoneChangeEvent) event).getTarget();
        } else {
            object = game.getObject(ability.getSourceId());
        }
        if (object == null) {
            return false;
        }
        boolean exists = true;
        if (!object.getAbilities().contains(ability)) {
            exists = false;
            if (object instanceof PermanentCard) {
                PermanentCard permanent = (PermanentCard) object;
                if (permanent.canTransform() && event.getType() == GameEvent.EventType.TRANSFORMED) {
                    exists = permanent.getCard().getAbilities().contains(ability);
                }
            }
        } else {
            if (object instanceof PermanentCard) {
                PermanentCard permanent = (PermanentCard) object;
                if (permanent.isFaceDown(game) && !ability.getWorksFaceDown()) {
                    return false;
                }
            } else if (object instanceof Spell) {
                Spell spell = (Spell) object;
                if (spell.isFaceDown(game) && !ability.getWorksFaceDown()) {
                    return false;
                }
            }
        }
        return exists;
    }

    /**
     * Filters out cost modification effects that are not active.
     *
     * @param game
     * @return
     */
    private List<CostModificationEffect> getApplicableCostModificationEffects(Game game) {
        List<CostModificationEffect> costEffects = new ArrayList<>();

        for (CostModificationEffect effect : costModificationEffects) {
            HashSet<Ability> abilities = costModificationEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, null)) {
                    if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                        costEffects.add(effect);
                        break;
                    }
                }
            }
        }

        return costEffects;
    }

    /**
     * Filters out splice effects that are not active.
     *
     * @param game
     * @return
     */
    private List<SpliceCardEffect> getApplicableSpliceCardEffects(Game game, UUID playerId) {
        List<SpliceCardEffect> spliceEffects = new ArrayList<>();

        for (SpliceCardEffect effect : spliceCardEffects) {
            HashSet<Ability> abilities = spliceCardEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (ability.getControllerId().equals(playerId) && (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, null))) {
                    if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                        spliceEffects.add(effect);
                        break;
                    }
                }
            }
        }

        return spliceEffects;
    }

    public boolean asThough(UUID objectId, AsThoughEffectType type, UUID controllerId, Game game) {
        return asThough(objectId, type, null, controllerId, game);
    }

    public boolean asThough(UUID objectId, AsThoughEffectType type, Ability affectedAbility, UUID controllerId, Game game) {
        List<AsThoughEffect> asThoughEffectsList = getApplicableAsThoughEffects(type, game);
        for (AsThoughEffect effect : asThoughEffectsList) {
            HashSet<Ability> abilities = asThoughEffectsMap.get(type).getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (affectedAbility == null) {
                    if (effect.applies(objectId, ability, controllerId, game)) {
                        return true;
                    }
                } else {
                    if (effect.applies(objectId, affectedAbility, ability, game)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * Filters out asThough effects that are not active.
     *
     * @param AsThoughEffectType type
     * @param game
     * @return
     */
    private List<AsThoughEffect> getApplicableAsThoughEffects(AsThoughEffectType type, Game game) {
        List<AsThoughEffect> asThoughEffectsList = new ArrayList<>();
        if (asThoughEffectsMap.containsKey(type)) {
            for (AsThoughEffect effect : asThoughEffectsMap.get(type)) {
                HashSet<Ability> abilities = asThoughEffectsMap.get(type).getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, null)) {
                        if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                            asThoughEffectsList.add(effect);
                            break;
                        }
                    }
                }
            }
        }
        return asThoughEffectsList;
    }

    /**
     * 601.2e The player determines the total cost of the spell. Usually this is
     * just the mana cost. Some spells have additional or alternative costs.
     * Some effects may increase or reduce the cost to pay, or may provide other
     * alternative costs. Costs may include paying mana, tapping permanents,
     * sacrificing permanents, discarding cards, and so on. The total cost is
     * the mana cost or alternative cost (as determined in rule 601.2b), plus
     * all additional costs and cost increases, and minus all cost reductions.
     * If the mana component of the total cost is reduced to nothing by cost
     * reduction effects, it is considered to be {0}. It can’t be reduced to
     * less than {0}. Once the total cost is determined, any effects that
     * directly affect the total cost are applied. Then the resulting total cost
     * becomes “locked in.” If effects would change the total cost after this
     * time, they have no effect.
     */
    /**
     * Inspects all {@link Permanent permanent's} {@link Ability abilities} on
     * the battlefield for
     * {@link CostModificationEffect cost modification effects} and applies them
     * if necessary.
     *
     * @param abilityToModify
     * @param game
     */
    public void costModification(Ability abilityToModify, Game game) {
        List<CostModificationEffect> costEffects = getApplicableCostModificationEffects(game);

        for (CostModificationEffect effect : costEffects) {
            if (effect.getModificationType() == CostModificationType.INCREASE_COST) {
                HashSet<Ability> abilities = costModificationEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (effect.applies(abilityToModify, ability, game)) {
                        effect.apply(game, ability, abilityToModify);
                    }
                }
            }
        }

        for (CostModificationEffect effect : costEffects) {
            if (effect.getModificationType() == CostModificationType.REDUCE_COST) {
                HashSet<Ability> abilities = costModificationEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (effect.applies(abilityToModify, ability, game)) {
                        effect.apply(game, ability, abilityToModify);
                    }
                }
            }
        }

        for (CostModificationEffect effect : costEffects) {
            if (effect.getModificationType() == CostModificationType.SET_COST) {
                HashSet<Ability> abilities = costModificationEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (effect.applies(abilityToModify, ability, game)) {
                        effect.apply(game, ability, abilityToModify);
                    }
                }
            }
        }
    }

    /**
     * Checks all available splice effects to be applied.
     *
     * @param abilityToModify
     * @param game
     */
    public void applySpliceEffects(Ability abilityToModify, Game game) {
        if (((SpellAbility) abilityToModify).getSpellAbilityType().equals(SpellAbilityType.SPLICE)) {
            // on a spliced ability of a spell can't be spliced again
            return;
        }
        List<SpliceCardEffect> spliceEffects = getApplicableSpliceCardEffects(game, abilityToModify.getControllerId());
        // get the applyable splice abilities
        List<SpliceOntoArcaneAbility> spliceAbilities = new ArrayList<>();
        for (SpliceCardEffect effect : spliceEffects) {
            HashSet<Ability> abilities = spliceCardEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (effect.applies(abilityToModify, ability, game)) {
                    spliceAbilities.add((SpliceOntoArcaneAbility) ability);
                }
            }
        }
        // check if player wants to use splice

        if (spliceAbilities.size() > 0) {
            Player controller = game.getPlayer(abilityToModify.getControllerId());
            if (controller.chooseUse(Outcome.Benefit, "Splice a card?", abilityToModify, game)) {
                Cards cardsToReveal = new CardsImpl();
                do {
                    FilterCard filter = new FilterCard("a card to splice");
                    ArrayList<Predicate<MageObject>> idPredicates = new ArrayList<>();
                    for (SpliceOntoArcaneAbility ability : spliceAbilities) {
                        idPredicates.add(new CardIdPredicate((ability.getSourceId())));
                    }
                    filter.add(Predicates.or(idPredicates));
                    TargetCardInHand target = new TargetCardInHand(filter);
                    controller.chooseTarget(Outcome.Benefit, target, abilityToModify, game);
                    UUID cardId = target.getFirstTarget();
                    if (cardId != null) {
                        SpliceOntoArcaneAbility selectedAbility = null;
                        for (SpliceOntoArcaneAbility ability : spliceAbilities) {
                            if (ability.getSourceId().equals(cardId)) {
                                selectedAbility = ability;
                                break;
                            }
                        }
                        if (selectedAbility != null) {
                            SpliceCardEffect spliceEffect = (SpliceCardEffect) selectedAbility.getEffects().get(0);
                            spliceEffect.apply(game, selectedAbility, abilityToModify);
                            cardsToReveal.add(game.getCard(cardId));
                            spliceAbilities.remove(selectedAbility);
                        }
                    }
                } while (!spliceAbilities.isEmpty() && controller.chooseUse(Outcome.Benefit, "Splice another card?", abilityToModify, game));
                controller.revealCards("Spliced cards", cardsToReveal, game);
            }
        }
    }

    /**
     * Checks if an event won't happen because of an rule modifying effect
     *
     * @param event
     * @param targetAbility ability the event is attached to. can be null.
     * @param game
     * @param checkPlayableMode true if the event does not really happen but
     * it's checked if the event would be replaced
     * @return
     */
    public boolean preventedByRuleModification(GameEvent event, Ability targetAbility, Game game, boolean checkPlayableMode) {
        for (ContinuousRuleModifyingEffect effect : continuousRuleModifyingEffects) {
            if (!effect.checksEventType(event, game)) {
                continue;
            }
            for (Ability sourceAbility : continuousRuleModifyingEffects.getAbility(effect.getId())) {
                if (!(sourceAbility instanceof StaticAbility) || sourceAbility.isInUseableZone(game, null, event)) {
                    if (checkAbilityStillExists(sourceAbility, effect, event, game)) {
                        if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                            effect.setValue("targetAbility", targetAbility);
                            if (effect.applies(event, sourceAbility, game)) {
                                if (!checkPlayableMode) {
                                    String message = effect.getInfoMessage(sourceAbility, event, game);
                                    if (message != null && !message.isEmpty()) {
                                        if (effect.sendMessageToUser()) {
                                            Player player = game.getPlayer(event.getPlayerId());
                                            if (player != null && !game.isSimulation()) {
                                                game.informPlayer(player, message);
                                            }
                                        }
                                        if (effect.sendMessageToGameLog() && !game.isSimulation()) {
                                            game.informPlayers(message);
                                        }
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean replaceEvent(GameEvent event, Game game) {
        boolean caught = false;
        HashMap<UUID, HashSet<UUID>> consumed = new HashMap<>();
        do {
            HashMap<ReplacementEffect, HashSet<Ability>> rEffects = getApplicableReplacementEffects(event, game);
            // Remove all consumed effects (ability dependant)
            for (Iterator<ReplacementEffect> it1 = rEffects.keySet().iterator(); it1.hasNext();) {
                ReplacementEffect entry = it1.next();
                if (consumed.containsKey(entry.getId())) {
                    HashSet<UUID> consumedAbilitiesIds = consumed.get(entry.getId());
                    if (rEffects.get(entry) == null || consumedAbilitiesIds.size() == rEffects.get(entry).size()) {
                        it1.remove();
                    } else {
                        Iterator it = rEffects.get(entry).iterator();
                        while (it.hasNext()) {
                            Ability ability = (Ability) it.next();
                            if (consumedAbilitiesIds.contains(ability.getId())) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            // no effects left, quit
            if (rEffects.isEmpty()) {
                break;
            }
            int index;
            boolean onlyOne = false;
            if (rEffects.size() == 1) {
                ReplacementEffect effect = rEffects.keySet().iterator().next();
                HashSet<Ability> abilities;
                if (effect.getEffectType().equals(EffectType.REPLACEMENT)) {
                    abilities = replacementEffects.getAbility(effect.getId());
                } else {
                    abilities = preventionEffects.getAbility(effect.getId());
                }
                if (abilities == null || abilities.size() == 1) {
                    onlyOne = true;
                }
            }
            if (onlyOne) {
                index = 0;
            } else {
                //20100716 - 616.1c
                Player player = game.getPlayer(event.getPlayerId());
                index = player.chooseReplacementEffect(getReplacementEffectsTexts(rEffects, game), game);
            }
            // get the selected effect
            int checked = 0;
            ReplacementEffect rEffect = null;
            Ability rAbility = null;
            for (Map.Entry<ReplacementEffect, HashSet<Ability>> entry : rEffects.entrySet()) {
                if (entry.getValue() == null) {
                    if (checked == index) {
                        rEffect = entry.getKey();
                        break;
                    } else {
                        checked++;
                    }
                } else {
                    HashSet<Ability> abilities = entry.getValue();
                    int size = abilities.size();
                    if (index > (checked + size - 1)) {
                        checked += size;
                    } else {
                        rEffect = entry.getKey();
                        Iterator it = abilities.iterator();
                        while (it.hasNext() && rAbility == null) {
                            if (checked == index) {
                                rAbility = (Ability) it.next();
                            } else {
                                it.next();
                                checked++;
                            }
                        }
                        break;
                    }
                }
            }

            if (rEffect != null) {
                event.getAppliedEffects().add(rEffect.getId());
                caught = rEffect.replaceEvent(event, rAbility, game);
            }
            if (caught) { // Event was completely replaced -> stop applying effects to it
                break;
            }

            // add the applied effect to the consumed effects
            if (rEffect != null) {
                if (consumed.containsKey(rEffect.getId())) {
                    HashSet<UUID> set = consumed.get(rEffect.getId());
                    if (rAbility != null) {
                        if (!set.contains(rAbility.getId())) {
                            set.add(rAbility.getId());
                        }
                    }
                } else {
                    HashSet<UUID> set = new HashSet<>();
                    if (rAbility != null) { // in case of AuraReplacementEffect or PlaneswalkerReplacementEffect there is no Ability
                        set.add(rAbility.getId());
                    }
                    consumed.put(rEffect.getId(), set);
                }
            }
            // Must be called here for some effects to be able to work correctly
            // TODO: add info which effects need that call
            game.applyEffects();
        } while (true);
        return caught;
    }

    //20091005 - 613
    public void apply(Game game) {
        removeInactiveEffects(game);
        List<ContinuousEffect> activeLayerEffects = getLayeredEffects(game);

        List<ContinuousEffect> layer = filterLayeredEffects(activeLayerEffects, Layer.CopyEffects_1);
        for (ContinuousEffect effect : layer) {
            HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.CopyEffects_1, SubLayer.NA, ability, game);
            }
        }
        //Reload layerEffect if copy effects were applied
        if (layer.size() > 0) {
            activeLayerEffects = getLayeredEffects(game);
        }

        layer = filterLayeredEffects(activeLayerEffects, Layer.ControlChangingEffects_2);
        // apply control changing effects multiple times if it's needed
        // for cases when control over permanents with change control abilities is changed
        // e.g. Mind Control is controlled by Steal Enchantment
        while (true) {
            for (ContinuousEffect effect : layer) {
                HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    effect.apply(Layer.ControlChangingEffects_2, SubLayer.NA, ability, game);
                }
            }
            // if control over all permanent has not changed, we can no longer reapply control changing effects
            if (!game.getBattlefield().fireControlChangeEvents(game)) {
                break;
            }
            // reset control before reapplying control changing effects
            game.getBattlefield().resetPermanentsControl();
        }

        applyLayer(activeLayerEffects, Layer.TextChangingEffects_3, game);
        applyLayer(activeLayerEffects, Layer.TypeChangingEffects_4, game);
        applyLayer(activeLayerEffects, Layer.ColorChangingEffects_5, game);

        Map<ContinuousEffect, List<Ability>> appliedEffectAbilities = new HashMap<>();
        boolean done = false;
        Map<ContinuousEffect, Set<UUID>> waitingEffects = new LinkedHashMap<>();
        Set<UUID> appliedEffects = new HashSet<>();
        while (!done) { // loop needed if a added effect adds again an effect (e.g. Level 5- of Joraga Treespeaker)
            done = true;
            layer = filterLayeredEffects(activeLayerEffects, Layer.AbilityAddingRemovingEffects_6);
            for (ContinuousEffect effect : layer) {
                if (activeLayerEffects.contains(effect) && !appliedEffects.contains(effect.getId())) { // Effect does still exist and was not applied yet
                    Set<UUID> dependentTo = effect.isDependentTo(layer);
                    if (dependentTo != null && !appliedEffects.containsAll(dependentTo)) {
                        waitingEffects.put(effect, dependentTo);
                        continue;
                    }
                    List<Ability> appliedAbilities = appliedEffectAbilities.get(effect);
                    HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
                    for (Ability ability : abilities) {
                        if (appliedAbilities == null || !appliedAbilities.contains(ability)) {
                            if (appliedAbilities == null) {
                                appliedAbilities = new ArrayList<>();
                                appliedEffectAbilities.put(effect, appliedAbilities);
                            }
                            appliedAbilities.add(ability);
                            effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, ability, game);
                            done = false;
                            // list must be updated after each applied effect (eg. if "Turn to Frog" removes abilities)
                            activeLayerEffects = getLayeredEffects(game);
                        }
                    }
                    appliedEffects.add(effect.getId());

                    if (!waitingEffects.isEmpty()) {
                        // check if waiting effects can be applied now
                        for (Iterator<Map.Entry<ContinuousEffect, Set<UUID>>> iterator = waitingEffects.entrySet().iterator(); iterator.hasNext();) {
                            Map.Entry<ContinuousEffect, Set<UUID>> entry = iterator.next();
                            if (appliedEffects.containsAll(entry.getValue())) { // all dependent to effects are applied now so apply the effect itself
                                appliedAbilities = appliedEffectAbilities.get(entry.getKey());
                                abilities = layeredEffects.getAbility(entry.getKey().getId());
                                for (Ability ability : abilities) {
                                    if (appliedAbilities == null || !appliedAbilities.contains(ability)) {
                                        if (appliedAbilities == null) {
                                            appliedAbilities = new ArrayList<>();
                                            appliedEffectAbilities.put(entry.getKey(), appliedAbilities);
                                        }
                                        appliedAbilities.add(ability);
                                        entry.getKey().apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, ability, game);
                                        done = false;
                                        // list must be updated after each applied effect (eg. if "Turn to Frog" removes abilities)
                                        activeLayerEffects = getLayeredEffects(game);
                                    }
                                }
                                appliedEffects.add(entry.getKey().getId());
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        }

        layer = filterLayeredEffects(activeLayerEffects, Layer.PTChangingEffects_7);
        for (ContinuousEffect effect : layer) {
            HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PTChangingEffects_7, SubLayer.SetPT_7b, ability, game);
            }
        }
        for (ContinuousEffect effect : layer) {
            HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, ability, game);
            }
        }

        applyCounters.apply(Layer.PTChangingEffects_7, SubLayer.Counters_7d, null, game);

        for (ContinuousEffect effect : layer) {
            HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, ability, game);
            }
        }
        layer = filterLayeredEffects(activeLayerEffects, Layer.PlayerEffects);
        for (ContinuousEffect effect : layer) {
            HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PlayerEffects, SubLayer.NA, ability, game);
            }
        }
        layer = filterLayeredEffects(activeLayerEffects, Layer.RulesEffects);
        for (ContinuousEffect effect : layer) {
            HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.RulesEffects, SubLayer.NA, ability, game);
            }
        }
    }

    private void applyLayer(List<ContinuousEffect> activeLayerEffects, Layer currentLayer, Game game) {
        List<ContinuousEffect> layer = filterLayeredEffects(activeLayerEffects, currentLayer);
        if (!layer.isEmpty()) {
            int numberOfEffects = layer.size();
            Set<UUID> appliedEffects = new HashSet<>();
            Map<ContinuousEffect, Set<UUID>> waitingEffects = new LinkedHashMap<>();
            for (ContinuousEffect effect : layer) {
                if (numberOfEffects > 1) { // If an effect is dependent to not applied effects yet of this layer, so wait to apply this effect
                    Set<UUID> dependentTo = effect.isDependentTo(layer);
                    if (dependentTo != null && !appliedEffects.containsAll(dependentTo)) {
                        waitingEffects.put(effect, dependentTo);
                        continue;
                    }
                }
                applyContinuousEffect(effect, currentLayer, game);
                appliedEffects.add(effect.getId());
                if (!waitingEffects.isEmpty()) {
                    // check if waiting effects can be applied now
                    for (Entry<ContinuousEffect, Set<UUID>> entry : waitingEffects.entrySet()) {
                        if (appliedEffects.containsAll(entry.getValue())) { // all dependent to effects are applied now so apply the effect itself
                            applyContinuousEffect(entry.getKey(), currentLayer, game);
                            appliedEffects.add(entry.getKey().getId());
                        }
                    }
                }
            }
        }
    }

    private void applyContinuousEffect(ContinuousEffect effect, Layer currentLayer, Game game) {
        HashSet<Ability> abilities = layeredEffects.getAbility(effect.getId());
        for (Ability ability : abilities) {
            effect.apply(currentLayer, SubLayer.NA, ability, game);
        }
    }

    /**
     * Adds a continuous ability with a reference to a sourceId. It's used for
     * effects that cease to exist again So this effects were removed again
     * before each applyEffecs
     *
     * @param effect
     * @param sourceId
     * @param source
     */
    public void addEffect(ContinuousEffect effect, UUID sourceId, Ability source) {
        if (!(source instanceof MageSingleton)) { // because MageSingletons may never be removed by removing the temporary effecs they are not added to the temporaryEffects to prevent this
            effect.setTemporary(true);
            Set abilities = temporaryEffects.get(effect);
            if (abilities == null) {
                abilities = new HashSet<>();
                temporaryEffects.put(effect, abilities);
            } else {
                if (abilities.contains(source)) {
                    // this ability (for the continuous effect) is already added
                    return;
                }
            }
            abilities.add(source);

            // add the effect itself
        }
        addEffect(effect, source);
    }

    public void addEffect(ContinuousEffect effect, Ability source) {
        if (effect == null) {
            logger.error("Effect is null: " + source.toString());
            return;
        } else if (source == null) {
            logger.warn("Adding effect without ability : " + effect.toString());
        }
        switch (effect.getEffectType()) {
            case REPLACEMENT:
            case REDIRECTION:
                ReplacementEffect newReplacementEffect = (ReplacementEffect) effect;
                replacementEffects.addEffect(newReplacementEffect, source);
                break;
            case PREVENTION:
                PreventionEffect newPreventionEffect = (PreventionEffect) effect;
                preventionEffects.addEffect(newPreventionEffect, source);
                break;
            case RESTRICTION:
                RestrictionEffect newRestrictionEffect = (RestrictionEffect) effect;
                restrictionEffects.addEffect(newRestrictionEffect, source);
                break;
            case RESTRICTION_UNTAP_NOT_MORE_THAN:
                RestrictionUntapNotMoreThanEffect newRestrictionUntapNotMoreThanEffect = (RestrictionUntapNotMoreThanEffect) effect;
                restrictionUntapNotMoreThanEffects.addEffect(newRestrictionUntapNotMoreThanEffect, source);
                break;
            case REQUIREMENT:
                RequirementEffect newRequirementEffect = (RequirementEffect) effect;
                requirementEffects.addEffect(newRequirementEffect, source);
                break;
            case ASTHOUGH:
                AsThoughEffect newAsThoughEffect = (AsThoughEffect) effect;
                if (!asThoughEffectsMap.containsKey(newAsThoughEffect.getAsThoughEffectType())) {
                    ContinuousEffectsList<AsThoughEffect> list = new ContinuousEffectsList<>();
                    allEffectsLists.add(list);
                    asThoughEffectsMap.put(newAsThoughEffect.getAsThoughEffectType(), list);
                }
                asThoughEffectsMap.get(newAsThoughEffect.getAsThoughEffectType()).addEffect(newAsThoughEffect, source);
                break;
            case COSTMODIFICATION:
                CostModificationEffect newCostModificationEffect = (CostModificationEffect) effect;
                costModificationEffects.addEffect(newCostModificationEffect, source);
                break;
            case SPLICE:
                SpliceCardEffect newSpliceCardEffect = (SpliceCardEffect) effect;
                spliceCardEffects.addEffect(newSpliceCardEffect, source);
                break;
            case CONTINUOUS_RULE_MODIFICATION:
                ContinuousRuleModifyingEffect newContinuousRuleModifiyingEffect = (ContinuousRuleModifyingEffect) effect;
                continuousRuleModifyingEffects.addEffect(newContinuousRuleModifiyingEffect, source);
                break;
            default:
                layeredEffects.addEffect(effect, source);
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
            HashSet<Ability> abilities = effects.getAbility(effect.getId());
            if (abilities != null) {
                for (Ability ability : abilities) {
                    if (ability.getSourceId() != null) {
                        if (ability.getSourceId().equals(cardId)) {
                            ability.setControllerId(controllerId);
                        }
                    } else {
                        if (!ability.getZone().equals(Zone.COMMAND)) {
                            logger.fatal(new StringBuilder("No sourceId Ability: ").append(ability));
                        }
                    }
                }
            }
        }
    }

    public void clear() {
        for (ContinuousEffectsList effectsList : allEffectsLists) {
            effectsList.clear();
        }
        temporaryEffects.clear();
    }

    public void removeAllTemporaryEffects() {
        for (Map.Entry<ContinuousEffect, Set<Ability>> entry : temporaryEffects.entrySet()) {
            switch (entry.getKey().getEffectType()) {
                case REPLACEMENT:
                case REDIRECTION:
                    replacementEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case PREVENTION:
                    preventionEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case RESTRICTION:
                    restrictionEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case RESTRICTION_UNTAP_NOT_MORE_THAN:
                    restrictionUntapNotMoreThanEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case REQUIREMENT:
                    requirementEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case ASTHOUGH:
                    AsThoughEffect newAsThoughEffect = (AsThoughEffect) entry.getKey();
                    if (!asThoughEffectsMap.containsKey(newAsThoughEffect.getAsThoughEffectType())) {
                        break;
                    }
                    asThoughEffectsMap.get(newAsThoughEffect.getAsThoughEffectType()).removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case COSTMODIFICATION:
                    costModificationEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case SPLICE:
                    spliceCardEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                case CONTINUOUS_RULE_MODIFICATION:
                    continuousRuleModifyingEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
                default:
                    layeredEffects.removeEffects(entry.getKey().getId(), entry.getValue());
                    break;
            }

        }
        temporaryEffects.clear();
    }

    public Map<String, String> getReplacementEffectsTexts(HashMap<ReplacementEffect, HashSet<Ability>> rEffects, Game game) {
        Map<String, String> texts = new LinkedHashMap<>();
        for (Map.Entry<ReplacementEffect, HashSet<Ability>> entry : rEffects.entrySet()) {
            if (entry.getValue() != null) {
                for (Ability ability : entry.getValue()) {
                    MageObject object = game.getObject(ability.getSourceId());
                    if (object != null) {
                        texts.put(ability.getId().toString() + "_" + entry.getKey().getId().toString(), object.getName() + ": " + ability.getRule(object.getName()));
                    } else {
                        texts.put(ability.getId().toString() + "_" + entry.getKey().getId().toString(), entry.getKey().getText(null));
                    }
                }
            } else {
                logger.error("Replacement effect without ability: " + entry.getKey().toString());
            }
        }
        return texts;
    }

    public boolean existRequirementEffects() {
        return !requirementEffects.isEmpty();
    }

    public UUID getControllerOfSourceId(UUID sourceId) {
        UUID controllerFound = null;
        for (PreventionEffect effect : preventionEffects) {
            HashSet<Ability> abilities = preventionEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (ability.getSourceId().equals(sourceId)) {
                    if (controllerFound == null || controllerFound == ability.getControllerId()) {
                        controllerFound = ability.getControllerId();
                    } else {
                        // not unique controller - No solution yet
                        return null;
                    }
                }
            }
        }
        for (ReplacementEffect effect : replacementEffects) {
            HashSet<Ability> abilities = replacementEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (ability.getSourceId() != null) {
                    if (ability.getSourceId().equals(sourceId)) {
                        if (controllerFound == null || controllerFound == ability.getControllerId()) {
                            controllerFound = ability.getControllerId();
                        } else {
                            // not unique controller - No solution yet
                            return null;
                        }
                    }
                } else {
                    logger.warn("Ability without sourceId:" + ability.getRule());
                }
            }
        }
        return controllerFound;
    }
}

class ContinuousEffectSorter implements Comparator<ContinuousEffect>, Serializable {

    @Override
    public int compare(ContinuousEffect one, ContinuousEffect two) {
        return Long.compare(one.getOrder(), two.getOrder());
    }
}
