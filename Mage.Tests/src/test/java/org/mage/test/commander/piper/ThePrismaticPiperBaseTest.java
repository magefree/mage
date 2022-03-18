package org.mage.test.commander.piper;

import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author TheElk801
 */
public abstract class ThePrismaticPiperBaseTest extends CardTestCommanderDuelBase {

    protected static final String piper = "The Prismatic Piper";

    protected ThePrismaticPiperBaseTest(int number) {
        super();
        this.deckNameA = "piperDecks/ThePrismaticPiper" + number + ".dck";
    }
}
