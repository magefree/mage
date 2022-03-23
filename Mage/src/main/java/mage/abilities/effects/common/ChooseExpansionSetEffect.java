
package mage.abilities.effects.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.repository.ExpansionRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2 (spjspj)
 */
public class ChooseExpansionSetEffect extends OneShotEffect {

    public ChooseExpansionSetEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose an expansion set";
    }

    public ChooseExpansionSetEffect(final ChooseExpansionSetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }

        if (controller != null) {
            Choice setChoice = new ChoiceImpl(true);
            setChoice.setMessage("Choose expansion set");
            List<String> setCodes = ExpansionRepository.instance.getSetCodes();
            Set<String> sets = new HashSet<String>(setCodes);

            setChoice.setChoices(sets);
            if (controller.choose(outcome, setChoice, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + " has chosen set " + setChoice.getChoice());
                }
                game.getState().setValue(mageObject.getId() + "_set", setChoice.getChoice());
                this.setValue("setchosen", setChoice.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen set", CardUtil.addToolTipMarkTags("Chosen set: " + setChoice.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ChooseExpansionSetEffect copy() {
        return new ChooseExpansionSetEffect(this);
    }

}
