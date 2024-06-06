package mage.abilities;

import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.constants.AbilityType;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TriggeredAbilityImpl extends AbilityImpl implements TriggeredAbility {

    private boolean optional;
    private boolean leavesTheBattlefieldTrigger;
    private int triggerLimitEachTurn = Integer.MAX_VALUE; // for "triggers only once|twice each turn"
    private boolean doOnlyOnceEachTurn = false;
    private boolean replaceRuleText = false; // if true, replace "{this}" with "it" in effect text
    private GameEvent triggerEvent = null;
    private String triggerPhrase = null;

    protected TriggeredAbilityImpl(Zone zone, Effect effect) {
        this(zone, effect, false);
    }

    protected TriggeredAbilityImpl(Zone zone, Effect effect, boolean optional) {
        super(AbilityType.TRIGGERED_NONMANA, zone);
        setLeavesTheBattlefieldTrigger(false);
        if (effect != null) {
            addEffect(effect);
        }
        this.optional = optional;

        // verify check: DoIfCostPaid effect already asks about action (optional), so no needs to ask it again in triggered ability
        if (effect instanceof DoIfCostPaid && (this.optional && ((DoIfCostPaid) effect).isOptional())) {
            throw new IllegalArgumentException("DoIfCostPaid effect must have only one optional settings, but it have two (trigger + DoIfCostPaid): " + this.getClass().getSimpleName());

        }
    }

    protected TriggeredAbilityImpl(final TriggeredAbilityImpl ability) {
        super(ability);
        this.optional = ability.optional;
        this.leavesTheBattlefieldTrigger = ability.leavesTheBattlefieldTrigger;
        this.triggerLimitEachTurn = ability.triggerLimitEachTurn;
        this.doOnlyOnceEachTurn = ability.doOnlyOnceEachTurn;
        this.replaceRuleText = ability.replaceRuleText;
        this.triggerEvent = ability.triggerEvent;
        this.triggerPhrase = ability.triggerPhrase;
    }

    @Override
    public void trigger(Game game, UUID controllerId, GameEvent triggeringEvent) {
        //20091005 - 603.4
        if (checkInterveningIfClause(game)) {
            setLastTrigger(game);
            game.addTriggeredAbility(this, triggeringEvent);
        }
    }

    // Used for triggers with a per-turn limit.
    private String getKeyLastTurnTriggered(Game game) {
        return CardUtil.getCardZoneString(
                "lastTurnTriggered|" + getOriginalId(), getSourceId(), game
        );
    }

    // Used for triggers with a per-turn limit.
    private String getKeyLastTurnTriggeredCount(Game game) {
        return CardUtil.getCardZoneString(
                "lastTurnTriggeredCount|" + getOriginalId(), getSourceId(), game
        );
    }

    private void setLastTrigger(Game game) {
        if (triggerLimitEachTurn == Integer.MAX_VALUE) {
            return;
        }
        String keyLastTurnTriggered = getKeyLastTurnTriggered(game);
        String keyLastTurnTriggeredCount = getKeyLastTurnTriggeredCount(game);
        Integer lastTurn = (Integer) game.getState().getValue(keyLastTurnTriggered);
        int currentTurn = game.getTurnNum();
        if (lastTurn != null && lastTurn == currentTurn) {
            // Ability already triggered this turn, incrementing the count.
            int lastCount = Optional.ofNullable((Integer) game.getState().getValue(keyLastTurnTriggeredCount)).orElse(0);
            game.getState().setValue(keyLastTurnTriggeredCount, lastCount + 1);
        } else {
            // first trigger for Ability this turn.
            game.getState().setValue(keyLastTurnTriggered, currentTurn);
            game.getState().setValue(keyLastTurnTriggeredCount, 1);
        }
    }

    @Override
    public TriggeredAbilityImpl setTriggerPhrase(String triggerPhrase) {
        this.triggerPhrase = triggerPhrase;
        return this;
    }

    @Override
    public void setTriggerEvent(GameEvent triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    @Override
    public GameEvent getTriggerEvent() {
        return triggerEvent;
    }

    @Override
    public boolean checkTriggeredLimit(Game game) {
        return getRemainingTriggersLimitEachTurn(game) > 0;
    }

    @Override
    public boolean checkUsedAlready(Game game) {
        if (!doOnlyOnceEachTurn) {
            return false;
        }
        Integer lastTurnUsed = (Integer) game.getState().getValue(
                CardUtil.getCardZoneString("lastTurnUsed" + getOriginalId(), sourceId, game)
        );
        return lastTurnUsed != null && lastTurnUsed == game.getTurnNum();
    }

    @Override
    public TriggeredAbility setTriggersLimitEachTurn(int limit) {
        this.triggerLimitEachTurn = limit;
        return this;
    }

    @Override
    public int getRemainingTriggersLimitEachTurn(Game game) {
        if (triggerLimitEachTurn == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        String keyLastTurnTriggered = getKeyLastTurnTriggered(game);
        Integer lastTurn = (Integer) game.getState().getValue(keyLastTurnTriggered);
        int currentTurn = game.getTurnNum();
        if (lastTurn != null && lastTurn == currentTurn) {
            // Ability already triggered this turn, so returning the limit minus the count this turn
            String keyLastTurnTriggeredCount = getKeyLastTurnTriggeredCount(game);
            int count = Optional.ofNullable((Integer) game.getState().getValue(keyLastTurnTriggeredCount)).orElse(0);
            return Math.max(0, triggerLimitEachTurn - count);
        } else {
            // Ability did not trigger this turn, so returning the limit
            return triggerLimitEachTurn;
        }
    }

    @Override
    public TriggeredAbility setDoOnlyOnceEachTurn(boolean doOnlyOnce) {
        this.optional = true;
        this.doOnlyOnceEachTurn = doOnlyOnce;
        return this;
    }

    @Override
    public TriggeredAbility withRuleTextReplacement(boolean replaceRuleText) {
        this.replaceRuleText = replaceRuleText;
        return this;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return true;
    }

    @Override
    public boolean resolve(Game game) {
        if (!checkInterveningIfClause(game)) {
            return false;
        }
        if (isOptional()) {
            MageObject object = game.getObject(getSourceId());
            Player player = game.getPlayer(this.getControllerId());
            if (player == null || object == null
                    || (doOnlyOnceEachTurn && checkUsedAlready(game))
                    || !player.chooseUse(
                    getEffects().getOutcome(this),
                    this.getRule(object.getLogName()), this, game
            )) {
                return false;
            }
        }
        if (doOnlyOnceEachTurn) {
            game.getState().setValue(CardUtil.getCardZoneString(
                    "lastTurnUsed" + getOriginalId(), sourceId, game
            ), game.getTurnNum());
        }
        //20091005 - 603.4
        if (!super.resolve(game)) {
            return false;
        }
        return true;
    }

    @Override
    public String getGameLogMessage(Game game) {
        MageObject object = game.getObject(sourceId);
        StringBuilder sb = new StringBuilder();
        if (object != null) {
            sb.append("Ability triggers: ").append(object.getLogName()).append(" - ").append(this.getRule(object.getLogName()));
        } else {
            sb.append("Ability triggers: ").append(this.getRule());
        }
        String targetText = getTargetDescriptionForLog(getTargets(), game);
        if (!targetText.isEmpty()) {
            sb.append(" - ").append(targetText);
        }
        return sb.toString();
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        String prefix;
        if (abilityWord != null) {
            prefix = abilityWord.formatWord();
        } else if (flavorWord != null) {
            prefix = CardUtil.italicizeWithEmDash(flavorWord);
        } else {
            prefix = "";
        }
        sb.append(prefix);

        sb.append(triggerPhrase == null ? "" : triggerPhrase);

        String superRule = super.getRule(true);
        if (!superRule.isEmpty()) {
            String ruleLow = superRule.toLowerCase(Locale.ENGLISH);
            if (isOptional()) {
                if (ruleLow.startsWith("you ")) {
                    if (!ruleLow.startsWith("you may")) {
                        StringBuilder newRule = new StringBuilder(superRule);
                        newRule.insert(4, "may ");
                        superRule = newRule.toString();
                    }
                } else if (!ruleLow.startsWith("{this}")
                        && (this.getTargets().isEmpty()
                        || startsWithVerb(ruleLow))) {
                    sb.append("you may ");
                } else if (!ruleLow.startsWith("its controller may")) {
                    sb.append("you may have ");
                    superRule = superRule.replaceFirst(" (become|block|deal|discard|gain|get|lose|mill|sacrifice)s? ", " $1 ");
                }
            }
            if (replaceRuleText && triggerPhrase != null) {
                superRule = superRule.replaceFirst("^((?:you may )?sacrifice |(put|remove) [^ ]+ [^ ]+ counters? (on|from) |return |transform |untap |regenerate )?\\{this\\}", "$1it");
            }
            sb.append(superRule);
            if (triggerLimitEachTurn != Integer.MAX_VALUE) {
                sb.append(" This ability triggers only ");
                switch (triggerLimitEachTurn) {
                    case 1:
                        sb.append("once");
                        break;
                    case 2:
                        sb.append("twice");
                        break;
                    default:
                        // No card with that behavior yet, so feel free to change the text once one exist
                        sb.append(CardUtil.numberToText(triggerLimitEachTurn) + " times");
                }
                sb.append(" each turn.");
            }
            if (doOnlyOnceEachTurn) {
                sb.append(" Do this only once each turn.");
            }
        }
        return sb.toString();
    }

    private static boolean startsWithVerb(String ruleLow) {
        return ruleLow.startsWith("attach")
                || ruleLow.startsWith("change")
                || ruleLow.startsWith("counter")
                || ruleLow.startsWith("create")
                || ruleLow.startsWith("destroy")
                || ruleLow.startsWith("distribute")
                || ruleLow.startsWith("sacrifice")
                || ruleLow.startsWith("exchange")
                || ruleLow.startsWith("exile")
                || ruleLow.startsWith("gain")
                || ruleLow.startsWith("goad")
                || ruleLow.startsWith("have")
                || ruleLow.startsWith("move")
                || ruleLow.startsWith("prevent")
                || ruleLow.startsWith("put")
                || ruleLow.startsWith("remove")
                || ruleLow.startsWith("return")
                || ruleLow.startsWith("shuffle")
                || ruleLow.startsWith("turn")
                || ruleLow.startsWith("tap")
                || ruleLow.startsWith("untap");
    }

    /**
     * For use in generating trigger phrases with correct text
     *
     * @return "When " for an effect that always removes the source from the battlefield, otherwise "Whenever "
     */
    protected final String getWhen() {
        return (!optional && getAllEffects().stream().anyMatch(
                effect -> effect instanceof SacrificeSourceEffect
                        || effect instanceof ReturnToHandSourceEffect
                        || effect instanceof ShuffleIntoLibrarySourceEffect
                        || effect instanceof ExileSourceEffect
                        || effect instanceof FlipSourceEffect
                        || effect instanceof DestroySourceEffect
        ) ? "When " : "Whenever ");
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {

        /**
         * 603.6. Trigger events that involve objects changing zones are called
         * “zone-change triggers.” Many abilities with zone-change triggers
         * attempt to do something to that object after it changes zones. During
         * resolution, these abilities look for the object in the zone that it
         * moved to. If the object is unable to be found in the zone it went to,
         * the part of the ability attempting to do something to the object will
         * fail to do anything. The ability could be unable to find the object
         * because the object never entered the specified zone, because it left
         * the zone before the ability resolved, or because it is in a zone that
         * is hidden from a player, such as a library or an opponent's hand.
         * (This rule applies even if the object leaves the zone and returns
         * again before the ability resolves.) The most common zone-change
         * triggers are enters-the-battlefield triggers and
         * leaves-the-battlefield triggers.
         *
         * from:
         * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/537065-ixidron-and-kozilek
         * There are two types of triggers that involve the graveyard: dies
         * triggers (which are a subset of leave-the-battlefield triggers) and
         * put into the graveyard from anywhere triggers.
         *
         * The former triggers trigger based on the game state prior to the move
         * where the Kozilek permanent is face down and has no abilities. The
         * latter triggers trigger from the game state after the move where the
         * Kozilek card is itself and has the ability.
         */

        Set<UUID> eventTargets = CardUtil.getEventTargets(event);
        if (!eventTargets.contains(getSourceId())) {
            return super.isInUseableZone(game, source, event);
        }

        switch (event.getType()) {
            case ZONE_CHANGE:
                ZoneChangeEvent zce = (ZoneChangeEvent) event;
                if (eventTargets.contains(getSourceId()) && !zce.getToZone().isPublicZone()) {
                    // If an ability triggers when the object that has it is put into a hidden zone from a graveyard,
                    // that ability triggers from the graveyard, (such as Golgari Brownscale),
                    // Yixlid Jailer will prevent that ability from triggering.
                    if (zce.getFromZone().match(Zone.GRAVEYARD)) {
                        if (!CardUtil.cardHadAbility(this, game.getLastKnownInformationCard(getSourceId(), zce.getFromZone()), getSourceId(), game)) {
                            return false;
                        }
                    }
                }
                if (isLeavesTheBattlefieldTrigger()) {
                    source = zce.getTarget();
                }
                break;
            case DESTROYED_PERMANENT:
                if (isLeavesTheBattlefieldTrigger()) {
                    source = game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD);
                }
                break;
        }
        return super.isInUseableZone(game, source, event);
    }

    /*
     603.6c Leaves-the-battlefield abilities, 603.6d
        if true the game “looks back in time” to determine if those abilities trigger,
        using the existence of those abilities and the appearance of objects immediately prior to the event (603.10)
     */
    @Override
    public boolean isLeavesTheBattlefieldTrigger() {
        return leavesTheBattlefieldTrigger;
    }

    /*
     603.6c,603.6d
     This has to be set, if the triggered ability has to check back in time if the permanent the ability is connected to had the ability on the battlefield while the trigger is checked
     */
    @Override
    public final void setLeavesTheBattlefieldTrigger(boolean leavesTheBattlefieldTrigger) {
        this.leavesTheBattlefieldTrigger = leavesTheBattlefieldTrigger;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public TriggeredAbility setOptional() {
        this.optional = true;

        if (getEffects().stream().anyMatch(
                effect -> effect instanceof DoIfCostPaid && ((DoIfCostPaid) effect).isOptional())) {
            throw new IllegalArgumentException(
                    "DoIfCostPaid effect must have only one optional settings, but it have two (trigger + DoIfCostPaid): "
                            + this.getClass().getSimpleName());
        }

        return this;
    }

    @Override
    public TriggeredAbilityImpl setAbilityWord(AbilityWord abilityWord) {
        super.setAbilityWord(abilityWord);
        return this;
    }

    public static boolean isInUseableZoneDiesTrigger(TriggeredAbility source, GameEvent event, Game game) {
        // Get the source permanent of the ability
        MageObject sourceObject = null;
        if (game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
            sourceObject = game.getPermanent(source.getSourceId());
        } else {
            // TODO: multiple calls of ApplyEffects all around the code are breaking a short living lki idea
            //  (PlayerImpl's call to move to battlefield do the worse thing)
            //  -
            //  Original idea: short living LKI must help to find a moment in the inner of resolve
            //  -
            //  Example:
            //   --!---------------!-------------!-----!-----------!
            //   - !     steps     !  perm zone  ! LKI ! short LKI !
            //   --!---------------!-------------!-----!-----------!
            //   - ! resolve start ! battlefield ! no  !   no      !
            //   - ! step 1        ! battlefield ! no  !   no      ! permanent moving to graveyard by step's command
            //   - ! step 2        !  graveyard  ! yes !   yes     ! other commands
            //   - ! step 3        !  graveyard  ! yes !   yes     ! other commands
            //   - ! raise triggers!  graveyard  ! yes !   yes     ! handle and put triggers that was raised in resolve steps
            //   - ! resolve end   !  graveyard  ! yes !   no      !
            //   - ! resolve next  !  graveyard  ! yes !   no      ! resolve next spell
            //   - ! empty stack   !  graveyard  ! no  !   no      ! no more to resolve
            //   --!---------------!-------------!-----!-----------!
            //  -
            //  - Problem 1: move code (well, not only move) calls ApplyEffects in the middle of the resolve
            //  - and reset short LKI (after short LKI reset dies trigger will not work)
            //  - Example: Goblin Welder calls sacrifice and card move in the same effect - but move call do
            //  - a reset and dies trigger ignored (trigger thinks that permanent already dies)
            //  -
            //  - Possible fix:
            //  - replace ApplyEffects in the move code by game.getState().processAction(game);
            //  - check and fix many broken (is it was a false positive test or something broken)
            //sourceObject = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (game.getShortLivingLKI(source.getSourceId(), Zone.BATTLEFIELD)) {
                sourceObject = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            }
        }
        if (sourceObject == null) { // source is no permanent
            sourceObject = game.getObject(source);
            if (sourceObject == null || sourceObject.isPermanent(game)) {
                return false; // No source object found => ability is not valid
            }
        }

        if (!source.hasSourceObjectAbility(game, sourceObject, event)) {
            return false; // the permanent does currently not have or before it dies the ability so no trigger
        }

        // check now it is in graveyard (only if it is no token and was the target itself)
        if (source.getSourceId().equals(event.getTargetId()) // source is also the target
                && !(sourceObject instanceof PermanentToken) // it's no token
                && sourceObject.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(source.getSourceId())) { // It's in the next zone
            Zone after = game.getState().getZone(source.getSourceId());
            if (!Zone.GRAVEYARD.match(after)) { // Zone is not the graveyard
                return false; // Moving to graveyard was replaced so no trigger
            }
        }

        return true;
    }
}
