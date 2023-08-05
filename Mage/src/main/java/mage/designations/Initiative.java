package mage.designations;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class Initiative extends Designation {

    public Initiative() {
        super(DesignationType.THE_INITIATIVE);

        // Whenever one or more creatures a player controls deals combat damage to you, that player takes the initiative.
        this.addAbility(new InitiativeDamageTriggeredAbility());

        // Whenever you take the initiative and at the beginning of your upkeep, venture into Undercity.
        this.addAbility(new InitiativeVentureTriggeredAbility());
    }

    private Initiative(final Initiative card) {
        super(card);
    }

    @Override
    public Initiative copy() {
        return new Initiative(this);
    }
}

class InitiativeDamageTriggeredAbility extends TriggeredAbilityImpl {

    InitiativeDamageTriggeredAbility() {
        super(Zone.ALL, new InitiativeTakeEffect());
    }

    private InitiativeDamageTriggeredAbility(final InitiativeDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InitiativeDamageTriggeredAbility copy() {
        return new InitiativeDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent dEvent = (DamagedPlayerBatchEvent) event;
        UUID playerId = dEvent
                .getEvents()
                .stream()
                .filter(DamagedEvent::isCombatDamage)
                .filter(e -> e.getTargetId().equals(game.getInitiativeId()))
                .map(GameEvent::getSourceId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .findFirst()
                .orElse(null);
        if (playerId == null) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(playerId));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures a player controls deals combat damage to you, that player takes the initiative.";
    }
}

class InitiativeTakeEffect extends OneShotEffect {

    InitiativeTakeEffect() {
        super(Outcome.Benefit);
    }

    private InitiativeTakeEffect(final InitiativeTakeEffect effect) {
        super(effect);
    }

    @Override
    public InitiativeTakeEffect copy() {
        return new InitiativeTakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.takeInitiative(source, getTargetPointer().getFirst(game, source));
        return true;
    }
}

class InitiativeVentureTriggeredAbility extends TriggeredAbilityImpl {

    InitiativeVentureTriggeredAbility() {
        super(Zone.ALL, new InitiativeUndercityEffect());
    }

    private InitiativeVentureTriggeredAbility(final InitiativeVentureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InitiativeVentureTriggeredAbility copy() {
        return new InitiativeVentureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE
                || event.getType() == GameEvent.EventType.TOOK_INITIATIVE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID playerId;
        switch (event.getType()) {
            case UPKEEP_STEP_PRE:
                if (!game.isActivePlayer(game.getInitiativeId())) {
                    return false;
                }
                playerId = game.getActivePlayerId();
                break;
            case TOOK_INITIATIVE:
                playerId = event.getPlayerId();
                break;
            default:
                return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(playerId));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you take the initiative and at the beginning of your upkeep, venture into Undercity.";
    }
}

class InitiativeUndercityEffect extends OneShotEffect {

    InitiativeUndercityEffect() {
        super(Outcome.Benefit);
    }

    private InitiativeUndercityEffect(final InitiativeUndercityEffect effect) {
        super(effect);
    }

    @Override
    public InitiativeUndercityEffect copy() {
        return new InitiativeUndercityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.ventureIntoDungeon(getTargetPointer().getFirst(game, source), true);
        return true;
    }
}
