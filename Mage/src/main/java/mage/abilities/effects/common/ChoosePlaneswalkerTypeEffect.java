package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoicePlaneswalkerType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jimga150
 */

// Based on ChooseCreatureTypeEffect
public class ChoosePlaneswalkerTypeEffect extends OneShotEffect {

    public ChoosePlaneswalkerTypeEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose a planeswalker type";
    }

    protected ChoosePlaneswalkerTypeEffect(final ChoosePlaneswalkerTypeEffect effect) {
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
            Choice typeChoice = new ChoicePlaneswalkerType(mageObject);
            if (controller.choose(outcome, typeChoice, game)) {
                game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoice());
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
    public ChoosePlaneswalkerTypeEffect copy() {
        return new ChoosePlaneswalkerTypeEffect(this);
    }

    public static SubType getChosenPlaneswalkerType(UUID objectId, Game game) {
        return getChosenPlaneswalkerType(objectId, game, "_type");
    }

    /**
     * @param objectId sourceId the effect was executed under
     * @param game
     * @param typePostfix special postfix if you want to store multiple choices
     * from different effects
     * @return
     */
    public static SubType getChosenPlaneswalkerType(UUID objectId, Game game, String typePostfix) {
        SubType planeswalkerType = null;
        Object savedPlaneswalkerType = game.getState().getValue(objectId + typePostfix);
        if (savedPlaneswalkerType != null) {
            planeswalkerType = SubType.byDescription(savedPlaneswalkerType.toString());
        }
        return planeswalkerType;
    }
}
