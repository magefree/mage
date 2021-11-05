package org.mage.test.cards.single.fut;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class YixlidJailerTest extends CardTestPlayerBase {

    // Rulings on Yixlid Jailer
    // 1. If an ability triggers when the object that has it is put into a graveyard from the battlefield, that ability triggers from the battlefield and isn’t affected by Yixlid Jailer. (2021-03-19)
    // 2. If an ability triggers when the object that has it is put into a graveyard from anywhere other than the battlefield, such as Krosan Tusker or Narcomoeba, that ability triggers from the graveyard.
    //    Yixlid Jailer stops those abilities from triggering at all. This includes abilities that trigger when a card is put into a graveyard “from anywhere,” even if that card was on the battlefield. (2021-03-19)

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
    public void emrakulWrathBaseCase() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Emrakul, the Aeons Torn", 1);
        addCard(Zone.HAND, playerA, "Wrath of God", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertGraveyardCount(playerA, 0); // Emrakul should shuffle graveyard into library
        assertPermanentCount(playerA, "Emrakul, the Aeons Torn", 0);
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

    @Test
    public void emrakulWrathWithJailer() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Emrakul, the Aeons Torn", 1);
        addCard(Zone.HAND, playerA, "Wrath of God", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Yixlid Jailer", 1);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertGraveyardCount(playerA, "Emrakul, the Aeons Torn", 1); // Emrakul should not trigger even if removed from the battlefield
        assertPermanentCount(playerA, "Emrakul, the Aeons Torn", 0);
    }

    @Test
    public void midnightReaperWithJailer() {
        addCard(Zone.BATTLEFIELD, playerA, "Midnight Reaper", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Yixlid Jailer", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, "Midnight Reaper");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertGraveyardCount(playerA, "Midnight Reaper", 1);
        assertPermanentCount(playerB, "Yixlid Jailer", 1);
        assertLife(playerA, 19); // Midnight Reaper should still trigger
    }

    @Test
    public void midnightReaperWrathWithJailer() {
        addCard(Zone.BATTLEFIELD, playerA, "Midnight Reaper", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Yixlid Jailer", 1);
        addCard(Zone.HAND, playerA, "Wrath of God", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertGraveyardCount(playerA, "Midnight Reaper", 1);
        assertGraveyardCount(playerB, "Yixlid Jailer", 1);
        assertLife(playerA, 19); // Midnight Reaper should still trigger
    }
}
