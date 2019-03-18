package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SylvokExplorerTest extends CardTestPlayerBase {

    /**
     * java.lang.StackOverflowError at
     * mage.filter.predicate.Predicates.and(Predicates.java:68) at
     * mage.filter.FilterImpl.match(FilterImpl.java:62) at
     * mage.filter.FilterPermanent.match(FilterPermanent.java:74) at
     * mage.game.permanent.Battlefield.getActivePermanents(Battlefield.java:362)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getManaTypes(AnyColorLandsProduceManaAbility.java:164)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getNetMana(AnyColorLandsProduceManaAbility.java:181)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaAbility.getNetMana(AnyColorLandsProduceManaAbility.java:70)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getManaTypes(AnyColorLandsProduceManaAbility.java:170)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getNetMana(AnyColorLandsProduceManaAbility.java:181)
     */
    @Test
    @Ignore
    public void testOneInstance() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvok Explorer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 1 red and 1 white mana", "{W}{R}", options.get(0).toString());
        Assert.assertEquals("Player should be able to create 1 blue and 1 white mana", "{W}{U}", options.get(1).toString());
    }

    @Test
    public void testTwoInstances() {
        addCard(Zone.BATTLEFIELD, playerB, "Exotic Orchard", 2);

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvok Explorer", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 3 white mana", "{W}{W}{W}", options.get(0).toString());
    }
}
