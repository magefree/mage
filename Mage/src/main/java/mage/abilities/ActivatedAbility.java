package mage.abilities;

import mage.ApprovingObject;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ManaOptions;
import mage.constants.TargetController;
import mage.constants.TimingRule;
import mage.game.Game;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com, Susucr
 */
public interface ActivatedAbility extends Ability {

    final class ActivationStatus {

        // Expected to not be modified after creation.
        private final Set<ApprovingObject> approvingObjects;

        // If true, the Activation Status will not check if there is an approvingObject.
        private final boolean forcedCanActivate;

        public ActivationStatus(ApprovingObject approvingObject) {
            this.forcedCanActivate = false;
            this.approvingObjects = Collections.singleton(approvingObject);
        }

        public ActivationStatus(Set<ApprovingObject> approvingObjects) {
            this(false, approvingObjects);
        }

        private ActivationStatus(boolean forcedCanActivate, Set<ApprovingObject> approvingObjects) {
            this.forcedCanActivate = forcedCanActivate;
            this.approvingObjects = new HashSet<>();
            this.approvingObjects.addAll(approvingObjects);
        }

        public boolean canActivate() {
            return forcedCanActivate || !approvingObjects.isEmpty();
        }

        /**
         * @return the set of all approving objects for that ActivationStatus.
         * That Set is readonly in spirit, as there might be different parts
         * of the engine retrieving info from it.
         */
        public Set<ApprovingObject> getApprovingObjects() {
            return approvingObjects;
        }

        private static final ActivationStatus falseInstance = new ActivationStatus(Collections.emptySet());

        public static ActivationStatus getFalse() {
            return falseInstance;
        }

        public static ActivationStatus withoutApprovingObject(boolean forcedCanActivate) {
            return new ActivationStatus(forcedCanActivate, Collections.emptySet());
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
