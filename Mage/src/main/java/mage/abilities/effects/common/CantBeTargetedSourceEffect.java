
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CantBeTargetedSourceEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterObject filterSource;

    public CantBeTargetedSourceEffect(FilterObject filterSource, Duration duration) {
        super(duration, Outcome.Benefit);
        this.filterSource = filterSource;
        setText();
    }

    public CantBeTargetedSourceEffect(final CantBeTargetedSourceEffect effect) {
        super(effect);
        this.filterSource = effect.filterSource.copy();
    }

    @Override
    public CantBeTargetedSourceEffect copy() {
        return new CantBeTargetedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            MageObject sourceObject;
            if (stackObject instanceof StackAbility) {
                sourceObject = ((StackAbility) stackObject).getSourceObject(game);
            } else {
                sourceObject = stackObject;
            }
            if (filterSource.match(sourceObject, game)) {
                return true;
            }
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("{this} can't be the target of ");
        sb.append(filterSource.getMessage());
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        staticText = sb.toString();
    }

}
