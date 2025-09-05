package org.mage.test.cards.single.big;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class EsotericDuplicatorTest extends CardTestPlayerBase {

    /*
    Esoteric Duplicator
    {2}{U}
    Artifact — Clue

    Whenever you sacrifice this artifact or another artifact, you may pay {2}. If you do, at the beginning of the next end step, create a token that’s a copy of that artifact.

    {2}, Sacrifice this artifact: Draw a card.
     */
    private static final String esotericDuplicator = "Esoteric Duplicator";

    /*
    Sculpting Steel
    {3}
    Artifact
    You may have Sculpting Steel enter the battlefield as a copy of any artifact on the battlefield.
    */
    private static final String sculptingSteel = "Sculpting Steel";

    @Test
    public void testEsotericDuplicator() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, esotericDuplicator);
        addCard(Zone.HAND, playerA, sculptingSteel);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sculptingSteel);
        setChoice(playerA, true);
        setChoice(playerA, esotericDuplicator);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Sacrifice"); // Sacrifice duplicator
        setChoice(playerA, true); // duplicate


        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, esotericDuplicator, 1);
    }
}