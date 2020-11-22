package org.mage.test.cards.cost.modaldoublefaces;

import mage.cards.Card;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.PermanentCard;
import mage.util.CardUtil;
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
        checkPlayableAbility("can play as creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

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
    public void test_PlayFromNonHand_SecondSideAsLand_ByRadhaHeartOfKeld() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.LIBRARY, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        // You may look at the top card of your library any time, and you may play lands from the top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Radha, Heart of Keld");

        checkLibraryCount("library before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPlayableAbility("can't play as creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", false);
        checkPlayableAbility("can play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        // play as land
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth");
        checkLibraryCount("library after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlayFromNonHand_SecondSideAsLand_CrucibleOfWorlds() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.GRAVEYARD, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        // You may play lands from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Crucible of Worlds");

        checkGraveyardCount("graveyard before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPlayableAbility("can't play as creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", false);
        checkPlayableAbility("can play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        // play as land
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth");
        checkLibraryCount("library after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
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
    public void test_DoubleLands_IgnoreDefaultAbilities() {
        // https://github.com/magefree/mage/issues/7197

        // Branchloft Pathway - land
        // Boulderloft Pathway - land
        addCard(Zone.HAND, playerA, "Branchloft Pathway");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        // possible bug: duplicated abilities list in main card
        Assert.assertEquals("must have only 2 play abilities", 2, playerA.getPlayable(currentGame, true).size());
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
    public void test_Zones_AfterCast() {
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare mdf permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        Card card = currentGame.getState().getBattlefield().getAllPermanents()
                .stream()
                .filter(p -> CardUtil.haveSameNames(p, "Akoum Warrior", currentGame))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(card);
        Assert.assertEquals("permanent card must be on battlefield", Zone.BATTLEFIELD, currentGame.getState().getZone(card.getId()));
        Assert.assertEquals("main permanent card must be on battlefield", Zone.BATTLEFIELD, currentGame.getState().getZone(card.getMainCard().getId()));
        Assert.assertEquals("half card must be on battlefield", Zone.BATTLEFIELD, currentGame.getState().getZone(((PermanentCard) card).getCard().getId()));
        Assert.assertEquals("main card must be on battlefield", Zone.BATTLEFIELD, currentGame.getState().getZone(((PermanentCard) card).getCard().getMainCard().getId()));
    }

    @Test
    public void test_Zones_AfterExile() {
        // {2}, {tap}: Exile target permanent you control.
        addCard(Zone.BATTLEFIELD, playerA, "Synod Sanctum");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare mdf permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, ", "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkExileCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        Card card = currentGame.getState().getExile().getAllCards(currentGame)
                .stream()
                .filter(p -> CardUtil.haveSameNames(p, "Akoum Warrior", currentGame))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(card);
        Assert.assertTrue("must be mdf card", card instanceof ModalDoubleFacesCard);
        ModalDoubleFacesCard mdfCard = (ModalDoubleFacesCard) card;
        Assert.assertEquals("card must be on exile", Zone.EXILED, currentGame.getState().getZone(mdfCard.getId()));
        Assert.assertEquals("left part must be on exile", Zone.EXILED, currentGame.getState().getZone(mdfCard.getLeftHalfCard().getId()));
        Assert.assertEquals("right part must be on exile", Zone.EXILED, currentGame.getState().getZone(mdfCard.getRightHalfCard().getId()));
    }

    @Test
    public void test_ExileAndReturnToBattlefield_AsCreature() {
        // rules:
        // If an effect puts a double-faced card onto the battlefield, it enters with its front face up. If that
        // front face can’t be put onto the battlefield, it doesn’t enter the battlefield. For example, if an
        // effect exiles Sejiri Glacier and returns it to the battlefield, it remains in exile because an instant
        // can’t be put onto the battlefield.

        // +2: Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Venser, the Sojourner");
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare mdf permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2:", "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkExileCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // return at the end
        showBattlefield("hmm b", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        showExile("hmm e", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        showGraveyard("hmm g", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkExileCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_ExileAndReturnToBattlefield_AsLand() {
        // rules:
        // If an effect puts a double-faced card onto the battlefield, it enters with its front face up. If that
        // front face can’t be put onto the battlefield, it doesn’t enter the battlefield. For example, if an
        // effect exiles Sejiri Glacier and returns it to the battlefield, it remains in exile because an instant
        // can’t be put onto the battlefield.

        // SO it can't return card as land

        // +2: Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Venser, the Sojourner");
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare mdf permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2:", "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkExileCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // return at the end
        showBattlefield("hmm b", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        showExile("hmm e", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        showGraveyard("hmm g", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkExileCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_ChooseName_AsCreature() {
        // rules:
        // If an effect instructs a player to choose a card name, the name of either face may be chosen. If that
        // effect or a linked ability refers to a spell with the chosen name being cast and/or a land with the
        // chosen name being played, it considers only the chosen name, not the other face’s name.

        // Choose a card name. Until your next turn, spells with the chosen name can’t be cast and lands with the chosen name can’t be played.
        addCard(Zone.HAND, playerA, "Conjurer's Ban"); // {W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // can play before
        checkPlayableAbility("can play creature before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can play land before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        // make restrict for creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conjurer's Ban");
        setChoice(playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can't play creature after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", false);
        checkPlayableAbility("can play land after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        // can play again later
        checkPlayableAbility("can play creature again", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can play land again", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_ChooseName_AsLand() {
        // rules:
        // If an effect instructs a player to choose a card name, the name of either face may be chosen. If that
        // effect or a linked ability refers to a spell with the chosen name being cast and/or a land with the
        // chosen name being played, it considers only the chosen name, not the other face’s name.

        // Choose a card name. Until your next turn, spells with the chosen name can’t be cast and lands with the chosen name can’t be played.
        addCard(Zone.HAND, playerA, "Conjurer's Ban"); // {W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // can play before
        checkPlayableAbility("can play creature before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can play land before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        // make restrict for land
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conjurer's Ban");
        setChoice(playerA, "Akoum Teeth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can play creature after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can't play land after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", false);

        // can play again later
        checkPlayableAbility("can play creature again", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("can play land again", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
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

    @Test
    public void test_Single_GlasspoolMimic_NormalPlay() {
        // https://github.com/magefree/mage/issues/7174

        // Glasspool Mimic
        // You may have Glasspool Mimic enter the battlefield as a copy of a creature you control, except it’s a Shapeshifter Rogue in addition to its other types.
        addCard(Zone.HAND, playerA, "Glasspool Mimic"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        // cast and make copy of bear
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glasspool Mimic");
        setChoice(playerA, "Yes"); // as copy
        setChoice(playerA, "Balduvian Bears"); // copy of

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Balduvian Bears", 2);
    }

    @Test
    public void test_Single_GlasspoolMimic_PutToHand() {
        // https://github.com/magefree/mage/issues/7174

        // Glasspool Mimic
        // You may have Glasspool Mimic enter the battlefield as a copy of a creature you control, except it’s a Shapeshifter Rogue in addition to its other types.
        addCard(Zone.HAND, playerA, "Glasspool Mimic"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        // Aether Vial
        // {T}: You may put a creature card with converted mana cost equal to the number of charge counters on Aether Vial from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Aether Vial", 1);

        // prepare charge counters
        setChoice(playerA, "Yes"); // +1 charge on turn 1
        setChoice(playerA, "Yes"); // +1 charge on turn 3
        setChoice(playerA, "Yes"); // +1 charge on turn 5

        // put card from hand to battlefield
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You may put a creature card");
        setChoice(playerA, "Yes"); // put card
        setChoice(playerA, "Glasspool Mimic"); // select card with cmc 3 from hand
        //
        setChoice(playerA, "Yes"); // put to battlefield as copy
        setChoice(playerA, "Balduvian Bears"); // copy of

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Balduvian Bears", 2);
    }

    @Test
    public void test_ETB_OnlySideCardsCanAddAbilitiesToGame() {
        // possible bug: double triggers (loadCard adds abilities from main + side cards instead side card only)
        // https://github.com/magefree/mage/issues/7187

        // Skyclave Cleric
        // creature 1/3
        // When Skyclave Cleric enters the battlefield, you gain 2 life.
        addCard(Zone.HAND, playerA, "Skyclave Cleric"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Skyclave Cleric");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 + 2); // +2 from 1 etb trigger
    }

    @Test
    public void test_ETB_OnlyActualSideCardCanRaiseTriggers() {
        // possible bug: you play one card but game raise triggers from another side too
        // https://github.com/magefree/mage/issues/7180

        // Kazandu Mammoth
        // creature 3/3
        // Landfall — Whenever a land enters the battlefield under your control, Kazandu Mammoth gets +2/+2 until end of turn.
        //
        // Kazandu Valley
        // land
        addCard(Zone.HAND, playerA, "Kazandu Mammoth"); // {1}{G}{G}

        // play land, but no landfall triggers from other side
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kazandu Valley");
        checkStackSize("no triggers", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}