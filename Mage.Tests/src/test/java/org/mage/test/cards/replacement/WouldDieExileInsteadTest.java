package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.turn.Phase;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by escplan9
 */
public class WouldDieExileInsteadTest extends CardTestPlayerBase {

    /*
     * Reported bug #3359 (NOTE: test is failing due to bug in code)
     * Creature with 2 toughness targetted by Soul-Scar Mage and Magma Spray was not exiled when it died
     */
    @Test
    public void magmaSpray_SoulScarMageEffect_ShouldExile() {

        /*
         Magma Spray {R}
        Instant
        Magma Spray deals 2 damage to target creature. If that creature would die this turn, exile it instead.
         */
        String mSpray = "Magma Spray";

        /*
        Soul-Scar Mage {R}
        Creature - Human Wizard 1/2
        Prowess
        If a source you control would deal noncombat damage to a creature an opponent controls, put that many -1/-1 counters on that creature instead.
         */
        String ssMage = "Soul-Scar Mage";

        String gBears = "Grizzly Bears"; // {1}{G} 2/2

        addCard(Zone.BATTLEFIELD, playerA, ssMage);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, mSpray);
        addCard(Zone.BATTLEFIELD, playerB, gBears);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mSpray, gBears);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, ssMage, 1);
        assertGraveyardCount(playerA, mSpray, 1);
        assertPermanentCount(playerB, gBears, 0);
        assertPowerToughness(playerA, ssMage, 2, 3); // prowess triggered
        assertGraveyardCount(playerB, gBears, 0);
        assertExileCount(playerB, gBears, 1);
    }

    /*
     * Incendiary Flow only exiles if a creature dealt damage by it would die.
     * With Soul-Scar Mage it would die from the -1/-1 counters, not the damage by Incendiary Flow.
     */
    @Test
    public void incendiaryFlow_SoulScarMageEffect_ShouldNotExile() {

        /*
        Soul-Scar Mage {R}
        Creature - Human Wizard 1/2
        Prowess
        If a source you control would deal noncombat damage to a creature an opponent controls, put that many -1/-1 counters on that creature instead.
         */
        String ssMage = "Soul-Scar Mage";

        /*
         Incendiary Flow {1}{R}
        Sorcery
        Incendiary Flow deals 3 damage to target creature or player. If a creature dealt damage this way would die this turn, exile it instead.
         */
        String iFlow = "Incendiary Flow";

        String hGiant = "Hill Giant"; // {3}{R} 3/3

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, ssMage);
        addCard(Zone.HAND, playerA, iFlow);
        addCard(Zone.BATTLEFIELD, playerB, hGiant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, iFlow, hGiant);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, ssMage, 1);
        assertGraveyardCount(playerA, iFlow, 1);
        assertPermanentCount(playerB, hGiant, 0);
        assertPowerToughness(playerA, ssMage, 2, 3); // prowess triggered
        assertExileCount(playerB, hGiant, 0);
        assertGraveyardCount(playerB, hGiant, 1);
    }
}
