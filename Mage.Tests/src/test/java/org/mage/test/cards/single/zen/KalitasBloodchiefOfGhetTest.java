package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KalitasBloodchiefOfGhetTest extends CardTestPlayerBase {

    @Test
    public void testTokenCreatedOnlyIfTargetDies() {
        // {B}{B}{B}, {T}: Destroy target creature. If that creature dies this way, create a black Vampire creature token.
        // Its power is equal to that creature's power and its toughness is equal to that creature's toughness.
        addCard(Zone.BATTLEFIELD, playerA, "Kalitas, Bloodchief of Ghet", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // When Rest in Peace enters the battlefield, exile all cards from all graveyards.
        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace", 1); // Enchantment

        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre", 1); // Creature - Ogre 2/2

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}{B}{B}", "Gray Ogre");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Vampire Token", 0);
    }
}
