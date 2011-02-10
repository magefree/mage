package org.mage.test.clientside.bdd.when;

import org.mage.test.clientside.base.MageBase;

public class I {
    public void play(String cardName) throws Exception {
        MageBase.getInstance().playCard(cardName);
    }
}