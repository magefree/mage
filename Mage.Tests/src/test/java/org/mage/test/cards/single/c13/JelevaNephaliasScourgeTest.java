package org.mage.test.cards.single.c13;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.j.JelevaNephaliasScourge Jeleva, Nephalia's Scourge}
 * {1}{U}{B}{R}
 * Flying
 * When Jeleva, Nephalia’s Scourge enters the battlefield, each player exiles the top X cards of their library,
 * where X is the amount of mana spent to cast Jeleva.
 * Whenever Jeleva attacks, you may cast an instant or sorcery spell from among cards exiled with Jeleva without paying its mana cost.
 */
public class JelevaNephaliasScourgeTest extends CardTestPlayerBase {
    private static final String jeleva = "Jeleva, Nephalia's Scourge";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/3476
     *      When casting Jeleva, Nephalia's Scourge,
     *      if Jeleva is no longer on the battlefield when her ETB trigger resolves,
     *      she won't exile any cards.
     */
    @Test
    public void etbWorksIfShesRemoved() {
        addCard(Zone.HAND, playerA, jeleva);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Destroy target Creature
        String murder = "Murder";
        addCard(Zone.HAND, playerB, murder);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jeleva);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, murder, jeleva);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, jeleva, 1);
        assertExileCount(playerA, 4);
        assertExileCount(playerB, 4);
    }
}
