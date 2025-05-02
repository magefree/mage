package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BeltOfGiantStrengthTest extends CardTestPlayerBase {

    /**
     * Equipped creature has base power and toughness 10/10.
     * Equip {10}. This ability costs {X} less to activate where X is the power of the creature it targets.
     */
    private static final String belt = "Belt of Giant Strength";
    private static final String gigantosauras = "Gigantosaurus"; // 10/10

    @Test
    public void testWithManaAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, belt);
        addCard(Zone.BATTLEFIELD, playerA, gigantosauras);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", gigantosauras);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, belt, gigantosauras, true);
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

    @Test
    public void testWithoutManaAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, belt);
        addCard(Zone.BATTLEFIELD, playerA, gigantosauras);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", gigantosauras);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, belt, gigantosauras, true);
    }
}
