
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

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
}
