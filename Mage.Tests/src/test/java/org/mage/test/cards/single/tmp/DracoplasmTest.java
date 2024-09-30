package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DracoplasmTest extends CardTestPlayerBase {

    private static final String dracoplasm = "Dracoplasm";
    /* Flying
    As Dracoplasm enters, sacrifice any number of creatures. Dracoplasm’s power becomes the total power of those creatures and its toughness becomes their total toughness.
    {R}: Dracoplasm gets +1/+0 until end of turn.
     */
    private static final String wurm = "Craw Wurm"; // 6/4
    private static final String elf = "Elvish Mystic"; // 1/1

    @Test
    public void testSac() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, elf);
        addCard(Zone.HAND, playerA, dracoplasm);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dracoplasm);
        setChoice(playerA, wurm + "^" + elf);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, wurm, 1);
        assertGraveyardCount(playerA, elf, 1);
        assertPowerToughness(playerA, dracoplasm, 7, 5);
    }

}
