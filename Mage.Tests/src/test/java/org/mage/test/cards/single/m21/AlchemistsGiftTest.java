package org.mage.test.cards.single.m21;

import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlchemistsGiftTest extends CardTestPlayerBase {

    private final String deathtouch = "Yes";
    private final String lifelink = "No";

    // Target creature gets +1/+1 and gains your choice of deathtouch or lifelink until end of turn.


    @Test
    public void giveDeathTouch(){
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD,playerA, "Adherent of Hope", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Alchemist's Gift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alchemist's Gift", "Adherent of Hope");
        setChoice(playerA, deathtouch);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertAbility(playerA, "Adherent of Hope", DeathtouchAbility.getInstance(), true);
        assertAbility(playerA, "Adherent of Hope", LifelinkAbility.getInstance(), false);
        assertPowerToughness(playerA, "Adherent of Hope", 3, 2);
    }

    @Test
    public void giveLifelink(){
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD,playerA, "Adherent of Hope", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Alchemist's Gift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alchemist's Gift", "Adherent of Hope");
        setChoice(playerA, lifelink);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertAbility(playerA, "Adherent of Hope", LifelinkAbility.getInstance(), true);
        assertAbility(playerA, "Adherent of Hope", DeathtouchAbility.getInstance(), false);
        assertPowerToughness(playerA, "Adherent of Hope", 3, 2);
    }
}
