
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

import java.util.Locale;

/**
 * @author Plopman
 */
public class ChooseColorEffect extends OneShotEffect {

    private final String exceptColor;

    public ChooseColorEffect(Outcome outcome) {
        this(outcome, null);
    }

    public ChooseColorEffect(Outcome outcome, String exceptColor) {
        super(outcome);
        this.exceptColor = exceptColor;
        staticText = "choose a color" + (exceptColor != null ? " other than " + exceptColor.toLowerCase(Locale.ENGLISH) : "");
    }

    public ChooseColorEffect(final ChooseColorEffect effect) {
        super(effect);
        this.exceptColor = effect.exceptColor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        ChoiceColor choice = new ChoiceColor();
        if (exceptColor != null) {
            choice.removeColorFromChoices(exceptColor);
        }
        if (controller == null || mageObject == null || !controller.choose(outcome, choice, game)) {
            return false;
        }
        game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
        game.getState().setValue(mageObject.getId() + "_color", choice.getColor());
        if (mageObject instanceof Permanent) {
            ((Permanent) mageObject).addInfo("chosen color", CardUtil.addToolTipMarkTags("Chosen color: " + choice.getChoice()), game);
        }
        return true;
    }

    @Override
    public ChooseColorEffect copy() {
        return new ChooseColorEffect(this);
    }

}
