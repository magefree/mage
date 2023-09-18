package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ContainmentPriestTest extends CardTestPlayerBase {

    @Test
    public void replacementEffect(){
        // If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Containment Priest");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerB, "Scryb Sprites");

        // Each player puts a creature card from their graveyard onto the battlefield.
        addCard(Zone.HAND, playerA, "Exhume");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhume");
        addTarget(playerA, "Grizzly Bears");
        addTarget(playerB, "Scryb Sprites");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerB, "Scryb Sprites", 1);
    }
}
