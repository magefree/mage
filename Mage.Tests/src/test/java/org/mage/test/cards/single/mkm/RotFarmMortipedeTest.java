package org.mage.test.cards.single.mkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.r.RotFarmMortipede Rot Farm Mortipede} {3}{B}
 * Whenever one or more creature cards leave your graveyard, Rot Farm Mortipede gets +1/+0
 * and gains menace and lifelink until end of turn.
 * <p>
 * CR 603.10a: leaves-the-graveyard abilities look back to the state before the event, so a
 * Mortipede returning itself from the graveyard doesn't trigger its own ability.
 */
public class RotFarmMortipedeTest extends CardTestPlayerBase {

    private static final String mortipede = "Rot Farm Mortipede";
    private static final String doesntAddUp = "It Doesn't Add Up"; // {3}{B}{B} instant, return target creature card from your graveyard to the battlefield

    @Test
    public void test_SelfReturnDoesNotTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, mortipede);
        addCard(Zone.HAND, playerA, doesntAddUp);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, doesntAddUp, mortipede);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkStackSize("no Mortipede trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkPT("no +1/+0", 1, PhaseStep.PRECOMBAT_MAIN, playerA, mortipede, 3, 4);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, mortipede, 1);
        assertPowerToughness(playerA, mortipede, 3, 4);
    }

    @Test
    public void test_OtherCreatureLeavingStillTriggers() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mortipede);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, doesntAddUp);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, doesntAddUp, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("Mortipede got +1/+0", 1, PhaseStep.PRECOMBAT_MAIN, playerA, mortipede, 4, 4);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, mortipede, 4, 4);
    }
}
