package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class GiftTest extends CardTestPlayerBase {

    private static final String truce = "Dawn's Truce";

    @Test
    public void testNoGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, truce);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, truce);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Plains", HexproofAbility.getInstance(), true, 2);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), false, 2);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, truce);

        setChoice(playerA, true);
        setChoice(playerA, playerB.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, truce);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Plains", HexproofAbility.getInstance(), true, 2);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), true, 2);
        assertHandCount(playerB, 1);
    }

    private static final String kitnap = "Kitnap";
    private static final String bear = "Grizzly Bears";

    @Test
    public void testPermanentNoGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, kitnap);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kitnap, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, kitnap, 1);
        assertPermanentCount(playerA, bear, 1);
        assertTapped(bear, true);
        assertCounterCount(bear, CounterType.STUN, 3);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testPermanentGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, kitnap);

        setChoice(playerA, true);
        setChoice(playerA, playerB.getName());
        setChoice(playerA, "When");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kitnap, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, kitnap, 1);
        assertPermanentCount(playerA, bear, 1);
        assertTapped(bear, true);
        assertCounterCount(bear, CounterType.STUN, 0);
        assertHandCount(playerB, 1);
    }

    private static final String hunger = "Nocturnal Hunger";

    @Test
    public void testNoGiftToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, hunger);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hunger, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, hunger, 1);
        assertGraveyardCount(playerB, bear, 1);
        assertPermanentCount(playerB, "Food Token", 0);
    }

    @Test
    public void testGiftToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, hunger);

        setChoice(playerA, true);
        setChoice(playerA, playerB.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hunger, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, hunger, 1);
        assertGraveyardCount(playerB, bear, 1);
        assertPermanentCount(playerB, "Food Token", 1);
    }

    private static final String rally = "Valley Rally";

    @Test
    public void testNoGiftExtraTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, rally);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rally);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, bear, 2 + 2, 2 + 0);
        assertAbility(playerA, bear, FirstStrikeAbility.getInstance(), false);
        assertPermanentCount(playerB, "Food Token", 0);
    }

    @Test
    public void testGiftExtraTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, rally);

        setChoice(playerA, true);
        setChoice(playerA, playerB.getName());
        addTarget(playerA, bear);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rally);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, bear, 2 + 2, 2 + 0);
        assertAbility(playerA, bear, FirstStrikeAbility.getInstance(), true);
        assertPermanentCount(playerB, "Food Token", 1);
    }

    private static final String gerbils = "Jolly Gerbils";

    @Test
    public void testGerbilNoGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, gerbils);
        addCard(Zone.HAND, playerA, truce);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, truce);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Plains", HexproofAbility.getInstance(), true, 2);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), false, 2);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testGerbilGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, gerbils);
        addCard(Zone.HAND, playerA, truce);

        setChoice(playerA, true);
        setChoice(playerA, playerB.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, truce);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Plains", HexproofAbility.getInstance(), true, 2);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), true, 2);
        assertHandCount(playerA, 1);
        assertHandCount(playerB, 1);
    }
}
