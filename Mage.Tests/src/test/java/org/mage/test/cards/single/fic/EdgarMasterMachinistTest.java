package org.mage.test.cards.single.fic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class EdgarMasterMachinistTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.EdgarMasterMachinist Edgar, Master Machinist} {2}{R}{W}
     * Legendary Creature — Human Artificer Noble
     * Once during each of your turns, you may cast an artifact spell from your graveyard. If you cast a spell this way, that artifact enters tapped.
     * Tools — Whenever Edgar attacks, it gets +X/+0 until end of turn, where X is the greatest mana value among artifacts you control.
     * 2/4
     */
    private static final String edgar = "Edgar, Master Machinist";

    @Test
    public void test_cast_from_yard() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.GRAVEYARD, playerA, "Golgari Signet");
        addCard(Zone.GRAVEYARD, playerA, "Elite Vanguard");

        checkPlayableAbility("can not cast Elite Vanguard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Elite Vanguard", false);
        checkPlayableAbility("can cast Golgari Signet", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Golgari Signet", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golgari Signet");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Golgari Signet", 1);
        assertTappedCount("Plains", true, 2);
        assertTappedCount("Golgari Signet", true, 1);
    }

    @Test
    public void test_tapped_effect_wait_for_cleanup() {
        // test to make sure the discarding of the "enters tapped effect" only happens when the spell leave the stack
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.GRAVEYARD, playerA, "Golgari Signet");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golgari Signet");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Golgari Signet", 1);
        assertTappedCount("Mountain", true, 3);
        assertTappedCount("Golgari Signet", true, 1);
    }

    @Test
    public void test_cast_limits() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.GRAVEYARD, playerA, "Golgari Signet");
        addCard(Zone.GRAVEYARD, playerA, "Bear Trap"); // has flash

        addCard(Zone.GRAVEYARD, playerB, "Orzhov Signet");

        checkPlayableAbility("can not cast opponent Orzhov Signet", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Orzhov Signet", false);
        checkPlayableAbility("can cast Golgari Signet", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Golgari Signet", true);
        checkPlayableAbility("can cast Bear Trap", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bear Trap", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golgari Signet");

        checkPlayableAbility("can not cast 2 per turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Bear Trap", false);

        checkPlayableAbility("can not cast opponent Orzhov Signet", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Orzhov Signet", false);
        checkPlayableAbility("opp can not cast Orzhov Signet", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Orzhov Signet", false);
        checkPlayableAbility("can not cast Bear Trap on opp turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bear Trap", false);

        castSpell(3, PhaseStep.UPKEEP, playerA, "Bear Trap");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Golgari Signet", 1);
        assertPermanentCount(playerA, "Bear Trap", 1);
        assertTappedCount("Bear Trap", true, 1);
    }

    @Test
    public void test_mdfc() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.GRAVEYARD, playerA, "Halvar, God of Battle");

        checkPlayableAbility("can not cast Halvar, God of Battle", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Halvar, God of Battle", false);
        checkPlayableAbility("can cast Sword of the Realms", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Sword of the Realms", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sword of the Realms");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Sword of the Realms", 1);
        assertTappedCount("Plains", true, 2);
        assertTappedCount("Sword of the Realms", true, 1);
    }

    @Test
    public void test_adventure() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.GRAVEYARD, playerA, "Horn of Valhalla");

        checkPlayableAbility("can not cast Ysgard's Call", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ysgard's Call", false);
        checkPlayableAbility("can cast Horn of Valhalla", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Horn of Valhalla", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Horn of Valhalla");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Horn of Valhalla", 1);
        assertTappedCount("Plains", true, 2);
        assertTappedCount("Horn of Valhalla", true, 1);
    }

    @Test
    public void test_remand_recast() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Remand");

        addCard(Zone.GRAVEYARD, playerA, "Golgari Signet");
        addCard(Zone.GRAVEYARD, playerA, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golgari Signet");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Remand", "Golgari Signet", "Golgari Signet");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golgari Signet");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Golgari Signet", 1);
        assertGraveyardCount(playerB, "Remand", 1);
        assertTappedCount("Plains", true, 4);
        assertTappedCount("Golgari Signet", false, 1);
    }

    @Test
    public void test_blink() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Cloudshift", 1);
        addCard(Zone.GRAVEYARD, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentTapped("Memnite entered tapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", true, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertTappedCount("Memnite", false, 1);
    }
}
