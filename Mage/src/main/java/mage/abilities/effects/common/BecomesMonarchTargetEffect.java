package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class BecomesMonarchTargetEffect extends OneShotEffect {

    public BecomesMonarchTargetEffect() {
        super(Outcome.Benefit);
        staticText = "target player becomes the monarch";
    }

    protected BecomesMonarchTargetEffect(final BecomesMonarchTargetEffect effect) {
        super(effect);
    }

    @Override
    public BecomesMonarchTargetEffect copy() {
        return new BecomesMonarchTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            game.setMonarchId(source, targetPlayer.getId());
            return true;
        }
        return false;
    }

}
