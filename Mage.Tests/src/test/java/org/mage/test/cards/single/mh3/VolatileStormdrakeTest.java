package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class VolatileStormdrakeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.VolatileStormdrake Volatile Stormdrake} {1}{U}
     * Creature — Drake
     * Flying, hexproof from activated and triggered abilities
     * When Volatile Stormdrake enters the battlefield, exchange control of Volatile Stormdrake and target creature an opponent controls. If you do, you get {E}{E}{E}{E}, then sacrifice that creature unless you pay an amount of {E} equal to its mana value.
     * 3/2
     */
    private static final String drake = "Volatile Stormdrake";

    @Test
    public void test_Hexproof_From_Triggered() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, drake);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Ravenous Chupacabra");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ravenous Chupacabra");
        // no trigger, as the drake has hexproof from triggered ability so there is no valid target

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, drake, 1);
        assertPermanentCount(playerA, "Ravenous Chupacabra", 1);
    }

    @Test
    public void test_Hexproof_From_Activated() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, drake);
        addCard(Zone.BATTLEFIELD, playerA, "Alluring Siren");
        addCard(Zone.HAND, playerB, "Memnite");

        checkPlayableAbility("can not activate Siren", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{T}: Target creature an opponent controls attacks you this turn if able.", false);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memnite");

        checkPlayableAbility("can not activate Siren", 3, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{T}: Target creature an opponent controls attacks you this turn if able.", true);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_CanBeTargetted_BySpell() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, drake);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", drake);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, drake, 1);
    }
}
