package org.mage.test.bdd.when;

import org.mage.test.base.MageBase;

public class I {
    public void play(String cardName) throws Exception {
        MageBase.getInstance().playCard(cardName);
    }
}