
package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 712.8a
 * While a double-faced card is outside the game or in a zone other than the battlefield or stack, it has only the
 * characteristics of its front face.
 * <p>
 * 712.8c
 * Normally, a nonmodal double-faced spell has its front face up while on the stack and has only the characteristics
 * of its front face.
 * <p>
 * 712.11b
 * A player casting a modal double-faced card or a copy of a modal double-faced card as a spell chooses which face
 * they are casting before putting it onto the stack.
 * <p>
 * Ruling (2018-07-13): If a nonland card has multiple types, such as an artifact creature, you may cast it as either
 * of those types. For example, you could cast one artifact creature as your artifact card and another artifact
 * creature as your creature card.
 *
 * @author LevelX2, DarioBarrabinoDev
 */
public class AminatousAuguryTest extends CardTestPlayerBase {

    @Test
    public void testCastMultiple() {
        setStrictChooseMode(true);
        
        
        addCard(Zone.LIBRARY, playerA, "Pillarfield Ox"); // Creature (2/4)
        // As an additional cost to cast this spell, discard a card.
        // Draw two cards.
        addCard(Zone.LIBRARY, playerA, "Tormenting Voice"); // Sorcery
        // {1}: Adarkar Sentinel gets +0/+1 until end of turn.        
        addCard(Zone.LIBRARY, playerA, "Adarkar Sentinel"); // Artifact Creature {5} (3/3) 
        addCard(Zone.LIBRARY, playerA, "Storm Crow");        
        // You have hexproof. (You can't be the target of spells or abilities your opponents control.)
        addCard(Zone.LIBRARY, playerA, "Aegis of the Gods"); // Enchantment Creature {1}{W} (2/1)
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // Instant 
        addCard(Zone.LIBRARY, playerA, "Badlands");
        skipInitShuffling();
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a card of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // SORCERY {6}{U}{U}
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8); 
                
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury", true);
        setChoice(playerA, true); // Put a land from among the exiled cards into play?
        setChoice(playerA, "Badlands"); // Select a land card
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adarkar Sentinel", true);
        setChoice(playerA, "Artifact"); // Which card type do you want to consume?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aegis of the Gods", true);
        setChoice(playerA, "Enchantment"); // Which card type do you want to consume?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storm Crow", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tormenting Voice");
        setChoice(playerA, "Silvercoat Lion"); // Select a card (discard cost)
        
        checkPlayableAbility("Cannot cast second creature from exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Pillarfield Ox", Boolean.FALSE); // Type Creature type is already consumed
        execute();

        assertGraveyardCount(playerA, "Aminatou's Augury", 1);
        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Badlands", 1);
        assertPermanentCount(playerA, "Adarkar Sentinel", 1);
        assertPermanentCount(playerA, "Aegis of the Gods", 1);
        assertPermanentCount(playerA, "Storm Crow", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
        
        assertHandCount(playerA, 2);
        assertGraveyardCount(playerA, "Silvercoat Lion",1);
        assertExileCount(playerA, 2);
    }

    @Test
    public void test_CastAgainSameTurn_OwnCardTypes() {
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a spell of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 16);
        //
        addCard(Zone.LIBRARY, playerA, "Island", 2); // for draws
        addCard(Zone.LIBRARY, playerA, "Divination"); // sorcery, exiled by the second Augury
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 7);
        addCard(Zone.LIBRARY, playerA, "Regrowth"); // sorcery, exiled by the first Augury
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 7);
        skipInitShuffling();

        // first Augury: use the sorcery type to return Augury to hand
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Regrowth", "Aminatou's Augury"); // return Augury
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // second Augury: it has its own sorcery type
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Aminatou's Augury", 1);
        assertGraveyardCount(playerA, "Regrowth", 1);
        assertGraveyardCount(playerA, "Divination", 1);
        assertHandCount(playerA, 2); // from Divination
    }

    @Test
    public void test_TransformingDoubleFacedCard() {
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a spell of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 7);
        addCard(Zone.LIBRARY, playerA, "Aberrant Researcher"); // creature 3/2, back face Perfected Form
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("back face can't be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Perfected Form", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aberrant Researcher"); // front face as creature

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Aberrant Researcher", 1);
    }

    @Test
    public void test_ModalDoubleFacedCard() {
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a spell of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 6);
        addCard(Zone.LIBRARY, playerA, "Tergrid, God of Fright"); // creature, back face Tergrid's Lantern (artifact)
        addCard(Zone.LIBRARY, playerA, "Sea Gate Restoration"); // sorcery, back face Sea Gate, Reborn (land)
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("land face can't be played", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Sea Gate, Reborn", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tergrid's Lantern"); // back face as artifact
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sea Gate Restoration"); // front face as sorcery

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Tergrid's Lantern", 1);
        assertGraveyardCount(playerA, "Sea Gate Restoration", 1);
    }

    @Test
    public void test_ArtifactCreature_ConsumesOneType() {
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a spell of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 5);
        addCard(Zone.LIBRARY, playerA, "Memnite"); // artifact creature 1/1
        addCard(Zone.LIBRARY, playerA, "Ornithopter"); // artifact creature 0/2
        addCard(Zone.LIBRARY, playerA, "Adarkar Sentinel"); // artifact creature 3/3
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adarkar Sentinel");
        setChoice(playerA, "Creature"); // Which card type do you want to consume?
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter"); // as artifact
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("artifact and creature are used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Memnite", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Adarkar Sentinel", 1);
        assertPermanentCount(playerA, "Ornithopter", 1);
    }

    @Test
    public void test_SplitCard() {
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a spell of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        //
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // instant
        addCard(Zone.LIBRARY, playerA, "Fire // Ice"); // instant // instant
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire", playerB); // one half as instant
        addTargetAmount(playerA, playerB, 2); // all damage to playerB
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("instant is used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Fire // Ice", 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_AdventureCard() {
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a spell of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        //
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // creature 2/2
        addCard(Zone.LIBRARY, playerA, "Bonecrusher Giant"); // creature, adventure Stomp (instant)
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears"); // creature
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stomp", playerB); // adventure as instant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_CastAgainLaterTurn_OwnCardTypes() {
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a spell of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        // Return target card from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Regrowth"); // {1}{G}, sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 17); // 8 for turn 1, 1 draw and 8 for turn 3
        skipInitShuffling();

        // turn 1: use the creature type
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        // turn 3: return and cast Augury again, the creature type is available again
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Regrowth", "Aminatou's Augury"); // return Augury
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertGraveyardCount(playerA, "Aminatou's Augury", 1);
    }

}
