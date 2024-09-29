package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DeeprootPilgrimageTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DeeprootPilgrimage} <br>
     * Deeproot Pilgrimage {1}{U} <br>
     * Enchantment <br>
     * Whenever one or more nontoken Merfolk you control become tapped, create a 1/1 blue Merfolk creature token with hexproof.
     */
    private static final String pilgrimage = "Deeproot Pilgrimage";

    // {2}{U}{U} Sorcery
    // Tap all creatures target player controls. Those creatures don’t untap during that player’s next untap step.
    private static final String sleep = "Sleep";

    // 3/2 Vanilla merfolk
    private static final String commando = "Coral Commando";

    @Test
    public void test_batch_tapped() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pilgrimage);
        addCard(Zone.BATTLEFIELD, playerA, commando, 4);

        addCard(Zone.HAND, playerA, sleep);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sleep", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount(commando, true, 4);
        assertPermanentCount(playerA, "Merfolk Token", 1);
    }

    @Test
    public void test_triggering_on_attack() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pilgrimage);
        addCard(Zone.BATTLEFIELD, playerA, commando);

        attack(1, playerA, commando, playerB);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertTappedCount(commando, true, 1);
        assertPermanentCount(playerA, "Merfolk Token", 1);
    }

    @Test
    public void test_not_triggering_on_non_merfolk() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pilgrimage);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2, not a merfolk.

        addCard(Zone.HAND, playerA, sleep);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sleep", playerA);
        // no trigger, bears is no Merfolk

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Grizzly Bears", true, 1);
        assertPermanentCount(playerA, "Merfolk Token", 0);
    }

    @Test
    public void test_not_triggering_on_opp_merfolks() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pilgrimage);
        addCard(Zone.BATTLEFIELD, playerB, commando);

        addCard(Zone.HAND, playerA, sleep);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sleep", playerB);
        // no trigger, as Pilgrimage only triggers on Merfolk you control

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount(commando, true, 1);
        assertPermanentCount(playerA, "Merfolk Token", 0);
    }
}
