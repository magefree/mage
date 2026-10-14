package org.mage.test.cards.abilities.keywords;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;

import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.UUID;

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

    //Test Conditional Target Adjuster allowing more generic casts
    @Test
    public void testLongRiversPull() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Ponder");
        addCard(Zone.HAND, playerA, "Long River's Pull"); // UU, counter noncreature only if gift

        setChoice(playerA, true);
        setChoice(playerA, playerB.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ponder");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Long River's Pull");
        addTarget(playerA, "Ponder");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 2);
        assertHandCount(playerB, 1);
    }

    @Test
    public void test_CastWithConvoke_PromisedGift() {
        // Gift a Treasure
        // Blooming Blast deals 2 damage to target creature. If the gift was promised, Blooming Blast also deals
        // 3 damage to that creature's controller.
        addCard(Zone.HAND, playerA, "Blooming Blast"); // {1}{R}, instant
        //
        // {T}: The next spell you cast this turn has convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // 2/2, for convoke
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blooming Blast", "Grizzly Bears");
        setChoice(playerA, true); // promise
        addTarget(playerA, "Runeclaw Bear"); // convoke for {1}
        setChoice(playerA, playerB.getName()); // gift to

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertTapped("Runeclaw Bear", true);
        assertPermanentCount(playerB, "Treasure Token", 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_CastWithConvoke_NotPromisedGift() {
        // Gift a Treasure
        // Blooming Blast deals 2 damage to target creature. If the gift was promised, Blooming Blast also deals
        // 3 damage to that creature's controller.
        addCard(Zone.HAND, playerA, "Blooming Blast"); // {1}{R}, instant
        //
        // {T}: The next spell you cast this turn has convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // 2/2, for convoke
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blooming Blast", "Grizzly Bears");
        setChoice(playerA, false); // no promise
        addTarget(playerA, "Runeclaw Bear"); // convoke for {1}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertTapped("Runeclaw Bear", true);
        assertPermanentCount(playerB, "Treasure Token", 0);
        assertLife(playerB, 20);
    }

    @Test
    @Ignore // TODO: fix costsTag data leak on rollbacks
    public void test_Rollback_MustNotLostGift_ManaAbilityCanceledWhilePaying() {
        // a canceled mana ability while paying a spell rolls back the game (example: a human cancels the sacrifice
        // of Ashnod's Altar), the spell must keep the promised gift after that
        //
        // Gift a Treasure
        // Blooming Blast deals 2 damage to target creature. If the gift was promised, Blooming Blast also deals
        // 3 damage to that creature's controller.
        addCard(Zone.HAND, playerA, "Blooming Blast"); // {1}{R}, instant
        addCustomCardWithAbility("canceled source", playerA, new SimpleManaAbility(Zone.BATTLEFIELD,
                Mana.ColorlessMana(2), new CanceledCost()), null, CardType.LAND, "", Zone.BATTLEFIELD);
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring"); // {T}: Add {C}{C}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        // R from Mountain, then {1}: the canceled source goes first (a land), then Sol Ring
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blooming Blast", "Grizzly Bears");
        setChoice(playerA, true); // promise
        setChoice(playerA, playerB.getName()); // gift to

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertTapped("Sol Ring", true);
        assertPermanentCount(playerB, "Treasure Token", 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    @Ignore // TODO: fix costsTag data leak on rollbacks
    public void test_Rollback_MustNotLostGift_ManaAbilityFailedWhilePaying() {
        // a failed mana ability while paying a spell rolls back the game, the spell must keep the promised gift after
        // that (Rakdos Signet is tried for {1} when nothing is left to pay its own {1}, then convoke pays)
        //
        // Gift a Treasure
        // Blooming Blast deals 2 damage to target creature. If the gift was promised, Blooming Blast also deals
        // 3 damage to that creature's controller.
        addCard(Zone.HAND, playerA, "Blooming Blast"); // {1}{R}, instant
        //
        // {T}: The next spell you cast this turn has convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // 2/2, for convoke
        addCard(Zone.BATTLEFIELD, playerA, "Rakdos Signet"); // {1}, {T}: Add {B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blooming Blast", "Grizzly Bears");
        setChoice(playerA, true); // promise
        addTarget(playerA, "Runeclaw Bear"); // convoke for {1}
        setChoice(playerA, playerB.getName()); // gift to

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertTapped("Runeclaw Bear", true);
        assertTapped("Rakdos Signet", false);
        assertPermanentCount(playerB, "Treasure Token", 1);
        assertLife(playerB, 20 - 3);
    }
}

/**
 * Payment cancel and rollback simulation (like HumanPlayer cancel a cost)
 * Use it if no cards available (current test uses both use cases as usage example)
 */
class CanceledCost extends CostImpl {

    CanceledCost() {
        this.text = "canceled cost";
    }

    private CanceledCost(final CanceledCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return false;
    }

    @Override
    public CanceledCost copy() {
        return new CanceledCost(this);
    }
}