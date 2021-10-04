package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MultipleTimesUsableActivatedManaAbilitiesTest extends CardTestPlayerBase {

    /**
     * Seton, Krosan Protector - only seems to get counted as if it were one
     * mana for determining if a spell can be cast, regardless of how many
     * druids you have in playF
     */
    @Test
    public void testCanBeCastWithSetonKrosanProtector() {
        // Tap an untapped Druid you control: Add {G}.
        addCard(Zone.BATTLEFIELD, playerA, "Seton, Krosan Protector", 1); // Creature {G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Citanul Druid", 3);
        
        addCard(Zone.HAND, playerA, "Leatherback Baloth", 1); // Creature 4/5
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Leatherback Baloth");
        
        setChoice(playerA, "Citanul Druid");
        setChoice(playerA, "Citanul Druid");
        setChoice(playerA, "Citanul Druid");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        
        setStrictChooseMode(true);
        execute();
        
        assertAllCommandsUsed();
        
        assertTappedCount("Citanul Druid", true, 3);
        assertPermanentCount(playerA, "Leatherback Baloth", 1);
    }

}
