package org.mage.test.cards.single.grn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {T}: Mill a card.
 * {2}, {T}, Exile Wand of Vertebrae: Shuffle up to five target cards from your graveyard into your library.
 *
 * @author Alex-Vasile
 */
public class WandOfVertebraeTest extends CardTestPlayerBase {

    private static final String wandOfVertebrae = "Wand of Vertebrae";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9464
     *
     */
    @Test
    public void canChoosePreviouslyBuggedCard() {
        String lavaCoil = "Lava Coil";

        addCard(Zone.BATTLEFIELD, playerA, wandOfVertebrae);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.GRAVEYARD, playerA, lavaCoil);

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}");
        addTarget(playerA, lavaCoil);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, wandOfVertebrae, 1);
        assertGraveyardCount(playerA, lavaCoil, 0);
        assertLibraryCount(playerA, lavaCoil, 1);
    }
}
