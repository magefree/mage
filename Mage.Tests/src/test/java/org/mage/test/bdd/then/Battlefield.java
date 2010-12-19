package org.mage.test.bdd.then;

import org.mage.test.base.MageBase;

public class Battlefield {
    public static boolean has(String cardName) throws Exception {
        return MageBase.getInstance().checkBattlefield(cardName);
    }
}
