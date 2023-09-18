package org.mage.test.cards.single.mh2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class GarthOneEyeTest extends CardTestPlayerBase {

    private static final String garth = "Garth One-Eye";
    private static final String disenchant = "Disenchant";
    private static final String braingeyser = "Braingeyser";
    private static final String terror = "Terror";
    private static final String dragon = "Shivan Dragon";
    private static final String regrowth = "Regrowth";
    private static final String lotus = "Black Lotus";
    private static final String courser = "Nyxborn Courser";

    @Test
    public void testDisenchant() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, garth);
        addCard(Zone.BATTLEFIELD, playerA, courser);

        setChoice(playerA, disenchant);
        setChoice(playerA, true);
        addTarget(playerA, courser);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(garth, true);
        assertTapped("Plains", true);
        assertPermanentCount(playerA, courser, 0);
        assertGraveyardCount(playerA, disenchant, 0);
        assertGraveyardCount(playerA, courser, 1);
    }

    @Test
    public void testBraingeyser() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, garth);

        setChoice(playerA, braingeyser);
        setChoice(playerA, true);
        setChoice(playerA, "X=3");
        addTarget(playerA, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(garth, true);
        assertTapped("Island", true);
        assertGraveyardCount(playerA, braingeyser, 0);
        assertHandCount(playerA, 3);
    }

    @Test
    public void testTerror() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, garth);
        addCard(Zone.BATTLEFIELD, playerA, courser);

        setChoice(playerA, terror);
        setChoice(playerA, true);
        addTarget(playerA, courser);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(garth, true);
        assertTapped("Swamp", true);
        assertPermanentCount(playerA, courser, 0);
        assertGraveyardCount(playerA, terror, 0);
        assertGraveyardCount(playerA, courser, 1);
    }

    @Test
    public void testShivanDragon() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, garth);

        setChoice(playerA, dragon);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(garth, true);
        assertTapped("Mountain", true);
        Permanent permanent = getPermanent(dragon);
        Assert.assertNotNull(dragon + " should be on the battlefield", permanent);
        Assert.assertTrue(dragon + " should be a token", permanent instanceof PermanentToken);
    }

    @Test
    public void testRegrowth() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, garth);
        addCard(Zone.GRAVEYARD, playerA, courser);

        setChoice(playerA, regrowth);
        setChoice(playerA, true);
        addTarget(playerA, courser);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(garth, true);
        assertTapped("Forest", true);
        assertHandCount(playerA, courser, 1);
        assertGraveyardCount(playerA, regrowth, 0);
        assertGraveyardCount(playerA, courser, 0);
    }

    @Test
    public void testBlackLotus() {
        addCard(Zone.BATTLEFIELD, playerA, garth);

        setChoice(playerA, lotus);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(garth, true);
        Permanent permanent = getPermanent(lotus);
        Assert.assertNotNull(lotus + " should be on the battlefield", permanent);
        Assert.assertTrue(lotus + " should be a token", permanent instanceof PermanentToken);
    }
}
