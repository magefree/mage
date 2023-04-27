package org.mage.test.cards.single.bfz;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by escplan9
 */
public class EmeriaShepherdTest extends CardTestPlayerBase {

    /*
     * Reported bug: Emeria Shepherd can't bounce Bruna to the table when a plains enters the battlefield.
     */
    @Test
    public void emeriaInteractionWithBruna()
    {
        /*
         *
         * Emeria Shepherd (5)(W)(W)
            Flying 4/4 Creature Angel
            Landfall — Whenever a land enters the battlefield under your control, you may return target nonland permanent card from your graveyard to your hand.
            If that land is a Plains, you may return that nonland permanent card to the battlefield instead.
         */
        String emeria = "Emeria Shepherd";

        /*
         *  Bruna, The Fading Light (5)(W)(W)
         *  Legendary Creature - Angel Horror 5/7
            When you cast Bruna, the Fading Light, you may return target Angel or Human creature card from your graveyard to the battlefield.
            Flying, vigilance
            (Melds with Gisela, the Broken Blade.)
         */
        String bruna = "Bruna, the Fading Light";

        addCard(Zone.BATTLEFIELD, playerA, emeria);
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.GRAVEYARD, playerA, bruna);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        setChoice(playerA, true); // opt to use Emeria's triggered ability
        setChoice(playerA, true); // opt to put it onto the battlefield instead of the hand
        addTarget(playerA, bruna); // target Bruna in grave

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Plains", 0);
        assertGraveyardCount(playerA, bruna, 0);
        assertHandCount(playerA, bruna, 0);
        assertPermanentCount(playerA, bruna, 1);
    }
}
