package org.mage.test.cards.single._40k;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class KharnTheBetrayerTest extends CardTestCommander4Players {
    
    private static final String KHARN = "Kharn the Betrayer";
    private static final String BOLT = "Lightning Bolt";
    private static final String OFFERING = "Harmless Offering";

    /**
     * 
        Berzerker — Khârn the Betrayer attacks or blocks each combat if able.
        Sigil of Corruption — When you lose control of Khârn the Betrayer, draw two cards.
        The Betrayer — If damage would be dealt to Khârn the Betrayer, prevent that damage and an opponent of your choice gains control of it.
     */
    @Test
    public void testEffect() {
        addCard(Zone.BATTLEFIELD, playerA, KHARN, 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 5);
        addCard(Zone.LIBRARY, playerB, "Mountain", 5);

        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 2);
        addCard(Zone.HAND, playerC, BOLT, 2);

        // Player C pings Kharn, which triggers his effect.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerC, BOLT, KHARN);
        // Player A chooses Player B to gain control, draws 2 cards when losing control.
        setChoice(playerA, "PlayerB");

        // // Player C pings Kharn, triggering effect again.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerC, BOLT, KHARN);
        // // Player B chooses player A, draws 2 cards when losing control.
        setChoice(playerB, "PlayerC");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        // Account for draw for turn
        assertHandCount(playerA, 1 + 2);
        assertHandCount(playerB, 2);
        assertHandCount(playerC, 0);
        assertPermanentCount(playerC, KHARN, 1);
    }

    @Test
    public void testLostControl() {
        addCard(Zone.BATTLEFIELD, playerA, KHARN, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, OFFERING);
        addCard(Zone.LIBRARY, playerA, "Mountain", 5);
        
        // Player A gives Kharn to player B, should trigger Kharn's effect
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OFFERING);
        addTarget(playerA, playerB);
        addTarget(playerA, KHARN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        // Account for draw for turn
        assertPermanentCount(playerB, KHARN, 1);
        assertHandCount(playerA, 1 + 2);
    }
}
