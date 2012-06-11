package org.mage.test.sba;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class PlaneswalkerRuleTest extends CardTestPlayerBase {

    /**
     * Check two copies of the same planeswalker will be destroyed
     */
    @Test
    public void testDestroySamePlaneswalkers() {
        addCard(Constants.Zone.HAND, playerA, "Jace, Memory Adept");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Jace, Memory Adept");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Jace, Memory Adept");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace, Memory Adept", 0);
        assertPermanentCount(playerB, "Jace, Memory Adept", 0);
    }

    /**
     * Check two different planeswalkers but with the same subtype will be destroyed
     */
    @Test
    public void testDestroySameSubtype() {
        addCard(Constants.Zone.HAND, playerA, "Jace Beleren");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Jace, Memory Adept");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Jace Beleren");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace Beleren", 0);
        assertPermanentCount(playerB, "Jace, Memory Adept", 0);

    }

}
