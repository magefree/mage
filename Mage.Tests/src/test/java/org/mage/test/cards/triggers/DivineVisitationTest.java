package org.mage.test.cards.triggers;

import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author luziferius
 */
public class DivineVisitationTest extends CardTestPlayerBase {

    /**
     * Test case for issue #6349. Divine Visitation should not replace Treasure tokens created by Smothering Tithe
     */
    @Test
    public void testDivineVisitationDoesNotReplaceNoncreatureTokens() {

        // If one or more creature tokens would be created under your control,
        // that many 4/4 white Angel creature tokens with flying and vigilance are created instead.
        addCard(Zone.BATTLEFIELD, playerA, "Divine Visitation");
        // Whenever an opponent draws a card, that player may pay {2}.
        // If the player doesn’t, you create a Treasure token.
        // (It’s an artifact with “{T}, Sacrifice this artifact: Add one mana of any color.”)
        addCard(Zone.BATTLEFIELD, playerA, "Smothering Tithe");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Target player draws three cards.
        addCard(Zone.HAND, playerA, "Ancestral Recall"); // {U}
        // Let the opponent draw
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Recall", playerB);
        setChoice(playerA, "Whenever an opponent draws a card", 2); // choose order of triggers
        setChoice(playerB, false, 3); // Decline to pay 2
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        
        assertHandCount(playerB, 3);
        assertPermanentCount(playerA, "Treasure", 3);
        assertType("Treasure", CardType.ARTIFACT, SubType.TREASURE);
        assertNotType("Treasure", CardType.CREATURE);
        assertNotSubtype("Treasure", SubType.ANGEL);
        assertPermanentCount(playerA, "Angel", 0);
        assertPermanentCount(playerA, 6);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testDivineVisitationReplacesCreatureTokens() {

        // If one or more creature tokens would be created under your control,
        // that many 4/4 white Angel creature tokens with flying and vigilance are created instead.
        addCard(Zone.BATTLEFIELD, playerA, "Divine Visitation");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Create two 1/1 red Goblin creature tokens.
        addCard(Zone.HAND, playerA, "Dragon Fodder");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dragon Fodder");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, 1);
        assertPermanentCount(playerA, 5);
        assertPermanentCount(playerA, "Goblin", 0);
        assertPermanentCount(playerA, "Angel", 2);
        assertType("Angel", CardType.CREATURE, SubType.ANGEL);
        assertColor(playerA, "Angel", ObjectColor.WHITE, true);
        assertColor(playerA, "Angel", ObjectColor.RED, false);
        assertPowerToughness(playerA, "Angel", 4,4);
        assertNotSubtype("Angel", SubType.GOBLIN);
        
    }
}