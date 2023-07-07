package org.mage.test.cards.single.ltr;

import mage.cards.f.FeldonOfTheThirdPath;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RadagastTheBrownTest extends CardTestPlayerBase {
    private static final String radagast = "Radagast the Brown";
    @Test
    public void libraryTest() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Goblin Guide"); // Goblin - 1 CMC
        addCard(Zone.LIBRARY, playerA, "Amoeboid Changeling"); // Changeling - 2 CMC - should share creature types with anything
        addCard(Zone.LIBRARY, playerA, "Heliod's Emissary"); // Enchantment Creature - Elk - 4 CMC
        addCard(Zone.LIBRARY, playerA, "Overbeing of Myth"); // Spirit Avatar - 5 CMC
        addCard(Zone.LIBRARY, playerA, "Boggart Shenanigans"); // Tribal Enchantment - Goblin
        addCard(Zone.LIBRARY, playerA, "Stampeding Elk Herd"); // Elk - 5 CMC

        addCard(Zone.HAND, playerA, radagast);

        addCard(Zone.LIBRARY, playerB, "Swamp", 50);

        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 10);

        addCard(Zone.BATTLEFIELD, playerB, "Amoeboid Changeling", 10);

        // 4 cards revealed - choose from Stampeding Elk Herd and Heliod's Emissary:
        setChoice(playerA, "Yes");
        addTarget(playerA, "Stampeding Elk Herd");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, radagast);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Stampeding Elk Herd", 1);
        assertLibraryCount(playerA, 5);

        setChoice(playerA, "Yes");
        addTarget(playerA, "Goblin Guide");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Stampeding Elk Herd");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerA, "Goblin Guide", 1);
    }

    @Test
    public void whenItsAToken() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Goblin Guide"); // Goblin - 1 CMC
        addCard(Zone.LIBRARY, playerA, "Amoeboid Changeling"); // Changeling - 2 CMC - should share creature types with anything
        addCard(Zone.LIBRARY, playerA, "Heliod's Emissary"); // Enchantment Creature - Elk - 4 CMC
        addCard(Zone.LIBRARY, playerA, "Overbeing of Myth"); // Spirit Avatar - 5 CMC
        addCard(Zone.LIBRARY, playerA, "Boggart Shenanigans"); // Tribal Enchantment - Goblin
        addCard(Zone.LIBRARY, playerA, "Stampeding Elk Herd"); // Elk - 5 CMC

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);

        addCard(Zone.GRAVEYARD, playerA, radagast);
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}{R}, {T}: Create a token that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.",
                radagast);

        // 4 cards revealed - choose from Stampeding Elk Herd and Heliod's Emissary:
        setChoice(playerA, "Yes");
        addTarget(playerA, "Heliod's Emissary");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, 12);
    }
}