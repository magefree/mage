
package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Grath
 */
public class CamelliaTheSeedmiserTest extends CardTestPlayerBase {

    @Test
    public void TestTokensAreCreated() {
        addCard(Zone.BATTLEFIELD, playerA, "Camellia, the Seedmiser", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Witch's Oven", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Cauldron Familiar", 1);

        // Sacrifice cat to oven
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Cauldron Familiar");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Sacrifice food to cat
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a Food");
        setChoice(playerA, "Food Token");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // One food sacrificed, one squirrel made.
        assertPermanentCount(playerA, "Squirrel Token", 1);
    }

    @Test
    public void TestTokensAreCreatedForGildedGoose() {
        addCard(Zone.BATTLEFIELD, playerA, "Camellia, the Seedmiser", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Witch's Oven", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Cauldron Familiar", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Sorin, Ravenous Neonate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Gilded Goose", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Sacrifice cat to oven
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Cauldron Familiar");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Make a food with Sorin
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(3, PhaseStep.UPKEEP, playerA, "{2}, {T}, Sacrifice");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // Activating Gilded Goose happens first, then other costs are paid including sacrificing the other Food.
        // So two Squirrels are made.
        assertPermanentCount(playerA, "Squirrel Token", 2);
    }

    @Test
    public void TestOneTokenForSimultaneousFoodSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Camellia, the Seedmiser", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Sorin, Ravenous Neonate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Sai, Master Thopterist", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Make two food with Sorin
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+2");

        // Sacrifice both food to Sai
        activateAbility(5, PhaseStep.UPKEEP, playerA, "{1}{U}, Sacrifice two artifacts: Draw a card.");

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        // One squirrel made because both foods were sacrificed at the same time.
        assertPermanentCount(playerA, "Squirrel Token", 1);
    }
}
