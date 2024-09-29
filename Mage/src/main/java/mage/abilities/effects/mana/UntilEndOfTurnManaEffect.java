package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.players.Player;

public class UntilEndOfTurnManaEffect extends BasicManaEffect {

    public UntilEndOfTurnManaEffect(Mana mana) {
        this(mana, null);
    }

    public UntilEndOfTurnManaEffect(Mana mana, DynamicValue netAmount) {
        super(mana, netAmount);
        staticText += ". Until end of turn, you don't lose this mana as steps and phases end";
    }

    protected UntilEndOfTurnManaEffect(final UntilEndOfTurnManaEffect effect) {
        super(effect);
    }

    @Override
    public UntilEndOfTurnManaEffect copy() {
        return new UntilEndOfTurnManaEffect(this);
    }

    @Override
    protected void addManaToPool(Player player, Mana manaToAdd, Game game, Ability source) {
        player.getManaPool().addMana(manaToAdd, game, source, true);
    }

}
