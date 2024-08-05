package org.mage.test.cards.triggers.damage;

import mage.abilities.common.DestroyPlaneswalkerWhenDamagedTriggeredAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jimga150
 */
public class DestroyPlaneswalkerWhenDamagedTest extends CardTestPlayerBase {

    @Test
    public void nullFilterTest() {
        addCustomCardWithAbility("nullFilterDPwD", playerA, new DestroyPlaneswalkerWhenDamagedTriggeredAbility(), null, CardType.CREATURE, "", Zone.BATTLEFIELD);

        addCard(Zone.BATTLEFIELD, playerB, "Phyrexian Walker", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Chandra, Acolyte of Flame");

        attack(1, playerA, "nullFilterDPwD", "Chandra, Acolyte of Flame");
        block(1, playerB, "Phyrexian Walker", "nullFilterDPwD");

        attack(3, playerA, "nullFilterDPwD", "Chandra, Acolyte of Flame");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "nullFilterDPwD", 1, 1);
        assertPermanentCount(playerB, "Phyrexian Walker", 1);
        assertGraveyardCount(playerB, "Chandra, Acolyte of Flame", 1);
    }

}
