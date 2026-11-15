package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class ThorAsgardsAvengerTest extends CardTestPlayerBase {

    /**
     * Thor, Asgard's Avenger {2}{R}{R}
     * Legendary Creature - God Warrior Hero 4/4
     * Flying
     * If another source you control would deal damage to an opponent or a permanent an opponent controls,
     * it deals that much damage plus 1 instead.
     */
    private static final String thor = "Thor, Asgard's Avenger";

    @Test
    public void test_OtherSourceDealsOneMore_ThorItselfDoesNot() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, thor);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        attack(3, playerA, thor, playerB);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        // bolt 3 + 1, Thor's own combat damage 4
        assertLife(playerB, 20 - 4 - 4);
    }

    @Test
    public void test_OpponentPermanentGetsOneMore_OwnDoesNot() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, thor);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3
        addCard(Zone.HAND, playerA, "Shock", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Centaur Courser");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Hill Giant");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // 2 + 1 kills the opponent's 3/3, own 3/3 takes the normal 2
        assertGraveyardCount(playerB, "Centaur Courser", 1);
        assertPermanentCount(playerA, "Hill Giant", 1);
    }
}
