
package org.mage.test.cards.copy;

import mage.abilities.keyword.ReplicateAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.stack.Spell;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl.StackClause;

/**
 * {@link mage.cards.i.IsochronScepter Isochron Scepter}
 * {2}
 * Artifact
 * Imprint — When Isochron Scepter enters the battlefield, you may exile an instant card with mana value 2 or less from your hand.
 * {2}, {T}: You may copy the exiled card. If you do, you may cast the copy without paying its mana cost.
 *
 * @author BetaSteward
 */
public class IsochronScepterTest extends CardTestPlayerBase {

    /**
     * Test that the imprinting works.
     */
    @Test
    public void testImprint() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Lightning Bolt", 1);
        assertLife(playerB, 20);
    }

    /**
     * Test that the exiled card can be cpied.
     */
    @Test
    public void testCopyCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, true); // use imprint
        setChoice(playerA, "Lightning Bolt"); // target for imprint (excile from hand)

        // copy and cast imprinted card
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertLife(playerB, 17);

    }

    @Test
    public void testCopyCardButDontCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertLife(playerB, 20);

    }

    /**
     * Not sure if it's triggered by just casting Angel's Grace or by casting it
     * from an Isochron Scepter, but when the bug happens neither player is able
     * to play spells or activate abilities anymore for the rest of the game.
     *
     * Maybe something related to Split Second?
     */
    @Test
    public void testAngelsGrace() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");

        // Split second (As long as this spell is on the stack, players can't cast spells or activate abilities that aren't mana abilities.)
        // You can't lose the game this turn and your opponents can't win the game this turn.
        // Until end of turn, damage that would reduce your life total to less than 1 reduces it to 1 instead.
        addCard(Zone.HAND, playerA, "Angel's Grace"); // Instant {W}

        addCard(Zone.BATTLEFIELD, playerB, "Dross Crocodile", 4);// 5/1
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, "Angel's Grace");

        attack(2, playerB, "Dross Crocodile");
        attack(2, playerB, "Dross Crocodile");
        attack(2, playerB, "Dross Crocodile");
        attack(2, playerB, "Dross Crocodile");

        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerA, "{2}, {T}:");
        setChoice(playerA, true);
        setChoice(playerA, true);

        // Damage life loss is reduced to 0 because of Angel's Grace effect active
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        // Spells can be cast again
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Dross Crocodile");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 1);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 2);
        assertGraveyardCount(playerB, "Dross Crocodile", 1);
        assertPermanentCount(playerB, "Dross Crocodile", 3);
        assertPermanentCount(playerA, "Isochron Scepter", 1);

        assertExileCount("Angel's Grace", 1);
        assertGraveyardCount(playerA, "Angel's Grace", 0);

    }

    /**
     * Resolving a Silence cast from exile via Isochron Scepter during my
     * opponent's upkeep does not prevent that opponent from casting spells that
     * turn.
     */
    @Test
    public void testSilence() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Silence");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, "Silence");

        activateAbility(2, PhaseStep.UPKEEP, playerA, "{2}, {T}:");
        setChoice(playerA, true);
        setChoice(playerA, true);

        checkPlayableAbility("Can't cast Silvercoat", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Silvercoat", false);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Silence", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

    }

    /**
     * https://github.com/magefree/mage/issues/13998
     */
    @Test
    public void testCopyCardWithReplicate() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Consign to Memory");
        addCard(Zone.HAND, playerB, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, true); // imprint
        setChoice(playerA, "Consign to Memory");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Ornithopter");
        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}:",
                TestPlayer.NO_TARGET, "Ornithopter", StackClause.WHILE_ON_STACK);
        setChoice(playerA, true); // copy imprinted card
        setChoice(playerA, true); // cast copy
        setChoice(playerA, true); // pay replicate {1} once
        setChoice(playerA, false); // stop paying replicate
        addTarget(playerA, "Ornithopter");
        setChoice(playerA, false); // keep replicate copy target

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Island", true, 3);
        assertExileCount("Consign to Memory", 1);
        assertGraveyardCount(playerB, "Ornithopter", 1);
    }
    /**
     * https://github.com/magefree/mage/issues/13758
     */
    @Test
    public void testCopyCardWithReplicateAgainstChalice() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Consign to Memory");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 2);
        addCard(Zone.HAND, playerB, "Chalice of the Void");
        addCard(Zone.HAND, playerB, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, true); //imprint
        setChoice(playerA, "Consign to Memory");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Chalice of the Void");
        setChoice(playerB, "X=1");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Ornithopter");
        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}:",
                TestPlayer.NO_TARGET, "Ornithopter", StackClause.WHILE_ON_STACK);
        setChoice(playerA, true); // copy imprinted card
        setChoice(playerA, true); // cast copy
        setChoice(playerA, true); // pay replicate {1} once
        setChoice(playerA, false); // stop paying replicate
        addTarget(playerA, "Ornithopter");
        setChoice(playerA, false); // keep replicate copy target

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        // Chalice counters cast copy, but not the Replicate copy because it wasn't cast
        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertPermanentCount(playerB, "Ornithopter", 0);
    }

    @Test
    public void testCopiedCardWithTwoGrantedReplicateAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Hatchery Sliver", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Autochthon Wurm"); // 9/14 survives all four resolutions
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Nameless Inversion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, true);
        setChoice(playerA, "Nameless Inversion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, true); // copy the imprinted card
        setChoice(playerA, true); // cast it
        setChoice(playerA, true); // first replicate: twice
        setChoice(playerA, true);
        setChoice(playerA, false);
        setChoice(playerA, true); // second replicate: once
        setChoice(playerA, false);
        addTarget(playerA, "Autochthon Wurm");
        setChoice(playerA, "Replicate"); // order the two triggers
        setChoice(playerA, false); // retain target for all three copies
        setChoice(playerA, false);
        setChoice(playerA, false);

        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true); // Scepter ability
        checkStackSize("spell and two replicate triggers", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 3);
        checkStackObject("two distinct replicate triggers", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Replicate", 2);
        runCode("two independently paid replicate abilities", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Spell spell = (Spell) game.getStack().stream().filter(Spell.class::isInstance).findFirst().get();
            Assert.assertEquals(2, spell.getCard().getAbilities(game).stream()
                    .filter(ReplicateAbility.class::isInstance).count());
        });
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPT("four Nameless Inversion resolutions", 1, PhaseStep.POSTCOMBAT_MAIN,
                playerB, "Autochthon Wurm", 21, 2);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, false); // decline both replicate costs on the new casting
        setChoice(playerA, false);
        addTarget(playerA, "Autochthon Wurm");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkStackSize("second casting has no replicate triggers", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkStackObject("only the cast card copy", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Nameless Inversion", 1);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerB, "Autochthon Wurm", 12, 11);
        assertExileCount("Nameless Inversion", 1);
        assertPermanentCount(playerA, "Hatchery Sliver", 2);
    }

    @Test
    public void testSimultaneousCardCopiesKeepReplicatePaymentsIndependent() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 12);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Pyromatics");
        addCard(Zone.HAND, playerA, "Dramatic Reversal");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, true);
        setChoice(playerA, "Pyromatics");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, true); // copy the imprinted card
        setChoice(playerA, true); // cast the first copy
        setChoice(playerA, true); // replicate twice
        setChoice(playerA, true);
        setChoice(playerA, false);
        addTarget(playerA, playerB);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);
        checkStackSize("first casting and its replicate trigger", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 2);

        // Cast another copy of the same card while the first copy's trigger is pending.
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dramatic Reversal", TestPlayer.NO_TARGET, "Replicate");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}:",
                TestPlayer.NO_TARGET, "Replicate", StackClause.WHILE_ON_STACK);
        setChoice(playerA, true);
        setChoice(playerA, true); // cast the second copy
        setChoice(playerA, false); // no replicate for this casting
        addTarget(playerA, playerA); // different target exposes copies of the wrong spell
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);
        checkStackSize("two cast copies and only the first replicate trigger", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 3);
        checkStackObject("second casting does not reuse the first payment", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Replicate", 1);
        setChoice(playerA, false); // retain the first spell's target for both replicate copies
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 19); // second casting: one resolution
        assertLife(playerB, 17); // first casting: original plus two replicate copies
        assertExileCount("Pyromatics", 1);
    }

}
