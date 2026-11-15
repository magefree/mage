package org.mage.test.cards.single.ala;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AngelsHeraldTest extends CardTestPlayerBase {

    // {2}{W}, {T}, Sacrifice a green creature, a white creature, and a blue creature:
    // Search your library for a card named Empyrial Archangel and put it onto the battlefield. Then shuffle.
    private static final String herald = "Angel's Herald";

    @Test
    public void test_AI_SacrificesOneOfEachColor() {
        addCard(Zone.BATTLEFIELD, playerA, herald);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.LIBRARY, playerA, "Empyrial Archangel");

        // the AI sacrifices its least valuable creatures first. It must skip the second Bears, because a green
        // creature is already chosen
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{W}, {T}, Sacrifice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Air Elemental", 0);
        assertPermanentCount(playerA, "Empyrial Archangel", 1);
    }
}
