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
 * @author LevelX2
 */

public class AssignNoCombatDamageSourceEffect extends ReplacementEffectImpl {

    public AssignNoCombatDamageSourceEffect(Duration duration) {
        this(duration, false);
    }

    public AssignNoCombatDamageSourceEffect(Duration duration, boolean partOfOptionalEffect) {
        super(duration, Outcome.PreventDamage);
        staticText = setText(partOfOptionalEffect);
    }

    public AssignNoCombatDamageSourceEffect(final AssignNoCombatDamageSourceEffect effect) {
        super(effect);
    }

    @Override
    public AssignNoCombatDamageSourceEffect copy() {
        return new AssignNoCombatDamageSourceEffect(this);
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
        DamageEvent damageEvent = (DamageEvent) event;
        return event.getSourceId().equals(source.getSourceId()) && damageEvent.isCombatDamage();
    }

    private String setText(boolean partOfOptionalEffect) {
        String text = (partOfOptionalEffect ? "If you do, " : "") + "{this} assigns no combat damage";
        switch(duration) {
            case EndOfTurn:
                text += " this turn";
                break;
            case EndOfCombat:
                text += " this combat";
                break;
            default:
                if (!duration.toString().isEmpty()) {
                    text += ' ' + duration.toString();
                }
        }
        return text;
    }
}
