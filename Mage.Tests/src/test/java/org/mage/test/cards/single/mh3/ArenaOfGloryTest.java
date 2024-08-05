package org.mage.test.cards.single.mh3;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ArenaOfGloryTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.ArenaOfGlory Arena of Glory}
     * Land
     * Arena of Glory enters the battlefield tapped unless you control a Mountain.
     * {T}: Add {R}.
     * {R}, {T}, Exert Arena of Glory: Add {R}{R}. If that mana is spent on a creature spell, it gains haste until end of turn. (An exerted permanent won’t untap during your next untap step.)
     */
    private static final String arena = "Arena of Glory";

    @Test
    public void test_NormalManaNoHaste() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Dwarven Trader"); // vanilla for {R}
        addCard(Zone.BATTLEFIELD, playerA, arena);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader", true);
        checkAbility("Dwarven Trader doesn't have haste", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader", HasteAbility.class, false);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(arena, false);
        assertPermanentCount(playerA, "Dwarven Trader", 1);
        assertAbility(playerA, "Dwarven Trader", HasteAbility.getInstance(), false);
    }

    @Test
    public void test_TwoCreatureHaste() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Dwarven Trader"); // vanilla for {R}
        addCard(Zone.HAND, playerA, "Mons's Goblin Raiders"); // vanilla for {R}
        addCard(Zone.BATTLEFIELD, playerA, arena);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mons's Goblin Raiders", true);
        checkAbility("Dwarven Trader has haste", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader", HasteAbility.class, true);
        checkAbility("Mons's Goblin Raiders has haste", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mons's Goblin Raiders", HasteAbility.class, true);

        attack(3, playerA, "Dwarven Trader", playerB);
        attack(3, playerA, "Mons's Goblin Raiders", playerB);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(arena, true); // exerted
        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, "Dwarven Trader", 1);
        assertAbility(playerA, "Dwarven Trader", HasteAbility.getInstance(), false);
        assertPermanentCount(playerA, "Mons's Goblin Raiders", 1);
        assertAbility(playerA, "Mons's Goblin Raiders", HasteAbility.getInstance(), false);
    }
}
