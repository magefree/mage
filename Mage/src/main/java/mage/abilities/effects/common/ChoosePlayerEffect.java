package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ChoosePlayerEffect extends OneShotEffect {

    public ChoosePlayerEffect(Outcome outcome) {
        super(outcome);
        this.staticText = "choose a player";
    }

    public ChoosePlayerEffect(final ChoosePlayerEffect effect) {
        super(effect);
    }

    @Override
    public ChoosePlayerEffect copy() {
        return new ChoosePlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            TargetPlayer target = new TargetPlayer(1, 1, true);
            if (controller.choose(this.outcome, target, source, game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + chosenPlayer.getLogName());
                    game.getState().setValue(mageObject.getId() + "_player", target.getFirstTarget());
                    if (mageObject instanceof Permanent) {
                        ((Permanent) mageObject).addInfo("chosen player", CardUtil.addToolTipMarkTags("Chosen player: " + chosenPlayer.getLogName()), game);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
