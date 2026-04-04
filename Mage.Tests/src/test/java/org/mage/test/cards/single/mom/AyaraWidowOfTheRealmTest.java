package org.mage.test.cards.single.mom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AyaraWidowOfTheRealmTest extends CardTestPlayerBase {

    @Test
    public void testBackside() {
        addCard(Zone.BATTLEFIELD, playerA, "Ayara, Widow of the Realm");
        addCard(Zone.GRAVEYARD, playerA, "Noxious Gearhulk");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}");
        setChoice(playerA, false);
        addTarget(playerA, "Noxious Gearhulk");
        setChoice(playerA, true);
        addTarget(playerA, "Balduvian Bears");
        attack(1, playerA, "Noxious Gearhulk", playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Ayara, Furnace Queen", 1);
        assertExileCount(playerA, "Noxious Gearhulk", 1);
        assertGraveyardCount(playerB, "Balduvian Bears", 1);
        assertLife(playerA, 22);
        assertLife(playerB, 15);
    }

}
