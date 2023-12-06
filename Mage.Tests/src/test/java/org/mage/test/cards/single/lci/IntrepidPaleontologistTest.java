package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class IntrepidPaleontologistTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.i.IntrepidPaleontologist} <br>
     * Intrepid Paleontologist {1}{G} <br>
     * Creature - Human Druid <p>
     * {T}: Add one mana of any color. <p>
     * {2}: Exile target card from a graveyard. <p>
     * You may cast Dinosaur creature spells from among cards you own exiled with Intrepid Paleontologist.
     * If you cast a spell this way, that creature enters the battlefield with a finality counter on it.
     **/
    private static final String paleo = "Intrepid Paleontologist";
    private static final String dino = "Frenzied Raptor";
    private static final String dino2 = "Scytheclaw Raptor";

    // You can't cast an enemy dinosaur, but can cast your own and that the dino enters with the finality counter
    @Test
    public void test_basic() {
        addCard(Zone.BATTLEFIELD, playerA, paleo);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2+2+3);
        addCard(Zone.GRAVEYARD, playerA, dino);
        addCard(Zone.GRAVEYARD, playerB, dino2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ",dino);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ",dino2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("Can't cast enemy-owned dino", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast "+dino2, false);
        checkPlayableAbility("Can cast own dino", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast "+dino, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dino, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, dino, 1);
        assertCounterCount(dino, CounterType.FINALITY, 1);
        assertExileCount(dino2, 1);
    }
    // Make sure that re-casting the dino does not come with the counter again
    @Test
    public void test_recast() {
        addCard(Zone.BATTLEFIELD, playerA, paleo);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2+3+1+3);
        addCard(Zone.HAND, playerA, "Unsummon", 1);
        addCard(Zone.GRAVEYARD, playerA, dino);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ",dino);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dino, true);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Unsummon", dino,true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, dino, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, dino, 1);
        assertCounterCount(dino, CounterType.FINALITY, 0);
    }

    // Make sure that having two paleos out doesn't break anything
    @Test
    public void test_double() {
        addCard(Zone.BATTLEFIELD, playerA, paleo, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", (2+3)*2);
        addCard(Zone.GRAVEYARD, playerA, dino);
        addCard(Zone.GRAVEYARD, playerA, dino2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ",dino);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ",dino2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dino, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dino2, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, dino, 1);
        assertPermanentCount(playerA, dino2, 1);
        assertCounterCount(dino, CounterType.FINALITY, 1);
        assertCounterCount(dino2, CounterType.FINALITY, 1);
    }
}
