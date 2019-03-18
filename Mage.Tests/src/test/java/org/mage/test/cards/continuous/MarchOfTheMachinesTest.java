

package org.mage.test.cards.continuous;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class MarchOfTheMachinesTest extends CardTestPlayerBase {
    
    /**
     * March of the Machines
     * Enchantment, 3U (4)
     * Each noncreature artifact is an artifact creature with power and toughness 
     * each equal to its converted mana cost. (Equipment that's a creature can't 
     * equip a creature.)
     * 
     */
        
    /**
     * Abzan Banner
     * Artifact, 3 (3)
     * {T}: Add {W}, {B}, or {G}.
     * {W}{B}{G}, {T}, Sacrifice Abzan Banner: Draw a card.
     */

    @Test
    public void testNonCreatureArtifactsBecomeCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner");
        addCard(Zone.BATTLEFIELD, playerA, "Alloy Myr");
        addCard(Zone.HAND, playerA, "March of the Machines");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Alloy Myr", 2, 2);
        assertPermanentCount(playerA, "Abzan Banner", 1);
        assertPowerToughness(playerA, "Abzan Banner", 3, 3);
        assertType("Abzan Banner", CardType.CREATURE, true);
    }

    @Test
    public void testArtifactsRevertBack() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner");
        addCard(Zone.HAND, playerA, "March of the Machines");
        
        addCard(Zone.HAND, playerB, "Disenchant");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines");    
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "March of the Machines");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Abzan Banner", 1);
        assertType("Abzan Banner", CardType.CREATURE, false);
    }

    @Test
    public void testEquipmentDetaches() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Avacyn's Collar");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.HAND, playerA, "March of the Machines");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Ornithopter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "March of the Machines");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Ornithopter", 1);
        assertPowerToughness(playerA, "Ornithopter", 0, 2);
        assertPermanentCount(playerA, "Avacyn's Collar", 1);
        assertPowerToughness(playerA, "Avacyn's Collar", 1, 1);
        assertType("Avacyn's Collar", CardType.CREATURE, true);
    }

    @Test
    public void testZeroCostIsDestroyed() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Accorder's Shield");
        addCard(Zone.HAND, playerA, "March of the Machines");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertGraveyardCount(playerA, "Accorder's Shield", 1);
    }

    @Test
    public void testLiquimetalCoatingLandIsDestroyed() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Liquimetal Coating");
        addCard(Zone.HAND, playerA, "March of the Machines");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target permanent", "Forest");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Forest", 0);
        assertGraveyardCount(playerA, "Forest", 1);
    }

}
