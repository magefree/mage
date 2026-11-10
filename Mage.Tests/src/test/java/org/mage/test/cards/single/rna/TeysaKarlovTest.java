package org.mage.test.cards.single.rna;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class TeysaKarlovTest extends CardTestPlayerBase {

    /* Teysa Karlov {2}{W}{B} 2/4
     * Legendary Creature — Human Advisor
     * If a creature dying causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
     * Creature tokens you control have vigilance and lifelink.
     */
    private static final String teysa = "Teysa Karlov";

    /* Revel in Riches {4}{B}
     * Enchantment
     * Whenever a creature an opponent controls dies, create a Treasure token.
     * At the beginning of your upkeep, if you control ten or more Treasures, you win the game.
     */
    private static final String revel = "Revel in Riches";

    private static final String bloodthrone = "Bloodthrone Vampire"; // 1/1
    // Sacrifice a creature: Bloodthrone Vampire gets +2/+2 until end of turn.

    private static final String ministrant = "Ministrant of Obligation"; // 2/1 Afterlife 2

    @Test
    public void testTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, teysa);
        addCard(Zone.BATTLEFIELD, playerB, revel);
        addCard(Zone.BATTLEFIELD, playerA, bloodthrone);
        addCard(Zone.BATTLEFIELD, playerA, ministrant);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, ministrant);
        setChoice(playerA, "Afterlife"); // order identical triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, bloodthrone, 3, 3);
        assertGraveyardCount(playerA, ministrant, 1);
        assertPermanentCount(playerA, "Spirit Token", 4);
        assertPermanentCount(playerB, "Treasure Token", 1);
    }

    @Test
    public void test_OneOrMoreCreaturesDie_TriggersAdditionalTime() {
        addCard(Zone.BATTLEFIELD, playerA, teysa);
        //
        // Whenever one or more creatures die, put a rev counter on Chainsaw.
        addCard(Zone.BATTLEFIELD, playerA, "Chainsaw");
        //
        // Each player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Innocent Blood"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // two creatures die at once - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Innocent Blood");
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerB, "Grizzly Bears");
        setChoice(playerA, "Whenever one or more creatures die"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Chainsaw", CounterType.REV, 2);
    }

    @Test
    public void test_DiesAtSameTimeAsOtherCreatures_TriggersAdditionalTime() {
        addCard(Zone.BATTLEFIELD, playerA, teysa);
        //
        // Whenever one or more creatures die, put a rev counter on Chainsaw.
        addCard(Zone.BATTLEFIELD, playerA, "Chainsaw");
        //
        // Destroy all creatures. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Wrath of God"); // {2}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // all creatures die at once, including Teysa - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");
        setChoice(playerA, "Whenever one or more creatures die"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, teysa, 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Chainsaw", CounterType.REV, 2);
    }
}
