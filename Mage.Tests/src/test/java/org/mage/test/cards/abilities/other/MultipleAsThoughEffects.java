package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alex-Vasile, Susucr
 */
public class MultipleAsThoughEffects extends CardTestPlayerBase {

    /**
     * Reported bug: https://github.com/magefree/mage/issues/8584
     *
     * If there are multiple effects which allow a player to cast a spell,
     * they should be able to choose which one they whish to use.
     */
    @Test
    public void ChoosingAlternateCastingMethod() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // You may cast creature spells from the top of your library.
        addCard(Zone.HAND, playerA, "Vivien, Monsters' Advocate");
        // You may play lands and cast spells from the top of your library.
        // If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        // Random creature card to play with mana value of 3
        addCard(Zone.LIBRARY, playerA, "Abzan Beastmaster",2);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears",1); // This one is drawn.

        addCard(Zone.BATTLEFIELD, playerA, "Forest",5);
        // For the "cast from the top" abilities to work, Vivien or Bolas's Citadel
        // must be played and not be in battlefield as start. Or else the top of the library will
        // not be able to be cast during the test.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vivien, Monsters' Advocate");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Abzan Beastmaster",true);
        setChoice(playerA, "Vivien");
        checkLife("Vivien not making pay life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Abzan Beastmaster");
        setChoice(playerA, "Bolas's");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Abzan Beastmaster", 2);
        assertTappedCount("Forest", true, 3);
        assertLife(playerA, 20 - 3); // 3 from casting Abzan Beastmaster with Bolas Citadel
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/2087
     *
     * If there are multiple effects which allow a player to cast a spell,
     * they should be able to choose which one they whish to use, even if one is single-use.
     */
    @Test
    public void RisenExecutioner() {
        setStrictChooseMode(true);

        // You may cast Risen Executioner from your graveyard if you pay {1} more to cast it for each other creature card in your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Risen Executioner", 2);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        // During each of your turns, you may cast a Zombie creature spell from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Gisa and Geralf");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp",9);  // Only enough mana to cast

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Risen Executioner", true); // Should cost {2}{B}{B} since cast with Gisa
        setChoice(playerA, "Gisa");
        checkPermanentTapped("Swamp tapped after cast with Gisa and Geralf", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", true, 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Risen Executioner"); // Should cost {3}{B}{B} when cast with own ability, there is another creature in the graveyard

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Risen Executioner", 2);
        assertTappedCount("Swamp", true, 4 + 5);
    }
}