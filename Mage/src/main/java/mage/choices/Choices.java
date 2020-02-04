
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
