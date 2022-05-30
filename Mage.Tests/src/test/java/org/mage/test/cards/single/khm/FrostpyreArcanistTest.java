package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.f.FrostpyreArcanist Frostpyre Arcanist}
 * {4}{U}
 * Creature — Giant Wizard
 * This spell costs {1} less to cast if you control a Giant or a Wizard.
 * When Frostpyre Arcanist enters the battlefield,
 * search your library for an instant or sorcery card with the same name as a card in your graveyard,
 * reveal it, put it into your hand, then shuffle.
 * 2/5
 *
 * @author TheElk801
 */
public class FrostpyreArcanistTest extends CardTestPlayerBase {

    private static final String arcanist = "Frostpyre Arcanist";
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testCanFind() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.LIBRARY, playerA, bolt);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.HAND, playerA, arcanist);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcanist);
        addTarget(playerA, bolt);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLibraryCount(playerA, bolt, 0);
        assertHandCount(playerA, bolt, 1);
    }

    @Test
    public void testCantFind() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.LIBRARY, playerA, bolt);
        addCard(Zone.HAND, playerA, arcanist);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcanist);
        // Let the choice be made automatically since there is no sorcery or instant card in the graveyard.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerA, bolt, 1);
        assertHandCount(playerA, bolt, 0);
    }
}
