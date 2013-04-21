package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Loki
 */
public class MayTapOrUntapTargetEffect extends OneShotEffect<MayTapOrUntapTargetEffect> {
    public MayTapOrUntapTargetEffect() {
        super(Constants.Outcome.Benefit);
    }

    public MayTapOrUntapTargetEffect(final MayTapOrUntapTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (target != null && player != null) {
            if (target.isTapped()) {
                if (player.chooseUse(Constants.Outcome.Untap, "Untap that permanent?", game)) {
                    target.untap(game);
                }
            } else {
                if (player.chooseUse(Constants.Outcome.Tap, "Tap that permanent?", game)) {
                    target.tap(game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MayTapOrUntapTargetEffect copy() {
        return new MayTapOrUntapTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (mode.getTargets().isEmpty()) {
            return "You may tap or untap it";
        } else {
            return "You may tap or untap target " + mode.getTargets().get(0).getTargetName();
        }
    }
}
