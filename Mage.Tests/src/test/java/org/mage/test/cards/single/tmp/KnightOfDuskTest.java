package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class KnightOfDuskTest extends CardTestPlayerBase {
    private static final String knight = "Knight of Dusk";
    private static final String lion = "Silvercoat Lion";
    private static final String murder = "Murder";
    private static final String blink = "Momentary Blink";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, knight);
        addCard(Zone.BATTLEFIELD, playerB, lion);

        attack(1, playerA, knight);
        block(1, playerB, lion, knight);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{B}{B}", lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, knight, 1);
        assertPermanentCount(playerB, lion, 0);
        assertGraveyardCount(playerA, knight, 0);
        assertGraveyardCount(playerB, lion, 1);
    }

    @Test
    public void testLeavesBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, knight);
        addCard(Zone.BATTLEFIELD, playerB, lion);
        addCard(Zone.HAND, playerA, murder);

        attack(1, playerA, knight);
        block(1, playerB, lion, knight);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{B}{B}", lion);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, murder, knight);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, knight, 0);
        assertPermanentCount(playerB, lion, 0);
        assertGraveyardCount(playerA, knight, 1);
        assertGraveyardCount(playerB, lion, 1);
    }

    @Test
    public void testLeavesBattlefieldReturns() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.BATTLEFIELD, playerA, knight);
        addCard(Zone.BATTLEFIELD, playerB, lion);
        addCard(Zone.HAND, playerA, blink);

        attack(1, playerA, knight);
        block(1, playerB, lion, knight);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{B}{B}", lion);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, blink, knight);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, knight, 1);
        assertPermanentCount(playerB, lion, 0);
        assertGraveyardCount(playerA, knight, 0);
        assertGraveyardCount(playerB, lion, 1);
    }
}
