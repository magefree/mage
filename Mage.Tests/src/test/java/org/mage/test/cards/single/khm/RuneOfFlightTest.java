package org.mage.test.cards.single.khm;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RuneOfFlightTest extends CardTestPlayerBase {

    @Test
    public void testGivesFlying() {
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Rune of Flight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rune of Flight", "Bonesplitter");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
    }

    @Test
    public void testGivesFlying2() {
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Rune of Flight");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rune of Flight", "Bonesplitter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
    }
}
