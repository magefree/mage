package org.mage.test.cards.abilities.oneshot;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;

public class KaghaShadowArchdruidAbilityTest extends CardTestPlayerBase {
    
    // 1. Deathtouch and mill attack trigger
    @Test
    public void testKaghaAttackTrigger() {
        // initialize library, hand and battlefield
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Swamp",7);
        addCard(Zone.LIBRARY, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Kagha, Shadow Archdruid", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        attack(3, playerA, "Kagha, Shadow Archdruid");
        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, "Forest", 2);
        assertAbility(playerA, "Kagha, Shadow Archdruid", DeathtouchAbility.getInstance(), true);
    }

    // 2. Test if possible to play a land or cast a permanent spell
    @Test
    public void testKaghaCheckSpecialEffect() {

        // initialize library, hand and battlefield
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Swamp",6);
        addCard(Zone.HAND, playerA, "Mulch",1);
        
        addCard(Zone.LIBRARY, playerA, "Forest", 1);
        addCard(Zone.LIBRARY, playerA, "Sol Ring", 1);
        addCard(Zone.LIBRARY, playerA, "Grapple with the Past", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1); // will be drawn - new turn
        addCard(Zone.LIBRARY, playerA, "Kagha, Shadow Archdruid", 4); // will be milled via Mulch
        skipInitShuffling(); 

        addCard(Zone.BATTLEFIELD, playerA, "Kagha, Shadow Archdruid", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // test previously milled cards are not taken into account - 4 cards are milled
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mulch");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // attack and trigger milling 2 cards via Kagha
        attack(3, playerA, "Kagha, Shadow Archdruid");

        // milled card from turn one (4x Kagha) cannot be cast anymore
        checkPlayableAbility("before", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Kagha, Shadow Archdruid", false);
        // Grapple from the Past is not a permanent card
        checkPlayableAbility("before", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grapple with the Past", false);
        // Sol Ring can be cast
        checkPlayableAbility("before", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Sol Ring", true);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 7);
        assertGraveyardCount(playerA, "Kagha, Shadow Archdruid", 4);
        assertGraveyardCount(playerA, "Grapple with the Past", 1);
        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Mulch", 1);
    }


    // 3. check if only one permanent can be cast/played
    @Test
    public void testKaghaCheckSpecialEffectOnce() {

        // initialize library, hand and battlefield
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Swamp",6);
        addCard(Zone.HAND, playerA, "Mulch",1);
        
        addCard(Zone.LIBRARY, playerA, "Sol Ring", 2);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1); // will be drawn - new turn
        skipInitShuffling(); 

        addCard(Zone.BATTLEFIELD, playerA, "Kagha, Shadow Archdruid", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        attack(3, playerA, "Kagha, Shadow Archdruid");

        // only one spell can be cast from graveyard
        checkPlayableAbility("check cast Sol Ring", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Sol Ring", true);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sol Ring");
        // another one should not be possible
        checkPlayableAbility("check cast Sol Ring", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Sol Ring", false);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertAbility(playerA, "Kagha, Shadow Archdruid", DeathtouchAbility.getInstance(), true);
    }

    // 4. Other mill sources should work as well
    @Test
    public void testKaghaCheckSpecialEffectOtherSources() {

        // initialize library, hand and battlefield
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Swamp",6);
        addCard(Zone.HAND, playerA, "Mulch",1);
        
        addCard(Zone.LIBRARY, playerA, "Forest", 1);
        addCard(Zone.LIBRARY, playerA, "Sol Ring", 1);
        addCard(Zone.LIBRARY, playerA, "Grapple with the Past", 1);
        addCard(Zone.LIBRARY, playerA, "Kagha, Shadow Archdruid", 4); // will be milled via Mulch
        addCard(Zone.LIBRARY, playerA, "Mountain", 1); // will be drawn - new turn
        skipInitShuffling(); 

        addCard(Zone.BATTLEFIELD, playerA, "Kagha, Shadow Archdruid", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // mill other cards this turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mulch");

        // attack and trigger milling 2 cards via Kagha
        attack(3, playerA, "Kagha, Shadow Archdruid");

        // Kagha(s) can be cast
        checkPlayableAbility("before", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Kagha, Shadow Archdruid", true);
        // Grapple from the Past is not a permanent card
        checkPlayableAbility("before", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grapple with the Past", false);
        // Sol Ring can be cast
        checkPlayableAbility("before", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Sol Ring", true);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 7);
        assertGraveyardCount(playerA, "Kagha, Shadow Archdruid", 4);
        assertGraveyardCount(playerA, "Grapple with the Past", 1);
        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Mulch", 1);
    }
}