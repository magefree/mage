package org.mage.test.cards.single.tdm;


import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MistriseVillageTest extends CardTestPlayerBase {

    /*
    Mistrise Village
    Land
    This land enters tapped unless you control a Mountain or a Forest.
    {T}: Add {U}.
    {U}, {T}: The next spell you cast this turn can’t be countered.
     */
    private static final String MISTRISE = "Mistrise Village";
    private static final String COUNTERSPELL = "Counterspell";
    private static final String BEAR_CUB = "Bear Cub";
    private static final String BALDUVIAN_BEARS = "Balduvian Bears";
    /*
    Force of Negation
    {1}{U}{U}
    Instant
    If it’s not your turn, you may exile a blue card from your hand rather than pay this spell’s mana cost.
    Counter target noncreature spell. If that spell is countered this way, exile it instead of putting it into its owner’s graveyard.
     */
    public static final String FORCE_OF_NEGATION = "Force of Negation";
    /*
    Narset, Parter of Veils
    {1}{U}{U}
    Legendary Planeswalker — Narset
    Each opponent can’t draw more than one card each turn.
    −2: Look at the top four cards of your library. You may reveal a noncreature, nonland card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
     */
    public static final String NARSET_PARTER_OF_VEILS = "Narset, Parter of Veils";
    /*
    Aether Spellbomb
    {1}
    Artifact
    {U}, Sacrifice this artifact: Return target creature to its owner’s hand.
    {1}, Sacrifice this artifact: Draw a card.
     */
    public static final String AETHER_SPELLBOMB = "Aether Spellbomb";

    @Test
    public void testCounter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, MISTRISE);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, COUNTERSPELL, 2);
        addCard(Zone.HAND, playerA, BEAR_CUB);
        addCard(Zone.HAND, playerA, BALDUVIAN_BEARS);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}"); // Activate mistrise
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BEAR_CUB, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, COUNTERSPELL, BEAR_CUB, BEAR_CUB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, BALDUVIAN_BEARS, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, COUNTERSPELL, BALDUVIAN_BEARS, BALDUVIAN_BEARS);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, BEAR_CUB, 1);
        assertGraveyardCount(playerA, BALDUVIAN_BEARS, 1);
        assertGraveyardCount(playerB, COUNTERSPELL, 2);
    }

    @Test
    public void testCastCounterAfterCastingSpell() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, MISTRISE);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerA, NARSET_PARTER_OF_VEILS);
        addCard(Zone.HAND, playerA, AETHER_SPELLBOMB);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, FORCE_OF_NEGATION);
        addCard(Zone.LIBRARY, playerA, "Stock Up");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}"); // Activate mistrise
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, NARSET_PARTER_OF_VEILS);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: "); // Narset ability
        setChoice(playerA, true);
        addTarget(playerA, "Stock Up");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Stock Up");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, FORCE_OF_NEGATION, "Stock Up");

        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, AETHER_SPELLBOMB);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, NARSET_PARTER_OF_VEILS, 1);
        assertPermanentCount(playerA, AETHER_SPELLBOMB, 1);
        assertGraveyardCount(playerB, FORCE_OF_NEGATION, 1);
        assertExileCount(playerA, "Stock Up", 1);
    }

    @Test
    public void testMultipleMistrise() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, MISTRISE,2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerA, NARSET_PARTER_OF_VEILS);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, FORCE_OF_NEGATION, 2);
        addCard(Zone.LIBRARY, playerA, AETHER_SPELLBOMB);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}"); // Activate mistrise
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}"); // Activate mistrise
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, NARSET_PARTER_OF_VEILS);
        checkPlayableAbility("cast force", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Force", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, FORCE_OF_NEGATION, NARSET_PARTER_OF_VEILS); // Fail to counter
        setChoice(playerB, "Cast with no alternative cost");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: "); // Narset ability
        setChoice(playerA, true);
        addTarget(playerA, AETHER_SPELLBOMB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, AETHER_SPELLBOMB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, FORCE_OF_NEGATION, AETHER_SPELLBOMB); // Successful counter
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, NARSET_PARTER_OF_VEILS, 1);
        assertGraveyardCount(playerB, FORCE_OF_NEGATION, 2);
        assertExileCount(playerA, AETHER_SPELLBOMB, 1);
    }

    @Test
    public void testMultipleMistriseNotConsecutive() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, MISTRISE,2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerA, NARSET_PARTER_OF_VEILS);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, FORCE_OF_NEGATION, 2);
        addCard(Zone.LIBRARY, playerA, AETHER_SPELLBOMB);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}"); // Activate mistrise
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, NARSET_PARTER_OF_VEILS);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, FORCE_OF_NEGATION, NARSET_PARTER_OF_VEILS); // Fail to counter
        setChoice(playerB, "Cast with no alternative cost");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: "); // Narset ability
        setChoice(playerA, true);
        addTarget(playerA, AETHER_SPELLBOMB);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{U}, {T}"); // Activate mistrise
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, 1);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, AETHER_SPELLBOMB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, FORCE_OF_NEGATION, AETHER_SPELLBOMB); // Successful counter
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, NARSET_PARTER_OF_VEILS, 1);
        assertGraveyardCount(playerB, FORCE_OF_NEGATION, 2);
        assertPermanentCount(playerA, AETHER_SPELLBOMB, 1);
    }

    @Test
    public void testCastingMultipleSpells() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, MISTRISE,2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 13);
        addCard(Zone.HAND, playerA, NARSET_PARTER_OF_VEILS);
        addCard(Zone.HAND, playerA, "Hieroglyphic Illumination");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, FORCE_OF_NEGATION, 2);
        addCard(Zone.LIBRARY, playerA, AETHER_SPELLBOMB);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}"); // Activate mistrise
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, NARSET_PARTER_OF_VEILS);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hieroglyphic Illumination");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, FORCE_OF_NEGATION, NARSET_PARTER_OF_VEILS); // Fail to counter
        setChoice(playerB, "Cast with no alternative cost");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, AETHER_SPELLBOMB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, FORCE_OF_NEGATION, AETHER_SPELLBOMB); // Successful counter
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, NARSET_PARTER_OF_VEILS, 1);
        assertGraveyardCount(playerB, FORCE_OF_NEGATION, 2);
        assertExileCount(playerA, AETHER_SPELLBOMB, 1);
    }
}
