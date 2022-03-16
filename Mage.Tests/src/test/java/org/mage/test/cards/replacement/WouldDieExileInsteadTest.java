package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by escplan9
 */
public class WouldDieExileInsteadTest extends CardTestPlayerBase {

    /*
    * Reported bug: Damnation with Kalitas, Traitor of Ghet on my side and 3 opponent creatures, it only exiled 1 creature giving me only 1 zombie instead of 3.
     */
    @Test
    public void kalitasDamnationInteraction() {

        /*
        Kalitas, Traitor of Ghet {2}{B}{B} 3/4 lifelink - Legendary Vampire
        If a nontoken creature an opponent controls would die, instead exile that card and put a 2/2 black Zombie creature token onto the battlefield.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Kalitas, Traitor of Ghet", 1);
        /*
        Damnation {2}{B}{B} - Sorcery
         Destroy all creatures. They can't be regenerated.
         */
        addCard(Zone.HAND, playerA, "Damnation", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Roots", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Sigiled Starfish", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Kalitas, Traitor of Ghet", 1);
        assertGraveyardCount(playerA, "Damnation", 1);
        assertExileCount("Bronze Sable", 1);
        assertExileCount("Wall of Roots", 1);
        assertExileCount("Sigiled Starfish", 1);
        assertGraveyardCount(playerB, 0); // all 3 creatures of playerB should be exiled not in graveyard
        assertExileCount("Kalitas, Traitor of Ghet", 0); // player controlled, not opponent so not exiled
        assertPermanentCount(playerA, "Zombie Token", 3); // 3 tokens generated from exiling 3 opponent's creatures
    }
    
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
     * Incendiary Flow is worded slightly differently and would not exile here. See issue #3359 for details.
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
        Incendiary Flow deals 3 damage to any target. If a creature dealt damage this way would die this turn, exile it instead.
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
        assertGraveyardCount(playerB, hGiant, 1);
        assertExileCount(playerB, hGiant, 0);
    }
}
