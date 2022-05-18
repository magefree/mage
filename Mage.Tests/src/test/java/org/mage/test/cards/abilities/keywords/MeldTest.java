package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MeldTest extends CardTestPlayerBase {

    @Test
    public void testMeldAndRestrict() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // When you cast Bruna, the Fading Light, you may return target Angel or Human creature card from your graveyard to the battlefield.
        // Flying, Vigilance
        // <i>(Melds with Gisela, the Broken Blade.)</i>
        addCard(Zone.HAND, playerA, "Bruna, the Fading Light"); // {5}{W}{W}
        // Flying, First strike, Lifelink
        // At the beginning of your end step, if you both own and control Gisela, the Broken Blade and a
        // creature named Bruna, the Fading Light, exile them, then meld them into Brisela, Voice of Nightmares.
        addCard(Zone.HAND, playerA, "Gisela, the Broken Blade"); // {2}{W}{W}
        // Brisela, Voice of Nightmares
        // Flying, First strike, Vigilance, Lifelink
        // Your opponents can't cast spells with converted mana cost 3 or less.

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bruna, the Fading Light");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Gisela, the Broken Blade");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion"); // Can't cast it because of Gisela

        setStopAt(4, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about cannot attack, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "Brisela, Voice of Nightmares", 1);
        assertPermanentCount(playerA, "Bruna, the Fading Light", 0);
        assertPermanentCount(playerA, "Gisela, the Broken Blade", 0);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);

    }

    /**
     * Brisela is bugged she is still "active" when dead
     */
    @Test
    public void testMeldAndStopRestrictIfMeldCreatureLeftBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // When you cast Bruna, the Fading Light, you may return target Angel or Human creature card from your graveyard to the battlefield.
        // Flying, Vigilance
        // <i>(Melds with Gisela, the Broken Blade.)</i>
        addCard(Zone.HAND, playerA, "Bruna, the Fading Light"); // {5}{W}{W}
        // Flying, First strike, Lifelink
        // At the beginning of your end step, if you both own and control Gisela, the Broken Blade and a
        // creature named Bruna, the Fading Light, exile them, then meld them into Brisela, Voice of Nightmares.
        addCard(Zone.HAND, playerA, "Gisela, the Broken Blade"); // {2}{W}{W}
        // Brisela, Voice of Nightmares  9/10
        // Flying, First strike, Vigilance, Lifelink
        // Your opponents can't cast spells with converted mana cost 3 or less.

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        // Exile target creature. You draw cards equal to that creature's power.
        // At the beginning of your next upkeep, return that card to the battlefield under its owner's control.
        // If you do, discard cards equal to that creature's toughness.
        addCard(Zone.HAND, playerB, "Vanish into Memory", 1); // Instant {2}{W}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bruna, the Fading Light");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Gisela, the Broken Blade");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Vanish into Memory", "Brisela, Voice of Nightmares");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Bruna, the Fading Light", 1);
        assertExileCount("Gisela, the Broken Blade", 1);
        assertPermanentCount(playerA, "Brisela, Voice of Nightmares", 0);

        assertGraveyardCount(playerB, "Vanish into Memory", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 2);
        assertHandCount(playerB, 2 + 9);

    }

    /**
     * Check that if the exiled parts return Brisela is created again
     */
    @Test
    public void testMeld3() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // When you cast Bruna, the Fading Light, you may return target Angel or Human creature card from your graveyard to the battlefield.
        // Flying, Vigilance
        // <i>(Melds with Gisela, the Broken Blade.)</i>
        addCard(Zone.HAND, playerA, "Bruna, the Fading Light"); // Creature {5}{W}{W}  5/7
        // Flying, First strike, Lifelink
        // At the beginning of your end step, if you both own and control Gisela, the Broken Blade and a
        // creature named Bruna, the Fading Light, exile them, then meld them into Brisela, Voice of Nightmares.
        addCard(Zone.HAND, playerA, "Gisela, the Broken Blade"); // Creature {2}{W}{W} 4/3
        // Brisela, Voice of Nightmares  9/10
        // Flying, First strike, Vigilance, Lifelink
        // Your opponents can't cast spells with converted mana cost 3 or less.

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        // Exile target creature. You draw cards equal to that creature's power.
        // At the beginning of your next upkeep, return that card to the battlefield under its owner's control.
        // If you do, discard cards equal to that creature's toughness.
        addCard(Zone.HAND, playerB, "Vanish into Memory", 1); // Instant {2}{W}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bruna, the Fading Light");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Gisela, the Broken Blade");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Vanish into Memory", "Brisela, Voice of Nightmares");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        // End step turn 7 the meld takes place again
        setStopAt(8, PhaseStep.UPKEEP);
        execute();

        assertExileCount("Bruna, the Fading Light", 0);
        assertExileCount("Gisela, the Broken Blade", 0);
        assertPermanentCount(playerA, "Brisela, Voice of Nightmares", 1);

        assertGraveyardCount(playerB, "Vanish into Memory", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 2);
        assertHandCount(playerB, 1); // discard 10 upkeep turn 6 ==> 0 + draw 1 at draw phase turn 6

    }

    /**
     * With Hanweir Garrison and Hanweir Battlements in your control put Hanweir
     * Battlements' ability in the stack to transform(i.e. meld). In answer to
     * that, return to hand Hanweir Garrison. Resolve Hanweir Battlements
     * ability.
     *
     * Expected result: The ability fizzles.
     *
     * Actual results: A NPE error is lauched.
     */
    @Test
    public void testReturnToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // Whenever Hanweir Garrison attacks, put two 1/1 red Human creature tokens onto the battlefield tapped and attacking.
        // <i>(Melds with Hanweir Battlements.)</i>
        addCard(Zone.BATTLEFIELD, playerA, "Hanweir Garrison"); // Creature 2/3 {2}{R}

        // {T}: Add {C}.
        // {R},{T}: Target creature gains haste until end of turn.
        // {3}{R}{R},{T}: If you both own and control Hanweir Battlements and a creature named Hanweir Garrison, exile them,
        // then meld them into Hanweir, the Writhing Township.
        addCard(Zone.BATTLEFIELD, playerA, "Hanweir Battlements"); // Land

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Return target creature to its owner's hand.
        addCard(Zone.HAND, playerB, "Unsummon", 1); // Instant {U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{R}{R}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon", "Hanweir Garrison", "{3}{R}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Unsummon", 1);

        assertPermanentCount(playerA, "Hanweir Battlements", 1);
        assertHandCount(playerA, "Hanweir Garrison", 1);

    }

    @Test
    public void testUnmeldAfterRollback() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // Whenever Hanweir Garrison attacks, put two 1/1 red Human creature tokens onto the battlefield tapped and attacking.
        // <i>(Melds with Hanweir Battlements.)</i>
        addCard(Zone.BATTLEFIELD, playerA, "Hanweir Garrison"); // Creature 2/3 {2}{R}

        // {T}: Add {C}.
        // {R},{T}: Target creature gains haste until end of turn.
        // {3}{R}{R},{T}: If you both own and control Hanweir Battlements and a creature named Hanweir Garrison, exile them,
        // then meld them into Hanweir, the Writhing Township.
        addCard(Zone.BATTLEFIELD, playerA, "Hanweir Battlements"); // Land

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Return target creature to its owner's hand.
        addCard(Zone.HAND, playerB, "Unsummon", 1); // Instant {U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{R}{R}");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon", "Hanweir, the Writhing Township");

        rollbackTurns(2, PhaseStep.BEGIN_COMBAT, playerB, 0);
        rollbackAfterActionsStart();
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Unsummon", "Hanweir, the Writhing Township");
        rollbackAfterActionsEnd();
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Unsummon", 1);

        assertHandCount(playerA, "Hanweir Battlements", 1);
        assertHandCount(playerA, "Hanweir Garrison", 1);

    }
}
