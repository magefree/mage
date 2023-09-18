package org.mage.test.cards.single.arb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Sen Triplets
 * {2}{W}{U}{B}
 * Legendary Artifact Creature — Human Wizard
 * At the beginning of your upkeep, choose target opponent.
 * This turn, that player can’t cast spells or activate abilities and plays with their hand revealed.
 * You may play lands and cast spells from that player’s hand this turn.
 *
 * @author TheElk801
 */
public class SenTripletsTest extends CardTestPlayerBase {

    private static final String triplets = "Sen Triplets";
    private static final String bolt = "Lightning Bolt";
    private static final String relic = "Darksteel Relic";

    private void initTriplets() {
        addCard(Zone.BATTLEFIELD, playerA, triplets);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.BATTLEFIELD, playerB, "Taiga");

        addCard(Zone.HAND, playerB, bolt);
        addCard(Zone.HAND, playerB, relic);
        addCard(Zone.HAND, playerB, "Island");

    }

    /**
     * Player who cast Sen Triplets must still be able to cast spells this turn, it's only playerB who can't.
     */
    @Test
    public void testCastSpell() {
        initTriplets();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic, true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, triplets, 1);
        assertPermanentCount(playerA, relic, 1);
        assertPermanentCount(playerA, "Island", 1);
        assertHandCount(playerB, bolt, 0);
        assertHandCount(playerB, relic, 0);
        assertGraveyardCount(playerB, 1);
        assertLife(playerB, 20 - 3);
    }

    /**
     * Target player (playerB) can't activate abilities on turn 1 since Sen Triplets was just cast.
     */
    @Test
    public void testCantActivate() {
        initTriplets();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}");

        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: {T}")) {
                Assert.fail("must throw error about bad targets, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * Target player (playerB) can't cast a spell on turn 1 since Sen Triplets was just cast.
     */
    @Test
    public void testCantCast() {
        initTriplets();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, playerA);

        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Cast Lightning Bolt$targetPlayer=PlayerA")) {
                Assert.fail("must throw error about bad targets, but got:\n" + e.getMessage());
            }
        }
    }
}
