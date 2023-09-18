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
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 1);
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

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastCuriousPair() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastTreatsToShareAndCuriousPair() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 1);
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
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 1);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastCuriousPairWithEdgewallInnkeeper() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Food Token", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastTreatsToShareAndCuriousPairWithEdgewallInnkeeper() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Food Token", 1);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCastCuriousPairWithMysteriousPathlighter() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mysterious Pathlighter");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 0);
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
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Opt");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.HAND, playerB, "Memory Theft");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memory Theft", playerA);
        setChoice(playerB, "Opt");
        setChoice(playerB, "Curious Pair");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

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
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Lucky Clover");
        addCard(Zone.HAND, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 2);
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
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Fork");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fork", "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 2);
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
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 0);
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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Psychic Intrusion");
        addCard(Zone.HAND, playerB, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Intrusion", playerB);
        setChoice(playerA, "Curious Pair");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
        assertPermanentCount(playerB, 0);
        assertPermanentCount(playerA, "Food Token", 1);
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

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Eager Cadet");
        addCard(Zone.HAND, playerA, "Rimrock Knight", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boulder Rush", "Eager Cadet");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boulder Rush", "Eager Cadet");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rimrock Knight", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rimrock Knight");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Rimrock Knight", 2);
        assertPermanentCount(playerA, "Eager Cadet", 1);
        assertPowerToughness(playerA, "Eager Cadet", 5, 1);
        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testRimrockKnightPermanentText() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Rimrock Knight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rimrock Knight");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

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
        addCard(Zone.BATTLEFIELD, playerA, "Melek, Izzet Paragon");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, 4);
        assertPermanentCount(playerA, "Food Token", 2);
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
        addCard(Zone.BATTLEFIELD, playerA, "Garruk's Horde");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 0);
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

        assertPermanentCount(playerA, "Food Token", 0);
        assertLibraryCount(playerA, 1);
    }

    @Test
    public void testCastTreatsToShareWithWrennAndSixEmblem() {
        /*
         * Wrenn and Six {R}{G}
         * Legendary Planeswalker — Wrenn
         * +1: Return up to one target land card from your graveyard to your hand.
         * −1: Wrenn and Six deals 1 damage to any target.
         * −7: You get an emblem with "Instant and sorcery cards in your graveyard have retrace."
         * Loyalty: 3
         */
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Wrenn and Six");
        addCard(Zone.GRAVEYARD, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Forest"); // pay for retrace

        addCounters(1, PhaseStep.UPKEEP, playerA, "Wrenn and Six", CounterType.LOYALTY, 5);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-7: You get an emblem");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // showAvailableAbilities("abils", 1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // retrace - You may cast this card from your graveyard by discarding a land card as an additional cost to cast it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setChoice(playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 1);
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
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Time Raveler");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // showAvailableAbilities("abils", 1, PhaseStep.BEGIN_COMBAT, playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, 3);
        assertPermanentCount(playerA, "Food Token", 1);
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
    }

    @Test
    public void test_BonecrusherGiant_Stopm() {
        // bug with non working stopm: https://github.com/magefree/mage/issues/6915

        // If noncombat damage would be dealt to Stormwild Capridor, prevent that damage.
        // Put a +1/+1 counter on Stormwild Capridor for each 1 damage prevented this way.
        addCard(Zone.BATTLEFIELD, playerA, "Stormwild Capridor@storm", 2); // 1/3
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Stomp {1}{R}
        // Damage can’t be prevented this turn. Stomp deals 2 damage to any target.
        addCard(Zone.HAND, playerA, "Bonecrusher Giant", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // prevent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@storm.1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("prevent", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkGraveyardCount("prevent", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@storm.1", 0);

        // prepare protect by stomp
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stomp");
        addTarget(playerA, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // can't prevent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@storm.2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("can't prevent", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 2);
        checkGraveyardCount("can't prevent", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@storm.2", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_HostageTaker_CastFromExileAllParts() {
        // bug: mdf must be playable as both sides
        // https://github.com/magefree/mage/pull/7446

        // Curious Pair {1}{G}, creature
        // Treats to Share {G}, sorcery
        // Create a Food token.
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2); // for prepare
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2); // cast from hostage taker for any color
        //
        // When Hostage Taker enters the battlefield, exile another target artifact or creature until Hostage Taker
        // leaves the battlefield. You may cast that card as long as it remains exiled, and you may spend mana
        // as though it were mana of any type to cast that spell.
        addCard(Zone.HAND, playerA, "Hostage Taker", 2); // {2}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // prepare adventure card on battlefield
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", 1);

        // turn 1 - exile by hostage
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hostage Taker");
        addTarget(playerA, "Curious Pair");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", 1);
        // play as creature for any color
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after play 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", 1);

        // eat green mana (test what hostage's any color effect work)
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);

        // turn 3 - exile by hostage
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hostage Taker");
        addTarget(playerA, "Curious Pair");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        checkExileCount("after exile 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Curious Pair", 1);
        // play as adventure spell
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Treats to Share");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after play 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Food Token", 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Cascade_CuriousPair() {
        // If a player cascades into Curious Pair with Bloodbraid Elf they can cast either spell
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        // Cascade
        addCard(Zone.HAND, playerA, "Bloodbraid Elf"); // {2}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        addCard(Zone.LIBRARY, playerA, "Curious Pair", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 2);

        // play elf with cascade
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodbraid Elf");
        setChoice(playerA, true); // use free cast
        setChoice(playerA, "Cast Treats to Share"); // can cast either

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Curious Pair", 0);
        assertPermanentCount(playerA, "Food Token", 1);
        assertExileCount(playerA, "Curious Pair", 1);
    }

    @Test
    public void test_Cascade_FlaxenIntruder() {
        // If a player cascades into Flaxen Intruder with Bloodbraid Elf they shouldn't be able to cast Welcome Home
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        // Cascade
        addCard(Zone.HAND, playerA, "Bloodbraid Elf"); // {2}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        addCard(Zone.LIBRARY, playerA, "Flaxen Intruder", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 2);

        // play elf with cascade
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodbraid Elf");
        setChoice(playerA, true); // use free cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Flaxen Intruder", 1);
        assertPermanentCount(playerA, "Bear", 0);
    }

    @Test
    public void test_SramsExpertise_CuriousPair() {
        addCard(Zone.HAND, playerA, "Sram's Expertise");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sram's Expertise");
        setChoice(playerA, true); // use free cast
        setChoice(playerA, "Cast Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Curious Pair", 0);
        assertPermanentCount(playerA, "Food Token", 1);
        assertPermanentCount(playerA, "Servo Token", 3);
        assertExileCount(playerA, "Curious Pair", 1);
    }

    @Test
    public void test_SramsExpertise_FlaxenIntruder() {
        addCard(Zone.HAND, playerA, "Sram's Expertise");
        addCard(Zone.HAND, playerA, "Flaxen Intruder");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sram's Expertise");
        setChoice(playerA, true); // use free cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Flaxen Intruder", 1);
        assertPermanentCount(playerA, "Bear", 0);
        assertPermanentCount(playerA, "Servo Token", 3);
    }

    @Test
    public void test_SramsExpertise_LonesomeUnicorn() {
        addCard(Zone.HAND, playerA, "Sram's Expertise");
        addCard(Zone.HAND, playerA, "Lonesome Unicorn");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sram's Expertise");
        setChoice(playerA, true); // use free cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lonesome Unicorn", 0);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertPermanentCount(playerA, "Servo Token", 3);
        assertExileCount(playerA, "Lonesome Unicorn", 1);
    }
}
