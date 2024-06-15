package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.*;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author NinthWorld
 */
public class MutateTest extends CardTestPlayerBase {
    // Lands
    private static final String ISLAND = "Island";
    private static final String SWAMP = "Swamp";
    private static final String MOUNTAIN = "Mountain";
    private static final String FOREST = "Forest";
    private static final String PLAINS = "Plains";

    // Creatures
    private static final String BROKKOS_APEX_OF_FOREVER = "Brokkos, Apex of Forever"; // {2}{B}{G}{U} - 6/6 - Mutate {2}{U/B}{G}{G} - Trample - You may cast from graveyard using mutate ability.
    private static final String DREAMTAIL_HERON = "Dreamtail Heron"; // {4}{U} - Mutate {3}{U} - 3/4 - Flying - Whenever this creature mutates, draw a card
    private static final String MAJESTIC_AURICORN = "Majestic Auricorn"; // {4}{W} - Mutate {3}{W} - 4/4 - Vigilance - Whenever this creature mutates, you gain 4 life.
    private static final String SEADASHER_OCTOPUS = "Sea-Dasher Octopus"; // {1}{U}{U} - Mutate {1}{U} - Flash
    private static final String BEASTCALLER_SAVANT = "Beastcaller Savant"; // {1}{G} - 1/1 - Haste - {T}: Add mana
    private static final String BANEHOUND = "Banehound"; // {B} - 1/1 - Lifelink, Haste
    private static final String VOLO_GUIDE_TO_MONSTERS = "Volo, Guide to Monsters"; // {2}{G}{U} - Human - 3/2 - Whenever you cast a creature spell that doesn't share a creature type with a creature you control or a creature card in your graveyard, copy that spell. (A copy of a creature spell becomes a token)
    private static final String CLONE = "Clone"; // {3}{U} - 0/0 - Enter as copy of any creature on battlefield.
    private static final String PATHBREAKER_WURM = "Pathbreaker Wurm"; // {4}{G}{G} - 6/4 - Soulbond - As long as ~ is paired with another creature, both creatures have trample.
    private static final String ALLURING_SUITOR = "Alluring Suitor"; // {2}{R} - 2/3 - When you attack with exactly 2 creatures, transform ~. (Into Deadly Dancer)
    private static final String DEADLY_DANCER = "Deadly Dancer"; // 3/3 - Trample - (some other stuff...)
    private static final String CORAL_TRICKSTER = "Coral Trickster"; // {1}{U} - 2/1 - Morph {U} - When its turned face up, you may tap or untap target permanent.
    private static final String HERALD_OF_FAITH = "Herald of Faith"; // {3}{W}{W} - 4/3 - Flying - Whenever ~ attacks, you gain 2 life.
    private static final String FLEECEMANE_LION = "Fleecemane Lion"; // {G}{W} - 3/3 - {3}{G}{W}: Monstrosity 1 - Has hexproof and indestructible when monstrous.
    private static final String GOBLIN_GLORY_CHASER = "Goblin Glory Chaser"; // {R} - 1/1 - Renown 1 - Has menace when renowned.
    private static final String KESSIG_PROWLER = "Kessig Prowler"; // {G} - 2/1 - {4}{G} Transform
    private static final String SINUOUS_PREDATOR = "Sinuous Predator"; // 4/4 - Can't be blocked by more than one creature.
    private static final String MYR_WELDER = "Myr Welder"; // {3} - 1/4 - Imprint {T}: Exile target artifact card from a graveyard. It has all activated abilities of all cards exiled with it.
    private static final String TEFERI_MASTER_OF_TIME = "Teferi, Master of Time"; // {2}{U}{U} - 3 - -3 Target creature you dont control phases out.
    private static final String AKKI_LAVARUNNER = "Akki Lavarunner"; // {3}{R} - 1/1 - Haste - When it deals damage to an opponent, flip it to Tok-Tok, Volcano Born
    private static final String TOK_TOK_VOLCANO_BORN = "Tok-Tok, Volcano Born"; // 2/2 - Protection from red - Some other stuff...
    private static final String CUDGEL_TROLL = "Cudgel Troll"; // {2}{G}{G} - 4/3 - {G}: Regenerate
    private static final String AERIE_OUPHES = "Aerie Ouphes"; // {4}{G} - 3/3 - Persist - Sac: Deal damage equal to its power to target creature w/ flying
    private static final String YOUNG_WOLF = "Young Wolf"; // {G} - 1/1 - Undying
    private static final String GOD_ETERNAL_RHONAS = "God-Eternal Rhonas"; // {3}{G}{G} - 5/5 - ... When {this} dies or is put into exile from the battlefield, you may put it into its owner's library third from the top
    private static final String NIGHTMARE_SHEPHERD = "Nightmare Shepherd"; // {2}{B}{B} - 4/4 - Whenever another nontoken creature you control dies, you may exile it. If you do, creata a token that's a copy of that creature, except it's 1/1 and it's a Nightmare in addition to its other types.
    private static final String ETRATA_THE_SILENCER = "Etrata, the Silencer"; // {2}{U}{B} - 3/5 - Whenever this deals combat damage to a player, exile target creature that player controls and put a hit counter on that card. That player loses the game if they own three or more exiled cards with hit counters on them.
    private static final String INSATIABLE_HEMOPHAGE = "Insatiable Hemophage"; // {3}{B} - 3/3 - Mutate {2}{B} - Deathtouch - Whenever mutates, opponents lose X life and you gain X life where X is the number of times this has mutated.
    private static final String ALMIGHTY_BRUSHWAGG = "Almighty Brushwagg"; // {G} - 1/1 - Trample - {3}{G}: Almighty Brushwagg gets +3/+3 until end of turn.


