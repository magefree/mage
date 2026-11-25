package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class LokiTheDeceiverTest extends CardTestPlayerBase {

    /**
     * Loki, the Deceiver {1}{U}{B}{R}
     * Legendary Creature - God Sorcerer Villain 4/4
     * Whenever Loki attacks, create a tapped and attacking token that's a copy of another target Villain you control,
     * except it isn't legendary and it's an Illusion in addition to its other types.
     * Sacrifice that token at the beginning of the next end step.
     * Whenever one or more Villains you control deal combat damage to a player, draw a card.
     */
    private static final String loki = "Loki, the Deceiver";

    private static final String ghost = "Ghost, Spectral Saboteur"; // legendary Villain 2/2, can't be blocked
    private static final String grapplers = "Glamorous Grapplers"; // Villain 3/3 menace
    private static final String bears = "Grizzly Bears"; // 2/2, not a Villain

    private Permanent getCreature(String name, boolean isToken) {
        for (Permanent permanent : currentGame.getBattlefield().getActivePermanents(playerA.getId(), currentGame)) {
            if (name.equals(permanent.getName()) && (permanent instanceof PermanentToken) == isToken) {
                return permanent;
            }
        }
        return null;
    }

    @Test
    public void test_TokenCopyAttackingNotLegendaryIllusion() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, ghost);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);

        attack(1, playerA, loki);
        addTarget(playerA, ghost);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // the original stays: the copy isn't legendary, so no legend rule
        assertPermanentCount(playerA, ghost, 2);
        assertTokenCount(playerA, ghost, 1);
        Permanent token = getCreature(ghost, true);
        Assert.assertNotNull(token);
        Assert.assertFalse("token must not be legendary", token.isLegendary(currentGame));
        Assert.assertTrue("token must be an Illusion", token.hasSubtype(SubType.ILLUSION, currentGame));
        Assert.assertTrue("token keeps the copied types", token.hasSubtype(SubType.VILLAIN, currentGame));
        Assert.assertTrue(token.isTapped());
        Permanent original = getCreature(ghost, false);
        Assert.assertNotNull(original);
        Assert.assertTrue(original.isLegendary(currentGame));
        Assert.assertFalse(original.hasSubtype(SubType.ILLUSION, currentGame));
        Assert.assertFalse("original didn't attack", original.isTapped());

        // Loki 4 + token 2, then one draw
        assertLife(playerB, 20 - 4 - 2);
        assertHandCount(playerA, bears, 1);
    }

    @Test
    public void test_TokenSacrificedAtEndStep() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, ghost);

        attack(1, playerA, loki);
        addTarget(playerA, ghost);

        checkPermanentCount("token before end step", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, ghost, 2);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, ghost, 1);
        assertTokenCount(playerA, ghost, 0);
        Assert.assertNotNull(getCreature(ghost, false));
    }

    @Test
    public void test_CantTargetItself() {
        setStrictChooseMode(true);

        // no other Villain: Loki itself and a non-Villain aren't legal targets, so the trigger does nothing
        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, bears);

        attack(1, playerA, loki);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, loki, 1);
        assertTokenCount(playerA, loki, 0);
        assertPermanentCount(playerA, bears, 1);
        assertTokenCount(playerA, bears, 0);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_DrawOnceForSeveralVillains() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, ghost);
        addCard(Zone.BATTLEFIELD, playerA, grapplers);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears, 3);

        // Loki, Ghost, Grapplers and the token copy of Grapplers all deal combat damage at once
        attack(1, playerA, loki);
        attack(1, playerA, ghost);
        attack(1, playerA, grapplers);
        addTarget(playerA, grapplers);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4 - 2 - 3 - 3);
        assertHandCount(playerA, 1);
        assertLibraryCount(playerA, 2);
    }

    @Test
    public void test_NonVillainDamageDoesNotDraw() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);

        attack(1, playerA, bears);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 0);
        assertLibraryCount(playerA, 1);
    }
}
