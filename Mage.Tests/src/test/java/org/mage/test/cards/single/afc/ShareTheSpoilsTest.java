package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ShareTheSpoilsTest extends CardTestPlayerBase {

    // When Share the Spoils enters the battlefield or an opponent loses the game,
    // exile the top card of each player’s library.
    //
    // During each player’s turn, that player may play a land or cast a spell from among cards exiled with Share the Spoils,
    // and they may spend mana as though it were mana of any color to cast that spell.
    // When they do, exile the top card of their library.
    private final String shareTheSpoils = "Share the Spoils";

    // TODO: finish all of these
    // TODO: What cards are in the decks?
    // Putting cards into exile
    @Test
    public void enterTheBattleField() {
        addCard(Zone.HAND,        playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);

    }

    // TODO: Make a 3 player game to test the following two
    @Test
    public void nonOwnerLoses() {
        assert true;
    }

    @Test
    public void ownerLosesGame() {
        assert true;
    }

    // Limit to only cast on your turn

    // Limit to one card per turn

    // Exile card when you cast
}
