
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SidisiBroodTyrantTest extends CardTestPlayerBase {

    /**
     * Tests that if Sidisi, Brood Tyrant leaves the battlefield before it's
     * first ability resolves, there will be no Zombie token added to the
     * battlefield
     *
     */
    @Test
    public void testDiesTriggeredAbility() {
        // {1}{B}{G}{U}
        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard
        // Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Sidisi, Brood Tyrant");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 2);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sidisi, Brood Tyrant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Sidisi, Brood Tyrant", "Whenever {this} enters the battlefield");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Sidisi, Brood Tyrant", 1);

        assertGraveyardCount(playerA, 4);
        assertPermanentCount(playerA, "Zombie Token", 0);

    }

    /**
     * Another potential bug would be related to Sidisi, Brood Tyrant 's second
     * trigger. If there is one in play and I play a Satyr Wayfinder or a Gather
     * the Pack, then mill some creatures, Sidisi should trigger and make a
     * Zombie token, right? This doesn't seem to work currently.
     *
     */
    @Test
    public void testDiesTriggeredAbilityNormal() {
        // {1}{B}{G}{U}
        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard
        // Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Sidisi, Brood Tyrant"); // 2/2  {1}{B}{G}{U}

        // When Satyr Wayfinder enters the battlefield, reveal the top four cards of your library. You may put a land card from among them into your hand. Put the rest into your graveyard.
        addCard(Zone.HAND, playerA, "Satyr Wayfinder"); // 1/1  {1}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sidisi, Brood Tyrant");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Satyr Wayfinder");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sidisi, Brood Tyrant", 1);
        assertPermanentCount(playerA, "Satyr Wayfinder", 1);
        assertHandCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 6);
        assertPermanentCount(playerA, "Zombie Token", 2);

    }

    /*
     Sidisi's zombie trigger still resolves even with Anafenza on the battle field.

     Steps:
     Cast Anafenza
     Pass
     Cast Sidisi, mill creature.
     Zombie is still created.

     Due to replacement effect of exiling creatures, the second phase of sidisi is null with Anafenza out.

     */
    @Test
    public void testWithAnafenza() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        // {1}{B}{G}{U}
        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard
        // Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Sidisi, Brood Tyrant"); // 2/2  {1}{B}{G}{U}
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);
        skipInitShuffling();

        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Anafenza, the Foremost");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sidisi, Brood Tyrant");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sidisi, Brood Tyrant", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 0);
        assertExileCount("Silvercoat Lion", 2);
        assertPermanentCount(playerA, "Zombie Token", 0);

    }

}
