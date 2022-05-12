package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BarteredCowTest extends CardTestPlayerBase {

    @Test
    public void testDiesTrigger() {
        setStrictChooseMode(true);

        // When Bartered Cow dies or when you discard it, create a Food token.        
        addCard(Zone.BATTLEFIELD, playerA, "Bartered Cow"); // Creature {3}{W} 3/3

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Bartered Cow");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Bartered Cow", 1);
        assertPermanentCount(playerA, "Food Token", 1);
    }

    @Test
    public void testDiscardTrigger() {
        setStrictChooseMode(true);

        // When Bartered Cow dies or when you discard it, create a Food token.        
        addCard(Zone.HAND, playerA, "Bartered Cow"); // Creature {3}{W} 3/3
        // Choose one —
        // • Target player discards a card.
        // • Target creature gets +2/-1 until end of turn.
        // • Target creature gains swampwalk until end of turn. (It can't be blocked as long as defending player controls a Swamp.)
        addCard(Zone.HAND, playerB, "Funeral Charm", 1); // Instant {B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Funeral Charm");
        setModeChoice(playerB, "1");
        addTarget(playerB, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Funeral Charm", 1);
        assertGraveyardCount(playerA, "Bartered Cow", 1);
        assertPermanentCount(playerA, "Food Token", 1);
    }

    @Test
    public void testDiscardTriggerWithTorturedExistence() {
        setStrictChooseMode(true);

        // When Bartered Cow dies or when you discard it, create a Food token.        
        addCard(Zone.HAND, playerA, "Bartered Cow"); // Creature {3}{W} 3/3

        // {B}, Discard a creature card: Return target creature card from your graveyard to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Tortured Existence", 1); // Instant {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}, Discard a creature card");
        setChoice(playerA, "Bartered Cow");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Bartered Cow", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Food Token", 1);
    }

}
