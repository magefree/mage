package mage.abilities;

import mage.MageObject;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.util.Copyable;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class TriggeredAbilities extends LinkedHashMap<String, TriggeredAbility> implements Copyable<TriggeredAbilities> {

    private static final Logger logger = Logger.getLogger(TriggeredAbilities.class);

    private final Map<String, List<UUID>> sources = new HashMap<>();

    // data integrity check for triggers
    // reason: game engine can generate additional events and triggers while checking another one,
    // it can generate multiple bugs, freeze, etc, see https://github.com/magefree/mage/issues/8426
    // all checks can be catches by existing tests
    private boolean enableIntegrityCheck1_MustKeepSameTriggersOrder = true; // good
    private boolean enableIntegrityCheck2_MustKeepSameTriggersList = false; // bad, impossible to fix due dynamic triggers gen
    private boolean enableIntegrityCheck3_CantStartEventProcessingBeforeFinishPrev = false; // bad, impossible to fix due dynamic triggers gen
    private boolean enableIntegrityCheck4_EventMustProcessAllOldTriggers = true; // good
    private boolean enableIntegrityCheck5_EventMustProcessInSameOrder = true; // good
    private boolean enableIntegrityCheck6_EventMustNotProcessNewTriggers = false; // bad, impossible to fix due dynamic triggers gen
    private boolean enableIntegrityLogs = false; // debug only
    private boolean processingStarted = false;
    private GameEvent.EventType processingStartedEvent = null; // null for game state triggers
    private List<TriggeredAbility> processingNeed = new ArrayList<>();
    private List<TriggeredAbility> processingDone = new ArrayList<>();

    public TriggeredAbilities() {
    }

    protected TriggeredAbilities(final TriggeredAbilities abilities) {
        makeSureNotProcessing(null);

        for (Map.Entry<String, TriggeredAbility> entry : abilities.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        for (Map.Entry<String, List<UUID>> entry : abilities.sources.entrySet()) {
            sources.put(entry.getKey(), entry.getValue());
        }

        this.enableIntegrityCheck1_MustKeepSameTriggersOrder = abilities.enableIntegrityCheck1_MustKeepSameTriggersOrder;
        this.enableIntegrityCheck2_MustKeepSameTriggersList = abilities.enableIntegrityCheck2_MustKeepSameTriggersList;
        this.enableIntegrityCheck3_CantStartEventProcessingBeforeFinishPrev = abilities.enableIntegrityCheck3_CantStartEventProcessingBeforeFinishPrev;
        this.enableIntegrityCheck4_EventMustProcessAllOldTriggers = abilities.enableIntegrityCheck4_EventMustProcessAllOldTriggers;
        this.enableIntegrityCheck5_EventMustProcessInSameOrder = abilities.enableIntegrityCheck5_EventMustProcessInSameOrder;
        this.enableIntegrityCheck6_EventMustNotProcessNewTriggers = abilities.enableIntegrityCheck6_EventMustNotProcessNewTriggers;

        this.enableIntegrityLogs = abilities.enableIntegrityLogs;
        this.processingStarted = abilities.processingStarted;
        this.processingStartedEvent = abilities.processingStartedEvent;
        this.processingNeed = CardUtil.deepCopyObject(abilities.processingNeed);
        this.processingDone = CardUtil.deepCopyObject(abilities.processingDone);

        // runtime check: triggers order (not required by paper rules, by required by xmage to make same result for all game instances)
        if (this.enableIntegrityCheck1_MustKeepSameTriggersOrder) {
            if (!Objects.equals(this.values().stream().findFirst().orElse(null) + "",
                    abilities.values().stream().findFirst().orElse(null) + "")) {
                // how-to fix: use LinkedHashMap instead HashMap/ConcurrentHashMap
                throw new IllegalStateException("Triggers integrity failed: triggers order changed");
            }
        }
    }

    public void checkStateTriggers(Game game) {
        makeSureNotProcessing(null);

        processingStart(null);
        boolean needErrorChecksOnEnd = true;
        try {
            for (Iterator<TriggeredAbility> it = this.values().iterator(); it.hasNext(); ) {
                TriggeredAbility ability = it.next();
                if (ability instanceof StateTriggeredAbility && ((StateTriggeredAbility) ability).canTrigger(game)) {
                    checkTrigger(ability, null, game);
                }
                this.processingDone(ability);
            }
        } catch (Exception e) {
            // need additional catch to show inner errors
            needErrorChecksOnEnd = false;
            throw e;
        } finally {
            processingEnd(needErrorChecksOnEnd);
        }
    }

    public void checkTriggers(GameEvent event, Game game) {
        processingStart(event);
        boolean needErrorChecksOnEnd = true;
        // must keep real object refs (not copies), cause check trigger code can change trigger's and effect's data like targets
        ArrayList<TriggeredAbility> currentTriggers = new ArrayList<>(this.values());
        try {
            for (TriggeredAbility ability : currentTriggers) {
                if (ability.checkEventType(event, game)) {
                    checkTrigger(ability, event, game);
                }
                this.processingDone(ability);
            }
        } catch (Exception e) {
            // need additional catch to show inner errors
            needErrorChecksOnEnd = false;
            throw e;
        } finally {
            processingEnd(needErrorChecksOnEnd);
        }
    }

    private void makeSureNotProcessing(GameEvent newEvent) {
        if (this.enableIntegrityCheck2_MustKeepSameTriggersList
                && this.processingStarted) {
            List<String> info = new ArrayList<>();
            info.add("old event: " + this.processingStartedEvent);
            info.add("new event: " + newEvent.getType());
            // how-to fix: impossible until mana events/triggers rework cause one mana event can generate additional events/triggers
            throw new IllegalArgumentException("Triggers integrity failed: triggers can't be modified while processing - "
                    + String.join(", ", info));
        }
    }

    private void processingStart(GameEvent newEvent) {
        makeSureNotProcessing(newEvent);

        this.processingStarted = true;
        this.processingStartedEvent = newEvent == null ? null : newEvent.getType();
        this.processingNeed.clear();
        this.processingNeed.addAll(this.values());
        this.processingDone.clear();
    }

    private void processingDone(TriggeredAbility trigger) {
        this.processingDone.add(trigger);
    }

    private void processingEnd(boolean needErrorChecks) {
        if (needErrorChecks) {
            if (this.enableIntegrityCheck3_CantStartEventProcessingBeforeFinishPrev
                    && !this.processingStarted) {
                throw new IllegalArgumentException("Triggers integrity failed: can't finish event before start");
            }

            // must use ability's id to check equal (rules can be diff due usage of dynamic values - alternative to card hints)
            List<UUID> needIds = new ArrayList<>();
            String needInfo = this.processingNeed.stream()
                    .peek(a -> needIds.add(a.getId()))
                    .map(t -> "- " + t)
                    .sorted()
                    .collect(Collectors.joining("\n"));
            List<UUID> doneIds = new ArrayList<>();
            String doneInfo = this.processingDone.stream()
                    .peek(a -> doneIds.add(a.getId()))
                    .map(t -> "- " + t)
                    .sorted()
                    .collect(Collectors.joining("\n"));
            String errorInfo = ""
                    + "\n" + "Need: "
                    + "\n" + (needInfo.isEmpty() ? "-" : needInfo)
                    + "\n" + "Done: "
                    + "\n" + (doneInfo.isEmpty() ? "-" : doneInfo);

            if (this.enableIntegrityCheck4_EventMustProcessAllOldTriggers
                    && this.processingDone.size() < this.processingNeed.size()) {
                throw new IllegalArgumentException("Triggers integrity failed: event processing miss some triggers" + errorInfo);
            }

            if (this.enableIntegrityCheck5_EventMustProcessInSameOrder
                    && this.processingDone.size() > 0
                    && this.processingDone.size() == this.processingNeed.size()
                    && !needIds.toString().equals(doneIds.toString())) {
                throw new IllegalArgumentException("Triggers integrity failed: event processing used wrong order" + errorInfo);
            }

            if (this.enableIntegrityCheck6_EventMustNotProcessNewTriggers
                    && this.processingDone.size() > this.processingNeed.size()) {
                throw new IllegalArgumentException("Triggers integrity failed: event processing must not process new triggers" + errorInfo);
            }
        }

        this.processingStarted = false;
        this.processingStartedEvent = null;
        this.processingNeed.clear();
        this.processingDone.clear();
    }

    private void checkTrigger(TriggeredAbility ability, GameEvent event, Game game) {
        // for effects like when leaves battlefield or destroyed use ShortLKI to check if permanent was in the correct zone before (e.g. Oblivion Ring or Karmic Justice)
        if (this.enableIntegrityLogs) {
            logger.info("---");
            logger.info("checking trigger: " + ability);
            logger.info("playable state: " + game.inCheckPlayableState());
            logger.info(game);
            logger.info("battlefield:" + "\n" + game.getBattlefield().getAllPermanents().stream()
                    .map(p -> "- " + p.toString())
                    .collect(Collectors.joining("\n")) + "\n");
        }
        MageObject object = game.getObject(ability.getSourceId());
        if (ability.isInUseableZone(game, object, event)) {
            if (event == null || !game.getContinuousEffects().preventedByRuleModification(event, ability, game, false)) {
                if (object != null) {
                    boolean controllerSet = false;
                    Set<UUID> eventTargets = CardUtil.getEventTargets(event);
                    if (ability.getZone() != Zone.COMMAND
                            && event != null
                            && !eventTargets.isEmpty()
                            && ability.isLeavesTheBattlefieldTrigger()
                            && game.getLKI().get(Zone.BATTLEFIELD) != null
                            && game.getLKI().get(Zone.BATTLEFIELD).containsKey(ability.getSourceId())) {
                        // need to check if object was face down for dies and destroy events because the ability triggers in the new zone, zone counter -1 is used
                        Permanent permanent = (Permanent) game.getLastKnownInformation(ability.getSourceId(), Zone.BATTLEFIELD, ability.getSourceObjectZoneChangeCounter() - 1);
                        if (permanent != null) {
                            if (permanent.isFaceDown(game)
                                    && !isGainedAbility(ability, permanent) // the face down creature got the ability from an effect => so it should work
                                    && !ability.getWorksFaceDown()) { // the ability is declared to work also face down
                                // Not all triggered abilities of face down creatures work if they are faced down
                                return;
                            }
                            controllerSet = true;
                            ability.setControllerId(permanent.getControllerId());
                        }
                    }
                    if (!controllerSet) {
                        if (object instanceof Permanent) {
                            ability.setControllerId(((Permanent) object).getControllerId());
                        } else if (object instanceof Spell) {
                            // needed so that cast triggered abilities have to correct controller (e.g. Ulamog, the Infinite Gyre).
                            ability.setControllerId(((Spell) object).getControllerId());
                        }
                    }
                }

                if (ability.checkTrigger(event, game) && ability.checkTriggeredAlready(game) && !ability.checkUsedAlready(game)) {
                    NumberOfTriggersEvent numberOfTriggersEvent = new NumberOfTriggersEvent(ability, event);
                    // event == null - state based triggers like StateTriggeredAbility, must be ignored for number event
                    if (event == null || !game.replaceEvent(numberOfTriggersEvent, ability)) {
                        int numTriggers = ability.getTriggersOnceEachTurn() ? 1 : numberOfTriggersEvent.getAmount();
                        for (int i = 0; i < numTriggers; i++) {
                            if (this.enableIntegrityLogs) {
                                logger.info("trigger will be USED: " + ability);
                            }
                            ability.trigger(game, ability.getControllerId(), event);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a by sourceId gained triggered ability
     *
     * @param ability    - the gained ability
     * @param sourceId   - the source that assigned the ability
     * @param attachedTo - the object that gained the ability
     */
    public void add(TriggeredAbility ability, UUID sourceId, MageObject attachedTo) {
        makeSureNotProcessing(null);
        if (sourceId == null) {
            add(ability, attachedTo);
        } else if (attachedTo == null) {
            this.put(ability.getId() + "_" + sourceId, ability);
        } else {
            this.add(ability, attachedTo);
            List<UUID> uuidList = new LinkedList<>();
            uuidList.add(sourceId);
            // if the object that gained the ability moves from zone then the triggered ability must be removed
            uuidList.add(attachedTo.getId());
            sources.put(getKey(ability, attachedTo), uuidList);
        }
    }

    public void add(TriggeredAbility ability, MageObject attachedTo) {
        makeSureNotProcessing(null);
        this.put(getKey(ability, attachedTo), ability);
    }

    private String getKey(TriggeredAbility ability, MageObject target) {
        String key = ability.getId() + "_";
        if (target != null) {
            key += target.getId();
        }
        return key;
    }

    public void removeAbilitiesOfSource(UUID sourceId) {
        keySet().removeIf(key -> key.endsWith(sourceId.toString()));
    }

    public void removeAllGainedAbilities() {
        this.keySet().removeAll(sources.keySet());
        sources.clear();
    }

    public boolean isGainedAbility(TriggeredAbility abilityToCheck, MageObject attachedTo) {
        return sources.containsKey(getKey(abilityToCheck, attachedTo));
    }

    public void removeAbilitiesOfNonExistingSources(Game game) {
        // e.g. Token that had triggered abilities
        entrySet().removeIf(entry -> game.getObject(entry.getValue().getSourceId()) == null
                && game.getState().getInherentEmblems().stream().noneMatch(emblem -> emblem.getId().equals(entry.getValue().getSourceId()))
                && game.getState().getDesignations().stream().noneMatch(designation -> designation.getId().equals(entry.getValue().getSourceId())));
    }

    @Override
    public TriggeredAbilities copy() {
        return new TriggeredAbilities(this);
    }
}
