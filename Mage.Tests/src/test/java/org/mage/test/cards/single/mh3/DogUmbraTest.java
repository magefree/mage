package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DogUmbraTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DogUmbra Dog Umbra} {1}{W}
     * Flash
     * Enchant creature
     * As long as another player controls enchanted creature, it can’t attack or block. Otherwise, Dog Umbra has umbra armor.
     */
    private static final String umbra = "Dog Umbra";

    @Test
    public void test_Umbra() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, umbra);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, umbra, "Memnite", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Murder", 1);
        assertGraveyardCount(playerA, umbra, 1);
        assertGraveyardCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Not_Umbra() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.HAND, playerA, umbra);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, umbra, "Memnite", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Murder", 1);
        assertGraveyardCount(playerA, umbra, 1);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertPermanentCount(playerB, "Memnite", 0);
    }
}
