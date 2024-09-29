package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by IGOUDT on 30-3-2017.
 */
public class KiraGreatGlassSpinnerTest extends CardTestPlayerBase {

    /*
       Kira, Great Glass-Spinner  {1}{U}{U}
       Legendary Creature - Spirit 2/2
       Flying
       Creatures you control have "Whenever this creature becomes the target of a spell or ability for the first time each turn, counter that spell or ability."
     */
    private static final String kira = "Kira, Great Glass-Spinner";
    private static final String ugin = "Ugin, the Spirit Dragon";
    private static final String bolt = "Lightning Bolt";

    @Test
    public void counterFirst() {

        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyalty counters

        addCard(Zone.BATTLEFIELD, playerA, kira);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage", kira); // Ugin ability

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kira, 1);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 9);
    }

    @Test
    public void counterFirstResolveSecond() {

        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyalty counters
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Unsummon", 1);

        addCard(Zone.BATTLEFIELD, playerA, kira);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage", kira); // Ugin ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", kira);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kira, 0);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 9);
        assertGraveyardCount(playerA, "Unsummon", 1);
    }

    @Test
    public void counterFirstThisTurn_counterFirstOnNextTurn() {

        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyalty counters

        addCard(Zone.BATTLEFIELD, playerA, kira);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage to", kira); // Ugin ability
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage to", kira); // Ugin ability

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, kira, 1);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 11);
    }


    @Test
    public void testKiraGreatGlassSpinnerFirstSpellTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, kira, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, kira);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, bolt, 1);
        assertPermanentCount(playerB, kira, 1);
    }

    @Test
    public void testKiraGreatGlassSpinnerRightAbility() {
        // Reported bug: #8026
        addCard(Zone.BATTLEFIELD, playerA, "Steadfast Guard", 1); // 2/2 Vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Angelic Benediction", 1); // Exalted
        // Whenever a creature you control attacks alone, you may tap target creature.
        addCard(Zone.BATTLEFIELD, playerB, kira, 1);

        attack(1, playerA, "Steadfast Guard", playerB);
        setChoice(playerA, "Whenever"); // choose trigger order - exalted at top of stack
        addTarget(playerA, kira); // creature to tap
        //setChoice(playerA, true); // choose yes to tap - gets countered before choice needed

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17); // exalted pumps 2/2 to 3/3, so 3 life lost
        assertTapped(kira, false); // tap ability was countered
    }

    @Test
    public void testKiraGreatGlassSpinnerRightAbilityOtherOrder() {
        // Reported bug: #8026
        addCard(Zone.BATTLEFIELD, playerA, "Steadfast Guard", 1); // 2/2 Vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Angelic Benediction", 1); // Exalted
        // Whenever a creature you control attacks alone, you may tap target creature.
        addCard(Zone.BATTLEFIELD, playerB, kira, 1);

        attack(1, playerA, "Steadfast Guard", playerB);
        setChoice(playerA, "exalted"); // just in case... try the other trigger order
        addTarget(playerA, kira); // creature to tap
        //setChoice(playerA, true); // choose yes to tap - gets countered before choice needed

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17); // exalted pumps 2/2 to 3/3, so 3 life lost
        assertTapped(kira, false); // tap ability was countered
    }

    @Test
    public void counterFirstBlinkCounterNext() {
        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyalty counters
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.HAND, playerA, "Shock", 1);
        addCard(Zone.HAND, playerA, "Cloudshift", 1);
        addCard(Zone.BATTLEFIELD, playerA, kira);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage", kira); // Ugin ability, countered
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Cloudshift", kira); // not countered (second time being targeted)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Shock", kira); // countered (zcc has changed, so first time again)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, kira, 1);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 9);
        assertGraveyardCount(playerA, "Shock", 1);
        assertGraveyardCount(playerA, "Cloudshift", 1);
    }

    @Test
    public void counterCorrectlyMultipleOnStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Battlegrowth", 1); // Instant {G} Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Dragonscale Boon", 1); // Instant {3}{G} Put two +1/+1 counters on target creature and untap it.
        addCard(Zone.BATTLEFIELD, playerA, kira);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", kira); // countered
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dragonscale Boon", kira); // not countered (second target)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kira, 1);
        assertGraveyardCount(playerA, "Battlegrowth", 1);
        assertGraveyardCount(playerA, "Dragonscale Boon", 1);
        assertPowerToughness(playerA, kira, 4, 4);
    }

}
