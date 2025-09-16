package mage.abilities;

import mage.MageObject;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.constants.AbilityType;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.BatchEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
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
    private Condition interveningIfCondition;
    private Condition triggerCondition;
    private boolean leavesTheBattlefieldTrigger;
    private int triggerLimitEachTurn = Integer.MAX_VALUE; // for "triggers only once|twice each turn"
    private int triggerLimitEachGame = Integer.MAX_VALUE; // for "triggers only once|twice"
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
        this.interveningIfCondition = ability.interveningIfCondition;
        this.triggerCondition = ability.triggerCondition;
        this.leavesTheBattlefieldTrigger = ability.leavesTheBattlefieldTrigger;
        this.triggerLimitEachTurn = ability.triggerLimitEachTurn;
        this.triggerLimitEachGame = ability.triggerLimitEachGame;
        this.doOnlyOnceEachTurn = ability.doOnlyOnceEachTurn;
        this.replaceRuleText = ability.replaceRuleText;
        this.triggerEvent = ability.triggerEvent;
        this.triggerPhrase = ability.triggerPhrase;
    }

    @Override
    public void trigger(Game game, UUID controllerId, GameEvent triggeringEvent) {
        //20091005 - 603.4
        if (checkInterveningIfClause(game) && checkTriggerCondition(game)) {
            updateTurnCount(game);
            updateGameCount(game);
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

    // Used for triggers with a per-game limit.
    private String getKeyGameTriggeredCount(Game game) {
        return CardUtil.getCardZoneString(
                "gameTriggeredCount|" + getOriginalId(), getSourceId(), game
        );
    }

    private void updateTurnCount(Game game) {
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

    private void updateGameCount(Game game) {
        if (triggerLimitEachGame == Integer.MAX_VALUE) {
            return;
        }
        String keyGameTriggeredCount = getKeyGameTriggeredCount(game);
        int lastCount = Optional.ofNullable((Integer) game.getState().getValue(keyGameTriggeredCount)).orElse(0);
        // Incrementing the count.
        game.getState().setValue(keyGameTriggeredCount, lastCount + 1);
    }

    @Override
    public TriggeredAbilityImpl setTriggerPhrase(String triggerPhrase) {
        this.triggerPhrase = triggerPhrase;
        return this;
    }

    @Override
    public String getTriggerPhrase() {
        return this.triggerPhrase;
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
        return getRemainingTriggersLimitEachGame(game) > 0 && getRemainingTriggersLimitEachTurn(game) > 0;
    }

    @Override
    public boolean checkUsedAlready(Game game) {
        return doOnlyOnceEachTurn && TriggeredAbility.checkDidThisTurn(this, game);
    }

    @Override
    public TriggeredAbility setTriggersLimitEachTurn(int limit) {
        this.triggerLimitEachTurn = limit;
        return this;
    }

    @Override
    public TriggeredAbility setTriggersLimitEachGame(int limit) {
        this.triggerLimitEachGame = limit;
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
    public int getRemainingTriggersLimitEachGame(Game game) {
        if (triggerLimitEachGame == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        String keyGameTriggeredCount = getKeyGameTriggeredCount(game);
        int count = Optional.ofNullable((Integer) game.getState().getValue(keyGameTriggeredCount)).orElse(0);
        return Math.max(0, triggerLimitEachGame - count);
    }

    @Override
    public TriggeredAbility setDoOnlyOnceEachTurn(boolean doOnlyOnce) {
        this.doOnlyOnceEachTurn = doOnlyOnce;
        if (CardUtil.castStream(this.getAllEffects(), DoIfCostPaid.class).noneMatch(DoIfCostPaid::isOptional)) {
            this.optional = true;
        }
        return this;
    }

    @Override
    public TriggeredAbility withRuleTextReplacement(boolean replaceRuleText) {
        this.replaceRuleText = replaceRuleText;
        return this;
    }

    @Override
    public TriggeredAbility withInterveningIf(Condition interveningIfCondition) {
        this.interveningIfCondition = interveningIfCondition;
        this.replaceRuleText = false;
        return this;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return interveningIfCondition == null || interveningIfCondition.apply(game, this);
    }

    @Override
    public TriggeredAbility withTriggerCondition(Condition condition) {
        this.triggerCondition = condition;
        if (this.triggerPhrase != null && !condition.toString().isEmpty()) {
            this.setTriggerPhrase(
                    this.triggerPhrase.substring(0, this.triggerPhrase.length() - 2) + ' ' +
                            (condition.toString().startsWith("during") ? "" : "while ") + condition + ", "
            );
        }
        return this;
    }

    @Override
    public Condition getTriggerCondition() {
        return triggerCondition;
    }

    @Override
    public boolean checkTriggerCondition(Game game) {
        return triggerCondition == null || triggerCondition.apply(game, this);
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
            if (doOnlyOnceEachTurn) {
                TriggeredAbility.setDidThisTurn(this, game);
            }
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
        sb.append(triggerPhrase == null ? "" : triggerPhrase);

        if (interveningIfCondition != null) {
            String conditionText = interveningIfCondition.toString();
            if (!conditionText.isEmpty()) { // e.g. CaseSolveAbility
                if (replaceRuleText && triggerPhrase != null && triggerPhrase.contains("{this}")) {
                    conditionText = conditionText.replace("{this}", "it");
                    if (conditionText.startsWith("it is ")) {
                        conditionText = conditionText.replace("it is ", "it's ");
                    }
                }
                if (!conditionText.startsWith("if ")) {
                    sb.append("if ");
                }
                sb.append(conditionText).append(", ");
            }
        }

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
                superRule = superRule.replaceFirst("^((?:you may )?sacrifice |(put|remove) [^ ]+ [^ ]+ counters? (on|from) |return |transform |untap |regenerate |attach |exile )?\\{this\\}", "$1it");
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
                        sb.append(CardUtil.numberToText(triggerLimitEachTurn)).append(" times");
                }
                sb.append(" each turn.");
            }
            if (triggerLimitEachGame != Integer.MAX_VALUE) {
                sb.append(" This ability triggers only ");
                switch (triggerLimitEachGame) {
                    case 1:
                        sb.append("once.");
                        break;
                    case 2:
                        // No card with that behavior yet, so feel free to change the text once one exist
                        sb.append("twice.");
                        break;
                    default:
                        // No card with that behavior yet, so feel free to change the text once one exist
                        sb.append(CardUtil.numberToText(triggerLimitEachGame)).append(" times.");
                }
            }
            if (doOnlyOnceEachTurn) {
                sb.append(" Do this only once each turn.");
            }
        }
        return addRulePrefix(sb.toString());
    }

    private static boolean startsWithVerb(String ruleLow) {
        return ruleLow.startsWith("attach")
                || ruleLow.startsWith("cast")
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
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {

        // workaround for singleton abilities like Flying
        UUID affectedSourceId = getRealSourceObjectId(this, sourceObject);

        // 603.6
        // Trigger events that involve objects changing zones are called "zone-change triggers." Many abilities with
        // zone-change triggers attempt to do something to that object after it changes zones. During resolution,
        // these abilities look for the object in the zone that it moved to. If the object is unable to be found
        // in the zone it went to, the part of the ability attempting to do something to the object will fail to
        // do anything. The ability could be unable to find the object because the object never entered the
        // specified zone, because it left the zone before the ability resolved, or because it is in a zone that
        // is hidden from a player, such as a library or an opponent’s hand. (This rule applies even if the
        // object leaves the zone and returns again before the ability resolves.) The most common zone-change
        // triggers are enters-the-battlefield triggers and leaves-the-battlefield triggers.

        // There are possible two different use cases:
        // * look in current game state (normal events):
        // * look back in time (leaves battlefield, dies, etc);

        // TODO: need sync or shared code with AbilityImpl.isInUseableZone
        MageObject affectedSourceObject = sourceObject;
        if (event == null) {
            // state base triggers - use only actual state
        } else {
            // event triggers - can look back in time for some use cases
            switch (event.getType()) {
                case ZONE_CHANGE:
                    ZoneChangeEvent zce = (ZoneChangeEvent) event;
                    Set<UUID> eventTargets = CardUtil.getEventTargets(event);
                    if (eventTargets.contains(getSourceId()) && !zce.getToZone().isPublicZone()) {
                        // TODO: need research and share with AbilityImpl
                        // If an ability triggers when the object that has it is put into a hidden zone from a graveyard,
                        // that ability triggers from the graveyard, (such as Golgari Brownscale),
                        // Yixlid Jailer will prevent that ability from triggering.
                        if (zce.getFromZone().match(Zone.GRAVEYARD)) {
                            if (!CardUtil.cardHadAbility(this, game.getLastKnownInformationCard(getSourceId(), zce.getFromZone()), getSourceId(), game)) {
                                return false;
                            }
                        }
                    }
                    if (isLeavesTheBattlefieldTrigger() && game.checkShortLivingLKI(affectedSourceId, Zone.BATTLEFIELD)) {
                        affectedSourceObject = game.getLastKnownInformation(affectedSourceId, Zone.BATTLEFIELD);
                    }
                    break;
                case DESTROYED_PERMANENT:
                case EXPLOITED_CREATURE:
                case SACRIFICED_PERMANENT:
                    if (isLeavesTheBattlefieldTrigger() && game.checkShortLivingLKI(affectedSourceId, Zone.BATTLEFIELD)) {
                        affectedSourceObject = game.getPermanentOrLKIBattlefield(affectedSourceId);
                    }
                    break;
            }
        }

        return super.isInUseableZone(game, affectedSourceObject, event);
    }

    @Override
    public boolean isLeavesTheBattlefieldTrigger() {
        return leavesTheBattlefieldTrigger;
    }

    @Override
    public final void setLeavesTheBattlefieldTrigger(boolean leavesTheBattlefieldTrigger) {
        this.leavesTheBattlefieldTrigger = leavesTheBattlefieldTrigger;

        // TODO: replace override of isInUseableZone in dies only triggers by like "isDiesOnlyTrigger" here
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public TriggeredAbilityImpl setAbilityWord(AbilityWord abilityWord) {
        super.setAbilityWord(abilityWord);
        return this;
    }

    /**
     * Looking object in GRAVEYARD zone only. If you need multi zone then use default isInUseableZone
     * - good example: Whenever another creature you control dies
     * - bad example: When {this} dies or is put into exile from the battlefield
     * <p>
     * For triggered abilities that function from the battlefield that must trigger when the source permanent dies
     * and/or for any other events that happen simultaneously to the source permanent dying.
     * (Similar logic must be used for any leaves-the-battlefield, but this method assumes to graveyard only.)
     * NOTE: If your ability functions from another zone (not battlefield) then must use standard logic, not this.
     */
    public static boolean isInUseableZoneDiesTrigger(TriggeredAbility sourceAbility, MageObject sourceObject, GameEvent event, Game game) {
        // runtime check: wrong trigger settings
        if (!sourceAbility.isLeavesTheBattlefieldTrigger()) {
            throw new IllegalArgumentException("Wrong code usage: all dies triggers must use setLeavesTheBattlefieldTrigger(true) and override isInUseableZone - "
                    + sourceAbility.getSourceObject(game) + " - " + sourceAbility);
        }

        // runtime check: wrong isInUseableZone for batch related triggers
        if (event instanceof BatchEvent) {
            throw new IllegalArgumentException("Wrong code usage: batch events unsupported here, possible miss of override isInUseableZone - "
                    + sourceAbility.getSourceObject(game) + " - " + sourceAbility);
        }

        // workaround for singleton abilities like Flying
        UUID affectedSourceId = getRealSourceObjectId(sourceAbility, sourceObject);

        // on permanent - can use actual or look back in time
        MageObject affectedObject = null;
        if (game.getState().getZone(affectedSourceId) == Zone.BATTLEFIELD) {
            affectedObject = game.getPermanent(affectedSourceId);
        } else {
            //  The idea: short living LKI must help to find a moment in the inner of resolve
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
            if (game.checkShortLivingLKI(affectedSourceId, Zone.BATTLEFIELD)) {
                affectedObject = game.getLastKnownInformation(affectedSourceId, Zone.BATTLEFIELD);
            }
        }

        if (affectedObject == null) {
            affectedObject = game.getObject(sourceAbility);
            if (affectedObject == null || affectedObject.isPermanent(game)) {
                // if it was a permanent, but now removed then ignore
                return false;
            }
        }

        if (!sourceAbility.hasSourceObjectAbility(game, affectedObject, event)) {
            return false; // the permanent does currently not have or before it dies the ability so no trigger
        }

        // check now it is in graveyard (only if it is no token and was the target itself)
        // TODO: need research
        if (affectedSourceId.equals(event.getTargetId()) // source is also the target
                && !(affectedObject instanceof PermanentToken) // it's no token
                && affectedObject.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(affectedSourceId)) { // It's in the next zone
            Zone after = game.getState().getZone(affectedSourceId);
            if (!Zone.GRAVEYARD.match(after)) { // Zone is not the graveyard
                return false; // Moving to graveyard was replaced so no trigger
            }
        }

        return true;
    }
}
