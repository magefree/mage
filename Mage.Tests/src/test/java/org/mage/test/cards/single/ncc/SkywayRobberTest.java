package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SkywayRobber Skyway Robber}
 * <p>
 * Escape—{3}{U}, Exile five other cards from your graveyard.
 *      (You may cast this card from your graveyard for its escape cost.)
 * <p>
 * Skyway Robber escapes with
 *      “Whenever Skyway Robber deals combat damage to a player,
 *       you may cast an artifact, instant, or sorcery spell from among cards exiled with Skyway Robber
 *       without paying its mana cost.”
 * @author Alex-Vasile
 */
public class SkywayRobberTest extends CardTestPlayerBase {

    private static final String skywayRobber = "Skyway Robber";

    /**
     * Check that you are not given the option to cast anything if there are no valid choices.
     */
    @Test
    public void testNoOption() {
        addCard(Zone.GRAVEYARD, playerA, skywayRobber);
        addCard(Zone.GRAVEYARD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + skywayRobber + " with Escape");

        attack(3, playerA, skywayRobber);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();
        assertExileCount(playerA, "Mountain", 5);
        assertLife(playerB, 17);
    }

    /**
     * Check that the cast works.
     */
    @Test
    public void testCast() {
        addCard(Zone.GRAVEYARD, playerA, skywayRobber);
        addCard(Zone.GRAVEYARD, playerA, "Mountain", 4);
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring" );
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + skywayRobber + " with Escape");

        attack(3, playerA, skywayRobber);

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertExileCount(playerA, "Mountain", 4);
        assertPermanentCount(playerA, "Sol Ring", 1);
        assertLife(playerB, 17);
    }
}
