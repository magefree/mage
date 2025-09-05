package org.mage.test.cards.single.dft;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AatchikEmeraldRadianTest extends CardTestPlayerBase {

    /*
    Aatchik, Emerald Radian
    {3}{B}{B}{G}
    Legendary Creature — Insect Druid
    When Aatchik enters, create a 1/1 green Insect creature token for each artifact and/or creature card in your graveyard.
    Whenever another Insect you control dies, put a +1/+1 counter on Aatchik. Each opponent loses 1 life.
    3/3
     */
    private static final String aatchik = "Aatchik, Emerald Radian";

    /*
    Springheart Nantuko
    {1}{G}
    Enchantment Creature — Insect Monk
    Bestow {1}{G}
    Enchanted creature gets +1/+1.
    Landfall — Whenever a land you control enters, you may pay {1}{G} if this permanent is attached to a creature you control.
    If you do, create a token that’s a copy of that creature. If you didn’t create a token this way, create a 1/1 green Insect creature token.
    1/1
     */
    private static final String nantuko = "Springheart Nantuko";

    @Test
    public void testOpponentCreatingTokens() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, aatchik);
        addCard(Zone.BATTLEFIELD, playerB, aatchik);
        addCard(Zone.GRAVEYARD, playerA, aatchik);
        addCard(Zone.GRAVEYARD, playerB, aatchik);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 9);
        addCard(Zone.HAND, playerA, nantuko);
        addCard(Zone.HAND, playerA, "Bayou");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nantuko + " using bestow");
        addTarget(playerA, aatchik);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bayou");
        setChoice(playerA, true);
        setChoice(playerA, aatchik);
        setChoice(playerA, "When {this} enters");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTokenCount(playerB, "Insect Token", 0);
        assertTokenCount(playerA, "Insect Token", 1);
    }
}
