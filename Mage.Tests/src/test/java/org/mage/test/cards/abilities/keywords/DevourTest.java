package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Objects;

/**
 * 702.82. Devour
 * <p>
 * 702.82a Devour is a static ability. “Devour N” means “As this object enters the battlefield, you may sacrifice any number of creatures. This permanent enters the battlefield with N +1/+1 counters on it for each creature sacrificed this way.”
 * <p>
 * 702.82b Some objects have abilities that refer to the number of creatures the permanent devoured. “It devoured” means “sacrificed as a result of its devour ability as it entered the battlefield.”
 * <p>
 * 702.82c Devour [quality] is a variant of devour. “Devour [quality] N” means “As this object enters the battlefield, you may sacrifice any number of [quality] permanents. This permanent enters the battlefield with N +1/+1 counters on it for each permanent sacrificed this way.”
 *
 * @author Susucr
 */
public class DevourTest extends CardTestPlayerBase {

    private void expectedPossibleTest(
            String devourer,
            String devourTargets,
            int assertCounter,
            boolean assertLion,
            boolean assertGolem,
            boolean assertGinger,
            boolean assertRelic,
            boolean assertAngrath,
            int life
    ) {
        setStrictChooseMode(true);

        // Chromatic Orrery
        // {7}
        // Legendary Artifact
        //
        // You may spend mana as though it were mana of any color.
        //
        // {T}: Add {C}{C}{C}{C}{C}.
        //
        // {5}, {T}: Draw a card for each color among permanents you control.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Orrery", 1);

        addCard(Zone.HAND, playerA, devourer);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature
        addCard(Zone.BATTLEFIELD, playerA, "Enatu Golem"); // Artifact Creature - gain 4 life on death
        addCard(Zone.BATTLEFIELD, playerA, "Gingerbrute"); // Artifact Creature — Food Golem
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic"); // Artifact
        addCard(Zone.BATTLEFIELD, playerA, "Angrath, Captain of Chaos"); // Planeswalker

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, devourer);
        if (Objects.equals(devourTargets, "")) {
            setChoice(playerA, false);   // no to devour
        } else {
            setChoice(playerA, true);    // yes to devour
            addTarget(playerA, devourTargets); // devour targets.
        }
        if (!assertGolem && devourer.equals("Marrow Chomper")) {
            setChoice(playerA, "When {this} dies, you gain 4 life"); // order triggers
        }

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        if (devourer.equals(thromok) && assertCounter == 0) {
            // Thromok is a 0/0, devouring nothing, it just dies due to SBA.
            assertGraveyardCount(playerA, devourer, 1);
        } else {
            assertCounterCount(playerA, devourer, CounterType.P1P1, assertCounter);
        }

