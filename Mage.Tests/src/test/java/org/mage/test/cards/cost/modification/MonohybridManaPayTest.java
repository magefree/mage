package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MonohybridManaPayTest extends CardTestPlayerBase {

    @Test
    public void test_PaySimpleMana_Manually() {
        // simulate user click on mana pool icons
        disableManaAutoPayment(playerA);

        addCard(Zone.HAND, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // fill mana pool
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
        // cast spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        // unlock mana order in pool
        setChoice(playerA, "Green");
        setChoice(playerA, "Black");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_PayMonohybridMana_ColorPart_SameUnlockOrder() {
        // simulate user click on mana pool icons
        // mono hybrid can be paid by color or {2}
        disableManaAutoPayment(playerA);

        // Reaper King
        addCard(Zone.HAND, playerA, "Reaper King"); // {2/W}{2/U}{2/B}{2/R}{2/G}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // fill mana pool
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        // cast spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reaper King");
        // unlock mana order in pool (SAME ORDER AS PAYMENT - {W}{U}{B}{R}{G})
        setChoice(playerA, "White");
        setChoice(playerA, "Blue");
        setChoice(playerA, "Black");
        setChoice(playerA, "Red");
        setChoice(playerA, "Green");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Reaper King", 1);
    }

    @Test
    public void test_PayMonohybridMana_ColorPart_AnyUnlockOrder() {
        // simulate user click on mana pool icons
        // mono hybrid can be paid by color or {2}
        disableManaAutoPayment(playerA);

        // Reaper King
        addCard(Zone.HAND, playerA, "Reaper King"); // {2/W}{2/U}{2/B}{2/R}{2/G}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // fill mana pool
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        // cast spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reaper King");
        // unlock mana order in pool (DIFFERENT ORDER AS PAYMENT - {R}{G}{W}{U}{B})
        setChoice(playerA, "Red");
        setChoice(playerA, "Green");
        setChoice(playerA, "White");
        setChoice(playerA, "Blue");
        setChoice(playerA, "Black");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Reaper King", 1);
    }

    @Test
    public void test_PayMonohybridMana_GenericPart_AnyUnlockOrder() {
        // simulate user click on mana pool icons
        // mono hybrid must be payed as color first
        disableManaAutoPayment(playerA); // must pay by mana unlock command (like human clicks on mana pool icons)

        // Reaper King
        addCard(Zone.HAND, playerA, "Reaper King"); // {2/W}{2/U}{2/B}{2/R}{2/G}
        //addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); -- white mana paid by {2}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 + 2 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); -- red mana paid by {2}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // fill mana pool
        //activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}"); -- paid by {2}
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 5); // pay for {U}, 2/W and 2/R
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
        //activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}"); -- paid by {2}
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        // cast spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reaper King");
        // unlock mana order in pool (ANY ORDER)
        setChoice(playerA, "Black");
        setChoice(playerA, "Green");
        setChoice(playerA, "Blue", 5); // unlocks and pays for U, 2/W and 2/R // TODO: add support to pay one by one, not all

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Reaper King", 1);
    }
}
