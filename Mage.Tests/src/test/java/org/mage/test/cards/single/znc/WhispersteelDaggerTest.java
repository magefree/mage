package org.mage.test.cards.single.znc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class WhispersteelDaggerTest extends CardTestPlayerBase {

    private static final String dagger = "Whispersteel Dagger";
    private static final String goblin = "Raging Goblin";
    private static final String bear = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";
    private static final String forest = "Forest";

    @Test
    public void testDagger() {
        addCard(Zone.BATTLEFIELD, playerA, dagger);
        addCard(Zone.BATTLEFIELD, playerA, forest, 7);
        addCard(Zone.BATTLEFIELD, playerA, goblin);
        addCard(Zone.GRAVEYARD, playerA, bear);
        addCard(Zone.GRAVEYARD, playerB, lion, 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", goblin);

        attack(1, playerA, goblin, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertGraveyardCount(playerB, lion, 1);
        assertPermanentCount(playerA, bear, 0);
        assertGraveyardCount(playerA, bear, 1);
        assertLife(playerB, 20 - 3);
    }
}
