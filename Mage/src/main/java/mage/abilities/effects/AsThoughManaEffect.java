
package mage.abilities.effects;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.ManaType;
import mage.game.Game;
import mage.players.ManaPoolItem;

/**
 *
 * @author LevelX2
 */
public interface AsThoughManaEffect extends AsThoughEffect {

    // return a mana type that can be used to pay a mana cost instead of the normally needed mana type
    /**
     *
     * @param manaType type of mana with which the player wants to pay the cost
     * @param mana mana pool item to pay from the cost
     * @param affectedControllerId
     * @param source
     * @param game
     * @return
     */
    ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game);

}
