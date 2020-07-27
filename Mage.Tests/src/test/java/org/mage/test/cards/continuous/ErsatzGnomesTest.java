
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ErsatzGnomesTest extends CardTestPlayerBase {

    /**
     * Ersatz Gnomes is incorrectly used I targeted a spell which is a permanent
     * to colorless. When it enters the field its suppose to be colorless not go
     * back to normal. It's colorless until it leaves the battlefield when you
     * make a permanent spell colorless when you cast it.
     */
    @Test
    public void testColorlessSpellCreatesColorlessPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // {T}: Target spell becomes colorless.
        // {T}: Target permanent becomes colorless until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Ersatz Gnomes");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target spell", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Ersatz Gnomes", true);
        Permanent lion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertTrue("Silvercoat lion has to be colorless", lion.getColor(currentGame).isColorless());
    }

    @Test
    public void testColorlessSpellCreatesColorlessPermanentUntilItBattlefieldLeft() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // {T}: Target spell becomes colorless.
        // {T}: Target permanent becomes colorless until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Ersatz Gnomes");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Unsummon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target spell", "Silvercoat Lion");

        castSpell(1, PhaseStep.END_COMBAT, playerB, "Unsummon", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Unsummon", 1);
        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Ersatz Gnomes", true);
        Permanent lion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertTrue("Silvercoat lion has to be white", lion.getColor(currentGame).isWhite());
    }

    @Test
    public void testChangeColorOfBestowSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // Bestow {3}{W}
        // Lifelink
        // Echanted creature gets +1/+1 and has lifelink.
        addCard(Zone.HAND, playerA, "Hopeful Eidolon");// Creature {W}

        // {T}: Target spell becomes colorless.
        // {T}: Target permanent becomes colorless until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Ersatz Gnomes");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Unsummon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target spell", "Hopeful Eidolon");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Hopeful Eidolon", 0);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertTapped("Ersatz Gnomes", true);
        Permanent eidolon = getPermanent("Hopeful Eidolon", playerA);
        Assert.assertTrue("Hopeful Eidolon Enchantment has to be colorless", eidolon.getColor(currentGame).isColorless());
    }

    @Test
    public void testChangeColorOfBestowSpellUnsummon() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Bestow {3}{W}
        // Lifelink
        // Echanted creature gets +1/+1 and has lifelink.
        addCard(Zone.HAND, playerA, "Hopeful Eidolon");// Creature {W}

        // {T}: Target spell becomes colorless.
        // {T}: Target permanent becomes colorless until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Ersatz Gnomes");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Unsummon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target spell", "Hopeful Eidolon");

        castSpell(1, PhaseStep.END_COMBAT, playerB, "Unsummon", "Hopeful Eidolon");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hopeful Eidolon");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Unsummon", 1);
        assertHandCount(playerA, "Hopeful Eidolon", 0);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertTapped("Ersatz Gnomes", true);
        Permanent lion = getPermanent("Hopeful Eidolon", playerA);
        Assert.assertTrue("Hopeful Eidolon has to be white", lion.getColor(currentGame).isWhite());
    }
}
