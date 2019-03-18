
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.ChoiceColor;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public class ChooseColorEffect extends OneShotEffect {

    public ChooseColorEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose a color";
    }

    public ChooseColorEffect(final ChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && mageObject != null && controller.choose(outcome, choice, game)) {
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
            }
            game.getState().setValue(mageObject.getId() + "_color", choice.getColor());
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("chosen color", CardUtil.addToolTipMarkTags("Chosen color: " + choice.getChoice()), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ChooseColorEffect copy() {
        return new ChooseColorEffect(this);
    }

}
