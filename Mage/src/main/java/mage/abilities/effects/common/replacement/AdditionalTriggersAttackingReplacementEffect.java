package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class AdditionalTriggersAttackingReplacementEffect extends ReplacementEffectImpl {

    private final boolean onlyControlled;

    public AdditionalTriggersAttackingReplacementEffect(boolean onlyControlled) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.onlyControlled = onlyControlled;
        staticText = "if a creature " + (onlyControlled ? "you control " : "") + "attacking causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private AdditionalTriggersAttackingReplacementEffect(final AdditionalTriggersAttackingReplacementEffect effect) {
        super(effect);
        this.onlyControlled = effect.onlyControlled;
    }

    @Override
    public AdditionalTriggersAttackingReplacementEffect copy() {
        return new AdditionalTriggersAttackingReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        Permanent sourcePermanent = game.getPermanent(numberOfTriggersEvent.getSourceId());
        if (sourcePermanent == null || !sourcePermanent.isControlledBy(source.getControllerId())) {
            return false;
        }
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }

        switch (sourceEvent.getType()) {
            case ATTACKER_DECLARED:
                return !onlyControlled || source.isControlledBy(sourceEvent.getPlayerId());
            case DECLARED_ATTACKERS:
                return !onlyControlled || game
                        .getCombat()
                        .getAttackers()
                        .stream()
                        .map(game::getControllerId)
                        .anyMatch(source::isControlledBy);
            case DEFENDER_ATTACKED:
                return !onlyControlled || ((DefenderAttackedEvent) sourceEvent)
                        .getAttackers(game)
                        .stream()
                        .map(Controllable::getControllerId)
                        .anyMatch(source::isControlledBy);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
