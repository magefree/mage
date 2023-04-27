package org.mage.test.cards.abilities.activated;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ReconfigureTest extends CardTestPlayerBase {

    private static final String lion = "Silvercoat Lion";
    private static final String boar = "Bronzeplate Boar";
    private static final String aid = "Sigarda's Aid";
    private static final String paladin = "Puresteel Paladin";
    private static final String relic = "Darksteel Relic";

    @Test
    public void testAttach() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, boar);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reconfigure", lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(boar, CardType.CREATURE, false);
        assertSubtype(boar, SubType.EQUIPMENT);
        assertIsAttachedTo(playerA, boar, lion);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 2);
        assertAbility(playerA, lion, TrampleAbility.getInstance(), true);
    }

    @Test
    public void testAttachDetach() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, boar);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reconfigure", lion);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{5}:");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(boar, CardType.CREATURE, true);
        assertSubtype(boar, SubType.EQUIPMENT);
        assertPowerToughness(playerA, lion, 2, 2);
        assertAbility(playerA, lion, TrampleAbility.getInstance(), false);
    }

    @Test
    public void testSigardasAid() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, aid);
        addCard(Zone.HAND, playerA, boar);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, boar);
        addTarget(playerA, lion);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(boar, CardType.CREATURE, false);
        assertSubtype(boar, SubType.EQUIPMENT);
        assertIsAttachedTo(playerA, boar, lion);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 2);
        assertAbility(playerA, lion, TrampleAbility.getInstance(), true);
    }

    @Test
    public void testPuresteelPaladin() {
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, boar);
        addCard(Zone.BATTLEFIELD, playerA, paladin);
        addCard(Zone.BATTLEFIELD, playerA, relic, 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(boar, CardType.CREATURE, false);
        assertSubtype(boar, SubType.EQUIPMENT);
        assertIsAttachedTo(playerA, boar, lion);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 2);
        assertAbility(playerA, lion, TrampleAbility.getInstance(), true);
    }
}
