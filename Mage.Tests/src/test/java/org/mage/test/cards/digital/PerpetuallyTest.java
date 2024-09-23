package org.mage.test.cards.digital;

import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

public class PerpetuallyTest extends CardTestCommander3PlayersFFA {

    private static final String familiar = "Plaguecrafter's Familiar";
    private static final String defenses = "Baffling Defenses";
    private static final String withering = "Davriel's Withering";


    private static final String simpleCard = "Goblin Striker";

    /**
     * Perpetually effects should be applied in any zone:
     * 1. gaining 2 singleton abilities (should stay one)
     * 2. changing PT boosted value
     * 3. setting base power value
     */
    @Test
    public void testGainSingletonAbility() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.HAND, playerA, familiar, 2);
        addCard(Zone.HAND, playerA, simpleCard);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, simpleCard);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);

        attack(1, playerA, simpleCard, playerB);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, simpleCard, DeathtouchAbility.class,  1);

    }

    /**
     * Tests perpetually gaining multiple abilities to the card in hand
     */
    // TODO: доделать тест!
    private static final String grasp = "Ethereal Grasp";

    @Test
    public void testGainMultipleAbilities() {

/*        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.HAND, playerA, grasp, 2);
        addCard(Zone.HAND, playerA, simpleCard);
        addCard(Zone.HAND, playerA, "Murder");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grasp, simpleCard);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grasp, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", simpleCard);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, simpleCard, DeathtouchAbility.class,  1);*/
        
    }
}
