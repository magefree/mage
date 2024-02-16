package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class FeastOfTheVictoriousDeadTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.FeastOfTheVictoriousDead}
     * Feast of the Victorious Dead
     * {W}{B}
     * Enchantment
     * At the beginning of your end step, if one or more creatures died this turn, you gain that much life and distribute that many +1/+1 counters among creatures you control.
     */
    private static final String feast = "Feast of the Victorious Dead";

    /**
     * {@link mage.cards.f.FanaticalFirebrand}
     * <p>
     * {T}, Sacrifice Fanatical Firebrand: It deals 1 damage to any target.
     * 1/1
     */
    private static final String firebrand = "Fanatical Firebrand";

    private static final String memnite = "Memnite"; // vanilla 1/1
    private static final String bears = "Grizzly Bears";  // vanilla 2/2
    private static final String seeker = "Glory Seeker";  // vanilla 2/2

    @Test
    public void noDistribution() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, feast);
        addCard(Zone.BATTLEFIELD, playerA, firebrand);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}, Sacrifice", memnite);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 + 2);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerB, 0);
    }

    @Test
    public void distributeOn1() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, feast);
        addCard(Zone.BATTLEFIELD, playerA, firebrand);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, seeker);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}, Sacrifice", memnite);

        addTargetAmount(playerA, seeker, 2);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 + 2);
        assertPermanentCount(playerA, 3);
        assertPowerToughness(playerA, seeker, 2 + 2, 2 + 2);
        assertPowerToughness(playerA, bears, 2 + 0, 2 + 0);
        assertPermanentCount(playerB, 0);
    }

    @Test
    public void distributeAmong2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, feast);
        addCard(Zone.BATTLEFIELD, playerA, firebrand);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, seeker);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}, Sacrifice", memnite);

        addTargetAmount(playerA, seeker, 1);
        addTargetAmount(playerA, bears, 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 + 2);
        assertPermanentCount(playerA, 3);
        assertPowerToughness(playerA, seeker, 2 + 1, 2 + 1);
        assertPowerToughness(playerA, bears, 2 + 1, 2 + 1);
        assertPermanentCount(playerB, 0);
    }
}
