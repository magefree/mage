
package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ChooseModeEffect extends OneShotEffect {

    protected final List<String> modes = new ArrayList<>();
    protected final String choiceMessage;

    public ChooseModeEffect(String choiceMessage, String... modes) {
        super(Outcome.Neutral);
        this.choiceMessage = choiceMessage;
        this.modes.addAll(Arrays.asList(modes));
        this.staticText = setText();
    }

    protected ChooseModeEffect(final ChooseModeEffect effect) {
        super(effect);
        this.modes.addAll(effect.modes);
        this.choiceMessage = effect.choiceMessage;
    }

    @Override
    public ChooseModeEffect copy() {
        return new ChooseModeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = game.getPermanentEntering(source.getSourceId());
        }
        if (controller != null && sourcePermanent != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage(choiceMessage);
            choice.getChoices().addAll(modes);
            if (controller.choose(Outcome.Neutral, choice, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(sourcePermanent.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
                }
                game.getState().setValue(source.getSourceId() + "_modeChoice", choice.getChoice());
                sourcePermanent.addInfo("_modeChoice", "<font color = 'blue'>Chosen mode: " + choice.getChoice() + "</font>", game);
                return true;
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("choose ");
        int count = 0;
        for (String choice : modes) {
            count++;
            sb.append(choice);
            if (count + 1 < modes.size()) {
                sb.append(", ");
            } else if (count < modes.size()) {
                sb.append(" or ");
            }
        }
        return sb.toString();
    }
}
