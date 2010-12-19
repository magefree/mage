package org.mage.test.bdd.and;

import org.mage.test.base.MageBase;

public class Graveyards {
    public static boolean empty() throws Exception {
        boolean empty = MageBase.getInstance().checkGraveyardsEmpty();
        System.out.println("empty: " + empty);
        return empty;
    }
}
