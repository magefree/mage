package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChangelingTest extends CardTestPlayerBase {

    // Mistform Ultimus is every creature type
    private final String ultimus = "Mistform Ultimus";
    // each creature gets +1/+1 for each creature you control that shares a creatureype
    private final String coatOfArms = "Coat of Arms";
    // all merfolk get +1/+1
    private final String lordOfAtlantis = "Lord of Atlantis";
    // all illusions get +1/+1
    private final String lordOfUnreal = "Lord of the Unreal";
    // mutavault becomes a token that is all creature types
    private final String mutavault = "Mutavault";

    // 2/2 changeling
    private final String woodlandChangeling = "Woodland Changeling";

    @Test
    public void coatOfArmsTest(){
        addCard(Zone.BATTLEFIELD, playerA, ultimus);
        addCard(Zone.BATTLEFIELD, playerA, coatOfArms);
        addCard(Zone.BATTLEFIELD, playerA, lordOfAtlantis);
        addCard(Zone.BATTLEFIELD, playerA, lordOfUnreal);
        addCard(Zone.BATTLEFIELD, playerA, mutavault);
        addCard(Zone.BATTLEFIELD, playerA, woodlandChangeling, 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: Until end of turn {this} becomes");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        /*
        ultimus; +3
        atlantis +1
        unreal:  +1
        coat of arms: +5
         */
        assertPowerToughness(playerA, ultimus, 10, 10);
        /*
        atlantis : +2
        coat of arms: + 4
         */
        assertPowerToughness(playerA, lordOfAtlantis, 6, 6);
         /*
        mutavault token; +3
        atlantis +1
        unreal:  +1
        coat of arms: +5
         */
        assertPowerToughness(playerA, mutavault, 9, 9);
    }
}
