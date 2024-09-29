package mage.abilities.effects.common.discard;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */

public class DiscardHandTargetEffect extends OneShotEffect {

    public DiscardHandTargetEffect() {
        super(Outcome.Discard);
    }

    protected DiscardHandTargetEffect(final DiscardHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public DiscardHandTargetEffect copy() {
        return new DiscardHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getTargetPointer().getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.discard(player.getHand().size(), false, false, source, game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that player")
                + " discards their hand";
    }
}
