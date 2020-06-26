package mage.abilities;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.mana.ManaOptions;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface ActivatedAbility extends Ability {

    final class ActivationStatus {

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

        /**
         * @param permittingObjectAbility card or permanent that allows to activate current ability
         */
        public static ActivationStatus getTrue(Ability permittingObjectAbility, Game game) {
            MageObject object = permittingObjectAbility == null ? null : permittingObjectAbility.getSourceObject(game);
            MageObjectReference ref = object == null ? null : new MageObjectReference(object, game);
            return new ActivationStatus(true, ref);
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

    void setMaxActivationsPerTurn(int maxActivationsPerTurn);

    int getMaxActivationsPerTurn(Game game);
}
