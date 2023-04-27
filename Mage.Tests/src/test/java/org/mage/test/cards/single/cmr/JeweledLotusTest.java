package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */

public class JeweledLotusTest extends CardTestCommanderDuelBase {

    @Test
    public void test_CantUseManaForNormalSpells() {
        // {T}, Sacrifice Jeweled Lotus: Add three mana of any one color. Spend this mana only to cast your commander.
        addCard(Zone.BATTLEFIELD, playerA, "Jeweled Lotus", 1);
        //
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);

        // generate mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Green");
        checkManaPool("new mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CanUseManaForCommander() {
        // {T}, Sacrifice Jeweled Lotus: Add three mana of any one color. Spend this mana only to cast your commander.
        addCard(Zone.BATTLEFIELD, playerA, "Jeweled Lotus", 1);
        //
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1);

        // Jeweled Lotus is mana ability -- game will use that mana for playable too, so must be playable here
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);

        // generate mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Green");
        checkManaPool("new mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);

        // play commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }
}
