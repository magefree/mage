
package org.mage.test.cards.conditional;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class ManaWasSpentToCastTest extends CardTestPlayerBase {

    @Test
    public void testArtifactWillBeDestroyed() {
        // Tin Street Hooligan - Creature 2/1   {1}{R}
        // When Tin Street Hooligan enters the battlefield, if {G} was spent to cast Tin Street Hooligan, destroy target artifact.
        addCard(Zone.HAND, playerA, "Tin Street Hooligan");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Abzan Banner");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tin Street Hooligan");
        addTarget(playerA, "Abzan Banner");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tin Street Hooligan", 1);
        assertPermanentCount(playerB, "Abzan Banner", 0);
    }

    @Test
    public void testArtifactWontBeDestroyed() {
        // Tin Street Hooligan - Creature 2/1   {1}{R}
        // When Tin Street Hooligan enters the battlefield, if {G} was spent to cast Tin Street Hooligan, destroy target artifact.
        addCard(Zone.HAND, playerA, "Tin Street Hooligan");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Abzan Banner");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tin Street Hooligan");
        addTarget(playerA, "Abzan Banner");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tin Street Hooligan", 1);
        assertPermanentCount(playerB, "Abzan Banner", 1);
    }

    @Test
    public void testSnowMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Boreal Druid");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Search for Glory");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Search for Glory");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Search for Glory", 1);
        assertLife(playerA, 22);
    }

    @Test
    public void testSnowMana2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Vodalian Arcanist");
        addCard(Zone.BATTLEFIELD, playerA, "Rimefeather Owl");
        addCard(Zone.HAND, playerA, "Search for Glory");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{S}", "Vodalian Arcanist");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Search for Glory");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Search for Glory", 1);
        assertLife(playerA, 21);
    }

    @Test
    public void testSnowMana3() {
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Island");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Berg Strider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Berg Strider");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertTapped("Silvercoat Lion", true);
    }

    @Test
    public void testTreasureMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Jaded Sell-Sword");
        addCard(Zone.HAND, playerA, "Strike It Rich", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Strike It Rich");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jaded Sell-Sword");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertAbility(playerA, "Jaded Sell-Sword", FirstStrikeAbility.getInstance(), true);
        assertAbility(playerA, "Jaded Sell-Sword", HasteAbility.getInstance(), true);
    }

    @Test
    public void testTreasureMana2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Jaded Sell-Sword");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jaded Sell-Sword");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertAbility(playerA, "Jaded Sell-Sword", FirstStrikeAbility.getInstance(), false);
        assertAbility(playerA, "Jaded Sell-Sword", HasteAbility.getInstance(), false);
    }

    @Test
    public void testVerazol() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Verazol, the Split Current");

        setChoice(playerA, "X=2");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Verazol, the Split Current");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertCounterCount(playerA, "Verazol, the Split Current", CounterType.P1P1, 4);
        assertPowerToughness(playerA, "Verazol, the Split Current", 4, 4);
    }

    @Test
    public void testProssh() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerA, "Sphere of Resistance");
        addCard(Zone.HAND, playerA, "Prossh, Skyraider of Kher");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prossh, Skyraider of Kher");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Kobolds of Kher Keep", 7);
    }
}
