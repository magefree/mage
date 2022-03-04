package org.mage.test.cards.single.chk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HorobiDeathsWailTest extends CardTestPlayerBase {

    // issue 7772
    @Test
    @Ignore
    public void animateDeadOnHorobi(){
        // Animate Dead
        addCard(Zone.HAND, playerA, "Animate Dead");
        // Whenever a creature becomes the target of a spell or ability, destroy that creature.
        addCard(Zone.GRAVEYARD, playerA, "Horobi, Death's Wail");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Horobi, Death's Wail");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Horobi, Death's Wail", 1);

    }
}
