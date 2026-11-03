package org.mage.test.cards.single.frc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Riley Jones
 */
public class JhoiraWeatherlightCorsairTest extends CardTestPlayerBase {

    /*
     * Jhoira, Weatherlight Corsair {4}{B}{B}
     * Legendary Creature — Human Pirate (4/5)
     * Whenever Jhoira enters or attacks, target opponent reveals cards from the top of their library
     * until they reveal a historic permanent card. You put that card onto the battlefield under
     * your control and lose life equal to that permanent's mana value. That player puts the rest of
     * the revealed cards on the bottom of their library in a random order.
     * (Artifacts, legendaries, and Sagas are historic.)
     */
    private static final String jhoira = "Jhoira, Weatherlight Corsair";

    @Test
    public void test_ETB_RevealsUntilHistoricPermanent() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        // Zone.LIBRARY - last added card goes to the top of the library
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // 4th (bottom)
        addCard(Zone.LIBRARY, playerB, "Sol Ring"); // 3rd (artifact permanent, MV 1)
        addCard(Zone.LIBRARY, playerB, "Urza's Ruinous Blast"); // 2nd (legendary sorcery, historic non-permanent)
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt"); // 1st (top)

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, jhoira);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jhoira);
        addTarget(playerA, playerB); // Target opponent for ETB

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, jhoira, 1);
        assertPermanentCount(playerA, "Sol Ring", 1);
        assertLife(playerA, 20 - 1);
        assertLibraryCount(playerB, 3); // Lightning Bolt, Urza's Ruinous Blast, Grizzly Bears
    }

    @Test
    public void test_Attack_RevealsUntilHistoricPermanent() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        // Zone.LIBRARY - last added card goes to the top of the library
        addCard(Zone.LIBRARY, playerB, "Plains"); // 3rd
        addCard(Zone.LIBRARY, playerB, "Blackblade Reforged"); // 2nd (legendary artifact equipment, MV 2)
        addCard(Zone.LIBRARY, playerB, "Forest"); // 1st (top)

        addCard(Zone.BATTLEFIELD, playerA, jhoira);

        attack(1, playerA, jhoira, playerB);
        addTarget(playerA, playerB); // Target opponent for attack trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Blackblade Reforged", 1);
        assertLife(playerA, 20 - 2);
        assertLibraryCount(playerB, 2);
    }

    @Test
    public void test_LegendaryLand_ManaValueZero() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerB, "Academy Ruins"); // legendary land (historic permanent, MV 0)
        addCard(Zone.LIBRARY, playerB, "Mountain");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, jhoira);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jhoira);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, jhoira, 1);
        assertPermanentCount(playerA, "Academy Ruins", 1);
        assertLife(playerA, 20); // 0 life lost
        assertLibraryCount(playerB, 1); // Mountain
    }

    @Test
    public void test_SagaEnchantment() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerB, "The Eldest Reborn"); // Saga (historic permanent, MV 5)
        addCard(Zone.LIBRARY, playerB, "Island");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, jhoira);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jhoira);
        addTarget(playerA, playerB);

        // When The Eldest Reborn enters, Chapter I triggers: "Each opponent sacrifices a creature or planeswalker."
        // We need playerB to sacrifice something or have no creatures. Since playerB controls no creatures/planeswalkers, nothing to choose.
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, jhoira, 1);
        assertPermanentCount(playerA, "The Eldest Reborn", 1);
        assertLife(playerA, 20 - 5);
        assertLibraryCount(playerB, 1);
    }

    @Test
    public void test_NoHistoricPermanentInLibrary() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, jhoira);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jhoira);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, jhoira, 1);
        assertLife(playerA, 20);
        assertLibraryCount(playerB, 2);
    }

    @Test
    public void test_HistoricAuraWithoutLegalTarget_StaysInLibrary_NoLifeLost() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        // Zone.LIBRARY - last added card goes to the top of the library
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // 3rd (bottom)
        addCard(Zone.LIBRARY, playerB, "Eye of Nidhogg"); // 2nd (legendary aura, "Enchant creature", MV 3)
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt"); // 1st (top)

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerA, jhoira);
        addCard(Zone.HAND, playerB, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jhoira);
        addTarget(playerA, playerB); // Target opponent for ETB
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // Kill Jhoira in response to the ETB trigger so no creature is on the battlefield when it resolves
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder", jhoira);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // 303.4g: Eye of Nidhogg has no legal object to enchant, so it remains in its current zone (library)
        assertPermanentCount(playerA, "Eye of Nidhogg", 0);
        assertLife(playerA, 20); // no permanent entered the battlefield, so no life is lost
        assertLibraryCount(playerB, 3); // Eye of Nidhogg stays in library, Lightning Bolt goes to the bottom
        assertGraveyardCount(playerA, jhoira, 1);
    }

    @Test
    public void test_EmptyLibrary() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, jhoira);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jhoira);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, jhoira, 1);
        assertLife(playerA, 20);
        assertLibraryCount(playerB, 0);
    }
}
