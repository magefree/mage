package org.mage.test.commander.duel;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.token.ArtifactWallToken;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */
public class CommanderAffinityTest extends CardTestCommanderDuelBase {

    /*
    Blinkmoth Infusion {12}{U}{U}
    Affinity for artifacts (This spell costs {1} less to cast for each artifact you control.)
    Untap all artifacts.
    */

    @Test
    public void test_AffinityNormal() {
        addCard(Zone.HAND, playerA, "Blinkmoth Infusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner", 12);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        checkHandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion", 1);

        // cast for UU (12 must be reduced)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        checkHandCardCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Blinkmoth Infusion", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AffinityCommanderNormalReduction() {
        addCard(Zone.COMMAND, playerA, "Blinkmoth Infusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner", 12);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 2 * 2);

        checkCommandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion", 1);

        // first cast for 12UU (-12 by abzan, -UU by islands)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, true); // keep commander
        checkCommandCardCount("after 1", 1, PhaseStep.BEGIN_COMBAT, playerA, "Blinkmoth Infusion", 1);

        // second cast for 12UU + 2 (-12 by abzan, -UU by islands, -2 by islands)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, false); // remove commander to grave
        checkCommandCardCount("after 2", 1, PhaseStep.END_TURN, playerA, "Blinkmoth Infusion", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AffinityCommanderAdditionalReduction() {
        addCard(Zone.COMMAND, playerA, "Blinkmoth Infusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 2);

        checkCommandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion", 1);

        // first cast for 12UU (-12 by abzan, -UU by islands)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, true); // keep commander
        checkCommandCardCount("after 1", 1, PhaseStep.BEGIN_COMBAT, playerA, "Blinkmoth Infusion", 1);

        // second cast for 12UU + 2 (-12 by abzan, -UU by islands, -2 by abzan)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, false); // remove commander to grave
        checkCommandCardCount("after 2", 1, PhaseStep.END_TURN, playerA, "Blinkmoth Infusion", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Gained_Affinity() {
        // bug: Mycosynth Golem did not allow my commander, Karn, Silver Golem, to cost 0 even though I had 7+ artifacts on the board.

        Ability ability = new SimpleActivatedAbility(Zone.ALL, new CreateTokenEffect(new ArtifactWallToken(), 7), new ManaCostsImpl<>("{R}"));
        addCustomCardWithAbility("generate tokens", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.COMMAND, playerA, "Karn, Silver Golem", 1); // {5}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        // Destroy target artifact.
        addCard(Zone.HAND, playerA, "Ancient Grudge", 1); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Artifact creature spells you cast have affinity for artifacts. (They cost {1} less to cast for each artifact you control.)
        addCard(Zone.BATTLEFIELD, playerA, "Mycosynth Golem", 1);

        // Affinity for artifacts
        // Artifact creature spells you cast have affinity for artifacts.
        // addCard(Zone.BATTLEFIELD, playerA, "Mycosynth Golem");
        // addCard(Zone.HAND, playerA, "Alpha Myr"); // Creature - Myr  2/1

        checkCommandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn, Silver Golem", 1);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Karn, Silver Golem", true);

        // first cast for 5 and destroy (prepare commander with additional cost)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn, Silver Golem");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancient Grudge", "Karn, Silver Golem");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkCommandCardCount("after destroy ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn, Silver Golem", 1);
        checkPlayableAbility("after destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Karn, Silver Golem", false);
        setChoice(playerA, true); // move to command zone
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // can't do the second cast with additional cost (must pay 2 + 5, but have only R)
        checkPlayableAbility("after move", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Karn, Silver Golem", false);

        // generate artifact tokens
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Create");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after tokens", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall Token", 7);
        checkPlayableAbility("after tokens", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Karn, Silver Golem", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Gained_Convoke() {
        // bug:
        // https://github.com/magefree/mage/issues/7171
        // Commander doesn't get 'convoke' when chief engineer is on the battlefield, so for instance I can't cast Breya by tapping other creatures.

        // Artifact spells you cast have convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Chief Engineer", 1);
        //
        addCard(Zone.COMMAND, playerA, "Karn, Silver Golem", 1); // {5}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 - 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);

        // 3 mana + 2 convoke
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Karn, Silver Golem", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn, Silver Golem");
        addTarget(playerA, "Grizzly Bears"); // convoke cost
        addTarget(playerA, "Grizzly Bears"); // convoke cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Karn, Silver Golem", 1);
    }
}
