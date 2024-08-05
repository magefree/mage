package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class EarlyWinterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.EarlyWinter Early Winter} {4}{B}
     * Instant
     * Choose one —
     * • Exile target creature.
     * • Target opponent exiles an enchantment they control.
     */
    private static final String winter = "Early Winter";

    @Test
    public void test_ExileEnchantment() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Glorious Anthem");
        addCard(Zone.HAND, playerA, winter);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, winter);
        setModeChoice(playerA, "2"); // Target opponent exiles an enchantment they control
        addTarget(playerA, playerB);
        setChoice(playerB, "Glorious Anthem");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Glorious Anthem", 0);
        assertExileCount(playerB, "Glorious Anthem", 1);
        assertGraveyardCount(playerA, winter, 1);
    }

    @Test
    public void test_ExileEnchantment_WorksOnShroud() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Helix Pinnacle"); // Shroud
        addCard(Zone.HAND, playerA, winter);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, winter);
        setModeChoice(playerA, "2"); // Target opponent exiles an enchantment they control
        addTarget(playerA, playerB);
        setChoice(playerB, "Helix Pinnacle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Helix Pinnacle", 0);
        assertExileCount(playerB, "Helix Pinnacle", 1);
        assertGraveyardCount(playerA, winter, 1);
    }

    @Test
    public void test_ExileEnchantment_CheckControlled() {
        //setStrictChooseMode(true); // enable once the choice for enchantment can be tested when no choice possible

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.HAND, playerA, winter);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, winter);
        setModeChoice(playerA, "2"); // Target opponent exiles an enchantment they control
        //addTarget(playerA, playerB);

        // playerB has no enchantment to choose from.
        //setChoice(playerB, TestPlayer.CHOICE_SKIP);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertGraveyardCount(playerA, winter, 1);
    }
}
