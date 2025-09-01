package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class ElectroAssaultingBatteryTest extends CardTestPlayerBase {

    /*
    Electro, Assaulting Battery
    {1}{R}{R}
    Legendary Creature - Human Villain
    Flying
    You don't lose unspent red mana as steps and phases end.
    Whenever you cast an instant or sorcery spell, add {R}.
    When Electro leaves the battlefield, you may pay {X}. When you do, he deals X damage to target player.
    2/3
    */
    private static final String electroAssaultingBattery = "Electro, Assaulting Battery";

    /*
    Pyretic Ritual
    {1}{R}
    Instant
    Add {R}{R}{R}.
    */
    private static final String pyreticRitual = "Pyretic Ritual";

    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
    */
    private static final String lightningBolt = "Lightning Bolt";


    @Test
    public void testElectroAssaultingBattery() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, electroAssaultingBattery);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerA, pyreticRitual);
        addCard(Zone.HAND, playerB, lightningBolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyreticRitual);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, lightningBolt, electroAssaultingBattery);
        setChoice(playerA, true);
        setChoiceAmount(playerA, 4); // 3 from ritual + 1 from electro
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
    }
}