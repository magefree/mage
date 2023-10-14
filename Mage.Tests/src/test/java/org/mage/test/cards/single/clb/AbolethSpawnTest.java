package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class AbolethSpawnTest extends CardTestPlayerBase {

    private static final String aboleth = "Aboleth Spawn"; // 2/3
    // Whenever a creature entering the battlefield under an opponent's control
    // causes a triggered ability of that creature to trigger,
    // you may copy that ability. You may choose new targets for the copy.
    private static final String sparkmage = "Sparkmage Apprentice";
    // When Sparkmage Apprentice enters the battlefield, it deals 1 damage to any target.
    private static final String attendant = "Soul's Attendant";
    // Whenever another creature enters the battlefield, you may gain 1 life.
    private static final String hatchling = "Kraken Hatchling"; // 0/4

    @Test
    public void testTriggerCopies() {
        addCard(Zone.BATTLEFIELD, playerA, aboleth);
        addCard(Zone.BATTLEFIELD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerB, attendant);
        addCard(Zone.HAND, playerB, sparkmage);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, sparkmage);
        setChoice(playerB, "Whenever another"); // order triggers
        addTarget(playerB, hatchling); // to deal 1 damage
        setChoice(playerA, true); // yes to copy sparkmage trigger
        setChoice(playerA, true); // yes to change targets
        addTarget(playerA, aboleth); // to deal 1 damage
        setChoice(playerB, true); // yes to gain 1 life

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertDamageReceived(playerA, hatchling, 1);
        assertDamageReceived(playerA, aboleth, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 21);
    }
}
