package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DontBlinkTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.HAND, playerB, "Don't Blink");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerA, "Augury Raven");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Don't Blink");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Augury Raven", 0);
        assertLibraryCount(playerA, "Augury Raven", 1);
        assertGraveyardCount(playerB, "Don't Blink", 1);
    }

    @Test
    public void testFlip() {
        addCard(Zone.HAND, playerB, "Don't Blink");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerA, "Azusa's Many Journeys");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azusa's Many Journeys");
        castSpell(5, PhaseStep.UPKEEP, playerB, "Don't Blink");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Likeness of the Seeker", 0);
        assertLibraryCount(playerA, "Azusa's Many Journeys", 1);
        assertGraveyardCount(playerB, "Don't Blink", 1);
    }
}
