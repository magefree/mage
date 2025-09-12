package org.mage.test.cards.single.neo;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;


public class EaterOfVirtueTest extends CardTestPlayerBase {

    /*
    Eater of Virtue
    {1}
    Legendary Artifact — Equipment

    Whenever equipped creature dies, exile it.

    Equipped creature gets +2/+0.

    As long as a card exiled with Eater of Virtue has flying, equipped creature has flying.
    The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, protection, reach, trample, and vigilance.

    Equip {1}
     */
    public static final String eaterOfVirtue = "Eater of Virtue";

    @Test
    public void testEaterOfVirtue() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, eaterOfVirtue);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Adult Gold Dragon"); // Flying, Lifelink, Haste
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerB, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Adult Gold Dragon");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Adult Gold Dragon");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbilities(playerA, "Balduvian Bears", Arrays.asList(FlyingAbility.getInstance(), LifelinkAbility.getInstance(), HasteAbility.getInstance()));
    }
}
