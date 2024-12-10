package org.mage.test.cards.single._40k;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

public class LuciusTheEternalTest extends CardTestPlayerBase {
    
    private static String LUCIUS = "Lucius the Eternal";
    private static String BOLT = "Lightning Bolt";
    private static String LION = "Silvercoat Lion";

    /**
     * When Lucius the Eternal dies, exile it and choose target creature an opponent controls. 
     * When that creature leaves the battlefield, return Lucius the Eternal from exile to the battlefield under its owner's control.
     */
    @Test
    public void testEffect() {
        addCard(Zone.BATTLEFIELD, playerA, LUCIUS, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, BOLT, 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, BOLT, 1);

        // Player B bolts Lucius, killing it and triggering its effect.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, BOLT, LUCIUS, true);

        // Player A chooses Silvercoat Lion for his effect
        addTarget(playerA, LION);

        // Player A bolts the Lion, which should trigger Lucius to return
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, BOLT, LION, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, LUCIUS, 1);
    }
}
