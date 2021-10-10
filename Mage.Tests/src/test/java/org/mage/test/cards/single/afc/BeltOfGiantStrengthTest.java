package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BeltOfGiantStrengthTest extends CardTestPlayerBase {

    private static final String belt = "Belt of Giant Strength";
    private static final String gigantosauras = "Gigantosaurus";

    @Test
    public void testWithManaAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, belt);
        addCard(Zone.BATTLEFIELD, playerA, gigantosauras);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", gigantosauras);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertIsAttachedTo(playerA, belt, gigantosauras);
        Assert.assertTrue(
                "All Forests should be untapped",
                currentGame
                        .getBattlefield()
                        .getAllActivePermanents()
                        .stream()
                        .filter(permanent -> permanent.hasSubtype(SubType.FOREST, currentGame))
                        .noneMatch(Permanent::isTapped)
        );
    }

    @Ignore // currently failing, need to fix
    @Test
    public void testWithoutManaAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, belt);
        addCard(Zone.BATTLEFIELD, playerA, gigantosauras);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", gigantosauras);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertIsAttachedTo(playerA, belt, gigantosauras);
    }
}
