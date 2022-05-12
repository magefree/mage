/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Quercitron
 */
public class PreventNextDamageFromChosenSourceToYouEffect extends PreventionEffectImpl {

    protected final TargetSource targetSource;

    public PreventNextDamageFromChosenSourceToYouEffect(Duration duration) {
        this(duration, new FilterObject("source"));
    }

    public PreventNextDamageFromChosenSourceToYouEffect(Duration duration, FilterObject filter) {
        this(duration, filter, false);
    }

    public PreventNextDamageFromChosenSourceToYouEffect(Duration duration, FilterObject filter, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.targetSource = new TargetSource(filter);
        this.staticText = setText();
    }

    public PreventNextDamageFromChosenSourceToYouEffect(final PreventNextDamageFromChosenSourceToYouEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public PreventNextDamageFromChosenSourceToYouEffect copy() {
        return new PreventNextDamageFromChosenSourceToYouEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        this.used = true;
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(targetSource.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("The next time a ").append(targetSource.getFilter().getMessage());
        sb.append(" of your choice would deal damage to you");
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        sb.append(", prevent that damage");
        return sb.toString();
    }

}
