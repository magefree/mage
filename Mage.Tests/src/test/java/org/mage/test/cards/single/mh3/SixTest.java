package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SixTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.Six Six} {2}{G}
     * Legendary Creature — Treefolk
     * Reach
     * Whenever Six attacks, mill three cards. You may put a land card from among them into your hand.
     * As long as it's your turn, nonland permanent cards in your graveyard have retrace.
     * 2/4
     */
    private static final String six = "Six";

    @Test
    public void test_Retrace_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, six);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Taiga");

        checkPlayableAbility("has retrace", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Grizzly Bears with retrace", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears with retrace");
        setChoice(playerA, "Taiga"); // discard to cast with Retrace

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Forest", true, 2);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Taiga", 1);
    }

    @Test
    public void test_Retrace_Flash_YourTurnOnly() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, six);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.GRAVEYARD, playerA, "Ambush Viper");
        addCard(Zone.HAND, playerA, "Taiga");

        checkPlayableAbility("1: has retrace your turn", 1, PhaseStep.UPKEEP, playerA,
                "Cast Ambush Viper with retrace", true);
        checkPlayableAbility("2: hasn't retrace not your turn", 2, PhaseStep.UPKEEP, playerA,
                "Cast Ambush Viper with retrace", false);
        checkPlayableAbility("3: has retrace your turn", 3, PhaseStep.UPKEEP, playerA,
                "Cast Ambush Viper with retrace", true);

        castSpell(3, PhaseStep.UPKEEP, playerA, "Ambush Viper with retrace");
        setChoice(playerA, "Taiga"); // discard to cast with Retrace

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertTappedCount("Forest", true, 2);
        assertPermanentCount(playerA, "Ambush Viper", 1);
        assertGraveyardCount(playerA, "Taiga", 1);
    }

    @Test
    public void test_Retrace_Adventure() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, six);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.GRAVEYARD, playerA, "Bonecrusher Giant");
        addCard(Zone.HAND, playerA, "Taiga");

        checkPlayableAbility("Giant has retrace", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Bonecrusher Giant with retrace", true);
        checkPlayableAbility("Stomp doesn't have retrace (not permanent)", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Stomp with retrace", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bonecrusher Giant with retrace");
        setChoice(playerA, "Taiga"); // discard to cast with Retrace

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Mountain", true, 3);
        assertPermanentCount(playerA, "Bonecrusher Giant", 1);
        assertGraveyardCount(playerA, "Taiga", 1);
    }

    @Test
    public void test_Retrace_MDFC() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, six);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Egon, God of Death"); // MDFC creature/artifact
        addCard(Zone.HAND, playerA, "Taiga");

        checkPlayableAbility("Egon, God of Death has retrace", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Egon, God of Death with retrace", true);
        checkPlayableAbility("Throne of Death has retrace", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Throne of Death with retrace", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Throne of Death with retrace");
        setChoice(playerA, "Taiga"); // discard to cast with Retrace

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Swamp", true, 1);
        assertPermanentCount(playerA, "Throne of Death", 1);
        assertGraveyardCount(playerA, "Taiga", 1);
    }

}
