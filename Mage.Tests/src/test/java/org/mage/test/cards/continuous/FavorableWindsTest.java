package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class FavorableWindsTest extends CardTestPlayerBase {

    /**
     * Tests that "Favorable Winds" boost only flying creatures and only on controller's side
     */
    @Test
    public void testBoostForFlyingCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Favorable Winds", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Looter", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sky Spirit", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk Looter", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Sky Spirit", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        int countSkySpirit = 0;
        int countMerfolkLooter = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Sky Spirit")) {
                countSkySpirit++;
                // should get +1/+1, original is 2/2
                Assertions.assertEquals(3, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(3, permanent.getToughness().getValue(), "Toughness is not the same");
            } else if (permanent.getName().equals("Merfolk Looter")) {
                countMerfolkLooter++;
                // should NOT get +1/+1, original is 1/1
                Assertions.assertEquals(1, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(1, permanent.getToughness().getValue(), "Toughness is not the same");
            }
        }
        Assertions.assertEquals(2, countSkySpirit);
        Assertions.assertEquals(2, countMerfolkLooter);

        countSkySpirit = 0;
        countMerfolkLooter = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerB.getId())) {
            if (permanent.getName().equals("Sky Spirit")) {
                countSkySpirit++;
                // should NOT +1/+1, original is 2/2
                Assertions.assertEquals(2, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(2, permanent.getToughness().getValue(), "Toughness is not the same");
            } else if (permanent.getName().equals("Merfolk Looter")) {
                countMerfolkLooter++;
                // should NOT get +1/+1, original is 1/1
                Assertions.assertEquals(1, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(1, permanent.getToughness().getValue(), "Toughness is not the same");
            }
        }
        Assertions.assertEquals(2, countSkySpirit);
        Assertions.assertEquals(2, countMerfolkLooter);
    }

    /**
     * Tests effect from several "Favorable Winds" cards
     */
    @Test
    public void testMultiBoostForFlyingCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Favorable Winds", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Looter", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sky Spirit", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk Looter", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Sky Spirit", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        int countSkySpirit = 0;
        int countMerfolkLooter = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Sky Spirit")) {
                countSkySpirit++;
                // should get +1/+1, original is 2/2
                Assertions.assertEquals(5, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(5, permanent.getToughness().getValue(), "Toughness is not the same");
            } else if (permanent.getName().equals("Merfolk Looter")) {
                countMerfolkLooter++;
                // should NOT get +1/+1, original is 1/1
                Assertions.assertEquals(1, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(1, permanent.getToughness().getValue(), "Toughness is not the same");
            }
        }
        Assertions.assertEquals(2, countSkySpirit);
        Assertions.assertEquals(2, countMerfolkLooter);

        countSkySpirit = 0;
        countMerfolkLooter = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerB.getId())) {
            if (permanent.getName().equals("Sky Spirit")) {
                countSkySpirit++;
                // should NOT +1/+1, original is 2/2
                Assertions.assertEquals(2, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(2, permanent.getToughness().getValue(), "Toughness is not the same");
            } else if (permanent.getName().equals("Merfolk Looter")) {
                countMerfolkLooter++;
                // should NOT get +1/+1, original is 1/1
                Assertions.assertEquals(1, permanent.getPower().getValue(), "Power is not the same");
                Assertions.assertEquals(1, permanent.getToughness().getValue(), "Toughness is not the same");
            }
        }
        Assertions.assertEquals(2, countSkySpirit);
        Assertions.assertEquals(2, countMerfolkLooter);
    }
}
