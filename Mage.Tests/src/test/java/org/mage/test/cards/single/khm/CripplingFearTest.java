package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CripplingFearTest extends CardTestPlayerBase {
    private static final String fear = "Crippling Fear";
    private static final String lion = "Silvercoat Lion";
    private static final String elf = "Cylian Elf";

    @Test
    public void testChooseElf() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, elf);
        addCard(Zone.HAND, playerA, fear);

        setChoice(playerA, "Elf");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 0);
        assertPermanentCount(playerA, elf, 1);
        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, elf, 0);
    }

    @Test
    public void testChooseCat() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, elf);
        addCard(Zone.HAND, playerA, fear);

        setChoice(playerA, "Cat");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertPermanentCount(playerA, elf, 0);
        assertGraveyardCount(playerA, lion, 0);
        assertGraveyardCount(playerA, elf, 1);
    }

    @Test
    public void testChooseGoblin() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, elf);
        addCard(Zone.HAND, playerA, fear);

        setChoice(playerA, "Goblin");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 0);
        assertPermanentCount(playerA, elf, 0);
        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, elf, 1);
    }
}
