package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by alexsandro on 06/03/17.
 */
public class SakashimaTheImpostorTest extends CardTestPlayerBase {
    @Test
    public void copySpellStutterTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Spellstutter Sprite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Sakashima the Impostor", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sakashima the Impostor");
        setChoice(playerB, "Spellstutter Sprite");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Spellstutter Sprite", 1);

        assertPowerToughness(playerB, "Sakashima the Impostor", 1, 1);
    }
}
