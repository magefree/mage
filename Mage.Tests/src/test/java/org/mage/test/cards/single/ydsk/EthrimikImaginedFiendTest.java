package org.mage.test.cards.single.ydsk;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EthrimikImaginedFiendTest extends CardTestPlayerBase {

    @Test
    public void testManifestDreadAndOtherCreatureBoost() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Ethrimik, Imagined Fiend");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethrimik, Imagined Fiend");
        setChoice(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 3, 3);
        assertPowerToughness(playerA, "Ethrimik, Imagined Fiend", 4, 4);
        assertGraveyardCount(playerA, "Forest", 1);
    }

    @Test
    public void testCombatRestrictionClearsWhenOtherCreatureLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Ethrimik, Imagined Fiend");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        runCode("restricted with another creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent fiend = game.getBattlefield().getAllActivePermanents().stream()
                    .filter(p -> p.getName().equals("Ethrimik, Imagined Fiend")).findFirst().get();
            Assert.assertFalse(fiend.canAttack(playerB.getId(), game));
            Assert.assertFalse(fiend.canBlockAny(game));
        });
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Grizzly Bears");
        attack(1, playerA, "Ethrimik, Imagined Fiend");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 16);
        assertLife(playerA, 20);
        assertPowerToughness(playerA, "Ethrimik, Imagined Fiend", 4, 4);
    }

    @Test
    public void testCanBlockWhenAlone() {
        addCard(Zone.BATTLEFIELD, playerA, "Ethrimik, Imagined Fiend");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        attack(2, playerB, "Grizzly Bears");
        block(2, playerA, "Ethrimik, Imagined Fiend", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}
