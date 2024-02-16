
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.128. Embalm
 * 702.128a Embalm is an activated ability that functions while the card with embalm is in a graveyard.
 *          “Embalm [cost]” means
 *          “[Cost], Exile this card from your graveyard: Create a token that’s a copy of this card, except it’s white, it has no mana cost, and it’s a Zombie in addition to its other types. Activate only as a sorcery.”
 * 702.128b A token is “embalmed” if it’s created by a resolving embalm ability.
 *
 * @author noxx
 */
public class EmbalmTest extends CardTestPlayerBase {

    /*
     * Tests that Embalm doesn't affect card to be case as usual card with ETB trigger
     */
    @Test
    public void testCreatureWithEmbalmJustCastAndTarget() {

        /*
        Angel of Sanctions {3}{W}{W}
        Creature - Angel
        Flying
        When Angel of Sanctions enters the battlefield, you may exile target nonland permanent an opponent controls until Angel of Sanctions leaves the battlefield.
        Embalm {5}{W}
         */
        String aSanctions = "Angel of Sanctions"; // {W} 3/4
        String yOx = "Yoked Ox"; // {W} 0/4
        String wKnight = "White Knight"; // {W}{W} 2/2

        addCard(Zone.HAND, playerA, aSanctions);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        addCard(Zone.BATTLEFIELD, playerB, yOx);
        addCard(Zone.BATTLEFIELD, playerB, wKnight);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aSanctions);
        addTarget(playerA, yOx);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, aSanctions, 1);
        assertPermanentCount(playerB, yOx, 0);
        assertPermanentCount(playerB, wKnight, 1);
    }

    /*
     * Tests that creature after using Embalm will be able to use ETB effect from original card
     */
    @Test
    public void testCreatureETBAfterEmbalm() {

        /*
        Angel of Sanctions {3}{W}{W}
        Creature - Angel
        Flying
        When Angel of Sanctions enters the battlefield, you may exile target nonland permanent an opponent controls until Angel of Sanctions leaves the battlefield.
        Embalm {5}{W}
         */
        String aSanctions = "Angel of Sanctions"; // {3}{W}{W} 3/4
        String yOx = "Yoked Ox"; // {W} 0/4
        String wKnight = "White Knight"; // {W}{W} 2/2
        String dBlade = "Doom Blade"; // {1}{B}

        addCard(Zone.HAND, playerA, aSanctions);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 11);

        addCard(Zone.HAND, playerB, dBlade);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, yOx);
        addCard(Zone.BATTLEFIELD, playerB, wKnight);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aSanctions);
        addTarget(playerA, yOx);
        setChoice(playerA, true);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, dBlade);
        addTarget(playerB, aSanctions);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Embalm");
        addTarget(playerA, wKnight);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, 0);
        assertPermanentCount(playerA, aSanctions, 1);
        assertPermanentCount(playerB, yOx, 1);
        assertPermanentCount(playerB, wKnight, 0);
        assertGraveyardCount(playerA, aSanctions, 0);
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/3144
     * Tests that not only creature targeted by original creature is returned.
     * After using Embalm creature will exile another creature and should return it back when leaves battlefield.
     */
    @Test
    public void testCreatureExiledByEmbalmCreatureReturns() {

        /*
        Angel of Sanctions {3}{W}{W}
        Creature - Angel
        Flying
        When Angel of Sanctions enters the battlefield, you may exile target nonland permanent an opponent controls until Angel of Sanctions leaves the battlefield.
        Embalm {5}{W}
         */
        String aSanctions = "Angel of Sanctions"; // {3}{W}{W} 3/4
        String yOx = "Yoked Ox"; // {W} 0/4
        String wKnight = "White Knight"; // {W}{W} 2/2
        String dBlade = "Doom Blade"; // {1}{B}

        addCard(Zone.HAND, playerA, aSanctions);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 11);

        addCard(Zone.HAND, playerB, dBlade, 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, yOx);
        addCard(Zone.BATTLEFIELD, playerB, wKnight);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aSanctions);
        addTarget(playerA, yOx);
        setChoice(playerA, true);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, dBlade, aSanctions);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Embalm");
        addTarget(playerA, wKnight);
        setChoice(playerA, true);

        castSpell(1, PhaseStep.END_TURN, playerB, dBlade, aSanctions);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertHandCount(playerB, 0);
        assertPermanentCount(playerA, aSanctions, 0);
        assertPermanentCount(playerB, yOx, 1);
        // second creature should also return after embalm token leaves battlefield
        assertPermanentCount(playerB, wKnight, 1);
        assertGraveyardCount(playerA, aSanctions, 0);
        assertGraveyardCount(playerB, dBlade, 2);
    }
}
