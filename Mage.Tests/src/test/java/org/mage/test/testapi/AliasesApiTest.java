package org.mage.test.testapi;

import mage.cards.Card;
import mage.cards.repository.CardRepository;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AliasesApiTest extends CardTestPlayerBase {

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

        // name with split card
        Card splitCard1 = CardRepository.instance.findCard("Armed // Dangerous").getCard();
        Card splitCard2 = CardRepository.instance.findCard("Alive // Well").getCard();
        Assert.assertTrue(CardUtil.haveSameNames(splitCard1, "Armed", currentGame));
        Assert.assertTrue(CardUtil.haveSameNames(splitCard1, "Dangerous", currentGame));
        Assert.assertTrue(CardUtil.haveSameNames(splitCard1, "Armed // Dangerous", currentGame));
        Assert.assertTrue(CardUtil.haveSameNames(splitCard1, splitCard1));
        Assert.assertFalse(CardUtil.haveSameNames(splitCard1, "Other", currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(splitCard1, "Other // Dangerous", currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(splitCard1, "Armed // Other", currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(splitCard1, splitCard2));

        // name with face down spells: face down spells don't have names, see https://github.com/magefree/mage/issues/6569
        Card bearCard = CardRepository.instance.findCard("Balduvian Bears").getCard();
        Spell normalSpell = new Spell(bearCard, bearCard.getSpellAbility(), playerA.getId(), Zone.HAND, currentGame);
        Spell faceDownSpell = new Spell(bearCard, bearCard.getSpellAbility(), playerA.getId(), Zone.HAND, currentGame);
        faceDownSpell.setFaceDown(true, currentGame);
        // normal spell
        Assert.assertFalse(CardUtil.haveSameNames(normalSpell, "", currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(normalSpell, "Other", currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(normalSpell, EmptyNames.FACE_DOWN_CREATURE.toString(), currentGame));
        Assert.assertTrue(CardUtil.haveSameNames(normalSpell, "Balduvian Bears", currentGame));
        // face down spell
        Assert.assertFalse(CardUtil.haveSameNames(faceDownSpell, "", currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(faceDownSpell, "Other", currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(faceDownSpell, EmptyNames.FACE_DOWN_CREATURE.toString(), currentGame));
        Assert.assertFalse(CardUtil.haveSameNames(faceDownSpell, "Balduvian Bears", currentGame));
    }

    @Test
    public void test_DifferentZones() {
        addCard(Zone.LIBRARY, playerA, "Swamp@lib", 1);
        addCard(Zone.HAND, playerA, "Swamp@hand", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp@battle", 1);
        addCard(Zone.GRAVEYARD, playerA, "Swamp@grave", 1);

        checkAliasZone("lib", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "lib", Zone.LIBRARY);
        checkAliasZone("hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "hand", Zone.HAND);
        checkAliasZone("battle", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "battle", Zone.BATTLEFIELD);
        checkAliasZone("grave", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "grave", Zone.GRAVEYARD);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
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
    }

    @Test
    public void test_CastTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion@lion", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@lion.1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@lion.3");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@lion.5");

        checkAliasZone("1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.1", Zone.BATTLEFIELD, false);
        checkAliasZone("2", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.2", Zone.BATTLEFIELD, true);
        checkAliasZone("3", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.3", Zone.BATTLEFIELD, false);
        checkAliasZone("4", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.4", Zone.BATTLEFIELD, true);
        checkAliasZone("5", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "lion.5", Zone.BATTLEFIELD, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 3);
        assertGraveyardCount(playerA, "Silvercoat Lion", 3);
    }

    @Test
    public void test_CastSpell_Normal() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void test_CastSpell_Alias() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion@lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt@bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bolt", "@lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "@bolt", 1);
        assertGraveyardCount(playerA, "@lion", 1);
    }

    @Test
    public void test_ActivateAbility_Normal() {
        // {T}: Embermage Goblin deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Embermage Goblin", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        showAvailableAbilities("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Embermage Goblin", 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void test_ActivateAbility_Alias() {
        // {T}: Embermage Goblin deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Embermage Goblin@goblin", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion@lion", 1);

        // use 2 of 3 goblins
        showAvailableAbilities("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        showAliases("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@goblin.1 {T}: {this} deals", "@lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@goblin.3 {T}: {this} deals", "@lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "@goblin.1", 1);
        assertPermanentCount(playerA, "@goblin.2", 1);
        assertPermanentCount(playerA, "@goblin.3", 1);
        assertGraveyardCount(playerA, "@lion", 1);
        assertTapped("@goblin.1", true);
        assertTapped("@goblin.2", false);
        assertTapped("@goblin.3", true);
    }

    @Test
    public void test_ActivateManaAbility_Alias() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion@lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain@mana", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt@bolt", 1);

        // testing alias for ability
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@mana.2 {T}: Add {R}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bolt", "@lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("@mana.1", false);
        assertTapped("@mana.2", true);
        assertTapped("@mana.3", false);
        assertGraveyardCount(playerA, "@bolt", 1);
        assertGraveyardCount(playerA, "@lion", 1);
    }
}
