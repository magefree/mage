package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ZinniaValleysVoiceTest extends CardTestPlayerBase {

    private static final String zinnia = "Zinnia, Valley's Voice";
    private static final String lion = "Silvercoat Lion";

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
}
