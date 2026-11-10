package org.mage.test.cards.single.bro;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class HurkylMasterWizardTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.h.HurkylMasterWizard}
     * <p>
     * {1}{U}{U} Legendary Creature - Human Wizard Advisor 2/4<br>
     * At the beginning of your end step, if you've cast a noncreature spell this turn, reveal the top five cards
     * of your library. For each card type among noncreature spells you've cast this turn, you may put a card of
     * that type from among the revealed cards into your hand. Put the rest on the bottom of your library in a
     * random order.
     */
    private static final String HURKYL = "Hurkyl, Master Wizard";

    @Test
    public void test_TakeCardOfCastType() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1); // creature
        addCard(Zone.LIBRARY, playerA, "Darksteel Monolith", 1); // artifact
        //
        addCard(Zone.BATTLEFIELD, playerA, HURKYL);
        //
        addCard(Zone.HAND, playerA, "Sol Ring"); // {1}, artifact
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sol Ring");

        setChoice(playerA, "Darksteel Monolith"); // artifact for the cast artifact

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertHandCount(playerA, "Darksteel Monolith", 1);
        assertHandCount(playerA, "Grizzly Bears", 0);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_TakeCardForEachCastType() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1); // creature
        addCard(Zone.LIBRARY, playerA, "Shock", 1); // instant
        addCard(Zone.LIBRARY, playerA, "Darksteel Monolith", 1); // artifact
        //
        addCard(Zone.BATTLEFIELD, playerA, HURKYL);
        //
        addCard(Zone.HAND, playerA, "Sol Ring"); // {1}, artifact
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, instant
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sol Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setChoice(playerA, "Darksteel Monolith"); // artifact for the cast artifact
        setChoice(playerA, "Shock"); // instant for the cast instant

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 3);
        assertHandCount(playerA, "Darksteel Monolith", 1);
        assertHandCount(playerA, "Shock", 1);
        assertHandCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_TakeNothingWithoutCardOfCastType() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Shock", 1); // instant
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1); // creature
        //
        addCard(Zone.BATTLEFIELD, playerA, HURKYL);
        //
        addCard(Zone.HAND, playerA, "Sol Ring"); // {1}, artifact
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sol Ring");

        // no revealed artifact, so no card can be taken (the creature, the instant and the lands don't fit)

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 0);
        assertHandCount(playerA, "Shock", 0);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, "Shock", 1);
    }
}
