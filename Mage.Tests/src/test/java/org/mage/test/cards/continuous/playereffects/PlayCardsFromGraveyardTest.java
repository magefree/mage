
package org.mage.test.cards.continuous.playereffects;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PlayCardsFromGraveyardTest extends CardTestPlayerBase {

    @Test
    public void testYawgmothsWill() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // Until end of turn, you may play cards from your graveyard.
        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        addCard(Zone.HAND, playerA, "Yawgmoth's Will"); // {2}{B}
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        // You gain 3 life.
        // Draw a card.
        addCard(Zone.GRAVEYARD, playerA, "Reviving Dose"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yawgmoth's Will", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reviving Dose", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 10/4/2004 	It will exile itself since it goes to the graveyard after its effect starts.
        assertExileCount("Yawgmoth's Will", 1);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertExileCount("Reviving Dose", 1);
        assertExileCount("Silvercoat Lion", 1);

        assertHandCount(playerA, 1);

        assertLife(playerA, 23);
        assertLife(playerB, 20);
    }

    /**
     * The null casting cost cards(Ancestral Visions, Lotus Bloom) can be cast
     * from graveyard with Yawgmoth's Will
     */
    @Test
    public void testYawgmothsWillNoCastingCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Until end of turn, you may play cards from your graveyard.
        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        addCard(Zone.HAND, playerA, "Yawgmoth's Will"); // {2}{B}
        // Suspend 4-{U}
        // Target player draws three cards.
        addCard(Zone.GRAVEYARD, playerA, "Ancestral Vision");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yawgmoth's Will");
        // You may not suspend it from graveyard
        checkPlayableAbility("Can't suspend", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend", false);
        // It may not be possible to cast it from graveyard
        checkPlayableAbility("Can't cast 0 cost cards", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ancestral", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 10/4/2004 	It will exile itself since it goes to the graveyard after its effect starts.
        assertExileCount("Yawgmoth's Will", 1);

        assertHandCount(playerA, 0);
        assertExileCount("Ancestral Vision", 0);

        assertGraveyardCount(playerA, "Ancestral Vision", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
