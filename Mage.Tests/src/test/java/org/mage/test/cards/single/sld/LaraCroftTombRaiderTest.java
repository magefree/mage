package org.mage.test.cards.single.sld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class LaraCroftTombRaiderTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.l.LaraCroftTombRaider Lara Croft, Tomb Raider} {G}{U}{R}
     * Legendary Creature — Human Ranger
     * First strike, reach
     * Whenever Lara Croft attacks, exile up to one target legendary artifact card or legendary land card from a graveyard and put a discovery counter on it. You may play a card from exile with a discovery counter on it this turn.
     * Raid — At end of combat on your turn, if you attacked this turn, create a Treasure token.
     * 3/4
     */
    private static final String lara = "Lara Croft, Tomb Raider";

    // Bug: other players could use lara's permission effect.
    @Test
    public void test_Lara_Permission_Over_Two_Turns() {
        setStrictChooseMode(true);

        /** {1} Legendary Artifact with Flash */
        final String pendant = "Gerrard's Hourglass Pendant";

        addCard(Zone.BATTLEFIELD, playerA, lara);
        addCard(Zone.GRAVEYARD, playerB, pendant);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        attack(1, playerA, lara, playerB);
        addTarget(playerA, pendant); // target for trigger

        checkPlayableAbility("playerA can cast Pendant turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + pendant, true);
        checkPlayableAbility("playerB can not cast Pendant turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast " + pendant, false);

        checkPlayableAbility("playerA can not cast Pendant turn 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + pendant, false);
        checkPlayableAbility("playerB can not cast Pendant turn 2", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast " + pendant, false);

        checkPlayableAbility("playerA can cast Pendant turn 3 before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + pendant, false);
        checkPlayableAbility("playerB can not cast Pendant turn 3  before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast " + pendant, false);

        attack(3, playerA, lara, playerB);
        addTarget(playerA, playerA.TARGET_SKIP); // no target this time

        checkPlayableAbility("playerA can cast Pendant turn 3 post combat", 1, PhaseStep.END_COMBAT, playerA, "Cast " + pendant, true);
        checkPlayableAbility("playerB can not cast Pendant turn 3 post combat", 1, PhaseStep.END_COMBAT, playerB, "Cast " + pendant, false);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, pendant);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, pendant, 1);
        assertTappedCount("Wastes", true, 1);
        assertLife(playerB, 20 - 3 - 3);
    }
}
