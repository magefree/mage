package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class BuybackTest extends CardTestPlayerBase {

    /**
     * Tests boosting on being blocked
     */
    @Test
    public void testNormal_User() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        setChoice(playerA, true); // use buyback

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertHandCount(playerA, "Elvish Fury", 1);
    }

    @Test
    @Ignore // TODO: enable test after buyback ability will be supported by AI
    public void testNormal_AI() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        //setChoice(playerA, true); // use buyback - AI must choose

        //setStrictChooseMode(true); - AI must choose
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertHandCount(playerA, "Elvish Fury", 1);
    }

    /**
     * It seems that a spell with it's buyback cost paid returned to hand after
     * it fizzled (by failing to target) when it should go to graveyard.
     * <p>
     * "Q: If I pay a spell's buyback cost, but it fizzles, do I get the card
     * back anyway? A: If you pay a buyback cost, you would get the card back
     * during the spell's resolution. So if it never resolves (i.e., something
     * counters it or it fizzles against all of its targets), you don't get the
     * card back."
     */
    @Test
    public void testBuybackSpellFizzles() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Boomerang", "Silvercoat Lion", "Elvish Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Elvish Fury", 0);
        assertGraveyardCount(playerA, "Elvish Fury", 1);
    }

    @Test
    public void testBuybackSpellWasCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Elvish Fury", "Elvish Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Counterspell", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertHandCount(playerA, "Elvish Fury", 0);
        assertGraveyardCount(playerA, "Elvish Fury", 1);
    }

    @Test
    public void test_FreeCast() {
        // bug: buyback doesn't work on free cast (no mana)
        // https://github.com/magefree/mage/issues/4721

        // {1}, {T}: You may cast a nonland card from your hand without paying its mana cost if it has
        // the same name as a spell that was cast this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Twinning Glass", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // Buyback {3} (You may pay an additional 3 as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Reiterate", 1); // Instant  {1}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", (3 + 3) * 3);
        //
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // bolt 1 - cast (R) and copy (RRR), return reiterate with buyback (RRR)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reiterate", "Lightning Bolt");
        setChoice(playerA, true); // use buyback
        setChoice(playerA, false); // same bolt's target
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("bolt 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 - 3 * 2);
        checkHandCardCount("bolt 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reiterate", 1);
        checkPermanentTapped("bolt 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain", true, 1 + 3 + 3);

        // bolt 2 - cast (R) and copy as free cast (R), return reiterate with buyback (RRR)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}:");
        setChoice(playerA, true); // cast for free
        setChoice(playerA, true); // use buyback
        addTarget(playerA, "Lightning Bolt"); // copy target
        setChoice(playerA, false); // same bolt's target
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("bolt 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 - 3 * 2 - 3 * 2);
        checkHandCardCount("bolt 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reiterate", 1);
        checkPermanentTapped("bolt 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain", true, (1 + 3 + 3) + (1 + 1 + 3));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
