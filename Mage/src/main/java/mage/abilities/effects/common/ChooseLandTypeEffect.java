package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.stream.Collectors;

/**
 * @author fireshoes
 */
public class ChooseLandTypeEffect extends OneShotEffect {

    public ChooseLandTypeEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose a land type";
    }

    public ChooseLandTypeEffect(final ChooseLandTypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose land type");
            typeChoice.setChoices(SubType.getLandTypes().stream().map(SubType::toString).collect(Collectors.toSet()));
            if (controller.choose(outcome, typeChoice, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoice());
                }
                game.getState().setValue(mageObject.getId() + "_type", SubType.byDescription(typeChoice.getChoice()));
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen type: " + typeChoice.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ChooseLandTypeEffect copy() {
        return new ChooseLandTypeEffect(this);
    }

}
