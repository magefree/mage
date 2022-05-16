package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ConstellationTest extends CardTestPlayerBase {

    /**
     * Daxos's Torment {3}{B} Constellation — Whenever Daxos's Torment or
     * another enchantment enters the battlefield under your control, Daxos's
     * Torment becomes a 5/5 Demon creature with flying and haste in addition to
     * its other types until end of turn.
     */
    private final String daxosCard = "Daxos's Torment";

    private void assertDaxosBoost(boolean mustHave) {
        if (mustHave) {
            assertPowerToughness(playerA, daxosCard, 5, 5);
            assertType(daxosCard, CardType.CREATURE, SubType.DEMON);
            assertAbility(playerA, daxosCard, FlyingAbility.getInstance(), true);
            assertAbility(playerA, daxosCard, HasteAbility.getInstance(), true);
        } else {
            assertPowerToughness(playerA, daxosCard, 0, 0);
            assertNotSubtype(daxosCard, SubType.DEMON);
            assertAbility(playerA, daxosCard, FlyingAbility.getInstance(), false);
            assertAbility(playerA, daxosCard, HasteAbility.getInstance(), false);
        }
    }

    @Test
    public void test_DaxosGotBoostOnEnter() {
        addCard(Zone.HAND, playerA, daxosCard, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, daxosCard, 0);
        assertPermanentCount(playerA, daxosCard, 1);
        assertDaxosBoost(true);
    }

    @Test
    public void test_DaxosLostBoostOnNextTurn() {
        addCard(Zone.HAND, playerA, daxosCard, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, daxosCard, 0);
        assertPermanentCount(playerA, daxosCard, 1);
        assertDaxosBoost(false);
    }

    @Test
    public void test_DaxosGotBoostOnOtherEnchantment() {
        addCard(Zone.HAND, playerA, daxosCard, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        addCard(Zone.HAND, playerA, "Absolute Grace", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Absolute Grace");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Absolute Grace", 0);
        assertPermanentCount(playerA, "Absolute Grace", 1);
        assertHandCount(playerA, daxosCard, 0);
        assertPermanentCount(playerA, daxosCard, 1);
        assertDaxosBoost(true);
    }

    @Test
    public void test_DaxosGotBoostAndWithPTLose() {
        addCard(Zone.HAND, playerA, daxosCard, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Night of Souls' Betrayal", 1); // All creatures get -1/-1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, daxosCard, 0);
        assertPermanentCount(playerA, daxosCard, 1);

        assertPowerToughness(playerA, daxosCard, 5 - 1, 5 - 1);
        assertType(daxosCard, CardType.CREATURE, SubType.DEMON);
        assertAbility(playerA, daxosCard, FlyingAbility.getInstance(), true);
        assertAbility(playerA, daxosCard, HasteAbility.getInstance(), true);
    }

    @Test
    public void test_DaxosGotBoostWithLoseFly() {
        // 112.10c  If two or more effects add and remove the same ability, in general the most recent one prevails.
        addCard(Zone.HAND, playerA, daxosCard, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.HAND, playerB, "Invert the Skies", 1); // Creatures your opponents control lose flying until end of turn
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);

        // got fly on enter
        checkHandCount("start hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);
        checkHandCount("first cast hand", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 0);
        checkPermanentCount("dax exist", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, daxosCard, 1);
        checkPT("pt after enter", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, daxosCard, 5, 5);
        checkAbility("fly after enter", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, daxosCard, FlyingAbility.class, true);
        // lose fly on invert
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Invert the Skies");
        checkPT("pt after invert", 1, PhaseStep.END_TURN, playerA, daxosCard, 5, 5);
        checkAbility("fly after invert", 1, PhaseStep.END_TURN, playerA, daxosCard, FlyingAbility.class, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_DaxosGotBoostWithLoseFlyAndGotItAgain() {
        // 112.10c  If two or more effects add and remove the same ability, in general the most recent one prevails.
        addCard(Zone.HAND, playerA, daxosCard, 1); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // All creatures lose flying.
        addCard(Zone.HAND, playerA, "Gravity Sphere", 1); // World Enchantment {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // got fly on enter, lose on gravity, got fly on gravity enter
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gravity Sphere");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, daxosCard, 0);
        assertPermanentCount(playerA, daxosCard, 1);
        assertHandCount(playerA, "Gravity Sphere", 0); // Possible problem : this is sometimes 1
        assertPermanentCount(playerA, "Gravity Sphere", 1);

        assertPowerToughness(playerA, daxosCard, 5, 5);
        assertType(daxosCard, CardType.CREATURE, SubType.DEMON);
        assertAbility(playerA, daxosCard, FlyingAbility.getInstance(), true);
        assertAbility(playerA, daxosCard, HasteAbility.getInstance(), true);
    }

    @Test
    public void test_DaxosGotBoostAndSaveColor() {
        addCard(Zone.HAND, playerA, daxosCard, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.HAND, playerA, "Chaoslace", 1); // Target spell or permanent becomes red.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.HAND, playerA, "Archetype of Courage", 1); // Enchantment to trigger Daxos
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // dax cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);
        checkPermanentCount("dax exist", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, daxosCard, 1);
        checkColor("dax without color", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, daxosCard, "R", false);
        // give dax new color
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaoslace", daxosCard);
        checkPT("dax not boost", 3, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, 0, 0);
        checkColor("dax is red", 3, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, "R", true);
        // color is saved on boost
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Archetype of Courage"); // make dax to creature
        checkPT("dax boost", 5, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, 5, 5);
        checkAbility("dax fly", 5, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, FlyingAbility.class, true);
        checkColor("dax is red", 5, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, "R", true);

        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }

    public void playDaxosAndVampire(boolean castVampireDifferentWay) {
        // 112.10c  If two or more effects add and remove the same ability, in general the most recent one prevails.
        // 613.7 -- dependacy effects (Mephidross Vampire must ALWAYS wait Daxos effect, not timestamp)
        addCard(Zone.HAND, playerA, daxosCard, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // Each creature you control is a Vampire in addition to its other creature types
        addCard(Zone.HAND, playerA, "Mephidross Vampire", 1); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        //
        addCard(Zone.HAND, playerA, "Archetype of Courage", 1); // Enchantment {1}{W}{W} to trigger Daxos
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // dax cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, daxosCard);
        checkPermanentCount("dax exist", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, daxosCard, 1);

        // give dax a new type (on next turn) -- effects dependacy
        if (castVampireDifferentWay) {
            castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Archetype of Courage"); // make dax to creature
            castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mephidross Vampire"); // give vampire to creatures
        } else {
            // Make sure not white mana is used here to cast the vampire
            checkManaPool("before B mana", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "B", 0);
            activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
            activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
            activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
            activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
            activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
            activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
            checkManaPool("after B mana", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "B", 6);
            castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mephidross Vampire"); // give vampire to creatures
            castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Archetype of Courage"); // make dax to creature
        }

        checkPT("dax boost", 3, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, 5, 5);
        checkAbility("dax fly", 3, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, FlyingAbility.class, true);
        checkSubType("dax is vampire", 3, PhaseStep.BEGIN_COMBAT, playerA, daxosCard, SubType.VAMPIRE, true);

        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_DaxosGotBoostAndNewTypeByDependencyEffects_RegularWay() {
        playDaxosAndVampire(false);
    }

    @Test
    public void test_DaxosGotBoostAndNewTypeByDependencyEffects_DifferentWay() {
        playDaxosAndVampire(true);
    }
}
