package org.mage.test.cards.single.ncc;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.UUID;

/**
 * {@link mage.cards.a.AnheloThePainter Anhelo, the Painter}
 * The first instant or sorcery spell you cast each turn has casualty 2.
 *      (As you cast that spell, you may sacrifice a creature with power 2 or greater.
 *       When you do, copy the spell and you may choose new targets for the copy.)
 *
 * @author Alex-Vasile
 */
public class AnheloTest extends CardTestPlayerBase {

    private static final String anhelo = "Anhelo, the Painter";  // {U}{B}{R}
    private static final String lightningBolt = "Lightning Bolt";  // {R}
    private static final String solRing = "Sol Ring";  // {1}
    private static final String mountain = "Mountain";
    // MDFC Creature—Instant
    private static final String flamescrollCelebrant = "Flamescroll Celebrant"; // {1}{R}
    private static final String revelInSilence = "Revel in Silence"; // {W}{W}
    // 7/7 used as casualty
    private static final String aetherwindBasker = "Aetherwind Basker";
    // Instant
    // {1}{U}
    // Casualty 1
    // Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
    private static final String aLittleChat = "A Little Chat";

    /**
     * Test that it works for sorcery, but only the first one.
     */
    @Test
    public void testWorksForFirstOnly() {
        addCard(Zone.BATTLEFIELD, playerA, anhelo);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 2);
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker, 2);
        addCard(Zone.HAND, playerA, lightningBolt, 2);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        setChoice(playerA, "Yes"); // Cast with Casualty
        setChoice(playerA, aetherwindBasker);
        setChoice(playerA, "No"); // Don't change targets
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerB, 20 - 2*3 - 3);
        assertPermanentCount(playerA, aetherwindBasker, 1);
    }

    /**
     * Test that it does not trigger for non-sorcery/instant.
     */
    @Test
    public void testNonSorceryOrInstant() {
        addCard(Zone.BATTLEFIELD, playerA, anhelo);
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker);
        addCard(Zone.HAND, playerA, solRing);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, solRing);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, solRing, 1);
        assertPermanentCount(playerA, aetherwindBasker, 1);
    }

    /**
     * Test that the instant side of an MDFC gains casualty
     */
    @Test
    public void testInstantSideMDFC() {
        addCard(Zone.BATTLEFIELD, playerA, anhelo);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker);
        addCard(Zone.HAND, playerA, flamescrollCelebrant);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, revelInSilence);
        setChoice(playerA, "Yes"); // Cast with Casualty
        setChoice(playerA, aetherwindBasker);
        // Spell has no targets, so not prompted to change them

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, aetherwindBasker, 0);
    }

    /**
     * Test that the non-instant side of an MDFC (one which has an instant on the other side) does NOT gain casualty.
     */
    @Test
    public void testNonInstantSideMDFC() {
        addCard(Zone.BATTLEFIELD, playerA, anhelo);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 2);
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker);
        addCard(Zone.HAND, playerA, flamescrollCelebrant);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flamescrollCelebrant);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, aetherwindBasker, 1);
        assertPermanentCount(playerA, flamescrollCelebrant, 1);
    }

    /**
     * Test that it works for one you cast on someone else's turn.
     */
    @Test
    public void testOnNotOwnTurn() {
        addCard(Zone.BATTLEFIELD, playerA, anhelo);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 2);
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker, 2);
        addCard(Zone.HAND, playerA, lightningBolt, 2);

        setStrictChooseMode(true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        setChoice(playerA, "Yes"); // Cast with Casualty
        setChoice(playerA, aetherwindBasker);
        setChoice(playerA, "No"); // Don't change targets
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertLife(playerB, 20 - 2*3 - 3);
        assertPermanentCount(playerA, aetherwindBasker, 1);
    }

    /**
     * Test that a card which already has Casualty will gain a second instance of Casualty and thus let you sacrifice twice in order to get 2 copies.
     */
    @Test
    public void testGainsSecondCasualty() {
        addCard(Zone.BATTLEFIELD, playerA, anhelo);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker, 2);
        addCard(Zone.HAND, playerA, aLittleChat);
        addCard(Zone.LIBRARY, playerA, "Desert", 6);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aLittleChat);
        setChoice(playerA, "Yes"); // First instance of casualty
        setChoice(playerA, "Yes"); // Second instance of casualty
        setChoice(playerA, aetherwindBasker);
        setChoice(playerA, aetherwindBasker);
        setChoice(playerA, "When you do"); // Chose which of the two copies to put on the stack first
        addTarget(playerA, "Desert");
        addTarget(playerA, "Desert");
        addTarget(playerA, "Desert");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertHandCount(playerA, 3);
        assertPermanentCount(playerA, aetherwindBasker, 0);
    }

    /**
     * Test that it does not work for opponents on your turn or on their.
     */
    @Test
    public void testOpponentCasts() {
        addCard(Zone.BATTLEFIELD, playerA, anhelo);
        addCard(Zone.BATTLEFIELD, playerB, mountain, 2);
        addCard(Zone.BATTLEFIELD, playerB, aetherwindBasker, 2);
        addCard(Zone.HAND, playerB, lightningBolt, 2);

        setStrictChooseMode(true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA);

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 - 3 - 3);
        assertPermanentCount(playerB, aetherwindBasker, 2);
    }
}