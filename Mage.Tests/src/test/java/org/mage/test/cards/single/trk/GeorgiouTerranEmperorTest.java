package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 704.5a
 * If a player has 0 or less life, that player loses the game.
 *
 * @author JayDi85
 */
public class GeorgiouTerranEmperorTest extends CardTestPlayerBase {

    @Test
    public void test_EndStep_LoseAndDraw() {
        // At the beginning of your end step, you lose 3 life and draw a card. Then if your life total is 0 or less,
        // create ten 1/1 red Officer creature tokens.
        addCard(Zone.BATTLEFIELD, playerA, "Georgiou, Terran Emperor");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 3);
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Officer Token", 0);
    }

    @Test
    public void test_EndStep_OnlyYourEndStep() {
        // At the beginning of your end step, you lose 3 life and draw a card. Then if your life total is 0 or less,
        // create ten 1/1 red Officer creature tokens.
        addCard(Zone.BATTLEFIELD, playerA, "Georgiou, Terran Emperor");

        // turn 2 is the opponent's turn
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20);
    }

    @Test
    public void test_EndStep_LifeExactlyZero_TenTokens() {
        addCustomEffect_TargetDamage(playerA, 17);

        // At the beginning of your end step, you lose 3 life and draw a card. Then if your life total is 0 or less,
        // create ten 1/1 red Officer creature tokens.
        addCard(Zone.BATTLEFIELD, playerA, "Georgiou, Terran Emperor");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 17", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 17 - 3);
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Officer Token", 10);
        assertPowerToughness(playerA, "Officer Token", 1, 1);
        assertColor(playerA, "Officer Token", "R", true);
        assertSubtype("Officer Token", SubType.OFFICER);
    }

    @Test
    public void test_EndStep_LifeAboveZero_NoTokens() {
        addCustomEffect_TargetDamage(playerA, 16);

        // At the beginning of your end step, you lose 3 life and draw a card. Then if your life total is 0 or less,
        // create ten 1/1 red Officer creature tokens.
        addCard(Zone.BATTLEFIELD, playerA, "Georgiou, Terran Emperor");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 16", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 16 - 3);
        assertPermanentCount(playerA, "Officer Token", 0);
    }

    @Test
    public void test_DontLoseForNegativeLife() {
        addCustomEffect_TargetDamage(playerA, 25);

        // You don't lose the game for having 0 or less life.
        // At the beginning of your end step, you lose 3 life and draw a card. Then if your life total is 0 or less,
        // create ten 1/1 red Officer creature tokens.
        addCard(Zone.BATTLEFIELD, playerA, "Georgiou, Terran Emperor");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 25", playerA);
        checkPlayerInGame("negative life", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 25 - 3);
        assertPermanentCount(playerA, "Officer Token", 10);
    }

    @Test
    public void test_DontLoseForNegativeLife_OnlyWhileOnBattlefield() {
        addCustomEffect_TargetDamage(playerA, 25);
        addCustomEffect_TargetDestroy(playerA);

        // You don't lose the game for having 0 or less life.
        addCard(Zone.BATTLEFIELD, playerA, "Georgiou, Terran Emperor");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 25", playerA);
        checkPlayerInGame("negative life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA, true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Georgiou, Terran Emperor");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertResult(playerA, GameResult.LOST);
    }
}
