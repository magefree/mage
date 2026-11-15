package org.mage.test.cards.single.mkm;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.k.KayaSpiritsJustice}
 * @author DominionSpy
 */
public class KayaSpiritsJusticeTest extends CardTestPlayerBase {

    // Test first ability of Kaya
    @Test
    public void test_TriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1 + 2 + 6);
        addCard(Zone.BATTLEFIELD, playerA, "Kaya, Spirits' Justice");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Fyndhorn Elves");
        addCard(Zone.HAND, playerA, "Thraben Inspector");
        addCard(Zone.HAND, playerA, "Astrid Peth");
        // Choose one or more —
        // • Exile all artifacts.
        // • Exile all creatures.
        // • Exile all enchantments.
        // • Exile all graveyards.
        addCard(Zone.HAND, playerA, "Farewell");

        // Creates a Clue token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Creates a Food token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Astrid Peth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Exile all creatures. Exile all graveyards.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Farewell");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "4");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        // Kaya's first ability triggers twice, so choose which is put on the stack:
        // Whenever one or more creatures you control and/or creature cards in your graveyard are put into exile,
        // you may choose a creature card from among them. Until end of turn, target token you control becomes a copy of it,
        // except it has flying.
        setChoice(playerA, "Whenever", 1);
        // Trigger targets
        addTarget(playerA, "Clue Token");
        addTarget(playerA, "Food Token");
        // Copy choices
        addTarget(playerA, "Fyndhorn Elves");
        addTarget(playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 0);
        assertPermanentCount(playerA, "Fyndhorn Elves", 1);
        assertAbility(playerA, "Fyndhorn Elves", FlyingAbility.getInstance(), true);
        assertPermanentCount(playerA, "Food Token", 0);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertAbility(playerA, "Llanowar Elves", FlyingAbility.getInstance(), true);
    }

    /**
     * Copies outlive both Kaya and the exiled cards they copy, including when Kaya dies with her trigger on the stack.
     */
    @Test
    public void test_CopiesOutliveKaya() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Kaya, Spirits' Justice");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Fyndhorn Elves");
        addCard(Zone.HAND, playerA, "Thraben Inspector", 2);
        addCard(Zone.HAND, playerA, "Swords to Plowshares", 2);
        addCard(Zone.HAND, playerA, "Hero's Downfall");
        // {W} instant: put target face-up exiled card into its owner's graveyard
        addCard(Zone.HAND, playerA, "Pull from Eternity", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", "Llanowar Elves");
        addTarget(playerA, "Clue Token");
        addTarget(playerA, "Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pull from Eternity", "Llanowar Elves", true);

        // kill Kaya in response to her second trigger
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Swords to Plowshares", "Fyndhorn Elves");
        addTarget(playerA, "Clue Token");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hero's Downfall", "Kaya, Spirits' Justice");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);
        checkPermanentCount("Kaya gone", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Kaya, Spirits' Justice", 0);
        checkStackObject("trigger still pending", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Whenever one or more", 1);
        addTarget(playerA, "Fyndhorn Elves");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Pull from Eternity", "Fyndhorn Elves", true);

        checkPermanentCount("first copy survives", 1, PhaseStep.END_TURN, playerA, "Llanowar Elves", 1);
        checkPermanentCount("second copy made", 1, PhaseStep.END_TURN, playerA, "Fyndhorn Elves", 1);
        checkAbility("with flying", 1, PhaseStep.END_TURN, playerA, "Fyndhorn Elves", FlyingAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        // both copies end at cleanup
        assertPermanentCount(playerA, "Llanowar Elves", 0);
        assertPermanentCount(playerA, "Fyndhorn Elves", 0);
        assertPermanentCount(playerA, "Clue Token", 2);
        // the copied cards really did leave exile
        assertGraveyardCount(playerA, "Llanowar Elves", 1);
        assertGraveyardCount(playerA, "Fyndhorn Elves", 1);
    }
}