    // Spells
    private static final String MURDER = "Murder"; // {1}{B}{B} - Destroy target creature
    private static final String ADVENT_OF_THE_WURM = "Advent of the Wurm"; // {1}{G}{G}{W} - Create 5/5 green Wurm token with trample
    private static final String REGRESS = "Regress"; // {2}{U} - Return target permanent to its owner's hand
    private static final String BANISHMENT_DECREE = "Banishment Decree"; // {3}{W}{W}    - Put target creature on top of its owner's library
    private static final String LEAD_ASTRAY = "Lead Astray"; // {1}{W} Tap up to 2 target creatures
    private static final String BACKSLIDE = "Backslide"; // {1}{U} Turn target creature with morph face down. Cycling {U}
    private static final String BRAINGEYSER = "Braingeyser"; // {X}{U}{U} Target player draws X cards
    private static final String SEEDS_OF_STRENGTH = "Seeds of Strength"; // {G}{W} Target creature gets +1/+1 until EOT (x3)

    // Enchantment
    private static final String OBLIVION_RING = "Oblivion Ring"; // {2}{W} - When ~ enters the battlefield, exile target permanent. When ~ leaves the battlefield, return exiled card to battlefield.
    private static final String ALPHA_AUTHORITY = "Alpha Authority"; // {1}{G} - Aura - Enchanted creature has hexproof and ...
    private static final String SHORT_SWORD = "Short Sword"; // {1} - Equip - Equip {1} - Equipped gets +1/+1, When creature transforms, transform this into Ashmouth Blade
    private static final String ASHMOUTH_BLADE = "Ashmouth Blade"; // Equip {3} - Equipped gets +3/+3 and first strike

    // Artifact
    private static final String SOL_RING = "Sol Ring"; // You know what it does....
    private static final String MIMIC_VAT = "Mimic Vat"; // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with this to its owner's graveyard.

    // Tokens
    private static final String WURM = "Wurm Token"; // 5/5 green Wurm token with trample

    // Ability
    private static final String USING_MUTATE = " using Mutate";

