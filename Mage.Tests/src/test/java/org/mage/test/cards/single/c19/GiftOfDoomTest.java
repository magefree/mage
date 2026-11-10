package org.mage.test.cards.single.c19;

import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GiftOfDoomTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GiftOfDoom}
     * <p>
     * {4}{B} Enchantment - Aura<br>
     * Enchant creature<br>
     * Enchanted creature has deathtouch and indestructible.<br>
     * Morph—Sacrifice another creature.<br>
     * As Gift of Doom is turned face up, you may attach it to a creature.
     */
    private static final String GIFT_OF_DOOM = "Gift of Doom";

    @Test
    public void test_TurnFaceUpAndAttach() {
        addCard(Zone.HAND, playerA, GIFT_OF_DOOM);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3); // face down cast for {3}
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, GIFT_OF_DOOM + " using Morph");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice another creature: Turn this face-down permanent face up.");
        setChoice(playerA, "Silvercoat Lion"); // creature to sacrifice
        setChoice(playerA, true); // attach it
        setChoice(playerA, "Grizzly Bears"); // creature to attach to

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 0);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertAttachedTo(playerA, GIFT_OF_DOOM, "Grizzly Bears", true);
        assertAbility(playerA, "Grizzly Bears", DeathtouchAbility.getInstance(), true);
        assertAbility(playerA, "Grizzly Bears", IndestructibleAbility.getInstance(), true);
    }

    @Test
    public void test_TurnFaceUpAndAttach_ThenReturnFromGraveyard() {
        addCard(Zone.HAND, playerA, GIFT_OF_DOOM);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3); // face down cast for {3}
        //
        addCustomEffect_TargetDestroy(playerA);
        // Return all enchantment cards from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Replenish"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // human 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, GIFT_OF_DOOM + " using Morph");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice another creature: Turn this face-down permanent face up.");
        setChoice(playerA, "Silvercoat Lion"); // creature to sacrifice
        setChoice(playerA, true); // attach it
        setChoice(playerA, "Grizzly Bears"); // creature to attach to
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", GIFT_OF_DOOM);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // the returned aura chooses a creature again
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Replenish");
        setChoice(playerA, "Elite Vanguard"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, GIFT_OF_DOOM, "Elite Vanguard", true);
        assertAttachedTo(playerA, GIFT_OF_DOOM, "Grizzly Bears", false);
        assertAbility(playerA, "Elite Vanguard", IndestructibleAbility.getInstance(), true);
    }
}
