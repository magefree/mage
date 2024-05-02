package org.mage.test.cards.single.big;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TerritoryForgeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TerritoryForge Territory Forge} {4}{R}
     * Artifact
     * When Territory Forge enters the battlefield, if you cast it, exile target artifact or land.
     * Territory Forge has all activated abilities of the exiled card.
     */
    private static final String forge = "Territory Forge";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Braidwood Cup"); // {T}: You gain 1 life.
        addCard(Zone.HAND, playerA, forge);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forge);
        addTarget(playerA, "Braidwood Cup");

        checkExileCount("After etb, Cup in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Braidwood Cup", 1);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertTapped(forge, true);
    }
}
