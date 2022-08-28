package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class UseAlternateSourceCostsTest extends CardTestPlayerBase {

    @Test
    public void DreamHallsCastColoredSpell() {
        setStrictChooseMode(true);
        
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);

        addCard(Zone.HAND, playerA, "Gray Ogre", 1); // Creature 3/1
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre"); // Cast Orgre by discarding the Lightning Bolt
        setChoice(playerA, true); // Pay alternative costs? (Discard a card that shares a color with that spell)
        setChoice(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //Gray Ogre is cast with the discard
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void DreamHallsCantCastColorlessSpell() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.HAND, playerA, "Juggernaut", 1); // Creature 5/3 - {4}
        addCard(Zone.HAND, playerA, "Haunted Plate Mail", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Juggernaut"); // Cast Juggernaut by discarding Haunted Plate Mail may not work

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Haunted Plate Mail", 0);
        assertTapped("Mountain", true);
        //Juggernaut is not cast by alternate casting costs
        assertPermanentCount(playerA, "Juggernaut", 1);
    }

    @Test
    public void DreamHallsCastWithFutureSight() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        // Play with the top card of your library revealed.
        // You may play the top card of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Future Sight", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.LIBRARY, playerA, "Gray Ogre", 1); // Creature 3/1
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre"); // Cast Orgre by discarding the Lightning Bolt

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Mountain", false);
        //Gray Ogre is cast with the discard
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }


    @Test
    public void test_Playable_WithMana() {
        // {1}{W}{W} instant
        // You may discard a Plains card rather than pay Abolish's mana cost.
        // Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerA, "Abolish");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Plains", 1); // discard cost
        //
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Myr");

        checkPlayableAbility("can", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Abolish", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Abolish", "Alpha Myr");
        setChoice(playerA, true); // use alternative cost
        setChoice(playerA, "Plains");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Alpha Myr", 1);
        assertTappedCount("Plains", false, 3); // must discard 1 instead tap
    }

    @Test
    public void test_Playable_WithoutMana() {
        // {1}{W}{W} instant
        // You may discard a Plains card rather than pay Abolish's mana cost.
        // Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerA, "Abolish");
        //addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Plains", 1); // discard cost
        //
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Myr");

        checkPlayableAbility("can", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Abolish", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Abolish", "Alpha Myr");
        setChoice(playerA, true); // use alternative cost
        setChoice(playerA, "Plains");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Alpha Myr", 1);
    }

    @Test
    public void test_Playable_WithoutManaAndCost() {
        // {1}{W}{W} instant
        // You may discard a Plains card rather than pay Abolish's mana cost.
        // Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerA, "Abolish");
        //addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //addCard(Zone.HAND, playerA, "Plains", 1); // discard cost
        //
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Myr");

        // can't see as playable (no mana for normal, no discard for alternative)
        checkPlayableAbility("can't", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Abolish", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_WithOpponentGainingLive() {
        // If you control a Forest, rather than pay Invigorate's mana cost, you may have an opponent gain 3 life.
        // Target creature gets +4/+4 until end of turn.        
        addCard(Zone.HAND, playerA, "Invigorate"); // Instant {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Invigorate", "Silvercoat Lion");
        setChoice(playerA, true); // use alternative cost
        addTarget(playerA, playerB); // Opponent to gain live
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Invigorate", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 6, 6);
        assertLife(playerB, 23);
    }
    
    @Test
    public void test_Not_Playable_WithOpponentGainingLive() {
        // If you control a Forest, rather than pay Invigorate's mana cost, you may have an opponent gain 3 life.
        // Target creature gets +4/+4 until end of turn.        
        addCard(Zone.GRAVEYARD, playerA, "Invigorate"); // Instant {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        
         // can't see as playable because in graveyard
        checkPlayableAbility("can't", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Invigorate", false);
        
        checkPlayableAbility("can't", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Invigorate", false);
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Invigorate", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertLife(playerB, 20);
    }
    
    @Test
    @Ignore // TODO: make test to check combo of alternative cost and cost reduction effects
    public void test_Playable_WithCostReduction() {
        addCard(Zone.HAND, playerA, "xxx");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
