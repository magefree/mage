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
package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.Arrays;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ChooseModeEffect extends OneShotEffect {

    protected final ArrayList<String> modes = new ArrayList();
    protected final String choiceMessage;

    public ChooseModeEffect(String choiceMessage, String... modes) {
        super(Outcome.Neutral);
        this.choiceMessage = choiceMessage;
        this.modes.addAll(Arrays.asList(modes));
        this.staticText = setText();
    }

    public ChooseModeEffect(final ChooseModeEffect effect) {
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
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage(choiceMessage);
            choice.getChoices().addAll(modes);
            while (!choice.isChosen()) {
                if (!controller.canRespond()) {
                    return false;
                }
                controller.choose(Outcome.Neutral, choice, game);
            }
            if (choice.isChosen()) {
                if (!game.isSimulation())
                    game.informPlayers(new StringBuilder(sourcePermanent.getLogName()).append(": ").append(controller.getLogName()).append(" has chosen ").append(choice.getChoice()).toString());
                game.getState().setValue(source.getSourceId() + "_modeChoice", choice.getChoice());
                sourcePermanent.addInfo("_modeChoice", "<font color = 'blue'>Chosen mode: " + choice.getChoice() + "</font>", game);
            }
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("choose ");
        int count = 0;
        for (String choice: modes) {
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
