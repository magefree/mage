package org.mage.test.cards.single;

import mage.Constants;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class HomicidalSeclusionTest extends CardTestPlayerBase {

    /**
     * Tests that there will be no effect when there is more than one creature
     */
    @Test
    public void testNoSingleCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Homicidal Seclusion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Horned Turtle", 1);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, "Air Elemental", 4, 4, Filter.ComparisonScope.All);
        assertPowerToughness(playerA, "Horned Turtle", 1, 4, Filter.ComparisonScope.All);
    }

    /**
     * Tests effect of a single card for a single creature
     */
    @Test
    public void testSingleCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Homicidal Seclusion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Horned Turtle", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, "Horned Turtle", 4, 5, Filter.ComparisonScope.All);

        Abilities abilities = new AbilitiesImpl();
        abilities.add(LifelinkAbility.getInstance());
        assertAbilities(playerA, "Horned Turtle", abilities);
    }

    /**
     * Tests several copies of Homicidal Seclusion card
     */
    @Test
    public void testMultiInstances() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Homicidal Seclusion", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Horned Turtle", 1);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, "Horned Turtle", 7, 6, Filter.ComparisonScope.All);
    }

    /**
     * Tests that effect will be applied later
     */
    @Test
    public void testApplyInProgress() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Homicidal Seclusion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPowerToughness(playerA, "Air Elemental", 7, 5, Filter.ComparisonScope.All);

        Abilities abilities = new AbilitiesImpl();
        abilities.add(LifelinkAbility.getInstance());
        assertAbilities(playerA, "Air Elemental", abilities);
    }

}
