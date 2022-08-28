package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ZulaportCutthroatTest extends CardTestPlayerBase {

    /**
     * Zulaport's ability doesn't trigger when it dies. I'm not sure if that's
     * always the case, but I've encountered that bug at least several times
     * today.
     */
    @Test
    public void testDiesAndControllerDamage() {
        // Whenever Zulaport Cutthroat or another creature you control dies, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Zulaport Cutthroat", 1); // 1/1

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        // Target creature you control gets +1/+0 and gains deathtouch until end of turn.
        // Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life.
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Zulaport Cutthroat");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Zulaport Cutthroat", 1);

        assertLife(playerA, 21);
        assertLife(playerB, 19);

    }

    @Test
    public void testTriggersWhenDevoured() {
        // Whenever Zulaport Cutthroat or another creature you control dies, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Zulaport Cutthroat", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // Devour 1
        addCard(Zone.HAND, playerA, "Gluttonous Slime");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Slime");
        setChoice(playerA, true); // Devour
        addTarget(playerA, "Zulaport Cutthroat^Grizzly Bears");
        setChoice(playerA, "Whenever {this}"); // Two triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }
}