        assertPermanentCount(playerA, "Silvercoat Lion", assertLion ? 1 : 0);
        assertGraveyardCount(playerA, "Silvercoat Lion", assertLion ? 0 : 1);
        assertPermanentCount(playerA, "Enatu Golem", assertGolem ? 1 : 0);
        assertGraveyardCount(playerA, "Enatu Golem", assertGolem ? 0 : 1);
        assertPermanentCount(playerA, "Gingerbrute", assertGinger ? 1 : 0);
        assertGraveyardCount(playerA, "Gingerbrute", assertGinger ? 0 : 1);
        assertPermanentCount(playerA, "Darksteel Relic", assertRelic ? 1 : 0);
        assertGraveyardCount(playerA, "Darksteel Relic", assertRelic ? 0 : 1);
        assertPermanentCount(playerA, "Angrath, Captain of Chaos", assertAngrath ? 1 : 0);
        assertGraveyardCount(playerA, "Angrath, Captain of Chaos", assertAngrath ? 0 : 1);
        assertLife(playerA, life);
    }

    private void expectedIllegalTest(
            String devourer,
            String devourTargets
    ) {
        setStrictChooseMode(true);

        // Chromatic Orrery
        // {7}
        // Legendary Artifact
        //
        // You may spend mana as though it were mana of any color.
        //
        // {T}: Add {C}{C}{C}{C}{C}.
        //
        // {5}, {T}: Draw a card for each color among permanents you control.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Orrery", 1);

        addCard(Zone.HAND, playerA, devourer);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature
        addCard(Zone.BATTLEFIELD, playerA, "Enatu Golem"); // Creature Artifact
        addCard(Zone.BATTLEFIELD, playerA, "Gingerbrute"); // Artifact Creature — Food Golem
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic"); // Artifact

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, devourer);
        setChoice(playerA, true);    // yes to devour
        addTarget(playerA, devourTargets); // devour targets.

        boolean legal = true;
        try {
            setStopAt(1, PhaseStep.BEGIN_COMBAT);
            execute();
        } catch (AssertionError e) {
            if (e.getMessage().startsWith("PlayerA - Targets list was setup by addTarget with [" + devourTargets + "], but not used")) {
                legal = false;
            }
        } finally {
            assert !legal;
        }
    }

    // Gorger Wurm
    // {3}{R}{G}
    // Creature — Wurm
    //
    // Devour 1
    //
    // 5/5
    private static final String gorgerWurm = "Gorger Wurm";

    @Test
    public void Wurm_NoDevour() {
        expectedPossibleTest(gorgerWurm, "",
                1 * 0, true, true, true, true, true, 20);
    }

    @Test
    public void Wurm_OneDevour() {
        expectedPossibleTest(gorgerWurm, "Enatu Golem",
                1 * 1, true, false, true, true, true, 24);
    }

    @Test
    public void Wurm_TwoDevour() {
        expectedPossibleTest(gorgerWurm, "Enatu Golem^Gingerbrute",
                1 * 2, true, false, false, true, true, 24);
    }

    @Test
    public void Wurm_ThreeDevour() {
        expectedPossibleTest(gorgerWurm, "Enatu Golem^Gingerbrute^Silvercoat Lion",
                1 * 3, false, false, false, true, true, 24);
    }

    @Test
    public void Wurm_IllegalDevour() {
        expectedIllegalTest(gorgerWurm, "Darksteel Relic");
    }

    // Thromok the Insatiable
    // {3}{R}{G}
    // Legendary Creature — Hellion
    //
    // Devour X, where X is the number of creatures devoured this way
    //
    // 0/0
    private static final String thromok = "Thromok the Insatiable";

    @Test
    public void Thromok_NoDevour() {
        expectedPossibleTest(thromok, "",
                0 * 0, true, true, true, true, true, 20);
    }

    @Test
    public void Thromok_OneDevour() {
        expectedPossibleTest(thromok, "Enatu Golem",
                1 * 1, true, false, true, true, true, 24);
    }

    @Test
    public void Thromok_TwoDevour() {
        expectedPossibleTest(thromok, "Enatu Golem^Gingerbrute",
                2 * 2, true, false, false, true, true, 24);
    }

    @Test
    public void Thromok_ThreeDevour() {
        expectedPossibleTest(thromok, "Enatu Golem^Gingerbrute^Silvercoat Lion",
                3 * 3, false, false, false, true, true, 24);
    }

    @Test
    public void Thromok_IllegalDevour() {
        expectedIllegalTest(thromok, "Darksteel Relic");
    }

    // Feasting Hobbit
    // {1}{G}
    // Creature — Halfling Citizen
    //
    // Devour Food 3
    //
    // Creatures with power less than Feasting Hobbit’s power can’t block it.
    private static final String hobbit = "Feasting Hobbit";

    @Test
    public void Hobbit_NoDevour() {
        expectedPossibleTest(hobbit, "",
                3 * 0, true, true, true, true, true, 20);
    }

    @Test
    public void Hobbit_OneDevour() {
        expectedPossibleTest(hobbit, "Gingerbrute",
                3 * 1, true, true, false, true, true, 20);
    }

    @Test
    public void Hobbit_IllegalDevour() {
        expectedIllegalTest(hobbit, "Enatu Golem");
    }

    // Caprichrome
    // {3}{W}
    // Artifact Creature — Goat
    //
    // Flash
    //
    // Vigilance
    //
    // Devour artifact 1
    //
    // 2/2
    private static final String caprichrome = "Caprichrome";

    @Test
    public void Caprichrome_NoDevour() {
        expectedPossibleTest(caprichrome, "",
                1 * 0, true, true, true, true, true, 20);
    }

    @Test
    public void Caprichrome_OneDevour() {
        expectedPossibleTest(caprichrome, "Enatu Golem",
                1 * 1, true, false, true, true, true, 24);
    }

    @Test
    public void Caprichrome_TwoDevour() {
        expectedPossibleTest(caprichrome, "Enatu Golem^Gingerbrute",
                1 * 2, true, false, false, true, true, 24);
    }

    @Test
    public void Caprichrome_ThreeDevour() {
        expectedPossibleTest(caprichrome, "Enatu Golem^Gingerbrute^Darksteel Relic",
                1 * 3, true, false, false, false, true, 24);
    }

    @Test
    public void Caprichrome_IllegalDevour() {
        expectedIllegalTest(caprichrome, "Silvercoat Lion");
    }

    // Hellkite Hatchling
    // {2}{R}{G}
    // Creature — Dragon
    //
    // Devour 1
    //
    // Hellkite Hatchling has flying and trample if it devoured a creature.
    //
    // 2/2
    private static final String hatchling = "Hellkite Hatchling";

    @Test
    public void Hatchling_NoDevour() {
        expectedPossibleTest(hatchling, "",
                1 * 0, true, true, true, true, true, 20);
        assertAbility(playerA, hatchling, FlyingAbility.getInstance(), false);
        assertAbility(playerA, hatchling, TrampleAbility.getInstance(), false);
    }

    @Test
    public void Hatchling_OneDevour() {
        expectedPossibleTest(hatchling, "Enatu Golem",
                1 * 1, true, false, true, true, true, 24);
        assertAbility(playerA, hatchling, FlyingAbility.getInstance(), true);
        assertAbility(playerA, hatchling, TrampleAbility.getInstance(), true);
    }

    @Test
    public void Hatchling_TwoDevour() {
        expectedPossibleTest(hatchling, "Enatu Golem^Gingerbrute",
                1 * 2, true, false, false, true, true, 24);
        assertAbility(playerA, hatchling, FlyingAbility.getInstance(), true);
        assertAbility(playerA, hatchling, TrampleAbility.getInstance(), true);
    }

    @Test
    public void Hatchling_ThreeDevour() {
        expectedPossibleTest(hatchling, "Enatu Golem^Gingerbrute^Silvercoat Lion",
                1 * 3, false, false, false, true, true, 24);
        assertAbility(playerA, hatchling, FlyingAbility.getInstance(), true);
        assertAbility(playerA, hatchling, TrampleAbility.getInstance(), true);
    }

    @Test
    public void Hatchling_IllegalDevour() {
        expectedIllegalTest(hatchling, "Darksteel Relic");
    }

    // Marrow Chomper
    // {3}{B}{G}
    // Creature — Zombie Lizard
    //
    // Devour 2
    //
    // When Marrow Chomper enters the battlefield, you gain 2 life for each creature it devoured.
    private static final String chomper = "Marrow Chomper";

    @Test
    public void Chomper_NoDevour() {
        expectedPossibleTest(chomper, "",
                2 * 0, true, true, true, true, true, 20);
    }

    @Test
    public void Chomper_OneDevour() {
        expectedPossibleTest(chomper, "Enatu Golem",
                2 * 1, true, false, true, true, true, 26);
    }

    @Test
    public void Chomper_TwoDevour() {
        expectedPossibleTest(chomper, "Enatu Golem^Gingerbrute",
                2 * 2, true, false, false, true, true, 28);
    }

    @Test
    public void Chomper_ThreeDevour() {
        expectedPossibleTest(chomper, "Enatu Golem^Gingerbrute^Silvercoat Lion",
                2 * 3, false, false, false, true, true, 30);
    }

    @Test
    public void Chomper_IllegalDevour() {
        expectedIllegalTest(chomper, "Darksteel Relic");
    }

    //  Devouring Hellion {2}{R}
    // Creature — Hellion
    // As Devouring Hellion enters, you may sacrifice any number of creatures and/or planeswalkers.
    // If you do, it enters with twice that many +1/+1 counters on it.
    private static final String hellion = "Devouring Hellion";

    @Test
    public void hellionNoDevour() {
        expectedPossibleTest(hellion, "",
                0, true, true, true, true, true, 20);
    }

    @Test
    public void hellionOneDevour() {
        expectedPossibleTest(hellion, "Angrath, Captain of Chaos",
                2, true, true, true, true, false, 20);
    }

    @Test
    public void hellionTwoDevour() {
        expectedPossibleTest(hellion, "Enatu Golem^Angrath, Captain of Chaos",
                4, true, false, true, true, false, 24);
    }

    @Test
    public void hellionThreeDevour() {
        expectedPossibleTest(hellion, "Enatu Golem^Gingerbrute^Angrath, Captain of Chaos",
                6, true, false, false, true, false, 24);
    }

    @Test
    public void hellionIllegalDevour() {
        expectedIllegalTest(hellion, "Darksteel Relic");
    }

}
