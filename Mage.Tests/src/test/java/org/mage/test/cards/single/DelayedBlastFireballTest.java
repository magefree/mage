package org.mage.test.cards.single;

import mage.cards.d.DelayedBlastFireball;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 * Tests for {@link DelayedBlastFireball}
 * <ul>
 *     <li>Bug test: Copy of spell <a href="https://github.com/magefree/mage/issues/9180">#9180</a></li>
 *     <li>MTG Rules edge case: 707.12. An effect that instructs a player to cast a copy of an object (and not just copy a spell) follows the
 *     rules for casting spells, except that the copy is created in the same zone the object is in and then cast
 *     while another spell or ability is resolving. Casting a copy of an object follows steps 601.2a-h of rule 601,
 *     "Casting Spells," and then the copy becomes cast. Once cast, the copy is a spell on the stack,
 *     and just like any other spell it can resolve or be countered.</li>
 * </ul>
 *
 * @author the-red-lily
 */
public class DelayedBlastFireballTest extends CardTestPlayerBase {

    // Delayed Blast Fireball deals 2 damage to each opponent and each creature they control.
    // If this spell was cast from exile, it deals 5 damage to each opponent and each creature they control instead.
    // Foretell {4}{R}{R} (During your turn, you may pay {2} and exile this card from your hand face down.
    //      Cast it on a later turn for its foretell cost.)
    @Test
    public void testForetoldDelayedBlastFireball() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Delayed Blast Fireball", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 6); //TODO

        // Foretell (pay {2} and exile this card from your hand face down. Cast it on a later turn for its foretell cost.)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");

        // Opponent's turn
        showAvailableAbilities("abilities", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Foretell {4}{R}{R} Cast it on a later turn for its foretell cost.
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        //activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Delayed Blast Fireball");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.DRAW);
        assertLife(playerB, 20 - 5);
        //todo check creatures
    }

    // Test for bug https://github.com/magefree/mage/issues/9180
    @Test
    public void testCopySpell() {
        removeAllCardsFromLibrary(playerA);
//            addCard(Zone.LIBRARY, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Delayed Blast Fireball", 1);
        addCard(Zone.HAND, playerA, "Fork", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 6); //TODO

        //Cast normally
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Delayed Blast Fireball");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fork", "Delayed Blast Fireball", "Cast Delayed Blast Fireball"); //Copy Delayed Blast Fireball
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN); //Wait for Fork to copy and for copy to resolve

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.DRAW);
        assertLife(playerB, 20 - 2 - 2);
    }

    // 707.12. An effect that instructs a player to cast a copy of an object (and not just copy a spell) follows the
    // rules for casting spells, except that the copy is created in the same zone the object is in and then cast
    // while another spell or ability is resolving. Casting a copy of an object follows steps 601.2a-h of rule 601,
    // "Casting Spells," and then the copy becomes cast. Once cast, the copy is a spell on the stack,
    // and just like any other spell it can resolve or be countered.
    //
    // Copy CARD not copy SPELL (copied cards are cast)
    // TL;DR Copy was cast from exile, so we should see 5 damage not 2
    @Test
    public void testCastExiledCopyCard() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.GRAVEYARD, playerA, "Delayed Blast Fireball");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Demilich");

        //Copied CARD counts as first Approach cast
        attack(1, playerA, "Demilich");
        //Whenever Demilich attacks, exile up to one target instant or sorcery card from your graveyard. Copy it. You may cast the copy.
        addTarget(playerA, "Delayed Blast Fireball"); //exile up to one target instant or sorcery card from your graveyard. Copy it.
        setChoice(playerA, "Yes"); //You may cast the copy.
        //Before Demilich combat damage
        checkLife("Copied Card Delayed Blast cast from exile deals 5 damage", 1, PhaseStep.DECLARE_BLOCKERS, playerB, 15);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20);
    }

    // Card was cast from exile, so we should see 5 damage not 2
    @Test
    public void testNonForetoldCastExiledCard() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Delayed Blast Fireball");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Jace's Mindseeker");

        // When Jace's Mindseeker enters the battlefield, target opponent mills five cards. You may cast an instant or sorcery spell from among them without paying its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Mindseeker");
        addTarget(playerA, playerB); //target opponent mills five cards
        // Message: Cast spell without paying its mana cost (Delayed Blast Fireball [c38])?
        setChoice(playerA, "Yes"); //You may cast an instant or sorcery spell from among them without paying its mana cost.
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        //checkLife("Non Foretold Delayed Blast cast from exile deals 5 damage", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 15);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20);
    }
}

