package org.mage.test.cards.single.khm;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MoritteOfTheFrostTest extends CardTestPlayerBase {

    @Test
    public void test_MustBeAnyTypeInHand() {
        // bug: can't cast by mana from Myr Reservoir

        // {T}: Add {C}{C}. Spend this mana only to cast Myr spells or activate abilities of Myr.
        addCard(Zone.BATTLEFIELD, playerA, "Myr Reservoir");
        //
        // Changeling (This card is every creature type.)
        addCard(Zone.HAND, playerA, "Moritte of the Frost");// {2}{G}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // prepare mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");

        // cast myr and remove to to graveyard due 0/0
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Moritte of the Frost", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Moritte of the Frost");
        setChoice(playerA, false); // no copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Moritte of the Frost", 1); // removed to graveyard due 0/0
    }

    @Test
    public void test_MustBeAnyTypeOnBattlefield() {
        // {T}: Add {C}{C}. Spend this mana only to cast Myr spells or activate abilities of Myr.
        addCard(Zone.BATTLEFIELD, playerA, "Myr Reservoir");
        //
        // Changeling (This card is every creature type.)
        addCard(Zone.HAND, playerA, "Moritte of the Frost");// {2}{G}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Minion creatures get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Balthor the Defiled", 1);

        // prepare mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");

        // cast myr and keep on battlefield due boost
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Moritte of the Frost", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Moritte of the Frost");
        setChoice(playerA, false); // no copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Moritte of the Frost", 1); // boosted +1/+1
    }

    @Test
    public void test_MustBeAnyTypeAfterCopy() {
        // {T}: Add {C}{C}. Spend this mana only to cast Myr spells or activate abilities of Myr.
        addCard(Zone.BATTLEFIELD, playerA, "Myr Reservoir");
        //
        // Changeling (This card is every creature type.)
        // You may have Moritte of the Frost enter the battlefield as a copy of a permanent you control,
        // except it's legendary and snow in addition to its other types and, if it's a creature, it enters
        // with two additional +1/+1 counters on it and has changeling.
        addCard(Zone.HAND, playerA, "Moritte of the Frost");// {2}{G}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Minion creatures get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Balthor the Defiled", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2

        // prepare mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");

        // cast myr and copy
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Moritte of the Frost", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Moritte of the Frost");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Grizzly Bears"); // copy target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);

        // boosted by copy and balthor
        Card card = currentGame.getBattlefield().getAllActivePermanents().stream().filter(p -> p.isCopy()).findFirst().orElse(null);
        Assert.assertNotNull("Can't find copy", card);
        Assert.assertEquals("Copy power", 2 + 2 + 1, card.getPower().getValue());
        Assert.assertEquals("Copy Toughness", 2 + 2 + 1, card.getToughness().getValue());
    }
}
