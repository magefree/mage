/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.choices;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Choices extends ArrayList<Choice> {

    protected Outcome outcome;

    public Choices() {
    }

    public Choices(final Choices choices) {
        this.outcome = choices.outcome;
        for (Choice choice : choices) {
            this.add(choice.copy());
        }
    }

    public Choices copy() {
        return new Choices(this);
    }

    public List<Choice> getUnchosen() {
        return stream()
                .filter(choice -> !choice.isChosen())
                .collect(Collectors.toList());
    }

    public void clearChosen() {
        for (Choice choice : this) {
            choice.clearChoice();
        }
    }

    public boolean isChosen() {
        for (Choice choice : this) {
            if (!choice.isChosen()) {
                return false;
            }
        }
        return true;
    }

    public boolean choose(Game game, Ability source) {
        if (this.size() > 0) {
            Player player = game.getPlayer(source.getControllerId());
            while (!isChosen()) {
                Choice choice = this.getUnchosen().get(0);
                if (!player.choose(outcome, choice, game)) {
                    return false;
                }
            }
        }
        return true;
    }

}
