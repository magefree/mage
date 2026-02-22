package org.mage.test.cards.single.fic;
 
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
 
/**
 * @author matoro
 */
public class FandanielTelophoroiAscianTest extends CardTestPlayerBase {
    /*
     * Whenever you cast an instant or sorcery spell, surveil 1.
     * At the beginning of your end step, each opponent may sacrifice a nontoken creature of their choice. Each opponent who doesn't loses 2 life for each instant and sorcery card in your graveyard.
     */

    // https://github.com/magefree/mage/issues/13965
    @Test
    public void testNoSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Fandaniel, Telophoroi Ascian");
        addCard(Zone.GRAVEYARD, playerA, "Counterspell");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);
    }

}
