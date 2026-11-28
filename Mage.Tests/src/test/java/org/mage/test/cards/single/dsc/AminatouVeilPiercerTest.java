package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AminatouVeilPiercerTest extends CardTestPlayerBase {

    @Test
    public void test_UpkeepSurveil() {
        skipInitShuffling();

        // At the beginning of your upkeep, surveil 2.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // second from top
        addCard(Zone.LIBRARY, playerA, "Plains"); // top of library

        // Turn 1 upkeep: surveil 2, put Plains into graveyard and keep Grizzly Bears on top
        addTarget(playerA, "Plains"); // put into graveyard via surveil 2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Plains", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_FirstDrawEnchantment_MiracleReducedCost() {
        skipInitShuffling();

        // At the beginning of your upkeep, surveil 2.
        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // When Oblivion Ring enters, exile another target nonland permanent.
        addCard(Zone.LIBRARY, playerA, "Oblivion Ring"); // {2}{W} -> miracle {W}
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // pays miracle {W}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2, exile target

        // Turn 1 upkeep: surveil 2, bin Forest so Oblivion Ring stays on top
        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Oblivion Ring for miracle
        setChoice(playerA, true); // cast Oblivion Ring for its miracle cost {W}
        addTarget(playerA, "Grizzly Bears"); // Oblivion Ring ETB target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Oblivion Ring", 1);
        assertExileCount(playerB, "Grizzly Bears", 1);
        assertTappedCount("Plains", true, 1);
    }

    @Test
    public void test_FirstDrawEnchantment_CostReducedToZero() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Enchantment {4} -> miracle {0} (reduced to nothing is {0})
        addCard(Zone.LIBRARY, playerA, "Ugin's Mastery"); // {4}
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Ugin's Mastery for miracle
        setChoice(playerA, true); // cast Ugin's Mastery for its miracle cost {0}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ugin's Mastery", 1);
    }

    @Test
    public void test_SecondDrawEnchantment_NoMiracle() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw two cards.
        addCard(Zone.HAND, playerA, "Divination"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.LIBRARY, playerA, "Oblivion Ring"); // second drawn card: {2}{W}
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // first drawn card: non-enchantment
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        // First drawn card is Grizzly Bears (not enchantment), second is Oblivion Ring (not first draw -> no miracle)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Oblivion Ring", 1);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
    }

    @Test
    public void test_FirstDrawNonEnchantment_NoMiracle() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // {1}{G}, creature (not enchantment)
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        // No miracle dialog for non-enchantment
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_DeclineReveal_StaysInHand() {
        // Aminatou, Veil Piercer ruling:
        // You don't have to reveal a drawn card with miracle if you don't wish to cast it at that time.
        // (2024-09-20)
        // Aminatou, Veil Piercer ruling:
        // You still draw the card, whether you use the miracle ability or not. Any ability that triggers whenever you draw
        // a card, for example, will trigger. If you don't cast the card using its miracle ability, it will remain in your
        // hand.
        // (2024-09-20)
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Oblivion Ring"); // {2}{W} -> miracle {W}
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, false); // decline to reveal Oblivion Ring for miracle

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Oblivion Ring", 1);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertTappedCount("Plains", false, 1);
    }

    @Test
    public void test_AminatouLeavesBeforeTriggerResolves_StillCasts() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // When Oblivion Ring enters, exile another target nonland permanent.
        addCard(Zone.LIBRARY, playerA, "Oblivion Ring"); // {2}{W} -> miracle {W}
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // pays miracle {W}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        // Destroy target creature.
        addCustomEffect_TargetDestroy(playerB);

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Oblivion Ring for miracle
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // let Think Twice resolve so miracle trigger is on stack

        // Destroy Aminatou while miracle trigger is on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "Aminatou, Veil Piercer");

        // Miracle trigger still resolves and casts Oblivion Ring
        setChoice(playerA, true); // cast Oblivion Ring for its miracle cost {W}
        addTarget(playerA, "Grizzly Bears"); // Oblivion Ring ETB target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Aminatou, Veil Piercer", 1);
        assertPermanentCount(playerA, "Oblivion Ring", 1);
        assertExileCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_FirstDrawRoom_ChooseDoorAndReduceChosenCost() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Bottomless Pool {U} // Locker Room {4}{U} (Enchantment -- Room)
        // Casting Locker Room for miracle reduces {4}{U} by {4} -> {U}
        addCard(Zone.LIBRARY, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1); // pays miracle {U} for Locker Room

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Bottomless Pool // Locker Room for miracle
        setChoice(playerA, true); // cast for miracle cost
        setChoice(playerA, "Cast Locker Room"); // choose Locker Room ({4}{U} - {4} = {U})

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Locker Room", 1);
        assertTappedCount("Island", true, 2 + 1);
    }

    @Test
    public void test_FirstDrawEnchantmentAdventure_CastAdventureWithReduction() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Virtue of Loyalty {3}{W}{W} (Enchantment) // Ardenvale Fealty {1}{W} (Instant -- Adventure: create 2/2 Knight)
        addCard(Zone.LIBRARY, playerA, "Virtue of Loyalty");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // pays miracle {W} for Ardenvale Fealty ({1}{W} - {4} = {W})

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Virtue of Loyalty for miracle
        setChoice(playerA, true); // cast for miracle cost
        setChoice(playerA, "Cast Ardenvale Fealty"); // cast Adventure side for {W}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Virtue of Loyalty", 1); // sent on an adventure
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTappedCount("Plains", true, 1);
    }

    @Test
    public void test_FirstDrawXCostEnchantment_ReductionAppliesToX() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // The Meathook Massacre {X}{B}{B}: When it enters, each creature gets -X/-X until end of turn.
        // With X = 4, miracle cost is {4}{B}{B} reduced by {4} = {B}{B}.
        addCard(Zone.LIBRARY, playerA, "The Meathook Massacre");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2); // pays {B}{B} for X = 4
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3, dies to -4/-4

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal The Meathook Massacre for miracle
        setChoice(playerA, true); // cast for miracle cost
        setChoice(playerA, "X=4"); // announce X=4, reduced by {4} -> pay {B}{B}
        // Both Aminatou (2/4) and Hill Giant (3/3) die to -4/-4, triggering both Meathook Massacre abilities
        setChoice(playerA, "Whenever a creature you control dies");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Meathook Massacre", 1);
        assertGraveyardCount(playerA, "Aminatou, Veil Piercer", 1);
        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertTappedCount("Swamp", true, 2);
    }

    @Test
    public void test_FirstDrawEnchantment_WithThaliaTaxNotReducedByExcessMiracleDiscount() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U} (+ {1} from Thalia = {2}{U})
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // Oblivion Ring {2}{W}: miracle cost = {2}{W} - {4} = {W}; + Thalia {1} = {1}{W} (excess {2} cannot pay Thalia)
        addCard(Zone.LIBRARY, playerA, "Oblivion Ring");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // pays {1}{W}
        // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben"); // 2/1

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3); // pay Think Twice + Thalia {1}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Oblivion Ring for miracle
        setChoice(playerA, true); // cast for miracle cost {W} + Thalia {1} = {1}{W}
        addTarget(playerA, "Thalia, Guardian of Thraben"); // Oblivion Ring ETB target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Oblivion Ring", 1);
        assertExileCount(playerB, "Thalia, Guardian of Thraben", 1);
        assertTappedCount("Plains", true, 2); // must pay {1}{W}, not {W}
    }

    @Test
    public void test_FirstDrawEnchantment_WithKickerNotReducedByExcessMiracleDiscount() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Bubble Snare {U}, Kicker {2}{U}: miracle cost = {U}, + kicker {2}{U} = {2}{U}{U}
        addCard(Zone.LIBRARY, playerA, "Bubble Snare");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4); // pays {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Bubble Snare for miracle
        setChoice(playerA, true); // cast for miracle cost
        setChoice(playerA, true); // pay Kicker {2}{U}
        addTarget(playerA, "Grizzly Bears"); // Bubble Snare aura target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Bubble Snare", 1);
        assertTapped("Grizzly Bears", true); // tapped because Bubble Snare was kicked
        assertTappedCount("Island", true, 2 + 4); // {1}{U} + {2}{U}{U}
    }

    @Test
    public void test_FirstDrawEnchantment_WithTrinisphereAndStarfieldMystic() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Enchantment spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Starfield Mystic");
        // As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Trinisphere");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U} -> Trinisphere makes it {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // Oblivion Ring {2}{W}: miracle {W} - {1} (Starfield Mystic) = {W}, Trinisphere raises total to {2}{W}
        addCard(Zone.LIBRARY, playerA, "Oblivion Ring");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3); // pays {2}{W}

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3); // pay Think Twice under Trinisphere
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Oblivion Ring for miracle
        setChoice(playerA, true); // cast for miracle cost under Trinisphere ({2}{W})
        addTarget(playerA, "Trinisphere"); // Oblivion Ring ETB target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Oblivion Ring", 1);
        assertExileCount(playerB, "Trinisphere", 1);
        assertTappedCount("Plains", true, 3);
    }

    @Test
    public void test_FirstDrawMDFCBackEnchantment_NoMiracle() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // MDFC: front face is Creature (Esika, God of the Tree), back face is Enchantment (The Prismatic Bridge).
        // In hand it has only the front face's characteristics, so it is not an enchantment card.
        addCard(Zone.LIBRARY, playerA, "Esika, God of the Tree");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        // No miracle dialog expected
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Esika, God of the Tree", 1);
        assertPermanentCount(playerA, "Esika, God of the Tree", 0);
        assertPermanentCount(playerA, "The Prismatic Bridge", 0);
    }

    @Test
    public void test_FirstDrawTDFCFrontEnchantment_Miracle() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // TDFC: front face is Enchantment -- Saga {2}{R} -> miracle {R}
        addCard(Zone.LIBRARY, playerA, "Fable of the Mirror-Breaker");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // pays miracle {R}

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Fable of the Mirror-Breaker for miracle
        setChoice(playerA, true); // cast for miracle cost {R}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Fable of the Mirror-Breaker", 1);
        assertPermanentCount(playerA, "Goblin Shaman Token", 1); // chapter I triggered on ETB
        assertTappedCount("Mountain", true, 1);
    }

    @Test
    public void test_FirstDrawBestowEnchantment_CantCombineAlternativeCosts() {
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Boon Satyr {1}{G}{G}, Bestow {3}{G}{G}: miracle cost is {G}{G}; cannot also use Bestow alternative cost
        addCard(Zone.LIBRARY, playerA, "Boon Satyr");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5); // enough for Bestow if it were wrongly offered

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Boon Satyr for miracle
        setChoice(playerA, true); // cast for miracle cost {G}{G} (must NOT offer Bestow choice)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Boon Satyr", 1);
        assertTappedCount("Forest", true, 2); // paid {G}{G}, not Bestow
    }

    @Test
    public void test_FirstDrawXCostEnchantment_WithThaliaTax_ExcessDiscountDoesNotPayTax() {
        // Aminatou, Veil Piercer ruling:
        // To determine the total cost of a spell, start with the mana cost or alternative cost (such as a miracle cost)
        // you're paying, add any cost increases, then apply any cost reductions. The mana value of the spell remains
        // unchanged, no matter what the total cost to cast it was.
        // (2024-09-20)
        skipInitShuffling();

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, Veil Piercer");
        // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U} + Thalia {1} = {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // The Meathook Massacre {X}{B}{B}: When it enters, each creature gets -X/-X until end of turn.
        // With X = 2: miracle cost is {2}{B}{B} reduced by {4} = {B}{B} (excess {2} cannot pay Thalia's {1});
        // total cost = {B}{B} + {1} = {1}{B}{B}.
        addCard(Zone.LIBRARY, playerA, "The Meathook Massacre");
        addCard(Zone.LIBRARY, playerA, "Forest"); // binned by upkeep surveil 2
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3); // must tap all 3 ({1}{B}{B})

        addTarget(playerA, "Forest"); // put into graveyard via surveil 2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3); // pay Think Twice + Thalia {1}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal The Meathook Massacre for miracle
        setChoice(playerA, true); // cast for miracle cost
        setChoice(playerA, "X=2"); // announce X=2 -> miracle cost {B}{B} + Thalia {1} = {1}{B}{B}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Meathook Massacre", 1);
        assertGraveyardCount(playerB, "Thalia, Guardian of Thraben", 1); // 2/1 dies to -2/-2
        assertPermanentCount(playerA, "Aminatou, Veil Piercer", 1); // 2/4 survives -2/-2
        assertTappedCount("Swamp", true, 3); // paid {1}{B}{B}, excess {2} did not cover Thalia
    }
}
