package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SkipNextPlayerUntapStepEffect extends OneShotEffect {

    public SkipNextPlayerUntapStepEffect() {
        this("");
    }

    public SkipNextPlayerUntapStepEffect(String text) {
        super(Outcome.Detriment);
        this.staticText = text.isEmpty()
                ? "You skip your next untap step"
                : text + " skips their next untap step";
    }

    protected SkipNextPlayerUntapStepEffect(final SkipNextPlayerUntapStepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = null;
        if (targetPointer != null) {
            if (!this.targetPointer.getTargets(game, source).isEmpty()) {
                player = game.getPlayer(targetPointer.getFirst(game, source));
            } else {
                player = game.getPlayer(source.getControllerId());
            }
        }
        if (player != null) {
            game.getState().getTurnMods().add(new TurnMod(player.getId()).withSkipStep(PhaseStep.UNTAP));
            return true;
        }
        return false;
    }

    @Override
    public SkipNextPlayerUntapStepEffect copy() {
        return new SkipNextPlayerUntapStepEffect(this);
    }

}
