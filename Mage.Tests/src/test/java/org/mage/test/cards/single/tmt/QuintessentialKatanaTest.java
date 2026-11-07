package org.mage.test.cards.single.tmt;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class QuintessentialKatanaTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.q.QuintessentialKatana}
     * <p>
     * {W} Artifact - Equipment<br>
     * Equipped creature gets +1/+1 and has "Whenever this creature deals combat damage, untap it and you gain 2
     * life."<br>
     * Whenever a Ninja you control enters, you may attach this Equipment to it.<br>
     * Equip {2}
     */
    private static final String KATANA = "Quintessential Katana";

    @Test
    public void test_CombatDamageToPlayer_UntapAndGainLife() {
        addCard(Zone.BATTLEFIELD, playerA, KATANA);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // {2} for equip
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        attack(1, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        assertTapped("Grizzly Bears", false);
        assertLife(playerB, 20 - 3);
        assertLife(playerA, 20 + 2);
    }

    @Test
    public void test_CombatDamageToCreature_UntapAndGainLife() {
        addCard(Zone.BATTLEFIELD, playerA, KATANA);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // {2} for equip
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Wood"); // wall 0/3, defender

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        attack(1, playerA, "Grizzly Bears", playerB);
        block(1, playerB, "Wall of Wood", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Wall of Wood", 1);
        assertTapped("Grizzly Bears", false);
        assertLife(playerB, 20);
        assertLife(playerA, 20 + 2);
    }
}
