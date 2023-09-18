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
    private final String newPerspectives = "New Perspectives";
    private final String reliquaryTower = "Reliquary Tower"; // only used to not have to discard due to hand size to make testing easier
    private final String flameJet = "Flame Jet"; // just a cycle card with (2) cycling

    /**
     * just a basic test for free cycling!
     */
    @Test
    public void newPerspectives_7Cards_FreeCycling() {

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, reliquaryTower);
        addCard(Zone.HAND, playerA, newPerspectives);
        addCard(Zone.HAND, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, flameJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, newPerspectives);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Cycling");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, newPerspectives, 1);
        assertGraveyardCount(playerA, flameJet, 1);
        assertHandCount(playerA, 7); // 4 + 3 (new perspectives ETB) (+ 1 - 1) (cycling)
    }

    /**
     * Fewer than 7 cards in hand, no free cycle!
     */
    @Test
    public void newPerspectives_LessThan7Cards_CyclingNotFree() {

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, reliquaryTower);
        addCard(Zone.HAND, playerA, newPerspectives);
        addCard(Zone.HAND, playerA, flameJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, newPerspectives);
        checkPlayableAbility("Can't cycle", 1, PhaseStep.BEGIN_COMBAT, playerA, "Cycling", false);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, newPerspectives, 1);
        assertGraveyardCount(playerA, flameJet, 0);
        assertHandCount(playerA, flameJet, 1);
        assertHandCount(playerA, 4); // 1 + 3 (new perspectives ETB)
    }

    /*
     * Reported bug for #3323:
     * If you cast a second copy of New Perspective while the first one is still in play, the client will freeze.
     */
    @Test
    public void newPerspectives_PlayingSecondOneWithFewerThan7CardsOnCast() {

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, reliquaryTower);
        addCard(Zone.HAND, playerA, newPerspectives, 2);
        addCard(Zone.HAND, playerA, flameJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, newPerspectives);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, newPerspectives);

        activateAbility(3, PhaseStep.BEGIN_COMBAT, playerA, "Cycling");
        setChoice(playerA, true);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, newPerspectives, 2);
        assertHandCount(playerA, 8); // 1 (flame jet cycled) + 3 (new perspectives ETB) + 1 (draw step) + 3 (2nd perspectives etb)
        assertGraveyardCount(playerA, flameJet, 1);
    }

    /*
     * Reported bug for #3323:
     * If you cast a second copy of New Perspective while the first one is still in play, the client will freeze.
     * Unable to reproduce by Unit Test, only through manual test.
     */
    @Test
    public void newPerspectives_PlayingSecondOneWithMoreThan7CardsOnCast() {

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, reliquaryTower);
        addCard(Zone.HAND, playerA, newPerspectives, 2);
        addCard(Zone.HAND, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, flameJet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, newPerspectives);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, newPerspectives);

        activateAbility(3, PhaseStep.BEGIN_COMBAT, playerA, "Cycling");
        setChoice(playerA, true);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, newPerspectives, 0); // check it wasn't discarded
        assertPermanentCount(playerA, newPerspectives, 2);
        assertGraveyardCount(playerA, flameJet, 1);
        assertHandCount(playerA, 11); // 1 (flame jet cycled) + 3 Mountains in hand + 3 (new perspectives ETB) + 1 (draw step) + 3 (2nd perspectives etb)
    }
}
