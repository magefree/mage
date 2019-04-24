
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BrainMaggotTest extends CardTestPlayerBase {

    /**
     * When Brain Maggot enters the battlefield, target opponent reveals his or
     * her hand and you choose a nonland card from it. Exile that card until
     * Brain Maggot leaves the battlefield.
     *
     */
    @Test
    public void testCardFromHandWillBeExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Brain Maggot", 2);

        addCard(Zone.HAND, playerB, "Bloodflow Connoisseur", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brain Maggot");
        addTarget(playerA, "Bloodflow Connoisseur");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Brain Maggot", 1);
        assertExileCount("Bloodflow Connoisseur", 1);
    }

    @Test
    public void testCardFromHandWillBeExiledAndReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // When Brain Maggot enters the battlefield, target opponent reveals their hand and you choose a nonland card from it. Exile that card until Brain Maggot leaves the battlefield.
        addCard(Zone.HAND, playerA, "Brain Maggot", 2);

        addCard(Zone.HAND, playerB, "Bloodflow Connoisseur", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brain Maggot");
        addTarget(playerA, "Bloodflow Connoisseur");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Brain Maggot");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, "Brain Maggot", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerB, "Bloodflow Connoisseur", 1);
        assertExileCount("Bloodflow Connoisseur", 0);
    }

    @Test
    public void testCardFromHandWillBeExiledAndReturnMesmericFiend() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Mesmeric Fiend", 2);
        // Sacrifice a creature: Put a +1/+1 counter on Bloodflow Connoisseur.
        addCard(Zone.HAND, playerB, "Bloodflow Connoisseur", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mesmeric Fiend");
        addTarget(playerA, "Bloodflow Connoisseur");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Mesmeric Fiend");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, "Mesmeric Fiend", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerB, "Bloodflow Connoisseur", 1);
        assertExileCount("Bloodflow Connoisseur", 0);
    }
}
