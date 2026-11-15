package org.mage.test.cards.single.msc;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Set;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public class RhinoTerribleTramplerTest extends CardTestPlayerBase {

    /**
     * Rhino, Terrible Trampler {4}{G}{G}
     * Legendary Creature - Human Villain 7/6
     * Trample
     * When Rhino enters, destroy target artifact or land. Distribute three +1/+1 counters among
     * up to three other target creatures. They gain trample until end of turn.
     */
    private static final String rhino = "Rhino, Terrible Trampler";

    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String lion = "Silvercoat Lion"; // 2/2
    private static final String balduvian = "Balduvian Bears"; // 2/2
    private static final String mindStone = "Mind Stone"; // artifact
    private static final String ornithopter = "Ornithopter"; // artifact creature 0/2
    private static final String bolt = "Lightning Bolt";

    @Test
    public void test_DestroyArtifact_DistributeAmongThree() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rhino);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, balduvian);
        addCard(Zone.BATTLEFIELD, playerB, mindStone);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rhino);
        addTargetAmount(playerA, bears, 1);
        addTargetAmount(playerA, lion, 1);
        addTargetAmount(playerA, balduvian, 1);
        addTarget(playerA, mindStone);

        checkAbility("bears trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, bears, TrampleAbility.class, true);
        checkAbility("lion trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, lion, TrampleAbility.class, true);
        checkAbility("balduvian trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, balduvian, TrampleAbility.class, true);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerB, mindStone, 1);
        assertPowerToughness(playerA, bears, 2 + 1, 2 + 1);
        assertPowerToughness(playerA, lion, 2 + 1, 2 + 1);
        assertPowerToughness(playerB, balduvian, 2 + 1, 2 + 1);
        assertCounterCount(playerA, rhino, CounterType.P1P1, 0);
        // trample until end of turn only
        assertAbility(playerA, bears, TrampleAbility.getInstance(), false);
        assertAbility(playerA, lion, TrampleAbility.getInstance(), false);
        assertAbility(playerB, balduvian, TrampleAbility.getInstance(), false);
    }

    @Test
    public void test_DestroyLand_AllCountersOnOne() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rhino);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rhino);
        addTargetAmount(playerA, bears, 3);
        addTarget(playerA, "Mountain");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Mountain", 1);
        assertPowerToughness(playerA, bears, 2 + 3, 2 + 3);
        assertAbility(playerA, bears, TrampleAbility.getInstance(), true);
        // not a target
        assertPowerToughness(playerA, lion, 2, 2);
        assertAbility(playerA, lion, TrampleAbility.getInstance(), false);
    }

    @Test
    public void test_NoCreatureTargets_RhinoIsNotPossibleTarget() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rhino);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerB, mindStone);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rhino);
        addTargetAmount(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, mindStone);

        runCode("rhino is not a possible target", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent rhinoPermanent = getPermanent(rhino, player);
            Ability ability = rhinoPermanent.getAbilities(game).stream()
                    .filter(EntersBattlefieldTriggeredAbility.class::isInstance)
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(info + " - must have the enters ability", ability);
            Set<UUID> possibleTargets = ability.getTargets().get(0).possibleTargets(player.getId(), ability, game);
            Assert.assertTrue(info + " - other creature must be possible", possibleTargets.contains(getPermanent(bears, player).getId()));
            Assert.assertFalse(info + " - rhino must not be possible", possibleTargets.contains(rhinoPermanent.getId()));
        });

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, mindStone, 1);
        assertPowerToughness(playerA, bears, 2, 2);
        assertAbility(playerA, bears, TrampleAbility.getInstance(), false);
        assertCounterCount(playerA, rhino, CounterType.P1P1, 0);
    }

    @Test
    public void test_ArtifactTargetIllegal_StillDistributes() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rhino);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, ornithopter);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rhino);
        addTargetAmount(playerA, bears, 2);
        addTargetAmount(playerA, lion, 1);
        addTarget(playerA, ornithopter);

        // kill the artifact in response to the trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, ornithopter);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, ornithopter, 1);
        assertGraveyardCount(playerB, bolt, 1);
        assertPowerToughness(playerA, bears, 2 + 2, 2 + 2);
        assertPowerToughness(playerA, lion, 2 + 1, 2 + 1);
        assertAbility(playerA, bears, TrampleAbility.getInstance(), true);
        assertAbility(playerA, lion, TrampleAbility.getInstance(), true);
    }
}
