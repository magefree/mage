package org.mage.test.cards.single.blc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ZinniaValleysVoiceTest extends CardTestPlayerBase {

    private static final String zinnia = "Zinnia, Valley's Voice";
    private static final String lion = "Silvercoat Lion";
    private static final String bandit = "Prosperous Bandit";

    // CR 702.175a/b: Offspring is an additional cost and creates a linked ETB trigger; multiple instances are paid separately.
    // CR 607.2i, 607.5: linked abilities remain linked per instance, including abilities gained from other effects.

    @Test
    public void testGrantsOffspringToCreatureSpells() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, lion);

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 2);
        assertTokenCount(playerA, lion, 1);
    }

    @Test
    public void testGrantedOffspringSourceRemovedBeforeEtbNoCopy() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, lion);
        addCard(Zone.HAND, playerA, "Path to Exile");

        setChoice(playerA, true);
        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lion);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Path to Exile", zinnia);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertTokenCount(playerA, lion, 0);
    }

    @Test
    public void testRemoveZinniaWhileOffspringTriggersOnStackBothStillResolve() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, bandit);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.HAND, playerB, "Path to Exile");

        setChoice(playerA, true); // Pay printed offspring {1}
        setChoice(playerA, true); // Pay granted offspring {2}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);
        setChoice(playerA, bandit); // stack both offspring triggers (2 triggers -> 1 choice)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Path to Exile", zinnia, "create a 1/1 token copy of it.");
        setChoice(playerA, false); // Decline Path's basic land search

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, zinnia, 0);
        assertPermanentCount(playerA, bandit, 3);
        assertTokenCount(playerA, bandit, 2);
    }

    @Test
    public void testPanharmoniconWithPrintedAndGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, bandit);

        setChoice(playerA, true); // Pay printed offspring {1}
        setChoice(playerA, true); // Pay granted offspring {2}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);
        setChoice(playerA, bandit, 3); // stack four offspring triggers (4 triggers -> 3 choices)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bandit, 5);
        assertTokenCount(playerA, bandit, 4);
    }
}
