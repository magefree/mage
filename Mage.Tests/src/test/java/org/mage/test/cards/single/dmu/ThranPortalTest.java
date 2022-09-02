package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.lang.annotation.Target;

/**
 * {@link mage.cards.t.ThranPortal Thran Portal}
 * Land Gate
 * Thran Portal enters the battlefield tapped unless you control two or fewer other lands.
 * As Thran Portal enters the battlefield, choose a basic land type.
 * Thran Portal is the chosen type in addition to its other types.
 * Mana abilities of Thran Portal cost an additional 1 life to activate.
 *
 * @author Alex-Vasile
 */
public class ThranPortalTest extends CardTestPlayerBase {

    private static final String thranPortal = "Thran Portal";

    /**
     * Test that it comes in untapped tapping for mana deals damage.
     * Also tests that it comes in untapped if you control 2 of fewer lands.
     */
    @Test
    public void dealsDamage() {
        String lightningBolt = "Lightning Bolt";
        addCard(Zone.HAND, playerA, thranPortal);
        addCard(Zone.HAND, playerA, lightningBolt);

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, thranPortal);
        setChoice(playerA, "Thran");
        setChoice(playerA, "Mountain");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1); // 1 life for tapping it
        assertLife(playerB, 20 - 3); // 3 life from lightning bolt
    }


    /**
     * Test that it gets properly seen as the land type that was chosen.
     * Also tests that it comes in tapped when you control 3 or more lands.
     */
    @Test
    public void seenAsChoice() {
        // Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains,
        // you may have Valakut, the Molten Pinnacle deal 3 damage to any target.
        String valakut = "Valakut, the Molten Pinnacle";

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, valakut);
        addCard(Zone.HAND, playerA, thranPortal);

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, thranPortal);
        setChoice(playerA, "Thran");
        setChoice(playerA, "Mountain");
        setChoice(playerA, "Yes");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertTapped(thranPortal, true);
        assertLife(playerB, 20 - 3);
    }

}
