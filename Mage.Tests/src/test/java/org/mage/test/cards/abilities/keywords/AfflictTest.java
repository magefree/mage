package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AfflictTest extends CardTestPlayerBase {

    private String khenra = "Khenra Eternal";
    private String elves = "Llanowar Elves";

    @Test
    public void testBecomesBlocked(){

        addCard(Zone.BATTLEFIELD, playerA, khenra);
        addCard(Zone.BATTLEFIELD, playerB, elves );

        attack(1, playerA, khenra);
        block(1, playerB, elves, khenra);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 19);

    }

    @Test
    public void testNotBlocked(){

        addCard(Zone.BATTLEFIELD, playerA, khenra);
        addCard(Zone.BATTLEFIELD, playerB, elves );

        attack(1, playerA, khenra);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 18);

    }
}
