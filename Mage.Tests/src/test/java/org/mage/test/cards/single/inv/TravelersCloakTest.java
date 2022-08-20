package org.mage.test.cards.single.inv;

import mage.abilities.keyword.LandwalkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class TravelersCloakTest extends CardTestPlayerBase {

    @Test
    public void test_MustHaveLandWalkOfTheChosenType() {
        // Enchant creature
        // As Traveler's Cloak enters the battlefield, choose a land type.
        // When Traveler's Cloak enters the battlefield, draw a card.
        // Enchanted creature has landwalk of the chosen type.
        addCard(Zone.HAND, playerA, "Traveler's Cloak"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail Corsair", 1);

        // cast and assign landwalk ability to creature
        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", LandwalkAbility.class, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Traveler's Cloak", "Grizzly Bears");
        setChoice(playerA, "Swamp"); // land type for landwalk
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", LandwalkAbility.class, true);

        // check that it can't be blocked
        attack(1, playerA, "Grizzly Bears");
        runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent blocker = game.getBattlefield().getAllActivePermanents()
                    .stream()
                    .filter(p -> p.getName().equals("Kitesail Corsair"))
                    .findFirst()
                    .get();
            Assert.assertFalse("Grizzly Bears must be protected from blocking by Kitesail Corsair",
                    game.getCombat().getGroups().get(0).canBlock(blocker, game));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}