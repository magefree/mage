package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alex-Vasile
 */
public class MultipleAsThoughEffects extends CardTestPlayerBase {

    /**
     * Reported bug: https://github.com/magefree/mage/issues/8584
     *
     * If there are multiple effects which allow a player to cast a spell,
     * they should be able to choose which one they whish to use.
     */
    @Test
    public void multipleNonConsumable() {
        // You may cast creature spells from the top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Vivien, Monsters' Advocate");
        // You may play lands and cast spells from the top of your library.
        // If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        // Random creature card to play with mana value of 3
        addCard(Zone.LIBRARY, playerA, "Abzan Beastmaster",3); // 1 for draw, 2 to play
        addCard(Zone.LIBRARY, playerA, "Forest",4);

        skipInitShuffling();
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Abzan Beastmaster");
        setChoice(playerA, "Vivien");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Abzan Beastmaster");
        setChoice(playerA, "Bolas's");

        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Abzan Beastmaster", 2);
        assertLife(playerA, 20 - 3); // 3 from casting Abzan Beastmaster with Bolas Citadel
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/2087
     *
     * If there are multiple effects which allow a player to cast a spell,
     * they should be able to choose which one they whish to use, even if one is single-use.
     */
    @Test
    public void ConsumableAndNonConsumable() {
        // You may cast Risen Executioner from your graveyard if you pay {1} more to cast it for each other creature card in your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Risen Executioner", 2);
        // During each of your turns, you may cast a Zombie creature spell from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Gisa and Geralf");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp",8);  // Only enough mana to cast

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Risen Executioner");
        setChoice(playerA, "Gisa");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Risen Executioner");

        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Risen Executioner", 2);
    }
}
