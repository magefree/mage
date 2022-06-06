
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SearchEntersBattlefieldTest extends CardTestPlayerBase {

    @Test
    public void testLandAfterFetchUntapped() {
        addCard(Zone.HAND, playerA, "Verdant Catacombs");
        addCard(Zone.LIBRARY, playerA, "Forest");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Verdant Catacombs");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay");
        addTarget(playerA, "Forest");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Verdant Catacombs", 1);
        assertPermanentCount(playerA, "Forest", 1);
        assertTapped("Forest", false);
    }
}
