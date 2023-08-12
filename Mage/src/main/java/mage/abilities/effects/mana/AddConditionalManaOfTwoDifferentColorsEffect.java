package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.choices.ManaChoice;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeffwadsworth
 */
public class AddConditionalManaOfTwoDifferentColorsEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder;

    public AddConditionalManaOfTwoDifferentColorsEffect(ConditionalManaBuilder manaBuilder) {
        super();
        this.manaBuilder = manaBuilder;
        staticText = "Add two mana of different colors. " + manaBuilder.getRule();
    }

    private AddConditionalManaOfTwoDifferentColorsEffect(final AddConditionalManaOfTwoDifferentColorsEffect effect) {
        super(effect);
        this.manaBuilder = effect.manaBuilder;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(2));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player player = getPlayer(game, source);
            Mana mana = manaBuilder.setMana(
                    ManaChoice.chooseTwoDifferentColors(
                            player, game), source, game).build();
            return mana;
        }
        return new Mana();
    }

    @Override
    public AddConditionalManaOfTwoDifferentColorsEffect copy() {
        return new AddConditionalManaOfTwoDifferentColorsEffect(this);
    }
}
