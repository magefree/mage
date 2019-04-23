/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author antoni-g
 */
public class PreventDamageAndRemoveCountersEffect extends PreventionEffectImpl {

    public PreventDamageAndRemoveCountersEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If damage would be dealt to {this}, prevent that damage and remove that many +1/+1 counters from it";
    }

    public PreventDamageAndRemoveCountersEffect(final PreventDamageAndRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageAndRemoveCountersEffect copy() {
        return new PreventDamageAndRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        preventDamageAction(event, source, game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeCounters(CounterType.P1P1.createInstance(damage), game); //MTG ruling (this) loses counters even if the damage isn't prevented
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }
}
