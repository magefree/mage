
package org.mage.test.cards.single.fut;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DaybreakCoronetTest extends CardTestPlayerBase {

    // Daybreak Coronet {W}{W}
    // Enchantment — Aura
    // Enchant creature with another Aura attached to it
    // Enchanted creature gets +3/+3 and has first strike, vigilance, and lifelink. (Damage dealt by the creature also causes its controller to gain that much life.)


    @Test
    public void testCantEnchantTargetWithoutAura() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Daybreak Coronet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daybreak Coronet", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Daybreak Coronet", 0);

    }

    @Test
    public void testCanBeCastIfEnchantedByAura() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Holy Strength");
        addCard(Zone.HAND, playerA, "Daybreak Coronet");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daybreak Coronet", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Daybreak Coronet", 1);

    }
    /*
    If Daybreak Coronet is already attached to a permanent and
    the other aura is destroyed. The target for Daybreak Coronet
    gets illegal and the Daybreak Coronet has to go to graveyard.
    */
    @Test
    public void testTargetGetsIllegal() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Holy Strength");
        addCard(Zone.HAND, playerA, "Daybreak Coronet");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.HAND, playerB, "Demystify");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daybreak Coronet", "Silvercoat Lion");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Demystify", "Holy Strength");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Holy Strength", 1);
        assertGraveyardCount(playerB, "Demystify", 1);
        assertPermanentCount(playerA, "Daybreak Coronet", 0);
        assertGraveyardCount(playerA, "Daybreak Coronet", 1);

    }
}
