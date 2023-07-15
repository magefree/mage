package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CantBeCounteredControlledEffect extends ContinuousRuleModifyingEffectImpl {

    private FilterSpell filterTarget;
    private FilterObject filterSource;

    public CantBeCounteredControlledEffect(FilterSpell filterTarget, FilterObject filterSource, Duration duration) {
        super(duration, Outcome.Benefit);
        this.filterTarget = filterTarget;
        this.filterSource = filterSource;
        setText();
    }

    public CantBeCounteredControlledEffect(FilterSpell filterTarget, Duration duration) {
        this(filterTarget, null, duration);
    }

    public CantBeCounteredControlledEffect(final CantBeCounteredControlledEffect effect) {
        super(effect);
        if (effect.filterTarget != null) {
            this.filterTarget = effect.filterTarget.copy();
        }
        if (effect.filterSource != null) {
            this.filterSource = effect.filterSource.copy();
        }
    }

    @Override
    public CantBeCounteredControlledEffect copy() {
        return new CantBeCounteredControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isControlledBy(source.getControllerId())
                && filterTarget.match(spell, source.getControllerId(), source, game)) {
            if (filterSource == null) {
                return true;
            } else {
                MageObject sourceObject = game.getObject(event.getSourceId());
                if (filterSource.match(sourceObject, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(filterTarget.getMessage());
        if (!filterTarget.getMessage().contains("you control")) {
            sb.append(" you control");
        }
        sb.append(" can't be countered");
        if (filterSource != null) {
            sb.append(" by ").append(filterSource.getMessage());
        }
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        staticText = sb.toString();
    }

}
