package org.mage.test.cards.single.c19;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WallOfStolenIdentityTest extends CardTestPlayerBase {

    /**
     * The "doesn't untap" comes from a "when you do" trigger fired while the Wall is entering. The trigger isn't on
     * the Wall (113.7a), and it must refer to the Wall on the battlefield, not the card that was entering (603.7f).
     * Each of these once ended the effect at once, and the creature untapped.
     */
    @Test
    public void testCopiedCreatureStaysTappedWhileYouControlTheWall() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.HAND, playerA, "Wall of Stolen Identity@wall"); // {3}{U}
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Stolen Identity");
        setChoice(playerA, true); // enter as a copy
        setChoice(playerA, "Grizzly Bears");
        checkPermanentTapped("tapped by the Wall", 1, PhaseStep.BEGIN_COMBAT, playerA, playerB, "Grizzly Bears", true, 1);
        checkPermanentTapped("skips B's untap", 2, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grizzly Bears", true, 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@wall");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertTapped("Grizzly Bears", false);
    }
}
