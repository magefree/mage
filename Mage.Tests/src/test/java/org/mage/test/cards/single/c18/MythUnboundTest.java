package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author DarioBarrabinoDev
 */
public class MythUnboundTest extends CardTestCommander4Players {

    /**
     * Myth Unbound {2}{G}
     * Enchantment
     * Your commander costs {1} less to cast for each time it's been cast from the command zone this game.
     * Whenever your commander is put into the command zone from anywhere, draw a card.
     */
    private static final String myth = "Myth Unbound";

    private static final String bears = "Balduvian Bears"; // {1}{G}, 2/2

    @Test
    public void test_FromGraveyard() {
        addCard(Zone.COMMAND, playerA, bears, 1);
        addCard(Zone.BATTLEFIELD, playerA, myth, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", bears);
        setChoice(playerA, true); // move to command zone (state-based action)

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, bears, 1);
        assertHandCount(playerA, 1 + 1); // draw step + Myth Unbound
    }

    @Test
    public void test_FromGraveyard_StayInGraveyard() {
        addCard(Zone.COMMAND, playerA, bears, 1);
        addCard(Zone.BATTLEFIELD, playerA, myth, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", bears);
        setChoice(playerA, false); // leave in graveyard

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, bears, 1);
        assertHandCount(playerA, 1); // draw step only
    }

    @Test
    public void test_FromExile() {
        addCard(Zone.COMMAND, playerA, bears, 1);
        addCard(Zone.BATTLEFIELD, playerA, myth, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerB, "Swords to Plowshares", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Swords to Plowshares", bears);
        setChoice(playerA, true); // move to command zone (state-based action)

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, bears, 1);
        assertHandCount(playerA, 1 + 1); // draw step + Myth Unbound
    }

    @Test
    public void test_FromBattlefieldToHand() {
        addCard(Zone.COMMAND, playerA, bears, 1);
        addCard(Zone.BATTLEFIELD, playerA, myth, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerB, "Unsummon", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon", bears);
        setChoice(playerA, true); // move to command zone instead of hand (replacement effect)

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, bears, 1);
        assertHandCount(playerA, 1 + 1); // draw step + Myth Unbound
    }

    @Test
    public void test_FromStackToLibrary() {
        addCard(Zone.COMMAND, playerA, bears, 1);
        addCard(Zone.BATTLEFIELD, playerA, myth, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerB, "Memory Lapse", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Memory Lapse", bears, bears);
        setChoice(playerA, true); // move to command zone instead of library (replacement effect)

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, bears, 1);
        assertHandCount(playerA, 1 + 1); // draw step + Myth Unbound
    }

    @Test
    public void test_SorceryCommanderResolves() {
        // Legendary Sorcery {1}{R}{G}
        // Discover X, where X is the amount of mana spent to cast this spell.
        // Ashaya's Enduring Bond can be your commander.
        addCard(Zone.COMMAND, playerA, "Ashaya's Enduring Bond", 1);
        addCard(Zone.BATTLEFIELD, playerA, myth, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda", 1); // legendary creature
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ashaya's Enduring Bond");
        setChoice(playerA, false); // discover: put Grizzly Bears into hand
        setChoice(playerA, true); // move to command zone (state-based action)

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, "Ashaya's Enduring Bond", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 1 + 1 + 1); // draw step + Grizzly Bears + Myth Unbound
    }
}
