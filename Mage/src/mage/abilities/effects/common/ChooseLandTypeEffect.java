/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
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
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose land type");
            typeChoice.setChoices(CardRepository.instance.getLandTypes());
            while (!controller.choose(outcome, typeChoice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (!game.isSimulation()) {
                game.informPlayers(permanent.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoice());
            }
            game.getState().setValue(permanent.getId() + "_type", typeChoice.getChoice());
            permanent.addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen type: " + typeChoice.getChoice()), game);
        }
        return false;
    }

    @Override
    public ChooseLandTypeEffect copy() {
        return new ChooseLandTypeEffect(this);
    }

}
