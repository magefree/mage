package org.mage.test.cards.single.msc;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

public class MBakuJabariChieftainMultiplayerTest extends CardTestMultiPlayerBase {

    // At the beginning of your end step, if there is no monarch, target opponent becomes the monarch.
    // Whenever a creature attacks one of your opponents, if that player is the monarch, that creature gets +1/+1 and gains trample until end of turn.
    private static final String mbaku = "M'Baku, Jabari Chieftain";

    private static final String bears = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";

    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        // attacking more than one opponent in the same combat needs MultiplayerAttackOption.MULTIPLE
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_OpponentAttacksMonarch_OnlyThatCreatureBoosted() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mbaku);
        addCard(Zone.BATTLEFIELD, playerC, bears);
        addCard(Zone.BATTLEFIELD, playerC, lion);

        // turn 1 end step: B becomes the monarch
        addTarget(playerA, playerB);

        // C attacks the monarch with one creature and another opponent with the other
        attack(3, playerC, bears, playerB);
        attack(3, playerC, lion, playerD);

        checkPT("attacking the monarch", 3, PhaseStep.DECLARE_BLOCKERS, playerC, bears, 3, 3);
        checkAbility("attacking the monarch", 3, PhaseStep.DECLARE_BLOCKERS, playerC, bears, TrampleAbility.class, true);
        checkPT("attacking another opponent", 3, PhaseStep.DECLARE_BLOCKERS, playerC, lion, 2, 2);
        checkAbility("attacking another opponent", 3, PhaseStep.DECLARE_BLOCKERS, playerC, lion, TrampleAbility.class, false);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertLife(playerD, 20 - 2);
        Assert.assertEquals("monarch", playerC.getId(), currentGame.getMonarchId());
    }
}
