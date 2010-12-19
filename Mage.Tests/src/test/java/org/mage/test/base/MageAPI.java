package org.mage.test.base;

import mage.game.turn.Phase;
import org.junit.BeforeClass;
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
        Thread.sleep(3000);
    }

    public void giveme(String card) throws Exception {
        MageBase.getInstance().giveme(card);
    }

    public boolean checkIhave(String card) throws Exception {
        return MageBase.getInstance().checkIhave(card);
    }

    public void goToPhase(String phase, Owner owner) {
        if ("Precombat Main".equals(phase) && (owner.equals(Owner.mine) || owner.equals(Owner.me))) {
            MageBase.getInstance().goToPhase("Precombat Main - play spells and sorceries.");
            return;
        }
        System.err.println("waitForPhase not implemented for phase="+phase+", owner="+owner.name());
        throw new NotImplementedException();
    }

    public void playCard(String cardName) throws Exception {
        MageBase.getInstance().playCard(cardName);
    }

    public boolean checkBattlefield(String cardName) throws Exception {
        return MageBase.getInstance().checkBattlefield(cardName);
    }

    public boolean checkGraveyardsEmpty() throws Exception {
        return MageBase.getInstance().checkGraveyardsEmpty();
    }
}
