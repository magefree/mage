package org.mage.test.cards.single.snc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class UnleashTheInfernoTest extends CardTestPlayerBase {

    /**
     * Reported bug: https://github.com/magefree/mage/issues/10442
     */
    @Test
    public void testExcessDamage() {

        addCard(Zone.BATTLEFIELD, playerB, "Stone Golem"); // creature to damage (4 toughness)
        addCard(Zone.BATTLEFIELD, playerB, "Crucible of Worlds"); // artifact to destroy (cmc 3)
        addCard(Zone.BATTLEFIELD, playerB, "Vedalken Orrery"); // artifact not legal target (cmc 4)
        addCard(Zone.HAND, playerA, "Unleash the Inferno"); // spell to test
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unleash the Inferno");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Unleash the Inferno", 1);
        assertGraveyardCount(playerB, "Stone Golem", 1);
        assertGraveyardCount(playerB, "Crucible of Worlds", 1);
        assertPermanentCount(playerB, "Vedalken Orrery", 1);

    }

}
