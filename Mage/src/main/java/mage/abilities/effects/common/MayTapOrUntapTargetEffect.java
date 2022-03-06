package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Loki
 */
public class MayTapOrUntapTargetEffect extends OneShotEffect {

    public MayTapOrUntapTargetEffect() {
        super(Outcome.AIDontUseIt);
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
                if (player.chooseUse(Outcome.Untap, "Untap that permanent?", source, game)) {
                    target.untap(game);
                }
            } else if (player.chooseUse(Outcome.Tap, "Tap that permanent?", source, game)) {
                target.tap(source, game);
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
        if (!staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().isEmpty()) {
            return "you may tap or untap it";
        } else {
            String targetName = mode.getTargets().get(0).getTargetName();
            return "you may tap or untap " + (targetName.contains("target") ? "" : "target ") + targetName;
        }
    }
}
