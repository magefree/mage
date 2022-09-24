package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

public class ConcurrentModificationExceptionTest extends CardTestPlayerBaseWithAIHelps {


    // see details here: https://github.com/magefree/mage/commit/d202278ccd6876f8113d26951e6bd018f6e9b792

    @Ignore // debug only: too slow, only for manual run (use multi run to ensure)
    @Test
    public void massWarpWorld() {
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Prosperous Innkeeper");
        addCard(Zone.BATTLEFIELD, playerA, "Tireless Tracker");
        addCard(Zone.BATTLEFIELD, playerA, "Gilded Goose");
        addCard(Zone.BATTLEFIELD, playerA, "Galazeth Prismari");
        addCard(Zone.BATTLEFIELD, playerA, "Wood Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Nesting Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Primeval Titan");
        addCard(Zone.BATTLEFIELD, playerA, "Eternal Witness");
        addCard(Zone.BATTLEFIELD, playerA, "Westvale Abbey");
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Cradle");
        addCard(Zone.BATTLEFIELD, playerA, "Castle Garenbrig");
        addCard(Zone.BATTLEFIELD, playerA, "Nyxbloom Ancient");
        addCard(Zone.BATTLEFIELD, playerA, "Huntmaster of the Fells");
        addCard(Zone.BATTLEFIELD, playerA, "Xenagos, the Reveler");
        addCard(Zone.BATTLEFIELD, playerA, "Thromok the Insatiable");
        addCard(Zone.BATTLEFIELD, playerA, "The World Tree");
        addCard(Zone.BATTLEFIELD, playerA, "Nylea, Keen-Eyed");
        addCard(Zone.BATTLEFIELD, playerA, "Kogla, the Titan Ape");
        addCard(Zone.BATTLEFIELD, playerA, "Acidic Slime");
        addCard(Zone.BATTLEFIELD, playerA, "Druid Class");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Warp World");

        // simple test
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warp World");
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        //setStrictChooseMode(true); // need AI while cards adding
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
