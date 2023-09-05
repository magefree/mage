package mage.abilities;

import mage.ApprovingObject;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ManaOptions;
import mage.constants.TargetController;
import mage.constants.TimingRule;
import mage.game.Game;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface ActivatedAbility extends Ability {

    final class ActivationStatus {

        private final boolean canActivate;
        private final Set<ApprovingObject> approvingObjects;

        private ActivationStatus(boolean canActivate, Set<ApprovingObject> approvingObjects) {
            this.canActivate = canActivate;
            this.approvingObjects = approvingObjects;
        }

        public boolean canActivate() {
            return canActivate;
        }

        public Set<ApprovingObject> getApprovingObjects() {
            return approvingObjects;
        }

        public static ActivationStatus getFalse() {
            return new ActivationStatus(false, new HashSet<>());
        }

        /**
         * @param approvingObjectAbility ability that allows to activate/use current ability
         */
        public static ActivationStatus getTrue(Ability approvingObjectAbility, Game game) {
            Set<ApprovingObject> approvingObjects = new HashSet<>();
            if(approvingObjects != null) {
                approvingObjects.add(new ApprovingObject(approvingObjectAbility, game));
            }
            return new ActivationStatus(true, approvingObjects);
        }

        public static ActivationStatus getTrue(Set<ApprovingObject> approvingObjects) {
            Set<ApprovingObject> newApprovingObjects = new HashSet<>();
            if(approvingObjects != null) {
                approvingObjects.addAll(newApprovingObjects);
            }
            return new ActivationStatus(true, approvingObjects);
        }

        public static ActivationStatus withoutApprovingObject(boolean status) {
            return new ActivationStatus(status, new HashSet<>());
        }
    }

    /**
     * WARNING, don't forget to call super.canActivate on override in card's code
     *
     * @param playerId
     * @param game
     * @return
     */
    ActivationStatus canActivate(UUID playerId, Game game); // has to return a reference to the permitting ability/source

    /**
     * Who can activate an ability. By default, only you (the controller/owner).
     *
     * @param mayActivate
     */
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

    ActivatedAbility setTiming(TimingRule timing);

    ActivatedAbility setCondition(Condition condition);
}