    /**
     * Cast spell from hand without using mutate
     */
    @Test
    public void testCastWithoutUsingMutate() {
        setupLands(playerA);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, DREAMTAIL_HERON, 0);
        assertPermanentCount(playerA, BEASTCALLER_SAVANT, 1);
        assertPermanentCount(playerA, DREAMTAIL_HERON, 1);
    }

    /**
     * Mutate spell can't resolve test
     */
    @Test
    public void testMutateCardKilledBeforeResolve() {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, MURDER);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, BEASTCALLER_SAVANT);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, MURDER, BEASTCALLER_SAVANT);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, DREAMTAIL_HERON, 0);
        assertPermanentCount(playerA, BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, DREAMTAIL_HERON, 1);
        assertGraveyardCount(playerA, BEASTCALLER_SAVANT, 1);
    }

    /**
     * Basic mutate tests - Removes from hand, P/T of OVER, Shared Abilities, Keeps Counters, Stays Tapped
     */
    public void setupTestMutateBasic(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        addCard(Zone.HAND, playerA, LEAD_ASTRAY);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }
        addCounters(1, PhaseStep.BEGIN_COMBAT, playerA, withToken ? WURM : BEASTCALLER_SAVANT, CounterType.TIME, 1);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, LEAD_ASTRAY, withToken ? WURM : BEASTCALLER_SAVANT);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, DREAMTAIL_HERON, 0);
        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, mutateUnder ? 1 : 0);
        assertPermanentCount(playerA, DREAMTAIL_HERON, mutateUnder ? 0 : 1);
        String creature = mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON;
        assertAbility(playerA, creature, withToken ? TrampleAbility.getInstance() : HasteAbility.getInstance(), true);
        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, creature, mutateUnder ? withToken ? 5 : 1 : 3, mutateUnder ? withToken ? 5 : 1 : 4);
        assertCounterCount(playerA, creature, CounterType.TIME, 1);
        assertTapped(creature, true);
    }
    @Test
    public void testMutateCardUnderCardBasic() {
        setupTestMutateBasic(false, true);
    }
    @Test
    public void testMutateCardOverCardBasic() {
        setupTestMutateBasic(false, false);
    }
    @Test
    public void testMutateCardUnderTokenBasic() {
        setupTestMutateBasic(true, true);
    }
    @Test
    public void testMutateCardOverTokenBasic() {
        setupTestMutateBasic(true, false);
    }

    /**
     * Change zones tests - Graveyard
     */
    public void setupTestMutateToGraveyard(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, MURDER);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT, 1);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, MURDER);
        addTarget(playerB, mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, DREAMTAIL_HERON, 0);
        assertGraveyardCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertGraveyardCount(playerA, DREAMTAIL_HERON, 1);
    }
    @Test
    public void testMutateCardUnderCardToGraveyard() {
        setupTestMutateToGraveyard(false, true);
    }
    @Test
    public void testMutateCardOverCardToGraveyard() {
        setupTestMutateToGraveyard(false, false);
    }
    @Test
    public void testMutateCardUnderTokenToGraveyard() {
        setupTestMutateToGraveyard(true, true);
    }
    @Test
    public void testMutateCardOverTokenToGraveyard() {
        setupTestMutateToGraveyard(true, false);
    }

    /**
     * Change zones tests - Hand
     */
    public void setupTestMutateToHand(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, REGRESS);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, REGRESS);
        addTarget(playerB, mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, DREAMTAIL_HERON, 0);
        assertHandCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertHandCount(playerA, DREAMTAIL_HERON, 1);
    }
    @Test
    public void testMutateCardUnderCardToHand() {
        setupTestMutateToHand(false, true);
    }
    @Test
    public void testMutateCardOverCardToHand() {
        setupTestMutateToHand(false, false);
    }
    @Test
    public void testMutateCardUnderTokenToHand() {
        setupTestMutateToHand(true, true);
    }
    @Test
    public void testMutateCardOverTokenToHand() {
        setupTestMutateToHand(true, false);
    }

    /**
     * Change zones tests - Library
     */
    public void setupTestMutateToLibrary(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, BANISHMENT_DECREE);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, BANISHMENT_DECREE);
        addTarget(playerB, mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, DREAMTAIL_HERON, 0);
        assertLibraryCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertLibraryCount(playerA, DREAMTAIL_HERON, 1);
    }
    @Test
    public void testMutateCardUnderCardToLibrary() {
        setupTestMutateToLibrary(false, true);
    }
    @Test
    public void testMutateCardOverCardToLibrary() {
        setupTestMutateToLibrary(false, false);
    }
    @Test
    public void testMutateCardUnderTokenToLibrary() {
        setupTestMutateToLibrary(true, true);
    }
    @Test
    public void testMutateCardOverTokenToLibrary() {
        setupTestMutateToLibrary(true, false);
    }

    /**
     * Change zones tests - Exile and back
     */
    public void setupTestMutateToExileAndBack(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, OBLIVION_RING);
        addCard(Zone.HAND, playerB, REGRESS);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, OBLIVION_RING);
        addTarget(playerB, mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, DREAMTAIL_HERON, 0);
        assertExileCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertExileCount(playerA, DREAMTAIL_HERON, 1);

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, REGRESS);
        addTarget(playerB, OBLIVION_RING);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertPermanentCount(playerA, DREAMTAIL_HERON, 1);
        assertExileCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        assertExileCount(playerA, DREAMTAIL_HERON, 0);
    }
    @Test
    public void testMutateCardUnderCardToExileAndBack() {
        setupTestMutateToExileAndBack(false, true);
    }
    @Test
    public void testMutateCardOverCardToExileAndBack() {
        setupTestMutateToExileAndBack(false, false);
    }
    @Test
    public void testMutateCardUnderTokenToExileAndBack() {
        setupTestMutateToExileAndBack(true, false);
    }
    @Test
    public void testMutateCardOverTokenToExileAndBack() {
        setupTestMutateToExileAndBack(true, true);
    }

    /**
     * SourceMutatedCount test
     */
    public void setupTestMutateCount(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.HAND, playerA, INSATIABLE_HEMOPHAGE);
        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, INSATIABLE_HEMOPHAGE + USING_MUTATE, mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON);
        setChoice(playerA, mutateUnder);
        setChoice(playerA, "Whenever this creature mutates, draw a card");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : INSATIABLE_HEMOPHAGE, 1);
        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }
    @Test
    public void testMutateCountCardUnderCard() {
        setupTestMutateCount(false, true);
    }
    @Test
    public void testMutateCountCardOverCard() {
        setupTestMutateCount(false, false);
    }
    @Test
    public void testMutateCountCardUnderToken() {
        setupTestMutateCount(true, true);
    }
    @Test
    public void testMutateCountCardOverToken() {
        setupTestMutateCount(true, false);
    }

    /**
     * Clone mutate tests - Check if the clone of a mutated creature is still valid after the original is killed.
     * Also checks if the mutated clone is sent to the graveyard correctly.
     */
    public void setupTestMutateClone(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, CLONE);

        addCard(Zone.HAND, playerA, MURDER, 2);
        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        String creature = mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON;

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, CLONE);
        setChoice(playerB, true);
        setChoice(playerB, creature);

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, MURDER);
        addTarget(playerA, creature + "[no copy]");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, DREAMTAIL_HERON, 0);
        assertPermanentCount(playerA, creature, 0);
        assertGraveyardCount(playerA, DREAMTAIL_HERON, 1);
        if (!withToken) assertGraveyardCount(playerA, BEASTCALLER_SAVANT, 1);

        assertPermanentCount(playerB, creature, 1);
        assertAbility(playerB, creature, withToken ? TrampleAbility.getInstance() : HasteAbility.getInstance(), true);
        assertAbility(playerB, creature, FlyingAbility.getInstance(), true);
        assertPowerToughness(playerB, creature, mutateUnder ? withToken ? 5 : 1 : 3, mutateUnder ? withToken ? 5 : 1 : 4);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER);
        addTarget(playerA, creature);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, creature, 0);
        assertGraveyardCount(playerB, DREAMTAIL_HERON, 0);
        assertGraveyardCount(playerB, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        assertGraveyardCount(playerB, CLONE, 1);
    }
    @Test
    public void testMutateCardUnderCardClone() {
        setupTestMutateClone(false, true);
    }
    @Test
    public void testMutateCardOverCardClone() {
        setupTestMutateClone(false, false);
    }
    @Test
    public void testMutateCardUnderTokenClone() {
        setupTestMutateClone(true, true);
    }
    @Test
    public void testMutateCardOverTokenClone() {
        setupTestMutateClone(true, false);
    }

    /**
     * Triggered ability mutate test
     */
    public void setupTestMutateTriggered(boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        addCard(Zone.BATTLEFIELD, playerA, HERALD_OF_FAITH);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, HERALD_OF_FAITH);
        setChoice(playerA, mutateUnder);

        attack(3, playerA, mutateUnder ? HERALD_OF_FAITH : DREAMTAIL_HERON);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 22);
    }
    @Test
    public void testMutateCardUnderCardTriggered() {
        setupTestMutateTriggered(true);
    }
    @Test
    public void testMutateCardOverCardTriggered() {
        setupTestMutateTriggered(false);
    }

//    /**
//     * Copy spell mutate test
//     */
//    public void setupTestMutateCopySpell(boolean withToken, boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerA, VOLO_GUIDE_TO_MONSTERS);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        if (withToken) {
//            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
//                    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
//        } else {
//            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
//        }
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
//
//        setChoice(playerA, false); // TODO: This might be a bug with Volo, as you shouldn't be asked if you want to choose a new target.
//
//        setChoice(playerA, mutateUnder);
//        setChoice(playerA, mutateUnder);
//
//        setChoice(playerA, "Whenever this creature mutates, draw a card."); // Choose which ability triggers first (they are identical)
//
//        setStopAt(1, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertHandCount(playerA, 3);
//    }
//    @Test
//    public void testMutateCardUnderCardCopySpell() {
//        setupTestMutateCopySpell(false, true);
//    }
//    @Test
//    public void testMutateCardOverCardCopySpell() {
//        setupTestMutateCopySpell(false, false);
//    }
//    @Test
//    public void testMutateCardUnderTokenCopySpell() {
//        setupTestMutateCopySpell(true, true);
//    }
//    @Test
//    public void testMutateCardOverTokenCopySpell() {
//        setupTestMutateCopySpell(true, false);
//    }

