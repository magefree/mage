package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created this test class to test issue #5816:
 * https://github.com/magefree/mage/issues/5816
 *
 * Tested Vizier's other effects while I was at it.
 *
 * -- Vizier of the Menagerie --
 * Types:
 * Creature — Naga Cleric
 * Card Text:
 * You may look at the top card of your library any time.
 * You may cast the top card of your library if it's a creature card.
 * You may spend mana as though it were mana of any type to cast creature spells.
 * P/T:
 * 3 / 4
 * ------------------------------
 *
 * @author jgray1206
 */
public class VizierOfTheMenagerieTest extends CardTestPlayerBase {

    @Test
    /*
     * Vizier was not working with casting creatures off the top of the deck at instant speed
     * even though they had flash via continues effects (like the one Yeva provides).
     */
    public void testCanCastFlashEffectCreaturesOffLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");
         // You may cast green creature cards as though they had flash.
        addCard(Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Llanowar Elves");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Llanowar Elves");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }

    @Test
    /**
     * The negative case of the above unit test.
     */
    public void testCantCastNonFlashEffectedCreaturesOffLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");
        // You may cast green creature cards as though they had flash.
        addCard(Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        removeAllCardsFromLibrary(playerA);
        // A non-green creature card should not have Yeva's effect
        addCard(Zone.LIBRARY, playerA, "Cabal Therapist");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Cabal Therapist");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertPermanentCount(playerA, "Cabal Therapist", 0);
    }

    @Test
    /**
     * Should be able to cast flash creatures at instant speed from the top of the deck.
     * Flash via static ability, not continuous effect (via Yeva's).
     */
    public void testCanCastFlashCreatureOffLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Skylasher");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Skylasher");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertPermanentCount(playerA, "Skylasher", 1);
    }

    @Test
    /**
     * The negative case of the above unit test
     */
    public void testCantCastNonFlashCreatureOffLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Cabal Therapist");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Cabal Therapist");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertPermanentCount(playerA, "Cabal Therapist", 0);
    }

    @Test
    /**
     * Should be able to cast creatures cards with any mana.
     */
    public void testCanCastCreaturesWithAnyMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        //Creature that costs 1 Mountain to cast
        addCard(Zone.HAND, playerA, "Bloodcrazed Goblin");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodcrazed Goblin");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //Should have been able to cast using the one Forest because Vizier's effect
        assertPermanentCount(playerA, "Bloodcrazed Goblin", 1);
    }

    @Test
    /**
     * Should not be able to cast non-creature cards with any mana.
     */
    public void testCantCastNonCreatureWithAnyMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        //Instant that grants +1/+0 to all creatures you control
        addCard(Zone.HAND, playerA, "Banners Raised");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banners Raised");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //Should not have +1/+0 effect
        assertPowerToughness(playerA, "Vizier of the Menagerie", 3, 4);
    }

    @Test
    /**
     * Negative of the above unit test.
     */
    public void testCanCastNonCreatureWithCorrectMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //Instant that grants +1/+0 to all creatures you control
        addCard(Zone.HAND, playerA, "Banners Raised");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banners Raised");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //Had correct mana. Should have +1/+0 effect.
        assertPowerToughness(playerA, "Vizier of the Menagerie", 4, 4);
    }

    @Test
    /**
     * Should not have any of Vizier's effects when not on battlefield.
     * Tested using Vizier's "cast creatures with any mana" effect
     */
    public void testOtherZones() {
        addCard(Zone.GRAVEYARD, playerA, "Vizier of the Menagerie");
        addCard(Zone.LIBRARY, playerA, "Vizier of the Menagerie");
        addCard(Zone.HAND, playerA, "Vizier of the Menagerie");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Llanowar Elves");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //Did not have correct mana and no Vizier = Could not have casted Llanowar
        assertPermanentCount(playerA, "Llanowar Elves", 0);
    }

}
