package org.mage.test.cards.single.m11;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AutumnsVeilTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AutumnsVeil Autumn's Veil} {G}
     * Instant
     * Spells you control can’t be countered by blue or black spells this turn, and creatures you control can’t be the targets of blue or black spells this turn.
     */
    private static final String veil = "Autumn's Veil";

    @Test
    public void test_BlueCounter() {
        addCard(Zone.HAND, playerA, veil, 1);
        addCard(Zone.HAND, playerA, "Elvish Mystic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, veil, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Mystic");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Elvish Mystic", "Elvish Mystic");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Elvish Mystic", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
    }

    @Test
    public void test_WhiteCounter() {
        addCard(Zone.HAND, playerA, veil, 1);
        addCard(Zone.HAND, playerA, "Elvish Mystic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.HAND, playerB, "Mana Tithe");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, veil, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Mystic");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mana Tithe", "Elvish Mystic", "Elvish Mystic");

        setChoice(playerA, false); // can't pay the Tithe Tax

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Elvish Mystic", 1);
        assertGraveyardCount(playerB, "Mana Tithe", 1);
    }
}
