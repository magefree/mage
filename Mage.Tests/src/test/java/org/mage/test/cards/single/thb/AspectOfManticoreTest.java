package org.mage.test.cards.single.thb;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AspectOfManticoreTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AspectOfManticore Aspect of Manticore} {2}{R}
     * Enchantment — Aura
     * Flash
     * Enchant creature
     * When Aspect of Manticore enters the battlefield, enchanted creature gains first strike until end of turn.
     * Enchanted creature gets +2/+0.
     */
    private static final String aspect = "Aspect of Manticore";

    /**
     * bug: trigger doesn't give first strike
     */
    @Test
    public void test_GivesFirstStrikeTemporary() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, aspect);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aspect, "Memnite", true);
        checkPT("Memnite is 3/1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", 3, 1);
        checkAbility("Memnite has first strike", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", FirstStrikeAbility.class, true);

        checkPT("Memnite is 3/1 turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", 3, 1);
        checkAbility("Memnite doesn't have first strike turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", FirstStrikeAbility.class, false);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, aspect, 1);
    }
}
