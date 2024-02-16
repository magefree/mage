package org.mage.test.cards.single.exo;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KeeperOfTheMindTest extends CardTestPlayerBase {
    // Keeper of the Mind {U}{U}
    // Creature — Human Wizard
    // {U}, {T}: Choose target opponent who had at least two more cards
    // in hand than you did as you activated this ability. Draw a card.
    private static final String keeper = "Keeper of the Mind";

    @Test
    public void simpleActivate() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, keeper, 1);
        addCard(Zone.HAND, playerB, "Island", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}: Choose target opponent ");

        // setStrictChooseMode(true); // targetting is weird due to targetAdjuster.
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void activateOtherPlayerTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, keeper, 1);
        addCard(Zone.HAND, playerB, "Island", 2);

        activateAbility(2, PhaseStep.UPKEEP, playerA, "{U}, {T}: Choose target opponent ");

        // setStrictChooseMode(true); // targetting is weird due to targetAdjuster.
        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void cantActivateIfCardDifferenceTooLow() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, keeper, 1);
        addCard(Zone.HAND, playerB, "Island", 1);

        checkPlayableAbility("No Activation", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}: Choose target opponent ", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
    }

    @Test
    public void doubleActivate() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, keeper, 2);
        addCard(Zone.HAND, playerB, "Island", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}: Choose target opponent ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}: Choose target opponent ");

        // setStrictChooseMode(true); // targetting is weird due to targetAdjuster.
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
    }
}
