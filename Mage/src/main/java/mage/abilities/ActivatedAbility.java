
package mage.abilities;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.mana.ManaOptions;
import mage.constants.TargetController;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface ActivatedAbility extends Ability {

    final public class ActivationStatus {

        private final boolean canActivate;
        private final MageObjectReference permittingObject;

        public ActivationStatus(boolean canActivate, MageObjectReference permittingObject) {
            this.canActivate = canActivate;
            this.permittingObject = permittingObject;
        }

        public boolean canActivate() {
            return canActivate;
        }

        public MageObjectReference getPermittingObject() {
            return permittingObject;
        }

        public static ActivationStatus getFalse() {
            return new ActivationStatus(false, null);
        }

        public static ActivationStatus getTrue() {
            return new ActivationStatus(true, null);
        }
    }

    ActivationStatus canActivate(UUID playerId, Game game); // has to return a reference to the permitting ability/source

    void setMayActivate(TargetController mayActivate);

    /**
     * Returns the minimal possible cost for what the ability can be activated
     * or cast
     *
     * @param playerId
     * @param game
     * @return
     */
    ManaOptions getMinimumCostToActivate(UUID playerId, Game game);

    /**
     * Creates a fresh copy of this activated ability.
     *
     * @return A new copy of this ability.
     */
    @Override
    ActivatedAbility copy();

    /**
     * Set a flag to know, that the ability is only created adn used to check
     * what's playbable for the player.
     */
    void setCheckPlayableMode();

    boolean isCheckPlayableMode();

    void setMaxActivationsPerTurn(int maxActivationsPerTurn);

    int getMaxActivationsPerTurn(Game game);
}
