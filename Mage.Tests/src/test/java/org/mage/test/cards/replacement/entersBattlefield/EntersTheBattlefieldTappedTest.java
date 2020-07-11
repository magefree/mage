package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EntersTheBattlefieldTappedTest extends CardTestPlayerBase {

    /**
     * Creatures that enter the battlefield tapped, like Dread Wanderer, if you
     * bring them back from graveyard to the battlefield they enter untapped!!
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

    @Test
    public void testScryLandEntersTapped() {
        // Temple of Enlightenment enters the battlefield tapped.
        // When Temple of Enlightenment enters the battlefield, scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
        addCard(Zone.HAND, playerA, "Temple of Enlightenment"); // Land

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Enlightenment");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Temple of Enlightenment", 1);
        assertTapped("Temple of Enlightenment", true);
    }

}
