package org.mage.test.cards.single.leg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author JayDi85
 */
public class JuxtaposeTest extends CardTestPlayerBase {

    @Test
    public void test_ExchangeTiedCreaturesAndArtifacts() {
        // You and target player exchange control of the creature you each control with the highest mana value.
        // Then exchange control of artifacts the same way. If two or more permanents a player controls are tied
        // for highest, their controller chooses one of them.
        addCard(Zone.HAND, playerA, "Juxtapose"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // creature, mana value 2
        addCard(Zone.BATTLEFIELD, playerA, "Watchwolf"); // creature, mana value 2
        addCard(Zone.BATTLEFIELD, playerA, "Millstone"); // artifact, mana value 2
        addCard(Zone.BATTLEFIELD, playerA, "Ankh of Mishra"); // artifact, mana value 2
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // creature, mana value 3
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // creature, mana value 3
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves"); // creature, mana value 1, not the highest
        addCard(Zone.BATTLEFIELD, playerB, "Icy Manipulator"); // artifact, mana value 4
        addCard(Zone.BATTLEFIELD, playerB, "Jayemdae Tome"); // artifact, mana value 4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Juxtapose", playerB);
        setChoice(playerA, "Watchwolf"); // tied creature of A
        setChoice(playerB, "Gray Ogre"); // tied creature of B
        setChoice(playerA, "Millstone"); // tied artifact of A
        setChoice(playerB, "Jayemdae Tome"); // tied artifact of B

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // exchanged
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertPermanentCount(playerB, "Watchwolf", 1);
        assertPermanentCount(playerA, "Jayemdae Tome", 1);
        assertPermanentCount(playerB, "Millstone", 1);
        // not exchanged
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Ankh of Mishra", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);
        assertPermanentCount(playerB, "Icy Manipulator", 1);
    }

    @Test
    public void test_ExchangeTiedCreatureWithShroud() {
        // A player chooses a permanent, it isn't a target, so a creature with shroud can be chosen too.

        // You and target player exchange control of the creature you each control with the highest mana value.
        // If two or more permanents a player controls are tied for highest, their controller chooses one of them.
        addCard(Zone.HAND, playerA, "Juxtapose"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser"); // creature, mana value 3, the only creature of A
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // creature, mana value 2
        addCard(Zone.BATTLEFIELD, playerB, "Argothian Enchantress"); // creature, mana value 2, shroud

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Juxtapose", playerB);
        setChoice(playerB, "Argothian Enchantress"); // tied creature with shroud

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // exchanged
        assertPermanentCount(playerA, "Argothian Enchantress", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
        // not exchanged
        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_AI_ExchangeTiedCreatures() {
        // AI chooses among tied creatures by itself and gives away the least valuable one.

        // You and target player exchange control of the creature you each control with the highest mana value.
        // If two or more permanents a player controls are tied for highest, their controller chooses one of them.
        addCard(Zone.HAND, playerA, "Juxtapose"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // creature, mana value 2, bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Watchwolf"); // creature, mana value 2, wolf 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // creature, mana value 3, ogre 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // creature, mana value 3, centaur 3/3

        // all choices by AI
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Juxtapose", playerB);

        setStrictChooseMode(false); // cast bad spell, but AI must choose
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // exchanged
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        // not exchanged
        assertPermanentCount(playerA, "Watchwolf", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
    }
}