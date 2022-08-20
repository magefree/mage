package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MimicVat Mimic Vat}
 * {3}
 * Artifact
 * Imprint — Whenever a nontoken creature dies, you may exile that card.
 *           If you do, return each other card exiled with Mimic Vat to its owner’s graveyard.
 * {3}, {T}: Create a token that’s a copy of a card exiled with Mimic Vat.
 *           It gains haste.
 *           Exile it at the beginning of the next end step.
 *
 * @author LevelX2
 */
public class MimicVatTest extends CardTestPlayerBase {

    /**
     * All the clone type cards that may enter as a copy of something don't work
     * correctly with Mimic Vat. The only one I found that works (the token
     * being able to clone something) is Phyrexian Metamorph. Phyrexian
     * Metamorph is implemented differently than the rest of similar functioning
     * cards, ie. Clone, Phantasmal Image, Body Double, Clever Impersonator.
     * Also The copy ability on Phyrexian Metamorph is optional but it is forced
     * in game
     */
    @Test
    public void TestClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mimic Vat", 1); // Artifact {3}
        // {2}, {T}, Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Vault", 1);

        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1);// Creature {3}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // clone the opponent's creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true); // use clone on etb
        setChoice(playerA, "Silvercoat Lion");

        // kill clone and exile it (imprint into vat)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}, Sacrifice a creature");
        setChoice(playerA, "Silvercoat Lion");
        setChoice(playerA, true); // exile killed card by vat

        // turn 3

        // create a token from exile (imprinted card: clone)
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Create a token");
        setChoice(playerA, true); // use clone on etb
        setChoice(playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Clone", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void TestPhyrexianMetamorph() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mimic Vat", 1); // Artifact {3}
        // {2}, {T}, Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Vault", 1);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph", 1);// Creature {3}{U/P}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, true);
        setChoice(playerA, "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}, Sacrifice a creature");
        setChoice(playerA, true);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Create a token that's a copy of a card exiled with ");
        setChoice(playerA, true);
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Phyrexian Metamorph", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     * Player A has Mimic Vat and plays Sidisi, Undead Vizier and exploits.
     * Player N responds to Mimic Vat Trigger with Shred Memory, exiling Sidisi.
     * Sidisi gets exiled but then xmage allows player A to imprint the
     * creature, which shouldn't be possible.
     */
    @Test
    public void TestExileFails() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mimic Vat", 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // Exile up to four target cards from a single graveyard.
        // Transmute {1}{B}{B}
        addCard(Zone.HAND, playerB, "Shred Memory", 1); // Instant {1}{B}

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, "Yes");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shred Memory", "Silvercoat Lion", "Whenever a nontoken creature dies");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Create a token that's a copy of a card exiled with ");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Shred Memory", 1);

        assertExileCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
    }
}
