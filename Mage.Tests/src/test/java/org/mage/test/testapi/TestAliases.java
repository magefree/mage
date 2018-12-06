package org.mage.test.testapi;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class TestAliases extends CardTestPlayerBase {

    @Test
    public void test_NamesEquals() {
        // empty names for face down cards
        Assert.assertTrue(CardUtil.haveEmptyName(""));
        Assert.assertTrue(CardUtil.haveEmptyName(EmptyNames.FACE_DOWN_CREATURE.toString()));
        Assert.assertFalse(CardUtil.haveEmptyName(" "));
        Assert.assertFalse(CardUtil.haveEmptyName("123"));
        Assert.assertFalse(CardUtil.haveEmptyName("Sample Name"));

        // same names (empty names can't be same)
        Assert.assertFalse(CardUtil.haveSameNames("", ""));
        Assert.assertFalse(CardUtil.haveSameNames(EmptyNames.FACE_DOWN_CREATURE.toString(), ""));
        Assert.assertFalse(CardUtil.haveSameNames(EmptyNames.FACE_DOWN_CREATURE.toString(), EmptyNames.FACE_DOWN_CREATURE.toString()));
        Assert.assertFalse(CardUtil.haveSameNames(EmptyNames.FACE_DOWN_TOKEN.toString(), ""));
        Assert.assertFalse(CardUtil.haveSameNames(EmptyNames.FACE_DOWN_TOKEN.toString(), EmptyNames.FACE_DOWN_CREATURE.toString()));
        Assert.assertTrue(CardUtil.haveSameNames("Name", "Name"));
        Assert.assertFalse(CardUtil.haveSameNames("Name", ""));
        Assert.assertFalse(CardUtil.haveSameNames("Name", " "));
        Assert.assertFalse(CardUtil.haveSameNames("Name", "123"));
        Assert.assertFalse(CardUtil.haveSameNames("Name", EmptyNames.FACE_DOWN_CREATURE.toString()));
        Assert.assertFalse(CardUtil.haveSameNames("Name1", "Name2"));

        // ignore mtg rules (empty names must be same)
        Assert.assertTrue(CardUtil.haveSameNames("", "", true));
        Assert.assertTrue(CardUtil.haveSameNames(EmptyNames.FACE_DOWN_CREATURE.toString(), EmptyNames.FACE_DOWN_CREATURE.toString(), true));
        Assert.assertTrue(CardUtil.haveSameNames("Name", "Name", true));
        Assert.assertFalse(CardUtil.haveSameNames("Name", "", true));
        Assert.assertFalse(CardUtil.haveSameNames("Name", " ", true));
        Assert.assertFalse(CardUtil.haveSameNames("Name", "123", true));
        Assert.assertFalse(CardUtil.haveSameNames("Name", EmptyNames.FACE_DOWN_CREATURE.toString(), true));
        Assert.assertFalse(CardUtil.haveSameNames("Name1", "Name2", true));
    }

    @Test
    public void test_DifferentZones() {
        addCard(Zone.LIBRARY, playerA, "Swamp@lib", 1);
        addCard(Zone.HAND, playerA, "Swamp@hand", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp@battle", 1);
        addCard(Zone.GRAVEYARD, playerA, "Swamp@grave", 1);

        showAliases("A aliases", 1, PhaseStep.UPKEEP, playerA);
        checkAliasZone("lib", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "lib", Zone.LIBRARY);
        checkAliasZone("hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "hand", Zone.HAND);
        checkAliasZone("battle", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "battle", Zone.BATTLEFIELD);
        checkAliasZone("grave", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "grave", Zone.GRAVEYARD);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_MultipleNames() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island@isl", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain@mnt", 5);

        checkPermanentCount("Swamp must exists", 1, PhaseStep.UPKEEP, playerA, "Swamp", 5);
        checkPermanentCount("Island must exists", 1, PhaseStep.UPKEEP, playerA, "Island", 5);
        checkPermanentCount("Plains must exists", 1, PhaseStep.UPKEEP, playerB, "Plains", 5);
        checkPermanentCount("Mountain must exists", 1, PhaseStep.UPKEEP, playerB, "Mountain", 5);
        //
        showAliases("A aliases", 1, PhaseStep.UPKEEP, playerA);
        showAliases("B aliases", 1, PhaseStep.UPKEEP, playerB);
        // A
        checkAliasZone("Swamp must not", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", Zone.BATTLEFIELD, false);
        checkAliasZone("Swamp.1 must not", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp.1", Zone.BATTLEFIELD, false);
        checkAliasZone("Island must not", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", Zone.BATTLEFIELD, false);
        checkAliasZone("isl must not", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "isl", Zone.BATTLEFIELD, false);
        checkAliasZone("isl.1 must", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "isl.1", Zone.BATTLEFIELD, true);
        checkAliasZone("isl.2 must", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "isl.2", Zone.BATTLEFIELD, true);
        checkAliasZone("isl.5 must", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "isl.5", Zone.BATTLEFIELD, true);
        // B
        checkAliasZone("Plains must not", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Plains", Zone.BATTLEFIELD, false);
        checkAliasZone("Plains.1 must not", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Plains.1", Zone.BATTLEFIELD, false);
        checkAliasZone("Plains must not", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains", Zone.BATTLEFIELD, false);
        checkAliasZone("mnt must not", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "mnt", Zone.BATTLEFIELD, false);
        checkAliasZone("mnt.1 must", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "mnt.1", Zone.BATTLEFIELD, true);
        checkAliasZone("mnt.2 must", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "mnt.2", Zone.BATTLEFIELD, true);
        checkAliasZone("mnt.5 must", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "mnt.5", Zone.BATTLEFIELD, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_CastTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion@lion", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@lion.1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@lion.3");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@lion.5");

        showAliases("A aliases", 1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        checkAliasZone("1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.1", Zone.BATTLEFIELD, false);
        checkAliasZone("2", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.2", Zone.BATTLEFIELD, true);
        checkAliasZone("3", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.3", Zone.BATTLEFIELD, false);
        checkAliasZone("4", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.4", Zone.BATTLEFIELD, true);
        checkAliasZone("5", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.5", Zone.BATTLEFIELD, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Lightning Bolt", 3);
        assertGraveyardCount(playerA, "Silvercoat Lion", 3);
    }
}