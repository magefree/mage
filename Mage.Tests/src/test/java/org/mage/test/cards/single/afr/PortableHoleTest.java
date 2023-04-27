package org.mage.test.cards.single.afr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class PortableHoleTest extends CardTestPlayerBase {

    // When Portable Hole enters the battlefield, exile target nonland permanent an opponent controls with mana value 2 or less until Portable Hole leaves the battlefield.
    private final String portable_hole = "Portable Hole";

    @Test
    public void opponentsPermanentisExiled(){
        addCard(Zone.HAND, playerA, portable_hole, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, portable_hole);
        addTarget(playerA, "Grizzly Bears");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertExileCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void opponentsPermanentisReturned(){
        addCard(Zone.HAND, playerA, portable_hole, 1);
        addCard(Zone.HAND, playerB, "Shatter");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, portable_hole);
        addTarget(playerA, "Grizzly Bears");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shatter", portable_hole);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }
}
