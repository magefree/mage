package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class NaduWingedWisdomTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.n.NaduWingedWisdom Nadu, Winged Wisdom} {1}{G}{U}
     * Legendary Creature — Bird Wizard
     * Flying
     * Creatures you control have “Whenever this creature becomes the target of a spell or ability, reveal the top card of your library. If it’s a land card, put it onto the battlefield. Otherwise, put it into your hand. This ability triggers only twice each turn.”
     * 3/4
     */
    private static final String nadu = "Nadu, Winged Wisdom";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, nadu);
        addCard(Zone.BATTLEFIELD, playerA, "Shuko"); // Equip {0}
        addCard(Zone.LIBRARY, playerA, "Forest", 1);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nadu);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nadu);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nadu);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, 3);
        assertPermanentCount(playerA, "Forest", 1);
    }

    @Test
    public void test_AnotherCreature_SeparateCount() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, nadu);
        addCard(Zone.BATTLEFIELD, playerA, "Shuko"); // Equip {0}
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.LIBRARY, playerA, "Forest", 2);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nadu);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nadu);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nadu);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nadu);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerA, 5);
        assertPermanentCount(playerA, "Forest", 2);
    }

    @Test
    public void test_CanTriggerMultipleAtSameTime() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, nadu);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.HAND, playerA, "Martial Glory"); // Target creature gets +3/+0 until end of turn. Target creature gets +0/+3 until end of turn.
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Martial Glory", nadu + "^Elite Vanguard");
        setChoice(playerA, "Whenever"); // trigger order

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerA, 4);
    }
}
