package org.mage.test.cards.single.tdc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ProtectorOfTheWastesTest extends CardTestPlayerBase {

    private static final String protector = "Protector of the Wastes";
    private static final String fountainOfYouth = "Fountain of Youth";

    @Test
    public void testEntersBattlefieldEventWithNullSourceTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, protector);
        addCard(Zone.BATTLEFIELD, playerB, fountainOfYouth);

        addTarget(playerA, fountainOfYouth);
        runCode("fire null-source enters event", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent permanent = game.getBattlefield()
                    .getAllActivePermanents(player.getId())
                    .stream()
                    .filter(card -> card.getName().equals(protector))
                    .findFirst()
                    .orElseThrow(AssertionError::new);
            game.fireEvent(new EntersTheBattlefieldEvent(permanent, null, player.getId(), Zone.OUTSIDE));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, fountainOfYouth, 1);
    }
}
