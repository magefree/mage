package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RassilonTheWarPresidentTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.HAND, playerA, "Delayed Blast Fireball");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Rassilon, the War President");
        addCard(Zone.BATTLEFIELD, playerA, "Crimson Kobolds");
        addCard(Zone.BATTLEFIELD, playerA, "Crookshank Kobolds");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        setChoice(playerA, true);
        setChoice(playerA, "Crimson Kobolds^Crookshank Kobolds");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // the first version cast from exile does 5 damage, the copy does 2
        assertLife(playerB, 13);
    }
}
