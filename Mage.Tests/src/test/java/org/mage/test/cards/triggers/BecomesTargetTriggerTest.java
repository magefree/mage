package org.mage.test.cards.triggers;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BecomesTargetTriggerTest extends CardTestPlayerBase {

    @Test
    public void testAshenmoorLiege() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Claustrophobia"); // {1}{U}{U}

        // Other black creatures you control get +1/+1.
        // Other red creatures you control get +1/+1.
        // Whenever Ashenmoor Liege becomes the target of a spell or ability an opponent controls, that player loses 4 life.
        addCard(Zone.BATTLEFIELD, playerB, "Ashenmoor Liege", 1);  // 4/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Claustrophobia", "Ashenmoor Liege");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 16);
        assertPermanentCount(playerA, "Claustrophobia", 1);
        assertPowerToughness(playerB, "Ashenmoor Liege", 4, 1);
    }

    @Test
    public void testVeneratedRotpriest() {
        String rotpriest = "Venerated Rotpriest"; // 1/2
        // Whenever a creature you control becomes the target of a spell, target opponent gets a poison counter.
        String growth = "Giant Growth";

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, rotpriest);
        addCard(Zone.HAND, playerA, growth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, rotpriest);
        addTarget(playerA, playerB); // to get a poison counter

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerB, CounterType.POISON, 1);
        assertPowerToughness(playerA, rotpriest, 4, 5);
    }

    private static final String gKeeper = "Glyph Keeper"; // 5/3 Flying
    // Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testGlyphKeeperCountersFirstSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, bolt);

        addCard(Zone.BATTLEFIELD, playerB, gKeeper);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gKeeper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, bolt, 1);
        assertPermanentCount(playerB, gKeeper, 1);
    }

    @Test
    public void testGlyphKeeperCountersFirstSpellEachTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, bolt, 2);

        addCard(Zone.BATTLEFIELD, playerB, gKeeper);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gKeeper);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gKeeper);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, bolt, 2);
        assertPermanentCount(playerB, gKeeper, 1);
    }
    
    @Test
    public void testGlyphKeeperCountersFirstSpellButNotSecondSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, bolt, 2);

        addCard(Zone.BATTLEFIELD, playerB, gKeeper);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gKeeper);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, bolt, gKeeper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, bolt, 2);
        assertPermanentCount(playerB, gKeeper, 0);
    }

    @Test
    public void testGlyphKeeperCountersFirstAbilityButNotSecondOne() {
        /*
        Soulstinger {3}{B}
        Creature - Scorpion Demon  4/5
        When Soulstinger enters the battlefield, put two -1/-1 counter on target creature you control.
        When Soulstinger dies, you may put a -1/-1 counter on target creature for each -1/-1 counter on Soulstinger. 
        */
        String sStinger = "Soulstinger";
        
        /*
        Cartouche of Strength {2}{G}
        Enchantment - Aura Cartouche
        Enchant creature you control
        When Cartouche of Strength enters the battlefield, you may have enchanted creature fight target creature an opponent controls.
        Enchanted creature gets +1/+1 and has trample. 
        */
        String cStrength = "Cartouche of Strength";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerA, gKeeper);
        addCard(Zone.HAND, playerA, sStinger);
        addCard(Zone.HAND, playerA, cStrength);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sStinger);
        addTarget(playerA, gKeeper); // should be countered by Glyph Keeper clause as first ability targetting it
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cStrength, gKeeper); // should not be countered anymore
        setChoice(playerA, true);
        addTarget(playerA, memnite); // Cartouche of Strength fight

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, gKeeper, 1);
        assertGraveyardCount(playerA, sStinger, 0); // countered
        assertGraveyardCount(playerA, cStrength, 0); // should not be countered
        assertPermanentCount(playerA, cStrength, 1);
        assertGraveyardCount(playerB, memnite, 1); // dies from fight
        assertPowerToughness(playerA, gKeeper, 6, 4, Filter.ComparisonScope.All);
        // Soul Stinger should never have given it two -1/-1 counters
        // And Cartouche of Strength gives +1/+1
        assertCounterCount(playerA, gKeeper, CounterType.M1M1, 0);
    }

    @Test
    public void testDiffusionSliver() {
        String diffusion = "Diffusion Sliver"; // 1/1 Sliver
        // Whenever a Sliver creature you control becomes the target of a spell or ability an opponent controls,
        // counter that spell or ability unless its controller pays {2}.
        String metallic = "Metallic Sliver"; // 1/1 Sliver

        addCard(Zone.BATTLEFIELD, playerA, diffusion);
        addCard(Zone.BATTLEFIELD, playerA, metallic);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, bolt, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Cunning Sparkmage", 1); // 0/1 Haste
        // {T}: Cunning Sparkmage deals 1 damage to any target.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, metallic);
        setChoice(playerB, false); // choose not to pay
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: {this} deals", diffusion);
        setChoice(playerB, false); // choose not to pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, bolt, 1);
        assertTapped("Cunning Sparkmage", true);
        assertPermanentCount(playerA, diffusion, 1);
        assertPermanentCount(playerA, metallic, 1);
    }

    @Test
    public void testThunderbreakRegent() {
        String dragon = "Thunderbreak Regent"; // 4/4
        // Whenever a Dragon you control becomes the target of a spell or ability an opponent controls, Thunderbreak Regent deals 3 damage to that player.

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, dragon);
        addCard(Zone.HAND, playerB, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, dragon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, dragon, 3);
        assertLife(playerB, 17);
    }

    @Test
    public void testCloudCover() {
        String cloud = "Cloud Cover"; // Enchantment
        // Whenever another permanent you control becomes the target of a spell or ability an opponent controls,
        // you may return that permanent to its owner’s hand.
        String myr = "Omega Myr"; // 1/2

        addCard(Zone.BATTLEFIELD, playerB, "Cunning Sparkmage");
        addCard(Zone.BATTLEFIELD, playerA, cloud);
        addCard(Zone.BATTLEFIELD, playerA, myr);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: {this} deals", myr);
        setChoice(playerA, true); // return to hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, myr, 1);
        assertTapped("Cunning Sparkmage", true);
    }

    @Test
    public void testIllusionaryArmor() {
        String armor = "Illusionary Armor"; // Enchantment - Aura {4}{U}
        // Enchant creature
        // Enchanted creature gets +4/+4.
        // When enchanted creature becomes the target of a spell or ability, sacrifice Illusionary Armor.
        String beast = "Axebane Beast"; // 3/4

        addCard(Zone.BATTLEFIELD, playerA, beast);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, armor);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, armor, beast);
        checkPT("Boosted", 1, PhaseStep.BEGIN_COMBAT, playerA, beast, 7, 8);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, bolt, beast);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, armor, 1);
        assertPowerToughness(playerA, beast, 3, 4);
        assertDamageReceived(playerA, beast, 3);
    }

    @Test
    public void testFracturedLoyalty() {
        String hatchling = "Kraken Hatchling"; // 0/4
        String aura = "Fractured Loyalty"; // Enchantment - Aura {1}{R}
        // Whenever enchanted creature becomes the target of a spell or ability,
        // that spell or ability's controller gains control of that creature. (This effect lasts indefinitely.)

        addCard(Zone.BATTLEFIELD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, aura);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, hatchling);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, bolt, hatchling);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, bolt, 1);
        assertPermanentCount(playerA, hatchling, 0);
        assertDamageReceived(playerB, hatchling, 3);
    }

    @Test
    public void testDormantGomazoa() {
        String gomazoa = "Dormant Gomazoa"; // 5/5 Flying {1}{U}{U}
        // Dormant Gomazoa enters the battlefield tapped.
        // Dormant Gomazoa doesn't untap during your untap step.
        // Whenever you become the target of a spell, you may untap Dormant Gomazoa.

        addCard(Zone.HAND, playerA, gomazoa);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gomazoa);
        checkPermanentTapped("Enters tapped", 1, PhaseStep.BEGIN_COMBAT, playerA, gomazoa, true, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, bolt, playerA);
        setChoice(playerA, true); // choose to untap

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, bolt, 1);
        assertTapped(gomazoa, false);
        assertLife(playerA, 17);
    }

    private static final String mammoth = "Battle Mammoth"; // 6/5 Trample
    // Whenever a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
    private static final String bear = "Runeclaw Bear"; // 2/2
    private static final String cBond = "Common Bond"; // 1GW Instant
    // Put a +1/+1 counter on target creature. Put a +1/+1 counter on target creature.

    @Test
    public void testBattleMammothSeparateTargets() {
        addCard(Zone.BATTLEFIELD, playerA, mammoth);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, "Savannah", 3);
        addCard(Zone.HAND, playerB, cBond);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, cBond, mammoth+"^"+bear);
        setChoice(playerA, "Whenever"); // order two triggers
        setChoice(playerA, true); // draw a card
        setChoice(playerA, true); // draw a card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, cBond, 1);
        assertPowerToughness(playerA, mammoth, 7, 6);
        assertPowerToughness(playerA, bear, 3, 3);
        assertHandCount(playerA, 2);
    }

    @Test
    public void testBattleMammothSameTarget() {
        addCard(Zone.BATTLEFIELD, playerA, mammoth);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, "Savannah", 3);
        addCard(Zone.HAND, playerB, cBond);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, cBond, bear+"^"+bear);
        // Should be only one trigger here
        setChoice(playerA, true); // draw a card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, cBond, 1);
        assertPowerToughness(playerA, bear, 4, 4);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testBattleMammothRepeatAbility() {
        addCard(Zone.BATTLEFIELD, playerA, mammoth);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 8);
        addCard(Zone.BATTLEFIELD, playerB, "Shapers of Nature"); // 3/3
        // {3}{G}: Put a +1/+1 counter on target creature.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{3}{G}: Put", bear);
        setChoice(playerA, true); // draw a card
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{3}{G}: Put", bear);
        setChoice(playerA, true); // draw a card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, bear, 4, 4);
        assertHandCount(playerA, 2);
    }

    @Test
    public void testAngelicCubDoubleTarget() {
        String cub = "Angelic Cub"; // 1/1
        // Whenever Angelic Cub becomes the target of a spell or ability for the first time each turn, put a +1/+1 counter on it.
        // As long as Angelic Cub has three or more +1/+1 counters on it, it has flying.

        addCard(Zone.BATTLEFIELD, playerA, cub);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 3);
        addCard(Zone.HAND, playerA, cBond);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cBond, cub+"^"+cub);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, cub, 4, 4);
        assertAbility(playerA, cub, FlyingAbility.getInstance(), true);
    }

    @Test
    public void testUnsettledMarinerFieldOfRuin() {
        // Reported bug: #10530
        String mariner = "Unsettled Mariner"; // 2/2
        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls,
        // counter that spell or ability unless its controller pays {1}.
        String ruin = "Field of Ruin"; // 2/2
        // {2}, {T}, Sacrifice Field of Ruin: Destroy target nonbasic land an opponent controls.
        // Each player searches their library for a basic land card, puts it onto the battlefield, then shuffles.

        addCard(Zone.BATTLEFIELD, playerA, mariner);
        addCard(Zone.BATTLEFIELD, playerA, "Evolving Wilds");
        addCard(Zone.BATTLEFIELD, playerB, ruin);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, {T}, Sacrifice");
        addTarget(playerB, "Evolving Wilds");
        setChoice(playerB, false); // choose not to pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, ruin, 1);
    }

    @Test
    public void testCounterAbilitySacrificedSource() {
        // related to #10530, but this one works fine regardless...
        String mariner = "Unsettled Mariner"; // 2/2
        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls,
        // counter that spell or ability unless its controller pays {1}.
        String felidar = "Felidar Cub"; // 2/2
        // Sacrifice Felidar Cub: Destroy target enchantment.
        String anthem = "Glorious Anthem"; // Creatures you control get +1/+1.

        addCard(Zone.BATTLEFIELD, playerA, mariner);
        addCard(Zone.BATTLEFIELD, playerA, anthem);
        addCard(Zone.BATTLEFIELD, playerB, felidar);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice");
        addTarget(playerB, anthem);
        setChoice(playerB, false); // choose not to pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, felidar, 1);
        assertPowerToughness(playerA, mariner, 3, 3);
    }

}
