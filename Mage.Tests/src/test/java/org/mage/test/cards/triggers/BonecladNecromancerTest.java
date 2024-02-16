package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.b.BonecladNecromancer Boneclad Necromancer}
 * P/T 3/3
 * {3}{B}{B}
 * Creature — Human Wizard
 * When Boneclad Necromancer enters the battlefield, you may exile target creature card from a graveyard.
 * If you do, create a 2/2 black Zombie creature token.
 *
 *
 * Added this test class to test issue #5875
 * https://github.com/magefree/mage/issues/5875
 *
 * Boneclad Necromancer could exile non-creature cards from the graveyard to get his 2/2 Zombie.
 *
 * @author jgray1206
 */
public class BonecladNecromancerTest extends CardTestPlayerBase {

    @Test
    public void testBonecladNecromancerCanExileCreaturesFromOwnGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Boneclad Necromancer", 1);
        addCard(Zone.GRAVEYARD, playerA, "Raptor Hatchling", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boneclad Necromancer");
        addTarget(playerA,"Raptor Hatchling");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Boneclad Necromancer", 1);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertExileCount(playerA, "Raptor Hatchling", 1);
        assertGraveyardCount(playerA, "Raptor Hatchling", 0);
    }

    @Test
    public void testBonecladNecromancerCanExileCreaturesFromOtherGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Boneclad Necromancer", 1);
        addCard(Zone.GRAVEYARD, playerB, "Raptor Hatchling", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boneclad Necromancer");
        addTarget(playerA,"Raptor Hatchling");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Boneclad Necromancer", 1);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertExileCount(playerB, "Raptor Hatchling", 1);
        assertGraveyardCount(playerB, "Raptor Hatchling", 0);
    }

    @Test
    public void testBonecladNecromancerCantExileNonCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Boneclad Necromancer", 1);
        addCard(Zone.GRAVEYARD, playerA, "Feral Invocation", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boneclad Necromancer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Boneclad Necromancer", 1);
        assertPermanentCount(playerA, "Zombie Token", 0);
        assertExileCount(playerA, "Feral Invocation", 0);
        assertGraveyardCount(playerA, "Feral Invocation", 1);

    }

}
