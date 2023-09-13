package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Codermann63
 */

public class UnfinishedBusinessTest extends CardTestPlayerBase {
    /*
    * Unfinished Business
    * {3}{W}{W} - Sorcery
    * Return target creature card from your graveyard to the battlefield,
    * then return up to two target Aura and/or Equipment cards from your graveyard to the battlefield attached to that creature.
    * (If the Auras can’t enchant that creature, they remain in your graveyard.)
    */
    private static final String UNFINISHEDBUSINESS = "Unfinished Business";
    // Deadly insect - 6/1 creature with Shroud
    private static final String SHROUDCREATURE = "Deadly Insect";
    // Apostle of Purifying Light - white creature with protection from black & activated ability to exile target card from a graveyard.
    private static final String APOSTLE = "Apostle of Purifying Light";
    private static final String BEAR = "Grizzly Bears";
    // Blanchwood Armor - green aura - enchanted creature gets +1/+1 for each forest you control
    private static final String AURA = "Blanchwood Armor";
    // Ghoulflesh - black aura - enchanted creature get -1/-1 and is a black Zombie in addition to its other colors and types.
    private static final String GHOULFLESH = "Ghoulflesh";
    // Shuko - colorless Equipment - Equipped creature gets +1/+0.
    private static final String EQUIPMENT = "Shuko";
    // Enormous Energy Blade - black equipment - Equipped creature gets +4/+0. Whenever Enormous Energy Blade becomes attached to a creature, tap that creature.
    private static final String EEB = "Enormous Energy Blade";


    // Return a creature with shroud, and return an Aura and an Equipment checking that both attach.
    @Test
    public void testShroud() {
        addCard(Zone.HAND, playerA, UNFINISHEDBUSINESS);
        addCard(Zone.GRAVEYARD, playerA, SHROUDCREATURE, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, AURA);
        addCard(Zone.GRAVEYARD, playerA, EQUIPMENT);

        setStrictChooseMode(true);

        castSpell(1,PhaseStep.PRECOMBAT_MAIN,playerA,UNFINISHEDBUSINESS,SHROUDCREATURE);
        addTarget(playerA, AURA+"^"+EQUIPMENT);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        execute();

        // Check that all returned to the battlefield
        assertPermanentCount(playerA, SHROUDCREATURE, 1);
        assertPermanentCount(playerA, AURA, 1);
        assertPermanentCount(playerA, EQUIPMENT, 1);

        // Check aura and equipment is attached
        assertAttachedTo(playerA, AURA, SHROUDCREATURE, true);
        assertAttachedTo(playerA, EQUIPMENT, SHROUDCREATURE, true);
    }



    // Return Apostle of purifying light (creature with protection from black),
    // and try to return a Ghoulflesh(black aura) and Enormous energy blade(black equipment).
    // The aura should remain in graveyard and the equipment should return but not attach.
    @Test
    public void testProtection(){
        addCard(Zone.HAND, playerA, UNFINISHEDBUSINESS);
        // Nexus wardens gain life if an enchantment entered the battlefield
        // This is to test if ghoulflesh enters the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Nexus Wardens");
        addCard(Zone.GRAVEYARD, playerA, APOSTLE);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, GHOULFLESH);
        addCard(Zone.GRAVEYARD, playerA, EEB);

        setStrictChooseMode(true);

        castSpell(1,PhaseStep.PRECOMBAT_MAIN,playerA,UNFINISHEDBUSINESS,APOSTLE);
        addTarget(playerA, GHOULFLESH+"^"+EEB);
        waitStackResolved(1, PhaseStep.END_TURN);

        execute();

        // Check boardstate
        assertPermanentCount(playerA, APOSTLE, 1);
        assertPermanentCount(playerA, GHOULFLESH, 0);
        assertPermanentCount(playerA, EEB, 1);

        // EEB should never have been attached and therefore the White knight should be untapped
        assertTapped(APOSTLE,false);
        assertAttachedTo(playerA, EEB, APOSTLE,false);

        // Check that Ghoulflesh never entered the battlefield
        assertLife(playerA, 20);
        assertGraveyardCount(playerA,GHOULFLESH, 1);
    }



    // Test equipment return if creature is exiled from graveyard before spell resolution
    @Test
    public void testExileCreatureBeforeResolution(){
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND,playerA,UNFINISHEDBUSINESS);
        addCard(Zone.GRAVEYARD, playerA, EQUIPMENT);
        addCard(Zone.GRAVEYARD, playerA, AURA);
        addCard(Zone.GRAVEYARD, playerA, BEAR);
        // Apostle of Purifying Light has an activated ability to exile card from graveyard
        addCard(Zone.BATTLEFIELD, playerB, APOSTLE);
        // Nexus wardens gain life if an enchantment entered the battlefield
        // This is to test if the aura enters the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Nexus Wardens");

        setStrictChooseMode(true);

        // Cast Unfinished Business targeting GrizzlyBears, and aura and an equipment
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, UNFINISHEDBUSINESS);
        addTarget(playerA, BEAR);
        addTarget(playerA, EQUIPMENT+"^"+AURA);
        // Exile Grizzly Bears from graveyard before spell resolution
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB,"{2}: ",BEAR,UNFINISHEDBUSINESS);
        waitStackResolved(1, PhaseStep.END_TURN);

        execute();

        // Grizzly Bears should be exiled
        assertExileCount(playerA, BEAR, 1);
        // The aura should still be in the graveyard and should never have entered
        assertGraveyardCount(playerA, AURA, 1);
        assertLife(playerA, 20);
        // The equipment should have returned to the battlefield
        assertPermanentCount(playerA,EQUIPMENT, 1);
    }
}
