package org.mage.test.cards.single.bro;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MishraDominationTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MishrasDomination Mishra's Domination} {1}{R}
     * Enchantment — Aura
     * Enchant creature
     * As long as you control enchanted creature, it gets +2/+2. Otherwise, it can’t block.
     */
    private static final String domination = "Mishra's Domination";

    @Test
    public void test_Boost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, domination);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, domination, "Memnite");
        attack(2, playerB, "Grizzly Bears", playerA);
        block(2, playerA, "Memnite", "Grizzly Bears");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Memnite", 3, 3);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_Threaten_Boosted() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, domination);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Threaten");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, domination, "Grizzly Bears", true);
        checkPT("no change in PT", 1, PhaseStep.BEGIN_COMBAT, playerB, "Grizzly Bears", 2, 2);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Threaten", "Grizzly Bears", true);
        checkPT("change in PT after control change", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 4, 4);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CantBlock() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, domination);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, domination, "Grizzly Bears");

        checkPT("no change in PT", 1, PhaseStep.BEGIN_COMBAT, playerB, "Grizzly Bears", 2, 2);
        attack(1, playerA, "Memnite", playerB);
        block(1, playerB, "Grizzly Bears", "Memnite"); // invalid block

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
        assertPowerToughness(playerA, "Memnite", 1, 1);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }
}
