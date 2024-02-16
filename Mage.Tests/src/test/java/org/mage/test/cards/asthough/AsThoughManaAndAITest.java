package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AsThoughManaAndAITest extends CardTestPlayerBase {

    @Test
    public void test_AutoPaymentMustUseAsThoughMana() {
        // possible bug: AI auto-payment uses first mana ability from the mana source, so multi-colored lands can be broken

        // You may spend white mana as though it were red mana.
        addCard(Zone.BATTLEFIELD, playerA, "Sunglasses of Urza", 1);
        //
        // {T}: Add {U} or {W}.
        addCard(Zone.BATTLEFIELD, playerA, "Sea of Clouds", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R}

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
    }
}
