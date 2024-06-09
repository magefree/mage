
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Styxo
 */
public class LoseHalfLifeTargetEffect extends OneShotEffect {

    public LoseHalfLifeTargetEffect() {
        super(Outcome.Damage);
    }

    protected LoseHalfLifeTargetEffect(final LoseHalfLifeTargetEffect effect) {
        super(effect);
    }

    @Override
    public LoseHalfLifeTargetEffect copy() {
        return new LoseHalfLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            int amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game, source, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that player") + " loses half their life, rounded up";
    }
}
