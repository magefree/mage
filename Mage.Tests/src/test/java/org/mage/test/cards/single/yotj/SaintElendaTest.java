package org.mage.test.cards.single.yotj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SaintElendaTest extends CardTestPlayerBase {

    @Test
    public void testChosenSpellIsFreeAndCreatesAvatarFromLifeGained() {
        addCard(Zone.HAND, playerA, "Saint Elenda");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saint Elenda");
        setChoice(playerA, "Ritual of Rejuvenation");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ritual of Rejuvenation");
        setChoice(playerA, "Saint Elenda");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerA, 24);
        assertPowerToughness(playerA, "Avatar Token", 4, 4);
        assertGraveyardCount(playerA, "Ritual of Rejuvenation", 1);
    }

    @Test
    public void testFreePermissionExpiresAndNoLifeMeansNoAvatar() {
        addCard(Zone.HAND, playerA, "Saint Elenda");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saint Elenda");
        setChoice(playerA, "Inspiring Cleric");
        runCode("remove mana", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerA.getId())) {
                if (permanent.isLand(game)) {
                    permanent.tap(null, game);
                }
            }
        });
        checkPlayableAbility("free permission expired", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Inspiring Cleric", false);
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Inspiring Cleric", 1);
        assertPermanentCount(playerA, "Avatar Token", 0);
    }

}
