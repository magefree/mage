package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class PutToGraveyardTest extends CardTestPlayerBase {

    /**
     * Void Attendant	The usable "Prozessor-Effect" doesnt put the card back
     * into the graveyard from the exile.
     */
    @Test
    public void testExileToGraveyard() {
        // Devoid
        // {1}{G}, Put a card an opponent owns from exile into that player's graveyard: Put a 1/1 colorless Eldrazi Scion creature token onto the battlefield. It has "Sacrifice this creature: Add {C}."
        addCard(Zone.BATTLEFIELD, playerA, "Void Attendant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // Flash (You may cast this spell any time you could cast an instant.)
        // When Stasis Snare enters the battlefield, exile target creature an opponent controls until Stasis Snare leaves the battlefield. (That creature returns under its owner's control.)
        addCard(Zone.HAND, playerA, "Stasis Snare", 1); // {1}{W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stasis Snare");
        addTarget(playerA, "Silvercoat Lion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}{G},", TestPlayer.NO_TARGET, "");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Stasis Snare", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertExileCount(playerB, 0);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Eldrazi Scion Token", 1);

    }

    /**
     * Oracle of Dust does not seem to actually move cards from exile into the
     * opponent's graveyard, even though every other part of the ability works
     * just fine.
     */
    @Test
    public void testExileToGraveyard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Devoid
        // {2}, Put a card an opponent owns from exile into that player's graveyard: Draw a card, then discard a card.
        addCard(Zone.BATTLEFIELD, playerA, "Oracle of Dust", 1); // {4}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        // Exile target creature. Its controller gains life equal to its power.
        addCard(Zone.HAND, playerA, "Swords to Plowshares");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", "Silvercoat Lion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Swords to Plowshares", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, 2);

    }
}
