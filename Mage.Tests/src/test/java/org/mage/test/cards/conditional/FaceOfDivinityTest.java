package org.mage.test.cards.conditional;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FaceOfDivinityTest extends CardTestPlayerBase {

    @Test
    public void test_BoostCondition() {
        // Enchanted creature gets +2/+2.
        // As long as another Aura is attached to enchanted creature, it has first strike and lifelink.
        addCard(Zone.HAND, playerA, "Face of Divinity", 1); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        //
        // Enchanted creature gets +1/+0 and can’t be blocked.
        addCard(Zone.HAND, playerA, "Aether Tunnel", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // attach without extra boost
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Face of Divinity", "Balduvian Bears");
        checkPT("boost 1", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 2 + 2, 2 + 2);
        checkAbility("boost 1", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", FirstStrikeAbility.class, false);

        // attach aura and get full boost
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Aether Tunnel", "Balduvian Bears");
        checkPT("boost 2", 1, PhaseStep.END_TURN, playerA, "Balduvian Bears", 2 + 2 + 1, 2 + 2);
        checkAbility("boost 2", 1, PhaseStep.END_TURN, playerA, "Balduvian Bears", FirstStrikeAbility.class, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
