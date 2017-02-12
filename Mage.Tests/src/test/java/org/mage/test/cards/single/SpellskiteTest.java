package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by goesta on 12/02/2017.
 */
public class SpellskiteTest extends CardTestPlayerBase {

    @Test
    public void testThatSplitDamageCanGetRedirected() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite");
        addCard(Zone.BATTLEFIELD, playerB, "Scute Mob");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        addCard(Zone.HAND, playerA, "Fiery Justice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Justice");
        addTarget(playerA, "Scute Mob");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Spellskite");
        setChoice(playerA, "X=4");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{UP}: Change a target of target spell or ability to {this}.", "Fiery Justice", "Fiery Justice");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 1);
        assertPowerToughness(playerB, "Scute Mob", 1, 1);
    }
}
