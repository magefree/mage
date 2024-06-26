package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheCreationOfAvacynTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheCreationOfAvacyn The Creation of Avacyn} {1}{B}{B}
     * Enchantment — Saga
     * (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
     * I — Search your library for a card, exile it face down, then shuffle.
     * II — Turn the exiled card face up. If it’s a creature card, you lose life equal to its mana value.
     * III — You may put the exiled card onto the battlefield if it’s a creature card. If you don’t put it onto the battlefield, put it into its owner’s hand.
     */
    private static final String creation = "The Creation of Avacyn";

    @Test
    public void test_Creature_PutIntoPlay() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, creation);
        addCard(Zone.LIBRARY, playerA, "Avacyn, Angel of Hope");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creation);
        addTarget(playerA, "Avacyn, Angel of Hope");

        setChoice(playerA, true); // put Avacyn into play

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 8);
        assertPermanentCount(playerA, "Avacyn, Angel of Hope", 1);
    }

    @Test
    public void test_Creature_DontPutIntoPlay() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, creation);
        addCard(Zone.LIBRARY, playerA, "Avacyn, Angel of Hope");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creation);
        addTarget(playerA, "Avacyn, Angel of Hope");

        setChoice(playerA, false); // put Avacyn into play

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 8);
        assertPermanentCount(playerA, "Avacyn, Angel of Hope", 0);
        assertHandCount(playerA, "Avacyn, Angel of Hope", 1);
    }

    @Test
    public void test_NotCreature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, creation);
        addCard(Zone.LIBRARY, playerA, "Helvault");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creation);
        addTarget(playerA, "Helvault");

        // No choice on III

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertHandCount(playerA, "Helvault", 1);
    }

    @Test
    public void test_StrionicResonator_DoubleCreature() {
        //setStrictChooseMode(true); // targetting the first saga ability is difficult in test

        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Strionic Resonator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, creation);
        addCard(Zone.LIBRARY, playerA, "Avacyn, Angel of Hope");
        addCard(Zone.LIBRARY, playerA, "Griselbrand");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creation);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // Creation resolve, trigger on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        addTarget(playerA, "Avacyn, Angel of Hope");
        addTarget(playerA, "Griselbrand");

        setChoice(playerA, true);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 8 - 8);
        assertPermanentCount(playerA, "Avacyn, Angel of Hope", 1);
        assertPermanentCount(playerA, "Griselbrand", 1);
    }

    @Test
    public void test_StrionicResonator_CreatureAndPermanent() {
        //setStrictChooseMode(true); // targetting the first saga ability is difficult in test

        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Strionic Resonator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, creation);
        addCard(Zone.LIBRARY, playerA, "Avacyn, Angel of Hope");
        addCard(Zone.LIBRARY, playerA, "Helvault");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creation);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // Creation resolve, trigger on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        addTarget(playerA, "Avacyn, Angel of Hope");
        addTarget(playerA, "Helvault");

        setChoice(playerA, true);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 8 - 3);
        assertPermanentCount(playerA, "Avacyn, Angel of Hope", 1);
        assertPermanentCount(playerA, "Helvault", 1);
    }

    @Test
    public void test_StrionicResonator_CreatureAndSpell() {
        //setStrictChooseMode(true); // targetting the first saga ability is difficult in test

        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Strionic Resonator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, creation);
        addCard(Zone.LIBRARY, playerA, "Avacyn, Angel of Hope");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creation);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // Creation resolve, trigger on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        addTarget(playerA, "Avacyn, Angel of Hope");
        addTarget(playerA, "Lightning Bolt");

        setChoice(playerA, true);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 8 - 1);
        assertPermanentCount(playerA, "Avacyn, Angel of Hope", 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
    }
}
