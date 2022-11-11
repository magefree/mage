package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PreventAllDamageTest extends CardTestPlayerBase {

    @Test
    public void test_SafePassage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Prevent all damage that would be dealt to you and creatures you control this turn.
        addCard(Zone.HAND, playerA, "Safe Passage"); // Instant {2}{W}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // (2/2)

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 2); // (2/4)
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 2); // Instnat {R}

        castSpell(2, PhaseStep.UPKEEP, playerA, "Safe Passage");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        attack(2, playerB, "Pillarfield Ox");
        attack(2, playerB, "Pillarfield Ox");

        block(2, playerA, "Silvercoat Lion", "Pillarfield Ox");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Safe Passage", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Lightning Bolt", 2);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void test_EtherealHaze() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Prevent all damage that would be dealt by creatures this turn.
        addCard(Zone.HAND, playerA, "Ethereal Haze"); // Instant {W}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // (2/2)

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2); // (2/4)
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1); // Instant {R}

        castSpell(2, PhaseStep.UPKEEP, playerA, "Ethereal Haze");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Silvercoat Lion");

        block(2, playerA, "Silvercoat Lion", "Silvercoat Lion");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Ethereal Haze", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 2);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }

    @Test
    public void test_EnergyStorm() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Cumulative upkeep {1}
        // Prevent all damage that would be dealt by instant and sorcery spells.
        // Creatures with flying don't untap during their controllers' untap steps.
        addCard(Zone.HAND, playerA, "Energy Storm"); // ENCHANTMENT {1}{W}

        // Flying, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin", 1); // (2/2)

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // (2/2)
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2); // Instant {R}
        // Fire Ambush deals 3 damage to any target.
        addCard(Zone.HAND, playerB, "Fire Ambush", 2); // Sorcery {1}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Energy Storm");

        attack(1, playerA, "Abbey Griffin");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Abbey Griffin");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Fire Ambush", playerA);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Fire Ambush", "Abbey Griffin");

        attack(2, playerB, "Silvercoat Lion");

        setChoice(playerA, false); //  Pay {1}?  Energy Storm - CumulativeUpkeepAbility: Cumulative upkeep {1}

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Energy Storm", 1);
        assertPermanentCount(playerA, "Abbey Griffin", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 2);
        assertGraveyardCount(playerB, "Fire Ambush", 2);

        assertLife(playerA, 18);
        assertLife(playerB, 18);
    }

    @Test
    public void test_ArmoredTransport() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Armored Transport");
        addCard(Zone.BATTLEFIELD, playerB, "Armored Transport");

        attack(1, playerA, "Armored Transport");
        block(1, playerB, "Armored Transport", "Armored Transport");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Armored Transport", 1);
        assertGraveyardCount(playerB, "Armored Transport", 1);
    }

    @Test
    public void test_HazeFrog() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Glory Seeker");
        addCard(Zone.BATTLEFIELD, playerA, "Jwari Scuttler");
        addCard(Zone.BATTLEFIELD, playerA, "Lagac Lizard");

        addCard(Zone.BATTLEFIELD, playerB, "Daggerback Basilisk");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 5);
        addCard(Zone.HAND, playerB, "Haze Frog");

        attack(1, playerA, "Glory Seeker");
        attack(1, playerA, "Jwari Scuttler");
        attack(1, playerA, "Lagac Lizard");

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Haze Frog");

        block(1, playerB, "Daggerback Basilisk", "Lagac Lizard");
        block(1, playerB, "Haze Frog", "Glory Seeker");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Glory Seeker", 1); // blocked by the one creature not being prevented
        assertPermanentCount(playerA, "Jwari Scuttler", 1);
        assertPermanentCount(playerA, "Lagac Lizard", 1);

        assertPermanentCount(playerB, "Daggerback Basilisk", 1);
        assertPermanentCount(playerB, "Haze Frog", 1);

        assertLife(playerB, 20);
    }
}
