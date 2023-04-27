package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author awjackson
 */

public class AssignNoCombatDamageTargetEffect extends ReplacementEffectImpl {

    public AssignNoCombatDamageTargetEffect() {
        this(Duration.EndOfTurn, "if you do, it assigns no combat damage this turn");
    }

    public AssignNoCombatDamageTargetEffect(Duration duration, String text) {
        super(duration, Outcome.PreventDamage);
        staticText = text;
    }

    public AssignNoCombatDamageTargetEffect(final AssignNoCombatDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public AssignNoCombatDamageTargetEffect copy() {
        return new AssignNoCombatDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return ((DamageEvent) event).isCombatDamage() && event.getSourceId().equals(targetPointer.getFirst(game, source));
    }
}
