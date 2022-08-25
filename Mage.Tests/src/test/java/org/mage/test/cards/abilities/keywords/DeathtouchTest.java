
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DeathtouchTest extends CardTestPlayerBase {

    @Test
    public void simpleDeathtouchDuringCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Archangel of Thune");
        // Creature - Rat     1/1
        // Deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Typhoid Rats");

        attack(2, playerB, "Typhoid Rats");
        block(2, playerA, "Archangel of Thune", "Typhoid Rats");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Archangel of Thune", 1);
        assertGraveyardCount(playerB, "Typhoid Rats", 1);
    }

    /**
     * Checks if a creature getting damage from Marath abilitity dies from
     * Deathtouch, if Marath is equiped with Deathtouch giving Equipment and
     * Marath dies from removing the +1/+1 counters.
     */
    @Test
    public void testMarathWillOfTheWild() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        // Equipped creature has deathtouch and lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Basilisk Collar");
        /*
         {R}{G}{W} Legendary Creature - Elemental Beast
         Marath, Will of the Wild enters the battlefield with a number of +1/+1 counters on
         it equal to the amount of mana spent to cast it.
         {X}, Remove X +1/+1 counters from Marath: Choose one -
         * Put X +1/+1 counters on target creature
         * Marath deals X damage to any target
         * Put an X/X green Elemental creature token onto the battlefield. X can't be 0
         */
        addCard(Zone.HAND, playerA, "Marath, Will of the Wild", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Archangel of Thune");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Marath, Will of the Wild");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Marath, Will of the Wild", "Marath, Will of the Wild", StackClause.WHILE_NOT_ON_STACK);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X}, Remove X +1/+1 counters from Marath", "Archangel of Thune");
        setChoice(playerA, "X=3");
        setModeChoice(playerA, "2"); // Marath deals X damage to any target

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Marath, Will of the Wild", 0); // died because all +1/+1 counters are removed
        assertPermanentCount(playerB, "Archangel of Thune", 0); // died from deathtouch

        assertLife(playerA, 23); // +3 from lifelink doing 3 damage with Marath to Archangel
        assertLife(playerB, 20);

    }

    @Test
    public void testMarathWillOfTheWildEleshNorn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        // Equipped creature has deathtouch and lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Basilisk Collar");
        /*
         {R}{G}{W} Legendary Creature - Elemental Beast
         Marath, Will of the Wild enters the battlefield with a number of +1/+1 counters on
         it equal to the amount of mana spent to cast it.
         {X}, Remove X +1/+1 counters from Marath: Choose one -
         * Put X +1/+1 counters on target creature
         * Marath deals X damage to any target
         * Put an X/X green Elemental creature token onto the battlefield. X can't be 0
         */
        addCard(Zone.HAND, playerA, "Marath, Will of the Wild", 1);

        // Vigilance
        // Other creatures you control get +2/+2.
        // Creatures your opponents control get -2/-2.
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Grand Cenobite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Marath, Will of the Wild", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Marath, Will of the Wild");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X}, Remove X +1/+1 counters from Marath", "Elesh Norn, Grand Cenobite");
        setModeChoice(playerA, "2"); // Marath deals X damage to any target
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Marath, Will of the Wild", 0); // died because he's 0/0
        assertPermanentCount(playerB, "Elesh Norn, Grand Cenobite", 0); // died from deathtouch

        assertLife(playerA, 21); // +1 from lifelink doing 1 damage with Marath to Elesh Norn
        assertLife(playerB, 20);

    }
}
