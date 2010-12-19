package org.mage.test.bdd.given;

import org.mage.test.base.MageBase;

public class A {
    public static void card(String cardName) throws Exception {
        MageBase.getInstance().giveme(cardName);
        Thread.sleep(4000);
        if (!MageBase.getInstance().checkIhave(cardName)) {
            throw new IllegalStateException("Couldn't find a card in hand: " + cardName);
        }
    }
}