//    /**
//     * Transform mutate test
//     */
//    public void setupTestMutateTransform(boolean transformed, boolean mutateUnder) {
//        addCard(Zone.BATTLEFIELD, playerA, ALLURING_SUITOR);
//        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        addCard(Zone.BATTLEFIELD, playerA, ISLAND, 4);
//
//        if (!transformed) {
//            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, ALLURING_SUITOR);
//            setChoice(playerA, mutateUnder);
//        }
//
//        attack(1, playerA, transformed || mutateUnder ? ALLURING_SUITOR : DREAMTAIL_HERON);
//        attack(1, playerA, BEASTCALLER_SAVANT);
//
//        if (transformed) {
//            castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, DEADLY_DANCER);
//            setChoice(playerA, mutateUnder);
//        }
//
//        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        String creature = mutateUnder ? DEADLY_DANCER : DREAMTAIL_HERON;
//        assertPermanentCount(playerA, creature, 1);
//        assertAbility(playerA, creature, TrampleAbility.getInstance(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertPowerToughness(playerA, creature, 3, mutateUnder ? 3 : 4);
//
//        if (transformed) {
//            assertTappedCount(ISLAND, true, 2);
//        } else {
//            assertManaPool(playerA, ManaType.RED, 2);
//        }
//    }
//    @Test
//    public void testMutateCardUnderCardTransform() {
//        setupTestMutateTransform(false, true);
//    }
//    @Test
//    public void testMutateCardOverCardTransform() {
//        setupTestMutateTransform(false, false);
//    }
//    @Test
//    public void testMutateCardUnderTransformedCardTransform() {
//        setupTestMutateTransform(true, true);
//    }
//    @Test
//    public void testMutateCardOverTransformedCardTransform() {
//        setupTestMutateTransform(true, false);
//    }
//
//    /**
//     * Imprint mutate test
//     */
//    public void setupTestMutateImprint(boolean imprinted, boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.GRAVEYARD, playerA, SOL_RING);
//        addCard(Zone.BATTLEFIELD, playerA, MYR_WELDER);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//
//        if (imprinted) {
//            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Imprint</i>", SOL_RING);
//        }
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, MYR_WELDER);
//        setChoice(playerA, mutateUnder);
//
//        if (!imprinted) {
//            activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Imprint</i>", SOL_RING);
//        }
//
//        setStopAt(3, PhaseStep.BEGIN_COMBAT);
//        setStrictChooseMode(true);
//        execute();
//
//        String creature = mutateUnder ? MYR_WELDER : DREAMTAIL_HERON;
//        assertPermanentCount(playerA, creature, 1);
//        assertAbility(playerA, creature, new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()), true);
//    }
//    @Test
//    public void testMutateCardUnderCardImprint() {
//        setupTestMutateImprint(false, true);
//    }
//    @Test
//    public void testMutateCardOverCardImprint() {
//        setupTestMutateImprint(false, false);
//    }
//    @Test
//    public void testMutateCardUnderImprintedCardImprint() {
//        setupTestMutateImprint(true, true);
//    }
//    @Test
//    public void testMutateCardOverImprintedCardImprint() {
//        setupTestMutateImprint(true, false);
//    }
//
//    /**
//     * Phasing mutate test
//     */
//    public void setupTestMutatePhasing(boolean withToken, boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerB, TEFERI_MASTER_OF_TIME);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        addCard(Zone.BATTLEFIELD, playerA, NEGLECTED_HEIRLOOM);
//        if (withToken) {
//            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
//                    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
//        } else {
//            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
//        }
//
//        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip {1}", withToken ? WURM : BEASTCALLER_SAVANT);
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
//        setChoice(playerA, mutateUnder);
//
//        setStopAt(1, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        String creature = mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : DREAMTAIL_HERON;
//        assertPermanentCount(playerA, creature, 1);
//        assertPermanentCount(playerA, NEGLECTED_HEIRLOOM, 1);
//        assertAbility(playerA, creature, withToken ? TrampleAbility.getInstance() : HasteAbility.getInstance(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertPowerToughness(playerA, creature, mutateUnder ? withToken ? 6 : 2 : 4, mutateUnder ? withToken ? 6 : 2 : 5);
//
//        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "-3: Target creature you don't control phases out.", creature);
//
//        setStopAt(2, PhaseStep.BEGIN_COMBAT);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, creature, 0);
//        assertPermanentCount(playerA, NEGLECTED_HEIRLOOM, 0);
//
//        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, creature, 1);
//        assertPermanentCount(playerA, NEGLECTED_HEIRLOOM, 1);
//        assertAbility(playerA, creature, withToken ? TrampleAbility.getInstance() : HasteAbility.getInstance(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertPowerToughness(playerA, creature, mutateUnder ? withToken ? 6 : 2 : 4, mutateUnder ? withToken ? 6 : 2 : 5);
//    }
//    @Test
//    public void testMutateCardUnderCardPhasing() {
//        setupTestMutatePhasing(false, true);
//    }
//    @Test
//    public void testMutateCardOverCardPhasing() {
//        setupTestMutatePhasing(false, false);
//    }
//    @Test
//    public void testMutateCardUnderTokenPhasing() {
//        setupTestMutatePhasing(true, true);
//    }
//    @Test
//    public void testMutateCardOverTokenPhasing() {
//        setupTestMutatePhasing(true, false);
//    }
//
//    /**
//     * Morph mutate test
//     */
//    public void setupTestMutateMorph(boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.HAND, playerA, CORAL_TRICKSTER);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CORAL_TRICKSTER);
//        setChoice(playerA, true);
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, "");
//        setChoice(playerA, mutateUnder);
//
//        setStopAt(1, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        if (mutateUnder) {
//            assertPermanentCount(playerA, "", 1);
//            assertAbility(playerA, "", FlyingAbility.getInstance(), false);
//            assertPowerToughness(playerA, "", 2, 2);
//
//            activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}: Turn this");
//            addTarget(playerA, ISLAND);
//            setChoice(playerA, true);
//
//            setStopAt(3, PhaseStep.BEGIN_COMBAT);
//            setStrictChooseMode(true);
//            execute();
//
//            assertPermanentCount(playerA, CORAL_TRICKSTER, 1);
//            assertAbility(playerA, CORAL_TRICKSTER, FlyingAbility.getInstance(), true);
//        } else {
//            assertPermanentCount(playerA, DREAMTAIL_HERON, 1);
//            //assertAbility(playerA, DREAMTAIL_HERON, new MorphAbility(new ManaCostsImpl("{U}")), false);
//            assertPowerToughness(playerA, DREAMTAIL_HERON, 3, 4);
//        }
//    }
//    @Test
//    public void testMutateCardUnderCardMorph() {
//        setupTestMutateMorph(true);
//    }
//    @Test
//    public void testMutateCardOverCardMorph() {
//        setupTestMutateMorph(false);
//    }
//
//    /**
//     * Soulbond mutate test
//     */
//    public void setupTestMutateSoulbond(boolean soulbond, boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerA, BANEHOUND);
//        addCard(Zone.HAND, playerA, PATHBREAKER_WURM);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PATHBREAKER_WURM);
//        setChoice(playerA, true);
//        setChoice(playerA, BANEHOUND);
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, soulbond ? PATHBREAKER_WURM : BANEHOUND);
//        setChoice(playerA, mutateUnder);
//
//        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        String creature = mutateUnder ? soulbond ? PATHBREAKER_WURM : BANEHOUND : DREAMTAIL_HERON;
//        assertAbility(playerA, !soulbond ? PATHBREAKER_WURM : BANEHOUND, TrampleAbility.getInstance(), true);
//        assertAbility(playerA, creature, TrampleAbility.getInstance(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//    }
//    @Test
//    public void testMutateCardUnderCardSoulbond() {
//        setupTestMutateSoulbond(false, true);
//    }
//    @Test
//    public void testMutateCardOverCardSoulbond() {
//        setupTestMutateSoulbond(false, false);
//    }
//    @Test
//    public void testMutateCardUnderSoulbondCardSoulbond() {
//        setupTestMutateSoulbond(true, true);
//    }
//    @Test
//    public void testMutateCardOverSoulbondCardSoulbond() {
//        setupTestMutateSoulbond(true, false);
//    }

