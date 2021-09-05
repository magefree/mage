package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class WrathOfGodTest extends CardTestPlayerBase {

    @Test
    public void testDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Wrath of God");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // If Mossbridge Troll would be destroyed, regenerate it.
        // Tap any number of untapped creatures you control other than Mossbridge Troll with total power 10 or greater: Mossbridge Troll gets +20/+20 until end of turn.        
        addCard(Zone.BATTLEFIELD, playerA, "Mossbridge Troll");

        // Flying
        // Darksteel Gargoyle is indestructible. ("Destroy" effects and lethal damage don't destroy it.)
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Gargoyle");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Mossbridge Troll");
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Gargoyle");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Mossbridge Troll", 0);
        assertPermanentCount(playerA, "Darksteel Gargoyle", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Mossbridge Troll", 0);
        assertPermanentCount(playerB, "Darksteel Gargoyle", 1);
    }
}