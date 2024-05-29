package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class EladamriKorvecdalTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.EladamriKorvecdal Elamdari, Korvecdal} {1}{G}{G}
     * Legendary Creature — Elf Warrior
     * You may look at the top card of your library any time.
     * You may cast creature spells from the top of your library.
     * {G}, {T}, Tap two untapped creatures you control: Reveal a card from your hand or the top card of your library.
     * If you reveal a creature card this way, put it onto the battlefield. Activate only during your turn.
     * 3/3
     */
    private static final String eladamri = "Eladamri, Korvecdal";

    @Test
    public void test_Activation_FromHand_NonCreature() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, eladamri);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Swamp");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}");
        setChoice(playerA, "Memnite^Ornithopter"); // chosen to tap those.
        setChoice(playerA, true); // reveal from hand
        addTarget(playerA, "Swamp"); // reveal Swamp from hand

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Forest", true, 1);
        assertTappedCount(eladamri, true, 1);
        assertTappedCount("Memnite", true, 1);
        assertTappedCount("Ornithopter", true, 1);
        assertHandCount(playerA, "Swamp", 1); // Swamp still in hand.
    }

    @Test
    public void test_Activation_FromHand_Creature() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, eladamri);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}");
        setChoice(playerA, "Memnite^Ornithopter"); // chosen to tap those.
        setChoice(playerA, true); // reveal from hand
        addTarget(playerA, "Grizzly Bears"); // reveal Grizzly Bears from hand

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Forest", true, 1);
        assertTappedCount(eladamri, true, 1);
        assertTappedCount("Memnite", true, 1);
        assertTappedCount("Ornithopter", true, 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1); // Grizzly Bears was revealed, thus moved to battlefield.
    }


    @Test
    public void test_Activation_FromTop_Creature() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, eladamri);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}");
        setChoice(playerA, "Memnite^Ornithopter"); // chosen to tap those.
        setChoice(playerA, false); // reveal from top

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Forest", true, 1);
        assertTappedCount(eladamri, true, 1);
        assertTappedCount("Memnite", true, 1);
        assertTappedCount("Ornithopter", true, 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1); // Grizzly Bears was revealed, thus moved to battlefield.
    }


    @Test
    public void test_Activation_FromTop_NonCreature() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, eladamri);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        addCard(Zone.LIBRARY, playerA, "Taiga");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}");
        setChoice(playerA, "Memnite^Ornithopter"); // chosen to tap those.
        //setChoice(playerA, false); // no hand: no choice.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Forest", true, 1);
        assertTappedCount(eladamri, true, 1);
        assertTappedCount("Memnite", true, 1);
        assertTappedCount("Ornithopter", true, 1);
        assertPermanentCount(playerA, "Taiga", 0); // Taiga was revealed, but not moved to battlefield.
    }
}
