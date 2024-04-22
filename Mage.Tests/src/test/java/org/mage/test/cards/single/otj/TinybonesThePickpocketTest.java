package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TinybonesThePickpocketTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TinybonesThePickpocket Tinybones, the Pickpocket} {B}
     * Legendary Creature — Skeleton Rogue
     * Deathtouch
     * Whenever Tinybones, the Pickpocket deals combat damage to a player, you may cast target nonland permanent card from that player's graveyard, and mana of any type can be spent to cast that spell.
     * 1/1
     */
    private static final String tinybones = "Tinybones, the Pickpocket";

    @Test
    public void test_CastPermanent_WithOtherType() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tinybones);
        addCard(Zone.GRAVEYARD, playerB, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        attack(1, playerA, tinybones, playerB);
        addTarget(playerA, "Raging Goblin"); // target card for the trigger
        setChoice(playerA, true); // yes to "you may cast"

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertTapped("Swamp", true); // It did cost 1 mana
    }

    @Test
    public void test_NoToCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tinybones);
        addCard(Zone.GRAVEYARD, playerB, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        attack(1, playerA, tinybones, playerB);
        addTarget(playerA, "Raging Goblin"); // target card for the trigger
        setChoice(playerA, false); // no to "you may cast"

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Raging Goblin", 1); // card did not move.
        assertTapped("Swamp", false);
    }
}
