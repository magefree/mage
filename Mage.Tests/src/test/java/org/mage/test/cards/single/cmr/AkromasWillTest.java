package org.mage.test.cards.single.cmr;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */

public class AkromasWillTest extends CardTestCommanderDuelBase {

    @Test
    public void test_OneMode() {
        // https://github.com/magefree/mage/issues/7210

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        // * Creatures you control gain flying, vigilance, and double strike until end of turn.
        // * Creatures you control gain lifelink, indestructible, and protection from all colors until end of turn.
        addCard(Zone.HAND, playerA, "Akroma's Will", 1); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Kitesail Corsair", 1);

        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", FlyingAbility.class, false);
        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", LifelinkAbility.class, false);

        // cast and use ONE mode only
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma's Will");
        setModeChoice(playerA, "1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", FlyingAbility.class, true);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", LifelinkAbility.class, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MultiModesOnCommander() {
        // https://github.com/magefree/mage/issues/7210

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        // * Creatures you control gain flying, vigilance, and double strike until end of turn.
        // * Creatures you control gain lifelink, indestructible, and protection from all colors until end of turn.
        addCard(Zone.HAND, playerA, "Akroma's Will", 1); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Kitesail Corsair", 1);

        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", FlyingAbility.class, false);
        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", LifelinkAbility.class, false);

        // prepare commander
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("commander on battle", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // cast and use two modes instead one
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma's Will");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", FlyingAbility.class, true);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", LifelinkAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
