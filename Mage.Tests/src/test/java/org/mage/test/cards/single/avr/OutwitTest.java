package org.mage.test.cards.single.avr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OutwitTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.Outwit Outwit} {U}
     * Instant
     * Counter target spell that targets a player.
     */
    private static final String outwit = "Outwit";

    @Test
    public void test_BoltTargettingPlayer() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, outwit);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("Outwit castable", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Outwit", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, outwit, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, outwit, 1);
    }

    @Test
    public void test_BoltTargettingCreature_CantCastOutwit() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, outwit);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Memnite");
        checkPlayableAbility("Outwit not castable without valid target", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Outwit", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
