package org.mage.test.cards.single.neo;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Assert;
import org.mage.test.serverside.base.CardTestCommander4Players;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 * {@link mage.cards.h.HinataDawnCrowned Hinata, Dawn-Crowned}  
 * {1}{U}{R}{W}
 * Flying, trample
 * Spells you cast cost {1} less to cast for each target.
 * Spells your opponents cast cost {1} more to cast for each target.
 *
 * @author alexander-novo
 */
@RunWith(Enclosed.class)
public class HinataDawnCrownedTest extends CardTestCommander4Players {

    private static final String hinata = "Hinata, Dawn-Crowned";

    /**
     * {@link mage.cards.v.VolcanicOffering Volcanic Offering}
     * {4}{R}
     * Destroy target nonbasic land you don’t control and target nonbasic land of an opponent’s choice you don’t control.
     * Volcanic Offering deals 7 damage to target creature you don’t control and 7 damage to target creature of an opponent’s choice you don’t control.
     * 
     * Included as a common card in a Hinata deck with an unusual TargetAdjuster - one that an opponent chooses.
     * As well, the card can target either 2, 3, or 4 different targets depending on what the opponent chooses.
     */
    public static class VolcanicOfferingTest extends CardTestCommander4Players {
        private static final String volcanic_offering = "Volcanic Offering";

        private static final String[] nonbasic_land = { "Command Tower", "Exotic Orchard" };
        private static final String[] creature = { "Birds of Paradise", "Llanowar Elves" };

        @Before
        public void setup() {
            addCard(Zone.HAND, playerA, volcanic_offering);

            // Targets for Volcanic Offering
            addCard(Zone.BATTLEFIELD, playerB, nonbasic_land[0]);
            addCard(Zone.BATTLEFIELD, playerB, nonbasic_land[1]);
            addCard(Zone.BATTLEFIELD, playerB, creature[0]);
            addCard(Zone.BATTLEFIELD, playerB, creature[1]);

            setStrictChooseMode(true);
        }

        /**
         * Happy path for controller of Hinata - the opponent chooses all different targets, so the spell's mana cost is reduced by {4}
         */
        @Test
        public void controlled_happy() {
            addCard(Zone.BATTLEFIELD, playerA, hinata);

            // Enough mana to cast Volcanic Offering if Hinata is working properly
            addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 - 4);

            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, volcanic_offering, true);
            addTarget(playerA, nonbasic_land[0]);
            addTarget(playerA, playerB);
            addTarget(playerB, nonbasic_land[1]);
            addTarget(playerA, creature[0]);
            addTarget(playerA, playerB);
            addTarget(playerB, creature[1]);

            setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
            execute();

            assertPermanentCount(playerB, nonbasic_land[0], 0);
            assertPermanentCount(playerB, nonbasic_land[1], 0);
            assertGraveyardCount(playerB, nonbasic_land[0], 1);
            assertGraveyardCount(playerB, nonbasic_land[1], 1);
            assertPermanentCount(playerB, creature[0], 0);
            assertPermanentCount(playerB, creature[1], 0);
            assertGraveyardCount(playerB, creature[0], 1);
            assertGraveyardCount(playerB, creature[1], 1);
        }

        /**
         * Sad path for controller of Hinata - the opponent chooses all the same targets, so the spell's mana cost is only reduced by {2}
         */
        @Test
        public void controlled_sad() {
            addCard(Zone.BATTLEFIELD, playerA, hinata);

            // Enough mana to cast Volcanic Offering only if opponent chooses poorly.
            addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 - 3);

            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, volcanic_offering, true);
            addTarget(playerA, nonbasic_land[0]);
            addTarget(playerA, playerB);
            addTarget(playerB, nonbasic_land[0]); // Make sure opponent chooses well
            addTarget(playerA, creature[0]);
            addTarget(playerA, playerB);
            addTarget(playerB, creature[0]); // Make sure opponent chooses well

            setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

            try {
                execute();
                Assert.fail("Expected to fail due to opponent's choices");
            } catch (AssertionError err) {
                assertThat(
                        err.getMessage(),
                        startsWith("Can't find ability to activate command: Cast " + volcanic_offering));
            }

        }

        @Test
        public void opponent() {
            addCard(Zone.BATTLEFIELD, playerB, hinata);

            // Volcanic Offering costs 5 mana, but has 4 targets which are added by a target adjuster. However, these targets can slightly overlap.
            // Therefore you need at least 7 mana to cast it, so this shouldn't be enough.
            addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 + 1);

            checkPlayableAbility("canCast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + volcanic_offering, false);

            // This player could have enough mana to cast it, depending on what the opponent chooses
            addCard(Zone.HAND, playerC, volcanic_offering);
            addCard(Zone.BATTLEFIELD, playerC, "Mountain", 5 + 2);
            checkPlayableAbility("canCast", 1, PhaseStep.PRECOMBAT_MAIN, playerC, "Cast " + volcanic_offering, true);

            setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
            execute();
        }
    }
}
