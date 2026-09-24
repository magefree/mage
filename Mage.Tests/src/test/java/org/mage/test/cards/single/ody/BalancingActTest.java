package org.mage.test.cards.single.ody;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.b.BalancingAct Balancing Act}
 * {2}{W}{W}
 * Sorcery
 * Each player chooses a number of permanents they control equal to the number of permanents
 * controlled by the player who controls the fewest, then sacrifices the rest.
 * Each player discards cards the same way.
 */
public class BalancingActTest extends CardTestPlayerBase {

    private static final String balancingAct = "Balancing Act";

    @Test
    public void test_Normal() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, balancingAct);
        addCard(Zone.HAND, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1);
        addCard(Zone.HAND, playerB, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, balancingAct);

        setStrictChooseMode(false); // auto-choose permanents/cards to keep
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 4);
        assertPermanentCount(playerB, 4);
        assertHandCount(playerA, 1);
        assertHandCount(playerB, 1);
        assertGraveyardCount(playerA, balancingAct, 1);
    }

    /**
     * Issue #16361: if a player controls no permanents, every other player must sacrifice all permanents
     */
    @Test
    public void test_ZeroPermanents() {
        // {T}, Sacrifice Ruins of Trokair: Add {W}{W}.
        addCard(Zone.BATTLEFIELD, playerA, "Ruins of Trokair", 2);
        addCard(Zone.HAND, playerA, balancingAct);
        addCard(Zone.HAND, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1);
        addCard(Zone.HAND, playerB, "Swamp", 2);

        // pay for Balancing Act by sacrificing both lands -> playerA controls no permanents
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, balancingAct);

        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 0);
        assertPermanentCount(playerB, 0);
        assertGraveyardCount(playerB, "Swamp", 3 + 1); // 3 sacrificed + 1 discarded
        assertGraveyardCount(playerB, "Runeclaw Bear", 1);
        // cards in hand: playerA has 1, playerB has 2 -> playerB discards 1
        assertHandCount(playerA, 1);
        assertHandCount(playerB, 1);
    }

    /**
     * If a player has no cards in hand, every other player must discard their whole hand
     */
    @Test
    public void test_ZeroCardsInHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, balancingAct);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.HAND, playerB, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, balancingAct);

        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 4);
        assertPermanentCount(playerB, 4);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
        assertGraveyardCount(playerB, "Swamp", 3);
    }
}
