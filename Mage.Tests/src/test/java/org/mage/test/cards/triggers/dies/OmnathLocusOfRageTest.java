package org.mage.test.cards.triggers.dies;

import mage.abilities.keyword.ProtectionAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class OmnathLocusOfRageTest extends CardTestPlayerBase {

    /**
     * The new Omnath's ability doesn't trigger when he dies, although it
     * explicitely states in the card's text that it should.
     */
    @Test
    public void testDiesTriggeredAbility() {
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, put a 5/5 red and green Elemental creature token onto the battlefield.
        // Whenever Omnath, Locus of Rage or another Elemental you control dies, Omnath deals 3 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Rage", 1);

        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerB, "Diabolic Edict", 1); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Diabolic Edict");
        addTarget(playerB, playerA);
        addTarget(playerA, "Omnath, Locus of Rage"); // sacrifice target
        addTarget(playerA, playerB); // target for dies trigger with damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Diabolic Edict", 1);
        assertGraveyardCount(playerA, "Omnath, Locus of Rage", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    @Test
    public void testDiesTriggeredAbilityOnlyIfPresent() {
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, put a 5/5 red and green Elemental creature token onto the battlefield.
        // Whenever Omnath, Locus of Rage or another Elemental you control dies, Omnath deals 3 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Rage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Lightning Elemental", 1); // 4/1 Elemental - Haste

        // Blastfire Bolt deals 5 damage to target creature. Destroy all Equipment attached to that creature.
        addCard(Zone.HAND, playerB, "Blastfire Bolt", 1); // {5}{R}
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Lightning Elemental"); // Dying Lightning Elemental does no longer trigger ability of Omnath
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Blastfire Bolt", "Omnath, Locus of Rage");
        addTarget(playerA, playerB); // target for dies trigger with damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Blastfire Bolt", 1);
        assertGraveyardCount(playerA, "Omnath, Locus of Rage", 1);
        assertGraveyardCount(playerA, "Lightning Elemental", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testDiesTriggeredAbilityProtection() {
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Rage", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.HAND, playerB, "Doom Blade", 1);
        addCard(Zone.HAND, playerB, "Stave Off", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Scrubland", 3);

        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Omnath, Locus of Rage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        setChoice(playerB, "Green");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Stave Off", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Doom Blade", 1);
        assertGraveyardCount(playerB, "Stave Off", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 0);
        assertGraveyardCount(playerA, "Omnath, Locus of Rage", 1);

        Permanent lion = getPermanent("Silvercoat Lion");
        Assert.assertTrue("Lion has protection from green", lion.getAbilities(currentGame).containsClass(ProtectionAbility.class));

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
