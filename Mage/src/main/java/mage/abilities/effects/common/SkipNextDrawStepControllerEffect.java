package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author noahg
 */
public class SkipNextDrawStepControllerEffect extends OneShotEffect {

    public SkipNextDrawStepControllerEffect() {
        this("you skip your next draw step");
    }

    public SkipNextDrawStepControllerEffect(String text) {
        super(Outcome.Detriment);
        this.staticText = text;
    }

    public SkipNextDrawStepControllerEffect(SkipNextDrawStepControllerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player =  game.getPlayer(source.getControllerId());
        if (player != null) {
            game.getState().getTurnMods().add(new TurnMod(player.getId()).withSkipStep(PhaseStep.DRAW));
            return true;
        }
        return false;
    }

    @Override
    public SkipNextDrawStepControllerEffect copy() {
        return new SkipNextDrawStepControllerEffect(this);
    }
}
