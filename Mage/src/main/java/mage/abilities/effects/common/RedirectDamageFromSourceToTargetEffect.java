package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class RedirectDamageFromSourceToTargetEffect extends RedirectionEffect {

    public RedirectDamageFromSourceToTargetEffect(Duration duration, int amountToRedirect, UsageType usageType) {
        super(duration, amountToRedirect, usageType);
        staticText = "The next " + amountToRedirect + " damage that would be dealt to {this} this turn is dealt to target creature you control instead.";
    }

    protected RedirectDamageFromSourceToTargetEffect(final RedirectDamageFromSourceToTargetEffect effect) {
        super(effect);
    }

    @Override
    public RedirectDamageFromSourceToTargetEffect copy() {
        return new RedirectDamageFromSourceToTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (event.getTargetId().equals(source.getSourceId())) {
                if (getTargetPointer().getFirst(game, source) != null) {
                    this.redirectTarget = source.getTargets().get(0);
                    return true;
                }
            }
        }
        return false;
    }
}
