package org.mage.test.cards.single.war;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class StorrevDevkarinLichTest extends CardTestPlayerBase {

    /**
     * Storrev, Devkarin Lich {1}{B}{B}{G}
     * Legendary Creature — Zombie Elf Wizard 5/4
     * Trample
     * Whenever Storrev deals combat damage to a player or planeswalker, return to your hand target creature or
     * planeswalker card in your graveyard that wasn't put there this combat.
     */
    private static final String storrev = "Storrev, Devkarin Lich";

    @Test
    public void test_DoubleFacedCardDiedThisCombat_CantBeTargeted() {
        addCard(Zone.BATTLEFIELD, playerA, storrev);
        addCard(Zone.BATTLEFIELD, playerA, "Delver of Secrets"); // transforming double-faced, 1/1
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        setChoice(playerA, false); // Delver: no reveal on upkeep

        attack(1, playerA, storrev);
        attack(1, playerA, "Delver of Secrets");
        block(1, playerB, "Grizzly Bears", "Delver of Secrets");
        // Storrev, Devkarin Lich ruling:
        // Storrev's triggered ability can't target any card that was put into your graveyard during this combat
        // phase. This includes creatures and planeswalkers that died from combat damage, ...
        // (2019-05-03)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 5);
        assertGraveyardCount(playerA, "Delver of Secrets", 1); // no target for the trigger
        assertHandCount(playerA, 0);
    }
}
