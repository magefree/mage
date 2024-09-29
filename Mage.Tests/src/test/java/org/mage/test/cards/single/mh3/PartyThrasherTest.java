package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class PartyThrasherTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PartyThrasher Party Thrasher} {1}{R}
     * Creature — Lizard Wizard
     * Noncreature spells you cast from exile have convoke.
     * (Each creature you tap while casting a noncreature spell from exile
     * pays for {1} or one mana of that creature’s color.)
     * At the beginning of your first main phase, you may discard a card.
     * If you do, exile the top two cards of your library, then choose one of them. You may play that card this turn.
     * 1/4
     */
    private static final String partyThrasher = "Party Thrasher";

    @Test
    public void testPlayFromExile() {
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Accorder's Shield");
        addCard(Zone.BATTLEFIELD, playerA, partyThrasher, 1);
        addCard(Zone.HAND, playerA, "Memnarch", 1);

        setChoice(playerA, "Yes"); // Discard card?
        setChoice(playerA, "Memnarch"); // Which card to pitch?
        setChoice(playerA, "Memnite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Memnarch", 1);
        assertPermanentCount(playerA, "Memnite", 1);
        assertExileCount(playerA, "Accorder's Shield", 1);
    }

    @Test
    public void testTryUnplayableCard() {
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Accorder's Shield");
        addCard(Zone.BATTLEFIELD, playerA, partyThrasher, 1);
        addCard(Zone.HAND, playerA, "Memnarch", 1);

        setChoice(playerA, "Yes"); // Discard card?
        setChoice(playerA, "Memnarch"); // Which card to pitch?
        setChoice(playerA, "Memnite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Accorder's Shield");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            Assert.fail("Should have failed to execute, as Accorder's Shield should not be castable");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: Cast Accorder's Shield")) {
                Assert.fail("must throw error about missing ability:\n" + e.getMessage());
            }
        }

    }

    @Test
    public void testTryPlayFromExileNextTurn() {
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Accorder's Shield");
        addCard(Zone.BATTLEFIELD, playerA, partyThrasher, 1);
        addCard(Zone.HAND, playerA, "Memnarch", 1);

        setChoice(playerA, "Yes"); // Discard card?
        setChoice(playerA, "Memnarch"); // Which card to pitch?
        setChoice(playerA, "Memnite");

        setChoice(playerA, "No"); // Discard card? (next turn)

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            Assert.fail("Should have failed to execute, as Memnite should not be castable the turn after Party Thrasher Exiled it.");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: Cast Memnite")) {
                Assert.fail("must throw error about missing ability:\n" + e.getMessage());
            }
        }

    }

}
