package org.mage.test.cards.copy;

import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectsList;
import mage.cards.Card;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.Permanent;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class CloneTest extends CardTestPlayerBase {

    /**
     * Tests triggers working on both sides after Clone coming onto battlefield
     */
    @Test
    public void testCloneTriggered() {
        addCard(Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 19);

        // 1 from draw steps + 2 from Demon
        assertHandCount(playerA, 3);
        // 2 from draw steps + 1 from Demon
        assertHandCount(playerB, 3);

        assertPermanentCount(playerA, "Bloodgift Demon", 1);
        assertPermanentCount(playerB, "Bloodgift Demon", 1);
    }

    /**
     * Tests Clone is sacrificed and only one effect is turned on
     */
    @Test
    public void testCloneSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Zone.HAND, playerA, "Diabolic Edict");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Diabolic Edict", playerB);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bloodgift Demon", 1);
        assertGraveyardCount(playerA, "Diabolic Edict", 1);
        assertPermanentCount(playerB, "Bloodgift Demon", 0);
        assertGraveyardCount(playerB, "Clone", 1);

        // 1 from draw steps + 2 from Demon
        assertHandCount(playerA, 3);
        // 2 from draw steps + no from Demon (should be sacrificed)
        assertHandCount(playerB, 2);

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    @Test
    public void testCard3() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.HAND, playerA, "Clone");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Public Execution", "Llanowar Elves");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clone");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Llanowar Elves", 0);
        assertPowerToughness(playerB, "Craw Wurm", 4, 4);
        assertPowerToughness(playerA, "Craw Wurm", 6, 4);
    }

    // copy Nightmare test, check that the P/T setting effect ends
    // if the clone leaves battlefield
    @Test
    public void testCopyNightmare() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        // Target creature you control gets +1/+1 and gains hexproof until end of turn. (It can't be the target of spells or abilities your opponents control.)
        addCard(Zone.HAND, playerB, "Ranger's Guile");

        addCard(Zone.HAND, playerA, "Clone");
        // Return target nonland permanent to its owner's hand.
        addCard(Zone.HAND, playerA, "Disperse");

        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Nightmare");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Ranger's Guile", "Nightmare");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disperse", "Nightmare");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Nightmare", 1);
        assertPowerToughness(playerB, "Nightmare", 6, 6);
        assertPermanentCount(playerA, "Nightmare", 0);
        assertHandCount(playerA, "Disperse", 0);

        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate("Clone"));
        Card card = playerA.getHand().getCards(filter, currentGame).iterator().next();
        if (card != null) {
            Assert.assertEquals("Power has to be 0 because copy from nightmare P/T ability may no longer be applied", 0, card.getPower().getValue());
        }

        Logger.getLogger(CloneTest.class).debug("EXISTING CONTINUOUS EFFECTS:");
        for (ContinuousEffectsList effectsList : currentGame.getContinuousEffects().allEffectsLists) {
            for (Object anEffectsList : effectsList) {
                ContinuousEffect effect = (ContinuousEffect) anEffectsList;
                Logger.getLogger(CloneTest.class).debug("- " + effect.toString());
            }
        }
    }

    /**
     * When I Clone a creature, and I try to use Vesuvan Doppelganger to my
     * cloned creature, The Vesuvan disappears and the creature is not created.
     */
    @Test
    public void testCloneAndVesuvanDoppelganger() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone"); // Creature {3}{U}
        // You may have Vesuvan Doppelganger enter the battlefield as a copy of any creature on the battlefield
        // except it doesn't copy that creature's color and it gains "At the beginning of your upkeep,
        // you may have this creature become a copy of target creature except it doesn't copy that creature's color.
        // If you do, this creature gains this ability."
        addCard(Zone.HAND, playerA, "Vesuvan Doppelganger"); // Creature  {3}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        // {2}, {T} , Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Phyrexian Vault", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Silvercoat Lion");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Vesuvan Doppelganger");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Vesuvan Doppelganger", 0);

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

        assertHandCount(playerB, 2);

        boolean whiteLion = false;
        boolean blueLion = false;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerA.getId(), currentGame)) {
            if (permanent.getColor(currentGame).isWhite()) {
                whiteLion = true;
            }
            if (permanent.getColor(currentGame).isBlue()) {
                blueLion = true;
            }
        }

        Assert.assertTrue("There should be a white and a blue Silvercoat Lion be on the battlefield", blueLion && whiteLion);
    }

    @Test
    public void testAdaptiveAutomaton() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Adaptive Automaton");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Clone");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adaptive Automaton");
        setChoice(playerA, "Elf");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true);
        setChoice(playerB, "Adaptive Automaton");
        setChoice(playerB, "Goblin");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Adaptive Automaton", 1);
        Permanent original = getPermanent("Adaptive Automaton", playerA);
        Assert.assertTrue("The original Adaptive Automaton should be an Elf", original.hasSubtype(SubType.ELF, currentGame));

        assertPermanentCount(playerB, "Adaptive Automaton", 1);
        Permanent clone = getPermanent("Adaptive Automaton", playerB);
        Assert.assertFalse("The cloned Adaptive Automaton should not be as Elf", clone.hasSubtype(SubType.ELF, currentGame));
        Assert.assertTrue("The cloned Adaptive Automaton should be a Goblin", clone.hasSubtype(SubType.GOBLIN, currentGame));
    }

    /**
     * Cloning a face-down creature should produce a plain 2/2 creature #3582
     *
     * What I expected: Using a clone effect on a face-down creature should
     * create another 2/2 creature.
     *
     * What I observed: The resulting token appears face-up as a copy of the
     * face-down creature's front face, copying its power/toughness but not
     * abilities.
     *
     * To reproduce: Play a Terastodon, then an Ixidron. Terastodon is turned
     * face-down. Then play a clone creature (such as Progenitor Mimic). It
     * appears as a face-up 9/9 Terastodon token, but without any ETB triggers.
     * The next turn, when it copies itself, it creates a Terastodon that does
     * have its ETB ability.
     *
     * https://blogs.magicjudges.org/articles/2014/09/16/morph-rules-problems-face-down-in-a-face-up-world/
     * explains that cloning a face-down 2/2 creature should create another 2/2
     * creature with no abilities (see Face-down Creatures and Clone).
     */

    @Test
    public void testCloningFaceDownCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // As Ixidron enters the battlefield, turn all other nontoken creatures face down.
        // Ixidron's power and toughness are each equal to the number of face-down creatures on the battlefield.        
        addCard(Zone.HAND, playerA, "Ixidron"); // Creature {3}{U}{U}

        // When Terastodon enters the battlefield, you may destroy up to three target noncreature permanents. 
        // For each permanent put into a graveyard this way, its controller creates a 3/3 green Elephant creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Terastodon", 1); // Creature 9/9  {6}{G}{G}

        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.        
        addCard(Zone.HAND, playerA, "Clone"); // Creature {3}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, EmptyNames.FACE_DOWN_CREATURE.toString());

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();


        assertPermanentCount(playerA, "Terastodon", 0);
        
        assertPermanentCount(playerA, "Ixidron", 1);
        assertPowerToughness(playerA, "Ixidron", 1, 1);

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2, Filter.ComparisonScope.All);
    }
    
}
