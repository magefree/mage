package org.mage.test.cards.single.ncc;

import mage.abilities.Ability;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * {@link mage.cards.h.HenzieToolboxTorre Henzie "Toolbox" Torre}
 * {B}{R}{G}
 * Each creature spell you cast with mana value 4 or greater has blitz.
 * The blitz cost is equal to its mana cost.
 *      (You may choose to cast that spell for its blitz cost.
 *       If you do, it gains haste and “When this creature dies, draw a card.”
 *       Sacrifice it at the beginning of the next end step.)
 * Blitz costs you pay cost {1} less for each time you’ve cast your commander from the command zone this game.
 *
 * @author Alex-Vasile
 */
public class HenzieToolboxTorreTest extends CardTestCommander4Players {

    private static final String henzieToolboxTorre = "Henzie \"Toolbox\" Torre";
    // 2/4
    // {2}{R}{R}{R}
    private static final String aerathiBerserker = "Aerathi Berserker";
    private static final String commandTower = "Command Tower";
    private static final String embrace = "Embrace the Unknown";
    private static final String withBlitz = " with Blitz";

    private void assertBlitzed(String cardName, boolean isBlitzed) {
        assertPermanentCount(playerA, cardName, 1);
        Permanent permanent = getPermanent(cardName);
        Assert.assertEquals(
                "Permanent should " + (isBlitzed ? "" : "not ") + "have haste", isBlitzed,
                permanent.hasAbility(HasteAbility.getInstance(), currentGame)
        );
        Assert.assertEquals(
                "Permanent should " + (isBlitzed ? "" : "not ") + "have card draw trigger", isBlitzed,
                permanent
                        .getAbilities(currentGame)
                        .stream()
                        .map(Ability::getRule)
                        .anyMatch("When this creature dies, draw a card."::equals)
        );
    }

    @Test
    public void test_DoubleFaced_TransformingFrontFace() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        // transforms to Braska's Final Aeon
        addCard(Zone.HAND, playerA, "Jecht, Reluctant Guardian"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jecht, Reluctant Guardian" + withBlitz);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed("Jecht, Reluctant Guardian", true);
    }

    @Test
    public void test_DoubleFaced_TransformingFrontFace_SacrificeAndDraw() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        // transforms to Braska's Final Aeon
        addCard(Zone.HAND, playerA, "Jecht, Reluctant Guardian"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jecht, Reluctant Guardian" + withBlitz);

        // sacrifice at the beginning of the next end step and draw on dies
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Jecht, Reluctant Guardian", 1);
        assertHandCount(playerA, 1 + 1); // draw step + draw on dies
    }

    @Test
    public void test_DoubleFaced_TransformingBackFace_CantCast() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        // transforms to Braska's Final Aeon
        addCard(Zone.HAND, playerA, "Jecht, Reluctant Guardian"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        checkPlayableAbility("front face with blitz", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Jecht, Reluctant Guardian" + withBlitz, true);
        checkPlayableAbility("back face with blitz", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Braska's Final Aeon" + withBlitz, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_DoubleFaced_ModalBackFace() {
        // If the back face of a modal double-faced card is a creature with mana value 4 or greater,
        // you may cast it using the blitz ability granted by Henzie.
        // (2022-04-29)
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        // front face Bruce Banner {U}, back face The Incredible Hulk {4}{G}{G}
        addCard(Zone.HAND, playerA, "Bruce Banner");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Incredible Hulk" + withBlitz);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed("The Incredible Hulk", true);
    }

    @Test
    public void test_DoubleFaced_ModalFrontFaceWithLowManaValue_NoBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        // front face Bruce Banner {U}, back face The Incredible Hulk {4}{G}{G}
        addCard(Zone.HAND, playerA, "Bruce Banner");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        checkPlayableAbility("front face", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bruce Banner", true);
        checkPlayableAbility("front face with blitz", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bruce Banner" + withBlitz, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_XCost() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        addCard(Zone.HAND, playerA, "Stonecoil Serpent"); // {X}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stonecoil Serpent" + withBlitz);
        setChoice(playerA, "X=4"); // mana value 4
        setChoice(playerA, "Stonecoil Serpent"); // order of the replacement effects on enter

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed("Stonecoil Serpent", true);
    }

    @Test
    public void test_XCost_WithOtherMana() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        addCard(Zone.HAND, playerA, "Genesis Hydra"); // {X}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Genesis Hydra" + withBlitz);
        setChoice(playerA, "X=2"); // mana value 4
        setChoice(playerA, "Genesis Hydra"); // order of the replacement effects on enter

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed("Genesis Hydra", true);
    }

    @Test
    public void test_XCost_DoubleX() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        // enters with X +1/+1 counters
        addCard(Zone.HAND, playerA, "Walking Ballista"); // {X}{X}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Walking Ballista" + withBlitz);
        setChoice(playerA, "X=2"); // mana value 4
        setChoice(playerA, "Walking Ballista"); // order of the replacement effects on enter

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed("Walking Ballista", true);
        assertCounterCount(playerA, "Walking Ballista", CounterType.P1P1, 2);
    }

    @Test
    public void test_XCost_DoubleX_LowManaValue_CantUseBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        addCard(Zone.HAND, playerA, "Walking Ballista"); // {X}{X}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Walking Ballista" + withBlitz);
        setChoice(playerA, "X=1"); // mana value 2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertTrue("X must have limits: " + e.getMessage(), e.getMessage().contains("Found wrong X value = 1"));
            return;
        }
        Assert.fail("test must fail");
    }

    @Test
    public void test_XCost_DoubleX_LowManaValue_NormalCast() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        //
        // enters with X +1/+1 counters
        addCard(Zone.HAND, playerA, "Walking Ballista"); // {X}{X}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Walking Ballista");
        setChoice(playerA, "X=1"); // mana value 2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed("Walking Ballista", false);
        assertCounterCount(playerA, "Walking Ballista", CounterType.P1P1, 1);
    }

    /**
     * Test that the card gets blitz and is properly sacrificed.
     */
    @Test
    public void gainsBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, aerathiBerserker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }

    /**
     * Test that it's properly being discount for each time the commander was cast.
     * Bonded Construct should be playable for free with its blitz costs.
     */
    @Test
    public void commanderCastDiscount() {
        addCard(Zone.COMMAND, playerA, henzieToolboxTorre);
        // Henzie costs 3 mana, and berserker costs 5 mana reduced to 4
        addCard(Zone.BATTLEFIELD, playerA, commandTower, 3 + 4);
        addCard(Zone.HAND, playerA, aerathiBerserker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, henzieToolboxTorre, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }

    /**
     * Test that you can cast your commander with the blitz cost
     */
    @Test
    @Ignore // TODO: see #12188
    public void commanderCastBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.COMMAND, playerA, aerathiBerserker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }

    /**
     * Test that you can cast your own card from exile
     */
    @Test
    public void exileCastBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 5);
        addCard(Zone.HAND, playerA, embrace);
        addCard(Zone.LIBRARY, playerA, aerathiBerserker);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, embrace, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }
}
