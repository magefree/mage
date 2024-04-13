package org.mage.test.cards.single.mkm;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AirtightAlibiTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AirtightAlibi Airtight Alibi} {2}{G}
     * Enchantment — Aura
     * Flash
     * Enchant creature
     * When Airtight Alibi enters the battlefield, untap enchanted creature. It gains hexproof until end of turn. If it’s suspected, it’s no longer suspected.
     * Enchanted creature gets +2/+2 and can’t become suspected.
     */
    private static final String alibi = "Airtight Alibi";

    /**
     * bug: trigger doesn't give hexproof
     */
    @Test
    public void test_GivesHexproofTemporary() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, alibi);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alibi, "Memnite", true);
        checkPT("Memnite is 3/3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", 3, 3);
        checkAbility("Memnite has Hexproof", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", HexproofAbility.class, true);

        checkPT("Memnite is 3/3 turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", 3, 3);
        checkAbility("Memnite doesn't have Hexproof turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", HexproofAbility.class, false);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, alibi, 1);
    }
}
