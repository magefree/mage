
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ManaFlareTest extends CardTestPlayerBase {

    @Test
    public void testIsland() {
        // Whenever a player taps a land for mana, that player adds one mana of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Mana Flare", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Creature {U}{U}
        // {U},{T} :Return target permanent you control to its owner's hand.
        addCard(Zone.HAND, playerA, "Vedalken Mastermind", 1);

        // because available mana calculation does not work correctly with Mana Flare we have to tap the land manually
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vedalken Mastermind");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vedalken Mastermind", 1);

    }

    /**
     * Mana Flare is only adding colorless mana, at least off of dual lands
     * (Watery Grave in this instance). Island only adds colorless. Plains adds
     * white though.
     */
    @Test
    public void testWateryGrave() {
        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerB, "Mana Flare", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Watery Grave", 1);

        // Creature {B}{B}
        // {B}: Nantuko Shade gets +1/+1 until end of turn.
        addCard(Zone.HAND, playerB, "Nantuko Shade", 1);

        // because available mana calculation does not work correctly with Mana Flare we have to tap the land manually
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {B}");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Nantuko Shade");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Nantuko Shade", 1);

    }

}
