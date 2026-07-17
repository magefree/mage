package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.ManaOptions;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;

/**
 * @author LevelX2
 */
public class BestowTest extends CardTestPlayerBase {

    /**
     * Tests that if from bestow permanent targeted creature gets protection
     * from the color of the bestow permanent, the bestow permanent becomes a
     * creature on the battlefield.
     */

    /* Silent Artisan
     * Creature - Giant 3/5
     *
     *
     * Hopeful Eidolon {W}
     * Enchantment Creature - Spirit   1/1
     * Bestow {3}{W} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature.
     * It becomes a creature again if it's not attached to a creature.)
     * Lifelink (Damage dealt by this creature also causes you to gain that much life.)
     * Enchanted creature gets +1/+1 and has lifelink.
     *
     * Gods Willing {W}
     * Instant
     * Target creature you control gains protection from the color of your choice until end of turn.
     * Scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
     *
     */
    @Test
    public void bestowEnchantmentToCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silent Artisan"); // 3/5
        addCard(Zone.HAND, playerA, "Hopeful Eidolon");
        addCard(Zone.HAND, playerA, "Gods Willing");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Silent Artisan");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gods Willing", "Silent Artisan");
        setChoice(playerA, "White");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scrying 1 to the top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // because of protection the Hopeful Eidolon should be a creature on the battlefield
        assertPermanentCount(playerA, "Silent Artisan", 1);
        assertPowerToughness(playerA, "Silent Artisan", 3, 5);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertPowerToughness(playerA, "Hopeful Eidolon", 1, 1);
    }

    @Test
    public void bestowStaysEnchantment() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silent Artisan"); // 3/5
        addCard(Zone.HAND, playerA, "Hopeful Eidolon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Silent Artisan");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Silent Artisan", 1);
        assertPowerToughness(playerA, "Silent Artisan", 4, 6);
        assertAbility(playerA, "Silent Artisan", LifelinkAbility.getInstance(), true);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertSubtype("Hopeful Eidolon", SubType.AURA);
        assertType("Hopeful Eidolon", CardType.ENCHANTMENT, true);
        assertType("Hopeful Eidolon", CardType.CREATURE, false);
        assertNotSubtype("Hopeful Eidolon", SubType.SPIRIT);
    }

    /**
     * Test that cast with bestow does not trigger evolve
     */
    @Test
    public void bestowEnchantmentDoesNotTriggerEvolve() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Creature - Giant 3/5
        addCard(Zone.BATTLEFIELD, playerA, "Silent Artisan");

        addCard(Zone.HAND, playerA, "Experiment One");
        // Enchanted creature gets +4/+2.
        addCard(Zone.HAND, playerA, "Boon Satyr");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Experiment One");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Boon Satyr using bestow", "Silent Artisan");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // because Boon Satyr is no creature on the battlefield, evolve may not trigger
        assertPermanentCount(playerA, "Boon Satyr", 1);
        Permanent boonSatyr = getPermanent("Boon Satyr", playerA);
        Assert.assertTrue("Boon Satyr may not be a creature", !boonSatyr.isCreature(currentGame));
        assertPermanentCount(playerA, "Silent Artisan", 1);
        assertPermanentCount(playerA, "Experiment One", 1);
        assertPowerToughness(playerA, "Experiment One", 1, 1);
        assertPowerToughness(playerA, "Silent Artisan", 7, 7);

    }

    /**
     * Test that the bestow enchantment becomes a creature if the enchanted
     * creature dies
     */
    @Test
    public void bestowEnchantmentBecomesCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Safehold Elite"); // 2/2 creature + Persist to return as a 1/1
        addCard(Zone.HAND, playerA, "Hopeful Eidolon");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Safehold Elite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Safehold Elite", 1);
        assertPowerToughness(playerA, "Safehold Elite", 1, 1);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertPowerToughness(playerA, "Hopeful Eidolon", 1, 1);
        assertType("Hopeful Eidolon", CardType.CREATURE, true);
        assertSubtype("Hopeful Eidolon", SubType.SPIRIT);
        assertType("Hopeful Eidolon", CardType.ENCHANTMENT, true);
        assertNotSubtype("Hopeful Eidolon", SubType.AURA);
    }

    /**
     * Test that card cast with bestow will not be tapped, if creatures come
     * into play tapped. If it is cast with bestow but the target removed, it should be tapped.
     */
    @Test
    public void bestowEnchantmentWillNotBeTapped() {
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Silent Artisan");

        addCard(Zone.HAND, playerA, "Boon Satyr");
        addCard(Zone.HAND, playerA, "Leafcrown Dryad");


        // Enchantment {1}{W}
        // Creatures your opponents control enter the battlefield tapped.
        addCard(Zone.BATTLEFIELD, playerB, "Imposing Sovereign");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boon Satyr using bestow", "Silent Artisan");
        checkPT("Boon Satyr attached", 1, PhaseStep.BEGIN_COMBAT, playerA, "Silent Artisan", 7, 7);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Leafcrown Dryad using bestow", "Silent Artisan");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Silent Artisan");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Boon Satyr should not be tapped since it resolved as an aura, Leafcrown Dryad resolved as a creature
        assertTapped("Boon Satyr", false);
        assertTapped("Leafcrown Dryad", true);

    }

    /**
     * If I have a bestowed creature on the battlefield and my opponent uses Far
     * // Away casting both sides, will the creature that has bestow come in
     * time for it to be sacrificed or does it fully resolve before the creature
     * comes in?
     * <p>
     * Bestowed creature can be used to sacrifice a creature for the Away part.
     * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/513828-bestow-far-away
     */
    @Test
    public void bestowWithFusedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        /**
         * Cyclops of One-Eyed Pass {2}{R}{R} Creature - Cyclops 5/2
         */
        addCard(Zone.BATTLEFIELD, playerA, "Cyclops of One-Eyed Pass");

        /**
         * Nyxborn Rollicker {R} Enchantment Creature - Satyr 1/1 Bestow {1}{R}
         * (If you cast this card for its bestow cost, it's an Aura spell with
         * enchant creature. It becomes a creature again if it's not attached to
         * a creature.) Enchanted creature gets +1/+1.
         */
        addCard(Zone.HAND, playerA, "Nyxborn Rollicker");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        // Instant
        // Far {1}{U} Return target creature to its owner's hand.
        // Away {2}{B} Target player sacrifices a creature.
        // Fuse (You may cast one or both halves of this card from your hand.)
        addCard(Zone.HAND, playerB, "Far // Away");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nyxborn Rollicker using bestow", "Cyclops of One-Eyed Pass");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "fused Far // Away");
        addTarget(playerB, "Cyclops of One-Eyed Pass"); // Far
        addTarget(playerB, playerA); // Away
        setChoice(playerA, "Nyxborn Rollicker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Cyclops of One-Eyed Pass", 1);
        assertHandCount(playerB, 0);

        assertGraveyardCount(playerB, "Far // Away", 1);

        assertPermanentCount(playerA, "Nyxborn Rollicker", 0);
        assertGraveyardCount(playerA, "Nyxborn Rollicker", 1);

    }

    /**
     * Test that CMC of a spell cast with bestowed is correct Disdainful Stroke
     * doesn't check converted mana cost correctly. Opponent was able to use it
     * to counter a Hypnotic Siren cast with Bestow.
     */
    @Test
    public void bestowCheckForCorrectCMC() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // Enchantment Creature — Siren
        // 1/1
        // Bestow {5}{U}{U} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Flying
        // You control enchanted creature.
        // Enchanted creature gets +1/+1 and has flying.
        addCard(Zone.HAND, playerA, "Hypnotic Siren");
        // Instant {1}{U}
        // Counter target spell with converted mana cost 4 or greater.
        addCard(Zone.HAND, playerB, "Disdainful Stroke");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        // B can't cast counter spell due CMC
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hypnotic Siren using bestow", "Silvercoat Lion");
        checkStackSize("after", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 1);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Disdainful Stroke", false);
        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disdainful Stroke", "Hypnotic Siren");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //
        assertHandCount(playerA, "Hypnotic Siren", 0);
        assertGraveyardCount(playerA, "Hypnotic Siren", 0);
        assertHandCount(playerB, "Disdainful Stroke", 1);
        assertPermanentCount(playerA, "Hypnotic Siren", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);

    }

    /**
     *
     */
    @Test
    public void bestowMogissWarhound() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Enchantment Creature — Hound
        // 2/2
        // Bestow 2R (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Mogis's Warhound attacks each turn if able.
        // Enchanted creature gets +2/+2 and attacks each turn if able.
        addCard(Zone.HAND, playerA, "Mogis's Warhound");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // Chandra's Outrage deals 4 damage to target creature and 2 damage to that creature's controller.
        addCard(Zone.HAND, playerB, "Chandra's Outrage");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mogis's Warhound using bestow", "Silvercoat Lion");
        castSpell(1, PhaseStep.END_TURN, playerB, "Chandra's Outrage", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18); // -2 from Chandra's Outrage
        assertLife(playerB, 18); // -2 from attack of Mogis's Warhound
        //
        assertHandCount(playerA, "Mogis's Warhound", 0);
        assertPermanentCount(playerA, "Mogis's Warhound", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

    }

    /*
     * Bug with the Nighthowler Card. Card does sometimes not get a creature
     * after Bestow.

     Steps:
     1) Cast any creature
     2) Cast Nightowler using Bestow on that creature
     3) Make sure creature is tapped
     4) Opponent uses any spell that kills creature which is bestowed by nighthowler
     5) Result: Nighthowler is still on the field, but is no creature (no Power/Toughness)

     Expected:Nighthowler gets on field as a creature

     Important: This happens ONLY if the creature is killed with a spell when
     its TAPPED!

     */
    @Test
    public void bestowNighthowlerTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Instant - {2}{R}{R}
        // Chandra's Outrage deals 4 damage to target creature and 2 damage to that creature's controller.
        addCard(Zone.HAND, playerA, "Chandra's Outrage");

        // Enchantment Creature — Horror
        // 0/0
        // Bestow {2}{B}{B}
        // Nighthowler and enchanted creature each get +X/+X, where X is the number of creature cards in all graveyards.
        addCard(Zone.HAND, playerB, "Nighthowler");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        // First strike
        // Whenever Alesha, Who Smiles at Death attacks, you may pay {W/B}{W/B}. If you do, return target creature card
        // with power 2 or less from your graveyard to the battlefield tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerB, "Alesha, Who Smiles at Death");  // 3/2
        addCard(Zone.GRAVEYARD, playerB, "Pillarfield Ox");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Nighthowler using bestow", "Alesha, Who Smiles at Death");

        // attacks by Alesha and return card on trigger
        attack(2, playerB, "Alesha, Who Smiles at Death");
        setChoice(playerB, true); // use trigger
        addTarget(playerB, "Pillarfield Ox"); // target card to return

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Chandra's Outrage", "Alesha, Who Smiles at Death");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18); // -2 from Chandra's Outrage
        assertLife(playerA, 16); // -3 from attack Alesha with bestowed Nighthowler

        assertGraveyardCount(playerA, "Chandra's Outrage", 1);
        assertGraveyardCount(playerB, "Alesha, Who Smiles at Death", 1);
        assertPermanentCount(playerB, "Nighthowler", 1);
        assertPowerToughness(playerB, "Nighthowler", 2, 2);
        Permanent nighthowler = getPermanent("Nighthowler", playerB);

        Assert.assertEquals("Nighthowler has to be a creature", true, nighthowler.isCreature(currentGame));
    }

    @Test
    public void testSightlessBrawlerCantAttackAloneEnforced() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Enchantment Creature — Human Warrior
        // 3/2
        // Bestow 4W (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Sightless Brawler can't attack alone.
        // Enchanted creature gets +3/+2 and can't attack alone.
        addCard(Zone.BATTLEFIELD, playerA, "Sightless Brawler");

        attack(1, playerA, "Sightless Brawler");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertTapped("Sightless Brawler", false);
    }

    @Test
    public void testSightlessBrawlerAttacksWithOthers() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Enchantment Creature — Human Warrior
        // 3/2
        // Bestow 4W (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Sightless Brawler can't attack alone.
        // Enchanted creature gets +3/+2 and can't attack alone.
        addCard(Zone.BATTLEFIELD, playerA, "Sightless Brawler"); // 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // {W} 2/1 creature

        attack(1, playerA, "Sightless Brawler");
        attack(1, playerA, "Elite Vanguard");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 15);
        assertTapped("Sightless Brawler", true);
    }

    @Test
    public void testSightlessBrawlerBestowedCantAttackAloneEnforced() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Enchantment Creature — Human Warrior
        // 3/2
        // Bestow 4W (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Sightless Brawler can't attack alone.
        // Enchanted creature gets +3/+2 and can't attack alone.
        addCard(Zone.HAND, playerA, "Sightless Brawler");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // {W} 2/1 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sightless Brawler using bestow", "Elite Vanguard");
        attack(1, playerA, "Elite Vanguard");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Sightless Brawler", 0);
        assertLife(playerB, 20);
        assertTapped("Elite Vanguard", false);
        assertPowerToughness(playerA, "Elite Vanguard", 5, 3); // 2/1 + 3/2 = 5/3
    }

    @Test
    public void testSightlessBrawlerBestowedAttacksWithOthers() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Enchantment Creature — Human Warrior
        // 3/2
        // Bestow 4W (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Sightless Brawler can't attack alone.
        // Enchanted creature gets +3/+2 and can't attack alone.
        addCard(Zone.HAND, playerA, "Sightless Brawler");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // {W} 2/1 creature
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // {1} 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sightless Brawler using bestow", "Elite Vanguard");
        attack(1, playerA, "Elite Vanguard");
        attack(1, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Sightless Brawler", 0);
        assertLife(playerB, 14);
        assertTapped("Elite Vanguard", true);
        assertPowerToughness(playerA, "Elite Vanguard", 5, 3); // 2/1 + 3/2 = 5/3
    }

    /**
     * When a creature with Nighthowler attatched gets enchanted with Song of
     * the Dryads, Nightholwer doesn't become a creature and gets turned into a
     * card without stats.
     */
    @Test
    public void testEnchantedChangedWithSongOfTheDryads() {
        // Enchantment Creature — Horror
        // 0/0
        // Bestow {2}{B}{B}
        // Nighthowler and enchanted creature each get +X/+X, where X is the number of creature cards in all graveyards.
        addCard(Zone.HAND, playerA, "Nighthowler");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // {1}{W} 2/2 creature

        addCard(Zone.GRAVEYARD, playerA, "Pillarfield Ox");
        addCard(Zone.GRAVEYARD, playerB, "Pillarfield Ox");

        // Enchant permanent
        // Enchanted permanent is a colorless Forest land.
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        addCard(Zone.HAND, playerB, "Song of the Dryads"); // Enchantment Aura {2}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nighthowler using bestow", "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Song of the Dryads", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Song of the Dryads", 1);

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 1 green mana", "{G}", options.getAtIndex(0).toString());

        assertPermanentCount(playerA, "Nighthowler", 1);
        assertPowerToughness(playerA, "Nighthowler", 2, 2);
        assertType("Nighthowler", CardType.CREATURE, true);
        assertType("Nighthowler", CardType.ENCHANTMENT, true);

        Permanent nighthowler = getPermanent("Nighthowler");
        Assert.assertFalse("The unattached Nighthowler may not have the aura subtype.", nighthowler.hasSubtype(SubType.AURA, currentGame));
    }

    @Test
    public void testCastBestowWithCostReduction() {
        // Enchantment Creature — Horror
        // Bestow {5}{G} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Trample
        // Enchanted creature gets +3/+3 and has trample.
        addCard(Zone.HAND, playerA, "Nylea's Emissary"); // Enchantment Creature
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // {1}{W} 2/2 creature
        // Aura spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Transcendent Envoy", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nylea's Emissary using bestow", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Nylea's Emissary", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 5, 5);
        assertType("Nylea's Emissary", CardType.CREATURE, false);
        assertType("Nylea's Emissary", CardType.ENCHANTMENT, SubType.AURA);
    }

    @Test
    public void testCastBestowFlash() {
        addCard(Zone.HAND, playerA, "Nylea's Emissary"); // +3/+3, only an aura if cast with bestow
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Sigarda's Aid"); //aura spells have flash
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // 1/1

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Nylea's Emissary using bestow", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Nylea's Emissary", 1);
        assertPowerToughness(playerA, "Memnite", 4, 4);
        assertType("Nylea's Emissary", CardType.CREATURE, false);
        assertType("Nylea's Emissary", CardType.ENCHANTMENT, SubType.AURA);
    }

    @Test
    public void testCastBestowFlashRootwaterShaman() {
        addCard(Zone.HAND, playerA, "Nylea's Emissary"); // +3/+3, only an aura if cast with bestow
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Rootwater Shaman"); //aura spells with enchant creature have flash
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // 1/1

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Nylea's Emissary using bestow", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Nylea's Emissary", 1);
        assertPowerToughness(playerA, "Memnite", 4, 4);
        assertType("Nylea's Emissary", CardType.CREATURE, false);
        assertType("Nylea's Emissary", CardType.ENCHANTMENT, SubType.AURA);
    }

    /**
     * Tests that copied bestow works correctly both on the stack and battlefield, including with the creature removed
     */
    @Test
    public void bestowCopiesTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 18);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Overloaded Mage-Ring", 2);
        addCard(Zone.HAND, playerA, "Hopeful Eidolon", 2);
        addCard(Zone.HAND, playerA, "Copy Enchantment");
        addCard(Zone.HAND, playerA, "Mythos of Illuna");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.HAND, playerB, "Lightning Blast");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}, Sacrifice", "Hopeful Eidolon");
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mythos of Illuna", "Hopeful Eidolon"); // Making a token copy of a resolved Bestow aura makes a token creature
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Enchantment"); // Copying a resolved Bestow aura makes a creature
        setChoice(playerA, true);
        setChoice(playerA, "Hopeful Eidolon");
        checkPT("Lion with 2x Hopeful Eidolon attached", 1, PhaseStep.BEGIN_COMBAT, playerA, "Silvercoat Lion", 4, 4);
        checkPermanentCount("Four Hopeful Eidolons resolved", 1, PhaseStep.BEGIN_COMBAT, playerA, "Hopeful Eidolon", 4);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Silvercoat Lion");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}, {T}, Sacrifice", "Hopeful Eidolon");
        setChoice(playerA, false);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Blast", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Hopeful Eidolon", 6);
        assertTokenCount(playerA, "Hopeful Eidolon", 2);

        FilterPermanent filter = new FilterPermanent();
        filter.add(new NamePredicate("Hopeful Eidolon"));
        List<Permanent> eidolons = currentGame.getBattlefield().getAllActivePermanents(filter, currentGame);
        Assert.assertEquals("Six Eidolons found with filter", 6, eidolons.size());
        for (Permanent p : eidolons){
            Assert.assertTrue("Is Enchantment", p.getCardType(currentGame).contains(CardType.ENCHANTMENT));
            Assert.assertFalse("Is not Aura", p.getSubtype(currentGame).contains(SubType.AURA));
            Assert.assertTrue("Is Creature", p.getCardType(currentGame).contains(CardType.CREATURE));
            Assert.assertTrue("Is Spirit", p.getSubtype(currentGame).contains(SubType.SPIRIT));
            Assert.assertEquals("Is 1 power", 1, p.getPower().getValue());
            Assert.assertEquals("Is 1 toughness", 1, p.getToughness().getValue());
        }
    }

    @Test
    public void testBestowSubtype() {
        addCard(Zone.HAND, playerA, "Hopeful Eidolon", 2); // Spirit when cast as creature, not when cast as aura
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Waxmane Baku"); // May add a counter on casting a spirit spell
        addCard(Zone.BATTLEFIELD, playerA, "Drogskol Cavalry"); // +2 life whenever another spirit enters

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Waxmane Baku");
        checkPermanentCounters("Not a Spirit, no KI counters", 1, PhaseStep.BEGIN_COMBAT, playerA, "Waxmane Baku", CounterType.KI, 0);
        checkLife("Not a Spirit, no lifegain", 1, PhaseStep.BEGIN_COMBAT, playerA, 20);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hopeful Eidolon");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hopeful Eidolon", 2);
        assertPowerToughness(playerA, "Waxmane Baku", 3, 3);
        assertCounterCount(playerA, "Waxmane Baku", CounterType.KI, 1);
        assertLife(playerA, 22);
    }

    @Test
    public void testBestowSBATiming() {
        addCard(Zone.HAND, playerA, "Hopeful Eidolon", 2); // +1/+1
        addCard(Zone.HAND, playerA, "Pyroclasm", 2); // 2 damage
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Crusader of Odric");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Eager Cadet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Memnite", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Eager Cadet");
        checkPT("Memnite P/T", 1, PhaseStep.BEGIN_COMBAT, playerA, "Memnite", 2, 2);
        checkPT("Eager Cadet P/T", 1, PhaseStep.BEGIN_COMBAT, playerA, "Eager Cadet", 2, 2);
        checkPT("Crusader P/T", 1, PhaseStep.BEGIN_COMBAT, playerA, "Crusader of Odric", 3, 3);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Pyroclasm");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hopeful Eidolon", 2);
        assertPermanentCount(playerA, "Crusader of Odric", 1);
        assertDamageReceived(playerA, "Crusader of Odric", 2);
        assertGraveyardCount(playerA, 3);
    }

}
