
package org.mage.test.cards.abilities.keywords;

import mage.abilities.Ability;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ChangelingTest extends CardTestPlayerBase {

    /**
     * Casting changelings with a Long-Forgotten Gohei in play reduces its
     * casting cost by {1}.
     */
    @Test
    public void testLongForgottenGohei() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Woodland Changeling");

        addCard(Zone.BATTLEFIELD, playerA, "Long-Forgotten Gohei");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Woodland Changeling");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Woodland Changeling", 0); // Casting cost of spell is not reduced so not on the battlefield
        assertHandCount(playerA, "Woodland Changeling", 1);

    }

    /**
     * Another bug, was playing Slivers again. I had a Amoeboid Changeling, a
     * Hibernation Sliver and a Prophet of Kruphix. In response to a boardwipe,
     * I tapped my Changeling, giving my Prophet Changeling. However, it didn't
     * gain any Sliver abilities despite having all creature types, including
     * Sliver, so I couldn't save it with my Hibernation Sliver. I clicked the
     * Prophet and nothing happened at all.
     */
    @Test
    public void testGainingChangeling() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Untap all creatures and lands you control during each other player's untap step.
        // You may cast creature cards as though they had flash.
        addCard(Zone.HAND, playerA, "Prophet of Kruphix");// {3}{G}{U}
        // Changeling
        // {T}: Target creature gains all creature types until end of turn.
        // {T}: Target creature loses all creature types until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Amoeboid Changeling");
        // All Slivers have "Pay 2 life: Return this permanent to its owner's hand."
        addCard(Zone.BATTLEFIELD, playerA, "Hibernation Sliver");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prophet of Kruphix");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target creature gains", "Prophet of Kruphix");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Amoeboid Changeling", true);

        Permanent prophet = getPermanent("Prophet of Kruphix", playerA);
        boolean abilityFound = false;
        for (Ability ability : prophet.getAbilities()) {
            if (ability.getRule().startsWith("Pay 2 life")) {
                abilityFound = true;
            }
        }
        Assert.assertTrue("Prophet of Kruphix has to have the 'Pay 2 life: Return this permanent to its owner's hand.' ability, but has not.", abilityFound);

    }

    /**
     * NOTE: As of 05/06/2017 this test is failing due to a bug in code.
     * See issue #3316
     * <p>
     * Kaseto, Orochi Archmage do not give Chameleon Colossus +2/+2 , even though Chameleon Colossus should have the "Snake Token" type
     */
    @Test
    public void kasetoOrochiArchmageSnakeTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        /* Kaseto, Orochi Archmage {1}{G}{U} - 2/2
         * Legendary Creature — Snake Wizard
         * {G}{U}: Target creature can't be blocked this turn. If that creature is a Snake, it gets +2/+2 until end of turn.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Kaseto, Orochi Archmage");
        /*
         * Chameleon Colossus {2}{G}{G} - 4/4
         * Changeling (This card is every creature type.)
         * Protection from black
         * {2}{G}{G}: Chameleon Colossus gets +X/+X until end of turn, where X is its power.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Chameleon Colossus");

        /* Nessian Asp {4}{G} - 4/5
         *  Creature — Snake
         *  Reach
         *  {6}{G}: Monstrosity 4. (If this creature isn't monstrous, put four +1/+1 counters on it and it becomes monstrous.)
         */
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Asp");


        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}: Target creature can't be blocked this turn. If that creature is a Snake, it gets +2/+2 until end of turn", "Chameleon Colossus");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}: Target creature can't be blocked this turn. If that creature is a Snake, it gets +2/+2 until end of turn", "Nessian Asp");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Check the actual snake creature - was 4/5 but +2/+2 from Kaseto's ability
        assertPowerToughness(playerA, "Nessian Asp", 6, 7);

        // Check the changeling - Was a 4/4 but +2/+2 from Kaseto's ability
        assertPowerToughness(playerA, "Chameleon Colossus", 6, 6);

    }

    @Test
    public void testLoseAllCreatureTypes() {
        addCard(Zone.BATTLEFIELD, playerA, "Game-Trail Changeling");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Chieftain");
        addCard(Zone.HAND, playerA, "Nameless Inversion");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nameless Inversion", "Game-Trail Changeling");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Should have no creature types but still have the Changeling ability
        assertPowerToughness(playerA, "Game-Trail Changeling", 7, 1);
        assertNotSubtype("Game-Trail Changeling", SubType.SHAPESHIFTER);
        assertAbility(playerA, "Game-Trail Changeling", HasteAbility.getInstance(), false);
        assertAbility(playerA, "Game-Trail Changeling", new ChangelingAbility(), true);
    }

    @Test
    public void testLoseAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Game-Trail Changeling");
        addCard(Zone.HAND, playerA, "Merfolk Trickster");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Merfolk Trickster");
        addTarget(playerA, "Game-Trail Changeling");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Game-Trail Changeling", true);
        assertSubtype("Game-Trail Changeling", SubType.GOBLIN);
        assertSubtype("Game-Trail Changeling", SubType.ELF);
        assertSubtype("Game-Trail Changeling", SubType.SHAPESHIFTER);
        assertAbility(playerB, "Game-Trail Changeling", new ChangelingAbility(), false);
    }
}
