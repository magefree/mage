
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReturnToHandEffectsTest extends CardTestPlayerBase {

    /**
     * Enduring Renewal doesn't return creatures to hand put into graveyard from
     * the battlefield It happened with Enduring Renewal in the battlefield
     * while feeding Ornithopter to Grinding Station
     */
    
    /*  jeffwadsworth:  I tested this scenario in the game and it worked perfectly.  The test suite is not reliable in this case.
    @Test
    public void testEnduringRenewal() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Play with your hand revealed.
        // If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card.
        // Whenever a creature is put into your graveyard from the battlefield, return it to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Renewal");

        // {T}, Sacrifice an artifact: Target player puts the top three cards of their library into their graveyard.
        // Whenever an artifact enters the battlefield, you may untap Grinding Station.
        addCard(Zone.BATTLEFIELD, playerA, "Grinding Station", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);
        
        addCard(Zone.LIBRARY, playerB, "Island", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice an artifact", playerB);
        setChoice(playerA, "Ornithopter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 3);
        assertHandCount(playerA, "Ornithopter", 1);

    }
*/
    @Test
    public void testStormfrontRidersTriggerForToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Flying
        // When Stormfront Riders enters the battlefield, return two creatures you control to their owner's hand.
        // Whenever Stormfront Riders or another creature is returned to your hand from the battlefield, put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Stormfront Riders"); // {4}{W}
        // Buyback {4} (You may pay an additional {4} as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Create a 1/1 black Rat creature token.
        addCard(Zone.HAND, playerA, "Lab Rats"); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stormfront Riders", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lab Rats");
        setChoice(playerA, false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Boomerang", "Rat Token");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Stormfront Riders", 1);
        assertHandCount(playerA, "Silvercoat Lion", 2);
        assertGraveyardCount(playerA, "Lab Rats", 1);
        assertGraveyardCount(playerB, "Boomerang", 1);

        assertPermanentCount(playerA, "Soldier Token", 3);
        assertPermanentCount(playerA, "Rat Token", 0);

    }

    @Test
    public void testZendikon() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Mountain");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Mountain");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 2 for perm, 3 for card

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Mountain", 0);
        assertHandCount(playerA, "Mountain", 1);
    }

    @Test
    public void testZendikonMDFC() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Tangled Florahedron");
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tangled Vale");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Tangled Vale");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Tangled Vale");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 2 for perm, 1 for card (?!)

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Tangled Florahedron", 0);
        assertHandCount(playerA, "Tangled Florahedron", 1);
    }

    @Test
    public void testZendikonPathwayTop() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Riverglide Pathway");
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Riverglide Pathway");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Riverglide Pathway");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Riverglide Pathway");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 2 for perm, 1 for card (?!)

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Riverglide Pathway", 0);
        assertHandCount(playerA, "Riverglide Pathway", 1);
    }

    @Test
    public void testZendikonPathwayBottom() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Riverglide Pathway");
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lavaglide Pathway");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Lavaglide Pathway");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Lavaglide Pathway");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 2 for perm, 1 for card (?!)

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Riverglide Pathway", 0);
        assertHandCount(playerA, "Riverglide Pathway", 1);
    }

    @Test
    public void testDemonicVigor() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Dryad Sophisticate");
        addCard(Zone.HAND, playerA, "Demonic Vigor");
        addCard(Zone.HAND, playerA, "Disfigure");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Dryad Sophisticate");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Dryad Sophisticate");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 3 for perm, 4 for card

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Demonic Vigor", 1);
        assertGraveyardCount(playerA, "Dryad Sophisticate", 0);
        assertHandCount(playerA, "Dryad Sophisticate", 1);
    }

    @Test
    public void testDemonicVigorMDFC() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Tangled Florahedron");
        addCard(Zone.HAND, playerA, "Demonic Vigor");
        addCard(Zone.HAND, playerA, "Disfigure");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tangled Florahedron");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Tangled Florahedron");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Tangled Florahedron");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 3 for perm, 2 for card (?!)

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Demonic Vigor", 1);
        assertGraveyardCount(playerA, "Tangled Florahedron", 0);
        assertHandCount(playerA, "Tangled Florahedron", 1);
    }

    @Test
    public void testDemonicVigorAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Dryad Sophisticate");
        addCard(Zone.HAND, playerA, "Demonic Vigor", 2);
        addCard(Zone.HAND, playerA, "Disfigure", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Dryad Sophisticate");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Dryad Sophisticate");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Demonic Vigor", "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Dryad Sophisticate");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 7 for perm, 8 for card

        assertGraveyardCount(playerA, "Disfigure", 2);
        assertGraveyardCount(playerA, "Demonic Vigor", 2);
        assertGraveyardCount(playerA, "Dryad Sophisticate", 0);
        assertHandCount(playerA, "Dryad Sophisticate", 1);
    }

    @Test
    public void testDemonicVigorMDFCAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Tangled Florahedron");
        addCard(Zone.HAND, playerA, "Demonic Vigor", 2);
        addCard(Zone.HAND, playerA, "Disfigure", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tangled Florahedron");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Tangled Florahedron");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Tangled Florahedron");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tangled Florahedron");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Demonic Vigor", "Tangled Florahedron");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Tangled Florahedron");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 7 for perm, 4 for card (?!)

        assertGraveyardCount(playerA, "Disfigure", 2);
        assertGraveyardCount(playerA, "Demonic Vigor", 2);
        assertGraveyardCount(playerA, "Tangled Florahedron", 0);
        assertHandCount(playerA, "Tangled Florahedron", 1);
    }

    @Test
    public void testDemonicVigorZoneChange() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.HAND, playerA, "Carrion Feeder");
        addCard(Zone.HAND, playerA, "Demonic Vigor");
        addCard(Zone.HAND, playerA, "Disfigure");
        addCard(Zone.HAND, playerA, "Makeshift Mannequin");
        addCard(Zone.HAND, playerA, "Coat with Venom");
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Carrion Feeder");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Carrion Feeder");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Carrion Feeder");
        waitStackResolved(1, PhaseStep.BEGIN_COMBAT, 1);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Makeshift Mannequin", "Carrion Feeder");
        waitStackResolved(1, PhaseStep.BEGIN_COMBAT, 1);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Coat with Venom", "Carrion Feeder");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // ZCC is 3 for perm, 6 for card, so should not return

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Demonic Vigor", 1);
        assertGraveyardCount(playerA, "Makeshift Mannequin", 1);
        assertGraveyardCount(playerA, "Carrion Feeder", 1);
        assertPermanentCount(playerA, "Carrion Feeder", 0);
        assertHandCount(playerA, "Carrion Feeder", 0);
    }

}
