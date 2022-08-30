package org.mage.test.cards.single.roe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class WorldAtWarTest extends CardTestPlayerBase {

    /**
     * Tests creatures attack twice
     */
    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.HAND, playerA, "World at War");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Warclamp Mastiff");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "World at War");

        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Warclamp Mastiff");
        attack(1, playerA, "Warclamp Mastiff");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 14);
        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA);
        Assert.assertTrue(eliteVanguard.isTapped());
    }

    /**
     * Tests creatures attack twice on each turn (Rebound)
     */
    @Test
    public void testCardWithRebound() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Enchantment - Creatures you control have haste. (They can attack and as soon as they come under your control.)
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        // After the first postcombat main phase this turn, there's an additional combat phase followed by
        // an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn.
        // Rebound (If you cast this spell from your hand, exile it as it resolves. At the beginning of your
        // next upkeep, you may cast this card from exile without paying its mana cost.)
        addCard(Zone.HAND, playerA, "World at War");
        // Creature - Human Soldier 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        // Creature - Hound 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Warclamp Mastiff");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "World at War");

        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Warclamp Mastiff");
        attack(1, playerA, "Warclamp Mastiff");

        attack(3, playerA, "Elite Vanguard");
        attack(3, playerA, "Elite Vanguard");
        attack(3, playerA, "Warclamp Mastiff");
        attack(3, playerA, "Warclamp Mastiff");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 8);
        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA);
        Assert.assertTrue(eliteVanguard.isTapped());
    }

    /**
     * Tests creatures attack three times
     */
    @Test
    public void testDoubleCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.HAND, playerA, "World at War", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Warclamp Mastiff");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "World at War", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "World at War");

        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Warclamp Mastiff");
        attack(1, playerA, "Warclamp Mastiff");
        attack(1, playerA, "Warclamp Mastiff");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 11);
        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA);
        Assert.assertTrue(eliteVanguard.isTapped());
    }
}
