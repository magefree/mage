package org.mage.test.cards.cost.modaldoublefaces;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ModalDoubleFacesCardsTest extends CardTestPlayerBase {

    @Test
    public void test_Playable_AsCreature() {
        removeAllCardsFromHand(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6 - 1);
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // can't cast without mana, but can play land
        checkPlayableAbility("before land left", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", false);
        checkPlayableAbility("before land right", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);
        checkPlayableAbility("before land both", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior // Akoum Teeth", false);

        // play land
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        // can cast creature, but can't play land
        checkPlayableAbility("after land left", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("after land right", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", false);
        checkPlayableAbility("after land both", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior // Akoum Teeth", false);

        // cast creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("hand after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Playable_AsLand() {
        removeAllCardsFromHand(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // cast and play restrictions tested in prev test, so use here simple land play

        checkPlayableAbility("before play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", false);
        checkPlayableAbility("before play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Mountain", true);
        checkHandCount("before play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

        // play as land
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth");
        checkHandCount("hand after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2 - 1);
        checkPermanentCount("after play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("after play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 1);
        checkPlayableAbility("after play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", false);
        checkPlayableAbility("can't play second land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Mountain", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_CostModification() {
        removeAllCardsFromHand(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6 - 3);

        addCustomEffect_SpellCostModification(playerA, -3);

        // cast creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("hand after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlayFromNonHand_LibraryByBolassCitadel() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.LIBRARY, playerA, "Akoum Warrior");
        //
        // You may play the top card of your library. If you cast a spell this way, pay life equal
        // to its converted mana cost rather than pay its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");

        checkLibraryCount("library before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPlayableAbility("can play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can play as creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        // play as creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLibraryCount("library after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 6); // creature life pay instead mana
    }

    @Test
    public void test_PlayFromNonHand_GraveyardByYawgmothsAgenda() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.GRAVEYARD, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        // You may play cards from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Yawgmoth's Agenda");

        checkGraveyardCount("grave before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPlayableAbility("can play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can play as creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        // play as creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("grave after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Characteristics() {
        // rules:
        // While a double-faced card isn’t on the stack or battlefield, consider only the characteristics
        // of its front face. For example, the above card has only the characteristics of Sejiri Shelter
        // in the graveyard, even if it was Sejiri Glacier on the battlefield before it was put into the
        // graveyard. Notably, this means that Sejiri Shelter is a nonland card even though you could play
        // it as a land
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // stats in hand
        Assert.assertEquals(1, getHandCards(playerA).size());
        Card card = getHandCards(playerA).get(0);
        Assert.assertFalse("must be non land", card.isLand());
        Assert.assertTrue("must be creature", card.isCreature());
        Assert.assertTrue("must be minotaur", card.getSubtype(currentGame).contains(SubType.MINOTAUR));
        Assert.assertEquals("power", 4, card.getPower().getValue());
        Assert.assertEquals("toughness", 5, card.getToughness().getValue());
    }

    @Test
    public void test_PlayFromNonHand_GraveyardByFlashback() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Emeria's Call - Sorcery {4}{W}{W}{W}
        // Emeria, Shattered Skyclave - land
        // Create two 4/4 white Angel Warrior creature tokens with flying. Non-Angel creatures you control gain indestructible until your next turn.
        addCard(Zone.GRAVEYARD, playerA, "Emeria's Call");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        //
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback
        // until end of turn. The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        checkGraveyardCount("grave before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emeria's Call", 1);
        checkPlayableAbility("can't play as sorcery", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Emeria's Call", false);
        checkPlayableAbility("can't play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Emeria, Shattered Skyclave", false);

        // cast Snapcaster and give flashback
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Emeria's Call");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("grave before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emeria's Call", 1);
        checkPlayableAbility("can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", true);

        // cast as sorcery with flashback
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("exile after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emeria's Call", 1);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emeria's Call", 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emeria, Shattered Skyclave", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
    }

    @Test
    public void test_Single_MalakirRebirth() {
        // Malakir Rebirth
        // Choose target creature. You lose 2 life. Until end of turn, that creature gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        addCard(Zone.HAND, playerA, "Malakir Rebirth"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // cast instant and give gained ability
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Malakir Rebirth", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // check gained ability (bear must be returned after die)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}