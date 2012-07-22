package org.mage.test.cards.replacement.lifereduce;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Elderscale Wurm:
 *   As long as you have 7 or more life, damage that would reduce your life total to less than 7 reduces it to 7 instead..
 *
 * @author magenoxx_at_gmail.com
 */
public class DamageSetToXLifeInsteadTest extends CardTestPlayerBase {

    /**
     * Tests direct damage
     */
    @Test
    public void testDirectDamage() {
        addCard(Constants.Zone.HAND, playerA, "Lava Axe");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 5);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elderscale Wurm");

        setLife(playerB, 8);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lava Axe", playerB);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // 5 damage dealt but it reduces life only to 7
        assertLife(playerB, 7);
    }

    /**
     * Tests doesn't work when less than 7 life total
     * Also tests that the ability doesn't affect the loss or payment of life.
     */
    @Test
    public void testLessLifeTotal() {
        addCard(Constants.Zone.HAND, playerA, "Lava Axe");
        addCard(Constants.Zone.HAND, playerA, "Bump in the Night");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elderscale Wurm");

        setLife(playerB, 8);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Bump in the Night", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lava Axe", playerB);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // 5 damage dealt but it reduces life only to 7
        assertLife(playerB, 0);
    }

    /**
     * Tests Worhsip:
     *   If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead
     */
    @Test
    public void testWorshipWithCreature() {
        addCard(Constants.Zone.HAND, playerA, "Volcanic Hammer");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Worship");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        setLife(playerB, 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Hammer", playerB);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 1);
    }

    /**
     * Tests Worhsip when there is no creature:
     *   If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead
     */
    @Test
    public void testWorshipWithoutCreature() {
        addCard(Constants.Zone.HAND, playerA, "Volcanic Hammer", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Worship");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        setLife(playerB, 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Hammer", "Elite Vanguard");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Hammer", playerB);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, -1);
    }

}

