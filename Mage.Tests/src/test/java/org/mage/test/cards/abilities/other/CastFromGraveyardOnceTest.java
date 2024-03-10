package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class CastFromGraveyardOnceTest extends CardTestPlayerBase {

    private static final String danitha = "Danitha, New Benalia's Light"; // 2/2
    // Vigilance, trample, lifelink
    // Once during each of your turns, you may cast an Aura or Equipment spell from your graveyard.

    private static final String bonesplitter = "Bonesplitter"; // 1 mana equip 1 for +2/+0
    private static final String kitesail = "Kitesail"; // 2 mana equip 2 for +1/+0 and flying

    @Test
    public void testDanithaAllowsOneCast() {
        addCard(Zone.BATTLEFIELD, playerA, danitha);
        addCard(Zone.GRAVEYARD, playerA, bonesplitter);
        addCard(Zone.GRAVEYARD, playerA, kitesail);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Raff Capashen, Ship's Mage"); // historic spells have flash

        checkPlayableAbility("bonesplitter your turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bonesplitter, true);
        checkPlayableAbility("kitesail your turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + kitesail, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kitesail);

        checkPermanentCount("kitesail on battlefield", 1, PhaseStep.BEGIN_COMBAT, playerA, kitesail, 1);
        checkPlayableAbility("no second cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bonesplitter, false);

        checkPlayableAbility("not during opponent's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bonesplitter, false);

        checkPlayableAbility("available next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bonesplitter, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, bonesplitter);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bonesplitter, 1);
    }
}
