package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DrownerOfTruthTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DrownerOfTruth Drowner of Truth} {5}{G/U}{G/U}
     * Creature — Eldrazi
     * Devoid (This card has no color.)
     * When you cast this spell, if {C} was spent to cast it, create two 0/1 colorless Eldrazi Spawn creature tokens with “Sacrifice this creature: Add {C}.”
     * 7/6
     * Drowned Jungle
     * Land
     * Drowned Jungle enters the battlefield tapped.
     * {T}: Add {G} or {U}.
     */
    private static final String drowner = "Drowner of Truth";

    @Test
    public void test_Cast_NoColorless() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, drowner);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, drowner);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 7 + 1);
    }

    @Test
    public void test_Cast_Colorless() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, drowner);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, drowner);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 7 + 1 + 2);
    }
}
