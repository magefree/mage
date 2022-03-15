package org.mage.test.lki;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class LastKnownInformationTest extends CardTestPlayerBase {

    /**
     * see here for more information
     * http://www.slightlymagic.net/forum/viewtopic.php?f=116&t=14516
     *
     * Tests Safehold Elite with persist returns to battlefield with -1/-1
     * counter Murder Investigation has to put 2 tokens onto battlefield because
     * enchanted Safehold Elite was 2/2
     *
     * @author LevelX
     */
    @Test
    public void testPersistTriggersInTime() {
        // Safehold Elite {1}{G/W}
        // Creature - Elf Scout
        // 2/2
        // Persist
        addCard(Zone.BATTLEFIELD, playerA, "Safehold Elite");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // {1}{W}
        // Enchant creature you control
        // When enchanted creature dies, put X 1/1 white Soldier creature tokens onto the battlefield, where X is its power.
        addCard(Zone.HAND, playerA, "Murder Investigation", 1);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder Investigation", "Safehold Elite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite");
        // choose triggered ability order
        setChoice(playerA, "When enchanted creature dies");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite", "When enchanted creature dies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Murder Investigation", 1);
        assertPermanentCount(playerA, "Safehold Elite", 0);
        // because enchanted Safehold Elite's P/T was 2/2, Murder Investigation has to put 2 Soldier onto the battlefield
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertGraveyardCount(playerB, "Lightning Bolt", 2);

        assertActionsCount(playerB, 0);

    }

    /**
     * Here we test that Trostani's first ability checks the toughness on
     * resolve.
     *
     */
    @Test
    public void testTrostaniSelesnyasVoice1() {
        // Whenever another creature enters the battlefield under your control, you gain life equal to that creature's toughness.
        // {1}{G}{W}, {T}: Populate. (Create a tokenonto the battlefield that's a copy of a creature token you control.)
        addCard(Zone.BATTLEFIELD, playerA, "Trostani, Selesnya's Voice");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears", "Grizzly Bears", StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Giant Growth", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 25);
    }

    /**
     * Here we test correct spell interaction by playing Cloudshift BEFORE Giant
     * Growth resolves. Cloudshift will remove 2/2 creature and it will return
     * as 2/2. Giant Growth will be fizzled. That means that player should gain
     * 2 + 2 life.
     */
    @Test
    public void testTrostaniSelesnyasVoice2() {
        addCard(Zone.BATTLEFIELD, playerA, "Trostani, Selesnya's Voice");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.HAND, playerA, "Cloudshift", 1);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears", "Grizzly Bears", StackClause.WHILE_NOT_ON_STACK);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears", "Giant Growth",
                StackClause.WHILE_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 24);
    }

    /**
     * Here we test actual use of LKI by playing Cloudshift AFTER Giant Growth
     * resolves. Cloudshift will remove 5/5 creature and it will return as 2/2.
     * That means that player should gain 5 + 2 life.
     *
     */
    @Test
    public void testTrostaniSelesnyasVoice3() {
        addCard(Zone.BATTLEFIELD, playerA, "Trostani, Selesnya's Voice");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.HAND, playerA, "Cloudshift", 1);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears", "Grizzly Bears", StackClause.WHILE_NOT_ON_STACK);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears", "Giant Growth",
                StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 27);

    }

}
