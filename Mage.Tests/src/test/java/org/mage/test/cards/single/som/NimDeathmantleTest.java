package org.mage.test.cards.single.som;

import mage.abilities.keyword.IntimidateAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class NimDeathmantleTest extends CardTestPlayerBase {

    @Test
    public void test_Basic() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Equipped creature gets +2/+2, has intimidate, and is a black Zombie.
        // Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {4}. If you do, return that card to the battlefield and attach Nim Deathmantle to it.        
        addCard(Zone.HAND, playerA, "Nim Deathmantle"); // Artifact Equipment {2}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nim Deathmantle");
        
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, true); // Message: Nim Deathmantle - Pay {4}?
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Lightning Bolt",1);
        
        assertPermanentCount(playerA, "Nim Deathmantle", 1);
        
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertAbility(playerA, "Silvercoat Lion", IntimidateAbility.getInstance(), true);
        assertType("Silvercoat Lion", CardType.CREATURE, SubType.ZOMBIE);        

    }
}