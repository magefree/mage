package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ProgenitorMimicTest extends CardTestPlayerBase {

    /**
     * Tests triggers working on both sides after Clone coming onto battlefield
     */
    @Test
    public void testCloneTriggered() {
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.HAND, playerB, "Progenitor Mimic");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Progenitor Mimic");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Runeclaw Bear", 1);
        assertPermanentCount(playerB, "Runeclaw Bear", 2);

        int tokens = 0;
        int nonTokens = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(playerB.getId())) {
                if (permanent.isCreature(currentGame)) {
                    if (permanent instanceof PermanentToken) {
                        tokens++;
                    } else {
                        nonTokens++;
                    }

                }
            }
        }

        Assert.assertEquals("Only one non token permanent ", 1, nonTokens);
        Assert.assertEquals("Only one token permanent ", 1, tokens);
    }

    /**
     * If you have Progenitor Mimic copy a creature it gets all of the abilities
     * plus "At the beginning of upkeep if this creature isn't a token, put a
     * token that's a copy of this creature". Up to this point everything works
     * correctly.
     *
     * If you then summon another mimic and have it be a copy of the first mimic
     * it should have "At the beginning of upkeep if this creature isn't a
     * token, put a token that's a copy of this creature" two times. The second
     * mimic would then make two copies and the first mimic would make one copy
     * every turn. Right now the second mimc only makes one copy per turn.
     *
     * 706.9a Some copy effects cause the copy to gain an ability as part of the
     * copying process. This ability becomes part of the copiable values for the
     * copy, along with any other abilities that were copied. Example: Quirion
     * Elves enters the battlefield and an Unstable Shapeshifter copies it. The
     * copiable values of the Shapeshifter now match those of the Elves, except
     * that the Shapeshifter also has the ability “Whenever a creature enters
     * the battlefield, Unstable Shapeshifter becomes a copy of that creature
     * and gains this ability.” Then a Clone enters the battlefield as a copy of
     * the Unstable Shapeshifter. The Clone copies the new copiable values of
     * the Shapeshifter, including the ability that the Shapeshifter gave itself
     * when it copied the Elves.
     *
     */
    @Test
    public void testTwoMimic() {
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Return target permanent you control to its owner's hand. You gain 4 life.
        addCard(Zone.HAND, playerA, "Narrow Escape");

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield except
        // it gains "At the beginning of your upkeep, if this creature isn't a token, put a token onto the battlefield
        // that's a copy of this creature."
        addCard(Zone.HAND, playerB, "Progenitor Mimic", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Progenitor Mimic");
        setChoice(playerB, "Runeclaw Bear");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Narrow Escape", "Runeclaw Bear");

        // Begin of upkeep 1 token added
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Progenitor Mimic");
        setChoice(playerB, "Runeclaw Bear");

        // Begin of upkeep 3 tokens added
        setStopAt(6, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Narrow Escape", 1);
        assertPermanentCount(playerA, "Runeclaw Bear", 0);
        assertHandCount(playerA, "Runeclaw Bear", 1);

        assertPermanentCount(playerB, "Runeclaw Bear", 6);

        int tokens = 0;
        int nonTokens = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(playerB.getId())) {
                if (permanent.isCreature(currentGame)) {
                    if (permanent instanceof PermanentToken) {
                        tokens++;
                    } else {
                        nonTokens++;
                    }

                }
            }
        }

        Assert.assertEquals("Two non token permanents ", 2, nonTokens);
        Assert.assertEquals("Four token permanents", 4, tokens);
    }

    /**
     * In a Commander FFA game, I controlled 5 vampires (one of which was
     * Captivating Vampire). My opponent cast Progenitor Mimic, copying
     * Captivating Vampire. I used the ability of my Captivating Vampire to gain
     * control of their Mimic/Vampire but the buff didn't switch control. Their
     * other vampire still got the buff even after I gained control of the
     * Mimic/Vampire.
     *
     * Did not get to see if the Mimic/Vampire produced tokens on the right side
     * of the field (my side) as the game ended just after my turn.
     */
    @Test
    public void testChangeControl() {
        // Other Vampire creatures you control get +1/+1.
        // Tap five untapped Vampires you control: Gain control of target creature. It becomes a Vampire in addition to its other types.
        addCard(Zone.BATTLEFIELD, playerA, "Captivating Vampire", 1); // 2/2
        // Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Child of Night", 4); // 2/1

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield except
        // it gains "At the beginning of your upkeep, if this creature isn't a token, put a token onto the battlefield
        // that's a copy of this creature."
        addCard(Zone.HAND, playerB, "Progenitor Mimic", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Bloodrage Vampire", 1); // 3/1

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Progenitor Mimic");
        setChoice(playerB, "Captivating Vampire");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tap five untapped Vampire", "Captivating Vampire[only copy]");
        setChoice(playerA, "Captivating Vampire");
        setChoice(playerA, "Child of Night");
        setChoice(playerA, "Child of Night");
        setChoice(playerA, "Child of Night");
        setChoice(playerA, "Child of Night");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Captivating Vampire", 2);

        assertPowerToughness(playerB, "Bloodrage Vampire", 3, 1); // +0 because all Captivating Vampire are controlled by playerB

        assertPowerToughness(playerA, "Captivating Vampire", 3, 3, Filter.ComparisonScope.All); // +1 from the other Captivating Vampire
        assertPowerToughness(playerA, "Child of Night", 4, 3, Filter.ComparisonScope.All); // +2 from the two Captivating Vampire

    }

    /**
     * Deadbridge Chant returns the battlefield Progenitor Mimic, but it's copy
     * effect doesn't applied. It's 0/0, game put it into graveyard.
     */
    @Test
    public void testDeadbridgeChant() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // When Deadbridge Chant enters the battlefield, put the top ten cards of your library into your graveyard.
        // At the beginning of your upkeep, choose a card at random in your graveyard. If it's a creature card, put it onto the battlefield. Otherwise, put it into your hand.
        addCard(Zone.HAND, playerA, "Deadbridge Chant", 1); // {4}{B}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield except
        // it gains "At the beginning of your upkeep, if this creature isn't a token, put a token onto the battlefield
        // that's a copy of this creature."
        addCard(Zone.LIBRARY, playerA, "Progenitor Mimic", 10);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deadbridge Chant");
        setChoice(playerA, "Silvercoat Lion"); // Copied by Progenitor Mimic returned by Deadbridge Chant on upkeep of turn 3

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Deadbridge Chant", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);

        assertGraveyardCount(playerA, "Progenitor Mimic", 9);
    }
}
