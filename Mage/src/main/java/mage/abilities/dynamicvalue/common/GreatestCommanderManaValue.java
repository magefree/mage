package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public enum GreatestCommanderManaValue implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Greatest mana value of a commander you own", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player != null ? game
                .getCommanderCardsFromAnyZones(
                        player, CommanderCardType.ANY,
                        Zone.BATTLEFIELD, Zone.COMMAND
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0) : 0;
    }

    @Override
    public GreatestCommanderManaValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest mana value of a commander you own on the battlefield or in the command zone";
    }

    @Override
    public String toString() {
        return "X";
    }
}
