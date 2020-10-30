

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ChampionTest extends CardTestPlayerBase {

    /**
     * 702.71. Champion
     * 702.71a Champion represents two triggered abilities. “Champion an [object]” means “When this
     * permanent enters the battlefield, sacrifice it unless you exile another [object] you control” and
     * “When this permanent leaves the battlefield, return the exiled card to the battlefield under its
     * owner's control.”
     * 
     * 702.71b The two abilities represented by champion are linked. See rule 607, “Linked Abilities.”
     * 
     * 702.71c A permanent is “championed” by another permanent if the latter exiles the former as the
     * direct result of a champion ability.
     * 
     */

    /** 
     * Lightning Crafter
     * Creature — Goblin Shaman 3/3, 3R (4)
     * Champion a Goblin or Shaman (When this enters the battlefield, sacrifice 
     * it unless you exile another Goblin or Shaman you control. When this 
     * leaves the battlefield, that card returns to the battlefield.)
     * {T}: Lightning Crafter deals 3 damage to any target.
     *
     */

    @Test
    public void testChampionCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Crafter");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Crafter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Lightning Crafter", 1);
        assertExileCount("Goblin Roughrider", 1);

    }

    @Test
    public void testExiledCreatureReturns() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Crafter");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Crafter");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 3 damage to ", "Lightning Crafter");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Lightning Crafter", 0);
        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertExileCount("Goblin Roughrider", 0);
        assertGraveyardCount(playerA, "Lightning Crafter", 1);

    }

    /**
     * Mistblind clique land tap ability does not work
     */
    
    @Test
    public void testMistbindClique() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Zephyr Sprite");
        // Flash, Flying
        // Champion a Faerie
        // When a Faerie is championed with Mistbind Clique, tap all lands target player controls.
        addCard(Zone.HAND, playerA, "Mistbind Clique");
        
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addTarget(playerA, playerB);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mistbind Clique");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mistbind Clique", 1);
        assertExileCount("Zephyr Sprite", 1);
        
        assertTapped("Plains", true);

    }

    
}
