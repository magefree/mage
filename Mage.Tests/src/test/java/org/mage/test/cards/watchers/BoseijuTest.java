package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class BoseijuTest extends CardTestPlayerBase {
    /*
     * Boseiju, Who Shelters All
     * Legendary Land
     * Boseiju, Who Shelters All enters the battlefield tapped.
     * {T}, Pay 2 life: Add {C}. If that mana is spent on an
     * instant or sorcery spell, that spell can't be countered.
     *
     */

    // test that instants and soceries can't be countered when Boseiju mana is used
    @Test
    public void testCantCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Boseiju, Who Shelters All");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Draw three cards.
        addCard(Zone.HAND, playerA, "Brilliant Plan"); // {4}{U}

        addCard(Zone.HAND, playerB, "Counterspell"); // {U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brilliant Plan");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Brilliant Plan");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        this.assertHandCount(playerA, 3);
        this.assertGraveyardCount(playerB, "Counterspell", 1);

    }

    // test that instants and soceries can be countered when Boseiju mana is not used
    @Test
    public void testCanCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Boseiju, Who Shelters All");
        addCard(Zone.HAND, playerA, "Mental Note");
        addCard(Zone.HAND, playerA, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mental Note");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Mental Note");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        this.assertHandCount(playerA, 0);
        this.assertGraveyardCount(playerA, 2);

    }

}
