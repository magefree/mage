
package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PrimordialTest extends CardTestMultiPlayerBase {

    /**
     * Tests Primordial cards with multiplayer effects
     *
     */
    @Test
    public void SepulchralPrimordialTest() {
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Sepulchral Primordial");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);

        // Player order: A -> D -> C -> B
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerC, "Walking Corpse");
        addCard(Zone.GRAVEYARD, playerD, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sepulchral Primordial");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Walking Corpse", 0);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerC, "Walking Corpse", 1);
        assertGraveyardCount(playerD, "Pillarfield Ox", 0);
    }

    /**
     * Sepulchral Primordial's "enter the battlefield" effect works correctly on
     * cast, but does not trigger if he is returned to the battlefield by other
     * means (e.g. summoned from the graveyard). I've encountered this in
     * 4-player commander games with other humans.
     */
    @Test
    public void SepulchralPrimordialFromGraveyardTest() {
        // Return target creature card from your graveyard to the battlefield. Put a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Miraculous Recovery", 1); // Instant {4}{W}
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.GRAVEYARD, playerA, "Sepulchral Primordial");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // Player order: A -> D -> C -> B
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerC, "Walking Corpse");
        addCard(Zone.GRAVEYARD, playerD, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Miraculous Recovery", "Sepulchral Primordial");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Walking Corpse", 0);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerC, "Walking Corpse", 1);
        assertGraveyardCount(playerD, "Pillarfield Ox", 0);
    }

    /**
     * I'm almost certain now about how this happens: when Sepulchral Primordial
     * enters the battlefield, and there's at least one opponent without a
     * creature in the graveyard, the ability doesn't trigger at all. It should
     * trigger at least for the players with creatures in the yard.
     */
    @Test
    public void SepulchralPrimordialFromGraveyardEmptyGraveTest() {
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Sepulchral Primordial"); // {5}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);

        // Player order: A -> D -> C -> B
        addCard(Zone.GRAVEYARD, playerC, "Walking Corpse"); // Not in Range
        addCard(Zone.GRAVEYARD, playerD, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sepulchral Primordial");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Walking Corpse", 0);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerC, "Walking Corpse", 1);
    }

    /**
     * Diluvian Primordial ETB trigger never happened in a 3 player FFA
     * commander game. He just resolved, no ETB trigger occurred.
     */
    @Test
    public void DiluvianPrimordialTest() {
        // Flying
        // When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Diluvian Primordial"); // {5}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerC, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerD, "Lightning Bolt");

        // Player order: A -> D -> C -> B
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diluvian Primordial");
        addTarget(playerA, "Lightning Bolt");
        addTarget(playerA, "Lightning Bolt");

        addTarget(playerA, playerB);
        addTarget(playerA, playerD);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Diluvian Primordial", 1);
        assertGraveyardCount(playerC, "Lightning Bolt", 1);
        assertExileCount("Lightning Bolt", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertLife(playerC, 20);
        assertLife(playerD, 17);
    }

}
