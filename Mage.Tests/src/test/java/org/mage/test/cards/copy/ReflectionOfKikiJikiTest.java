
package org.mage.test.cards.copy;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ReflectionOfKikiJikiTest extends CardTestPlayerBase {

    /**
     * Reflection of Kiki-Jiki creates a copy of Humble Defector, activate
     * Humble defector, token gets sacrificed while under opponents control.
     */
    @Test
    public void testTokenNotSacrificedIfNotControlled() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Tap target creature you don't control.
        // Overload {3}{U}
        addCard(Zone.HAND, playerA, "Blustersquall", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Reflection of Kiki-Jiki", 1);
        // {T}: Draw two cards. Target opponent gains control of Humble Defector. Activate this ability only during your turn.
        addCard(Zone.BATTLEFIELD, playerB, "Humble Defector", 1);

        castSpell(2, PhaseStep.UPKEEP, playerA, "Blustersquall", "Humble Defector"); // Tap nontoken Defector so only the Token can be used later

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}, {T}: Create a token that's a copy of another target nonlegendary creature you control", "Humble Defector");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: Draw two cards. Target opponent gains control");

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerB, 3); // normal 3 draw from turn two + 2 from Defector

        assertGraveyardCount(playerA, "Blustersquall", 1);
        assertPermanentCount(playerB, "Humble Defector", 1);
        assertPermanentCount(playerA, "Humble Defector", 1);

    }
}
