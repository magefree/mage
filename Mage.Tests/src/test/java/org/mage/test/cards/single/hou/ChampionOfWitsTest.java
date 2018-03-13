package org.mage.test.cards.single.hou;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChampionOfWitsTest extends CardTestPlayerBase {


    private final String champion = "Champion of Wits";

    @Test
    public void testEternalize(){
        addCard(Zone.GRAVEYARD, playerA, champion);
        addCard(Zone.BATTLEFIELD,playerA, "Island", 10);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,"Eternalize {5}{U}{U}");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();
        assertHandCount(playerA, 2);
    }

    @Test
    public void testEternalizeWithAnthem(){
        String anthem = "Glorious Anthem";
        addCard(Zone.GRAVEYARD, playerA, champion);
        addCard(Zone.BATTLEFIELD,playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, anthem);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,"Eternalize {5}{U}{U}");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();
        assertHandCount(playerA, 3);
    }
}
