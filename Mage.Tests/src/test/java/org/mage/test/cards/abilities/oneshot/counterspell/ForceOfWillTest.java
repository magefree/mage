package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.f.ForceOfWill Force of Will} {3}{U}{U}
 * Instant
 * You may pay 1 life and exile a blue card from your hand rather than pay Force of Will's mana cost.
 * Counter target spell.
 *
 * @author LevelX2
 */
public class ForceOfWillTest extends CardTestPlayerBase {

    /**
     * Test that Force of Will can be played with alternate casting costs
     */
    @Test
    public void testWithBlueCardsInHand() {
        addCard(Zone.HAND, playerA, "Thoughtseize");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        
        addCard(Zone.HAND, playerB, "Force of Will");
        addCard(Zone.HAND, playerB, "Remand", 2); // blue cards to pay force of will
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thoughtseize", playerB);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Force of Will", "Thoughtseize");
        playerB.addChoice("Yes"); // use alternate costs
        
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerA, 20); 
        assertLife(playerB, 19); // losing 1 from Force of Will

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerB, 1);     // One Remand left
        assertGraveyardCount(playerB, 1); // Force of Will 
        assertExileCount("Remand", 1); // one Remand (cost from Force of Will)
    }
    
    /**
     * Test that Force of Will can'be played with alternate casting costs
     * if no blue card is in hand and not enough mana available
     */
    @Test
    public void testWithRedCardsInHand() {
        addCard(Zone.HAND, playerA, "Thoughtseize");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // No Red cards in hand
        addCard(Zone.HAND, playerB, "Force of Will");
        addCard(Zone.HAND, playerB, "Fireball", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thoughtseize", playerB);
        playerA.addChoice("Fireball");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Force of Will", "Thoughtseize");

        setStopAt(1, PhaseStep.CLEANUP);

        // TODO: Needed since the alternative cost is not being properly check for playability.
        try {
            execute();
            assertAllCommandsUsed();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find available command - activate:Cast Force of Will$target=Thoughtseize")) {
                Assert.fail("must throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerA, 18);
        assertLife(playerB, 20); // losing 1 from Force of Will

        assertHandCount(playerB, 2);      // 1 Fireball 1 Force of Will
        assertGraveyardCount(playerB, 1); // 1 Fireball discarded because of Thoughseize
    }
}