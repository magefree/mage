/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
     * become to.
     * Use effect.setText() if case you use a targetPointer, otherwise the rule text will be empty.
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
                if (!controller.chooseUse(Outcome.Neutral, "Do you wish to choose another color?", game)) {
                    break;
                }
                ChoiceColor choiceColor = new ChoiceColor();
                controller.choose(Outcome.Benefit, choiceColor, game);
                if (!controller.isInGame()) {
                    return false;
                }
                game.informPlayers(target.getName() + ": " + controller.getName() + " has chosen " + choiceColor.getChoice());
                if (choiceColor.getColor().isBlack()) {
                    sb.append("B");
                } else if (choiceColor.getColor().isBlue()) {
                    sb.append("U");
                } else if (choiceColor.getColor().isRed()) {
                    sb.append("R");
                } else if (choiceColor.getColor().isGreen()) {
                    sb.append("G");
                } else if (choiceColor.getColor().isWhite()) {
                    sb.append("W");
                }
            }
            String colors = new String(sb);
            ObjectColor chosenColors = new ObjectColor(colors);
            ContinuousEffect effect = new BecomesColorTargetEffect(chosenColors, duration);
            effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
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
        if (mode.getTargets().size() > 0) {
            sb.append("target ");
            sb.append(mode.getTargets().get(0).getMessage());
            sb.append(" becomes the color or colors of your choice");
            if (duration.toString().length() > 0) {
                sb.append(" ").append(duration.toString());
            }
        }
        return sb.toString();
    }
}
