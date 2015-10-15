/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
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

    public static String VALUE_KEY = "BasicLandType";

    public ChooseBasicLandTypeEffect(Outcome outcome) {
        super(outcome);
        this.staticText = "Choose a basic land type";
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
        MageObject mageObject = (Permanent) getValue(EntersBattlefieldEffect.ENTERING_PERMANENT);
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        if (controller != null && mageObject != null) {
            ChoiceImpl choices = new ChoiceImpl(true);
            choices.setMessage("Choose basic land type");
            choices.isRequired();
            choices.getChoices().add("Forest");
            choices.getChoices().add("Plains");
            choices.getChoices().add("Mountain");
            choices.getChoices().add("Island");
            choices.getChoices().add("Swamp");
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
