package org.mage.test.cards.rules;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class NamePredicateTest extends CardTestPlayerBase {

    private void assertNamePredicate(String checkName, int needAmount, String needName, boolean ignoreMtgRules) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(new NamePredicate(needName, ignoreMtgRules));
        Assert.assertEquals(checkName, needAmount, currentGame.getBattlefield().countAll(filter, playerA.getId(), currentGame));
    }

    @Test
    public void test_SearchPermanentsByName() {
        // Morph {4}{G}
        addCard(Zone.HAND, playerA, "Pine Walker"); // {3}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker using Morph");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 3 + 1);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);

        // use mtg rules for name searching
        assertNamePredicate("by rules - empty choice must return zero", 0, "", false);
        assertNamePredicate("by rules - face down choice must return zero", 0, EmptyNames.FACE_DOWN_CREATURE.toString(), false);
        assertNamePredicate("by rules - non existing name must return zero", 0, "Island", false);
        assertNamePredicate("by rules - existing name must work", 3, "Forest", false);

        // use inner engine for name searching (e.g. must find face down permanents with empty names)
        if (!EmptyNames.FACE_DOWN_CREATURE.toString().isEmpty()) {
            // if face down permanents gets inner name someday then empty choice must ignore it
            assertNamePredicate("by inner - empty choice must return zero", 0, "", true);
        }
        assertNamePredicate("by inner - face down choice must work", 1, EmptyNames.FACE_DOWN_CREATURE.toString(), true);
        assertNamePredicate("by inner - non existing name must return zero", 0, "Island", true);
        assertNamePredicate("by inner - existing name must work", 3, "Forest", true);
    }

    @Test
    public void testCityInABottle() {
        String bottle = "City in a Bottle"; // Artifact {2}
        // Whenever one or more other nontoken permanents with a name originally printed in the Arabian Nights expansion
        // are on the battlefield, their controllers sacrifice them.
        // Players can’t cast spells or play lands with a name originally printed in the Arabian Nights expansion.
        String nomads = "Desert Nomads"; // Creature {2}{R} (originally printed in Arabian Nights)
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // printed in Arabian Nights, but first printed in Alpha
        addCard(Zone.BATTLEFIELD, playerA, "Camel"); // originally printed in Arabian Nights
        addCard(Zone.HAND, playerA, bottle);
        addCard(Zone.HAND, playerA, nomads);

        checkPlayableAbility("Nomads, unbottled", 1, PhaseStep.PRECOMBAT_MAIN , playerA, "Cast Desert Nomads", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bottle);
        checkGraveyardCount("Camel sacrificed" , 1, PhaseStep.BEGIN_COMBAT, playerA, "Camel", 1);
        checkPlayableAbility("Nomads, bottled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Desert Nomads", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 1);
        assertPermanentCount(playerA, bottle, 1);
        assertPermanentCount(playerA, "Mountain", 5);
    }
}
