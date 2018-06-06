package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DreadWandererTest extends CardTestPlayerBase {

    /**
     * That the +1/+1 counters are added to Living Lore before state based
     * actions take place
     */
    @Test
    public void testTappedFromHand() {

        // Dread Wanderer enters the battlefield tapped.
        // {2}{B}: Return Dread Wanderer from your graveyard to the battlefield.
        // Activate this ability only any time you could cast a sorcery and only if you have one or fewer cards in hand.
        addCard(Zone.HAND, playerA, "Dread Wanderer"); // Creature {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dread Wanderer");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dread Wanderer", 1);
        assertTapped("Dread Wanderer", true);
    }

    @Test
    public void testTappedFromGraveyard() {

        // Dread Wanderer enters the battlefield tapped.
        // {2}{B}: Return Dread Wanderer from your graveyard to the battlefield.
        // Activate this ability only any time you could cast a sorcery and only if you have one or fewer cards in hand.
        addCard(Zone.GRAVEYARD, playerA, "Dread Wanderer"); // Creature {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}: Return ");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dread Wanderer", 1);
        assertTapped("Dread Wanderer", true);
    }

}
