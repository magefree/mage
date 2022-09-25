/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.87. Rebound
 * 702.87a Rebound appears on some instants and sorceries. It represents a static ability that functions while
 * the spell is on the stack and may create a delayed triggered ability. "Rebound" means "If this spell was cast
 * from your hand, instead of putting it into your graveyard as it resolves, exile it and, at the beginning of
 * your next upkeep, you may cast this card from exile without paying its mana cost."
 * 702.87b Casting a card without paying its mana cost as the result of a rebound ability follows the rules for
 * paying alternative costs in rules 601.2b and 601.2e–g.
 * 702.87c Multiple instances of rebound on the same spell are redundant.
 *
 * @author jeff
 */
public class ReboundTest extends CardTestPlayerBase {

    /**
     * Test that the spell with rebound is moved to exile if
     * the spell resolves
     */

    @Test
    public void testCastFromHandMovedToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        addCard(Zone.HAND, playerA, "Distortion Strike");

        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //check exile and graveyard        
        assertExileCount("Distortion Strike", 1);
        assertGraveyardCount(playerA, 0);
    }

    /**
     * Test that the spell with rebound can be cast again
     * on the beginning of the next upkeep without paying mana costs
     */

    @Test
    public void testRecastFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        addCard(Zone.HAND, playerA, "Distortion Strike");

        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        //check exile and graveyard        
        assertPowerToughness(playerA, "Memnite", 2, 1);
        assertExileCount("Distortion Strike", 0);
        assertGraveyardCount(playerA, "Distortion Strike", 1);
    }

    /**
     * Check that a countered spell with rebound
     * is not cast again
     */

    @Test
    public void testDontRecastAfterCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        addCard(Zone.HAND, playerA, "Distortion Strike");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Distortion Strike");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        //check exile and graveyard        
        assertGraveyardCount(playerB, "Counterspell", 1);
        assertGraveyardCount(playerA, "Distortion Strike", 1);

        assertPowerToughness(playerA, "Memnite", 1, 1);
        assertExileCount("Distortion Strike", 0);
    }


    /**
     * Check that a fizzled spell with rebound
     * is not cast again on the next controllers upkeep
     * <p>
     * rules:
     * If a spell with rebound that you cast from your hand doesn’t resolve for any reason (due being countered by a
     * spell like Cancel, or because all of its targets are illegal), rebound has no effect. The spell is simply put
     * into your graveyard. You won’t get to cast it again next turn.
     * (2010-06-15)
     */

    @Test
    public void testDontRecastAfterFizzling() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Target creature gets +1/+0 until end of turn and is unblockable this turn.
        // Rebound (If you cast this spell from your hand, exile it as it resolves. At the beginning of your next upkeep,
        //    you may cast this card from exile without paying its mana cost.)
        addCard(Zone.HAND, playerA, "Distortion Strike");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distortion Strike", "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Memnite", "Distortion Strike");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //check exile and graveyard        
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Distortion Strike", 1); // rebound is not activated in fizzled spell
    }
}
