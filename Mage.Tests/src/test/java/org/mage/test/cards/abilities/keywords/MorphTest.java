package org.mage.test.cards.abilities.keywords;

import mage.cards.Card;
import mage.constants.*;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author levelX2
 */
public class MorphTest extends CardTestPlayerBase {

    /**
     * Tests if a creature with Morph is cast normal, it behaves as normal
     * creature
     */
    @Test
    public void testCastMorphCreatureWithoutMorph() {
        /*
         Pine Walker
         Creature - Elemental
         5/5
         Morph {4}{G} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
         Whenever Pine Walker or another creature you control is turned face up, untap that creature.
         */
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, false); // cast it normal as 5/5

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Pine Walker", 1);
        assertPowerToughness(playerA, "Pine Walker", 5, 5);

    }

    /**
     * Cast the creature face down as a 2/2
     */
    @Test
    public void testCastFaceDown() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2);

    }

    /**
     * Test triggered turn face up ability of Pine Walker
     */
    @Test
    public void testTurnFaceUpTrigger() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        attack(3, playerA, EmptyNames.FACE_DOWN_CREATURE.toString());

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}: Turn this face-down permanent face up.");
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
        assertPermanentCount(playerA, "Pine Walker", 1);
        assertPowerToughness(playerA, "Pine Walker", 5, 5);
        assertTapped("Pine Walker", false);

    }

    /**
     * Test that the triggered "turned face up" ability of Pine Walker does not
     * trigger as long as Pine Walker is not turned face up.
     */
    @Test
    public void testDoesNotTriggerFaceDown() {
        // Whenever Pine Walker or another creature you control is turned face up, untap that creature.
        addCard(Zone.HAND, playerA, "Pine Walker");
        // When Icefeather Aven is turned face up, you may return another target creature to its owner's hand.
        addCard(Zone.HAND, playerA, "Icefeather Aven");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Icefeather Aven", TestPlayer.NO_TARGET, "Pine Walker", StackClause.WHILE_NOT_ON_STACK);
        setChoice(playerA, true); // cast it face down as 2/2 creature

        attack(3, playerA, EmptyNames.FACE_DOWN_CREATURE.toString());
        attack(3, playerA, EmptyNames.FACE_DOWN_CREATURE.toString());
        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{1}{G}{U}: Turn this face-down permanent face up.");
        setChoice(playerA, false); // Don't use return permanent to hand effect

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);

        assertHandCount(playerA, "Pine Walker", 0);
        assertHandCount(playerA, "Icefeather Aven", 0);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPermanentCount(playerA, "Icefeather Aven", 1);
        assertTapped("Icefeather Aven", true);

    }

    /**
     * Test that Morph creature do not trigger abilities with their face up
     * attributes
     */
    @Test
    public void testMorphedRemovesAttributesCreature() {
        // Ponyback Brigade {3}{R}{W}{B}
        // Creature - Goblin Warrior
        // 2/2
        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        // Morph {2}{R}{W}{B}(You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerA, "Ponyback Brigade");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Soldier of the Pantheon", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ponyback Brigade");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20); // and not 21

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPermanentCount(playerB, "Soldier of the Pantheon", 1);

    }

    /**
     * Test to copy a morphed 2/2 creature
     */
    @Test
    public void testCopyAMorphedCreature() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Clever Impersonator  {2}{U}{U}
        // Creature - Shapeshifter
        // 0/0
        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clever Impersonator");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2);
        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPowerToughness(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2);
    }

    /**
     *
     */
    @Test
    public void testPineWalkerWithUnboostEffect() {
        // Morph {4}{G}
        // Whenever Pine Walker or another creature you control is turned face up, untap that creature.
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        // Doomwake Giant  {4}{B}
        // Creature - Giant
        // 4/6
        // Constellation - When Doomwake Giant or another enchantment enters the battlefield under your control, creatures your opponents control get -1/-1 until end of turn.
        addCard(Zone.HAND, playerB, "Doomwake Giant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Doomwake Giant");

        // activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{R}{W}{B}: Turn this face-down permanent face up.");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}: Turn this face-down permanent face up.");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Pine Walker", 0);
        assertHandCount(playerB, "Doomwake Giant", 0);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
        assertPermanentCount(playerB, "Doomwake Giant", 1);
        assertPermanentCount(playerA, "Pine Walker", 1);
        assertPowerToughness(playerA, "Pine Walker", 4, 4);

    }

    /**
     * If a morph is on the table and an enemy Doomwake Giant comes down, the
     * morph goes down to 1/1 correctly. If you unmorph the 2/2 and is also a
     * 2/2 after umorphing, the morph will be erroneously reduced to 0/0 and
     * die.
     */
    @Test
    public void testDoomwakeGiantEffect() {
        addCard(Zone.HAND, playerA, "Ponyback Brigade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // Doomwake Giant  {4}{B}
        // Creature - Giant
        // 4/6
        // Constellation - When Doomwake Giant or another enchantment enters the battlefield under your control, creatures your opponents control get -1/-1 until end of turn.
        addCard(Zone.HAND, playerB, "Doomwake Giant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ponyback Brigade");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Doomwake Giant");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{R}{W}{B}: Turn this face-down permanent face up.");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Ponyback Brigade", 0);
        assertHandCount(playerB, "Doomwake Giant", 0);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
        assertPermanentCount(playerA, "Goblin Token", 3);
        assertPowerToughness(playerA, "Goblin Token", 1, 1, Filter.ComparisonScope.Any);
        assertPermanentCount(playerB, "Doomwake Giant", 1);
        assertPermanentCount(playerA, "Ponyback Brigade", 1);
        assertPowerToughness(playerA, "Ponyback Brigade", 1, 1);

    }

    /**
     * Clone a Morph creature that was cast face down and meanwhile was turned
     * face up
     */
    @Test
    public void testCloneFaceUpMorphEffect() {
        // Sagu Mauler 6/6 - Creature - Beast
        // Trample, hexproof
        // Morph {3}{G}{B} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerA, "Sagu Mauler");
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler");
        setChoice(playerA, true); // cast it face down as 2/2 creature
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{G}{U}: Turn this face-down permanent face up.");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Sagu Mauler");

        setStopAt(5, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Sagu Mauler", 0);
        assertHandCount(playerA, "Clone", 0);

        assertPermanentCount(playerA, "Sagu Mauler", 2);
        assertPowerToughness(playerA, "Sagu Mauler", 6, 6, Filter.ComparisonScope.Any);

    }

    /**
     * Check that you can't counter a creature cast for it morph costs with
     * Disdainful Stroke if it's normal cmc > 3
     */
    @Test
    public void testCounterCastWithMorphEffect() {
        // Sagu Mauler 6/6 - Creature - Beast
        // Trample, hexproof
        // Morph {3}{G}{B} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerA, "Sagu Mauler");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // Disdainful Stroke {1}{U}
        // Instant
        // Counter target spell with converted mana cost 4 or greater.
        addCard(Zone.HAND, playerB, "Disdainful Stroke");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        checkPlayableAbility("Can't Disdainful Stroke Sagu", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Disdainful", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Sagu Mauler", 0);
        assertHandCount(playerB, "Disdainful Stroke", 1); // can't be cast

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);

    }

    /**
     * Check that an effect like "Target creature and all other creatures with
     * the same name" does only effect one face down creature, also if multiple
     * on the battlefield. Because they have no name, they don't have the same
     * name.
     */
    @Test
    public void testEchoingDecaySameNameEffect() {
        // Sagu Mauler 6/6 - Creature - Beast
        // Trample, hexproof
        // Morph {3}{G}{B} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerA, "Sagu Mauler", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        // Echoing Decay {1}{B}
        // Instant
        // Target creature and all other creatures with the same name as that creature get -2/-2 until end of turn.
        addCard(Zone.HAND, playerB, "Echoing Decay");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler");
        setChoice(playerA, true); // cast it face down as 2/2 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler", TestPlayer.NO_TARGET, "Sagu Mauler", StackClause.WHILE_NOT_ON_STACK);
        setChoice(playerA, true); // cast it face down as 2/2 creature

        // showBattlefield("A battle", 1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Echoing Decay", EmptyNames.FACE_DOWN_CREATURE.toString());

        // showBattlefield("A battle after", 1, PhaseStep.END_TURN, playerA);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 20);

        assertHandCount(playerB, "Echoing Decay", 0);
        assertGraveyardCount(playerB, "Echoing Decay", 1);

        assertHandCount(playerA, "Sagu Mauler", 0);
        assertHandCount(playerB, "Echoing Decay", 0);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertGraveyardCount(playerA, "Sagu Mauler", 1);
    }

    /**
     * I played a Akroma, Angel of Fury face down, and my opponent tried to
     * counter it. The counter failed and Akroma face successfully play face
     * down, when it should have been countered. (The card text on akroma should
     * not prevent her from being countered).
     */
    @Test
    public void testRuleModifyingEffectsFromManifestedCardWontBeAppliedAbilities() {
        addCard(Zone.HAND, playerA, "Akroma, Angel of Fury", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.HAND, playerB, "Counterspell", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Fury");
        setChoice(playerA, true); // cast it face down as 2/2 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Akroma, Angel of Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Counterspell", 1);
        assertGraveyardCount(playerA, "Akroma, Angel of Fury", 1);

    }

    /**
     * Check if a face down Morph creature gets exiled, it will be face up in
     * exile zone.
     */
    @Test
    public void testExileFaceDownCreature() {
        addCard(Zone.HAND, playerA, "Birchlore Rangers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Swords to Plowshares", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birchlore Rangers");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Swords to Plowshares", "");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 22); // + 2 from Swords to Plowshares
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Swords to Plowshares", 1);
        assertExileCount("Birchlore Rangers", 1);

        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals("Birchlore Rangers")) {
                Assert.assertEquals("Birchlore Rangers has to be face up in exile", false, card.isFaceDown(currentGame));
                break;
            }
        }

    }

    /**
     * Check that a DiesTriggeredAbility of a creature does not trigger if the
     * creature dies face down
     */
    @Test
    public void testDiesTriggeredDoesNotTriggerIfFaceDown() {
        // Flying
        // When Ashcloud Phoenix dies, return it to the battlefield face down.
        // Morph (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        // When Ashcloud Phoenix is turned face up, it deals 2 damage to each player.
        addCard(Zone.HAND, playerA, "Ashcloud Phoenix", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ashcloud Phoenix");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", EmptyNames.FACE_DOWN_CREATURE.toString());

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Ashcloud Phoenix", 1);

        for (Card card : playerA.getGraveyard().getCards(currentGame)) {
            if (card.getName().equals("Ashcloud Phoenix")) {
                Assert.assertEquals("Ashcloud Phoenix has to be face up in graveyard", false, card.isFaceDown(currentGame));
                break;
            }
        }

    }

    /**
     * Check that a DiesTriggeredAbility of a creature does not trigger if the
     * creature dies face down in combat
     */
    @Test
    public void testDiesTriggeredDoesNotTriggerInCombatIfFaceDown() {
        // Flying
        // When Ashcloud Phoenix dies, return it to the battlefield face down.
        // Morph (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        // When Ashcloud Phoenix is turned face up, it deals 2 damage to each player.
        addCard(Zone.HAND, playerA, "Ashcloud Phoenix", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // First strike, forestwalk, vigilance
        // (This creature deals combat damage before creatures without first strike, it can't be blocked as long as defending player controls a Forest, and attacking doesn't cause this creature to tap.)
        addCard(Zone.BATTLEFIELD, playerB, "Mirri, Cat Warrior");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ashcloud Phoenix");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        attack(2, playerB, "Mirri, Cat Warrior");
        block(2, playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), "Mirri, Cat Warrior");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Ashcloud Phoenix", 1);

        for (Card card : playerA.getGraveyard().getCards(currentGame)) {
            if (card.getName().equals("Ashcloud Phoenix")) {
                Assert.assertEquals("Ashcloud Phoenix has to be face up in graveyard", false, card.isFaceDown(currentGame));
                break;
            }
        }

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Supplant Form does not work correctly with morph creatures. If you bounce
     * and copy a face-down morph, the created token should be a colorless 2/2,
     * but the token created is instead the face-up of what the morph creature
     * was.
     */
    @Test
    public void testSupplantFormWithMorphedCreature() {
        addCard(Zone.HAND, playerA, "Akroma, Angel of Fury", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Return target creature to its owner's hand. You put a token onto the battlefield that's a copy of that creature
        addCard(Zone.HAND, playerB, "Supplant Form", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Fury");
        setChoice(playerA, true); // cast it face down as 2/2 creature
//        showBattlefield("A battle", 1, PhaseStep.POSTCOMBAT_MAIN, playerA);
//        showBattlefield("B battle", 1, PhaseStep.POSTCOMBAT_MAIN, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Supplant Form");
        addTarget(playerB, EmptyNames.FACE_DOWN_CREATURE.toString());

//        showBattlefield("A battle end", 1, PhaseStep.END_TURN, playerA);
//        showBattlefield("B battle end", 1, PhaseStep.END_TURN, playerB);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Akroma, Angel of Fury", 1);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
        assertPermanentCount(playerA, "Akroma, Angel of Fury", 0);
        assertGraveyardCount(playerB, "Supplant Form", 1);

        assertPermanentCount(playerB, "Akroma, Angel of Fury", 0);
        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_TOKEN.toString(), 1);
        assertPowerToughness(playerB, EmptyNames.FACE_DOWN_TOKEN.toString(), 2, 2);
    }

    /**
     * Dragonlord Kolaghan passive of 10 damage works when you play a morph
     * creature and it isn't suposed to. Because it is nameless.
     */
    @Test
    public void testDragonlordKolaghan() {
        addCard(Zone.GRAVEYARD, playerA, "Akroma, Angel of Fury", 1);
        addCard(Zone.HAND, playerA, "Akroma, Angel of Fury", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Flying, haste
        // Other creatures you control have haste.
        // Whenever an opponent casts a creature or planeswalker spell with the same name as a card in their graveyard, that player loses 10 life.
        addCard(Zone.BATTLEFIELD, playerB, "Dragonlord Kolaghan", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Fury");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);

    }

    /**
     * Linvala, Keep of Silence is preventing morph creatures from turning face
     * up. Turning face up is a special ability not an active ability. This
     * should not be prevented by the loss of active abilities.
     */
    @Test
    public void testTurnFaceUpWithLinvala() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        /*
            Linvala, Keeper of Silence {2}{W}{W}
            Legendary Creature - Angel 3/4
            Flying
            Activated abilities of creatures your opponents control can't be activated.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Linvala, Keeper of Silence", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        attack(3, playerA, EmptyNames.FACE_DOWN_CREATURE.toString());

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}: Turn this face-down permanent face up.");
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
        assertPermanentCount(playerA, "Pine Walker", 1);
        assertPowerToughness(playerA, "Pine Walker", 5, 5);
        assertTapped("Pine Walker", false);
    }

    /**
     * Reflector Mage bouncing a creature that can be played as a morph should
     * not prevent the card from being replayed as a morph. Morph creatures are
     * nameless.
     * <p>
     * Reported bug: Face-up morph creatures that are bounced by Reflector Mage
     * should be able to be replayed as morphs without the "until the next turn"
     * restriction."
     */
    @Test
    public void test_ReflectorMageCantStopMorphToCast_TryNormalCast() {
        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand.
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerA, "Reflector Mage"); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Tap: Add {G}, {U}, or {R}.
        // Morph 2 (You may cast this card face down as a 2/2 creature for 3. Turn it face up any time for its morph cost.)
        // When Rattleclaw Mystic is turned face up, add {G}{U}{R}.
        addCard(Zone.BATTLEFIELD, playerB, "Rattleclaw Mystic"); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        setStrictChooseMode(true);

        // return to hand
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reflector Mage");
        addTarget(playerA, "Rattleclaw Mystic");

        // try cast as normal -- must not work
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Rattleclaw Mystic");
        setChoice(playerB, false);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find available command - activate:Cast Rattleclaw Mystic (use checkPlayableAbility for \"non available\" checks)")) {
                Assert.fail("Should have gotten an error about not being able to cast Rattleclaw, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "Reflector Mage", 1);
        assertPermanentCount(playerB, "Rattleclaw Mystic", 0);
        assertHandCount(playerB, "Rattleclaw Mystic", 1); // can't play
        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 0); // don't try as morph
    }

    @Test
    public void test_ReflectorMageCantStopMorphToCast_TryMorph() {
        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand.
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerA, "Reflector Mage"); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Tap: Add {G}, {U}, or {R}.
        // Morph 2 (You may cast this card face down as a 2/2 creature for 3. Turn it face up any time for its morph cost.)
        // When Rattleclaw Mystic is turned face up, add {G}{U}{R}.
        addCard(Zone.BATTLEFIELD, playerB, "Rattleclaw Mystic"); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        // return to hand
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reflector Mage");
        addTarget(playerA, "Rattleclaw Mystic");

        // try cast as morph - must work
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Rattleclaw Mystic");
        setChoice(playerB, true); // try cast as morph

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Reflector Mage", 1);
        assertPermanentCount(playerB, "Rattleclaw Mystic", 0);
        assertHandCount(playerB, "Rattleclaw Mystic", 0); // able cast as morph
        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    /**
     * Reflector Mage bouncing a creature that can be played as a morph should
     * not prevent the card from being replayed as a morph. Morph creatures are
     * nameless.
     * <p>
     * Reported bug: Face-up morph creatures that are bounced by Reflector Mage
     * should be able to be replayed as morphs without the "until the next turn"
     * restriction."
     * <p>
     * Testing bouncing a face-down creature played next turn face-up.
     */
    @Test
    public void testReflectorMageBouncesMorphCreatureReplayAsFaceup() {

        //Tap: Add {G}, {U}, or {R}.
        // Morph 2 (You may cast this card face down as a 2/2 creature for 3. Turn it face up any time for its morph cost.)
        // When Rattleclaw Mystic is turned face up, add {G}{U}{R}.
        addCard(Zone.HAND, playerA, "Rattleclaw Mystic"); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand.
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerB, "Reflector Mage"); // 2/3
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rattleclaw Mystic");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Reflector Mage");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Rattleclaw Mystic");
        setChoice(playerA, false); // cast it face down as 2/2 creature

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerB, "Reflector Mage", 1);
        assertPermanentCount(playerA, "Rattleclaw Mystic", 1);
        assertHandCount(playerA, "Rattleclaw Mystic", 0); // should have been replayed faceup
    }

    /**
     * The well-known combo with Vesuvan Shapeshifter and Brine Elemental does
     * not work correctly. When Vesuvan Shapeshifter turns face up and becomes a
     * copy of the targeted creature, it should still be in the state of
     * "turning face up", thus triggering the ability of the Brine Elemental.
     * <p>
     * combo: Vesuvan Shapeshifter + Brine Elemental Brine Elemental in play,
     * Vesuvan Shapeshifter in hand 1) Cast Vesuvan Shapeshifter face-down. 2)
     * Flip Vesuvan Shapeshifter for its morph cost, copying Brine Elemental.
     * Your opponent skips his next untap. 3) During your upkeep, flip Vesuvan
     * Shapeshifter face-down. 4) Repeat from 2.
     */
    @Test
    public void testVesuvanShapeshifter() {

        // Morph {5}{U}{U}
        // When Brine Elemental is turned face up, each opponent skips their next untap step.
        addCard(Zone.BATTLEFIELD, playerA, "Brine Elemental"); // Creature {4}{U}{U} 5/4
        //addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        // As Vesuvan Shapeshifter enters the battlefield or is turned face up, you may choose another creature on the battlefield.
        // If you do, until Vesuvan Shapeshifter is turned face down, it becomes a copy of that creature
        // and gains "At the beginning of your upkeep, you may turn this creature face down."
        // Morph {1}{U} (You may cast this card face down as a 2/2 creature for 3. Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerB, "Vesuvan Shapeshifter"); // Creature 0/0
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        // 1. Cast Vesuvan as face-down
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Vesuvan Shapeshifter");
        setChoice(playerB, true); // cast as face-down

        // 2. Moth Vesuvan and copy brine
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{1}{U}: Turn this face-down permanent");
        addTarget(playerB, "Brine Elemental");

        // No face up trigger and choose from Vesuvan
        // But brine's trigger must works on next turn 3 (skip untap)
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Brine Elemental", 1);
        assertPermanentCount(playerB, "Brine Elemental", 1);
        Assert.assertTrue("Skip next turn has to be added to TurnMods", currentGame.getState().getTurnMods().size() == 1);
    }

    /**
     * Permanents that have been morphed have wrongly the converted mana cost of
     * their face up side, which makes cards such as Fatal Push of Smother
     * unable to destroy them if their cmc is greater than the one specified on
     * said cards. Face-down permanents should have a cmc of 0 as per rule
     * 707.2.
     */
    @Test
    public void testCMCofFaceDownCreature() {
        /*
         Pine Walker
         Creature - Elemental
         5/5
         Morph {4}{G} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
         Whenever Pine Walker or another creature you control is turned face up, untap that creature.
         */
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // Destroy target creature if it has converted mana cost 2 or less.
        // Revolt - Destroy that creature if it has converted mana cost 4 or less instead if a permanent you controlled left the battlefield this turn.
        addCard(Zone.HAND, playerB, "Fatal Push"); // Instant {B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Fatal Push");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Fatal Push", 1);
        assertGraveyardCount(playerA, "Pine Walker", 1);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);

    }

    /**
     * The Ur-Dragon reduces cost of face down morph Dragons Simple to reproduce
     * - Dragons with Morph/Megamorph such as Quicksilver Dragon cost {2} to
     * play face-down instead of the normal {3}. Other non-Dragon morph costs
     * are unchanged.
     */
    @Test
    public void testNoCostReductionOfFaceDownCastCreature() {
        /*
         Quicksilver Dragon {4}{U}{U}
         Creature - Dragon
         5/5
         Flying
         {U}: If target spell has only one target and that target is Quicksilver Dragon, change that spell's target to another creature.
         Morph {4}{U}
         */
        addCard(Zone.HAND, playerA, "Quicksilver Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Eminence - As long as The Ur-Dragon is in the command zone or on the battlefield, other Dragon spells you cast cost {1} less to cast.
        // Flying
        // Whenever one or more Dragons you control attack, draw that many cards, then you may put a permanent card from your hand onto the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "The Ur-Dragon", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Quicksilver Dragon");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertHandCount(playerA, 0);

        assertTappedCount("Island", true, 3);

    }

    /**
     * If you have Endless Whispers in play and a morph creature dies, it should
     * be returned to play face up at end of turn under the control of an
     * opponent.
     */
    @Test
    public void testMorphEndlessWhispers() {
        /*
         Quicksilver Dragon {4}{U}{U}
         Creature - Dragon
         5/5
         Flying
         {U}: If target spell has only one target and that target is Quicksilver Dragon, change that spell's target to another creature.
         Morph {4}{U}
         */
        addCard(Zone.HAND, playerA, "Quicksilver Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Each creature has "When this creature dies, choose target opponent. That player puts this card from its owner's graveyard
        // onto the battlefield under their control at the beginning of the next end step."
        addCard(Zone.BATTLEFIELD, playerA, "Endless Whispers", 1);

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Quicksilver Dragon");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", EmptyNames.FACE_DOWN_CREATURE.toString());

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Quicksilver Dragon", 0);

        assertPermanentCount(playerA, "Quicksilver Dragon", 0);
        assertPermanentCount(playerB, "Quicksilver Dragon", 1);

    }

    /**
     * A creature with Morph/Megamorph cast normally will properly include its
     * printed creature subtypes: for example Akroma, Angel of Fury is a
     * Legendary Creature - Angel. However, if Akroma is cast face down and then
     * turned face up via its morph ability, it has no subtypes: it's just a
     * Legendary Creature and creature type-specific effects (Radiant Destiny,
     * etc) don't apply to it. Haven't tested whether this also applies to
     * external effects turning the creature face up, like Skirk Alarmist.
     */
    @Test
    public void testSubTypesAfterTurningFaceUp() {
        /*
         Akroma, Angel of Fury {5}{R}{R}{R}
         Creature - Legendary Angel
         6/6
        // Akroma, Angel of Fury can't be countered.
        // Flying
        // Trample
        // protection from white and from blue
        // {R}: Akroma, Angel of Fury gets +1/+0 until end of turn.
        // Morph {3}{R}{R}{R}
         */
        addCard(Zone.HAND, playerA, "Akroma, Angel of Fury");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Fury");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{R}{R}{R}: Turn this face-down permanent face up.");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Akroma, Angel of Fury", 1);
        assertType("Akroma, Angel of Fury", CardType.CREATURE, SubType.ANGEL);
        Permanent akroma = getPermanent("Akroma, Angel of Fury");
        Assert.assertTrue("Akroma has to be red", akroma.getColor(currentGame).isRed());
    }

    @Test
    public void test_LandWithMorph_PlayLand() {
        // Morph {2}
        addCard(Zone.HAND, playerA, "Zoetic Cavern");

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Zoetic Cavern", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern");
        setChoice(playerA, false); // no morph (canPay for generic/colored mana returns true all the time, so xmage ask about face down cast)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Zoetic Cavern", 1);
    }

    @Test
    public void test_LandWithMorph_Morph() {
        // Morph {2}
        addCard(Zone.HAND, playerA, "Zoetic Cavern");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Zoetic Cavern", true);
        checkPlayableAbility("morph must be replaced by play ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morph", false);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern");
        setChoice(playerA, true); // morph

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Zoetic Cavern", 0);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    @Test
    public void test_LandWithMorph_MorphAfterLand() {
        removeAllCardsFromHand(playerA);

        // Morph {2}
        addCard(Zone.HAND, playerA, "Zoetic Cavern");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.HAND, playerA, "Island", 1);

        // play land first
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        // morph ability (play as face down) calls from playLand method, so it visible for play land command
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Zoetic Cavern", true);
        checkPlayableAbility("morph must be replaced by play ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morph", false);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern");
        setChoice(playerA, true); // morph

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Zoetic Cavern", 0);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    @Test
    public void test_LandWithMorph_MorphFromLibrary() {
        removeAllCardsFromLibrary(playerA);

        // You may play lands and cast spells from the top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Future Sight");
        //
        // Morph {2}
        addCard(Zone.LIBRARY, playerA, "Zoetic Cavern");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Zoetic Cavern", true);
        checkPlayableAbility("morph must be replaced by play ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morph", false);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern");
        setChoice(playerA, true); // morph

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Zoetic Cavern", 0);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    @Test
    public void test_CantActivateOnOpponentTurn() {
        // https://github.com/magefree/mage/issues/6698

        // Morph {1}{U} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
        // When Willbender is turned face up, change the target of target spell or ability with a single target.
        addCard(Zone.HAND, playerA, "Willbender");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // can play on own turn
        checkPlayableAbility("can", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", true);

        // can't play on opponent turn
        checkPlayableAbility("can't", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_MorphWithCostReductionMustBePlayable_NormalCondition() {
        // {1}{U} creature
        // Morph {1}{U} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
        // When Willbender is turned face up, change the target of target spell or ability with a single target.
        addCard(Zone.HAND, playerA, "Willbender");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Creature spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Nylea, Keen-Eyed");

        checkPlayableAbility("can", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender");
        setChoice(playerA, true); // morph

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    @Test
    public void test_MorphWithCostReductionMustBePlayable_MorphCondition1() {
        // {1}{U} creature
        // Morph {1}{U} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
        // When Willbender is turned face up, change the target of target spell or ability with a single target.
        addCard(Zone.HAND, playerA, "Willbender");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Face-down creature spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Chisel");

        checkPlayableAbility("can", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender");
        setChoice(playerA, true); // morph

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    @Test
    public void test_MorphWithCostReductionMustBePlayable_MorphCondition2() {
        // {1}{U} creature
        // Morph {1}{U} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
        // When Willbender is turned face up, change the target of target spell or ability with a single target.
        addCard(Zone.HAND, playerA, "Willbender", 2);
        //addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // The first face-down creature spell you cast each turn costs {3} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Kadena, Slinking Sorcerer");

        // creature one - get cost reduce
        checkPlayableAbility("can", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender");
        setChoice(playerA, true); // morph
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // creature two - do not get cost reduce
        checkPlayableAbility("can't by no reduce", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", false);

        // on next turn it can cost reduce again
        checkPlayableAbility("can't by not your turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", false);
        checkPlayableAbility("can", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Willbender", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }
}
