package mage.choices;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class FaceVillainousChoice {

    private final Outcome outcome;
    private final VillainousChoice firstChoice;
    private final VillainousChoice secondChoice;

    public FaceVillainousChoice(Outcome outcome, VillainousChoice firstChoice, VillainousChoice secondChoice) {
        this.outcome = outcome;
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
    }

    public boolean faceChoice(Player player, Game game, Ability source) {
        return handleChoice(player, game, source);
    }

    private boolean handleChoice(Player player, Game game, Ability source) {
        VillainousChoice chosenChoice = player.chooseUse(
                outcome, "You face a villanous choice:", null,
                firstChoice.getMessage(game, source), secondChoice.getMessage(game, source), source, game
        ) ? firstChoice : secondChoice;
        return chosenChoice.doChoice(player, game, source);
    }

    public String generateRule() {
        return "faces a villanous choice &mdash; " + firstChoice.getRule() + ", or " + secondChoice.getRule();
    }
}

