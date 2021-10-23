package org.mage.test.cards.single.fut;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class YixlidJailerTest extends CardTestPlayerBase {

    @Test
    public void narcomoebaBaseCase() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Narcomoeba", 1);
        addCard(Zone.HAND, playerA, "Thought Scour", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought Scour");
        addTarget(playerA, playerA);
        setChoice(playerA, true); // Use Narcomoeba's ability

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Narcomoeba", 1);
        assertGraveyardCount(playerA, "Thought Scour", 1);
        assertGraveyardCount(playerA, "Narcomoeba", 0);
    }

    @Test
    public void emrakulBaseCase() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Emrakul, the Aeons Torn", 1);
        addCard(Zone.HAND, playerA, "Thought Scour", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought Scour");
        addTarget(playerA, playerA);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertGraveyardCount(playerA, 0); // Emrakul should shuffle graveyard into library
    }

    @Test
    public void narcomoebaWithJailer() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Narcomoeba", 1);
        addCard(Zone.HAND, playerA, "Thought Scour", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Yixlid Jailer", 1);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought Scour");
        addTarget(playerA, playerA);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Narcomoeba", 0);
        assertGraveyardCount(playerA, "Thought Scour", 1);
        assertGraveyardCount(playerA, "Narcomoeba", 1);
    }

    @Test
    public void emrakulWithJailer() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Emrakul, the Aeons Torn", 1);
        addCard(Zone.HAND, playerA, "Thought Scour", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Yixlid Jailer", 1);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought Scour");
        addTarget(playerA, playerA);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertGraveyardCount(playerA, "Emrakul, the Aeons Torn", 1);
    }
}
