package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HideousEndTest extends CardTestPlayerBase {

    @Test
    public void testWithValidTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Hideous End");
        addCard(Zone.BATTLEFIELD, playerB, "Copper Myr");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hideous End", "Copper Myr");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Copper Myr", 0);
        assertLife(playerB, 18);
    }

    @Test
    public void testWithInvalidTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Hideous End");
        addCard(Zone.BATTLEFIELD, playerB, "Zombie Goliath");

        checkPlayableAbility("No Non-Black creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hideous", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Zombie Goliath", 1);
        assertLife(playerB, 20);
    }

    @Test
    public void testWithPossibleProtection() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Hideous End");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Copper Myr");
        // Target artifact or creature you control gains protection from artifacts or from the color of your choice until end of turn.
        addCard(Zone.HAND, playerB, "Blessed Breath");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hideous End", "Copper Myr");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Blessed Breath", "Copper Myr");
        setChoice(playerB, "Black");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Hideous End", 1);
        assertPermanentCount(playerB, "Copper Myr", 1);
        assertLife(playerB, 20);
    }
}
