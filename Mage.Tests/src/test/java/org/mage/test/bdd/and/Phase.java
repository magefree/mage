package org.mage.test.bdd.and;

import org.mage.test.base.MageAPI;
import org.mage.test.base.MageBase;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.mage.test.base.MageAPI.*;
import static org.mage.test.base.MageAPI.Owner.*;

public class Phase {
    public static void is(String phase, MageAPI.Owner owner) throws Exception {
        if ("Precombat Main".equals(phase) && (owner.equals(mine) || owner.equals(me))) {
            MageBase.getInstance().goToPhase("Precombat Main - play spells and sorceries.");
            Thread.sleep(3000);
            return;
        }
        System.err.println("waitForPhase not implemented for phase="+phase+", owner="+owner.name());
        throw new NotImplementedException();
    }
}
