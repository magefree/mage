package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class PlumbTheForbiddenTest extends CardTestPlayerBase {

    private static final String plumb = "Plumb the Forbidden";
    private static final String bear = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";
    private static final String corpse = "Walking Corpse";

    @Test
    public void testPlumbTheForbidden() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, corpse);
        addCard(Zone.HAND, playerA, plumb);

        setChoice(playerA, String.join("^", bear, lion, corpse));
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, plumb);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, corpse, 1);
        assertGraveyardCount(playerA, plumb, 1);
        assertLife(playerA, 20 - 4);
        assertHandCount(playerA, 4);
    }
}
