
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Checks that dies triggered ability works when sacrificed
 *
 * @author Susucr
 */
public class SacrificeDiesTriggerTest extends CardTestPlayerBase {

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This test passed 10k times without failure.
    @Test
    public void test_DiesTrigger_ResponseOfSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Footlight Fiend {B/R}
        // Creature — Devil
        // When Footlight Fiend dies, it deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Footlight Fiend", 1);
        // Phyrexian Vault {3}
        // Artifact
        // {2}, {T}, Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Vault", 1);

        setChoice(playerA, "Footlight Fiend");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}");
        addTarget(playerA, playerB); // Fiend trigger

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, "Footlight Fiend", 1);
        assertTappedCount("Phyrexian Vault", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);

        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // Trying to change Su-Chi's zcc to see if that changes anything.
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam_BlinkBefore() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);

        addCard(Zone.HAND, playerA, "Ephemerate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        castSpell(1, PhaseStep.UPKEEP, playerA, "Ephemerate", "Su-Chi");

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }
}
