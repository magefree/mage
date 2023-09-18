package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author LevelX2
 */
public class CastBGPartnerCommanderTest extends CardTestCommanderDuelBase {

    /**
     * With commander rule changes 6/2020 Reyhan goes to exile first before it
     * goes to command zone. So it's triggerd ability does no longer move the
     * counters on it on the battelfield
     */
    @Test
    public void testExileReyhan() {
        addCard(Zone.COMMAND, playerA, "Reyhan, Last of the Abzan", 1);
        addCard(Zone.COMMAND, playerA, "Ikra Shidiqi, the Usurper", 1);
        addCard(Zone.COMMAND, playerB, "Daxos of Meletis", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // As an additional cost to cast this spell, reveal a colorless creature card from your hand.
        // Exile target creature if its power is less than or equal to the revealed card's power.
        addCard(Zone.HAND, playerB, "Titan's Presence", 1);
        addCard(Zone.HAND, playerB, "Ancient Stone Idol", 1); // Artifact Creature 12/12
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        // Reyhan, Last of the Abzan enters the battlefield with three +1/+1 counters on it.
        // Whenever a creature you control dies or is put into the command zone,
        // if it had one or more +1/+1 counters on it, you may put that many +1/+1 counters on target creature.
        // Partner (You can have two commanders if both have partner.)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reyhan, Last of the Abzan");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Titan's Presence", "Reyhan, Last of the Abzan");
        setChoice(playerB, "Ancient Stone Idol");

        setChoice(playerA, true); // Commander goes to command zone

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Titan's Presence", 1);
        assertCommandZoneCount(playerA, "Reyhan, Last of the Abzan", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
    }

    @Test
    public void testCastBothPartnerCommanders() {
        addCard(Zone.COMMAND, playerA, "Reyhan, Last of the Abzan", 1);
        addCard(Zone.COMMAND, playerA, "Ikra Shidiqi, the Usurper", 1);
        addCard(Zone.COMMAND, playerB, "Daxos of Meletis", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Reyhan, Last of the Abzan enters the battlefield with three +1/+1 counters on it.
        // Whenever a creature you control dies or is put into the command zone,
        // if it had one or more +1/+1 counters on it, you may put that many +1/+1 counters on target creature.
        // Partner (You can have two commanders if both have partner.)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reyhan, Last of the Abzan"); // {1}{B}{G} Creature 0/0

        // Menace
        // Whenever a creature you control deals combat damage to a player, you gain life equal to that creature's toughness.
        // Partner
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ikra Shidiqi, the Usurper"); // Creature 3/7 {3}{B}{G}

        attack(3, playerA, "Reyhan, Last of the Abzan");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCommandZoneCount(playerA, "Reyhan, Last of the Abzan", 0);
        assertCommandZoneCount(playerA, "Ikra Shidiqi, the Usurper", 0);

        assertPowerToughness(playerA, "Reyhan, Last of the Abzan", 3, 3);
        assertPowerToughness(playerA, "Ikra Shidiqi, the Usurper", 3, 7);

        assertLife(playerA, 43);
        assertLife(playerB, 37);
    }
}
