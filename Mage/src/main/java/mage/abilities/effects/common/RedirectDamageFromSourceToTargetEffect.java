/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class RedirectDamageFromSourceToTargetEffect extends RedirectionEffect {

    public RedirectDamageFromSourceToTargetEffect(Duration duration, int amountToRedirect, boolean oneUsage) {
        super(duration, amountToRedirect, oneUsage);
        staticText = "The next " + amountToRedirect + " damage that would be dealt to {this} this turn is dealt to target creature you control instead.";
    }

    public RedirectDamageFromSourceToTargetEffect(final RedirectDamageFromSourceToTargetEffect effect) {
        super(effect);
    }

    @Override
    public RedirectDamageFromSourceToTargetEffect copy() {
        return new RedirectDamageFromSourceToTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            this.redirectTarget = source.getTargets().get(0);
            return true;
        }
        return false;
    }
}
