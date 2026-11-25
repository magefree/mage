package org.mage.test.cards.single.wth;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 *
 * @author JayDi85
 */
public class TariffTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_SacrificeTiedCreature() {
        // Each player sacrifices the creature they control with the highest mana value unless they pay that creature's
        // mana cost. If two or more creatures a player controls are tied for highest, that player chooses one.
        addCard(Zone.HAND, playerA, "Tariff"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // mana value 2, bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Watchwolf"); // mana value 2, wolf 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // mana value 3, ogre 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // mana value 3, centaur 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves"); // mana value 1, not the highest

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tariff");
        setChoice(playerA, "Watchwolf"); // tied creature of A
        setChoice(playerA, false); // don't pay -> sacrifice
        setChoice(playerB, "Gray Ogre"); // tied creature of B
        setChoice(playerB, false); // don't pay -> sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Watchwolf", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Gray Ogre", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);
    }

    @Test
    public void test_PayForTiedCreature() {
        // Each player sacrifices the creature they control with the highest mana value unless they pay that creature's
        // mana cost. If two or more creatures a player controls are tied for highest, that player chooses one.
        addCard(Zone.HAND, playerA, "Tariff"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // mana value 2, the only creature
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // mana value 3, ogre 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // {2}{G}, mana value 3, centaur 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3); // to pay for Centaur Courser

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tariff");
        setChoice(playerA, false); // don't pay for Grizzly Bears -> sacrifice
        setChoice(playerB, "Centaur Courser"); // tied creature of B
        setChoice(playerB, true); // pay {2}{G} -> keep it

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
        assertPermanentCount(playerB, "Gray Ogre", 1);
        assertTappedCount("Forest", true, 3);
    }

    @Test
    public void test_SacrificeTiedCreatureWithShroud() {
        // A player chooses a creature, it isn't a target, so a creature with shroud can be chosen too.

        // Each player sacrifices the creature they control with the highest mana value unless they pay that creature's
        // mana cost. If two or more creatures a player controls are tied for highest, that player chooses one.
        addCard(Zone.HAND, playerA, "Tariff"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // mana value 2, bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Argothian Enchantress"); // mana value 2, druid 0/1, shroud

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tariff");
        setChoice(playerA, "Argothian Enchantress"); // tied creature with shroud
        setChoice(playerA, false); // don't pay -> sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Argothian Enchantress", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_SacrificeAtTheSameTime() {
        // All chosen creatures are sacrificed at the same time, so Blood Artist sees itself and the other creature die.

        // Each player sacrifices the creature they control with the highest mana value unless they pay that creature's
        // mana cost. If two or more creatures a player controls are tied for highest, that player chooses one.
        addCard(Zone.HAND, playerA, "Tariff"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist"); // the only creature of A
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // the only creature of B

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tariff");
        setChoice(playerA, false); // don't pay for Blood Artist -> sacrifice
        setChoice(playerB, false); // don't pay for Grizzly Bears -> sacrifice
        setChoice(playerA, "Whenever"); // order of two Blood Artist triggers
        addTarget(playerA, playerB); // Blood Artist trigger for itself
        addTarget(playerA, playerB); // Blood Artist trigger for Grizzly Bears

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Blood Artist", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_AI_SacrificeTiedCreature() {
        // AI chooses among tied creatures by itself and gives up the least valuable one.

        // Each player sacrifices the creature they control with the highest mana value unless they pay that creature's
        // mana cost. If two or more creatures a player controls are tied for highest, that player chooses one.
        addCard(Zone.HAND, playerA, "Tariff"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // mana value 2, bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Watchwolf"); // mana value 2, wolf 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // mana value 3, ogre 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // mana value 3, centaur 3/3

        // no mana left to pay, all choices by AI
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tariff");

        setStrictChooseMode(false); // cast bad spell, but AI must choose
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Watchwolf", 1);
        assertGraveyardCount(playerB, "Gray Ogre", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
    }
}