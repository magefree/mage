package org.mage.test.cards.single.an;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.AliFromCairo Ali from Cairo}
 * {2}{R}{R}
 * Creature — Human
 * Damage that would reduce your life total to less than 1 reduces it to 1 instead.
 *
 * @author BetaSteward
 */
public class AliFromCairoTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Ali from Cairo", 1);       
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 12);

        addCard(Zone.BATTLEFIELD, playerB, "Soulfire Grand Master", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 7);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);        

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 1);
        assertLife(playerB, 23);       
    }
}