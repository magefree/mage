package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.b.BanisherPriest Banisher Pries}
 * {1}{W}{W}
 * Creature — Human Cleric 2/2,
 * When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.
 *
 * @author LevelX2
 */
public class BanisherPriestTest extends CardTestPlayerBase {

   /**
     * If Banisher Priest leaves the battlefield before its enters-the-battlefield
     * ability resolves, the target creature won't be exiled.
     */
    @Test
    public void testDoNotExileIfBanisherPriestLeavesBattlefieldBeforeResolve() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.HAND, playerA, "Banisher Priest");
        addCard(Zone.HAND, playerB, "Incinerate");

        /**
         *  Rockslide Elemental
         *  Creature — Elemental 1/1, 2R (3)
         *  First strike.
         *  Whenever another creature dies, you may put a +1/+1 counter on Rockslide Elemental..
         */
        addCard(Zone.BATTLEFIELD, playerB, "Rockslide Elemental");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banisher Priest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Incinerate", "Banisher Priest");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // Rockslide Elemental returned to battlefield
        assertPermanentCount(playerB, "Rockslide Elemental", 1);
        // Banisher Priest should be in graveyard
        assertPermanentCount(playerA, "Banisher Priest", 0);

        // check that returning Rockslide Elemental did get a +1/+1 counter from dying Banisher Priest
        assertPowerToughness(playerB, "Rockslide Elemental", 2, 2);
    }


    /**
     * Check that the returning target did not trigger the dies Event of
     * the dying Banisher Priest
     */
    @Test
    public void testReturningTargetDoesNotTriggerDieEventOfBanisherPriest() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.HAND, playerA, "Banisher Priest");
        addCard(Zone.HAND, playerB, "Incinerate");

        /**
         *  Rockslide Elemental
         *  Creature — Elemental 1/1, 2R (3)
         *  First strike.
         *  Whenever another creature dies, you may put a +1/+1 counter on Rockslide Elemental.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Rockslide Elemental");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banisher Priest");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Incinerate", "Banisher Priest");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // Rockslide Elemental returned to battlefield
        assertPermanentCount(playerB, "Rockslide Elemental", 1);
        // Banisher Priest should be in graveyard
        assertPermanentCount(playerA, "Banisher Priest", 0);

        // check that returning Rockslide Elemental did not get a +1/+1 counter from dying Banisher Priest
        assertPowerToughness(playerB, "Rockslide Elemental", 1, 1);
    }

    /**
     * Check if Banisher Priest is removed from graveyard with Seance and
     * the target creature exiled with the token returns to the game, when
     * the token is exiled.
     */
    @Test
    public void testBanisherPriestToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.GRAVEYARD, playerB, "Banisher Priest");
        /**
         * Seance
         * {2}{W}{W}
         * Enchantment
         * At the beginning of each upkeep, you may exile target creature card from your graveyard.
         * If you do, put a token onto the battlefield that's a copy of that card except it's a
         * Spirit in addition to its other types.
         * Exile it at the beginning of the next end step.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Seance");
        addTarget(playerB, "Banisher Priest"); // Return the Banisher Priest from graveyard with Seance
        // The Silvercoat Lion is autochosen for Banisher Priest's ETB since it's the only creature on the opponent's board

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Banisher Priest should be in exile
        assertExileCount("Banisher Priest", 1);
        // Token ceased to exist
        assertPermanentCount(playerB, "Banisher Priest", 0);
        // Silvercoat Lion should be back on battlefield
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }
}

