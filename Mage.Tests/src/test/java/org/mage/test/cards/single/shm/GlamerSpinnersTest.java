package org.mage.test.cards.single.shm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class GlamerSpinnersTest extends CardTestPlayerBase {

    /*
    Glamer Spinners
    {4}{WU}
    Creature - Faerie Wizard
    Flash
    Flying
    When Glamer Spinners enters the battlefield, attach all Auras enchanting target permanent to another permanent with the same controller.
    2/4
    */
    private static final String glamerSpinners = "Glamer Spinners";

    /*
    Feral Invocation
    {2}{G}
    Enchantment - Aura
    Flash <i>(You may cast this spell any time you could cast an instant.)</i>
    Enchant creature
    Enchanted creature gets +2/+2.
    */
    private static final String feralInvocation = "Feral Invocation";

    /*
    Memnite
    {0}
    Artifact Creature - Construct
    1/1
    */
    private static final String memnite = "Memnite";

    /*
    Kraken Hatchling
    {U}
    Creature - Kraken
    0/4
    */
    private static final String krakenHatchling = "Kraken Hatchling";

    @Test
    public void testGlamerSpinners() {
        addCard(Zone.HAND, playerA, glamerSpinners);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerB, feralInvocation);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        addCard(Zone.BATTLEFIELD, playerB, krakenHatchling);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, feralInvocation, memnite);
        checkPT("enchanted", 1, PhaseStep.BEGIN_COMBAT, playerB, memnite, 3, 3);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, glamerSpinners);
        addTarget(playerA, memnite);
        setChoice(playerA, krakenHatchling);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, glamerSpinners, 1);
        assertPowerToughness(playerB, memnite, 1, 1);
        assertPowerToughness(playerB, krakenHatchling, 2, 6);
    }
}
