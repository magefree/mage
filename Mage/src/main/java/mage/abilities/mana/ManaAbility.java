package mage.abilities.mana;

import java.util.List;
import java.util.Set;
import mage.Mana;
import mage.constants.ManaType;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public interface ManaAbility {

    /**
     * Used to check the possible mana production to determine which spells
     * and/or abilities can be used. (player.getPlayable()).
     *
     * @param game
     * @return
     */
    List<Mana> getNetMana(Game game);
    
    /**
     * Used to check the possible mana production to determine which spells
     * and/or abilities can be used. (player.getPlayable()).
     * Only used for abilities were the poolDependant flag is set
     *
     * @param game
     * @param possibleManaInPool The possible mana already produced by other sources for this calculation option
     * @return 
     */
    List<Mana> getNetMana(Game game, Mana possibleManaInPool);

    /**
     * The type of mana a permanent "could produce" is the type of mana that any
     * ability of that permanent can generate, taking into account any
     * applicable replacement effects. If the type of mana can’t be defined,
     * there’s no type of mana that that permanent could produce. The "type" of
     * mana is its color, or lack thereof (for colorless mana).
     *
     * @param game
     * @return
     */
    Set<ManaType> getProducableManaTypes(Game game);

    /**
     * Used to check if the ability itself defines mana types it can produce.
     *
     * @param game
     * @return
     */
    boolean definesMana(Game game);

    /**
     * Set to true if the ability is dependant from the mana pool. E.g. the more
     * mana the pool contains the more mana the ability can produce (Doubling
     * Cube). Therefore the use of that ability after other mana abilities does produce more mana.
     *
     * @return
     */
    boolean isPoolDependant();
    
    ManaAbility setPoolDependant(boolean pooleDependant);
    
}
