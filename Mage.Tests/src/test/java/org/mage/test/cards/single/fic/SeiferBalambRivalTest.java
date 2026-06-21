package org.mage.test.cards.single.fic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SeiferBalambRivalTest extends CardTestPlayerBase {

    @Test
    public void testGoad() {
        addCard(Zone.BATTLEFIELD, playerA, "Seifer, Balamb Rival");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");

        attack(1, playerA, "Seifer, Balamb Rival", playerB);
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        Assert.assertEquals(Stream.of(playerA.getId()).collect(Collectors.toSet()), getPermanent("Balduvian Bears").getGoadingPlayers());
    }

}
