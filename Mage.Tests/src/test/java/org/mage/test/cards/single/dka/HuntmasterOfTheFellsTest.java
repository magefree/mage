package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward, noxx
 */
public class HuntmasterOfTheFellsTest extends CardTestPlayerBase {

    /**
     * Huntmaster of the Fells Creature — Human Werewolf 2/2, 2RG (4) Whenever
     * this creature enters the battlefield or transforms into Huntmaster of the
     * Fells, put a 2/2 green Wolf creature token onto the battlefield and you
     * gain 2 life. At the beginning of each upkeep, if no spells were cast last
     * turn, transform Huntmaster of the Fells.
     *
     */
    /**
     * Ravager of the Fells Creature — Werewolf 4/4 Trample Whenever this
     * creature transforms into Ravager of the Fells, it deals 2 damage to
     * target opponent and 2 damage to up to one target creature that player
     * controls. At the beginning of each upkeep, if a player cast two or more
     * spells last turn, transform Ravager of the Fells.
     */

    @Test
    public void test_CantTransformOnSecondTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells");

        // need one turn to transform, but create token and gain 2 life
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Huntmaster of the Fells", 1);
        assertPermanentCount(playerA, "Wolf Token", 1);
        assertPermanentCount(playerA, "Ravager of the Fells", 0);
    }

    @Test
    public void test_TransformOneTime() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells");

        // transform, create token and gain 2 life, make 2 damage to other player
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, "Huntmaster of the Fells", 0);
        assertPermanentCount(playerA, "Wolf Token", 1);
        assertPermanentCount(playerA, "Ravager of the Fells", 1);
    }

    @Test
    public void test_TransformTwoTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        // etb: new token, gain 2 life
        // first transform: make 2 damage to player
        // second transform: new token, gain 2 life
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2 + 2);
        assertLife(playerB, 20 - 2 - 3 * 2);
        assertPermanentCount(playerA, "Huntmaster of the Fells", 1);
        assertPermanentCount(playerA, "Wolf Token", 2);
        assertPermanentCount(playerA, "Ravager of the Fells", 0);
        assertPermanentCount(playerA, "Lightning Bolt", 0);
    }

    @Test
    public void test_TransformTwoTimesWithCreatureDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // etb: new token, gain 2 life
        // first transform: make 2 damage to player and 2 damage to bear
        // second transform: new token, gain 2 life
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2 + 2);
        assertLife(playerB, 20 - 2 - 3 * 2);
        assertPermanentCount(playerA, "Huntmaster of the Fells", 1);
        assertPermanentCount(playerA, "Wolf Token", 2);
        assertPermanentCount(playerA, "Ravager of the Fells", 0);
        assertPermanentCount(playerA, "Lightning Bolt", 0);
        assertPermanentCount(playerB, "Balduvian Bears", 0);
    }
}
