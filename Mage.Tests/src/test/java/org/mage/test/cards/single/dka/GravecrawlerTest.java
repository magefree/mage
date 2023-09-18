package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class GravecrawlerTest extends CardTestPlayerBase {

  /*
   *  Gravecrawler
   *  Creature — Zombie 2/1, B
   *  Gravecrawler can't block.
   *  You may cast Gravecrawler from your graveyard as long as you control a Zombie.
   */

    @Test
    public void testShouldBeCastable() {
        addCard(Zone.GRAVEYARD, playerA, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerA, "Black Cat");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gravecrawler");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gravecrawler", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testShouldNotBeCastable() {
        addCard(Zone.GRAVEYARD, playerA, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        checkPlayableAbility("befre", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gravecrawler", false);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gravecrawler", 0);
        assertGraveyardCount(playerA, "Gravecrawler", 1);
    }

    /*
     *  Cryptoplasm
     *  Creature — Shapeshifter 2/2, 1UU
     *  At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
     *
     *  Elite Vanguard
     *  Creature — Human Soldier 2/1, W
     *
     *  Fervor English
     *  Enchantment, 2R (3)
     *  Creatures you control have haste. (They can attack and {T} as soon as they come under your control.)
     */
    @Test
    public void testCopiedCantBlockAbilityWorks() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND,        playerA, "Cryptoplasm");
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        addCard(Zone.BATTLEFIELD, playerB, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        addTarget(playerA, "Gravecrawler");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptoplasm");
        attack(3, playerA, "Elite Vanguard");
        block(3, playerB, "Gravecrawler", "Elite Vanguard");

        attack(4, playerB, "Llanowar Elves");
        block(4, playerA, "Gravecrawler", "Llanowar Elves");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gravecrawler", 1);
        assertPermanentCount(playerB, "Gravecrawler", 1);
        assertLife(playerB, 18);
        assertLife(playerA, 19);
    }

    @Test
    public void testCantBlockAbilityAfterChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerB, "Walking Corpse");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Gravecrawler");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Gravecrawler");

        attack(3, playerA, "Elite Vanguard");
        block(3, playerB, "Gravecrawler", "Elite Vanguard");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Gravecrawler", 1);
        assertLife(playerB, 18);
    }
}