//    /**
//     * Monstrous mutate test
//     */
//    public void setupTestMutateMonstrous(boolean monstrous, boolean mutateUnder) {
//        setupLands(playerA);
//
//        final Ability monstrousAbility = new MonstrosityAbility("{3}{G}{W}", 1);
//
//        addCard(Zone.BATTLEFIELD, playerA, FLEECEMANE_LION);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//
//        if (monstrous) {
//            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, monstrousAbility.getRule());
//        }
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, FLEECEMANE_LION);
//        setChoice(playerA, mutateUnder);
//
//        if (!monstrous) {
//            activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, monstrousAbility.getRule());
//        }
//
//        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        String creature = mutateUnder ? FLEECEMANE_LION : DREAMTAIL_HERON;
//        assertAbility(playerA, creature, HexproofAbility.getInstance(), true);
//        assertAbility(playerA, creature, IndestructibleAbility.getInstance(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertCounterCount(playerA, creature, CounterType.P1P1, 1);
//
//        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, monstrousAbility.getRule());
//        setStopAt(3, PhaseStep.BEGIN_COMBAT);
//        setStrictChooseMode(false);
//        execute();
//
//        assertCounterCount(playerA, creature, CounterType.P1P1, 1);
//    }
//    @Test
//    public void testMutateCardUnderCardMonstrous() {
//        setupTestMutateMonstrous(false, true);
//    }
//    @Test
//    public void testMutateCardOverCardMonstrous() {
//        setupTestMutateMonstrous(false, false);
//    }
//    @Test
//    public void testMutateCardUnderMonstrousCardMonstrous() {
//        setupTestMutateMonstrous(true, true);
//    }
//    @Test
//    public void testMutateCardOverMonstrousCardMonstrous() {
//        setupTestMutateMonstrous(true, false);
//    }
//
//    /**
//     * Renowned mutate test
//     */
//    public void setupTestMutateRenown(boolean renowned, boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerA, GOBLIN_GLORY_CHASER);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//
//        if (renowned) {
//            attack(1, playerA, GOBLIN_GLORY_CHASER);
//        }
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, GOBLIN_GLORY_CHASER);
//        setChoice(playerA, mutateUnder);
//
//        attack(3, playerA, mutateUnder ? GOBLIN_GLORY_CHASER : DREAMTAIL_HERON);
//
//        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        String creature = mutateUnder ? GOBLIN_GLORY_CHASER : DREAMTAIL_HERON;
//        assertAbility(playerA, creature, new MenaceAbility(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertCounterCount(playerA, creature, CounterType.P1P1, 1);
//    }
//    @Test
//    public void testMutateCardUnderCardRenown() {
//        setupTestMutateRenown(false, true);
//    }
//    @Test
//    public void testMutateCardOverCardRenown() {
//        setupTestMutateRenown(false, false);
//    }
//    @Test
//    public void testMutateCardUnderRenownedCardRenown() {
//        setupTestMutateRenown(true, true);
//    }
//    @Test
//    public void testMutateCardOverRenownedCardRenown() {
//        setupTestMutateRenown(true, false);
//    }

    /**
     * Attachments (aura, equip) mutate test
     */
    public void setupTestMutateAttachment(boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.BATTLEFIELD, playerA, KESSIG_PROWLER);
        addCard(Zone.BATTLEFIELD, playerA, SHORT_SWORD);
        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
        addCard(Zone.HAND, playerA, ALPHA_AUTHORITY);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ALPHA_AUTHORITY, KESSIG_PROWLER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", KESSIG_PROWLER);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, KESSIG_PROWLER);
        setChoice(playerA, mutateUnder);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{G}: Transform");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        String creature = mutateUnder ? SINUOUS_PREDATOR : DREAMTAIL_HERON;
        assertPermanentCount(playerA, ALPHA_AUTHORITY, 1);
        assertPermanentCount(playerA, SHORT_SWORD, 1);
        assertPermanentCount(playerA, creature, 1);
        assertAbility(playerA, creature, HexproofAbility.getInstance(), true);
        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, creature, mutateUnder ? 5 : 4, 5);
    }
    @Test
    public void testMutateCardUnderCardAttachment() {
        setupTestMutateAttachment(true);
    }
    @Test
    public void testMutateCardOverCardAttachment() {
        setupTestMutateAttachment(false);
    }

    /**
     * test for continuous effects that use the source permanent
     */
    public void setupTestMutateBoostedSource(boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.BATTLEFIELD, playerA, ALMIGHTY_BRUSHWAGG);
        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{G}: ");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, ALMIGHTY_BRUSHWAGG);
        setChoice(playerA, mutateUnder);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        String creature = mutateUnder ? ALMIGHTY_BRUSHWAGG : DREAMTAIL_HERON;
        assertPermanentCount(playerA, creature, 1);
        assertAbility(playerA, creature, TrampleAbility.getInstance(), true);
        assertPowerToughness(playerA, creature, 3 + (mutateUnder ? 1 : 3), 3 + (mutateUnder ? 1 : 4));
    }
    @Test
    public void testMutateCardUnderCardBoostedSource() {
        setupTestMutateBoostedSource(true);
    }
    @Test
    public void testMutateCardOverCardBoostedSource() {
        setupTestMutateBoostedSource(false);
    }

