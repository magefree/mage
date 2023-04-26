package mage.abilities.effects;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.CommanderReplacementEffect;
import mage.cards.*;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;
import mage.util.trace.TraceInfo;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffects implements Serializable {

    private static final Logger logger = Logger.getLogger(ContinuousEffects.class);

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
    public final List<ContinuousEffectsList<?>> allEffectsLists = new ArrayList<>(); // contains refs to real effect's list
    private final ApplyCountersEffect applyCounters;
    private final AuraReplacementEffect auraReplacementEffect;

    private final Map<String, ContinuousEffectsList<ContinuousEffect>> lastEffectsListOnLayer = new HashMap<>(); // helps to find out new effect timestamps on layers

    public ContinuousEffects() {
        applyCounters = new ApplyCountersEffect();
        auraReplacementEffect = new AuraReplacementEffect();
        collectAllEffects();
    }

    public ContinuousEffects(final ContinuousEffects effect) {
        applyCounters = effect.applyCounters.copy();
        auraReplacementEffect = effect.auraReplacementEffect.copy();
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
        for (Map.Entry<String, ContinuousEffectsList<ContinuousEffect>> entry : effect.lastEffectsListOnLayer.entrySet()) {
            lastEffectsListOnLayer.put(entry.getKey(), entry.getValue().copy());
        }
        collectAllEffects();
        order = effect.order;
    }

    private synchronized void collectAllEffects() {
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

    public synchronized void removeEndOfCombatEffects() {
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

    public synchronized void removeEndOfTurnEffects(Game game) {
        layeredEffects.removeEndOfTurnEffects(game);
        continuousRuleModifyingEffects.removeEndOfTurnEffects(game);
        replacementEffects.removeEndOfTurnEffects(game);
        preventionEffects.removeEndOfTurnEffects(game);
        requirementEffects.removeEndOfTurnEffects(game);
        restrictionEffects.removeEndOfTurnEffects(game);
        for (ContinuousEffectsList asThoughtlist : asThoughEffectsMap.values()) {
            asThoughtlist.removeEndOfTurnEffects(game);
        }
        costModificationEffects.removeEndOfTurnEffects(game);
        spliceCardEffects.removeEndOfTurnEffects(game);
    }

    public synchronized void removeInactiveEffects(Game game) {
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

    public synchronized List<ContinuousEffect> getLayeredEffects(Game game) {
        return getLayeredEffects(game, "main");
    }

    /**
     * Return effects list ordered by timestamps (timestamps are automaticity
     * generates from new/old lists on same layer)
     *
     * @param game
     * @param timestampGroupName workaround to fix broken timestamps on effect's
     *                           add/remove between different layers
     * @return effects list ordered by timestamp
     */
    public synchronized List<ContinuousEffect> getLayeredEffects(Game game, String timestampGroupName) {
        List<ContinuousEffect> layerEffects = new ArrayList<>();
        for (ContinuousEffect effect : layeredEffects) {
            switch (effect.getDuration()) {
                case WhileOnBattlefield:
                case WhileControlled:
                case WhileOnStack:
                case WhileInGraveyard:
                    Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
                    if (!abilities.isEmpty()) {
                        for (Ability ability : abilities) {
                            // If e.g. triggerd abilities (non static) created the effect, the ability must not be in usable zone (e.g. Unearth giving Haste effect)
                            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, null)) {
                                layerEffects.add(effect);
                                break;
                            }
                        }
                    } else {
                        logger.error("No abilities for continuous effect: " + effect);
                    }
                    break;
                default:
                    layerEffects.add(effect);
            }
        }

        updateTimestamps(timestampGroupName, layerEffects);
        layerEffects.sort(Comparator.comparingLong(ContinuousEffect::getOrder));
        /* debug effects apply order:
        if (game.getStep() != null) System.out.println("layr - " + game.getTurnNum() + "." + game.getTurnStepType() + ": layers " + layerEffects.size()
                + " - " + layerEffects.stream().map(l -> l.getClass().getSimpleName()).collect(Collectors.joining(", "))
                + " - " + callName);
        //*/

        return layerEffects;
    }

    /**
     * Initially effect timestamp is set when game starts in game.loadCard
     * method. After that timestamp should be updated whenever effect becomes
     * "actual" meaning it becomes turned on that is defined by
     * Ability.#isInUseableZone(Game, boolean) method in
     * #getLayeredEffects(Game).
     * <p>
     * It must be called with different timestamp group name (otherwise sort
     * order will be changed for add/remove effects, see Urborg and Bloodmoon
     * test)
     *
     * @param layerEffects
     */
    private synchronized void updateTimestamps(String timestampGroupName, List<ContinuousEffect> layerEffects) {
        if (!lastEffectsListOnLayer.containsKey(timestampGroupName)) {
            lastEffectsListOnLayer.put(timestampGroupName, new ContinuousEffectsList<>());
        }
        ContinuousEffectsList<ContinuousEffect> prevs = lastEffectsListOnLayer.get(timestampGroupName);
        for (ContinuousEffect continuousEffect : layerEffects) {
            // check if it's new, then set order
            if (!prevs.contains(continuousEffect)) {
                setOrder(continuousEffect);
            }
        }
        prevs.clear();
        prevs.addAll(layerEffects);
    }

    public void setOrder(ContinuousEffect effect) {
        effect.setOrder(order++);
    }

    private List<ContinuousEffect> filterLayeredEffects(List<ContinuousEffect> effects, Layer layer) {
        return effects.stream()
                .filter(effect -> effect.hasLayer(layer))
                .collect(Collectors.toList());
    }

    public Map<RequirementEffect, Set<Ability>> getApplicableRequirementEffects(Permanent permanent, boolean playerRealted, Game game) {
        Map<RequirementEffect, Set<Ability>> effects = new HashMap<>();
        for (RequirementEffect effect : requirementEffects) {
            if (playerRealted == effect.isPlayerRelated()) {
                Set<Ability> abilities = requirementEffects.getAbility(effect.getId());
                Set<Ability> applicableAbilities = new HashSet<>();
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
        }
        return effects;
    }

    public Map<RestrictionEffect, Set<Ability>> getApplicableRestrictionEffects(Permanent permanent, Game game) {
        Map<RestrictionEffect, Set<Ability>> effects = new HashMap<>();
        for (RestrictionEffect effect : restrictionEffects) {
            Set<Ability> abilities = restrictionEffects.getAbility(effect.getId());
            Set<Ability> applicableAbilities = new HashSet<>();
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

    public Map<RestrictionUntapNotMoreThanEffect, Set<Ability>> getApplicableRestrictionUntapNotMoreThanEffects(Player player, Game game) {
        Map<RestrictionUntapNotMoreThanEffect, Set<Ability>> effects = new HashMap<>();
        for (RestrictionUntapNotMoreThanEffect effect : restrictionUntapNotMoreThanEffects) {
            Set<Ability> abilities = restrictionUntapNotMoreThanEffects.getAbility(effect.getId());
            Set<Ability> applicableAbilities = new HashSet<>();
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
                Set<Ability> abilities = replacementEffects.getAbility(effect.getId());
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
     * @param event
     * @param game
     * @return a list of all {@link ReplacementEffect} that apply to the current
     * event
     */
    private Map<ReplacementEffect, Set<Ability>> getApplicableReplacementEffects(GameEvent event, Game game) {
        Map<ReplacementEffect, Set<Ability>> replaceEffects = new LinkedHashMap<>();
        if (auraReplacementEffect.checksEventType(event, game) && auraReplacementEffect.applies(event, null, game)) {
            replaceEffects.put(auraReplacementEffect, null);
        }
        // boolean checkLKI = event.getType().equals(EventType.ZONE_CHANGE) || event.getType().equals(EventType.DESTROYED_PERMANENT);
        //get all applicable transient Replacement effects
        for (Iterator<ReplacementEffect> iterator = replacementEffects.iterator(); iterator.hasNext(); ) {
            ReplacementEffect effect = iterator.next();
            if (!effect.checksEventType(event, game)) {
                continue;
            }
            if (event.getAppliedEffects() != null && event.getAppliedEffects().contains(effect.getId())) {
                if (!(effect instanceof CommanderReplacementEffect)) { // 903.9.
                    // Effect already applied to this event, ignore it
                    // TODO: Handle also gained effect that are connected to different abilities.
                    continue;
                }
            }
            Set<Ability> abilities = replacementEffects.getAbility(effect.getId());
            Set<Ability> applicableAbilities = new HashSet<>();
            for (Ability ability : abilities) {
                // for replacment effects of static abilities do not use LKI to check if to apply
                if (ability.getAbilityType() != AbilityType.STATIC || ability.isInUseableZone(game, null, event)) {
                    if (!effect.isUsed()) {
                        if (!game.getScopeRelevant()
                                || effect.hasSelfScope()
                                || !event.getTargetId().equals(ability.getSourceId())) {
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

        for (Iterator<PreventionEffect> iterator = preventionEffects.iterator(); iterator.hasNext(); ) {
            PreventionEffect effect = iterator.next();
            if (!effect.checksEventType(event, game)) {
                continue;
            }
            if (event.getAppliedEffects() != null && event.getAppliedEffects().contains(effect.getId())) {
                // Effect already applied to this event, ignore it
                // TODO: Handle also gained effect that are connected to different abilities.
                continue;
            }
            Set<Ability> abilities = preventionEffects.getAbility(effect.getId());
            Set<Ability> applicableAbilities = new HashSet<>();
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
                replaceEffects.put(effect, applicableAbilities);
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
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && event.getTargetId().equals(ability.getSourceId())) {
            object = ((ZoneChangeEvent) event).getTarget();
        } else {
            object = game.getObject(ability.getSourceId());
        }
        if (object == null) {
            return false;
        }
        boolean exists = true;
        if (!object.hasAbility(ability, game)) {
            exists = false;
            if (object instanceof PermanentCard) {
                PermanentCard permanent = (PermanentCard) object;
                if (permanent.isTransformable() && event.getType() == GameEvent.EventType.TRANSFORMED) {
                    exists = permanent.getCard().hasAbility(ability, game);
                }
            }
        } else if (object instanceof PermanentCard) {
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
            Set<Ability> abilities = costModificationEffects.getAbility(effect.getId());
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
            Set<Ability> abilities = spliceCardEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (ability.isControlledBy(playerId) && (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, null, null))) {
                    if (effect.getDuration() != Duration.OneUse || !effect.isUsed()) {
                        spliceEffects.add(effect);
                        break;
                    }
                }
            }
        }

        return spliceEffects;
    }

    /**
     * @param objectId        object to check
     * @param type
     * @param affectedAbility null if check full object or ability if check only one ability from that object
     * @param controllerId
     * @param game
     * @return sourceId of the permitting effect if any exists otherwise returns null
     */
    public ApprovingObject asThough(UUID objectId, AsThoughEffectType type, Ability affectedAbility, UUID controllerId, Game game) {

        // usage check: effect must apply for specific ability only, not to full object (example: PLAY_FROM_NOT_OWN_HAND_ZONE)
        if (type.needAffectedAbility() && affectedAbility == null) {
            throw new IllegalArgumentException("ERROR, you can't call asThough check to whole object, call it with affected ability instead: " + type);
        }

        // usage check: effect must apply to full object, not specific ability (example: ATTACK_AS_HASTE)
        // P.S. In theory a same AsThough effect can be applied to object or to ability, so if you really, really
        // need it then disable that check or add extra param to AsThoughEffectType like needAffectedAbilityOrFullObject
        if (!type.needAffectedAbility() && affectedAbility != null) {
            throw new IllegalArgumentException("ERROR, you can't call AsThough check to affected ability, call it with empty affected ability instead: " + type);
        }

        List<AsThoughEffect> asThoughEffectsList = getApplicableAsThoughEffects(type, game);
        if (!asThoughEffectsList.isEmpty()) {
            MageObject objectToCheck;
            if (affectedAbility != null) {
                objectToCheck = affectedAbility.getSourceObject(game);
            } else {
                objectToCheck = game.getCard(objectId);
            }

            UUID idToCheck;
            if (!type.needPlayCardAbility() && objectToCheck instanceof SplitCardHalf) {
                // each split side uses own characteristics to check for playing, all other cases must use main card
                // rules:
                // 708.4. In every zone except the stack, the characteristics of a split card are those of its two halves combined.
                idToCheck = ((SplitCardHalf) objectToCheck).getMainCard().getId();
            } else if (!type.needPlayCardAbility() && objectToCheck instanceof AdventureCardSpell) {
                // adventure spell uses alternative characteristics for spell/stack, all other cases must use main card
                idToCheck = ((AdventureCardSpell) objectToCheck).getMainCard().getId();
            } else if (!type.needPlayCardAbility() && objectToCheck instanceof ModalDoubleFacesCardHalf) {
                // each mdf side uses own characteristics to check for playing, all other cases must use main card
                // rules:
                // "If an effect allows you to play a land or cast a spell from among a group of cards,
                // you may play or cast a modal double-faced card with any face that fits the criteria
                // of that effect. For example, if Sejiri Shelter / Sejiri Glacier is in your graveyard
                // and an effect allows you to play lands from your graveyard, you could play Sejiri Glacier.
                // That effect doesn't allow you to cast Sejiri Shelter."
                idToCheck = ((ModalDoubleFacesCardHalf) objectToCheck).getMainCard().getId();
            } else {
                idToCheck = objectId;
            }

            Set<ApprovingObject> possibleApprovingObjects = new HashSet<>();
            for (AsThoughEffect effect : asThoughEffectsList) {
                Set<Ability> abilities = asThoughEffectsMap.get(type).getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (affectedAbility == null) {
                        // applies to full object (one effect can be used in multiple abilities)
                        if (effect.applies(idToCheck, ability, controllerId, game)) {
                            if (effect.isConsumable() && !game.inCheckPlayableState()) {
                                possibleApprovingObjects.add(new ApprovingObject(ability, game));
                            } else {
                                return new ApprovingObject(ability, game);
                            }
                        }
                    } else {
                        // applies to one affected ability

                        // filter play abilities (no need to check it in every effect's code)
                        if (type.needPlayCardAbility() && !affectedAbility.getAbilityType().isPlayCardAbility()) {
                            continue;
                        }

                        if (effect.applies(idToCheck, affectedAbility, ability, game, controllerId)) {
                            if (effect.isConsumable() && !game.inCheckPlayableState()) {
                                possibleApprovingObjects.add(new ApprovingObject(ability, game));
                            } else {
                                return new ApprovingObject(ability, game);
                            }
                        }
                    }
                }
            }

            if (possibleApprovingObjects.size() == 1) {
                return possibleApprovingObjects.iterator().next();
            } else if (possibleApprovingObjects.size() > 1) {
                // Select the ability that you use to permit the action                
                Map<String, String> keyChoices = new HashMap<>();
                for (ApprovingObject approvingObject : possibleApprovingObjects) {
                    MageObject mageObject = game.getObject(approvingObject.getApprovingAbility().getSourceId());
                    String choiceKey = approvingObject.getApprovingAbility().getId().toString();
                    String choiceValue;
                    if (mageObject == null) {
                        choiceValue = approvingObject.getApprovingAbility().getRule();
                    } else {
                        choiceValue = mageObject.getIdName() + ": " + approvingObject.getApprovingAbility().getRule(mageObject.getName());
                    }
                    keyChoices.put(choiceKey, choiceValue);
                }
                Choice choicePermitting = new ChoiceImpl(true);
                choicePermitting.setMessage("Choose the permitting object");
                choicePermitting.setKeyChoices(keyChoices);
                Player player = game.getPlayer(controllerId);
                player.choose(Outcome.Detriment, choicePermitting, game);
                for (ApprovingObject approvingObject : possibleApprovingObjects) {
                    if (approvingObject.getApprovingAbility().getId().toString().equals(choicePermitting.getChoiceKey())) {
                        return approvingObject;
                    }
                }
            }

        }
        return null;
    }

    /**
     * Fit paying mana type with current mana pool (if asThoughMana affected)
     * <p>
     * Example:
     * - you need to pay {R} as cost
     * - asThoughMana effect allows to use {G} as any color;
     * - asThoughMana effect must change/fit paying mana type from {R} to {G}
     * - after that you can pay {G} as cost
     *
     * @param manaType        paying mana type
     * @param mana            checking pool item
     * @param objectId        paying ability's source object
     * @param affectedAbility paying ability
     * @param controllerId    controller who pay
     * @param game
     * @return corrected paying mana type (same if no asThough effects and different on applied asThough effect)
     */
    public ManaType asThoughMana(ManaType manaType, ManaPoolItem mana, UUID objectId, Ability affectedAbility, UUID controllerId, Game game) {
        // First check existing only effects
        List<AsThoughEffect> asThoughEffectsList = getApplicableAsThoughEffects(AsThoughEffectType.SPEND_ONLY_MANA, game);
        for (AsThoughEffect effect : asThoughEffectsList) {
            Set<Ability> abilities = asThoughEffectsMap.get(AsThoughEffectType.SPEND_ONLY_MANA).getAbility(effect.getId());
            for (Ability ability : abilities) {
                if ((affectedAbility == null && effect.applies(objectId, ability, controllerId, game))
                        || effect.applies(objectId, affectedAbility, ability, game, controllerId)) {
                    if (((AsThoughManaEffect) effect).getAsThoughManaType(manaType, mana, controllerId, ability, game) == null) {
                        return null;
                    }
                }
            }
        }
        // then check effects that allow to use other mana types to pay the current mana type to pay
        asThoughEffectsList = getApplicableAsThoughEffects(AsThoughEffectType.SPEND_OTHER_MANA, game);
        for (AsThoughEffect effect : asThoughEffectsList) {
            Set<Ability> abilities = asThoughEffectsMap.get(AsThoughEffectType.SPEND_OTHER_MANA).getAbility(effect.getId());
            for (Ability ability : abilities) {
                if ((affectedAbility == null && effect.applies(objectId, ability, controllerId, game))
                        || effect.applies(objectId, affectedAbility, ability, game, controllerId)) {
                    ManaType usableManaType = ((AsThoughManaEffect) effect).getAsThoughManaType(manaType, mana, controllerId, ability, game);
                    if (usableManaType != null) {
                        return usableManaType;
                    }
                }
            }
        }
        return manaType;
    }

    /**
     * Filters out asThough effects that are not active.
     *
     * @param type type
     * @param game
     * @return
     */
    public List<AsThoughEffect> getApplicableAsThoughEffects(AsThoughEffectType type, Game game) {
        List<AsThoughEffect> asThoughEffectsList = new ArrayList<>();
        if (asThoughEffectsMap.containsKey(type)) {
            for (AsThoughEffect effect : asThoughEffectsMap.get(type)) {
                Set<Ability> abilities = asThoughEffectsMap.get(type).getAbility(effect.getId());
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

    public Set<Ability> getAsThoughEffectsAbility(AsThoughEffect effect) {
        return asThoughEffectsMap.get(effect.getAsThoughEffectType()).getAbility(effect.getId());
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
     * reduction effects, it is considered to be {0}. It can't be reduced to
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
                Set<Ability> abilities = costModificationEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (effect.applies(abilityToModify, ability, game)) {
                        effect.apply(game, ability, abilityToModify);
                    }
                }
            }
        }

        for (CostModificationEffect effect : costEffects) {
            if (effect.getModificationType() == CostModificationType.REDUCE_COST) {
                Set<Ability> abilities = costModificationEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (effect.applies(abilityToModify, ability, game)) {
                        effect.apply(game, ability, abilityToModify);
                    }
                }
            }
        }

        for (CostModificationEffect effect : costEffects) {
            if (effect.getModificationType() == CostModificationType.SET_COST) {
                Set<Ability> abilities = costModificationEffects.getAbility(effect.getId());
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
        // add effects from splice card to spell ability on activate/cast

        List<SpliceCardEffect> spliceEffects = getApplicableSpliceCardEffects(game, abilityToModify.getControllerId());
        // get the applyable splice abilities
        List<Ability> spliceAbilities = new ArrayList<>();
        for (SpliceCardEffect effect : spliceEffects) {
            Set<Ability> abilities = spliceCardEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (effect.applies(abilityToModify, ability, game)) {
                    spliceAbilities.add(ability);
                }
            }
        }
        // check if player wants to use splice

        if (!spliceAbilities.isEmpty()) {
            Player controller = game.getPlayer(abilityToModify.getControllerId());
            if (controller.chooseUse(Outcome.Benefit, "Splice a card?", abilityToModify, game)) {
                Cards cardsToReveal = new CardsImpl();
                do {
                    FilterCard filter = new FilterCard("a card to splice");
                    List<Predicate<MageObject>> idPredicates = new ArrayList<>();
                    for (Ability ability : spliceAbilities) {
                        idPredicates.add(new CardIdPredicate((ability.getSourceId())));
                    }
                    filter.add(Predicates.or(idPredicates));
                    TargetCardInHand target = new TargetCardInHand(filter);
                    controller.chooseTarget(Outcome.Benefit, target, abilityToModify, game);
                    UUID cardId = target.getFirstTarget();
                    if (cardId != null) {
                        Ability selectedAbility = null;
                        for (Ability ability : spliceAbilities) {
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
     * @param silentMode    true if the event does not really happen but it's
     *                      checked if the event would be replaced
     * @return
     */
    public boolean preventedByRuleModification(GameEvent event, Ability targetAbility, Game game, boolean silentMode) {
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
                                if (!game.inCheckPlayableState() && !silentMode) {
                                    MageObject sourceObject = sourceAbility.getSourceObject(game);
                                    String message = effect.getInfoMessage(sourceAbility, event, game);
                                    if (sourceObject != null) {
                                        message = sourceObject.getIdName() + ": " + message;
                                    }
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
        Map<UUID, Set<UUID>> consumed = new HashMap<>();
        do {
            Map<ReplacementEffect, Set<Ability>> rEffects = getApplicableReplacementEffects(event, game);
            // Remove all consumed effects (ability dependant)
            for (Iterator<ReplacementEffect> it1 = rEffects.keySet().iterator(); it1.hasNext(); ) {
                ReplacementEffect entry = it1.next();
                if (consumed.containsKey(entry.getId()) /*&& !(entry instanceof CommanderReplacementEffect) */) { // 903.9.
                    Set<UUID> consumedAbilitiesIds = consumed.get(entry.getId());
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
                Set<Ability> abilities;
                if (effect.getEffectType() == EffectType.REPLACEMENT) {
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
            for (Map.Entry<ReplacementEffect, Set<Ability>> entry : rEffects.entrySet()) {
                if (entry.getValue() == null) {
                    if (checked == index) {
                        rEffect = entry.getKey();
                        break;
                    } else {
                        checked++;
                    }
                } else {
                    Set<Ability> abilities = entry.getValue();
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
                if (Duration.OneUse.equals(rEffect.getDuration())) {
                    rEffect.discard();
                }
            }
            if (caught) { // Event was completely replaced -> stop applying effects to it
                break;
            }

            // add the applied effect to the consumed effects
            if (rEffect != null) {
                if (consumed.containsKey(rEffect.getId())) {
                    Set<UUID> set = consumed.get(rEffect.getId());
                    if (rAbility != null) {
                        set.add(rAbility.getId());

                    }
                } else {
                    Set<UUID> set = new HashSet<>();
                    if (rAbility != null) { // in case of AuraReplacementEffect or PlaneswalkerReplacementEffect there is no Ability
                        set.add(rAbility.getId());
                    }
                    consumed.put(rEffect.getId(), set);
                }
            }
            // Must be called here for some effects to be able to work correctly
            // For example: Vesuva copying a Dark Depth (VesuvaTest:testDarkDepth)
            // This call should be removed if possible as replacement effects of EntersTheBattlefield events
            // do no longer work correctly because the entering permanents are not yet on the battlefield (before they were).
            // game.applyEffects();
        } while (true);
        return caught;
    }

    //20091005 - 613
    public synchronized void apply(Game game) {
        removeInactiveEffects(game);
        List<ContinuousEffect> activeLayerEffects = getLayeredEffects(game); // main call

        List<ContinuousEffect> layer = filterLayeredEffects(activeLayerEffects, Layer.CopyEffects_1);
        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.CopyEffects_1, SubLayer.CopyEffects_1a, ability, game);
            }
        }
        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, ability, game);
            }
        }
        //Reload layerEffect if copy effects were applied
        if (!layer.isEmpty()) {
            activeLayerEffects = getLayeredEffects(game, "layer_1");
        }

        layer = filterLayeredEffects(activeLayerEffects, Layer.ControlChangingEffects_2);
        // apply control changing effects multiple times if it's needed
        // for cases when control over permanents with change control abilities is changed
        // e.g. Mind Control is controlled by Steal Enchantment
        while (true) {
            for (ContinuousEffect effect : layer) {
                Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
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

        applyLayer(activeLayerEffects, Layer.TextChangingEffects_3, game, "layer_3");
        activeLayerEffects = getLayeredEffects(game, "layer_3");
        applyLayer(activeLayerEffects, Layer.TypeChangingEffects_4, game, "layer_4");
        activeLayerEffects = getLayeredEffects(game, "layer_4");
        applyLayer(activeLayerEffects, Layer.ColorChangingEffects_5, game, "layer_5");
        activeLayerEffects = getLayeredEffects(game, "layer_5");

        Map<ContinuousEffect, List<Ability>> appliedEffectAbilities = new HashMap<>();
        boolean done = false;
        Map<ContinuousEffect, Set<UUID>> waitingEffects = new LinkedHashMap<>();
        Set<UUID> appliedEffects = new HashSet<>();
        applyCounters.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, null, game);
        activeLayerEffects = getLayeredEffects(game, "layer_6");

        while (!done) { // loop needed if a added effect adds again an effect (e.g. Level 5- of Joraga Treespeaker)
            done = true;
            layer = filterLayeredEffects(activeLayerEffects, Layer.AbilityAddingRemovingEffects_6);

            // debug
            /*
            System.out.println(
                    game.getTurn()
                            + ", " + game.getPhase()
                            + ": need to apply "
                            + layer.stream()
                            .map(Object::getClass)
                            .map(Class::getName)
                            .map(s -> s.replaceAll(".+\\.(.+)", "$1"))
                            .collect(Collectors.joining(", "))
            );
             */
            for (ContinuousEffect effect : layer) {
                if (!activeLayerEffects.contains(effect) || appliedEffects.contains(effect.getId())) {
                    continue;
                } // Effect does still exist and was not applied yet
                Set<UUID> dependentTo = effect.isDependentTo(layer);
                if (!appliedEffects.containsAll(dependentTo)) {
                    waitingEffects
                            .entrySet()
                            .stream()
                            .filter(entry -> dependentTo.contains(entry.getKey().getId())
                                    && entry.getValue().contains(effect.getId()))
                            .forEach(entry -> {
                                entry.getValue().remove(effect.getId());
                                dependentTo.remove(entry.getKey().getId());
                            });
                    waitingEffects.entrySet().removeIf(x -> x.getValue() == null || x.getValue().isEmpty());
                    if (!dependentTo.isEmpty() && !waitingEffects.containsKey(effect)) {
                        // make sure circular dependencies weren't the only dependencies
                        waitingEffects.put(effect, dependentTo);
                        continue;
                    }
                }
                List<Ability> appliedAbilities = appliedEffectAbilities.get(effect);
                Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (appliedAbilities != null && appliedAbilities.contains(ability)) {
                        continue;
                    }
                    if (appliedAbilities == null) {
                        appliedAbilities = new ArrayList<>();
                        appliedEffectAbilities.put(effect, appliedAbilities);
                    }
                    appliedAbilities.add(ability);
                    effect.apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, ability, game);
                    done = false;
                    // list must be updated after each applied effect (eg. if "Turn to Frog" removes abilities)
                    activeLayerEffects = getLayeredEffects(game, "apply");
                }
                appliedEffects.add(effect.getId());

                if (waitingEffects.isEmpty()) {
                    continue;
                }
                // check if waiting effects can be applied now
                for (Iterator<Map.Entry<ContinuousEffect, Set<UUID>>> iterator = waitingEffects.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<ContinuousEffect, Set<UUID>> entry = iterator.next();
                    if (!appliedEffects.containsAll(entry.getValue())) { // all dependent to effects are applied now so apply the effect itself
                        continue;
                    }
                    appliedAbilities = appliedEffectAbilities.get(entry.getKey());
                    abilities = layeredEffects.getAbility(entry.getKey().getId());
                    for (Ability ability : abilities) {
                        if (appliedAbilities != null && appliedAbilities.contains(ability)) {
                            continue;
                        }
                        if (appliedAbilities == null) {
                            appliedAbilities = new ArrayList<>();
                            appliedEffectAbilities.put(entry.getKey(), appliedAbilities);
                        }
                        appliedAbilities.add(ability);
                        entry.getKey().apply(Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, ability, game);
                        done = false;
                        // list must be updated after each applied effect (eg. if "Turn to Frog" removes abilities)
                        activeLayerEffects = getLayeredEffects(game, "apply");
                    }
                    appliedEffects.add(entry.getKey().getId());
                    iterator.remove();
                }
            }
        }

        layer = filterLayeredEffects(activeLayerEffects, Layer.PTChangingEffects_7);
        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (abilityActive(ability, game)) {
                    effect.apply(Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, ability, game);
                }
            }
        }
        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PTChangingEffects_7, SubLayer.SetPT_7b, ability, game);
            }
        }
        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, ability, game);
            }
        }

        applyCounters.apply(Layer.PTChangingEffects_7, SubLayer.Counters_7d, null, game);

        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, ability, game);
            }
        }
        layer = filterLayeredEffects(activeLayerEffects, Layer.PlayerEffects);
        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.PlayerEffects, SubLayer.NA, ability, game);
            }
        }
        layer = filterLayeredEffects(activeLayerEffects, Layer.RulesEffects);
        for (ContinuousEffect effect : layer) {
            Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                effect.apply(Layer.RulesEffects, SubLayer.NA, ability, game);
            }
        }
    }

    private boolean abilityActive(Ability ability, Game game) {
        MageObject object = game.getObject(ability.getSourceId());
        return object != null && object.hasAbility(ability, game);
    }

    private void applyLayer(List<ContinuousEffect> activeLayerEffects, Layer currentLayer, Game game, String timestampGroupName) {
        List<ContinuousEffect> layer = filterLayeredEffects(activeLayerEffects, currentLayer);
        // layer is a list of all effects at the current layer
        if (!layer.isEmpty()) {
            int numberOfEffects = layer.size();
            // appliedEffects holds the list of effects currently applied to the layer
            Set<UUID> appliedEffects = new HashSet<>();
            // waitingEffects holds the list of dependent effects and their independent counterparts
            Map<ContinuousEffect, Set<UUID>> waitingEffects = new LinkedHashMap<>();
            for (ContinuousEffect effect : layer) {
                if (numberOfEffects > 1) {
                    // If an effect is dependent to not applied effects yet of this layer, so wait to apply this effect
                    // check to see if any effect is dependent to other cards indirectly due to the independent card being dependent, etc.
                    Set<UUID> dependentTo = effect.isDependentTo(layer);
                    if (!appliedEffects.containsAll(dependentTo)) {
                        waitingEffects.put(effect, dependentTo);
                        continue;
                    }
                }
                // apply the effect
                applyContinuousEffect(effect, currentLayer, game);
                // add it to the applied effects list
                appliedEffects.add(effect.getId());
                layer = getLayeredEffects(game, timestampGroupName);

                // check waiting effects to see if it has anything to check
                if (!waitingEffects.isEmpty()) {
                    // check if waiting effects can be applied now
                    for (Entry<ContinuousEffect, Set<UUID>> entry : waitingEffects.entrySet()) {
                        // all dependent to effects are applied now so apply the effect itself
                        if (appliedEffects.containsAll(entry.getValue())) {
                            applyContinuousEffect(entry.getKey(), currentLayer, game);
                            // add it to the applied effects list
                            appliedEffects.add(entry.getKey().getId());
                            layer = getLayeredEffects(game, timestampGroupName);
                        }
                    }
                }
                if (numberOfEffects != appliedEffects.size()) {
                    for (Entry<ContinuousEffect, Set<UUID>> entry : waitingEffects.entrySet()) {
                        // all dependent to effects are applied now so apply the effect itself
                        if (appliedEffects.containsAll(entry.getValue())) {
                            applyContinuousEffect(entry.getKey(), currentLayer, game);
                            // add it to the applied effects list
                            appliedEffects.add(entry.getKey().getId());
                            layer = getLayeredEffects(game, timestampGroupName);
                        }
                    }
                }
            }
        }
    }

    private void applyContinuousEffect(ContinuousEffect effect, Layer currentLayer, Game game) {
        Set<Ability> abilities = layeredEffects.getAbility(effect.getId());
        for (Ability ability : abilities) {
            //effect.apply(currentLayer, SubLayer.NA, ability, game);
            if (isAbilityStillExists(game, ability, effect)) {
                effect.apply(currentLayer, SubLayer.NA, ability, game);
            }
        }
    }

    private boolean isAbilityStillExists(final Game game, final Ability ability, ContinuousEffect effect) {
        switch (effect.getDuration()) { // effects with fixed duration don't need an object with the source ability (e.g. a silence cast with isochronic Scepter has no more a card object
            case EndOfCombat:
            case EndOfGame:
            case EndOfStep:
            case EndOfTurn:
            case OneUse:
            case Custom:  // custom duration means the effect ends itself if needed
                return true;
        }
        final Card card = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        return effect instanceof BecomesFaceDownCreatureEffect
                || effect == null || card == null || card.hasAbility(ability, game);
    }

    public Set<Ability> getLayeredEffectAbilities(ContinuousEffect effect) {
        return layeredEffects.getAbility(effect.getId());
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
    public synchronized void addEffect(ContinuousEffect effect, UUID sourceId, Ability source) {
        if (!(source instanceof MageSingleton)) { // because MageSingletons may never be removed by removing the temporary effecs they are not added to the temporaryEffects to prevent this
            effect.setTemporary(true);
        }
        addEffect(effect, source);
    }

    public synchronized void addEffect(ContinuousEffect effect, Ability source) {
        if (effect == null) {
            logger.error("Effect is null: " + source.toString());
            return;
        } else if (source == null) {
            logger.warn("Adding effect without ability : " + effect);
        }
        switch (effect.getEffectType()) {
            case REPLACEMENT:
            case REDIRECTION:
                replacementEffects.addEffect((ReplacementEffect) effect, source);
                break;
            case PREVENTION:
                preventionEffects.addEffect((PreventionEffect) effect, source);
                break;
            case RESTRICTION:
                restrictionEffects.addEffect((RestrictionEffect) effect, source);
                break;
            case RESTRICTION_UNTAP_NOT_MORE_THAN:
                restrictionUntapNotMoreThanEffects.addEffect((RestrictionUntapNotMoreThanEffect) effect, source);
                break;
            case REQUIREMENT:
                requirementEffects.addEffect((RequirementEffect) effect, source);
                break;
            case ASTHOUGH:
                AsThoughEffect newAsThoughEffect = (AsThoughEffect) effect;
                asThoughEffectsMap.computeIfAbsent(newAsThoughEffect.getAsThoughEffectType(), x -> {
                    ContinuousEffectsList<AsThoughEffect> list = new ContinuousEffectsList<>();
                    allEffectsLists.add(list);
                    return list;
                }).addEffect(newAsThoughEffect, source);
                break;
            case COSTMODIFICATION:
                costModificationEffects.addEffect((CostModificationEffect) effect, source);
                break;
            case SPLICE:
                spliceCardEffects.addEffect((SpliceCardEffect) effect, source);
                break;
            case CONTINUOUS_RULE_MODIFICATION:
                continuousRuleModifyingEffects.addEffect((ContinuousRuleModifyingEffect) effect, source);
                break;
            default:
                layeredEffects.addEffect(effect, source);
                break;
        }
    }

    public synchronized void setController(UUID cardId, UUID controllerId) {
        for (ContinuousEffectsList effectsList : allEffectsLists) {
            setControllerForEffect(effectsList, cardId, controllerId);
        }
    }

    private void setControllerForEffect(ContinuousEffectsList<?> effects, UUID sourceId, UUID controllerId) {
        for (ContinuousEffect effect : effects) {
            if (!effect.getDuration().isFixedController()) {
                Set<Ability> abilities = effects.getAbility(effect.getId());
                for (Ability ability : abilities) {
                    if (ability.getSourceId() != null) {
                        if (ability.getSourceId().equals(sourceId)) {
                            ability.setControllerId(controllerId);
                        }
                    } else if (ability.getZone() != Zone.COMMAND) {
                        logger.fatal("Continuous effect for ability with no sourceId Ability: " + ability);
                    }
                }
            }
        }
    }

    public synchronized void clear() {
        for (ContinuousEffectsList effectsList : allEffectsLists) {
            effectsList.clear();
        }
    }

    public synchronized void removeAllTemporaryEffects() {
        for (ContinuousEffectsList effectsList : allEffectsLists) {
            effectsList.removeTemporaryEffects();
        }
    }

    public Map<String, String> getReplacementEffectsTexts(Map<ReplacementEffect, Set<Ability>> rEffects, Game game) {
        // warning, autoSelectReplacementEffects uses [object id] in texts as different settings,
        // so if you change keys or texts logic then don't forget to change auto-choose too
        Map<String, String> texts = new LinkedHashMap<>();
        for (Map.Entry<ReplacementEffect, Set<Ability>> entry : rEffects.entrySet()) {
            if (entry.getValue() != null) {
                for (Ability ability : entry.getValue()) {
                    MageObject object = game.getObject(ability.getSourceId());
                    if (object != null) {
                        texts.put(ability.getId().toString() + '_' + entry.getKey().getId().toString(), object.getIdName() + ": " + ability.getRule(object.getName()));
                    } else {
                        texts.put(ability.getId().toString() + '_' + entry.getKey().getId().toString(), entry.getKey().getText(null));
                    }
                }
            } else {
                if (!(entry.getKey() instanceof AuraReplacementEffect)) {
                    logger.error("Replacement effect without ability: " + entry.getKey().toString());
                }
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
            Set<Ability> abilities = preventionEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (ability.getSourceId().equals(sourceId)) {
                    if (controllerFound == null || controllerFound.equals(ability.getControllerId())) {
                        controllerFound = ability.getControllerId();
                    } else {
                        // not unique controller - No solution yet
                        return null;
                    }
                }
            }
        }
        for (ReplacementEffect effect : replacementEffects) {
            Set<Ability> abilities = replacementEffects.getAbility(effect.getId());
            for (Ability ability : abilities) {
                if (ability.getSourceId() != null) {
                    if (ability.getSourceId().equals(sourceId)) {
                        if (controllerFound == null || controllerFound.equals(ability.getControllerId())) {
                            controllerFound = ability.getControllerId();
                        } else {
                            // not unique controller - No solution yet
                            return null;
                        }
                    }
                } else {
                    if (!(effect instanceof CommanderReplacementEffect)) {
                        logger.warn("Ability without sourceId:" + ability.getRule());
                    }
                }
            }
        }
        return controllerFound;
    }

    /**
     * Debug only: prints out a status of the currently existing continuous effects
     *
     * @param game
     */
    public void traceContinuousEffects(Game game) {
        game.getContinuousEffects().getLayeredEffects(game);
        logger.info("-------------------------------------------------------------------------------------------------");
        int numberEffects = 0;
        for (ContinuousEffectsList list : allEffectsLists) {
            numberEffects += list.size();
        }
        logger.info("Turn: " + game.getTurnNum() + " - currently existing continuous effects: " + numberEffects);
        logger.info("layeredEffects ...................: " + layeredEffects.size());
        logger.info("continuousRuleModifyingEffects ...: " + continuousRuleModifyingEffects.size());
        logger.info("replacementEffects ...............: " + replacementEffects.size());
        logger.info("preventionEffects ................: " + preventionEffects.size());
        logger.info("requirementEffects ...............: " + requirementEffects.size());
        logger.info("restrictionEffects ...............: " + restrictionEffects.size());
        logger.info("restrictionUntapNotMoreThanEffects: " + restrictionUntapNotMoreThanEffects.size());
        logger.info("costModificationEffects ..........: " + costModificationEffects.size());
        logger.info("spliceCardEffects ................: " + spliceCardEffects.size());
        logger.info("asThoughEffects:");
        for (Map.Entry<AsThoughEffectType, ContinuousEffectsList<AsThoughEffect>> entry : asThoughEffectsMap.entrySet()) {
            logger.info("... " + entry.getKey().toString() + ": " + entry.getValue().size());
        }
        logger.info("applyCounters ....................: " + (applyCounters != null ? "exists" : "null"));
        logger.info("auraReplacementEffect ............: " + (continuousRuleModifyingEffects != null ? "exists" : "null"));
        Map<String, TraceInfo> orderedEffects = new TreeMap<>();
        traceAddContinuousEffects(orderedEffects, layeredEffects, game, "layeredEffects................");
        traceAddContinuousEffects(orderedEffects, continuousRuleModifyingEffects, game, "continuousRuleModifyingEffects");
        traceAddContinuousEffects(orderedEffects, replacementEffects, game, "replacementEffects............");
        traceAddContinuousEffects(orderedEffects, preventionEffects, game, "preventionEffects.............");
        traceAddContinuousEffects(orderedEffects, requirementEffects, game, "requirementEffects............");
        traceAddContinuousEffects(orderedEffects, restrictionEffects, game, "restrictionEffects............");
        traceAddContinuousEffects(orderedEffects, restrictionUntapNotMoreThanEffects, game, "restrictionUntapNotMore...");
        traceAddContinuousEffects(orderedEffects, costModificationEffects, game, "costModificationEffects.......");
        traceAddContinuousEffects(orderedEffects, spliceCardEffects, game, "spliceCardEffects.............");
        for (Map.Entry<AsThoughEffectType, ContinuousEffectsList<AsThoughEffect>> entry : asThoughEffectsMap.entrySet()) {
            traceAddContinuousEffects(orderedEffects, entry.getValue(), game, entry.getKey().toString());
        }
        String playerName = "";
        for (Map.Entry<String, TraceInfo> entry : orderedEffects.entrySet()) {
            if (!entry.getValue().getPlayerName().equals(playerName)) {
                playerName = entry.getValue().getPlayerName();
                logger.info("--- Player: " + playerName + "  --------------------------------");
            }
            logger.info(entry.getValue().getInfo()
                    + " " + entry.getValue().getSourceName()
                    + " " + entry.getValue().getDuration().name()
                    + " " + entry.getValue().getRule()
                    + " (Order: " + entry.getValue().getOrder() + ")"
            );
        }
        logger.info("---- End trace Continuous effects --------------------------------------------------------------------------");
    }

    public static void traceAddContinuousEffects(Map orderedEffects, ContinuousEffectsList<?> cel, Game game, String listName) {
        for (ContinuousEffect effect : cel) {
            Set<Ability> abilities = cel.getAbility(effect.getId());
            for (Ability ability : abilities) {
                Player controller = game.getPlayer(ability.getControllerId());
                MageObject source = game.getObject(ability.getSourceId());
                TraceInfo traceInfo = new TraceInfo();
                traceInfo.setInfo(listName);
                traceInfo.setOrder(effect.getOrder());
                if (ability instanceof MageSingleton) {
                    traceInfo.setPlayerName("Mage Singleton");
                    traceInfo.setSourceName("Mage Singleton");
                } else {
                    traceInfo.setPlayerName(controller == null ? "no controller" : controller.getName());
                    traceInfo.setSourceName(source == null ? "no source" : source.getIdName());
                }
                traceInfo.setRule(ability.getRule());
                traceInfo.setAbilityId(ability.getId());
                traceInfo.setEffectId(effect.getId());
                traceInfo.setDuration(effect.getDuration());
                orderedEffects.put(traceInfo.getPlayerName() + traceInfo.getSourceName() + effect.getId() + ability.getId(), traceInfo);
            }
        }
    }

    @Override
    public String toString() {
        return "Effects: " + allEffectsLists.stream().mapToInt(ContinuousEffectsList::size).sum();
    }
}
