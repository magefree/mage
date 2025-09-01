package org.mage.test.cards.enchantments;

import mage.abilities.keyword.ReadAheadAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ReadAheadTest extends CardTestPlayerBase {

    private static final String war = "The Elder Dragon War";
    private static final String recall = "Ancestral Recall";

    @Test
    public void testElderDragonWarChapter1() {
        addCard(Zone.HAND, playerA, war);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);
        setChoice(playerA, "X=1");


        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, war, 1);
        assertGraveyardCount(playerA, war, 0);
    }

    @Test
    public void testElderDragonWarChapter2() {
        addCard(Zone.HAND, playerA, war);
        addCard(Zone.HAND, playerA, recall);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);
        setChoice(playerA, "X=2");
        setChoice(playerA, recall);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, recall, 1);
        assertHandCount(playerA, recall, 0);
        assertPermanentCount(playerA, war, 1);
        assertGraveyardCount(playerA, war, 0);
    }

    @Test
    public void testElderDragonWarChapter3() {
        addCard(Zone.HAND, playerA, war);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Dragon Token", 1);
        assertPermanentCount(playerA, war, 0);
        assertGraveyardCount(playerA, war, 1);
    }

    private static final String rite = "Rite of Belzenlok";
    private static final String babs = "Barbara Wright";

    @Test
    public void testBarbaraWrightChapter1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, babs);
        addCard(Zone.HAND, playerA, rite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertCounterCount(rite, CounterType.LORE, 1);
        assertPermanentCount(playerA, "Cleric Token", 2);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(rite, CounterType.LORE, 2);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        assertPermanentCount(playerA, rite, 0);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);
        assertPermanentCount(playerA, "Demon Token", 1);
    }

    @Test
    public void testBarbaraWrightChapter2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, babs);
        addCard(Zone.HAND, playerA, rite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite);
        setChoice(playerA, "X=2");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertCounterCount(rite, CounterType.LORE, 2);
        assertAbility(playerA, rite, ReadAheadAbility.getInstance(), true);
        assertPermanentCount(playerA, "Cleric Token", 2);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        assertPermanentCount(playerA, rite, 0);
        assertPermanentCount(playerA, "Cleric Token", 2);
        assertPermanentCount(playerA, "Demon Token", 1);
    }

    @Test
    public void testBarbaraWrightChapter3() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, babs);
        addCard(Zone.HAND, playerA, rite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite);
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        assertPermanentCount(playerA, rite, 0);
        assertPermanentCount(playerA, "Cleric Token", 0);
        assertPermanentCount(playerA, "Demon Token", 1);
    }
}