//    /**
//     * Flip mutate test
//     */
//    public void setupTestMutateFlip(boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerA, AKKI_LAVARUNNER);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, AKKI_LAVARUNNER);
//        setChoice(playerA, mutateUnder);
//
//        setStopAt(1, PhaseStep.BEGIN_COMBAT);
//        setStrictChooseMode(true);
//        execute();
//
//        String creature = mutateUnder ? AKKI_LAVARUNNER : DREAMTAIL_HERON;
//        assertPermanentCount(playerA, creature, 1);
//        assertAbility(playerA, creature, HasteAbility.getInstance(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertPowerToughness(playerA, creature, mutateUnder ? 1 : 3, mutateUnder ? 1 : 4);
//
//        attack(1, playerA, creature, playerB);
//
//        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        creature = mutateUnder ? TOK_TOK_VOLCANO_BORN : DREAMTAIL_HERON;
//        assertPermanentCount(playerA, creature, 1);
//        assertAbility(playerA, creature, HasteAbility.getInstance(), false);
//        assertAbility(playerA, creature, ProtectionAbility.from(ObjectColor.RED), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertPowerToughness(playerA, creature, mutateUnder ? 2 : 3, mutateUnder ? 2 : 4);
//    }
//    @Test
//    public void testMutateCardUnderCardFlip() {
//        setupTestMutateFlip(true);
//    }
//    @Test
//    public void testMutateCardOverCardFlip() {
//        setupTestMutateFlip(false);
//    }
//
//    /**
//     * Regenerate mutate test
//     */
//    public void setupTestMutateRegenerate(boolean mutateUnder) {
//        setupLands(playerA);
//        setupLands(playerB);
//
//        addCard(Zone.HAND, playerB, MURDER);
//
//        addCard(Zone.BATTLEFIELD, playerA, CUDGEL_TROLL);
//        addCard(Zone.BATTLEFIELD, playerA, FOREST);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//
//        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate");
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, CUDGEL_TROLL);
//        setChoice(playerA, mutateUnder);
//
//        String creature = mutateUnder ? CUDGEL_TROLL : DREAMTAIL_HERON;
//
//        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, MURDER, creature);
//
//        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, DREAMTAIL_HERON, mutateUnder ? 0 : 1);
//        assertPermanentCount(playerA, CUDGEL_TROLL, mutateUnder ? 1 : 0);
//        assertGraveyardCount(playerA, 0);
//    }
//    @Test
//    public void testMutateCardUnderRegeneratedCard() {
//        setupTestMutateRegenerate(true);
//    }
//    @Test
//    public void testMutateCardOverRegeneratedCard() {
//        setupTestMutateRegenerate(false);
//    }

