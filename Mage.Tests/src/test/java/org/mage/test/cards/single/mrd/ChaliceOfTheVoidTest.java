package org.mage.test.cards.single.mrd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ChaliceOfTheVoidTest extends CardTestPlayerBase {

    /**
     * Theres a Chalice of the Void with 1 counter in play under my control.
     * Then I cast second chalice with x=1. For spells on the stack the cmc is
     * the base CMC + X value * {X} in casting costs on top right of card. So
     * cmc should be 2 in this case, it shouldnt be countered.
     * http://boardgames.stackexchange.com/questions/7327/what-is-the-converted-mana-cost-of-a-spell-with-x-when-cast-with-the-miracle-m
     */
    @Test
    public void testX1CountsFor2CMC() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // Chalice of the Void enters the battlefield with X charge counters on it.
        // Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.
        addCard(Zone.HAND, playerA, "Chalice of the Void", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void", true);
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chalice of the Void", 2);
    }

    /*
    If X=1 the cmc of Chalice on the stack is 2. So it can't be countered by Mental Misstep
     */
    @Test
    public void testCantBeCounteredByMentalMisstep() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Chalice of the Void", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Counter target spell with converted mana cost 1.
        addCard(Zone.HAND, playerB, "Mental Misstep", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");
        checkPlayableAbility("can't Mental Misstep", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Mental", false);
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mental Misstep", "Chalice of the Void", "Chalice of the Void");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        // assertAllCommandsUsed(); // cast Mental Misstep must be ignored

        assertHandCount(playerB, "Mental Misstep", 1); // cannot be cast because no legal target exists
        assertPermanentCount(playerA, "Chalice of the Void", 1); // was not countered

    }

    /*
        Conflagurate flashed back for X >= 0 should not be countered by chalice.
     */
    @Test
    public void testConflagrateFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Chalice of the Void", 1);

        // Conflagrate deals X damage divided as you choose among any number of target creatures and/or players.
        // Flashback-{R}{R}, Discard X cards.
        addCard(Zone.GRAVEYARD, playerB, "Conflagrate", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flashback {R}{R}");
        setChoice(playerB, "X=1");
        addTargetAmount(playerB, playerA, 1);
        setChoice(playerB, "Mountain"); // discard 1 card

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Conflagrate", 1);
        //TODO: Apparently there are two mountains in the graveyard at the end of the test now.
        assertGraveyardCount(playerB, "Mountain", 1);

        assertLife(playerA, 19);
        assertLife(playerB, 20);
    }

}
