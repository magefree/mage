package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MoleculeManTest extends CardTestPlayerBase {

    @Test
    public void test_FirstDrawNonlandCard_MiracleZero() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // {1}{G} -> miracle {0}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Grizzly Bears for miracle
        setChoice(playerA, true); // cast Grizzly Bears for miracle {0}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_FirstDrawLand_NoMiracle() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Forest"); // land -> no miracle

        // No miracle dialog expected
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Forest", 0);
    }

    @Test
    public void test_FirstDrawMDFCBothSpells_CastBackFaceForZero() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Valki, God of Lies {1}{B} (Creature) // Tibalt, Cosmic Impostor {5}{B}{R} (Planeswalker)
        addCard(Zone.LIBRARY, playerA, "Valki, God of Lies");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Valki, God of Lies for miracle
        setChoice(playerA, true); // cast for miracle {0}
        setChoice(playerA, "Cast Tibalt, Cosmic Impostor"); // choose back face for {0}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tibalt, Cosmic Impostor", 1);
        assertPermanentCount(playerA, "Valki, God of Lies", 0);
    }

    @Test
    public void test_FirstDrawAdventureCreature_CastAdventureForZero() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Bonecrusher Giant {2}{R} // Stomp {1}{R}: deals 2 damage to any target.
        addCard(Zone.LIBRARY, playerA, "Bonecrusher Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Bonecrusher Giant for miracle
        setChoice(playerA, true); // cast for miracle {0}
        setChoice(playerA, "Cast Stomp"); // choose Adventure side for {0}
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Bonecrusher Giant", 1); // on an adventure
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_FirstDrawNoManaCostCard_CastForZero() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Living End (no mana cost, Sorcery)
        addCard(Zone.LIBRARY, playerA, "Living End");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // returned by Living End
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // sacrificed by Living End

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Living End for miracle
        setChoice(playerA, true); // cast Living End for miracle {0}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Living End", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Centaur Courser", 1);
    }

    @Test
    public void test_FirstDrawCardWithAlternativeCost_CantApplySecondAlternativeCost() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Force of Will {3}{U}{U}: You may pay 1 life and exile a blue card from your hand rather than pay its mana cost.
        addCard(Zone.LIBRARY, playerA, "Force of Will");
        addCard(Zone.HAND, playerA, "Opt"); // blue card in hand so Force of Will alternative cost would be legal
        addCard(Zone.HAND, playerB, "Grizzly Bears"); // spell to counter
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        // PlayerB casts Grizzly Bears; PlayerA responds with Think Twice to draw Force of Will and miracle it for {0}.
        // Miracle is already an alternative cost, so Force of Will's own alternative cost may not be offered.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Force of Will for miracle
        setChoice(playerA, true); // cast Force of Will for miracle {0} (must NOT prompt for alternative cost)
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Force of Will", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, "Opt", 1); // not exiled
        assertLife(playerA, 20); // no life paid
    }
}
