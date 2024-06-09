package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
/**
 * @author notgreat
 */
public class CostTagCopyCloneTests extends CardTestPlayerBase {

    @Test
    public void KickerETBCountersClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 11);
        addCard(Zone.HAND, playerA, "Aether Figment");
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.HAND, playerA, "Shrivel");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Figment");
        setChoice(playerA, true); // with Kicker
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Aether Figment"); // copy target
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, false);

        // since Clone wasn't kicked, it's a 1/1. Cast Shrivel to easily separate
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shrivel");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Aether Figment", 1);
        assertGraveyardCount(playerA, "Clone", 1);
    }
    @Test
    public void XCountersCopyClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 7);
        addCard(Zone.HAND, playerA, "Endless One");
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.HAND, playerA, "Double Major");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major");
        addTarget(playerA, "Endless One");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Endless One"); // since Clone doesn't copy X, it's a 0/0 and dies
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);


        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Endless One", 2);
        assertGraveyardCount(playerA, "Clone", 1);
    }
    @Test
    public void ETBXClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 4+4);
        addCard(Zone.HAND, playerA, "Defenders of Humanity");
        addCard(Zone.HAND, playerA, "Clever Impersonator");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Defenders of Humanity");
        setChoice(playerA, "X=1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clever Impersonator");
        setChoice(playerA, true);
        setChoice(playerA, "Defenders of Humanity"); //clones don't copy X
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, false);


        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA,"Defenders of Humanity",2);
        assertPermanentCount(playerA,"Astartes Warrior Token",1);
    }
    @Test
    public void ETBXCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 4+1);
        addCard(Zone.HAND, playerA, "Defenders of Humanity");
        addCard(Zone.BATTLEFIELD, playerA, "Overloaded Mage-Ring");

        assertPermanentCount(playerA,"Astartes Warrior Token",0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Defenders of Humanity");
        setChoice(playerA, "X=1");

        activateAbility(1,PhaseStep.PRECOMBAT_MAIN,playerA,
                "{1}, {T}, Sacrifice {this}: Copy target spell you control.",
                "Defenders of Humanity");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA,"Defenders of Humanity",2);
        assertPermanentCount(playerA,"Astartes Warrior Token",2);
    }
}
