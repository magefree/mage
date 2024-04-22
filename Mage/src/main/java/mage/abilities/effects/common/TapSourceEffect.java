package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TapSourceEffect extends OneShotEffect {

    private boolean withoutTrigger;

    public TapSourceEffect() {
        this(false);
    }

    public TapSourceEffect(boolean withoutTrigger) {
        super(Outcome.Tap);
        this.withoutTrigger = withoutTrigger;
        staticText = "tap {this}";
    }

    protected TapSourceEffect(final TapSourceEffect effect) {
        super(effect);
        this.withoutTrigger = effect.withoutTrigger;
    }

    @Override
    public TapSourceEffect copy() {
        return new TapSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (permanent != null) {
            if (withoutTrigger) {
                permanent.setTapped(true);
            } else {
                permanent.tap(source, game);
            }
            return true;
        }
        return false;
    }

}
