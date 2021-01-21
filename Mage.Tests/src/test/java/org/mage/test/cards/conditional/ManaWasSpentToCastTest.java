
package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
}
