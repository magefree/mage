package mage.choices;

import mage.Mana;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

public class ManaChoice {
    public static Mana chooseAnyColor(Player player, Game game, int amount) {
        if (player == null) {
            return null;
        }
        Mana mana = new Mana();
        for (int i = 0; i < amount; i++) {
            ChoiceColor choiceColor = new ChoiceColor();
            if (amount > 1) {
                choiceColor.setMessage("Choose color " + (i + 1));
            }
            if (!player.choose(Outcome.Benefit, choiceColor, game)) {
                return null;
            }
            choiceColor.increaseMana(mana);
        }
        return mana;
    }

    public static Mana chooseTwoDifferentColors(Player player, Game game) {
        Mana mana = new Mana();

        if (game == null || player == null) {
            return mana;
        }

        ChoiceColor color1 = new ChoiceColor(true, "Choose color 1");
        if (!player.choose(Outcome.PutManaInPool, color1, game) || color1.getColor() == null) {
            return mana;
        }

        ChoiceColor color2 = new ChoiceColor(true, "Choose color 2");
        color2.removeColorFromChoices(color1.getChoice());
        if (!player.choose(Outcome.PutManaInPool, color2, game) || color2.getColor() == null) {
            return mana;
        }

        if (color1.getColor().equals(color2.getColor())) {
            game.informPlayers("Player " + player.getName() + " is cheating with mana choices.");
            return mana;
        }

        mana.add(color1.getMana(1));
        mana.add(color2.getMana(1));
        return mana;
    }
}