//    /**
//     * Persist mutate test
//     */
//    public void setupTestMutatePersist(boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerB, DREAMTAIL_HERON);
//
//        addCard(Zone.BATTLEFIELD, playerA, AERIE_OUPHES);
//        addCard(Zone.HAND, playerA, BROKKOS_APEX_OF_FOREVER);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BROKKOS_APEX_OF_FOREVER + USING_MUTATE, AERIE_OUPHES);
//        setChoice(playerA, mutateUnder);
//
//        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice");
//        addTarget(playerA, DREAMTAIL_HERON);
//
//        setStopAt(1, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, BROKKOS_APEX_OF_FOREVER, 1);
//        assertCounterCount(playerA, BROKKOS_APEX_OF_FOREVER, CounterType.M1M1, 1);
//        assertPermanentCount(playerA, AERIE_OUPHES, 1);
//        assertCounterCount(playerA, AERIE_OUPHES, CounterType.M1M1, 1);
//
//        assertPermanentCount(playerB, DREAMTAIL_HERON, mutateUnder ? 1 : 0);
//    }
//    @Test
//    public void testMutateCardUnderPersistCard() {
//        setupTestMutatePersist(true);
//    }
//    @Test
//    public void testMutateCardOverPersistCard() {
//        setupTestMutatePersist(false);
//    }
//
//    /**
//     * Undying mutate test
//     */
//    public void setupTestMutateUndying(boolean mutateUnder) {
//        setupLands(playerA);
//        setupLands(playerB);
//
//        addCard(Zone.HAND, playerB, MURDER);
//
//        addCard(Zone.BATTLEFIELD, playerA, YOUNG_WOLF);
//        addCard(Zone.HAND, playerA, BROKKOS_APEX_OF_FOREVER);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BROKKOS_APEX_OF_FOREVER + USING_MUTATE, YOUNG_WOLF);
//        setChoice(playerA, mutateUnder);
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, MURDER, mutateUnder ? YOUNG_WOLF : BROKKOS_APEX_OF_FOREVER);
//
//        setStopAt(1, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, BROKKOS_APEX_OF_FOREVER, 1);
//        assertCounterCount(playerA, BROKKOS_APEX_OF_FOREVER, CounterType.P1P1, 1);
//        assertPermanentCount(playerA, YOUNG_WOLF, 1);
//        assertCounterCount(playerA, YOUNG_WOLF, CounterType.P1P1, 1);
//    }
//    @Test
//    public void testMutateCardUnderUndyingCard() {
//        setupTestMutateUndying(true);
//    }
//    @Test
//    public void testMutateCardOverUndyingCard() {
//        setupTestMutateUndying(false);
//    }

    /**
     * Brokkos, Apex of Forever mutate test
     */
    public void setupTestMutateBrokkosApexOfForever(boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        addCard(Zone.GRAVEYARD, playerA, BROKKOS_APEX_OF_FOREVER);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BROKKOS_APEX_OF_FOREVER + USING_MUTATE, BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, BROKKOS_APEX_OF_FOREVER, 0);
        assertPermanentCount(playerA, mutateUnder ? BEASTCALLER_SAVANT : BROKKOS_APEX_OF_FOREVER, 1);
    }
    @Test
    public void testMutateBrokkosApexOfForeverUnderCard() {
        setupTestMutateBrokkosApexOfForever(true);
    }
    @Test
    public void testMutateBrokkosApexOfForeverOverCard() {
        setupTestMutateBrokkosApexOfForever(false);
    }

    /**
     * Spell targeting the same creature multiple times on the stack before mutate over test
     * Continuous effect retained after mutate over test
     */
    public void setupTestSpellMultipleSameTargetMutateOver(boolean spellOnStack) {
        setupLands(playerA);

        addCard(Zone.HAND, playerA, SEADASHER_OCTOPUS);
        addCard(Zone.HAND, playerA, SEEDS_OF_STRENGTH);

        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SEEDS_OF_STRENGTH);
        addTarget(playerA, BEASTCALLER_SAVANT);
        addTarget(playerA, BEASTCALLER_SAVANT);
        addTarget(playerA, BEASTCALLER_SAVANT);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SEADASHER_OCTOPUS + USING_MUTATE, BEASTCALLER_SAVANT, SEEDS_OF_STRENGTH, spellOnStack ? StackClause.WHILE_ON_STACK : StackClause.WHILE_NOT_ON_STACK);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, SEADASHER_OCTOPUS, 5, 5);
    }
    @Test
    public void testContinuousEffectAfterMutateOver() {
        setupTestSpellMultipleSameTargetMutateOver(false);
    }
    @Test
    public void testSpellMultipleSameTargetOnStackMutateOver() {
        setupTestSpellMultipleSameTargetMutateOver(true);
    }

