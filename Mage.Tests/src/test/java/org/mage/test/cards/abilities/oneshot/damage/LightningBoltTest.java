package org.mage.test.cards.abilities.oneshot.damage;

import mage.Constants;
import mage.Constants.PhaseStep;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayrat
 */
public class LightningBoltTest extends CardTestPlayerBase {

    @Test
    public void testDamageOpponent() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testDamageSelf() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 17);
        assertLife(playerB, 20);
    }

    @Test
    public void testDamageSmallCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Sejiri Merfolk");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
    }

    @Test
    public void testDamageBigCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void testDamageBigCreatureTwice() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Craw Wurm", 0);
    }

}
