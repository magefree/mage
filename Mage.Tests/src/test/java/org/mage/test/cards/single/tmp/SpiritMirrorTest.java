package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Simown
 */

/*
 * Spirit Mirror - Enchantment {2}}{W}{W}
 * At the beginning of your upkeep, if there are no Reflection tokens on the battlefield, create a 2/2 white Reflection creature token.
 * {0}: Destroy target Reflection.
*/
public class SpiritMirrorTest extends CardTestPlayerBase {

    @Test
    public void createsTokenBeginningOfUpkeepTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Spirit Mirror");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertPermanentCount(playerA, "Reflection Token", 1);
        Permanent reflection = getPermanent("Reflection Token");
        Assert.assertTrue(reflection.hasSubtype(SubType.REFLECTION, currentGame));
    }


    @Test
    public void destroyCreatedTokenTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Spirit Mirror");

        // Destroy playerAs own reflection token
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Destroy target Reflection", "Reflection Token");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Reflection Token", 0);
    }

    @Test
    public void onlyCreatesSingleTokenTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Spirit Mirror");
        // Allow lots of turns without doing anything
        playerA.setMaxCallsWithoutAction(1000);
        playerB.setMaxCallsWithoutAction(1000);

        // Run for lots of turns
        setStopAt(20, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Only one token created
        assertPermanentCount(playerA, "Reflection Token", 1);
        Permanent reflection = getPermanent("Reflection Token");
        Assert.assertTrue(reflection.hasSubtype(SubType.REFLECTION, currentGame));
    }


    @Test
    public void destroyChangelingTest() {
        // Changeling - all creature types
        addCard(Zone.BATTLEFIELD, playerA, "Chameleon Colossus");
        addCard(Zone.BATTLEFIELD, playerB, "Spirit Mirror");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{0}: Destroy target Reflection", "Chameleon Colossus");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // Was a reflection so destroyed and put in the graveyard
        assertPermanentCount(playerA, "Chameleon Colossus", 0);
        assertGraveyardCount(playerA, "Chameleon Colossus", 1);
    }

    @Test
    public void interactionWithUnnaturalSelectionTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Spirit Mirror");
        addCard(Zone.BATTLEFIELD, playerA, "Unnatural Selection");
        // Mana for Unnatural Selection activation
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // Add some creatures that are not Reflections
        String[] creatures = new String[]{"Memnite", "Chasm Skulker", "Fabled Hero", "Bronze Sable"};
        for (String creature : creatures) {
            addCard(Zone.BATTLEFIELD, playerB, creature);
            // Make them all reflections with Unnatural Selection
            activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: Choose a creature", creature);
            setChoice(playerA, "Reflection");
            // Destroy them all with Spirit Mirror's ability
            activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{0}: Destroy target Reflection", creature);
        }

        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        // All creatures destroyed with Spirit Mirror's ability
        for (String creature : creatures) {
            assertPermanentCount(playerB, creature, 0);
            assertGraveyardCount(playerB, creature, 1);
        }

    }
}
