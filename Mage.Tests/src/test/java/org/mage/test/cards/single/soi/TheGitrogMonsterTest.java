package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 3BG Legendary Creature - Frog Horror Deathtouch
 *
 * At the beginning of your upkeep, sacrifice The Gitrog Monster unless you
 * sacrifice a land.
 *
 * You may play an additional land on each of your turns.
 *
 * Whenever one or more land cards are put into your graveyard from anywhere,
 * draw a card.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class TheGitrogMonsterTest extends CardTestPlayerBase {

    /**
     * Basic sacrifice test when no lands are present
     */
    @Test
    public void noLandsSacrificeGitrog() {
        addCard(Zone.HAND, playerA, "The Gitrog Monster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.HAND, playerB, "Armageddon", 1); // destroy all lands
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Gitrog Monster");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Armageddon");

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Swamp", 3);
        assertGraveyardCount(playerA, "Forest", 2);
        assertGraveyardCount(playerA, "The Gitrog Monster", 1);
        assertGraveyardCount(playerB, "Plains", 4);
        assertGraveyardCount(playerB, "Armageddon", 1);
        assertPermanentCount(playerA, "The Gitrog Monster", 0);
        assertHandCount(playerA, 2); // 1 for turn, 1 more for lands that hit the grave
    }

    /**
     * Basic sacrifice test when there is a land
     */
    @Test
    public void hasLandsSacrificeLand() {
        addCard(Zone.HAND, playerA, "The Gitrog Monster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Gitrog Monster");
        // on 3rd turn during upkeep opt to sacrifice a land
        // TODO: I don't know how to get these choices to work, let the choices go automatically
//        addTarget(playerA, "Swamp");
//        setChoice(playerA, true);

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Swamp", 1);
        assertPermanentCount(playerA, "The Gitrog Monster", 1);
        assertHandCount(playerA, 2); // 1 for turn, 1 more for land sacrificed
    }

    /**
     * Basic sacrifice test when there is a land
     */
    @Test
    public void boardSweeperWithTokens() {
        addCard(Zone.HAND, playerA, "The Gitrog Monster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.HAND, playerB, "Planar Outburst", 1); // destroy all non-land creatures
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Archangel of Tithes", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Gitrog Monster");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Planar Outburst");

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "The Gitrog Monster", 0);
        assertPermanentCount(playerB, "Planar Outburst", 0);
    }

    /**
     * NOTE: As of 05/05/2017 this test is failing due to a bug in code. See
     * issue #3251
     *
     * I took control of a Gitrog Monster, while the Gitrog Monster's owner
     * controlled a Dryad Arbor and cast Toxic Deluge for 6.
     */
    @Test
    public void controlChange() {
        // Deathtouch
        // At the beginning of your upkeep, sacrifice The Gitrog Monster unless you sacrifice a land.
        // You may play an additional land on each of your turns.
        // Whenever one or more land cards are put into your graveyard from anywhere, draw a card.
        addCard(Zone.HAND, playerA, "The Gitrog Monster", 1); // Creature 6/6 {3}{B}{G}
        // As an additional cost to cast Toxic Deluge, pay X life.
        // All creatures get -X/-X until end of turn.
        addCard(Zone.HAND, playerA, "Toxic Deluge", 1); // Sorcery {2}{B}
        // (Dryad Arbor isn't a spell, it's affected by summoning sickness, and it has "{T}: Add {G}.")
        addCard(Zone.HAND, playerA, "Dryad Arbor", 1); // Land Creature 1/1
        addCard(Zone.HAND, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // Rags Sorcery {2}{B}{B}
        // All creatures get -2/-2 until end of turn.
        // Riches Sorcery {5}{U}{U}
        // Each opponent chooses a creature they control. You gain control of each of those creatures.
        addCard(Zone.GRAVEYARD, playerB, "Rags // Riches", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 7);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Gitrog Monster");
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dryad Arbor");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Riches");
        setChoice(playerA, "The Gitrog Monster");

        // As an additional cost to cast Toxic Deluge, pay X life.
        // All creatures get -X/-X until end of turn.
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Toxic Deluge");
        setChoice(playerA, "X=6");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Rags // Riches", 1);

        assertGraveyardCount(playerA, "Toxic Deluge", 1);
        assertLife(playerA, 14);

        assertGraveyardCount(playerA, "The Gitrog Monster", 1);
        assertGraveyardCount(playerA, "Dryad Arbor", 1);

        assertHandCount(playerB, 1); // 1 drawn in draw step of turn 2
        assertHandCount(playerA, 1); // 1 drawn in draw step of turn 3 - no card from Gitrog
    }
}
