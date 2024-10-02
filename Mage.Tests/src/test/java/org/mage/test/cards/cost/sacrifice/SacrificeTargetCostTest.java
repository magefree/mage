package org.mage.test.cards.cost.sacrifice;

import mage.abilities.common.LicidAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jimga150
 */
public class SacrificeTargetCostTest extends CardTestPlayerBase {

    // Tests a variety of use cases with SacrificeTargetCost, making sure the right player pays the cost

    @Test
    public void testSimpleCost() {
        // All Rats have fear.
        // {T}, Sacrifice a Rat: Create X 1/1 black Rat creature tokens, where X is the number of Rats you control.
        addCard(Zone.BATTLEFIELD, playerA, "Marrow-Gnawer");
        addCard(Zone.BATTLEFIELD, playerA, "Karumonix, the Rat King");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        setChoice(playerA, "Karumonix, the Rat King"); // Target to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Marrow-Gnawer", 1);
        assertPermanentCount(playerA, "Rat Token", 1);
        assertGraveyardCount(playerA, "Karumonix, the Rat King", 1);
    }

    @Test
    public void testSimpleCostOtherPlayerActivate() {
        // {1}, Sacrifice a land: Draw a card. Any player may activate this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Excavation");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        // Player B activates Player A's Excavate ability
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}");
        setChoice(playerB, "Forest"); // Target to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerB, 1);
        assertGraveyardCount(playerB, "Forest", 1);
    }

    @Test
    public void testDoUnlessSacrificeTrigger() {
        // When Demanding Dragon enters, it deals 5 damage to target opponent unless that player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Demanding Dragon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demanding Dragon");
        addTarget(playerA, playerB);
        setChoice(playerB, "No"); // Sac a creature?

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Demanding Dragon");
        addTarget(playerA, playerB);
        setChoice(playerB, "Yes"); // Sac a creature?
        setChoice(playerB, "Memnite"); // Sac Memnite

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Memnite", 1);
    }

    @Test
    public void testDoUnlessSacrificeActivated() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.HAND, playerA, "Tergrid, God of Fright // Tergrid's Lantern");
        addCard(Zone.HAND, playerB, "Memnarch");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tergrid's Lantern", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player");
        addTarget(playerA, playerB);
        setChoice(playerB, "No"); // Sac or discard to avoid life loss?

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player");
        addTarget(playerA, playerB);
        setChoice(playerB, "Yes"); // Sac or discard to avoid life loss?
        setChoice(playerB, "Yes"); // Yes - Sacrifice, No - Discard
        setChoice(playerB, "Memnite"); // To sacrifice

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, currentGame.getStartingLife() - 3);
        assertPermanentCount(playerB, "Memnite", 0);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertHandCount(playerB, "Memnarch", 1);
    }

    /**
     * Use special action that has opponent sac a permanent
     */
    @Test
    public void SpecialActionTest() {
        // Enchanted creature can't attack or block, and its activated abilities can't be activated.
        // That creature's controller may sacrifice a permanent for that player to ignore this effect until end of turn.
        addCard(Zone.HAND, playerA, "Volrath's Curse");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Memnarch");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volrath's Curse");
        addTarget(playerA, "Memnarch");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice a ", "Volrath's Curse");
        setChoice(playerB, "Memnite");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Memnite", 0);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertPermanentCount(playerB, "Memnarch", 1);
    }

}
