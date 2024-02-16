package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class MeldTest extends CardTestPlayerBase {

    /**
     * Bruna, the Fading Light
     * <p>
     * When you cast Bruna, the Fading Light, you may return target Angel or Human creature card from your graveyard to the battlefield.
     * Flying, Vigilance
     * <i>(Melds with Gisela, the Broken Blade.)</i>
     */
    private static final String bruna = "Bruna, the Fading Light";
    /**
     * Gisela, the Broken Blade
     * <p>
     * Flying, First strike, Lifelink
     * At the beginning of your end step, if you both own and control Gisela, the Broken Blade and a
     * creature named Bruna, the Fading Light, exile them, then meld them into Brisela, Voice of Nightmares.
     */
    private static final String gisela = "Gisela, the Broken Blade";

    /**
     * Brisela, Voice of Nightmares
     * <p>
     * Flying, First strike, Vigilance, Lifelink
     * Your opponents can't cast spells with converted mana cost 3 or less.
     */
    private static final String brisela = "Brisela, Voice of Nightmares";

    @Test
    public void testMeldAndRestrict() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, bruna); // {5}{W}{W}
        addCard(Zone.HAND, playerA, gisela); // {2}{W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bruna);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, gisela);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion"); // Can't cast it because of Gisela

        setStopAt(4, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Cast Silvercoat Lion")) {
                Assert.fail("Should have thrown error about casting Silvercoat Lion, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, brisela, 1);
        assertPermanentCount(playerA, bruna, 0);
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
        addCard(Zone.HAND, playerA, bruna); // {5}{W}{W}
        addCard(Zone.HAND, playerA, gisela); // {2}{W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        // Exile target creature. You draw cards equal to that creature's power.
        // At the beginning of your next upkeep, return that card to the battlefield under its owner's control.
        // If you do, discard cards equal to that creature's toughness.
        addCard(Zone.HAND, playerB, "Vanish into Memory", 1); // Instant {2}{W}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bruna);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, gisela);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Vanish into Memory", brisela, true);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(bruna, 1);
        assertExileCount(gisela, 1);
        assertPermanentCount(playerA, brisela, 0);

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
        addCard(Zone.HAND, playerA, bruna); // {5}{W}{W}
        addCard(Zone.HAND, playerA, gisela); // {2}{W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        // Exile target creature. You draw cards equal to that creature's power.
        // At the beginning of your next upkeep, return that card to the battlefield under its owner's control.
        // If you do, discard cards equal to that creature's toughness.
        addCard(Zone.HAND, playerB, "Vanish into Memory", 1); // Instant {2}{W}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bruna, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, gisela, true);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Vanish into Memory", brisela);
        waitStackResolved(4, PhaseStep.PRECOMBAT_MAIN);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        // End step turn 7 the meld takes place again
        setStopAt(8, PhaseStep.UPKEEP);
        execute();

        assertExileCount(bruna, 0);
        assertExileCount(gisela, 0);
        assertPermanentCount(playerA, brisela, 1);

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

    /**
     * 202.3c. The mana value of a melded permanent is calculated as though it had
     * the combined mana cost of the front faces of each card that represents it.
     * If a permanent is a copy of a melded permanent (even if that copy is represented
     * by two other meld cards), the mana value of the copy is 0.
     */
    @Test
    public void testMeldManaValue() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 11);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerA, bruna); // {5}{W}{W}
        addCard(Zone.HAND, playerA, gisela); // {2}{W}{W}

        addCard(Zone.HAND, playerB, "Clone"); // {2}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bruna, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gisela);
        setChoice(playerA, bruna); // Gisela has an eot trigger that ask to choose Bruna apparently.

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, brisela, 1);
        int manaValue = getPermanent(brisela, playerA).getManaValue();
        Assert.assertEquals("Melded Brisela's mana value", manaValue, 7 + 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true); // Yes to clone.
        setChoice(playerB, brisela);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, brisela, 1);
        manaValue = getPermanent(brisela, playerB).getManaValue();
        Assert.assertEquals("Clone of Brisela should have mana value 0", manaValue, 0);
    }

    /**
     * The Mightstone and Weakstone
     * {5}
     * Legendary Artifact — Powerstone
     * <p>
     * When The Mightstone and Weakstone enters the battlefield, choose one —
     * • Draw two cards.
     * • Target creature gets -5/-5 until end of turn.
     * {T}: Add {C}{C}. This mana can’t be spent to cast nonartifact spells.
     * (Melds with Urza, Lord Protector.)
     */
    private static final String stones = "The Mightstone and Weakstone";

    /**
     * Urza, Lord Protector
     * {1}{W}{U}
     * Legendary Creature — Human Artificer
     * <p>
     * Artifact, instant, and sorcery spells you cast cost {1} less to cast.
     * {7}: If you both own and control Urza, Lord Protector and an artifact named The Mightstone and Weakstone, exile them, then meld them into Urza, Planeswalker. Activate only as a sorcery.
     */
    private static final String urza = "Urza, Lord Protector";
    // has too much text. Don't worry about it. We're not testing that.
    private static final String urzaPlaneswalker = "Urza, Planeswalker";

    /**
     * 202.3c. The mana value of a melded permanent is calculated as though it had
     * the combined mana cost of the front faces of each card that represents it.
     * If a permanent is a copy of a melded permanent (even if that copy is represented
     * by two other meld cards), the mana value of the copy is 0.
     */
    @Test
    public void testMeldManaValue_Urza() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, urza);
        addCard(Zone.BATTLEFIELD, playerA, stones);

        // {2}{U}{U} create a token copy of target permanent
        addCard(Zone.HAND, playerB, "Mythos of Illuna");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}: If you both own and control {this} and");
        setChoice(playerA, stones);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, urzaPlaneswalker, 1);
        int manaValue = getPermanent(urzaPlaneswalker, playerA).getManaValue();
        Assert.assertEquals("Melded Urza, Planeswalker's mana value", manaValue, 3 + 5);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mythos of Illuna", urzaPlaneswalker);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, urzaPlaneswalker, 1);
        manaValue = getPermanent(urzaPlaneswalker, playerB).getManaValue();
        Assert.assertEquals("Clone of Urza, Planeswalker should have mana value 0", manaValue, 0);
    }

    // Eliminate can not kill Urza's Planeswalker.
    @Test
    public void testMeld_Urza_Eliminate() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, urza);
        addCard(Zone.BATTLEFIELD, playerA, stones);

        // {1}{U}
        // Destroy target creature or planeswalker with mana value 3 or less.
        addCard(Zone.HAND, playerB, "Eliminate");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}: If you both own and control {this} and");
        setChoice(playerA, stones);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerA, urzaPlaneswalker, 1);

        // should not be possible.
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Eliminate", urzaPlaneswalker);

        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            Assert.assertEquals("Player PlayerB must have 0 actions but found 1", e.getMessage());
        }
    }


    // Bug report: after rollback, Eliminate can kill Urza's Planeswalker.
    // Issue was that after rollback, Urza had mana value 0 due to a missing copy constructor.
    @Test
    public void testMeld_Urza_Eliminate_After_Rollback() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, urza);
        addCard(Zone.BATTLEFIELD, playerA, stones);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}: If you both own and control {this} and");
        setChoice(playerA, stones);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();
        assertPermanentCount(playerA, urzaPlaneswalker, 1);

        rollbackTurns(2, PhaseStep.POSTCOMBAT_MAIN, playerA, 0);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, urzaPlaneswalker, 1);
        int manaValue = getPermanent(urzaPlaneswalker, playerA).getManaValue();
        Assert.assertEquals("Melded Urza, Planeswalker's mana value", manaValue, 3 + 5);
    }
}
