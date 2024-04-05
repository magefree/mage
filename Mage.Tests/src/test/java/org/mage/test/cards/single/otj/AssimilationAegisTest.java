package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AssimilationAegisTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AssimilationAegis Assimilation Aegis} {1}{W}{U}
     * Artifact — Equipment
     * When Assimilation Aegis enters the battlefield, exile up to one target creature until Assimilation Aegis leaves the battlefield.
     * Whenever Assimilation Aegis becomes attached to a creature, for as long as Assimilation Aegis remains attached to it, that creature becomes a copy of a creature card exiled with Assimilation Aegis.
     * Equip {2}
     */
    private static final String aegis = "Assimilation Aegis";

    @Test
    public void test_Equip_Equip_Disenchant() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 9);
        addCard(Zone.HAND, playerA, aegis);
        addCard(Zone.HAND, playerA, "Disenchant"); // to test when Aegis leaves the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aegis);
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkExileCount("After etb: Bears in exile ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("After etb: No Bears in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkPermanentCount("After etb: Piker in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 1);
        checkPermanentCount("After etb: Vanguard in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Goblin Piker");
        setChoice(playerA, "Grizzly Bears"); // choose what card to copy (there could be multiple)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("After equip Piker: Bears (copy) in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("After equip Piker: No Piker in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 0);
        checkPermanentCount("After equip Piker: Vanguard in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Elite Vanguard");
        setChoice(playerA, "Grizzly Bears"); // choose what card to copy (there could be multiple)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("After equip Vanguard: Bears (copy) in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("After equip Vanguard: Piker in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 1);
        checkPermanentCount("After equip Vanguard: No Vanguard in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", 0);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disenchant", aegis);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("After Disenchant: Bears in play (on playerB side)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grizzly Bears", 1);
        checkPermanentCount("After Disenchant: Piker in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 1);
        checkPermanentCount("After Disenchant: Vanguard in play ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, aegis, 1);
    }
}
