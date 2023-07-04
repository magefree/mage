
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author notgreat
 */
public class SpectacleTest extends CardTestPlayerBase {

    /**
     *
     */
    @Test
    public void testWithoutSpectacle1() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Spikewheel Acrobat"); // {3}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spikewheel Acrobat");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA,"Spikewheel Acrobat",1);
        assertTappedCount("Mountain",true,4);
    }
    @Test
    public void testWithoutSpectacle2() {
        // Rafter Demon {2}{B}{R}
        // Spectacle {3}{B}{R}
        // When Rafter Demon enters the battlefield, if its spectacle cost was paid, each opponent discards a card.
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 6);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.HAND, playerA, "Rafter Demon"); // {2}{B}{R}
        addCard(Zone.HAND, playerB, "Darksteel Relic",5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt",playerB);
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rafter Demon");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA,"Rafter Demon",1);
        assertTappedCount("Badlands",true,5);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 17);
        assertGraveyardCount(playerB, "Darksteel Relic", 0);
        assertHandCount(playerB, "Darksteel Relic", 5);
    }

    @Test
    public void testWithSpectacle() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.HAND, playerA, "Spikewheel Acrobat"); // Surge {2}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt",playerB);
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spikewheel Acrobat with spectacle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA,"Spikewheel Acrobat",1);
        assertTappedCount("Mountain",true,4);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 17);
    }

    @Test
    public void testRafterDemonCopyClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 5);
        // Rafter Demon {2}{B}{R}
        // Spectacle {3}{B}{R}
        // When Rafter Demon enters the battlefield, if its spectacle cost was paid, each opponent discards a card.
        addCard(Zone.HAND, playerA, "Rafter Demon");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Darksteel Relic",5);

        addCard(Zone.HAND, playerA, "Double Major");
        addCard(Zone.HAND, playerA, "Clone");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rafter Demon with spectacle");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major");
        addTarget(playerA, "Rafter Demon");
        addTarget(playerB, "Darksteel Relic",2);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("Discard x2", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Darksteel Relic", 2);
        checkPermanentCount("Rafter Demon x2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rafter Demon", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true); // copy
        setChoice(playerA, "Rafter Demon");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Darksteel Relic", 2);
        assertHandCount(playerB, "Darksteel Relic", 3);
        assertPermanentCount(playerA, "Rafter Demon", 3);

        assertLife(playerB, 17);
    }
}
