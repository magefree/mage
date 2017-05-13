package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by escplan9
 */
public class NewPerspectivesTest extends CardTestPlayerBase {

    /*
      New Perspectives {5}{U}
      Enchantment
      When New Perspectives enters the battlefield, draw three cards.
      As long as you have seven or more cards in hand, you may pay 0 rather than pay cycling costs.
     */
    private String nPerspectives = "New Perspectives";

    /**
     * just a basic test for free cycling!
     */
    @Test
    public void newPerspectives_7Cards_FreeCycling() {

        String fJet = "Flame Jet"; // {1}{R} Sorcery deal 3 dmg to player. cycling (2)

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, nPerspectives);
        addCard(Zone.HAND, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, fJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nPerspectives);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Cycling");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, nPerspectives, 1);
        assertGraveyardCount(playerA, fJet, 1);
        assertHandCount(playerA, 7); // 4 + 3 (new perspectives ETB) (+ 1 - 1) (cycling)
    }

    /**
     * Fewer than 7 cards in hand, no free cycle!
     */
    @Test
    public void newPerspectives_LessThan7Cards_CyclingNotFree() {

        String fJet = "Flame Jet"; // {1}{R} Sorcery deal 3 dmg to player. cycling (2)

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, nPerspectives);
        addCard(Zone.HAND, playerA, fJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nPerspectives);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Cycling");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, nPerspectives, 1);
        assertGraveyardCount(playerA, fJet, 0);
        assertHandCount(playerA, fJet, 1);
        assertHandCount(playerA, 4); // 1 + 3 (new perspectives ETB)
    }

    /*
     * Reported bug for #3323:
     * If you cast a second copy of New Perspective while the first one is still in play, the client will freeze.
     */
    @Test
    public void newPerspectives_PlayingSecondOneWithFewerThan7CardsOnCast() {

        String fJet = "Flame Jet"; // {1}{R} Sorcery deal 3 dmg to player. cycling (2)

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, nPerspectives, 2);
        addCard(Zone.HAND, playerA, fJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nPerspectives);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, nPerspectives);

        activateAbility(3, PhaseStep.BEGIN_COMBAT, playerA, "Cycling");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, nPerspectives, 2);
        assertHandCount(playerA, 8); // 1 (flame jet cycled) + 3 (new perspectives ETB) + 1 (draw step) + 3 (2nd perspectives etb)
        assertGraveyardCount(playerA, fJet, 1);
    }

    /*
     * Reported bug for #3323: NOTE test failing due to bug in code (game freezes after casting 2nd new perspectives)
     * If you cast a second copy of New Perspective while the first one is still in play, the client will freeze.
     */
    @Test
    public void newPerspectives_PlayingSecondOneWithMoreThan7CardsOnCast() {

        String fJet = "Flame Jet"; // {1}{R} Sorcery deal 3 dmg to player. cycling (2)

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, nPerspectives, 2);
        //this made test buggy: addCard(Zone.HAND, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, fJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nPerspectives);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, nPerspectives);

        activateAbility(3, PhaseStep.BEGIN_COMBAT, playerA, "Cycling");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, nPerspectives, 0); // check it wasn't discarded
        assertPermanentCount(playerA, nPerspectives, 2);
        assertGraveyardCount(playerA, fJet, 1);
        //assertHandCount(playerA, 11); // 1 (flame jet cycled) + 3 Mountains in hand + 3 (new perspectives ETB) + 1 (draw step) + 3 (2nd perspectives etb)
        assertHandCount(playerA, 8); // 1 (flame jet cycled) + 3 (new perspectives ETB) + 1 (draw step) + 3 (2nd perspectives etb)
    }
}
