package org.mage.test.cards.single.acr;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AdrestiaTest extends CardTestPlayerBase {

    /*
    Adrestia
    {3}
    Legendary Artifact — Vehicle

    Islandwalk (This creature can’t be blocked as long as defending player controls an Island.)

    Whenever Adrestia attacks, if an Assassin crewed it this turn, draw a card. Adrestia becomes an Assassin in addition to its other types until end of turn.

    Crew 1
     */
    private static final String adrestia = "Adrestia";
    /*
    Nekrataal
    {2}{B}{B}
    Creature — Human Assassin

    First strike

    When this creature enters, destroy target nonartifact, nonblack creature. That creature can’t be regenerated.

    2/1
     */
    private static final String nekrataal = "Nekrataal";

    @Test
    public void testAdrestia() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, adrestia);
        addCard(Zone.BATTLEFIELD, playerA, nekrataal);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew");
        setChoice(playerA, nekrataal);
        attack(1, playerA, adrestia);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSubtype(adrestia, SubType.VEHICLE);
        assertType(adrestia, CardType.CREATURE, true);
        assertType(adrestia, CardType.ARTIFACT, true);
        assertSubtype(adrestia, SubType.ASSASSIN);
        assertHandCount(playerA, 1);
    }
}