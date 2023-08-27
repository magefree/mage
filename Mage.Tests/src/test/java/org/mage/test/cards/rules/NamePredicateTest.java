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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

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
}
