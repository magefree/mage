
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.ModeChoice;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class ChooseModeEffect extends OneShotEffect {

    protected final List<ModeChoice> modes = new ArrayList<>();
    protected final String message;

    public ChooseModeEffect(ModeChoice... modes) {
        super(Outcome.Neutral);
        this.modes.addAll(Arrays.asList(modes));
        this.message = makeMessage(this.modes);
        this.staticText = "choose " + this.message;
    }

    protected ChooseModeEffect(final ChooseModeEffect effect) {
        super(effect);
        this.modes.addAll(effect.modes);
        this.message = effect.message;
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
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage(message + "? (" + CardUtil.getSourceLogName(game, source) + ')');
        choice.getChoices().addAll(modes.stream().map(ModeChoice::toString).collect(Collectors.toList()));
        if (!controller.choose(Outcome.Neutral, choice, game)) {
            return false;
        }
        game.informPlayers(sourcePermanent.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
        game.getState().setValue(source.getSourceId() + "_modeChoice", choice.getChoice());
        sourcePermanent.addInfo("_modeChoice", "<font color = 'blue'>Chosen mode: " + choice.getChoice() + "</font>", game);
        return true;
    }

    private static String makeMessage(List<ModeChoice> modeChoices) {
        return CardUtil.concatWithOr(
                modeChoices
                        .stream()
                        .map(ModeChoice::toString)
                        .collect(Collectors.toList())
        );
    }
}
