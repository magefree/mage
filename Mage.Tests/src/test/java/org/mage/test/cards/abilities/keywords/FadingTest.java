

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class FadingTest extends CardTestPlayerBase {

    /**
     * 702.31. Fading
     * 702.31a Fading is a keyword that represents two abilities. “Fading N” means “This permanent
     * enters the battlefield with N fade counters on it” and “At the beginning of your upkeep, remove
     * a fade counter from this permanent. If you can't, sacrifice the permanent.”
     */

    /** 
     * 	Blastoderm 
     * 	Creature — Beast 5/5, 2GG (4)
     * 	Shroud (This creature can't be the target of spells or abilities.)
     * 	Fading 3 (This creature enters the battlefield with three fade counters 
     *  on it. At the beginning of your upkeep, remove a fade counter from it. 
     *  If you can't, sacrifice it.)
     *
     */

    @Test
    public void testFading() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Blastoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastoderm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Blastoderm", 1);
        this.assertCounterCount("Blastoderm", CounterType.FADE, 3);

    }

    @Test
    public void testFades() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Blastoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastoderm");

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Blastoderm", 1);
        this.assertCounterCount("Blastoderm", CounterType.FADE, 1);

    }

    @Test
    public void testFadesAway() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Blastoderm");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastoderm");

        setStopAt(9, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Blastoderm", 0);
        assertGraveyardCount(playerA, "Blastoderm", 1);

    }
    
}
