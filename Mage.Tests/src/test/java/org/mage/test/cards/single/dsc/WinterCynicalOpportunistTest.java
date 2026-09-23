package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class WinterCynicalOpportunistTest extends CardTestPlayerBase {

    @Test
    public void testAttackMillsAndEndStepReanimatesWithFinalityCounter() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        // Graveyard has Creature, Artifact, Enchantment, Land = 4 types
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");   // Creature (2/2)
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");        // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Rancor");          // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Forest");          // Land

        attack(1, playerA, "Winter, Cynical Opportunist", playerB);

        setChoice(playerA, true); // Yes, exile cards with 4+ card types
        setChoice(playerA, "Grizzly Bears^Sol Ring^Rancor^Forest");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // Finish selecting cards to exile
        setChoice(playerA, "Grizzly Bears"); // Choose permanent card to put onto battlefield

        checkPermanentCount("Grizzly Bears on battlefield at upkeep", 2, PhaseStep.UPKEEP, playerA, "Grizzly Bears", 1);

        // Kill Grizzly Bears on turn 2: finality counter should exile it instead of putting it into graveyard
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // Attacked -> milled 3 Swamps
        assertGraveyardCount(playerA, "Swamp", 3);
        // Other 3 exiled cards remain in exile, plus Grizzly Bears exiled when it died with a finality counter
        assertExileCount(playerA, "Sol Ring", 1);
        assertExileCount(playerA, "Rancor", 1);
        assertExileCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        assertExileCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testFewerThanFourCardTypesInGraveyardDoesNotReanimate() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");
        // Only 3 card types in graveyard (Creature, Artifact, Land)
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");
        addCard(Zone.GRAVEYARD, playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
