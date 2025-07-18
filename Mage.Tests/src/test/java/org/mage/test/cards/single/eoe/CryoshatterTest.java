package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CryoshatterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.Cryoshatter Cryoshatter} {U}
     * Enchant creature
     * Enchanted creature gets -5/-0.
     * When enchanted creature becomes tapped or is dealt damage, destroy it.
     */
    private static final String shatter = "Cryoshatter";

    @Test
    public void test_tapped() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, shatter);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shatter, "Grizzly Bears");
        attack(1, playerA, "Grizzly Bears", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20);
    }

    @Test
    public void test_damage() {
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser");
        addCard(Zone.HAND, playerA, shatter);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shatter, "Centaur Courser");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Centaur Courser");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Centaur Courser", 1);
    }
}
