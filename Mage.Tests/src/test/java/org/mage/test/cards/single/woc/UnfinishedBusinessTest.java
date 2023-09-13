package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Codermann63
 */

public class UnfinishedBusinessTest extends CardTestPlayerBase {

    private static final String UNFINISHEDBUSINESS = "Unfinished Business";
    private static final String SHROUDCREATURE = "Deadly Insect";
    private static final String BEAR = "Grizzly Bears";
    private static final String WHITEKNIGHT = "White Knight";
    private static final String AURA = "Blanchwood Armor";
    private static final String GHOULFLESH = "Ghoulflesh";
    private static final String EQUIPMENT = "Shuko";
    private static final String EEB = "Enormous Energy Blade";
    private static final String APOSTLE = "Apostle of Purifying Light";

    // Return a creature with shroud, and return an Aura and an Equipment checking that both attach.
    @Test
    public void testShroud() {
        addCard(Zone.HAND, playerA, UNFINISHEDBUSINESS);
        addCard(Zone.GRAVEYARD, playerA, SHROUDCREATURE, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, AURA);
        addCard(Zone.GRAVEYARD, playerA, EQUIPMENT);

        castSpell(1,PhaseStep.PRECOMBAT_MAIN,playerA,UNFINISHEDBUSINESS,SHROUDCREATURE);
        addTarget(playerA, AURA+"^"+EQUIPMENT);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        execute();

        // Check for permanents
        assertPermanentCount(playerA, SHROUDCREATURE, 1);
        assertPermanentCount(playerA, AURA, 1);
        assertPermanentCount(playerA, EQUIPMENT, 1);

        // Check aura and equipment is attached
        assertAttachedTo(playerA, AURA, SHROUDCREATURE, true);
        assertAttachedTo(playerA, EQUIPMENT, SHROUDCREATURE, true);
    }



    // Return a White Knight with protection from black,
    // and try to return a Ghoulflesh(black aura) and Enormous energy blade(black equipment).
    // The aura should remain in graveyard and the equipment should return but not attach.
    @Test
    public void testProtection(){
        addCard(Zone.HAND, playerA, UNFINISHEDBUSINESS);
        addCard(Zone.BATTLEFIELD, playerA, "Nexus Wardens"); // To test if ghoulflesh enters the battlefield
        addCard(Zone.GRAVEYARD, playerA, WHITEKNIGHT);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, GHOULFLESH);
        addCard(Zone.GRAVEYARD, playerA, EEB);

        castSpell(1,PhaseStep.PRECOMBAT_MAIN,playerA,UNFINISHEDBUSINESS,WHITEKNIGHT);
        addTarget(playerA, GHOULFLESH+"^"+EEB);
        waitStackResolved(1, PhaseStep.END_TURN);

        execute();

        // Check boardstate
        assertPermanentCount(playerA, WHITEKNIGHT, 1);
        assertPermanentCount(playerA, GHOULFLESH, 0);
        assertPermanentCount(playerA, EEB, 1);

        // EEB should never have been attached and therefore the White knight should be untapped
        assertTapped(WHITEKNIGHT,false);
        assertAttachedTo(playerA, EEB, WHITEKNIGHT,false);

        // Check that Ghoulflesh never entered the battlefield
        assertLife(playerA, 20);
        assertGraveyardCount(playerA,GHOULFLESH, 1);
    }



    // Test equipment return if creature is exiled from graveyard before resolution
    @Test
    public void testExileCreatureBeforeResolution(){
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND,playerA,UNFINISHEDBUSINESS);
        addCard(Zone.GRAVEYARD, playerA, EQUIPMENT);
        addCard(Zone.GRAVEYARD, playerA, AURA);
        addCard(Zone.GRAVEYARD, playerA, BEAR);
        addCard(Zone.BATTLEFIELD, playerB, APOSTLE); // Apostle to exile creature from graveyard

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, UNFINISHEDBUSINESS);
        addTarget(playerA, BEAR);
        addTarget(playerA, EQUIPMENT+"^"+AURA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB,"{2}: ",BEAR,UNFINISHEDBUSINESS);

        waitStackResolved(1, PhaseStep.END_TURN);

        execute();
        assertExileCount(playerA, BEAR, 1);
        assertGraveyardCount(playerA, AURA, 1);
        assertPermanentCount(playerA,EQUIPMENT, 1);
    }



}
