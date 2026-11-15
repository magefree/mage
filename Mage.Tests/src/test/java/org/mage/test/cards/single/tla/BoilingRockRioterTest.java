package org.mage.test.cards.single.tla;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.b.BoilingRockRioter Boiling Rock Rioter} {2}{B}
 * Creature — Human Rogue Ally 3/3
 * Firebending 1
 * Tap an untapped Ally you control: Exile target card from a graveyard.
 * Whenever this creature attacks, you may cast an Ally spell from among cards you own exiled with this creature.
 */
public class BoilingRockRioterTest extends CardTestPlayerBase {

    private static final String rioter = "Boiling Rock Rioter";
    private static final String protectors = "Earth Kingdom Protectors"; // Ally to tap for the cost
    private static final String enthusiasts = "Avatar Enthusiasts"; // {2}{W} Ally, owned
    private static final String bears = "Grizzly Bears"; // owned, not an Ally
    private static final String gliderKids = "Glider Kids"; // Ally, not owned

    @Test
    public void test_CastsOwnedAllyPayingItsCost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, rioter);
        addCard(Zone.BATTLEFIELD, playerA, protectors, 3);
        // exactly {2}{W} with firebending's {R}, so the spell can only be cast by paying for it
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.GRAVEYARD, playerA, enthusiasts);
        addCard(Zone.GRAVEYARD, playerA, bears);
        addCard(Zone.GRAVEYARD, playerB, gliderKids);

        for (String card : new String[]{enthusiasts, bears, gliderKids}) {
            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tap an untapped Ally", card);
            setChoice(playerA, protectors);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        }

        attack(1, playerA, rioter);
        setChoice(playerA, "Whenever"); // order the attack triggers: firebending resolves first
        setChoice(playerA, true); // cast Avatar Enthusiasts

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, enthusiasts, 1);
        assertTappedCount("Plains", true, 2);
        assertExileCount(playerA, bears, 1);
        assertExileCount(playerB, gliderKids, 1);
        assertLife(playerB, 20 - 3);
    }
}
