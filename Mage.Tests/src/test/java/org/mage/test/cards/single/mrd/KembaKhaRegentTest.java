package org.mage.test.cards.single.mrd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.k.KembaKhaRegent Kemba, Kha Regent} {1}{W}{W}, 2/4
 * At the beginning of your upkeep, create a 2/2 white Cat creature token for each Equipment attached to
 * Kemba, Kha Regent.
 *
 * @author notgreat
 */
public class KembaKhaRegentTest extends CardTestPlayerBase {

    private static final String kemba = "Kemba, Kha Regent";

    /**
     * 608.2h: once Kemba has left the battlefield, the trigger counts the Equipment attached to her as she last
     * existed there.
     */
    @Test
    public void testKembaRemovedInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 3);
        addCard(Zone.BATTLEFIELD, playerA, kemba);
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter"); // Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Vulshok Morningstar"); // Equip {2}
        addCard(Zone.HAND, playerA, "Doom Blade"); // {1}{B} instant: destroy target nonblack creature

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", kemba);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", kemba);

        castSpell(3, PhaseStep.UPKEEP, playerA, "Doom Blade", kemba);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, kemba, 1);
        assertPermanentCount(playerA, "Cat Token", 2);
    }
}
