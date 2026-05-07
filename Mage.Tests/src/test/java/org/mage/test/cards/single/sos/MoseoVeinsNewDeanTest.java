package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MoseoVeinsNewDeanTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MoseoVeinsNewDean Moseo, Vein's new Dean} {2}{B}
     * Legendary Creature — Bird Skeleton Warlock
     * Flying
     * When Moseo enters, create a 1/1 black and green Pest creature token with “Whenever this token attacks, you gain 1 life.”
     * Infusion — At the beginning of your end step, if you gained life this turn, return up to one target creature card with mana value X or less from your graveyard to the battlefield, where X is the amount of life you gained this turn.
     * 2/1
     */
    private static final String moseo = "Moseo, Vein's New Dean";

    @Test
    public void test_NoTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, moseo, 1);
        addCard(Zone.GRAVEYARD, playerA, "Memnite", 1);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, 1);
    }

    @Test
    public void test_Gain5_NoValidTarget() {
        addCard(Zone.BATTLEFIELD, playerA, moseo, 1);
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm", 1);
        addCard(Zone.HAND, playerA, "Chaplain's Blessing"); // {W} gain 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaplain's Blessing");

        // there is a trigger, but no valid target
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);

        execute();
        assertLife(playerA, 25);
        assertPermanentCount(playerA, "Craw Wurm", 0);
    }

    @Test
    public void test_Gain5_NoValidTarget_Attempt() {
        addCard(Zone.BATTLEFIELD, playerA, moseo, 1);
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm", 1);
        addCard(Zone.HAND, playerA, "Chaplain's Blessing"); // {W} gain 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaplain's Blessing");

        // there is a trigger, but no valid target, target will not be used
        addTarget(playerA, "Craw Wurm");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Targets list was setup by addTarget with [Craw Wurm], but not used")) {
                Assert.fail("must have thrown error about unused target, but got:\n" + e.getMessage());
            }
        }
        assertPermanentCount(playerA, "Craw Wurm", 0);
    }

    @Test
    public void test_Gain5_ValidTarget() {
        addCard(Zone.BATTLEFIELD, playerA, moseo, 1);
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm", 1);
        addCard(Zone.GRAVEYARD, playerA, "Centaur Courser", 1);
        addCard(Zone.GRAVEYARD, playerA, "Armored Cancrix", 1);
        addCard(Zone.HAND, playerA, "Chaplain's Blessing"); // {W} gain 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaplain's Blessing");

        // target for the trigger
        addTarget(playerA, "Armored Cancrix");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Craw Wurm", 0);
    }
}
