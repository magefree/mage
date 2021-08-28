package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9 (Derek Monturo)
 */
public class AthreosTest extends CardTestPlayerBase {

    /*
     * Reported bug: Athreos interaction with Cartel Aristocrat not working.
     * Athreos ability is not being triggered from Cartel's activated ability to sacrifice a creature.
     */
    @Test
    public void cartelAristrocraftInteractionOpponentDoesNotPayLife() {

        String athreos = "Athreos, God of Passage";
        String cAristrocrat = "Cartel Aristocrat";
        String gBears = "Grizzly Bears";

        /*
        Athreos, God of Passage {1}{W}{B}
        Legendary Enchantment Creature - God 5/4
        Indestructible
        As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.
         */
        addCard(Zone.BATTLEFIELD, playerA, athreos);

        /*
        Cartel Aristocrat {W}{B}
        Creature - Human Advisor 2/2
        Sacrifice another creature: Cartel Aristocrat gains protection from the color of your choice until end of turn.
        */
        addCard(Zone.BATTLEFIELD, playerA, cAristrocrat);

        addCard(Zone.BATTLEFIELD, playerA, gBears); // {1}{G} 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, gBears); // sac bears to Cartel activated ability
        setChoice(playerA, "Red"); // color chosen for Cartel

        setChoice(playerB, false); // opponent does not pay 3 life

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, gBears, 1);
        assertGraveyardCount(playerA, gBears, 0);
        assertLife(playerB, 20);
    }

    /*
 * Reported bug: Athreos interaction with Cartel Aristocrat not working.
 * Athreos ability is not being triggered from Cartel's activated ability to sacrifice a creature.
 */
    @Test
    public void cartelAristrocraftInteractionOpponentPaysLife() {

        String athreos = "Athreos, God of Passage";
        String cAristrocrat = "Cartel Aristocrat";
        String gBears = "Grizzly Bears";

        /*
        Athreos, God of Passage {1}{W}{B}
        Legendary Enchantment Creature - God 5/4
        Indestructible
        As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.
         */
        addCard(Zone.BATTLEFIELD, playerA, athreos);

        /*
        Cartel Aristocrat {W}{B}
        Creature - Human Advisor 2/2
        Sacrifice another creature: Cartel Aristocrat gains protection from the color of your choice until end of turn.
        */
        addCard(Zone.BATTLEFIELD, playerA, cAristrocrat);

        addCard(Zone.BATTLEFIELD, playerA, gBears); // {1}{G} 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, gBears); // sac bears to Cartel activated ability
        setChoice(playerA, "Red"); // color chosen for Cartel

        setChoice(playerB, true); // opponent does pay 3 life

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, gBears, 0);
        assertGraveyardCount(playerA, gBears, 1);
        assertLife(playerB, 17); // paid life
    }
}
