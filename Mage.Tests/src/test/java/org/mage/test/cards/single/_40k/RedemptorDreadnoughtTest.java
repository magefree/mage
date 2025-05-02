package org.mage.test.cards.single._40k;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RedemptorDreadnoughtTest extends CardTestPlayerBase {
    
    private static final String redemptor = "Redemptor Dreadnought"; // {5} Artifact Creature 4/4
    // As an additional cost to cast this spell, you may exile a creature card from your graveyard.
    // Trample
    // Whenever Redemptor Dreadnought attacks, if a card is exiled with it, it gets +X/+X until end of turn, where X is the power of the exiled card.

    private static final String ghoul = "Warpath Ghoul"; // 3/2

    @Test
    public void testCastNoAdditionalCost() {
        addCard(Zone.HAND, playerA, redemptor, 1);
        addCard(Zone.GRAVEYARD, playerA, ghoul, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, redemptor);
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // choose not to exile anything

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, ghoul, 1);
        assertPowerToughness(playerA, redemptor, 4, 4);
    }

    @Test
    public void testCastAdditionalCostAndTrigger() {
        addCard(Zone.HAND, playerA, redemptor, 1);
        addCard(Zone.GRAVEYARD, playerA, ghoul, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, redemptor);
        setChoice(playerA, ghoul); // exile Warpath Ghoul

        attack(3, playerA, redemptor, playerB);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, ghoul, 1);
        assertPowerToughness(playerA, redemptor, 7, 7);
    }


}
