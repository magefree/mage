
package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author Susucre
 */
public class JoleneThePlunderQueenTest extends CardTestCommander4Players {

    /*
        Jolene, the Plunder Queen {2}{R}{G}
        Legendary Creature — Human Warrior 2/2

        Whenever a player attacks one or more of your opponents, that attacking player creates a Treasure token.
        If you would create one or more Treasure tokens, instead create those tokens plus an additional Treasure token.
        Sacrifice five Treasures: Put five +1/+1 counters on Jolene.
    */
    String jolene = "Jolene, the Plunder Queen";

    /*
        Elite Vanguard {W}
        Creature — Human Soldier 2/1
    */
    String vanguard = "Elite Vanguard";

    /*
        Balduvian Bears {1}{G}
        Creature — Bear 2/2
    */
    String bear = "Balduvian Bears";

    /**
     * test with three players:
     * A with a Jolene and an Elite Vanguard
     * B, C other players.
     *
     * A attacks both B & C, get two treasures.
     */
    @Test
    public void testAttackingTwoOpponents() {
        addCard(Zone.BATTLEFIELD, playerA, jolene, 1);
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);

        attack(1, playerA, jolene, playerB);
        attack(1, playerA, vanguard, playerC);
        
        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        // 1 attack trigger, +1 Treasure with the replacement effect.
        assertPermanentCount(playerA, "Treasure Token", 2);
    }

    /**
     * test with three players:
     * A with an Elite Vanguard and a Balduvian Bears
     * B with a Jolene
     * C other player.
     *
     * A attacks both B & C, get one treasure.
     */
    @Test
    public void testAttackingJoleneAndAnotherOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);
        addCard(Zone.BATTLEFIELD, playerB, jolene, 1);

        attack(1, playerA, bear, playerB);
        attack(1, playerA, vanguard, playerC);
        
        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        // 1 attack trigger, not controlling Jolene so no replacement effect.
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

    /**
     * test with three players:
     * A with an Elite Vanguard
     * B with a Jolene
     * C other player.
     *
     * A attacks only B, no trigger, no treasure.
     */
    @Test
    public void testAttackingJoleneOnly() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerB, jolene, 1);

        attack(1, playerA, vanguard, playerB);
        
        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        // no trigger, no token.
        assertPermanentCount(playerA, "Treasure Token", 0);
    }

    /**
     * test with three players:
     * A with a Jolene and an Elite Vanguard
     * B with a Jolene.
     * C with a Jolene.
     *
     * A attacks both B & C, 3 triggers, 6 treasures.
     */
    @Test
    public void testEveryoneGotAJolene() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, jolene, 1);
        addCard(Zone.BATTLEFIELD, playerB, jolene, 1);
        addCard(Zone.BATTLEFIELD, playerC, jolene, 1);

        attack(1, playerA, vanguard, playerB);
        attack(1, playerA, jolene, playerC);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        // 3 triggers (1 for each Jolene), +1 Treasure for each with the replacement effect.
        assertPermanentCount(playerA, "Treasure Token", 6);
    }
}
