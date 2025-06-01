package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, Susucr
 */
public class CantBeCounteredControlledEffect extends ContinuousRuleModifyingEffectImpl {

    private FilterSpell filterTarget; // what can't be countered
    private FilterStackObject filterSource; // can filter on what is countering

    public CantBeCounteredControlledEffect(FilterSpell filterTarget, Duration duration) {
        this(filterTarget, null, duration);
    }

    public CantBeCounteredControlledEffect(FilterSpell filterTarget, FilterStackObject filterSource, Duration duration) {
        super(duration, Outcome.Benefit);
        this.filterTarget = filterTarget;
        this.filterSource = filterSource;
        setText();
    }

    protected CantBeCounteredControlledEffect(final CantBeCounteredControlledEffect effect) {
        super(effect);
        this.filterTarget = effect.filterTarget.copy();
        if (effect.filterSource != null) {
            this.filterSource = effect.filterSource.copy();
        }
    }

    @Override
    public CantBeCounteredControlledEffect copy() {
        return new CantBeCounteredControlledEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID controllerId = source.getControllerId();
        Spell spell = game.getStack().getSpell(event.getTargetId());
        StackObject counterSource = game.getStack().getStackObject(event.getSourceId());
        return spell != null
                && spell.isControlledBy(controllerId)
                && filterTarget.match(spell, controllerId, source, game)
                && (filterSource == null || filterSource.match(counterSource, controllerId, source, game));
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
