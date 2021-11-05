
package mage.abilities.effects.common.continuous;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BecomesColorOrColorsTargetEffect extends OneShotEffect {

    Duration duration;

    /**
     * This effect let the controller choose one or more colors the target will
     * become to. Use effect.setText() if case you use a targetPointer,
     * otherwise the rule text will be empty.
     *
     * @param duration
     */
    public BecomesColorOrColorsTargetEffect(Duration duration) {
        super(Outcome.Neutral);
        this.duration = duration;
    }

    public BecomesColorOrColorsTargetEffect(final BecomesColorOrColorsTargetEffect effect) {
        super(effect);
        this.duration = effect.duration;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(source.getFirstTarget());
        StringBuilder sb = new StringBuilder();

        if (controller != null && target != null) {
            for (int i = 0; i < 5; i++) {
                if (i > 0) {
                    if (!controller.chooseUse(Outcome.Neutral, "Choose another color?", source, game)) {
                        break;
                    }
                }
                ChoiceColor choiceColor = new ChoiceColor();
                if (!controller.choose(Outcome.Benefit, choiceColor, game)) {
                    return false;
                }
                if (!game.isSimulation()) {
                    game.informPlayers(target.getName() + ": " + controller.getLogName() + " has chosen " + choiceColor.getChoice());
                }
                if (choiceColor.getColor().isBlack()) {
                    sb.append('B');
                } else if (choiceColor.getColor().isBlue()) {
                    sb.append('U');
                } else if (choiceColor.getColor().isRed()) {
                    sb.append('R');
                } else if (choiceColor.getColor().isGreen()) {
                    sb.append('G');
                } else if (choiceColor.getColor().isWhite()) {
                    sb.append('W');
                }
            }
            String colors = new String(sb);
            ObjectColor chosenColors = new ObjectColor(colors);
            ContinuousEffect effect = new BecomesColorTargetEffect(chosenColors, duration);
            effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
            game.addEffect(effect, source);

            return true;
        }
        return false;
    }

    @Override
    public BecomesColorOrColorsTargetEffect copy() {
        return new BecomesColorOrColorsTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (!mode.getTargets().isEmpty()) {
            sb.append("target ");
            sb.append(mode.getTargets().get(0).getFilter().getMessage());
            sb.append(" becomes the color or colors of your choice");
            if (!duration.toString().isEmpty()) {
                sb.append(' ').append(duration.toString());
            }
        }
        return sb.toString();
    }
}
