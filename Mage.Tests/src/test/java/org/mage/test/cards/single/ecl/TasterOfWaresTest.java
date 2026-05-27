package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TasterOfWaresTest extends CardTestPlayerBase {

    @Test
    public void testControllerChoosesRevealedCardToExileAndCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Taster of Wares");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Forest");

        // Taster and Raging Goblin make X=2, so playerB reveals both cards.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Taster of Wares");
        addTarget(playerA, playerB);
        setChoice(playerB, "Lightning Bolt^Forest");
        setChoice(playerA, "Lightning Bolt");

        // The chosen instant should be exiled and castable with any mana while Taster remains controlled.
        checkPlayableAbility("chosen instant is playable from exile", 1, PhaseStep.POSTCOMBAT_MAIN,
                playerA, "Cast Lightning Bolt", true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerB, "Forest", 1);
        assertPermanentCount(playerA, "Taster of Wares", 1);
    }

    @Test
    public void testBasic() {
        addCard(Zone.HAND, playerB, "Lightning Bolt", 3);
        addCard(Zone.HAND, playerA, "Taster of Wares");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Assailant", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Taster of Wares");
        addTarget(playerA, playerB);
        setChoice(playerB, "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");
        setChoice(playerA, "Lightning Bolt");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
    }

}
