package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 * @author LevelX2
 */
public class PreventAllDamageFromChosenSourceToYouEffect extends PreventionEffectImpl {

    protected final TargetSource targetSource;

    public PreventAllDamageFromChosenSourceToYouEffect(Duration duration) {
        this(duration, new FilterObject("source"));
    }

    public PreventAllDamageFromChosenSourceToYouEffect(Duration duration, FilterObject filter) {
        this(duration, filter, false);
    }

    public PreventAllDamageFromChosenSourceToYouEffect(Duration duration, FilterObject filter, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.targetSource = new TargetSource(filter);
        this.staticText = setText();
    }

    protected PreventAllDamageFromChosenSourceToYouEffect(final PreventAllDamageFromChosenSourceToYouEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public PreventAllDamageFromChosenSourceToYouEffect copy() {
        return new PreventAllDamageFromChosenSourceToYouEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        // be sure to note the target source's zcc, etc, if able.
        if (targetSource.getFirstTarget() != null) {
            this.targetSource.updateTarget(targetSource.getFirstTarget(), game);
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())
                    && event.getSourceId().equals(targetSource.getFirstTarget())
                    && targetSource.isLegal(source, game)) {  // source is blinked, becomes a new object, etc.
                return true;
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt to you ");
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn ");
        }
        sb.append("by a ").append(targetSource.getFilter().getMessage());
        sb.append(" of your choice");
        return sb.toString();
    }

}
