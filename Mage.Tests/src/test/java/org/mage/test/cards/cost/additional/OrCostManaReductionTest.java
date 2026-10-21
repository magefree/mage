package org.mage.test.cards.cost.additional;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class OrCostManaReductionTest extends CardTestPlayerBase {

    @Test
    public void test_LightningAxe_PayManaOption_IsReduced() {
        // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
        addCard(Zone.BATTLEFIELD, playerA, "Vadrik, Astral Archmage"); // 1/2
        //
        // As an additional cost to cast this spell, discard a card or pay {5}.
        // Lightning Axe deals 5 damage to target creature.
        addCard(Zone.HAND, playerA, "Lightning Axe"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // {R} + {5} - {1}
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Axe", "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertTappedCount("Mountain", true, 5);
    }

    @Test
    public void test_LouisoixsSacrifice_PayManaOption_IsReduced() {
        // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
        addCard(Zone.BATTLEFIELD, playerA, "Vadrik, Astral Archmage"); // 1/2
        //
        // As an additional cost to cast this spell, sacrifice a legendary creature or pay {2}.
        // Counter target activated ability, triggered ability, or noncreature spell.
        addCard(Zone.HAND, playerA, "Louisoix's Sacrifice"); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // {U} + {2} - {1}
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Louisoix's Sacrifice", "Lightning Bolt", "Lightning Bolt");
        setChoice(playerA, false); // pay {2} instead of sacrificing Vadrik

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Vadrik, Astral Archmage", 1);
    }

    @Test
    public void test_ActivatedAbility_ManaAndTapOption_IsReduced() {
        // Abilities you activate that aren't mana abilities cost {2} less to activate.
        // This effect can't reduce the mana in that cost to less than one mana.
        addCard(Zone.BATTLEFIELD, playerA, "Zirda, the Dawnwaker");
        //
        // {3}, {T} or {U}, {T}: Return target creature to its owner's hand unless its controller pays {1}.
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Shard");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // {3} - {2}
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T} or {U}, {T}", "Hill Giant");
        setChoice(playerA, true); // pay {3}, {T}
        setChoice(playerB, false); // don't pay {1}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Hill Giant", 1);
        assertTapped("Crystal Shard", true);
        assertTappedCount("Mountain", true, 1);
    }

    @Test
    public void test_XCost_AndOrCostManaOption_AreReducedTogether() {
        // Abilities you activate that aren't mana abilities cost {2} less to activate.
        // This effect can't reduce the mana in that cost to less than one mana.
        addCard(Zone.BATTLEFIELD, playerA, "Zirda, the Dawnwaker");
        //
        // {X}, discard a card or pay {2}: You gain X life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(GetXValue.instance), new ManaCostsImpl<>("{X}"));
        ability.addCost(new OrCost("discard a card or pay {2}", new DiscardCardCost(), new GenericManaCost(2)));
        addCustomCardWithAbility("test", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // {1} + {2} - {2}

        // no cards in hand, so the {2} has to be paid
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}");
        setChoice(playerA, "X=1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertTappedCount("Mountain", true, 1);
    }

    @Test
    public void test_CostAdjuster_ReducesOrCostManaOption() {
        // Isamaru is a legendary creature for the cost adjuster below
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");
        //
        // Discard a card or pay {2}: You gain 1 life. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(1), new ManaCostsImpl<>(""));
        ability.addCost(new OrCost("discard a card or pay {2}", new DiscardCardCost(), new GenericManaCost(2)));
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        addCustomCardWithAbility("test", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // {2} - {1}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card or pay {2}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertTappedCount("Mountain", true, 1);
    }

    @Test
    public void test_Modes_ChosenBeforeOrCost() {
        // Abilities you activate that aren't mana abilities cost {2} less to activate.
        // This effect can't reduce the mana in that cost to less than one mana.
        addCard(Zone.BATTLEFIELD, playerA, "Zirda, the Dawnwaker");
        //
        // Discard a card or pay {2}: Choose one -- You gain 1 life; or You gain 3 life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(1), new ManaCostsImpl<>(""));
        ability.addMode(new Mode(new GainLifeEffect(3)));
        ability.addCost(new OrCost("discard a card or pay {2}", new DiscardCardCost(), new GenericManaCost(2)));
        addCustomCardWithAbility("test", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // {2} - {2}, but at least one mana
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // can be discarded, so both options are offered

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card or pay {2}");
        setModeChoice(playerA, "2");
        setChoice(playerA, false); // pay {2} instead of discarding

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertTappedCount("Mountain", true, 1);
    }

    private void checkCustomInstantPlayable(boolean withVadrik, int mountains, boolean withArtifact, boolean mustBePlayable) {
        if (withVadrik) {
            // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
            addCard(Zone.BATTLEFIELD, playerA, "Vadrik, Astral Archmage"); // 1/2
        }
        if (withArtifact) {
            addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        }
        // As an additional cost to cast this spell, sacrifice an artifact or pay {2}.
        // You gain 1 life.
        SpellAbility spellAbility = new SpellAbility(new ManaCostsImpl<>("{R}"), "test instant");
        spellAbility.addCost(new OrCost(
                "sacrifice an artifact or pay {2}",
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN),
                new GenericManaCost(2)
        ));
        spellAbility.addEffect(new GainLifeEffect(1));
        addCustomCardWithAbility("test instant", playerA, null, spellAbility, CardType.INSTANT, "{R}", Zone.HAND);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", mountains);

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast test instant", mustBePlayable);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_ReducedManaOption() {
        checkCustomInstantPlayable(true, 2, false, true); // {R} + {2} - {1}
    }

    @Test
    public void test_Playable_NotEnoughManaForReducedOption() {
        checkCustomInstantPlayable(true, 1, false, false);
    }

    @Test
    public void test_Playable_NoReduction() {
        checkCustomInstantPlayable(false, 2, false, false); // needs {R} + {2}
    }

    @Test
    public void test_Playable_NonManaOption() {
        checkCustomInstantPlayable(false, 1, true, true); // {R} and sacrifice Ornithopter
    }

    @Test
    public void test_Playable_ManaAndTapOption() {
        // Abilities you activate that aren't mana abilities cost {2} less to activate.
        // This effect can't reduce the mana in that cost to less than one mana.
        addCard(Zone.BATTLEFIELD, playerA, "Zirda, the Dawnwaker");
        //
        // {3}, {T} or {U}, {T}: Return target creature to its owner's hand unless its controller pays {1}.
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Shard");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // {3} - {2}, no blue for the other option

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T} or {U}, {T}", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_ManaAndTapOption_NoReduction() {
        // {3}, {T} or {U}, {T}: Return target creature to its owner's hand unless its controller pays {1}.
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Shard");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // not enough for {3} and no blue for {U}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // target

        checkPlayableAbility("not playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T} or {U}, {T}", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_ManaAndTapOption_ColoredOption() {
        // {3}, {T} or {U}, {T}: Return target creature to its owner's hand unless its controller pays {1}.
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Shard");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1); // pays {U}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // target

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T} or {U}, {T}", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Flashback_PayManaOption_IsReduced() {
        // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
        addCard(Zone.BATTLEFIELD, playerA, "Vadrik, Astral Archmage"); // 1/2
        //
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, "Snapcaster Mage"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // As an additional cost to cast this spell, discard a card or pay {5}.
        // Lightning Axe deals 5 damage to target creature.
        addCard(Zone.GRAVEYARD, playerA, "Lightning Axe"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // {R} + {5} - {1}
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Lightning Axe");

        // no cards left in hand, so the {5} has to be paid
        checkPlayableAbility("playable", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertExileCount(playerA, "Lightning Axe", 1);
        assertTappedCount("Mountain", true, 5);
    }
}
