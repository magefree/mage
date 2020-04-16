package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceCardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author emerald000
 */
public class ChooseCardTypeEffect extends OneShotEffect {

    public ChooseCardTypeEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose a card type";
    }

    private ChooseCardTypeEffect(final ChooseCardTypeEffect effect) {
        super(effect);
    }

    @Override
    public ChooseCardTypeEffect copy() {
        return new ChooseCardTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        if (controller != null && mageObject != null) {
            Choice typeChoice = new ChoiceCardType();
            if (controller.choose(outcome, typeChoice, game)) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen: " + typeChoice.getChoice());
                game.getState().setValue(source.getSourceId() + "_type", typeChoice.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen type: " + typeChoice.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }
}
