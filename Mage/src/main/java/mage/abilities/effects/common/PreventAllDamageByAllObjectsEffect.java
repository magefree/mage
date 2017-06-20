/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class PreventAllDamageByAllObjectsEffect extends PreventionEffectImpl {

    private FilterObject filter;

    public PreventAllDamageByAllObjectsEffect(Duration duration) {
        this(null, duration, false);
    }

    public PreventAllDamageByAllObjectsEffect(Duration duration, boolean onlyCombat) {
        this(null, duration, onlyCombat);
    }

    public PreventAllDamageByAllObjectsEffect(FilterObject filter, Duration duration, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.filter = filter;
    }

    public PreventAllDamageByAllObjectsEffect(final PreventAllDamageByAllObjectsEffect effect) {
        super(effect);
        if (effect.filter != null) {
            this.filter = effect.filter.copy();
        }
    }

    @Override
    public PreventAllDamageByAllObjectsEffect copy() {
        return new PreventAllDamageByAllObjectsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage() || !onlyCombat) {
                if (filter == null) {
                    return true;
                }
                MageObject damageSource = game.getObject(damageEvent.getSourceId());
                if (damageSource != null && filter.match(damageSource, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("Prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt");
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        if (filter != null) {
            sb.append(" by ");
            sb.append(filter.getMessage());
        }
        return sb.toString();
    }
}
