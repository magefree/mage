package org.mage.test.serverside.cards.abilities;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class ProtectionFromColorTest extends CardTestPlayerBase {

    @Test
    public void testAgainstAbilityInTheStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Royal Assassin");

        // tapped White Knight with Protection from Black
        addCard(Zone.BATTLEFIELD, playerB, "White Knight", 1, true);

        checkPlayableAbility("test", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testAgainstAbilityInTheStackNoProtection() {
        addCard(Zone.BATTLEFIELD, playerA, "Royal Assassin");

        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Destroy target tapped creature.", "Runeclaw Bear");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // One should have been destroyed by Royal Assassin
        assertPermanentCount(playerB, "Runeclaw Bear", 0);
    }

    @Test
    public void testProtectionChosenColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        // Defender
        // As Order of the Stars enters the battlefield, choose a color.
        // Order of the Stars has protection from the chosen color.
        addCard(Zone.HAND, playerA, "Order of the Stars"); // 0/1  {W}
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate"); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // Seismic Shudder deals 1 damage to each creature without flying.
        addCard(Zone.HAND, playerB, "Seismic Shudder", 2); // {1}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Order of the Stars");
        setChoice(playerA, "White");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Order of the Stars", "Silvercoat Lion");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Seismic Shudder");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Order of the Stars");
        setChoice(playerA, "Red");

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Seismic Shudder");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Seismic Shudder", 2);
        assertGraveyardCount(playerA, "Reanimate", 1);
        assertPermanentCount(playerA, "Order of the Stars", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 19);
        assertLife(playerB, 20);

    }

}
