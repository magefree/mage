package org.mage.test.cards.single.gtc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SpellRuptureTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SpellRupture Spell Rupture} {1}{U}
     * Instant
     * Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control.
     */
    private static final String rupture = "Spell Rupture";

    @Test
    public void test_no_creature() {
        addCard(Zone.HAND, playerA, rupture, 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 10);

        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk of the Pearl Trident", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rupture, "Lightning Bolt");
        setChoice(playerA, true); // yes for pays {0}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3);
        assertTappedCount("Volcanic Island", true, 2 + 1);
    }

    @Test
    public void test_4_power() {
        disableManaAutoPayment(playerA); // TODO: investigate why TestPlayer can't pay the Spell Rupture's tax mana here.

        addCard(Zone.HAND, playerA, rupture, 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 10);

        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Borderland Minotaur", 1); // 4/3

        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk of the Pearl Trident", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}.", 5);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}.", 2);

        setChoice(playerA, "Red", 1); // pay for bolt
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        setChoice(playerA, "Blue", 2); // pay for rupture
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rupture, "Lightning Bolt");
        setChoice(playerA, true); // yes for pays {4}
        setChoice(playerA, "Red", 4); // pay the 4

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3);
        assertTappedCount("Volcanic Island", true, 2 + 1 + 4);
    }
}
