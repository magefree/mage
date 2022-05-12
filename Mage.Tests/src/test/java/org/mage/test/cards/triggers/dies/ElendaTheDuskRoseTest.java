package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 *
 * @author LevelX2
 */
public class ElendaTheDuskRoseTest extends CardTestPlayerBase {

    @Test
    public void testAddCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        // Lifelink
        // Whenever another creature dies, put a +1/+1 counter on Elenda, The Dusk Rose.
        // When Elenda dies, create X 1/1 white Vampire creature tokens with lifelink, where X is Elenda's power.
        addCard(Zone.HAND, playerA, "Elenda, the Dusk Rose", 1); // {2}{W}{B}   1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elenda, the Dusk Rose");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elenda, the Dusk Rose", 1);

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertPowerToughness(playerA, "Elenda, the Dusk Rose", 2, 2);
    }

    @Test
    public void testCreateVampireTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        // Lifelink
        // Whenever another creature dies, put a +1/+1 counter on Elenda, The Dusk Rose.
        // When Elenda dies, create X 1/1 white Vampire creature tokens with lifelink, where X is Elenda's power.
        addCard(Zone.HAND, playerA, "Elenda, the Dusk Rose", 1); // {2}{W}{B}   1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elenda, the Dusk Rose");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Elenda, the Dusk Rose");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elenda, the Dusk Rose", 0);

        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Elenda, the Dusk Rose", 1);

        assertPermanentCount(playerA, "Vampire Token", 2);
        assertPowerToughness(playerA, "Vampire Token", 1, 1, Filter.ComparisonScope.All);
    }

    @Test
    public void testKillAndReanimate() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); /// 2/2
        // Whenever a creature is put into your graveyard from the battlefield, you may sacrifice Angelic Renewal. If you do, return that card to the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Angelic Renewal", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        // Lifelink
        // Whenever another creature dies, put a +1/+1 counter on Elenda, The Dusk Rose.
        // When Elenda dies, create X 1/1 white Vampire creature tokens with lifelink, where X is Elenda's power.
        addCard(Zone.HAND, playerA, "Elenda, the Dusk Rose", 1); // {2}{W}{B}   1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elenda, the Dusk Rose");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Elenda, the Dusk Rose");
        setChoice(playerA, false); // use Angelic Renewal on Silvercoat Lion
        setChoice(playerA, true); // use Angelic Renewal on Elenda, the Dusk Rose

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elenda, the Dusk Rose", 1);
        assertPowerToughness(playerA, "Elenda, the Dusk Rose", 1, 1);

        assertGraveyardCount(playerB, "Lightning Bolt", 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Elenda, the Dusk Rose", 0);

        assertPermanentCount(playerA, "Vampire Token", 2);
        assertPowerToughness(playerA, "Vampire Token", 1, 1, Filter.ComparisonScope.All);
    }

    @Test
    public void testKillMultipleAndReanimate() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); /// 2/2
        // Whenever a creature is put into your graveyard from the battlefield, you may sacrifice Angelic Renewal. If you do, return that card to the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Angelic Renewal", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        // Sweltering Suns deals 3 damage to each creature.
        // Cycling {3} ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerB, "Sweltering Suns", 1); // Sorcery {1}{R}{R}

        // Lifelink
        // Whenever another creature dies, put a +1/+1 counter on Elenda, The Dusk Rose.
        // When Elenda dies, create X 1/1 white Vampire creature tokens with lifelink, where X is Elenda's power.
        addCard(Zone.HAND, playerA, "Elenda, the Dusk Rose", 1); // {2}{W}{B}   1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elenda, the Dusk Rose");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sweltering Suns");

        setChoice(playerA, true); // use Angelic Renewal on Elenda, the Dusk Rose
        setChoice(playerA, false); // use Angelic Renewal on Silvercoat Lion

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Angelic Renewal", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Elenda, the Dusk Rose", 1);
        assertPowerToughness(playerA, "Elenda, the Dusk Rose", 1, 1);

        assertGraveyardCount(playerB, "Sweltering Suns", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Elenda, the Dusk Rose", 0);

        assertPermanentCount(playerA, "Vampire Token", 1);
        assertPowerToughness(playerA, "Vampire Token", 1, 1, Filter.ComparisonScope.All);
    }

}
