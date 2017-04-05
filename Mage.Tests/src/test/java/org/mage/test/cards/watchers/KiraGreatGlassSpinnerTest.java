package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by IGOUDT on 30-3-2017.
 */
public class KiraGreatGlassSpinnerTest extends CardTestPlayerBase {
    private final String kira = "Kira, Great Glass-Spinner";
    private final String shock = "Shock";

    @Test
    public void counterFirst(){
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Ugin, the Spirit Dragon"); // starts with 7 Loyality counters

        addCard(Zone.BATTLEFIELD, playerA, kira);
        addCard(Zone.HAND, playerA, shock);
        addCard(Zone.HAND, playerA, shock);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {source} deals 3 damage to target creature or player.", kira);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        Permanent _kira = getPermanent(kira, playerA.getId());
        Assert.assertNotNull(_kira);


    }
}
