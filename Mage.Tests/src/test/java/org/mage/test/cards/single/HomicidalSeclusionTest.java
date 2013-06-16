package org.mage.test.cards.single;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Homicidal Seclusion");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Horned Turtle", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.BATTLEFIELD, playerA, "Homicidal Seclusion");
        addCard(Zone.BATTLEFIELD, playerA, "Horned Turtle", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.BATTLEFIELD, playerA, "Homicidal Seclusion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Horned Turtle", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.BATTLEFIELD, playerA, "Homicidal Seclusion");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
