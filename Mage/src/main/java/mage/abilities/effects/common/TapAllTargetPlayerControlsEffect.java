package mage.abilities.effects.common;

import java.util.List;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LoneFox
 */
public class TapAllTargetPlayerControlsEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public TapAllTargetPlayerControlsEffect(FilterPermanent filter) {
        super(Outcome.Tap);
        this.filter = filter;
    }

    protected TapAllTargetPlayerControlsEffect(final TapAllTargetPlayerControlsEffect effect) {
        super(effect);
        filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, player.getId(), game);
            for (Permanent p : permanents) {
                p.tap(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public TapAllTargetPlayerControlsEffect copy() {
        return new TapAllTargetPlayerControlsEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "tap all " + filter.getMessage() + ' '
                + getTargetPointer().describeTargets(mode.getTargets(), "that player")
                + " controls";
    }
}
