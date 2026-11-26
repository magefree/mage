package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class WoodlandWeavemasterTest extends CardTestPlayerBase {

    @Test
    public void test_ManaByPower_AnotherElfEnters() {
        // Whenever another Elf you control enters, this creature gets +1/+1 until end of turn.
        // {T}: Add X mana of any one color, where X is this creature's power. Spend this mana only to cast Elf spells
        // and activate abilities of Elf sources.
        addCard(Zone.BATTLEFIELD, playerA, "Woodland Weavemaster"); // 1/2
        //
        addCard(Zone.HAND, playerA, "Llanowar Elves"); // {G}, elf
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Elvish Visionary"); // {1}{G}, elf

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("boosted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Woodland Weavemaster", 1 + 1, 2 + 1);

        // 2 mana from 2 power
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add X mana");
        setChoice(playerA, "Green");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Visionary");
        setChoice(playerA, "When {this} enters, draw a card"); // triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPermanentCount(playerA, "Elvish Visionary", 1);
        assertTapped("Woodland Weavemaster", true);
    }

    @Test
    public void test_SpendOnlyForElves() {
        // {T}: Add X mana of any one color, where X is this creature's power. Spend this mana only to cast Elf spells
        // and activate abilities of Elf sources.
        addCard(Zone.BATTLEFIELD, playerA, "Woodland Weavemaster"); // 1/2
        //
        addCard(Zone.HAND, playerA, "Llanowar Elves"); // {G}, elf spell
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}, non-elf spell
        addCard(Zone.BATTLEFIELD, playerA, "Imperious Perfect"); // elf, {G}, {T}: create elf token
        addCard(Zone.BATTLEFIELD, playerA, "Cudgel Troll"); // non-elf, {G}: regenerate

        checkPlayableAbility("elf spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Llanowar Elves", true);
        checkPlayableAbility("non-elf spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Giant Growth", false);
        checkPlayableAbility("elf ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}: Create a 1/1 green Elf", true);
        checkPlayableAbility("non-elf ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate", false);

        // elf ability
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add X mana");
        setChoice(playerA, "Green");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}: Create a 1/1 green Elf");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Elf Warrior Token", 1);
    }
}
