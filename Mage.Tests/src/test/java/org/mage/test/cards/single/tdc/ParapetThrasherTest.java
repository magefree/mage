package org.mage.test.cards.single.tdc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author Jmlundeen
 */
public class ParapetThrasherTest extends CardTestCommander4Players {

    /*
    Parapet Thrasher
    {2}{R}{R}
    Creature - Dragon
    Flying
    Whenever one or more Dragons you control deal combat damage to an opponent, choose one that hasn't been chosen this turn --
    * Destroy target artifact that opponent controls.
    * This creature deals 4 damage to each other opponent.
    * Exile the top card of your library. You may play it this turn.
    4/3
    */
    private static final String parapetThrasher = "Parapet Thrasher";

    /*
    Fountain of Youth
    {0}
    Artifact
    {2}, {tap}: You gain 1 life.
    */
    private static final String fountainOfYouth = "Fountain of Youth";

    @Test
    public void testParapetThrasherMode1() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, parapetThrasher);
        addCard(Zone.BATTLEFIELD, playerB, fountainOfYouth);

        attack(1, playerA, parapetThrasher, playerB);
        setModeChoice(playerA, "1");
        addTarget(playerA, fountainOfYouth);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, fountainOfYouth, 1);
    }

    @Test
    public void testParapetThrasherMode2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, parapetThrasher);

        attack(1, playerA, parapetThrasher);
        setModeChoice(playerA, "2");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertLife(playerC, 20 - 4);
        assertLife(playerD, 20 - 4);
    }

    @Test
    public void testParapetThrasherMode3() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, parapetThrasher);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Mountain");

        attack(1, playerA, parapetThrasher);
        setModeChoice(playerA, "3");

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0);
        assertPermanentCount(playerA, "Island", 1);
    }
}