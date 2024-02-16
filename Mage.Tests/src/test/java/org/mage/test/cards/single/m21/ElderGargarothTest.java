package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ElderGargarothTest extends CardTestPlayerBase {

    private static final String gargaroth = "Elder Gargaroth";

    @Test
    public void createToken(){
        // Whenever Elder Gargaroth attacks or blocks, choose one —

        addCard(Zone.BATTLEFIELD, playerA, gargaroth);

        attack(3, playerA, gargaroth, playerB);
        // • Create a 3/3 green Beast creature token.
        setModeChoice(playerA, "1");
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Beast Token", 1);
    }

    @Test
    public void gainLife(){
        // Whenever Elder Gargaroth attacks or blocks, choose one —

        addCard(Zone.BATTLEFIELD, playerA, gargaroth);

        attack(3, playerA, gargaroth, playerB);
        // • You gain 3 life.
        setModeChoice(playerA, "2");
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerA, 23);
    }

    @Test
    public void drawCard(){
        // Whenever Elder Gargaroth attacks or blocks, choose one —

        addCard(Zone.BATTLEFIELD, playerA, gargaroth);

        attack(3, playerA, gargaroth, playerB);

        // • Draw a card.
        setModeChoice(playerA, "3");
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2);
    }
}
