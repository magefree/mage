package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.ManaType;
import mage.game.Game;
import mage.players.ManaPoolItem;

import java.util.UUID;

/**
 * @author LevelX2
 */
public interface AsThoughManaEffect extends AsThoughEffect {

    /**
     * Return a mana type that can be used to pay a mana cost instead of the normally needed mana type
     * <p>
     * Usage instructions:
     * - on non applied effect: must return null (example: basic object applied, by you need to check a mana source too, see Draugr Necromancer);
     * - on applied effect: must return compatible manaType with ManaPoolItem (e.g. return mana.getFirstAvailable)
     *
     * @param manaType             type of mana with which the player wants to pay the cost
     * @param mana                 mana pool item to pay from the cost
     * @param affectedControllerId
     * @param source
     * @param game
     * @return null on non applied effect
     */
    ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game);

}
