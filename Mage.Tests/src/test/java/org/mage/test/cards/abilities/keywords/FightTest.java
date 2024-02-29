package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class FightTest extends CardTestPlayerBase {

    //701.12. Fight
    //701.12a. A spell or ability may instruct a creature to fight another creature or it may instruct two creatures to fight each other.
    // Each of those creatures deals damage equal to its power to the other creature.
    //701.12b. If one or both creatures instructed to fight are no longer on the battlefield or are no longer creatures, neither of them fights or deals damage.
    // If one or both creatures are illegal targets for a resolving spell or ability that instructs them to fight, neither of them fights or deals damage.
    //701.12c. If a creature fights itself, it deals damage to itself equal to twice its power.
    //701.12d. The damage dealt when a creature fights isn't combat damage.

    private static final String preyUpon = "Prey Upon"; // {G}
    // Target creature you control fights target creature you don’t control.
    private static final String huntTheWeak = "Hunt the Weak"; // {3}{G}
    // Put a +1/+1 counter on target creature you control. Then that creature fights target creature you don’t control.
    private static final String savageSwipe = "Savage Swipe"; // {G}
    // Target creature you control gets +2/+2 until end of turn if its power is 2. Then it fights target creature you don’t control.
    private static final String rivalsDuel = "Rivals' Duel";
    // Choose two target creatures that share no creature types. Those creatures fight each other.
    private static final String daybreakRanger = "Daybreak Ranger";
    // At the beginning of each upkeep, if no spells were cast last turn, transform Daybreak Ranger.
    private static final String nightfallPredator = "Nightfall Predator";
    private static final String nightfallAbility = "{R}, {T}: {this} fights target creature.";
    private static final String sunhomeEnforcer = "Sunhome Enforcer"; // 2/4
    // Whenever Sunhome Enforcer deals combat damage, you gain that much life.
    private static final String mammothSpider = "Mammoth Spider"; // 3/5
    private static final String warriorAngel = "Warrior Angel"; // 3/4
    // Whenever Warrior Angel deals damage, you gain that much life.
    private static final String caravanHurda = "Caravan Hurda"; // 1/5 Lifelink
    private static final String foeRazer = "Foe-Razer Regent"; // 4/5
    // When Foe-Razer Regent enters the battlefield, you may have it fight target creature you don’t control.
    // Whenever a creature you control fights, put two +1/+1 counters on it at the beginning of the next end step.

    @Test
    public void testSimpleFight() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, preyUpon);
        addCard(Zone.BATTLEFIELD, playerA, sunhomeEnforcer);
        addCard(Zone.BATTLEFIELD, playerB, mammothSpider);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, preyUpon);
        addTarget(playerA, sunhomeEnforcer);
        addTarget(playerA, mammothSpider);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertDamageReceived(playerA, sunhomeEnforcer, 3);
        assertDamageReceived(playerB, mammothSpider, 2);
    }

    @Test
    public void testPumpFight() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, savageSwipe);
        addCard(Zone.BATTLEFIELD, playerA, sunhomeEnforcer);
        addCard(Zone.BATTLEFIELD, playerB, caravanHurda);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, savageSwipe);
        addTarget(playerA, sunhomeEnforcer);
        addTarget(playerA, caravanHurda);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 21);
        assertDamageReceived(playerA, sunhomeEnforcer, 1);
        assertDamageReceived(playerB, caravanHurda, 4);
    }

    @Test
    public void testCounterFight() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, huntTheWeak);
        addCard(Zone.BATTLEFIELD, playerA, warriorAngel);
        addCard(Zone.BATTLEFIELD, playerB, caravanHurda);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, huntTheWeak);
        addTarget(playerA, warriorAngel);
        addTarget(playerA, caravanHurda);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 21);
        assertDamageReceived(playerA, warriorAngel, 1);
        assertDamageReceived(playerB, caravanHurda, 4);
    }

    @Test
    public void testFightTwoTargets() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, rivalsDuel);
        addCard(Zone.BATTLEFIELD, playerA, mammothSpider);
        addCard(Zone.BATTLEFIELD, playerA, caravanHurda);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rivalsDuel, mammothSpider+"^"+caravanHurda);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);
        assertDamageReceived(playerA, mammothSpider, 1);
        assertDamageReceived(playerA, caravanHurda, 3);
    }

    @Test
    public void testFightEvent() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, foeRazer);
        addCard(Zone.BATTLEFIELD, playerB, mammothSpider);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, foeRazer);
        setChoice(playerA, true);
        addTarget(playerA, mammothSpider);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertCounterCount(playerA, foeRazer, CounterType.P1P1, 2);
        assertCounterCount(playerB, mammothSpider, CounterType.P1P1, 0);
    }

    @Test
    public void testFightSelf() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, daybreakRanger);
        addCard(Zone.BATTLEFIELD, playerB, "Dampening Pulse", 3); // -1/-0 to playerA's creatures, x3

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, nightfallAbility);
        addTarget(playerA, nightfallPredator); // fights self

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertDamageReceived(playerA, nightfallPredator, 2);
    }

}
