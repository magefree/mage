package org.mage.test.cards.single.mh2;

import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class GristTheHungerTideTest extends CardTestPlayerBase {

    private static final String grist = "Grist, the Hunger Tide";
    private static final String imp = "Putrid Imp";
    private static final String leyline = "Leyline of the Void";
    private static final String bounty = "Primeval Bounty";

    @Test
    public void testGristInHandBattlefieldGraveLibrary() {
        addCard(Zone.HAND, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, grist);
        addCard(Zone.GRAVEYARD, playerA, grist);
        addCard(Zone.LIBRARY, playerA, grist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        for (Card card : currentGame.getCards()) {
            if (!card.getName().equals(grist)) {
                continue;
            }
            Zone zone = currentGame.getState().getZone(card.getId());
            if (zone == Zone.BATTLEFIELD) {
                Assertions.assertFalse(card.isCreature(currentGame), "Not a creature on the battlefield");
            } else {
                Assertions.assertTrue(card.isCreature(currentGame), "Should be a creature when zone is " + zone);
            }
        }
    }

    @Test
    public void testGristInExile() {
        addCard(Zone.HAND, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.BATTLEFIELD, playerB, leyline);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, grist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        for (Card card : currentGame.getCards()) {
            if (!card.getName().equals(grist)) {
                continue;
            }
            Assertions.assertEquals(Zone.EXILED, currentGame.getState().getZone(card.getId()));
            Assertions.assertTrue(card.isCreature(currentGame), "Should be a creature in exile");
        }
    }

    @Test
    public void testGristFromStackToBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3);
        addCard(Zone.BATTLEFIELD, playerA, bounty);
        addCard(Zone.HAND, playerA, grist);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Grist is a creature spell when cast and triggers bounty
        assertPermanentCount(playerA, "Beast Token", 1);
        // But not a creature on the battlefield
        assertPermanentCount(playerA, grist, 1);
        assertType(grist, CardType.CREATURE, false);
    }
}
