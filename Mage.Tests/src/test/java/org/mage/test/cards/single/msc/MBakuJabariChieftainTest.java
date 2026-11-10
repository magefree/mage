package org.mage.test.cards.single.msc;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MBakuJabariChieftainTest extends CardTestPlayerBase {

    // At the beginning of your end step, if there is no monarch, target opponent becomes the monarch.
    // Whenever a creature attacks one of your opponents, if that player is the monarch, that creature gets +1/+1 and gains trample until end of turn.
    private static final String mbaku = "M'Baku, Jabari Chieftain";

    // When Palace Sentinels enters the battlefield, you become the monarch.
    private static final String sentinels = "Palace Sentinels";

    private static final String bears = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";
    private static final String memnite = "Memnite";

    @Test
    public void test_EndStep_NoMonarch_TargetOpponentBecomesMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mbaku);

        checkMonarch("no monarch yet", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, null);
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("monarch", playerB.getId(), currentGame.getMonarchId());
    }

    @Test
    public void test_EndStep_MonarchExists_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mbaku);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, sentinels);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentinels);

        // no target for the end step trigger: it must not trigger
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("monarch", playerA.getId(), currentGame.getMonarchId());
    }

    @Test
    public void test_AttackMonarch_BoostAndTrample() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mbaku);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        // turn 1 end step: B becomes the monarch
        addTarget(playerA, playerB);

        attack(3, playerA, bears, playerB);
        block(3, playerB, memnite, bears);
        setChoiceAmount(playerA, 1); // 1 damage to Memnite, 2 trample over

        checkPT("boosted", 3, PhaseStep.DECLARE_BLOCKERS, playerA, bears, 3, 3);
        checkAbility("trample", 3, PhaseStep.DECLARE_BLOCKERS, playerA, bears, TrampleAbility.class, true);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, memnite, 1);
        assertLife(playerB, 20 - 2);
        // combat damage to the monarch takes the crown
        Assert.assertEquals("monarch", playerA.getId(), currentGame.getMonarchId());
    }

    @Test
    public void test_AttackOpponentNotMonarch_NoBoost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mbaku);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, sentinels);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentinels);

        attack(3, playerA, bears, playerB);

        checkPT("not boosted", 3, PhaseStep.DECLARE_BLOCKERS, playerA, bears, 2, 2);
        checkAbility("no trample", 3, PhaseStep.DECLARE_BLOCKERS, playerA, bears, TrampleAbility.class, false);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        Assert.assertEquals("monarch", playerA.getId(), currentGame.getMonarchId());
    }

    @Test
    public void test_CreatureAttackingYou_NoBoost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mbaku);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, sentinels);
        addCard(Zone.BATTLEFIELD, playerB, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentinels);

        // you are the monarch, but you aren't one of your opponents
        attack(2, playerB, lion, playerA);

        checkPT("not boosted", 2, PhaseStep.DECLARE_BLOCKERS, playerB, lion, 2, 2);
        checkAbility("no trample", 2, PhaseStep.DECLARE_BLOCKERS, playerB, lion, TrampleAbility.class, false);

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20 - 2);
        Assert.assertEquals("monarch", playerB.getId(), currentGame.getMonarchId());
    }

    @Test
    public void test_AttackPlaneswalkerOfMonarch_NoBoost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mbaku);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren");

        // turn 1 end step: B becomes the monarch
        addTarget(playerA, playerB);

        // attacking a planeswalker isn't attacking a player
        attack(3, playerA, bears, "Jace Beleren");

        checkPT("not boosted", 3, PhaseStep.DECLARE_BLOCKERS, playerA, bears, 2, 2);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 1);
        Assert.assertEquals("monarch", playerB.getId(), currentGame.getMonarchId());
    }
}
