package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantBeSacrificedTest extends CardTestPlayerBase {

    private static final String assaultSuit = "Assault Suit";
    /*
     Assault Suit {4}
     Artifact — Equipment
     Equipped creature gets +2/+2, has haste, can’t attack you or planeswalkers you control, and can’t be sacrificed.
     At the beginning of each opponent’s upkeep, you may have that player gain control of equipped creature until end of turn. If you do, untap it.
     Equip {3}
     */

    private static final String allSac = "Innocent Blood";
    // Sorcery {B} Each player sacrifices a creature.

    private static final String urchin = "Bile Urchin";
    // Creature — Spirit {B}  Sacrifice Bile Urchin: Target player loses 1 life.

    private static final String zombie = "Walking Corpse"; // 2/2 vanilla
    private static final String vampire = "Barony Vampire"; // 3/2 vanilla

    private static final String bairn = "Blood Bairn"; // Sacrifice another creature: ~ gets +2/+2 until EOT

    private static final String jonIren = "Jon Irenicus, Shattered One";
    // At the beginning of your end step, target opponent gains control of up to one target creature you control.
    // Put two +1/+1 counters on it and tap it. It’s goaded for the rest of the game and it gains “This creature can’t be sacrificed.”

    @Test
    public void testAssaultSuitWithSacEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, zombie);
        addCard(Zone.BATTLEFIELD, playerA, assaultSuit);
        addCard(Zone.BATTLEFIELD, playerB, vampire);
        addCard(Zone.HAND, playerA, allSac);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", zombie);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, allSac);
        setChoice(playerB, vampire); // to sacrifice (player A can't)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, zombie, 1);
        assertGraveyardCount(playerB, vampire, 1);
    }

    @Test
    public void testAssaultSuitWithSacSourceCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, urchin);
        addCard(Zone.BATTLEFIELD, playerA, assaultSuit);

        checkPlayableAbility("Can sacrifice", 1, PhaseStep.UPKEEP, playerA, "Sacrifice ", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", urchin);
        checkPlayableAbility("Can't sacrifice", 1, PhaseStep.BEGIN_COMBAT, playerA, "Sacrifice ", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, assaultSuit, urchin, true);
    }

    @Test
    public void testAssaultSuitWithSacAnotherCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, zombie);
        addCard(Zone.BATTLEFIELD, playerA, bairn);
        addCard(Zone.BATTLEFIELD, playerA, assaultSuit);

        checkPlayableAbility("Can sacrifice", 1, PhaseStep.UPKEEP, playerA, "Sacrifice another", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", zombie);
        checkPlayableAbility("Can't sacrifice", 1, PhaseStep.BEGIN_COMBAT, playerA, "Sacrifice another", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, assaultSuit, zombie, true);
        assertPowerToughness(playerA, bairn, 2, 2);
    }

    @Test
    public void testJonIrenicusWithSacSourceCost() {
        addCard(Zone.BATTLEFIELD, playerA, urchin);
        addCard(Zone.BATTLEFIELD, playerA, jonIren);

        checkPlayableAbility("Can sacrifice", 1, PhaseStep.UPKEEP, playerA, "Sacrifice ", true);
        checkPlayableAbility("Can't sacrifice", 1, PhaseStep.UPKEEP, playerB, "Sacrifice ", false);

        addTarget(playerA, playerB); // target opponent gains control
        addTarget(playerA, urchin); // of up to one target creature you control

        checkPlayableAbility("Can't sacrifice", 2, PhaseStep.UPKEEP, playerA, "Sacrifice ", false);
        checkPlayableAbility("Can't sacrifice", 2, PhaseStep.UPKEEP, playerB, "Sacrifice ", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, urchin, 1);
        assertCounterCount(urchin, CounterType.P1P1, 2);
    }

}
