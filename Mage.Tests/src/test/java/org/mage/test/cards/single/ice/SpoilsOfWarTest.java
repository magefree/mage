package org.mage.test.cards.single.ice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class SpoilsOfWarTest extends CardTestCommander4Players {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 3);
        addCard(Zone.GRAVEYARD, playerB, "Solemn Simulacrum");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Spoils of War");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        setChoice(playerA, playerB.getName());
        addTargetAmount(playerA, "Balduvian Bears", 3);
        addTargetAmount(playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 5, 5);
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertTappedCount("Swamp", true, 5);
    }

    @Test
    public void testCantCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 6);
        addCard(Zone.GRAVEYARD, playerC, "Balduvian Bears", 6);
        addCard(Zone.GRAVEYARD, playerD, "Balduvian Bears", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Spoils of War");

        checkPlayableAbility("shouldn't be able to cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Spoils of War", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
