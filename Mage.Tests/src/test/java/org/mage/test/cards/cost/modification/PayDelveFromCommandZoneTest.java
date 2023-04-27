package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class PayDelveFromCommandZoneTest extends CardTestCommander4Players {

    // Player order: A -> D -> C -> B
    @Test
    public void test_Other_CastFromCommand_Delve() {
        // https://github.com/magefree/mage/issues/6698
        // Having this problem with Tasigur, the Golden Fang. I can't attempt to use delve to cast him from command zone.

        // {5}{B} creature
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        addCard(Zone.COMMAND, playerA, "Tasigur, the Golden Fang", 1);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 5); // delve
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tasigur, the Golden Fang");
        setChoice(playerA, "Balduvian Bears", 5); // delve pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tasigur, the Golden Fang", 1);
    }
}
