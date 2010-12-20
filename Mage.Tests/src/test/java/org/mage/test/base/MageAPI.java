package org.mage.test.base;

import org.junit.BeforeClass;
import org.mage.test.bdd.StepController;
import org.mage.test.bdd.StepState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Parent class for all Mage tests.
 * Provides basic actions in mage game and assert functions to check game state.
 */
public class MageAPI {

    public enum Owner {
        mine,
        me,
        ai
    }

    @BeforeClass
    public static void startServer() throws Exception {
        MageBase.getInstance().start();
    }

    /**
     * Defined step depending on input parameter.
     * If step is UNKNOWN, then use previous remember step, otherwise remember it as current.
     *
     * Used for replacing "And." by "Given", "When", "Then"
     *
     * @param step
     * @return
     */
    public static StepState defineStep(StepState step) {
        StepState current = step;
        if (!step.equals(StepState.UNKNOWN)) {
            StepController.currentState = step;
        } else {
            current = StepController.currentState;
        }
        return current;
    }
}
