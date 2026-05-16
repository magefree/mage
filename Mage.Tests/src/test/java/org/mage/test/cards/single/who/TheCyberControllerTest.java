package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class TheCyberControllerTest extends CardTestPlayerBase {

    private static final String controller = "The Cyber-Controller";

    @Test
    public void test_MilledCreatureCardsReturnFaceDownAsCybermen() {
        skipInitShuffling();

        addCard(Zone.HAND, playerA, controller);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 6);

        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerB, "Runeclaw Bear");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, controller);
        setChoice(playerA, "X=3");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, controller, 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Runeclaw Bear", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        runCode("face-down Cyberman characteristics", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Permanent> permanents = game
                    .getBattlefield()
                    .getAllPermanents()
                    .stream()
                    .filter(permanent -> permanent.isControlledBy(playerA.getId()))
                    .filter(permanent -> permanent.isFaceDown(game))
                    .collect(Collectors.toList());
            Assert.assertEquals(2, permanents.size());
            permanents.forEach(permanent -> {
                Assert.assertTrue(permanent.isArtifact(game));
                Assert.assertTrue(permanent.isCreature(game));
                Assert.assertTrue(permanent.hasSubtype(SubType.CYBERMAN, game));
                Assert.assertEquals(3, permanent.getPower().getValue());
                Assert.assertEquals(3, permanent.getToughness().getValue());
            });
        });
    }
}
