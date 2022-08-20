package org.mage.test.cards.single.afr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class VorpalSwordTest extends CardTestPlayerBase {
    // Equipped creature gets +2/+0 and has deathtouch.
    // {5}{B}{B}{B}: Until end of turn, Vorpal Sword gains "Whenever equipped creature deals combat damage to a player, that player loses the game."
    // Equip {B}{B}
    private final String vorpalSword = "Vorpal Sword";

    @Test
    public void loseTheGame(){
        addCard(Zone.BATTLEFIELD, playerA, vorpalSword);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Grizzly Bears");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{B}{B}{B}");
        attack(3, playerA,"Grizzly Bears", playerB);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
    }

}
