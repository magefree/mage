package org.mage.test.cards.rules;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_googlemail.com, JayDi85
 */
public class AlternativeCostRuleTest extends CardTestPlayerBase {

    @Test
    public void test_DisplayedText() {
        addCard(Zone.GRAVEYARD, playerA, "Firewild Borderpost");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Card firewildBorderpost = playerA.getGraveyard().getCards(currentGame).iterator().next();
        boolean found = false;
        for (String rule : firewildBorderpost.getRules(currentGame)) {
            if (rule.startsWith("You may pay")) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Couldn't find rule text for alternative cost on a card: " + firewildBorderpost.getName(), found);
    }

    @Test
    public void test_PayLife() {
        // You may pay 1 life and exile a black card from your hand rather than pay this spell’s mana cost.
        // Distribute two -2/-1 counters among one or two target creatures.
        addCard(Zone.HAND, playerA, "Contagion"); // {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Arrogant Vampire", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Bronze Dragon"); // 7/7

        // cast alternative
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Contagion");
        setChoice(playerA, "Cast with alternative cost: Pay 1 life");
        addTargetAmount(playerA, "Ancient Bronze Dragon", 2);
        setChoice(playerA, "Arrogant Vampire"); // to pay discard cost

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Ancient Bronze Dragon", 7 - 2 * 2, 7 - 1 * 2); // from x1 boost
    }

    @Test
    public void test_AlternativeCostSourceAbility_OneCardMustNotAffectAnother() {
        // You may pay {W}{U}{B}{R}{G} rather than pay Bringer of the Black Dawn's mana cost.
        addCard(Zone.HAND, playerA, "Bringer of the Black Dawn"); // {7}{B}{B}
        //
        addCard(Zone.HAND, playerA, "Alpha Tyrranax"); // {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Forest");

        // alpha don't have mana
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bringer of the Black Dawn", true);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alpha Tyrranax", false);

        // add additional mana for alpha to cast
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest");
        checkPlayableAbility("after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Bringer of the Black Dawn", true);
        checkPlayableAbility("after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Alpha Tyrranax", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AlternativeCostSourceAbility_PlayerAlternativeCostMustAffectAllCards() {
        // You may pay {W}{U}{B}{R}{G} rather than pay the mana cost for spells that you cast.
        addCard(Zone.HAND, playerA, "Fist of Suns"); // {3}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.HAND, playerA, "Alpha Tyrranax"); // {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Forest");

        // alpha don't have mana
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fist of Suns", true);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alpha Tyrranax", false);

        // add artifact and allows wubrg cost for alpha
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fist of Suns");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fist of Suns", 1);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alpha Tyrranax", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
