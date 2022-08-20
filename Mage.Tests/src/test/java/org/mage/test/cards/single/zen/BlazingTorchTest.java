package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BlazingTorchTest extends CardTestPlayerBase {

    private static final String plains = "Plains";
    private static final String torch = "Blazing Torch";
    private static final String lion = "Silvercoat Lion";
    private static final String masterwork = "Masterwork of Ingenuity";
    private static final String halo = "Runed Halo";

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, plains);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, torch);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T},", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, lion, 1);
        assertPermanentCount(playerA, torch, 0);
        assertGraveyardCount(playerA, torch, 1);
    }

    @Test
    public void testCardCopied() {
        addCard(Zone.BATTLEFIELD, playerA, plains, 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        // You may have Masterwork of Ingenuity enter the battlefield as a copy of any Equipment on the battlefield.
        addCard(Zone.HAND, playerA, masterwork);
        addCard(Zone.BATTLEFIELD, playerB, torch);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, masterwork);
        setChoice(playerA, true); // use copy
        setChoice(playerA, torch);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T},", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, lion, 1);
        assertPermanentCount(playerA, torch, 0);
        assertPermanentCount(playerB, torch, 1);
        assertGraveyardCount(playerA, masterwork, 1);
        assertGraveyardCount(playerB, torch, 0);
    }

    @Test
    public void testRunedHalo() {
        addCard(Zone.BATTLEFIELD, playerA, plains);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, torch);
        addCard(Zone.BATTLEFIELD, playerB, plains, 2);
        addCard(Zone.HAND, playerB, halo);

        setChoice(playerB, torch);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, halo);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T},", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20); // Ability resolves as it's from the equipped creature but damage is from torch and is prevented
        assertPermanentCount(playerA, lion, 1);
        assertPermanentCount(playerA, torch, 0);
        assertGraveyardCount(playerA, torch, 1);
    }
}
