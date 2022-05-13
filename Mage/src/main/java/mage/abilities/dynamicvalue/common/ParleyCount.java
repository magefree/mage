
package mage.abilities.dynamicvalue.common;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 * Don't use this for continuous effects because it applies a reveal effect!
 *
 * @author LevelX2
 */
public class ParleyCount implements DynamicValue, MageSingleton {

    private static final ParleyCount instance = new ParleyCount();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ParleyCount getInstance() {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // Each player reveals the top card of their library. For each nonland card revealed this way
        int parleyValue = 0;

        MageObject sourceObject = game.getObject(sourceAbility.getSourceId());
        if (sourceObject == null) {
            return parleyValue;
        }

        for (UUID playerId : game.getState().getPlayersInRange(sourceAbility.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }

            if (!card.isLand(game)) {
                parleyValue++;
            }

            player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ')', new CardsImpl(card), game);
        }

        return parleyValue;
    }

    @Override
    public ParleyCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "Parley";
    }

}
