package org.mage.test.cards.cost.modaldoublefaces;

import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.PermanentCard;
import mage.util.CardUtil;
import mage.util.ManaUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
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

        // mdf and legendary
        addCard(Zone.HAND, playerA, "Halvar, God of Battle");

        // mdf and color identity
        addCard(Zone.HAND, playerA, "Esika, God of the Tree");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        Assert.assertEquals(3, getHandCards(playerA).size());

        // stats in hand - normal
        Card card = getHandCards(playerA).stream().filter(c -> CardUtil.haveSameNames(c, "Akoum Warrior", currentGame)).findFirst().get();
        Assert.assertFalse("must be non land", card.isLand(currentGame));
        Assert.assertTrue("must be creature", card.isCreature(currentGame));
        Assert.assertTrue("must be minotaur", card.hasSubtype(SubType.MINOTAUR, currentGame));
        Assert.assertEquals("power", 4, card.getPower().getValue());
        Assert.assertEquals("toughness", 5, card.getToughness().getValue());

        // stats in hand - mdf
        card = getHandCards(playerA).stream().filter(c -> CardUtil.haveSameNames(c, "Halvar, God of Battle", currentGame)).findFirst().get();
        Assert.assertTrue("must be legendary", card.isLegendary());
        Assert.assertTrue("must be creature", card.isCreature(currentGame));
        Assert.assertTrue("must be god", card.hasSubtype(SubType.GOD, currentGame));

        // stats in hand - mdf - color identity must be from both sides
        card = getHandCards(playerA).stream().filter(c -> CardUtil.haveSameNames(c, "Esika, God of the Tree", currentGame)).findFirst().get();
        Assert.assertEquals("color identity of mdf card must be from both sides", "{W}{U}{B}{R}{G}", ManaUtil.getColorIdentity(card).toString());
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
    public void test_Zones_AfterCast_1() {
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
    public void test_Zones_AfterCast_2() {
        removeAllCardsFromHand(playerA);
        // possible bug: if you click on mdf card second side then keep in hand after cast (sorcery + land)
        // P.S. it works in GUI only, reason: user can sends UUID from wrong card side

        // Ondu Inversion {6}{W}{W} - sorcery
        // Ondu Skyruins - land
        addCard(Zone.HAND, playerA, "Ondu Inversion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);

        // prepare mdf permanent
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ondu Skyruins");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ondu Skyruins", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, 0);
        Card card = currentGame.getState().getBattlefield().getAllPermanents()
                .stream()
                .filter(p -> CardUtil.haveSameNames(p, "Ondu Skyruins", currentGame))
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
        checkPermanentCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkExileCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_ExileAsSecondSideAndReturnAsMainSide() {
        // https://github.com/magefree/mage/issues/7212

        // When Flickerwisp enters the battlefield, exile another target permanent. Return that card to the battlefield
        // under its owner’s control at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Flickerwisp"); // {1}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare mdf permanent as land
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth");
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 1);

        // exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flickerwisp");
        addTarget(playerA, "Akoum Teeth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkPermanentCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);
        checkExileCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1); // exile as main card
        checkExileCount("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);

        // return at the end
        checkPermanentCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkPermanentCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);
        checkExileCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);
        checkExileCount("return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Teeth", 0);

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
        setChoice(playerA, true); // as copy
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
        setChoice(playerA, true); // +1 charge on turn 1
        setChoice(playerA, true); // +1 charge on turn 3
        setChoice(playerA, true); // +1 charge on turn 5

        // put card from hand to battlefield
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You may put a creature card");
        setChoice(playerA, true); // put card
        setChoice(playerA, "Glasspool Mimic"); // select card with cmc 3 from hand
        //
        setChoice(playerA, true); // put to battlefield as copy
        setChoice(playerA, "Balduvian Bears"); // copy of

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Balduvian Bears", 2);
    }

    @Test
    public void test_Single_HostageTaker_CastFromExile() {
        // bug: mdf must be playable as both sides
        // https://github.com/magefree/mage/pull/7446

        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6); // cast from hostage taker for any color
        //
        // When Hostage Taker enters the battlefield, exile another target artifact or creature until Hostage Taker
        // leaves the battlefield. You may cast that card as long as it remains exiled, and you may spend mana
        // as though it were mana of any type to cast that spell.
        addCard(Zone.HAND, playerA, "Hostage Taker", 1); // {2}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // prepare mdf on battlefield
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // exile by hostage
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hostage Taker");
        addTarget(playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        // play as creature for any color
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Akoum Warrior", 1);
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

    @Test
    public void test_Cascade_ValkiGodOfLies() {
        // https://magic.wizards.com/en/articles/archive/news/february-15-2021-banned-and-restricted-announcement
        // For example, if you cast Bloodbraid Elf and exile Valki, God of Lies from your library,
        // you'll be able to cast Valki but not Tibalt, Cosmic Impostor. On the other hand, if you
        // exile Cosima, God of the Voyage, you may cast either Cosima or The Omenkeel, as each face
        // has a lesser converted mana cost than Bloodbraid Elf.
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        // Cascade
        addCard(Zone.HAND, playerA, "Bloodbraid Elf"); // {2}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        addCard(Zone.LIBRARY, playerA, "Valki, God of Lies", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 2);

        // play elf with cascade
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodbraid Elf");
        setChoice(playerA, true); // use free cast
        //setChoice(playerA, "Cast Valki, God of Lies"); possible bug: you can see two spell abilities to choose, but only one allows here
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // no choices for valki's etb exile

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Valki, God of Lies", 1);
    }

    @Test
    public void test_Cascade_CosimaGodOfTheVoyage() {
        // https://magic.wizards.com/en/articles/archive/news/february-15-2021-banned-and-restricted-announcement
        // For example, if you cast Bloodbraid Elf and exile Valki, God of Lies from your library,
        // you'll be able to cast Valki but not Tibalt, Cosmic Impostor. On the other hand, if you
        // exile Cosima, God of the Voyage, you may cast either Cosima or The Omenkeel, as each face
        // has a lesser converted mana cost than Bloodbraid Elf.
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        // Cascade
        addCard(Zone.HAND, playerA, "Bloodbraid Elf"); // {2}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        addCard(Zone.LIBRARY, playerA, "Cosima, God of the Voyage", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 2);

        // play elf with cascade
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodbraid Elf");
        setChoice(playerA, true); // use free cast
        setChoice(playerA, "Cast The Omenkeel"); // can cast any side here

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "The Omenkeel", 1);
    }

    @Test
    public void test_Copy_AsSpell() {
        addCard(Zone.HAND, playerA, "Akoum Warrior", 1); // {5}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        addCard(Zone.HAND, playerA, "Double Major", 1); // {G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // cast mdf card
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        // prepare copy of spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Akoum Warrior", "Akoum Warrior");
        checkStackSize("before copy spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkStackSize("after copy spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Copy_AsCloneFromPermanent() {
        addCard(Zone.HAND, playerA, "Akoum Warrior", 1); // {5}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // cast mdf card
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // copy permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Akoum Warrior"); // copy source
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    @Ignore // TODO: Zam Wesell must be reworked to use on cast + etb abilities
    public void test_Copy_AsCloneFromCard_ZamWesell() {
        // When you cast Zam Wesell, target opponent reveals their hand. You may choose a creature card from it
        // and have Zam Wesell enter the battlefield as a copy of that creature card.
        addCard(Zone.HAND, playerA, "Zam Wesell"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.HAND, playerB, "Akoum Warrior", 1);

        // cast as copy of mdf card
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zam Wesell");
        addTarget(playerA, playerB); // target opponent
        setChoice(playerA, "Akoum Warrior"); // creature card to copy
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Copy_AsCloneFromCard_ValkiGodOfLies() {
        // When Valki enters the battlefield, each opponent reveals their hand. For each opponent,
        // exile a creature card they revealed this way until Valki leaves the battlefield.
        // X: Choose a creature card exiled with Valki with converted mana cost X. Valki becomes a copy of that card.
        addCard(Zone.HAND, playerA, "Valki, God of Lies"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 + 3); // 3 for X
        //
        addCard(Zone.HAND, playerB, "Birgi, God of Storytelling", 1); // {2}{R}

        // prepare valki
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Valki, God of Lies");
        setChoice(playerA, "Birgi, God of Storytelling"); // exile
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // copy exiled card
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}:");
        setChoice(playerA, "X=3");
        setChoice(playerA, "Birgi, God of Storytelling");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Valki, God of Lies", 0);
        assertPermanentCount(playerA, "Birgi, God of Storytelling", 1);
    }

    @Test
    public void test_FindMovedPermanentByCard() {
        // original problem: you must be able to find a card after move it to battlefield
        // https://github.com/magefree/mage/issues/8474

        // {R}: You may put a creature card from your hand onto the battlefield. That creature gains haste.
        // Sacrifice the creature at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Sneak Attack");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.HAND, playerA, "Akoum Warrior", 1);

        // move
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:");
        setChoice(playerA, true); // yes, activate
        setChoice(playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after move", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkAbility("must have haste after etb", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", HasteAbility.class, true);

        // must sacrifice
        checkPermanentCount("after sacrifice", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}