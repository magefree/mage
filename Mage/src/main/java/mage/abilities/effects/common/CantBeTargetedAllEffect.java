
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CantBeTargetedAllEffect extends ContinuousRuleModifyingEffectImpl {

    private FilterPermanent filterTarget;
    private FilterObject filterSource;

    public CantBeTargetedAllEffect(FilterPermanent filterTarget, Duration duration) {
        this(filterTarget, null, duration);
    }

    public CantBeTargetedAllEffect(FilterPermanent filterTarget, FilterObject filterSource, Duration duration) {
        super(duration, Outcome.Benefit);
        this.filterTarget = filterTarget;
        this.filterSource = filterSource;
        setText();
    }

    public CantBeTargetedAllEffect(final CantBeTargetedAllEffect effect) {
        super(effect);
        if (effect.filterTarget != null) {
            this.filterTarget = effect.filterTarget.copy();
        }
        if (effect.filterSource != null) {
            this.filterSource = effect.filterSource.copy();
        }
    }

    @Override
    public CantBeTargetedAllEffect copy() {
        return new CantBeTargetedAllEffect(this);
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
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && filterTarget.match(permanent, source.getControllerId(), source, game)) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            MageObject sourceObject;
            if (stackObject instanceof StackAbility) {
                if (filterSource instanceof FilterSpell) {
                    // only spells have to be selected
                    return false;
                }
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
        sb.append(filterTarget.getMessage()).append(" can't be the targets of ");
        if (filterSource != null) {
            sb.append(filterSource.getMessage());
        } else {
            sb.append("spells");
        }
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        staticText = sb.toString();
    }

}
