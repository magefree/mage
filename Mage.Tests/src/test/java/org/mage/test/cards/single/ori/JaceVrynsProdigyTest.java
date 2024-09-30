package org.mage.test.cards.single.ori;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class JaceVrynsProdigyTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.j.JaceVrynsProdigy Jace, Vryn's Prodigy} {1}{U}
     * Legendary Creature — Human Wizard
     * {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard, exile Jace, Vryn’s Prodigy, then return him to the battlefield transformed under his owner’s control.
     * 0/2
     * //
     * Jace, Telepath Unbound
     * Legendary Planeswalker — Jace
     * +1: Up to one target creature gets -2/-0 until your next turn.
     * −3: You may cast target instant or sorcery card from your graveyard this turn. If that spell would be put into your graveyard, exile it instead.
     * −9: You get an emblem with “Whenever you cast a spell, target opponent mills five cards.”
     * Loyalty: 5
     */
    private static final String jace = "Jace, Vryn's Prodigy";

    @Test
    public void test_Minus3_Split() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.LIBRARY, playerA, "Fire // Ice");
        addCard(Zone.GRAVEYARD, playerA, "Taiga", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3", "Fire // Ice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice", "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Fire // Ice", 1);
        assertHandCount(playerA, 1);
        assertTappedCount("Memnite", true, 1);
        assertTappedCount("Island", true, 2); // cost mana to cast
    }

    @Test
    public void test_Minus3_MDFC() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.LIBRARY, playerA, "Zof Consumption"); // Each opponent loses 4 life and you gain 4 life.
        addCard(Zone.GRAVEYARD, playerA, "Taiga", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3", "Zof Consumption");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zof Consumption");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Zof Consumption", 1);
        assertLife(playerA, 20 + 4);
        assertLife(playerB, 20 - 4);
        assertTappedCount("Swamp", true, 6); // cost mana to cast
    }
}
