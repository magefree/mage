package org.mage.test.cards.cost.adventure;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CastAdventureCardsTest extends CardTestPlayerBase {
    @Test
    public void testCastCuriousPair() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerA, "Food", 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA,0);
    }
}
