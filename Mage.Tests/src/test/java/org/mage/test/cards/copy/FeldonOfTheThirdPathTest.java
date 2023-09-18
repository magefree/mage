package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test the funtions of Feldon of the Third Path - {1}{R}{R} Legendary Creature
 * - Human Artificer 2/3 {2}{R}, {T} : Create a tokenonto the battlefield that's
 * a copy of target creature card in your graveyard, except it's an artifact in
 * addition to its other types. It gains haste. Sacrifice it at the beginning of
 * the next end step.
 *
 * @author LevelX2
 */
public class FeldonOfTheThirdPathTest extends CardTestPlayerBase {

    /**
     * Checking that enters the battlefield abilities of the copied creature
     * card works.
     */
    @Test
    public void testETBEffect() {
        // {2}{R}, {T}: Create a token that's a copy of target creature card in your graveyard, except it's an artifact
        // in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        // When Highway Robber enters the battlefield, target opponent loses 2 life and you gain 2 life.
        addCard(Zone.GRAVEYARD, playerA, "Highway Robber", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}, {T}: Create a token", "Highway Robber");
        addTarget(playerA, playerB); // opponent to loses 2 life
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("must have token", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Highway Robber", 1);
        checkPermanentCount("must have card", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Feldon of the Third Path", 1);

        // destroy token
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Highway Robber");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("must haven't token", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Highway Robber", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 22); // +2 from Robber
        assertLife(playerB, 18); // -2 from Robber

        // possible bug: triggers from destroyed permanents keeps in game state (e.g. 2 triggers in game state)
        Assert.assertEquals("game state must have only 1 trigger from original card", 1, currentGame.getState().getTriggers().size());
    }

    @Test
    public void testETB2Effect() {
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.GRAVEYARD, playerA, "Sepulchral Primordial", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}{R}, {T}: Create a token that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.",
                "Sepulchral Primordial");
        addTarget(playerA, "Silvercoat Lion"); // target for ETB Sepulchral Primordial

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Feldon of the Third Path", 1);
        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
}