//    /**
//     * Q: What happens if I turn a mutated pile face down?
//     * A: All the cards turn face down. If any of them have a morph ability, you can use it to turn all of them face up.
//     */
//    public void setupTestMutateFaceDown(boolean mutateUnder) {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerA, CORAL_TRICKSTER);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        addCard(Zone.HAND, playerA, BACKSLIDE);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, CORAL_TRICKSTER);
//        setChoice(playerA, mutateUnder);
//
//        String creature = mutateUnder ? CORAL_TRICKSTER : DREAMTAIL_HERON;
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, BACKSLIDE, creature);
//
//        setStopAt(1, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, "", 1);
//        assertAbility(playerA, "", FlyingAbility.getInstance(), false);
//        assertPowerToughness(playerA, "", 2, 2);
//
//        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}: Turn this");
//        addTarget(playerA, ISLAND);
//        setChoice(playerA, true);
//
//        setStopAt(3, PhaseStep.BEGIN_COMBAT);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, creature, 1);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        //assertAbility(playerA, creature, new MorphAbility(new ManaCostsImpl("{U}")), true);
//        assertPowerToughness(playerA, creature, mutateUnder ? 2 : 3, mutateUnder ? 1 : 4);
//    }
//    @Test
//    public void testMutateFaceDownUnderCard() {
//        setupTestMutateFaceDown(true);
//    }
//    @Test
//    public void testMutateFaceDownOverCard() {
//        setupTestMutateFaceDown(false);
//    }
//
//    /**
//     * Q: What happens if a mutated pile that contains a God-Eternal dies?
//     * A: All the creature cards will be put under the second card from the top of your deck in the order of your choosing.
//     */
//    public void setupTestMutateGodEternal(boolean mutateUnder) {
//        setupLands(playerA);
//        setupLands(playerB);
//
//        addCard(Zone.HAND, playerB, MURDER);
//
//        addCard(Zone.BATTLEFIELD, playerA, GOD_ETERNAL_RHONAS);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        addCard(Zone.HAND, playerA, BRAINGEYSER);
//        addCard(Zone.BATTLEFIELD, playerA, ISLAND, 20);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, GOD_ETERNAL_RHONAS);
//        setChoice(playerA, mutateUnder);
//
//        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, MURDER, mutateUnder ? GOD_ETERNAL_RHONAS : DREAMTAIL_HERON);
//        setChoice(playerA, true);
//
//        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertPermanentCount(playerA, mutateUnder ? GOD_ETERNAL_RHONAS : DREAMTAIL_HERON, 0);
//        assertLibraryCount(playerA, GOD_ETERNAL_RHONAS, 1);
//        assertLibraryCount(playerA, DREAMTAIL_HERON, 1);
//
//        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, BRAINGEYSER, playerA);
//        setChoiceAmount(playerA, 5);
//
//        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertHandCount(playerA, GOD_ETERNAL_RHONAS, 1);
//        assertHandCount(playerA, DREAMTAIL_HERON, 1);
//    }
//    @Test
//    public void testMutateGodEternalUnderCard() {
//        setupTestMutateGodEternal(true);
//    }
//    @Test
//    public void testMutateGodEternalOverCard() {
//        setupTestMutateGodEternal(false);
//    }
//
//    /**
//     * Q: How does a mutated pile work with Nightmare Shepherd?
//     * A: You get one token that's a copy of the mutated creature that died - but only if you exile each component of it from your graveyard.
//     */
//    public void setupTestMutateNightmareShepherd(boolean mutateUnder) {
//        setupLands(playerA);
//        setupLands(playerB);
//
//        addCard(Zone.HAND, playerB, MURDER);
//
//        addCard(Zone.BATTLEFIELD, playerA, NIGHTMARE_SHEPHERD);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT, 1);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, BEASTCALLER_SAVANT);
//        setChoice(playerA, mutateUnder);
//
//        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, MURDER);
//        addTarget(playerB, mutateUnder ? BEASTCALLER_SAVANT : DREAMTAIL_HERON);
//        setChoice(playerA, true); // Choose to exile
//
//        setStopAt(1, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertGraveyardCount(playerA, DREAMTAIL_HERON, 0);
//        assertGraveyardCount(playerA, BEASTCALLER_SAVANT, 0);
//        assertExileCount(playerA, DREAMTAIL_HERON, 1);
//        assertExileCount(playerA, BEASTCALLER_SAVANT, 1);
//
//        String creature = mutateUnder ? BEASTCALLER_SAVANT : DREAMTAIL_HERON;
//        assertPermanentCount(playerA, creature, 1);
//        assertAbility(playerA, creature, HasteAbility.getInstance(), true);
//        assertAbility(playerA, creature, FlyingAbility.getInstance(), true);
//        assertSubtype(creature, SubType.NIGHTMARE);
//        assertPowerToughness(playerA, creature, 1, 1);
//    }
//    @Test
//    public void testMutateNightmareShepherdUnderCard() {
//        setupTestMutateNightmareShepherd(true);
//    }
//    @Test
//    public void testMutateNightmareShepherdOverCard() {
//        setupTestMutateNightmareShepherd(false);
//    }
//
//    /**
//     * Q: What happens if you use Etrata's ability to exile a mutated pile?
//     * A: Each creature card is exiled with a hit counter.
//     */
//    @Test
//    public void testMutateEtrataExile() {
//        setupLands(playerA);
//
//        addCard(Zone.BATTLEFIELD, playerB, ETRATA_THE_SILENCER);
//
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        addCard(Zone.HAND, playerA, MAJESTIC_AURICORN);
//        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT, 1);
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, BEASTCALLER_SAVANT);
//        setChoice(playerA, true);
//
//        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, MAJESTIC_AURICORN + USING_MUTATE, BEASTCALLER_SAVANT);
//        setChoice(playerA, true);
//        setChoice(playerA, "Whenever this creature mutates, draw a card");
//
//        attack(2, playerB, ETRATA_THE_SILENCER, playerA);
//        addTarget(playerB, BEASTCALLER_SAVANT);
//
//        setStopAt(2, PhaseStep.END_TURN);
//        setStrictChooseMode(true);
//        execute();
//
//        // PlayerA should have 3 exiled cards with hit counters on them, losing the game
//        assertWonTheGame(playerB);
//    }
//
//    /**
//     * Q: What happens when a mutated creature dies and there's a Mimic Vat on the board?
//     * A: All the cards get imprinted together, but when you activate Mimic Vat you choose only one card to create a copy of.
//     */
//    public void setupTestMutateMimicVat(boolean withToken, boolean mutateUnder, boolean mimicUnder) {
//        setupLands(playerA);
//        setupLands(playerB);
//
//        addCard(Zone.HAND, playerB, MURDER);
//
//        addCard(Zone.BATTLEFIELD, playerA, MIMIC_VAT);
//        addCard(Zone.HAND, playerA, DREAMTAIL_HERON);
//        if (withToken) {
//            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
//            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
//        } else {
//            addCard(Zone.BATTLEFIELD, playerA, BANEHOUND, 1);
//        }
//
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DREAMTAIL_HERON + USING_MUTATE, withToken ? WURM : BANEHOUND);
//        setChoice(playerA, mutateUnder);
//
//        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, MURDER);
//        addTarget(playerB, mutateUnder ? withToken ? WURM : BANEHOUND : DREAMTAIL_HERON);
//        setChoice(playerA, true);
//
//        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3}, {T}");
//        final String mimic = withToken ? DREAMTAIL_HERON : mimicUnder && mutateUnder ? BANEHOUND : DREAMTAIL_HERON;
//        if (!withToken) {
//            addTarget(playerA, mimic);
//        }
//
//        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
//        setStrictChooseMode(true);
//        execute();
//
//        assertGraveyardCount(playerA, DREAMTAIL_HERON, 0);
//        assertExileCount(playerA, DREAMTAIL_HERON, 1);
//        if (!withToken) {
//            assertGraveyardCount(playerA, BANEHOUND, 0);
//            assertExileCount(playerA, BANEHOUND, 1);
//        }
//
//        assertPermanentCount(playerA, mimic, 1);
//        assertAbility(playerA, mimic, HasteAbility.getInstance(), true);
//        assertAbility(playerA, mimic, withToken ? TrampleAbility.getInstance() : mimicUnder && mutateUnder ? FlyingAbility.getInstance() : LifelinkAbility.getInstance(), false);
//    }
//    @Test
//    public void testMutateMimicVatCardUnderCardMimicUnder() {
//        setupTestMutateMimicVat(false, true, true);
//    }
//    @Test
//    public void testMutateMimicVatCardOverCardMimicUnder() {
//        setupTestMutateMimicVat(false, false, false);
//    }
//    @Test
//    public void testMutateMimicVatCardUnderCardMimicOver() {
//        setupTestMutateMimicVat(false, true, true);
//    }
//    @Test
//    public void testMutateMimicVatCardOverCardMimicOver() {
//        setupTestMutateMimicVat(false, false, false);
//    }
//    @Test
//    public void testMutateMimicVatCardUnderToken() {
//        setupTestMutateMimicVat(true, true, false);
//    }
//    @Test
//    public void testMutateMimicVatCardOverToken() {
//        setupTestMutateMimicVat(true, false, false);
//    }

    private void setupLands(TestPlayer player) {
        addCard(Zone.BATTLEFIELD, player, ISLAND, 20);
        addCard(Zone.BATTLEFIELD, player, PLAINS, 20);
        addCard(Zone.BATTLEFIELD, player, FOREST, 20);
        addCard(Zone.BATTLEFIELD, player, MOUNTAIN, 20);
        addCard(Zone.BATTLEFIELD, player, SWAMP, 20);
    }
}
