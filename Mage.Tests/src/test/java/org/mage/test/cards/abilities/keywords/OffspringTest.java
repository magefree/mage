package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class OffspringTest extends CardTestPlayerBase {

    private static final String vinelasher = "Iridescent Vinelasher";

    private Permanent getCreature(String name, boolean isToken) {
        for (Permanent permanent : currentGame.getBattlefield().getActivePermanents(playerA.getId(), currentGame)) {
            if (name.equals(permanent.getName()) && (permanent instanceof PermanentToken) == isToken) {
                return permanent;
            }
        }
        return null;
    }

    private void checkOffspring(String name, int power, int toughness, boolean paid) {
        assertPermanentCount(playerA, name, paid ? 2 : 1);
        assertTokenCount(playerA, name, paid ? 1 : 0);

        Permanent original = getCreature(name, false);
        Assert.assertEquals(
                "Original creature should have power " + power,
                power, original.getPower().getValue()
        );
        Assert.assertEquals(
                "Original creature should have toughness " + toughness,
                toughness, original.getToughness().getValue()
        );
        if (!paid) {
            return;
        }
        Permanent token = getCreature(name, true);
        Assert.assertEquals(
                "Token creature should have power 1",
                1, token.getPower().getValue()
        );
        Assert.assertEquals(
                "Token creature should have toughness 1",
                1, token.getToughness().getValue()
        );
    }

    @Test
    public void testNoPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, vinelasher);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        checkOffspring(vinelasher, 1, 2, false);
    }

    @Test
    public void testPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, vinelasher);

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        checkOffspring(vinelasher, 1, 2, true);
    }

    @Test
    public void testPrintedOffspringCopiedByDoubleMajor() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, vinelasher);
        addCard(Zone.HAND, playerA, "Double Major");

        setChoice(playerA, true); // Pay printed offspring {2}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", vinelasher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // 1 original card + 1 token from Double Major spell copy + 2 offspring tokens (one from each)
        assertPermanentCount(playerA, vinelasher, 4);
        assertTokenCount(playerA, vinelasher, 3);
    }

    @Test
    public void testCopiedOffspringTriggerStrionicResonator() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Strionic Resonator");
        addCard(Zone.HAND, playerA, vinelasher);

        setChoice(playerA, true); // Pay printed offspring {2}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        // Vinelasher resolves, its offspring trigger goes on the stack.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.");

        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // 1 original card + 2 offspring tokens (one from original trigger, one from copied trigger)
        assertPermanentCount(playerA, vinelasher, 3);
        assertTokenCount(playerA, vinelasher, 2);
    }
}
