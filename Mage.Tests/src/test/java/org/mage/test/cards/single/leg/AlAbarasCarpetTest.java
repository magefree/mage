package org.mage.test.cards.single.leg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlAbarasCarpetTest extends CardTestPlayerBase {

    /*
    Al-abara's Carpet
    {5}
    Artifact

    {5}, {T}: Prevent all damage that would be dealt to you this turn by attacking creatures without flying.
     */
    private static final String alabarasCarpet = "Al-abara's Carpet";

    /*
    Storm Crow
    {1}{U}
    Creature — Bird

    Flying (This creature can’t be blocked except by creatures with flying or reach.)

    1/2
     */
    private static final String stormCrow = "Storm Crow";
    /*
    Balduvian Bears
    {1}{G}
    Creature — Bear

    2/2
     */
    private static final String balduvianBears = "Balduvian Bears";
    @Test
    public void testCarpet() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, alabarasCarpet);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, balduvianBears);
        addCard(Zone.BATTLEFIELD, playerB, stormCrow);

        attack(2, playerB, balduvianBears);
        attack(2, playerB, stormCrow);
        activateAbility(2, PhaseStep.DECLARE_ATTACKERS, playerA, "{5}, {T}");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
    }
}