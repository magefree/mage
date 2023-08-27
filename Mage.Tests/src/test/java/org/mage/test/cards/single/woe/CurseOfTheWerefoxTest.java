package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CurseOfTheWerefoxTest extends CardTestPlayerBase {

    /**
     * Curse of the Werefox
     * {2}{G}
     * Sorcery
     * <p>
     * Create a Monster Role token attached to target creature you control. When you do, that creature fights up to one target creature you don’t control.
     */
    private static final String curseWerefox = "Curse of the Werefox";

    /**
     * Nexus Wardens
     * Creature — Satyr Archer
     * <p>
     * Reach
     * Constellation — Whenever an enchantment enters the battlefield under your control, you gain 2 life.
     * <p>
     * 1/4
     */
    private static final String wardens = "Nexus Wardens";

    /**
     * Azorius First-Wing
     * {W}{U}
     * Creature — Griffin
     * <p>
     * Flying, protection from enchantments
     * <p>
     * 2/2
     */
    private static final String azoriusFirstWing = "Azorius First-Wing";

    // Checks that the "When you do" part of the ability does not trigger.
    @Test
    public void noTokenCreated() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, curseWerefox);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.BATTLEFIELD, playerA, azoriusFirstWing);
        addCard(Zone.BATTLEFIELD, playerB, wardens);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curseWerefox, azoriusFirstWing);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20); // no Constellation trigger.
        assertDamageReceived(playerA, wardens, 0);
        assertDamageReceived(playerA, azoriusFirstWing, 0);
        assertDamageReceived(playerB, wardens, 0);
        assertPermanentCount(playerA, "Monster", 0);
    }

    @Test
    public void usualBehavior() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, curseWerefox);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.BATTLEFIELD, playerB, wardens);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curseWerefox, wardens);
        setChoice(playerA, "<i>Constellation</i>");
        addTarget(playerA, wardens); // Choosing second target for the fight.
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20 + 2); // Constellation Trigger.
        assertPermanentCount(playerA, wardens, 1);
        assertPermanentCount(playerB, wardens, 1);
        assertDamageReceived(playerA, wardens, 1);
        assertDamageReceived(playerB, wardens, 2);
        assertPermanentCount(playerA, "Monster", 1);
    }
}