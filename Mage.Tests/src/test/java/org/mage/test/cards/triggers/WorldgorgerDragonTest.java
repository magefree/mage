/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class WorldgorgerDragonTest extends CardTestPlayerBase {

    /**
     * Tests that exiled permanents return to battlefield
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Worldgorger Dragon");
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Gerrard's Battle Cry", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1);
        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldgorger Dragon");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Worldgorger Dragon");
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Worldgorger Dragon", 1);
        assertGraveyardCount(playerB, "Terror", 1);
        
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Gerrard's Battle Cry", 1);
        
    }

    /**
        1. Cast Animate Dead, targeting the Dragon
        2. Dragon comes into play, it's ability goes on the stack.
        3. The ability resolves, and all my other permanents leave play
        4. Since Animate Dead left play, Dragon goes to the graveyard
        5. Since the Dragon left play, the land and Animate Dead return to play. Animate Dead triggers, targeting the Dragon.
        6. In response to Animate Dead's ability going on the stack, tap the lands for mana.
        7. Animate Dead resolves, Dragon comes into play, everything else leaves play.
        8. Steps 4-7 repeat endlessly. Your mana pool fills.
        9. You can interrupt the sequence to play an instant.
     */
    @Test
    public void testWithAnimateDead() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        // When Worldgorger Dragon enters the battlefield, exile all other permanents you control.
        // When Worldgorger Dragon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.
        addCard(Zone.GRAVEYARD, playerA, "Worldgorger Dragon", 1);
        addCard(Zone.HAND, playerA, "Animate Dead");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Instant {X}{R}{R}
        // Volcanic Geyser deals X damage to target creature or player.
        addCard(Zone.HAND, playerA, "Volcanic Geyser", 1);
        // When Staunch Defenders enters the battlefield, you gain 4 life.
        addCard(Zone.BATTLEFIELD, playerA, "Staunch Defenders", 1);
        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Worldgorger Dragon");
        
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB, 22);
        setChoice(playerA, "X=20");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 0);

        assertGraveyardCount(playerA, "Volcanic Geyser", 1);
        
    }
    
}