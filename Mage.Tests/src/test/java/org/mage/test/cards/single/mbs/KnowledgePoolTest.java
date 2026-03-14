package org.mage.test.cards.single.mbs;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class KnowledgePoolTest extends CardTestPlayerBase {

    /*
    Knowledge Pool
    {6}
    Artifact
    Imprint - When Knowledge Pool enters the battlefield, each player exiles the top three cards of their library.
    Whenever a player casts a spell from their hand, that player exiles it. If the player does, they may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost.
    */
    private static final String knowledgePool = "Knowledge Pool";

    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
    */
    private static final String lightningBolt = "Lightning Bolt";

    /*
    Shock
    {R}
    Instant
    Shock deals 2 damage to any target.
    */
    private static final String shock = "Shock";

    @Test
    public void testKnowledgePool() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, knowledgePool);
        addCard(Zone.HAND, playerA, lightningBolt);
        addCard(Zone.HAND, playerB, shock);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        setChoice(playerA, false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, shock, playerA);
        setChoice(playerB, true);
        setChoice(playerB, lightningBolt);
        addTarget(playerB, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 - 3); // shock exiled, bolt cast from knowledge pool
        assertLife(playerB, 20); // bolt exiled, no card to cast from knowledge pool
    }
}