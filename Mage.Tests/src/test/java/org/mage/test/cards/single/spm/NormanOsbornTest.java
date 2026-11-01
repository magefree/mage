package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class NormanOsbornTest extends CardTestPlayerBase {

    /*
    Norman Osborn // Green Goblin
    Green Goblin {1}{U}{B}{R}
    Legendary Creature - Goblin Human Villain
    Flying, menace
    Spells you cast from your graveyard cost {2} less to cast.
    Goblin Formula -- Each nonland card in your graveyard has mayhem. The mayhem cost is equal to the card's mana cost.
    3/3
    */
    private static final String greenGoblin = "Green Goblin";

    // Discard a card: Putrid Imp gains flying until end of turn.
    private static final String imp = "Putrid Imp";

    private static final String bears = "Grizzly Bears"; // {1}{G}

    // https://github.com/magefree/mage/issues/16660
    @Test
    public void test_DiscardWithGreenGoblinOnBattlefield() {
        addCard(Zone.HAND, playerA, greenGoblin);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, greenGoblin);

        // no nonland card in the graveyard yet, so nothing has mayhem when the card is discarded
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, bears);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, bears + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, greenGoblin, 1);
        assertPermanentCount(playerA, bears, 1);
    }

    @Test
    public void test_DiscardThenCastGreenGoblin() {
        addCard(Zone.HAND, playerA, greenGoblin);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // discarded before Green Goblin is on the battlefield
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, greenGoblin);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bears + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, greenGoblin, 1);
        assertPermanentCount(playerA, bears, 1);
    }
}
