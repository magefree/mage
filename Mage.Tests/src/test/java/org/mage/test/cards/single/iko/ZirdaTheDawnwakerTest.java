package org.mage.test.cards.single.iko;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ZirdaTheDawnwakerTest extends CardTestPlayerBase {

    private static final String zirda = "Zirda, the Dawnwaker";
    private static final String golem = "Igneous Golem";
    private static final String mauler = "Barkhide Mauler";
    private static final String geth = "Geth, Lord of the Vault";
    private static final String lion = "Silvercoat Lion";

    @Test
    public void testReduceToOne() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, zirda);
        addCard(Zone.BATTLEFIELD, playerA, golem);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Swamp", true);
        assertAbility(playerA, golem, TrampleAbility.getInstance(), true);
    }

    @Test
    public void testCycling() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, zirda);
        addCard(Zone.HAND, playerA, mauler);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Swamp", true);
        assertGraveyardCount(playerA, mauler, 1);
    }

    @Test
    public void testWithGeth() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, zirda);
        addCard(Zone.BATTLEFIELD, playerA, geth);
        addCard(Zone.GRAVEYARD, playerB, lion);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{B}");
        setChoice(playerA, "X=2");
        addTarget(playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertTapped(lion, true);
        assertGraveyardCount(playerB, 2);
        assertGraveyardCount(playerB, lion, 0);
    }

    @Test
    public void testWithGeth2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, geth);
        addCard(Zone.GRAVEYARD, playerB, zirda);
        addCard(Zone.GRAVEYARD, playerB, lion);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{B}");
        setChoice(playerA, "X=3");
        addTarget(playerA, zirda);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X}{B}");
        setChoice(playerA, "X=2");
        addTarget(playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, zirda, 1);
        assertPermanentCount(playerA, lion, 1);
        assertTapped(zirda, true);
        assertTapped(lion, true);
        assertGraveyardCount(playerB, 2 + 3);
        assertGraveyardCount(playerB, zirda, 0);
        assertGraveyardCount(playerB, lion, 0);
    }
}
