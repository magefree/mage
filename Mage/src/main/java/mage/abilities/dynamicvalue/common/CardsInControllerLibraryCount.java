package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Susucr
 */
public enum CardsInControllerLibraryCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        return controller != null ? controller.getLibrary().size() : 0;
    }

    @Override
    public CardsInControllerLibraryCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of cards in your library";
    }

    @Override
    public String toString() {
        return "1";
    }
}