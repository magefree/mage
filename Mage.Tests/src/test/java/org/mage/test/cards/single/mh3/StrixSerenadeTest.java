package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class StrixSerenadeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.StrixSerenade Strix Serenade} {U}
     * Instant
     * Counter target artifact, creature, or planeswalker spell. Its controller creates a 2/2 blue Bird creature token with flying.
     */
    private static final String serenade = "Strix Serenade";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, serenade);
        addCard(Zone.HAND, playerB, "Mox Ruby");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mox Ruby");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, serenade, "Mox Ruby", "Mox Ruby");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Mox Ruby", 1);
        assertPermanentCount(playerB, "Bird Token", 1);
    }

    @Test
    public void test_Simple_OwnSpellTargetted() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, serenade);
        addCard(Zone.HAND, playerA, "Mox Ruby");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Ruby");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, serenade, "Mox Ruby", "Mox Ruby");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Mox Ruby", 1);
        assertPermanentCount(playerA, "Bird Token", 1);
    }

    @Test
    public void test_CantBeCountered_CreateToken() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, serenade);
        addCard(Zone.HAND, playerB, "Chandra, Awakened Inferno"); // Can't be countered
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Chandra, Awakened Inferno");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, serenade, "Chandra, Awakened Inferno", "Chandra, Awakened Inferno");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Chandra, Awakened Inferno", 1);
        assertPermanentCount(playerB, "Bird Token", 1);
    }
}
