package org.mage.test.cards.battle;

import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.jupiter.api.Assertions;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BattleBaseTest extends CardTestPlayerBase {

    protected static final String belenon = "Invasion of Belenon";
    protected static final String bear = "Grizzly Bears";
    protected static final String confiscate = "Confiscate";

    protected void assertBattle(Player controller, Player protector, String name) {
        assertPermanentCount(controller, name, 1);
        Permanent permanent = getPermanent(name);
        Assertions.assertTrue(
                permanent.isProtectedBy(protector.getId()),
                "Battle " + name + " should be protected by " + protector.getName()
        );
    }
}
