package org.mage.test.cards.single.frc;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;
import java.util.stream.Collectors;

public class VenserFerventForgerTest extends CardTestPlayerBase {

    private static final String venser = "Venser, Fervent Forger";
    private static final String bolt = "Lightning Bolt";
    private static final String shock = "Shock";
    private static final String bears = "Grizzly Bears";
    private static final String solRing = "Sol Ring";

    @Test
    public void testCopyOpponentSpellTwiceWithFlash() {
        // Flash
        // When Venser enters, choose one --
        // * Copy target instant or sorcery spell an opponent controls twice. You may choose new targets for the copies.
        // * Create two tokens that are copies of target permanent an opponent controls. They gain haste. At the beginning of the next end step, sacrifice them.
        addCard(Zone.HAND, playerA, venser);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerB, bolt);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, venser, null, bolt);
        setModeChoice(playerA, "1");
        addTarget(playerA, bolt);
        setChoice(playerA, true); // change targets of the first copy
        addTarget(playerA, playerB);
        setChoice(playerA, true); // change targets of the second copy
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, venser, 1);
        assertGraveyardCount(playerB, bolt, 1);
        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 3 - 3);
    }

    @Test
    public void testCantCopyOwnSpell() {
        addCard(Zone.HAND, playerA, venser);
        // Shock deals 2 damage to any target.
        addCard(Zone.HAND, playerA, shock);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shock, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, venser, null, shock);
        setModeChoice(playerA, "1"); // no legal target, so nothing is copied

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, venser, 1);
        assertGraveyardCount(playerA, shock, 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void testCopyOpponentCreature() {
        addCard(Zone.HAND, playerA, venser);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, venser);
        setModeChoice(playerA, "2");
        addTarget(playerA, bears);

        runCode("both tokens have haste", 1, PhaseStep.DECLARE_ATTACKERS, playerA, (info, player, game) -> {
            List<Permanent> tokens = game.getBattlefield()
                    .getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)
                    .stream()
                    .filter(permanent -> permanent.getName().equals(bears))
                    .collect(Collectors.toList());
            Assert.assertEquals(info, 2, tokens.size());
            tokens.forEach(token -> Assert.assertTrue(info, token.hasAbility(HasteAbility.getInstance(), game)));
        });
        attack(1, playerA, bears);
        checkPermanentCount("two copies", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, bears, 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, bears, 0);
        assertPermanentCount(playerB, bears, 1);
        assertPermanentCount(playerA, venser, 1);
    }

    @Test
    public void testCopyOpponentNoncreature() {
        addCard(Zone.HAND, playerA, venser);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerB, solRing);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, venser);
        setModeChoice(playerA, "2");
        addTarget(playerA, solRing);

        checkPermanentCount("two copies", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, solRing, 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, solRing, 0);
        assertPermanentCount(playerB, solRing, 1);
    }
}
