package org.mage.test.cards.continuous;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChangelingTest extends CardTestPlayerBase {

    // Mistform Ultimus is every creature type
    private static final String ultimus = "Mistform Ultimus";
    // each creature gets +1/+1 for each other creature that shares a creature type with it
    private static final String coatOfArms = "Coat of Arms";
    // all merfolk get +1/+1
    private static final String lordOfAtlantis = "Lord of Atlantis";
    // all illusions get +1/+1
    private static final String lordOfUnreal = "Lord of the Unreal";
    // mutavault becomes a token that is all creature types
    private static final String mutavault = "Mutavault";
    // vehicles have no creature type
    private static final String copter = "Smuggler's Copter";
    // 2/2 changeling
    private static final String woodlandChangeling = "Woodland Changeling";

    @Test
    public void coatOfArmsTest() {
        addCard(Zone.BATTLEFIELD, playerA, ultimus);
        addCard(Zone.BATTLEFIELD, playerA, coatOfArms);
        addCard(Zone.BATTLEFIELD, playerA, lordOfAtlantis);
        addCard(Zone.BATTLEFIELD, playerA, lordOfUnreal);
        addCard(Zone.BATTLEFIELD, playerA, mutavault);
        addCard(Zone.BATTLEFIELD, playerA, copter);
        addCard(Zone.BATTLEFIELD, playerA, woodlandChangeling, 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew");
        setChoice(playerA, ultimus);

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
        mutavault token; +2
        atlantis +1
        unreal:  +1
        coat of arms: +5
         */
        assertPowerToughness(playerA, mutavault, 9, 9);
         /*
        smuggler's copter; +3
         */
        assertType(copter, CardType.CREATURE, true);
        assertPowerToughness(playerA, copter, 3, 3);
    }

    private static final String mimic = "Metallic Mimic";

    @Test
    public void testMetallicMimicChangelingTrigger() {
        // all creatures with the chosen subtype come into play with a +1/+1 counter

        addCard(Zone.HAND, playerA, mimic, 1);
        addCard(Zone.HAND, playerA, woodlandChangeling, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mimic);
        setChoice(playerA, "Sliver");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, woodlandChangeling);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // 2/2 + +1/+1 counter
        assertPowerToughness(playerA, woodlandChangeling, 3, 3);
    }

    private static final String paragon = "Bramblewood Paragon";
    private static final String cohort = "Irregular Cohort";

    @Test
    public void testTokensHaveTypesAsTheyEnter() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, paragon);
        addCard(Zone.HAND, playerA, cohort);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cohort);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertCounterCount(playerA, cohort, CounterType.P1P1, 1);
        assertCounterCount(playerA, "Shapeshifter", CounterType.P1P1, 1);
    }

    private static final String amoeboid = "Amoeboid Changeling";

    @Test
    public void testTokensCanLoseTypes() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Wizened Cenn");
        addCard(Zone.BATTLEFIELD, playerA, amoeboid);
        addCard(Zone.HAND, playerA, cohort);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cohort);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target creature loses", "Shapeshifter");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertTapped(amoeboid, true);
        assertPowerToughness(playerA, "Shapeshifter", 2, 2);
    }
}
