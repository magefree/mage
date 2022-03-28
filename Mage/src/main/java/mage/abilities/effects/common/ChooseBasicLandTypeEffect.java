package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.ChoiceBasicLandType;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ChooseBasicLandTypeEffect extends OneShotEffect {

    public static final String VALUE_KEY = "BasicLandType";

    public ChooseBasicLandTypeEffect(Outcome outcome) {
        super(outcome);
        this.staticText = "choose a basic land type";
    }

    public ChooseBasicLandTypeEffect(final ChooseBasicLandTypeEffect effect) {
        super(effect);
    }

    @Override
    public ChooseBasicLandTypeEffect copy() {
        return new ChooseBasicLandTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            ChoiceImpl choices = new ChoiceBasicLandType();
            if (controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(mageObject.getName() + ":  Chosen basic land type is " + choices.getChoice());
                game.getState().setValue(mageObject.getId().toString() + VALUE_KEY, choices.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen color", CardUtil.addToolTipMarkTags("Chosen basic land type: " + choices.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }
}
