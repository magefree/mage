package org.mage.test.cards.mana.conditional;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 * @author LevelX2
 */
public class ConditionalManaTest extends CardTestPlayerBase {

    @Test
    public void testNormalUse() {
        setStrictChooseMode(true);

        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);

        // Target player gains 7 life.
        addCard(Zone.HAND, playerA, "Heroes' Reunion", 1); // Instant {G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroes' Reunion", playerA);
        setChoice(playerA, "Green"); // Choose color
        setChoice(playerA, "White"); // Choose color

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Heroes' Reunion", 1);
        assertHandCount(playerA, "Heroes' Reunion", 0); // player A could not cast it
        assertLife(playerA, 27);
    }

    @Test
    public void testNotAllowedUse() {
        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Silvercoat", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 1); // player A could not cast Silvercoat Lion because the conditional mana is not available
    }

    @Test
    public void testWorkingWithReflectingPool() {
        addCard(Zone.BATTLEFIELD, playerA, "Cavern of Souls", 1); // can give {C] or {any} mana ({any} with restrictions)
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1); // must give {C} or {any} mana from the Cavern, but without restrictions
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1); // white bear
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void testWorkingWithReflectingPool2() {
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1); // can create white mana without restriction from the Hive
        // {T}: Add {C}.
        // {T}: Add one mana of any color. Spend this mana only to cast a Sliver spell.
        // {5}, {T}: Create a 1/1 colorless Sliver creature token. Activate this ability only if you control a Sliver.
        addCard(Zone.BATTLEFIELD, playerA, "Sliver Hive", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add one mana of any type");

        setChoice(playerA, "White");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * I was unable to use "Rosheen Meanderer" ability to pay for "Candelabra of
     * Tawnos" ability even thought it has "X" on its cost
     */
    @Test
    public void testRosheenMeandererUsingAbility() {
        // Flying
        addCard(Zone.HAND, playerB, "Snapping Drake", 2); // {3}{U}
        // {T}: Add {C}{C}{C}{C}. Spend this mana only on costs that contain {X}.
        addCard(Zone.BATTLEFIELD, playerB, "Rosheen Meanderer", 1);
        // {X}, {T}: Untap X target lands.
        addCard(Zone.BATTLEFIELD, playerB, "Candelabra of Tawnos", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        setStrictChooseMode(true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Snapping Drake", true);
        activateManaAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: Add {C}{C}{C}{C}");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{X}, {T}: Untap");
        setChoice(playerB, "X=4");
        addTarget(playerB, "Island");
        addTarget(playerB, "Island");
        addTarget(playerB, "Island");
        addTarget(playerB, "Island");
        waitStackResolved(2, PhaseStep.POSTCOMBAT_MAIN);

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Snapping Drake");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Island", true, 4);
        assertTappedCount("Rosheen Meanderer", true, 1);
        assertTappedCount("Candelabra of Tawnos", true, 1);

        assertPermanentCount(playerB, "Snapping Drake", 2);
    }

    /**
     * I've found a bit of a problem with colorless costs. I've been unable to
     * pay colorless costs with lands conditionally tapping for 2 colorless i.e
     * shrine of forsaken gods and eldrazi temple ,including if I float the
     * mana. Seperately but on a related note, if you float at least one
     * colorless mana you can pay all colorless costs with floated generic mana.
     */
    @Test
    public void testPayColorlessWithConditionalMana() {
        // {T}: Add {C}.
        // {T}: Add {C}{C}. Spend this mana only to cast colorless spells. Activate this ability only if you control seven or more lands.
        addCard(Zone.BATTLEFIELD, playerA, "Shrine of the Forsaken Gods", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        // When you cast Kozilek, the Great Distortion, if you have fewer than seven cards in hand, draw cards equal to the difference.
        // Menace
        // Discard a card with converted mana cost X: Counter target spell with converted mana cost X.
        addCard(Zone.HAND, playerA, "Kozilek, the Great Distortion", 1); // {8}{C}{C}

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kozilek, the Great Distortion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kozilek, the Great Distortion", 1);
    }

    @Test
    public void CultivatorDroneColorlessSpell() {
        // Devoid
        // {T}: Add {C}. Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}.
        addCard(Zone.BATTLEFIELD, playerA, "Cultivator Drone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Target creature gets +3/-3 until end of turn.
        addCard(Zone.HAND, playerA, "Spatial Contortion", 1); // {1}{C}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spatial Contortion", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Spatial Contortion", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void CultivatorDroneColorlessAbility() {
        // Devoid
        // {T}: Add {C}. Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}.
        addCard(Zone.BATTLEFIELD, playerA, "Cultivator Drone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1);
        // Untap Endbringer during each other player's untap step.
        // {T}: Endbringer deals 1 damage to any target.
        // {C}, {T}: Target creature can't attack or block this turn.
        // {C}{C}, {T}: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Endbringer", 1); // {1}{C}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{C}{C},");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void CultivatorDroneColorlessCost() {
        // Devoid
        // {T}: Add {C}. Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}.
        addCard(Zone.BATTLEFIELD, playerA, "Cultivator Drone", 1);
        // Devoid (This card has no color.)
        // Flying
        // When Gravity Negator attacks, you may pay {C}. If you do, another target creature gains flying until end of turn. ({C} represents colorless mana)
        addCard(Zone.BATTLEFIELD, playerA, "Gravity Negator", 1); // 2/3

        attack(1, playerA, "Gravity Negator");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Gravity Negator", true);
        assertAbility(playerA, "Cultivator Drone", FlyingAbility.getInstance(), true);

        assertLife(playerB, 18);
    }

    @Test
    public void EmpoweredAutogeneratorAddsCountWithMana() {
        // Empowered Autogenerator's activated ability is a mana ability. It doesn’t use the stack and can’t be responded to. (2019-08-23)

        // Empowered Autogenerator enters the battlefield tapped.
        // {T}: Put a charge counter on Empowered Autogenerator. Add X mana of any one color, where X is the number of charge counters on Empowered Autogenerator.
        addCard(Zone.BATTLEFIELD, playerA, "Empowered Autogenerator", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a charge counter");
        setChoice(playerA, "Red");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Empowered Autogenerator", CounterType.CHARGE, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void EmpoweredAutogeneratorAddsDoubleCountersWithDoubleSeason() {
        // Empowered Autogenerator's activated ability is a mana ability. It doesn’t use the stack and can’t be responded to. (2019-08-23)

        // Empowered Autogenerator enters the battlefield tapped.
        // {T}: Put a charge counter on Empowered Autogenerator. Add X mana of any one color, where X is the number of charge counters on Empowered Autogenerator.
        addCard(Zone.BATTLEFIELD, playerA, "Empowered Autogenerator", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        //
        // If an effect would put one or more tokens into play under your control, it puts twice that many of those tokens into play instead.
        // If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season", 1);

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a charge counter");
        setChoice(playerA, "Red");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Empowered Autogenerator", CounterType.CHARGE, 2);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void EmpoweredAutogeneratorAddsDoubleCountersWithDoubleSeason_AutoPay() {
        // Empowered Autogenerator's activated ability is a mana ability. It doesn’t use the stack and can’t be responded to. (2019-08-23)

        // Empowered Autogenerator enters the battlefield tapped.
        // {T}: Put a charge counter on Empowered Autogenerator. Add X mana of any one color, where X is the number of charge counters on Empowered Autogenerator.
        addCard(Zone.BATTLEFIELD, playerA, "Empowered Autogenerator", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        //
        // If an effect would put one or more tokens into play under your control, it puts twice that many of those tokens into play instead.
        // If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season", 1);

        //activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a charge counter"); // auto pay
        setChoice(playerA, "Red");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Empowered Autogenerator", CounterType.CHARGE, 2);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void DictateOfKarametra_ManualPay() {
        // Whenever you tap a land for mana, add one mana of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Dictate of Karametra");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.HAND, playerA, "Precision Bolt", 1); // {2}{R}

        // manual mana pay to activate extra mana
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Precision Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
    }

    // TODO: add support TriggeredManaAbility for available mana calculations
    //  AI can't see extra mana added by AddManaOfAnyTypeProducedEffect and same
    //  (maybe it was removed by https://github.com/magefree/mage/pull/5943 to fix multiple TAPPED_FOR_MANA calls or never works before)
    //  As idea: getPlayable -> getManaAvailable -> available.addMana -- search all TriggeredManaAbility
    //  and process all available net mana by special call like TriggeredManaAbility->getNetManaForEvent(ManaEvent xxx)

    @Test
    public void TriggeredManaAbilityMustGivesExtraManaOptions() {
        // TriggeredManaAbility must give extra mana options (2 red instead 1)
        // Whenever you tap a land for mana, add one mana of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Dictate of Karametra");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{R}{R}", manaOptions);
    }

    @Test
    public void DictateOfKarametra_AutoPay() {
        // Whenever you tap a land for mana, add one mana of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Dictate of Karametra");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.HAND, playerA, "Precision Bolt", 1); // {2}{R}

        // computer must see available mana (4 red mana instead 2)
        //activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        //activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        showAvailableAbilities("abils", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Precision Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testTwoConditionalMana() {
        setStrictChooseMode(true);

        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
        // Exile a card from your graveyard: Add {C}. Spend this mana only to cast a colored spell without {X} in its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Titans' Nest"); // Enchantment {1}{B}{G}{U}

        // {T}: Add {C}{C}{C}{C}. Spend this mana only on costs that contain {X}.
        addCard(Zone.BATTLEFIELD, playerA, "Rosheen Meanderer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Titans' Nest", 1);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{R}[{TitansNestManaCondition}]", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{R}[{RosheenMeandererManaCondition}]", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{R}[{RosheenMeandererManaCondition}{TitansNestManaCondition}]", manaOptions);
    }

    @Test
    public void testConditionalManaDeduplication() {
        ManaOptions manaOptions = new ManaOptions();

        ConditionalMana originalMana = new ConditionalMana(Mana.GreenMana(1));

        ConditionalMana mana2 = originalMana.copy();
        mana2.addCondition(AdamantCondition.WHITE);

        ConditionalMana mana3 = originalMana.copy();
        mana3.addCondition(AdamantCondition.BLUE);
        ConditionalMana mana3Copy = originalMana.copy();
        mana3Copy.addCondition(AdamantCondition.BLUE);
        mana3Copy.add(Mana.GreenMana(1));

        ConditionalMana mana4 = originalMana.copy();
        mana4.addCondition(AdamantCondition.BLACK);
        ConditionalMana mana4Copy = originalMana.copy();
        mana4Copy.addCondition(AdamantCondition.BLACK);

        manaOptions.add(originalMana);
        manaOptions.add(mana2);
        manaOptions.add(mana3);
        manaOptions.add(mana3Copy); // Added, and should remain since different amount of Green mana
        manaOptions.add(mana4);
        manaOptions.add(mana4Copy); // Adding it to make sure it gets removed

        Assert.assertEquals("Incorrect number of mana", 5, manaOptions.size());
    }

}
