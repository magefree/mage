package org.mage.test.cards.single.dst;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DemonsHornTest extends CardTestPlayerBase {

    
    @Test
    public void testWithBlackSpell() {
        setStrictChooseMode(true);
        
        // When Abyssal Gatekeeper dies, each player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Abyssal Gatekeeper", 1); // Creature {2}{B}   1/1
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        // Whenever a player casts a black spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerB, "Demon's Horn", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Abyssal Gatekeeper");
        setChoice(playerB, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        
        assertPermanentCount(playerA, "Abyssal Gatekeeper", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 21);
    }
    
    /**
     * https://github.com/magefree/mage/issues/6890
     * 
     * Color == Color Identity #6890
     *
     * Alesha, Who Smiles at Death triggers Demon's Horn
     */
    @Test
    public void testSpellWithBlackManaOnlyInTriggeredOptionalCost() {
        setStrictChooseMode(true);
        
        // First strike
        // Whenever Alesha, Who Smiles at Death attacks, you may pay {W/B}{W/B}. If you do, return target creature card with power 2 or less from your graveyard to the battlefield tapped and attacking.        
        addCard(Zone.HAND, playerA, "Alesha, Who Smiles at Death", 1); // Creature {2}{R}   3/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        
        // Whenever a player casts a black spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerB, "Demon's Horn", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alesha, Who Smiles at Death");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        
        assertPermanentCount(playerA, "Alesha, Who Smiles at Death", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
