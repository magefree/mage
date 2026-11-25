package org.mage.test.cards.single.trc;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * 702.184a
 * Station is an activated ability. "Station" means "Tap another untapped creature you control: Put a number of charge
 * counters on this permanent equal to the tapped creature's power. Activate only as a sorcery."
 * <p>
 * 722.2b
 * "{N+}[abilities][P/T]" means "As long as this permanent has N or more charge counters on it, it has [abilities] and
 * is a creature with base power and toughness [P/T] in addition to its other types."
 * <p>
 * 106.6
 * Some spells and abilities produce mana that can be spent only in certain ways.
 *
 * @author DarioBarrabinoDev
 */
public class QueensDiamondTest extends CardTestCommanderDuelBase {

    @Test
    public void test_Station_PutsChargeCountersEqualToPower() {
        // Station (Tap another creature you control: Put charge counters equal to its power on this Spacecraft.
        // Station only as a sorcery. It's an artifact creature at 8+.)
        addCard(Zone.BATTLEFIELD, playerA, "Queen's Diamond"); // {4}
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Hill Giant"); // creature to tap for station

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Hill Giant", true);
        assertCounterCount(playerA, "Queen's Diamond", CounterType.CHARGE, 3);
        assertType("Queen's Diamond", CardType.CREATURE, false); // below the 8+ threshold
    }

    @Test
    public void test_Mana_AmountEqualsChargeCounters() {
        // {T}: Choose a color. Add one mana of that color for each charge counter on Queen's Diamond.
        // Spend this mana only to cast your commander.
        addCard(Zone.BATTLEFIELD, playerA, "Queen's Diamond"); // {4}
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Hill Giant"); // creature to tap for station

        // postcombat main, so the station ability has already resolved
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Choose a color");
        setChoice(playerA, "Green"); // color of the produced mana
        checkManaPool("one mana per charge counter", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "G", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Queen's Diamond", CounterType.CHARGE, 3);
    }

    @Test
    public void test_Mana_CantBeSpentOnNonCommanderSpell() {
        // {T}: Choose a color. Add one mana of that color for each charge counter on Queen's Diamond.
        // Spend this mana only to cast your commander.
        addCard(Zone.BATTLEFIELD, playerA, "Queen's Diamond"); // {4}
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        //
        addCard(Zone.HAND, playerA, "Balduvian Bears"); // {1}{G}, not the commander

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Hill Giant"); // creature to tap for station

        // postcombat main, so the station ability has already resolved
        checkPlayableAbility("before mana", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Choose a color");
        setChoice(playerA, "Green"); // color of the produced mana
        checkManaPool("restricted mana", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "G", 3);
        checkPlayableAbility("after mana", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 0);
    }

    @Test
    public void test_Mana_CanBeSpentOnCommander() {
        // {T}: Choose a color. Add one mana of that color for each charge counter on Queen's Diamond.
        // Spend this mana only to cast your commander.
        addCard(Zone.BATTLEFIELD, playerA, "Queen's Diamond"); // {4}
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        //
        addCard(Zone.COMMAND, playerA, "Balduvian Bears"); // {1}{G}, the commander

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Hill Giant"); // creature to tap for station

        // postcombat main, so the station ability has already resolved
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Choose a color");
        setChoice(playerA, "Green"); // color of the produced mana
        checkManaPool("restricted mana", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "G", 3);
        checkPlayableAbility("commander spell", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_StationEight_BecomesArtifactCreature() {
        // 8+ | Flying, vigilance, haste
        addCard(Zone.BATTLEFIELD, playerA, "Queen's Diamond"); // {4}
        addCard(Zone.BATTLEFIELD, playerA, "Fusion Elemental"); // 8/8

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Fusion Elemental"); // creature to tap for station

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Queen's Diamond", CounterType.CHARGE, 8);
        assertType("Queen's Diamond", CardType.ARTIFACT, SubType.SPACECRAFT);
        assertType("Queen's Diamond", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Queen's Diamond", 5, 5);
        assertAbility(playerA, "Queen's Diamond", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "Queen's Diamond", VigilanceAbility.getInstance(), true);
        assertAbility(playerA, "Queen's Diamond", HasteAbility.getInstance(), true);
    }
}
