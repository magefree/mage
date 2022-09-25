package org.mage.test.cards.cost.modification;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 * @author JayDi85
 */
public class CostReduceForEachTest extends CardTestPlayerBaseWithAIHelps {

    // tests for https://github.com/magefree/mage/issues/6698

    @Test
    public void test_AncientStoneIdol_Attacking() {
        // {10}
        // Flash
        // This spell costs {1} less to cast for each attacking creature.
        addCard(Zone.HAND, playerA, "Ancient Stone Idol", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10 - 2);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2); // give 2 cost reduction

        // before
        checkPlayableAbility("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ancient Stone Idol", false);

        // prepare for attack
        attack(1, playerA, "Balduvian Bears");
        attack(1, playerA, "Balduvian Bears");

        // on attack
        checkPlayableAbility("on attack", 1, PhaseStep.DECLARE_BLOCKERS, playerA, "Cast Ancient Stone Idol", true);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ancient Stone Idol");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ancient Stone Idol", 1);
    }

    @Test
    public void test_AncientStoneIdol_AttackingWithSacrifice() {
        // The total cost to cast a spell is locked in before you pay that cost. For example, if you control five attacking
        // creatures, including one you can sacrifice to add {C} to your mana pool, Ancient Stone Idol costs {5} to cast.
        // Then you can sacrifice the creature when you activate mana abilities just before paying the cost, and it still
        // costs only {5} to cast.
        // (2018-07-13)

        // {10}
        // Flash
        // This spell costs {1} less to cast for each attacking creature.
        addCard(Zone.HAND, playerA, "Ancient Stone Idol", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10 - 4);
        //
        // Sacrifice Blood Pet: Add {B}.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Pet", 2); // give 2 cost reduction + can be sacrificed as 2 mana

        // before
        checkPlayableAbility("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ancient Stone Idol", false);

        // prepare for attack
        attack(1, playerA, "Blood Pet");
        attack(1, playerA, "Blood Pet");

        // on attack (must automaticly sacrifice creatures as mana pay)
        checkPlayableAbility("on attack", 1, PhaseStep.DECLARE_BLOCKERS, playerA, "Cast Ancient Stone Idol", true);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ancient Stone Idol");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ancient Stone Idol", 1);
        assertGraveyardCount(playerA, "Blood Pet", 2);
    }

    @Test
    public void test_KhalniHydra_ColorReduce() {
        // {G}{G}{G}{G}{G}{G}{G}{G}
        // This spell costs {G} less to cast for each green creature you control.
        addCard(Zone.HAND, playerA, "Khalni Hydra", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8 - 2);
        addCard(Zone.HAND, playerA, "Balduvian Bears", 2); // give 2 cost reduction

        checkPlayableAbility("no cost reduction 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Khalni Hydra", false);

        // prepare creatures for reduce
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", true);
        checkPlayableAbility("no cost reduction 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Khalni Hydra", false);

        // can cast on next turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Khalni Hydra");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Khalni Hydra", 1);
    }

    @Test
    public void test_TorgaarFamineIncarnate_SacrificeXTargets() {
        // {6}{B}{B}
        // As an additional cost to cast this spell, you may sacrifice any number of creatures.
        // This spell costs {2} less to cast for each creature sacrificed this way.
        // When Torgaar, Famine Incarnate enters the battlefield, up to one target player's life total becomes half their starting life total, rounded down.
        addCard(Zone.HAND, playerA, "Torgaar, Famine Incarnate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8 - 4 - 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Balduvian Bears", 2); // give 4 cost reduction on sacrifice

        checkPlayableAbility("no cost reduction 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Torgaar, Famine Incarnate", false);

        // prepare creatures for reduce
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        checkPlayableAbility("no cost reduction 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Torgaar, Famine Incarnate", false);

        // can cast on next turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Torgaar, Famine Incarnate", true);
        setChoice(playerA, "X=2"); // two creatures sacrifice
        setChoice(playerA, "Balduvian Bears");
        setChoice(playerA, "Balduvian Bears");
        addTarget(playerA, playerB); // target player for half life

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Torgaar, Famine Incarnate", 1);
        assertLife(playerB, 20 / 2);
    }

    @Test
    public void test_AshnodsAltar_SacrificeCost() {
        // Sacrifice a creature: Add {C}{C}.
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);
        //
        addCard(Zone.HAND, playerA, "Alloy Myr", 1); // {3}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3 - 2);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // give 2 mana on sacrifice

        checkPlayableAbility("must play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alloy Myr", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alloy Myr");
        setChoice(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Alloy Myr", 1);
    }

    @Test
    public void test_BattlefieldThaumaturge_TargetCostReduce() {
        // Each instant and sorcery spell you cast costs {1} less to cast for each creature it targets.
        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Thaumaturge", 1);
        //
        // {3}{R}{R} sorcery
        // Shower of Coals deals 2 damage to each of up to three target creatures and/or players.
        addCard(Zone.HAND, playerA, "Shower of Coals", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 - 3);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears@bear", 3); // add 3 cost reduce on target

        checkPlayableAbility("must play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Shower of Coals", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shower of Coals");
        addTarget(playerA, "@bear.1^@bear.2^@bear.3");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Shower of Coals", 1);
    }

    @Test
    public void test_BenthicExplorers_ManaAbilityFromOpponentCard() {
        // {T}, Untap a tapped land an opponent controls: Add one mana of any type that land could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Benthic Explorers", 1);
        //
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1); // give 1 mana
        //
        // {1}, {T}: Tap target land.
        addCard(Zone.BATTLEFIELD, playerA, "Rishadan Port", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // prepare tapped land
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}: Tap target land", "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentTapped("must be tapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Forest", true, 1);

        // cast with opponent's mana
        checkPlayableAbility("must play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        setChoice(playerA, "Forest"); // mana from tapped

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }
    @Test
    public void test_BenthicExplorers_BottomlessVault() {
        // {T}, Untap a tapped land an opponent controls: Add one mana of any type that land could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Benthic Explorers", 1);

        // Bottomless Vault enters the battlefield tapped.
        // You may choose not to untap Bottomless Vault during your untap step.
        // At the beginning of your upkeep, if Bottomless Vault is tapped, put a storage counter on it.
        // Tap, Remove any number of storage counters from Bottomless Vault: Add Black for each storage counter removed this way.
        addCard(Zone.BATTLEFIELD, playerB, "Bottomless Vault", 1); // give 1 mana
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{B}", manaOptions);        
        
    }

    @Test
    public void test_CloudKey_ChooseCardTypeForCostReduce() {
        // {3}
        // As Cloud Key enters the battlefield, choose artifact, creature, enchantment, instant, or sorcery.
        // Spells you cast of the chosen type cost {1} less to cast.
        addCard(Zone.HAND, playerA, "Cloud Key", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1); // {1}{G}
        addCard(Zone.HAND, playerA, "Forest", 1);

        // prepare cloud key to reduce
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloud Key");
        setChoice(playerA, "Creature");

        // prepare lands
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can't with no mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        checkPlayableAbility("can play with mana and reduce", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_InspiringStatuary_PayByArtifacts() {
        // {3}
        // Nonartifact spells you cast have improvise. (Your artifacts can help cast those spells. Each artifact you
        // tap after you’re done activating mana abilities pays for {1}.)
        addCard(Zone.BATTLEFIELD, playerA, "Inspiring Statuary", 5);
        //
        addCard(Zone.HAND, playerA, "Keeper of Tresserhorn", 1); // {5}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6 - 5); // 5 mana from artifact

        checkPlayableAbility("can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Keeper of Tresserhorn", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Keeper of Tresserhorn");
        addTarget(playerA, "Inspiring Statuary", 5); // as pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Keeper of Tresserhorn", 1);
    }
}
