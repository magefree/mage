package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ChooseOpponentEffect extends OneShotEffect {

    public static final String VALUE_KEY = "_opponent";

    public ChooseOpponentEffect(Outcome outcome) {
        super(outcome);
        this.staticText = "choose an opponent";
    }

    public ChooseOpponentEffect(final ChooseOpponentEffect effect) {
        super(effect);
    }

    @Override
    public ChooseOpponentEffect copy() {
        return new ChooseOpponentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            TargetOpponent target = new TargetOpponent(true);
            if (controller.choose(this.outcome, target, source, game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + chosenPlayer.getLogName());
                    game.getState().setValue(mageObject.getId() + VALUE_KEY, target.getFirstTarget());
                    if (mageObject instanceof Permanent) {
                        ((Permanent) mageObject).addInfo("chosen opponent", CardUtil.addToolTipMarkTags("Chosen player: " + chosenPlayer.getLogName()), game);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
