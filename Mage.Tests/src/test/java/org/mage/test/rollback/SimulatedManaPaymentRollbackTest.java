package org.mage.test.rollback;

import mage.abilities.ActivatedAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.ComputerPlayer7;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

public class SimulatedManaPaymentRollbackTest extends CardTestPlayerBaseWithAIHelps {

    private static final String EVENDO = "Evendo, Waking Haven";
    private static final String BELL = "Fountainport Bell";

    private void addEvendoAndCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, EVENDO);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
    }

    private Game simulateWithStationedEvendo(Game game) {
        Permanent evendo = findPermanent(game, EVENDO);
        evendo.addCounters(CounterType.CHARGE.createInstance(12), playerA.getId(), null, game);
        game.applyEffects();

        ComputerPlayer7 bot = (ComputerPlayer7) game.getPlayer(playerA.getId()).getRealPlayer();
        return new ComputerPlayer7(bot) {
            Game copyForAction() {
                return createSimulation(game).createSimulationForAI();
            }
        }.copyForAction();
    }

    private Permanent findPermanent(Game game, String name) {
        return game.getBattlefield().getAllActivePermanents(playerA.getId()).stream()
                .filter(permanent -> permanent.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Missing " + name));
    }

    @Test
    public void testFailedManaAbilityLeavesEvendoAvailableForBell() {
        addEvendoAndCreatures();
        addCard(Zone.BATTLEFIELD, playerA, BELL);
        runCode("Pay for Fountainport Bell with Evendo", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Game sim = simulateWithStationedEvendo(game);
            Permanent bell = findPermanent(sim, BELL);
            ActivatedAbility draw = bell.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD).get(0);

            // The larger mana ability is tried first. Without another source to pay for it,
            // the bot has to undo the tap and use Evendo's other mana ability.
            Assert.assertTrue("The bot can activate the Bell",
                    sim.getPlayer(playerA.getId()).activateAbility(draw, sim));
            Assert.assertTrue(findPermanent(sim, EVENDO).isTapped());
            Assert.assertNull("The Bell was sacrificed", sim.getPermanent(bell.getId()));
            Assert.assertEquals("The draw ability is on the stack", bell.getId(), sim.getStack().getFirst().getSourceId());
        });

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testSuccessfulManaAbilityKeepsPaymentAndMana() {
        addEvendoAndCreatures();
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        runCode("Pay for Evendo's larger mana ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Game sim = simulateWithStationedEvendo(game);
            ActivatedAbility mana = findPermanent(sim, EVENDO).getAbilities()
                    .getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerA.getId(), sim).stream()
                    .filter(ability -> !ability.getManaCosts().isEmpty())
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Missing Evendo's paid mana ability"));

            Assert.assertTrue(sim.getPlayer(playerA.getId()).activateAbility(mana, sim));
            Assert.assertTrue(findPermanent(sim, EVENDO).isTapped());
            Assert.assertTrue(findPermanent(sim, "Forest").isTapped());
            Assert.assertEquals(2, sim.getPlayer(playerA.getId()).getManaPool().getMana().getGreen());
        });

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
