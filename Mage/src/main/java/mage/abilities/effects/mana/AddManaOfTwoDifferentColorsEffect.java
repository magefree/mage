package mage.abilities.effects.mana;


import mage.Mana;
import mage.abilities.Ability;
import mage.choices.ManaChoice;
import mage.constants.ManaType;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

public class AddManaOfTwoDifferentColorsEffect extends ManaEffect {

    // Performing this calculation here since it never changes and should not be recalcualted each time.
    private static final List<ManaType> colorsToCycle   = new ArrayList<>();
    private static final List<Mana> netMana             = new ArrayList<>();
    static {
        // Add the colored mana in order to cycle through them
        colorsToCycle.add(ManaType.WHITE);
        colorsToCycle.add(ManaType.BLUE);
        colorsToCycle.add(ManaType.BLACK);
        colorsToCycle.add(ManaType.RED);
        colorsToCycle.add(ManaType.GREEN);

        // Create the possible combinations of two mana
        for (ManaType manaType1 : colorsToCycle) {
            for (ManaType manaType2 : colorsToCycle) {
                // Mana types must be different
                if (manaType1 == manaType2) {
                    continue;
                }

                Mana manaCombo = new Mana(manaType1);
                manaCombo.increase(manaType2);
                netMana.add(manaCombo);
            }
        }
    }

    public AddManaOfTwoDifferentColorsEffect() {
        super();
        staticText = "Add two mana of different colors.";
    }

    private AddManaOfTwoDifferentColorsEffect(final AddManaOfTwoDifferentColorsEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player player = getPlayer(game, source);
            return ManaChoice.chooseTwoDifferentColors(player, game);
        } else {
            return new Mana();
        }
    }

    @Override
    public AddManaOfTwoDifferentColorsEffect copy() {
        return new AddManaOfTwoDifferentColorsEffect(this);
    }
}
