package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class DiesExiledTest extends CardTestPlayerBase {
    /*
     * Kumano's Blessing
     * Enchantment — Aura, 2R (3)
     * Flash
     * Enchant creature
     * If a creature dealt damage by enchanted creature this turn would die, exile it instead.
     *
    */
    
    // test that when creature damaged by enchanted creature dies it is exiled
    @Test
    public void testKumanosBlessing() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Kumano's Blessing");
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kumano's Blessing", "Prodigal Pyromancer");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage to", "Sejiri Merfolk");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertExileCount("Sejiri Merfolk", 1);
        
    }

    /*
     * Frostwielder
     * Creature — Human Shaman 1/2, 2RR (4)
     * {T}: Frostwielder deals 1 damage to 
     * If a creature dealt damage by Frostwielder this turn would die, exile it instead.
     *
    */
    
    // test that when creature damaged by Frostwielder dies it is exiled
    @Test
    public void testFrostwielder() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Frostwielder");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage to ", "Sejiri Merfolk");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertExileCount("Sejiri Merfolk", 1);
        
    }

    /*
     * Kumano, Master Yamabushi
     * Legendary Creature — Human Shaman 4/4, 3RR (5)
     * {1}{R}: Kumano, Master Yamabushi deals 1 damage to 
     * If a creature dealt damage by Kumano this turn would die, exile it instead.
     *
    */
    
    // test that when creature damaged by Kumano, Master Yamabushi dies it is exiled
    @Test
    public void testKumanoMasterYamabushi() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Kumano, Master Yamabushi");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{R}: {this} deals 1 damage to ", "Sejiri Merfolk");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertExileCount("Sejiri Merfolk", 1);
        
    }

    /*
     * Yamabushi's Flame
     * Instant, 2R (3)
     * Yamabushi's Flame deals 3 damage to  If a 
     * creature dealt damage this way would die this turn, exile it instead.
     *
    */
    
    // test that when creature damaged by Yamabushi's Flame dies it is exiled
    @Test
    public void testYamabushisFlame() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Yamabushi's Flame");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yamabushi's Flame", "Sejiri Merfolk");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertExileCount("Sejiri Merfolk", 1);
        
    }
    
    /*
     * Yamabushi's Storm
     * Sorcery, 1R (2)
     * Yamabushi's Storm deals 1 damage to each creature. If a creature dealt 
     * damage this way would die this turn, exile it instead.
     *
    */
    
    // test that when creatures damaged by Yamabushi's Storm die they are exiled
    @Test
    public void testYamabushisStorm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Yamabushi's Storm");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yamabushi's Storm");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertExileCount("Sejiri Merfolk", 2);
        
    }
    
}
