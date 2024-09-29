package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author PurpleCrowbar
 */
public enum CommanderGreatestManaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getCommanderCardsFromAnyZones(
                        game.getPlayer(sourceAbility.getControllerId()), CommanderCardType.ANY, Zone.ALL)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public CommanderGreatestManaValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the greatest mana value among your commanders";
    }
}
