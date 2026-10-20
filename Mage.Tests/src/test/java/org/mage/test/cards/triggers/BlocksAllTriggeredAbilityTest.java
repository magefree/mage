package org.mage.test.cards.triggers;

import mage.abilities.common.BlocksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.PhaseStep;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class BlocksAllTriggeredAbilityTest extends CardTestPlayerBase {

    @Test
    public void test_TargetPlayer_BattleStrain() {
        // Whenever a creature blocks, Battle Strain deals 1 damage to that creature's controller.
        addCard(Zone.BATTLEFIELD, playerA, "Battle Strain");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Silvercoat Lion", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_TargetPlayer_CarnageGladiator() {
        // Whenever a creature blocks, that creature's controller loses 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Carnage Gladiator");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(1, playerA, "Carnage Gladiator");
        block(1, playerB, "Silvercoat Lion", "Carnage Gladiator");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_TargetPermanent() {
        addCustomCardWithAbility("tester", playerB, new BlocksAllTriggeredAbility(
                new BoostTargetEffect(1, 1), false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PERMANENT
        ));
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Silvercoat Lion", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerB, "Silvercoat Lion", 3, 3);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_Filter_NobleStand() {
        // Whenever a creature you control blocks, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Noble Stand");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Noble Stand");
        // Palace Guard can block any number of creatures.
        addCard(Zone.BATTLEFIELD, playerB, "Palace Guard");

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Palace Guard", "Grizzly Bears");

        attack(2, playerB, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 + 2);
    }

    @Test
    public void test_BlocksMultipleCreatures_TriggersOnce() {
        // Whenever a creature you control blocks, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Noble Stand");
        // Palace Guard can block any number of creatures.
        addCard(Zone.BATTLEFIELD, playerB, "Palace Guard");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        attack(1, playerA, "Grizzly Bears");
        attack(1, playerA, "Llanowar Elves");
        block(1, playerB, "Palace Guard", "Grizzly Bears");
        block(1, playerB, "Palace Guard", "Llanowar Elves");
        setChoiceAmount(playerB, 1, 0); // Palace Guard's combat damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 + 2);
    }

    @Test
    public void test_Filter_PerimeterCaptain() {
        // Whenever a creature you control with defender blocks, you may gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Perimeter Captain");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        attack(1, playerA, "Grizzly Bears");
        attack(1, playerA, "Llanowar Elves");
        block(1, playerB, "Perimeter Captain", "Grizzly Bears");
        block(1, playerB, "Silvercoat Lion", "Llanowar Elves");
        setChoice(playerB, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 + 2);
    }

    @Test
    public void test_FilterAndTargetPlayer_TetsukoUmezawaPursuer() {
        // Whenever a creature an opponent controls with power or toughness 1 or less blocks,
        // Tetsuko Umezawa deals 1 damage to that creature's controller.
        addCard(Zone.BATTLEFIELD, playerA, "Tetsuko Umezawa, Pursuer");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(1, playerA, "Tetsuko Umezawa, Pursuer");
        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Llanowar Elves", "Tetsuko Umezawa, Pursuer");
        block(1, playerB, "Silvercoat Lion", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
    }
}
