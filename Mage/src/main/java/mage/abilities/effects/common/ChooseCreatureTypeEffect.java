package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ChooseCreatureTypeEffect extends OneShotEffect {

    public ChooseCreatureTypeEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose a creature type";
    }

    public ChooseCreatureTypeEffect(final ChooseCreatureTypeEffect effect) {
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
            Choice typeChoice = new ChoiceCreatureType(mageObject);
            if (controller.choose(outcome, typeChoice, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoice());
                }
                game.getState().setValue(source.getSourceId() + "_type", SubType.byDescription(typeChoice.getChoice()));
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen type: " + typeChoice.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ChooseCreatureTypeEffect copy() {
        return new ChooseCreatureTypeEffect(this);
    }

    public static SubType getChosenCreatureType(UUID objectId, Game game) {
        return getChosenCreatureType(objectId, game, "_type");
    }

    /**
     * @param objectId    sourceId the effect was exeuted under
     * @param game
     * @param typePostfix special postfix if you want to store multiple choices from different effects
     * @return
     */
    public static SubType getChosenCreatureType(UUID objectId, Game game, String typePostfix) {
        SubType creatureType = null;
        Object savedCreatureType = game.getState().getValue(objectId + typePostfix);
        if (savedCreatureType != null) {
            creatureType = SubType.byDescription(savedCreatureType.toString());
        }
        return creatureType;
    }
}
