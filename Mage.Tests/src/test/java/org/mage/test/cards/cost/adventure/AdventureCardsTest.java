package org.mage.test.cards.cost.adventure;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AdventureCardsTest extends CardTestPlayerBase {

    String abilityBrazenBorrowerMainCast = "Cast Brazen Borrower";
    String abilityBrazenBorrowerAdventureCast = "Cast Petty Theft";

    @Test
    public void testCastTreatsToShare() {
        /*
         * Curious Pair {1}{G}
         * Creature — Human Peasant
         * 1/3
         * ----
         * Treats to Share {G}
         * Sorcery — Adventure
         * Create a Food token.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCantCastTreatsToShareTwice() {
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        checkPlayableAbility("can play on first", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can't play on second", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastCuriousPair() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastTreatsToShareAndCuriousPair() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastTreatsToShareWithEdgewallInnkeeper() {
        /*
         * Edgewall Innkeeper {G}
         * Creature — Human Peasant
         * Whenever you cast a creature spell that has an Adventure, draw a card.
         * 1/1
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastCuriousPairWithEdgewallInnkeeper() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Food", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastTreatsToShareAndCuriousPairWithEdgewallInnkeeper() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastCuriousPairWithMysteriousPathlighter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mysterious Pathlighter");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertPowerToughness(playerA, "Curious Pair", 2, 4);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastMemoryTheft() {
        /*
         * Memory Theft {2}{B}
         * Sorcery
         * Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
         * You may put a card that has an Adventure that player owns from exile into that player's graveyard.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Opt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.HAND, playerB, "Memory Theft");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memory Theft", playerA);
        playerB.addChoice("Opt");
        playerB.addChoice("Curious Pair");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, 0);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 2);
    }

    @Test
    public void testCastTreatsToShareWithLuckyClover() {
        /*
         * Lucky Clover {2}
         * Artifact
         * Whenever you cast an Adventure instant or sorcery spell, copy it. You may choose new targets for the copy.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Lucky Clover");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 2);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastTreatsToShareAndCopy() {
        /*
         * Fork {R}{R}
         * Instant
         * Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Fork");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fork", "Treats to Share");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 2);
        assertPermanentCount(playerA, 5);
        assertExileCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, 1);
        assertGraveyardCount(playerA, "Fork", 1);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCastTreatsToShareAndCounter() {
        /*
         * Counterspell {U}{U}
         * Instant
         * Counter target spell.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerB, "Counterspell");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Treats to Share");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 0);
        assertPermanentCount(playerA, 1);
        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testCastOpponentsHandTreatsToShare() {
        /*
         * Psychic Intrusion {3}{U}{B}
         * Sorcery
         * Target opponent reveals their hand. You choose a nonland card from that player's graveyard or hand and exile it.
         * You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Psychic Intrusion");
        addCard(Zone.HAND, playerB, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Intrusion", playerB);
        setChoice(playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
        assertPermanentCount(playerB, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 0);
        assertGraveyardCount(playerA, "Psychic Intrusion", 1);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testMultipleAdventures() {
        /*
         * Eager Cadet
         * Creature — Human Soldier
         * 1/1
         */
        /*
         * Rimrock Knight {1}{R}
         * Creature — Dwarf Knight
         * Rimrock Knight can't block.
         * 3/1
         * ----
         * Boulder Rush {R}
         * Instant — Adventure
         * Target creature gets +2/+0 until end of turn.
         */

        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Eager Cadet");
        addCard(Zone.HAND, playerA, "Rimrock Knight", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boulder Rush", "Eager Cadet");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boulder Rush", "Eager Cadet");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rimrock Knight");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rimrock Knight");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Rimrock Knight", 2);
        assertPermanentCount(playerA, "Eager Cadet", 1);
        assertPowerToughness(playerA, "Eager Cadet", 5, 1);
        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testRimrockKnightPermanentText() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Rimrock Knight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rimrock Knight");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Rimrock Knight", 1);
        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, 0);

        Permanent rimrock = getPermanent("Rimrock Knight");
        Assert.assertTrue(rimrock.getRules(currentGame).get(0).startsWith("Adventure Instant")); // must have adventure spell info on battlefield
        Assert.assertTrue(rimrock.getRules(currentGame).get(1).startsWith("{this} can't block."));
    }

    /*
     * Tests for Rule 601.3e:
     * 601.3e If a rule or effect states that only an alternative set of characteristics or a subset of characteristics
     * are considered to determine if a card or copy of a card is legal to cast, those alternative characteristics
     * replace the object’s characteristics prior to determining whether the player may begin to cast it.
     * Example: Garruk’s Horde says, in part, “You may cast the top card of your library if it’s a creature card.” If
     * you control Garruk’s Horde and the top card of your library is a noncreature card with morph, you may cast it
     * using its morph ability.
     * Example: Melek, Izzet Paragon says, in part, “You may cast the top card of your library if it’s an instant or
     * sorcery card.” If you control Melek, Izzet Paragon and the top card of your library is Giant Killer, an
     * adventurer creature card whose Adventure is an instant named Chop Down, you may cast Chop Down but not Giant
     * Killer. If instead you control Garruk’s Horde and the top card of your library is Giant Killer, you may cast
     * Giant Killer but not Chop Down.
     */
    @Test
    public void testCastTreatsToShareWithMelek() {
        /*
         * Melek, Izzet Paragon {4}{U}{R}
         * Legendary Creature — Weird Wizard
         * Play with the top card of your library revealed.
         * You may cast the top card of your library if it's an instant or sorcery card.
         * Whenever you cast an instant or sorcery spell from your library, copy it. You may choose new targets for the copy.
         * 2/4
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Melek, Izzet Paragon");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, 4);
        assertPermanentCount(playerA, "Food", 2);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCantCastCuriousPairWithMelek() {
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Melek, Izzet Paragon");
        //
        addCard(Zone.LIBRARY, playerA, "Curious Pair");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        checkPlayableAbility("can't play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Curious Pair", 0);
        assertLibraryCount(playerA, 1);
    }

    @Test
    public void testCastCuriousPairWithGarruksHorde() {
        /*
         * Garruk's Horde {5}{G}{G}
         * Creature — Beast
         * Trample
         * Play with the top card of your library revealed.
         * You may cast the top card of your library if it's a creature card.
         * 7/7
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Garruk's Horde");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCantCastTreatsToShareWithGarruksHorde() {
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Garruk's Horde");
        //
        addCard(Zone.LIBRARY, playerA, "Curious Pair");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        checkPlayableAbility("can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", true);
        checkPlayableAbility("can't play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Food", 0);
        assertLibraryCount(playerA, 1);
    }

    @Test
    //@Ignore("Not yet working correctly.")
    public void testCastTreatsToShareWithWrennAndSixEmblem() {
        /*
         * Wrenn and Six {R}{G}
         * Legendary Planeswalker — Wrenn
         * +1: Return up to one target land card from your graveyard to your hand.
         * −1: Wrenn and Six deals 1 damage to any target.
         * −7: You get an emblem with "Instant and sorcery cards in your graveyard have retrace."
         * Loyalty: 3
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Wrenn and Six");
        addCard(Zone.GRAVEYARD, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Forest"); // pay for retrace

        addCounters(1, PhaseStep.UPKEEP, playerA, "Wrenn and Six", CounterType.LOYALTY, 5);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-7: You get an emblem");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // showAvaileableAbilities("abils", 1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // retrace - You may cast this card from your graveyard by discarding a land card as an additional cost to cast it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setChoice(playerA, "Forest");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertPermanentCount(playerA, "Wrenn and Six", 1);
        assertEmblemCount(playerA, 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCastTreatsToShareWithTeferiTimeRaveler() {
        /*
         * Teferi, Time Raveler {1}{W}{U}
         * Legendary Planeswalker — Teferi
         * Each opponent can cast spells only any time they could cast a sorcery.
         * +1: Until your next turn, you may cast sorcery spells as though they had flash.
         * −3: Return up to one target artifact, creature, or enchantment to its owner's hand. Draw a card.
         * Loyalty: 4
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Time Raveler");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // showAvaileableAbilities("abils", 1, PhaseStep.BEGIN_COMBAT, playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Treats to Share");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, 3);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void test_PlayableAbiities_NoneByMana() {

        addCard(Zone.HAND, playerA, "Brazen Borrower", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // no playable by mana
        checkPlayableAbility("main", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerMainCast, false);
        checkPlayableAbility("adventure", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerAdventureCast, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlayableAbiities_NoneByTarget() {
        // Brazen Borrower {1}{U}{U}
        // Petty Theft {1}{U} Return target nonland permanent an opponent controls to its owner’s hand.

        addCard(Zone.HAND, playerA, "Brazen Borrower", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // no playable by wrong target
        checkPlayableAbility("main", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerMainCast, false);
        checkPlayableAbility("adventure", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerAdventureCast, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlayableAbiities_OnlyAdventure() {
        // Brazen Borrower {1}{U}{U}
        // Petty Theft {1}{U} Return target nonland permanent an opponent controls to its owner’s hand.

        addCard(Zone.HAND, playerA, "Brazen Borrower", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // only adventure
        checkPlayableAbility("main", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerMainCast, false);
        checkPlayableAbility("adventure", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerAdventureCast, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlayableAbiities_All() {
        // Brazen Borrower {1}{U}{U}
        // Petty Theft {1}{U} Return target nonland permanent an opponent controls to its owner’s hand.

        addCard(Zone.HAND, playerA, "Brazen Borrower", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // all
        checkPlayableAbility("main", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerMainCast, true);
        checkPlayableAbility("adventure", 1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityBrazenBorrowerAdventureCast, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }
}
