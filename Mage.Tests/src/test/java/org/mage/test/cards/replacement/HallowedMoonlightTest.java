package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class HallowedMoonlightTest extends CardTestPlayerBase {

    @Test
    public void testGrindstoneProgenius() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Until end of turn, if a creature would enter the battlefield and it wasn't cast, exile it instead.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Hallowed Moonlight");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerB, "Pillarfield Ox");
        // Put a 1/1 colorless Spirit creature token onto the battlefield.
        // Splice onto Arcane {W}
        addCard(Zone.HAND, playerB, "Spiritual Visit");
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerB, "Reanimate");

        castSpell(2, PhaseStep.DRAW, playerA, "Hallowed Moonlight");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Spiritual Visit");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Reanimate", "Pillarfield Ox");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Hallowed Moonlight", 1);
        assertGraveyardCount(playerB, "Spiritual Visit", 1);

        assertPermanentCount(playerB, "Spirit Token", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertExileCount(playerB, 1);
        assertExileCount("Pillarfield Ox", 1);

    }

    /**
     * I cast Rally the Ancestors with many creatures in my graveyard. Opponent
     * responds with Hallowed Moonlight. After Rally the Ancestors resolves, the
     * creature cards in my graveyard remain in my graveyard, but are also added
     * to the exile zone.
     */
    @Test
    public void testRallyTheAncestors() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Until end of turn, if a creature would enter the battlefield and it wasn't cast, exile it instead.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Hallowed Moonlight");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        // Return each creature card with converted mana cost X or less from your graveyard to the battlefield.
        // Exile those creatures at the beginning of your next upkeep. Exile Rally the Ancestors.
        addCard(Zone.HAND, playerB, "Rally the Ancestors"); // Instant - {X}{W}{W}
        addCard(Zone.GRAVEYARD, playerB, "Pillarfield Ox");
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Rally the Ancestors");
        setChoice(playerB, "X=4");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Hallowed Moonlight", TestPlayer.NO_TARGET, "Rally the Ancestors");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Hallowed Moonlight", 1);

        assertExileCount("Rally the Ancestors", 1);
        assertExileCount("Pillarfield Ox", 1);
        assertExileCount("Silvercoat Lion", 1);

    }

}
