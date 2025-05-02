package org.mage.test.cards.single.otc;

import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class VihaanGoldwakerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.VihaanGoldwaker Vihaan, Goldwaker} {R}{W}{B}
     * Legendary Creature — Dwarf Warlock
     * Other outlaws you control have vigilance and haste. (Assassins, Mercenaries, Pirates, Rogues, and Warlocks are outlaws.)
     * At the beginning of combat on your turn, you may have Treasures you control become 3/3 Construct Assassin artifact creatures in addition to their other types until end of turn.
     * 3/3
     */
    private static final String vihaan = "Vihaan, Goldwaker";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, vihaan);
        addCard(Zone.BATTLEFIELD, playerA, "Mimic"); // Treasure

        setChoice(playerA, true); // Yes to the "you may" trigger from Vihaan

        // From the trigger
        checkPT("check mimic PT", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", 3, 3);
        checkType("check mimic Artifact", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", CardType.ARTIFACT, true);
        checkType("check mimic Creature", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", CardType.CREATURE, true);
        checkSubType("check mimic Treasure", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", SubType.TREASURE, true);
        checkSubType("check mimic Construct", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", SubType.CONSTRUCT, true);
        checkSubType("check mimic Assassin", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", SubType.ASSASSIN, true);

        // From the static boosting outlaws
        checkAbility("check mimic Vigilance", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", VigilanceAbility.class, true);
        checkAbility("check mimic Haste", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mimic", HasteAbility.class, true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}, Sacrifice"); // still has Treasure ability.
        setChoice(playerA, "Red"); // choose mana color.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Mimic", 1);
    }
}
