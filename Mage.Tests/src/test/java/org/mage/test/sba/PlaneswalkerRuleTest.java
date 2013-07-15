package org.mage.test.sba;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class PlaneswalkerRuleTest extends CardTestPlayerBase {

    /**
     * Check two copies of the same planeswalker played from different players won't be destroyed
     */
    @Test
    public void testDontDestroySamePlaneswalkers() {
        addCard(Zone.HAND, playerA, "Jace, Memory Adept");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Jace, Memory Adept");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace, Memory Adept");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace, Memory Adept", 1);
        assertPermanentCount(playerB, "Jace, Memory Adept", 1);
    }

    /**
     * Check two different planeswalkers but with the same subtype played by two different players won't be destroyed
     */
    @Test
    public void testDontDestroySameSubtype() {
        addCard(Zone.HAND, playerA, "Jace Beleren");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Jace, Memory Adept");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace Beleren");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace Beleren", 1);
        assertPermanentCount(playerB, "Jace, Memory Adept", 1);

    }
    /**
     * Check two copies of the same planeswalker played from the same player that one must be sacrificed
     */
    @Test
    public void testDestroySamePlaneswalkers() {
        addCard(Zone.HAND, playerA, "Jace, Memory Adept");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Memory Adept");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace, Memory Adept");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace, Memory Adept", 1);
    }

}
