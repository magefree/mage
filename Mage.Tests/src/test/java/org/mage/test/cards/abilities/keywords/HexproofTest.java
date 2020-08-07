

package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class HexproofTest extends CardTestPlayerBase {

    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testOneTargetOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        addCard(Zone.HAND, playerA, "Ranger's Guile");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Into the Void");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
    }

    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testTwoTargetsOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf");
        addCard(Zone.HAND, playerA, "Ranger's Guile");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Into the Void");

        // Return up to two target creatures to their owners' hands.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels^Arbor Elf");
        // Target creature you control gets +1/+1 and gains hexproof until end of turn. (It can't be the target of spells or abilities your opponents control.)
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
        assertPermanentCount(playerA, "Arbor Elf", 0);
    }

    /**
     * Tests hexproof from a color with opponent's spells
     */
    @Test
    public void testHexproofFromColorOpponentSpells() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Murder", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Knight of Grace");
        addCard(Zone.BATTLEFIELD, playerB, "Knight of Malice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Knight of Grace");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Knight of Malice");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Knight of Grace", 1);
        assertPermanentCount(playerB, "Knight of Malice", 0);
    }

    /**
     * Tests hexproof from a color with controller's spells
     */
    @Test
    public void testHexproofFromColorOwnSpells() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Knight of Grace");
        addCard(Zone.HAND, playerA, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Knight of Grace");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Knight of Grace", 0);
    }
}
