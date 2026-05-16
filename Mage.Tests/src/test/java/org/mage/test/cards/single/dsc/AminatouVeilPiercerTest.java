package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AminatouVeilPiercerTest extends CardTestPlayerBase {

    private static final String AMINATOU = "Aminatou, Veil Piercer";
    private static final String CAST_OUT = "Cast Out";
    private static final String MEMNITE = "Memnite";

    @Test
    public void testFirstDrawnEnchantmentGetsReducedMiracleCost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, AMINATOU);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, MEMNITE);
        addCard(Zone.LIBRARY, playerA, MEMNITE, 3);
        addCard(Zone.LIBRARY, playerB, MEMNITE, 3);
        addCard(Zone.LIBRARY, playerB, CAST_OUT);
        skipInitShuffling();

        addTarget(playerB, TestPlayer.TARGET_SKIP);
        setChoice(playerB, MEMNITE);
        setChoice(playerB, true);
        addTarget(playerB, MEMNITE);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, CAST_OUT, 1);
        assertExileCount(playerA, MEMNITE, 1);
        assertHandCount(playerB, CAST_OUT, 0);
    }

    @Test
    public void testSecondDrawnEnchantmentDoesNotGetMiracle() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, AMINATOU);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Think Twice");
        addCard(Zone.LIBRARY, playerA, MEMNITE, 3);
        addCard(Zone.LIBRARY, playerB, CAST_OUT);
        addCard(Zone.LIBRARY, playerB, MEMNITE);
        skipInitShuffling();

        addTarget(playerB, TestPlayer.TARGET_SKIP);
        setChoice(playerB, CAST_OUT);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Think Twice");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerB, CAST_OUT, 1);
        assertPermanentCount(playerB, CAST_OUT, 0);
        assertGraveyardCount(playerB, "Think Twice", 1);
    }
}
