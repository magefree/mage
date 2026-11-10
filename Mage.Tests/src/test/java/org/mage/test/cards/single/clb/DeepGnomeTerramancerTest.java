package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DeepGnomeTerramancerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DeepGnomeTerramancer Deep Gnome Terramancer} {1}{W}
     * Creature — Gnome Wizard
     * Flash
     * Mold Earth — Whenever one or more lands enter the battlefield under an opponent’s control without being played, you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle. Do this only once each turn.
     * 2/2
     */
    private static final String gnome = "Deep Gnome Terramancer";

    // Bug: Deep Gnome triggers out of playing extra lands.
    @Test
    public void test_OracleOfMuldaya_NoTrigger() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, gnome);
        addCard(Zone.BATTLEFIELD, playerA, "Oracle of Mul Daya");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Island", 2);
    }

    @Test
    public void test_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, gnome);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Arboreal Grazer");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.LIBRARY, playerB, "Tundra");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arboreal Grazer");
        setChoice(playerA, true); // yes to trigger from Grazer
        setChoice(playerA, "Plains"); // puts plains into play
        setChoice(playerB, true); // yes to trigger from Gnome
        addTarget(playerB, "Tundra"); // search for Tundra with Gnome trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerB, "Tundra", 1);
        assertTappedCount("Tundra", true, 1);
    }

    @Test
    public void test_OneOrMoreLandsEnter_OneTrigger() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.BATTLEFIELD, playerA, gnome);
        addCard(Zone.LIBRARY, playerA, "Plains", 3);
        //
        // Search your library for up to two basic land cards and/or Gate cards, put them onto the battlefield tapped, then shuffle.
        addCard(Zone.HAND, playerB, "Circuitous Route"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        addCard(Zone.LIBRARY, playerB, "Forest", 3); // 1 for draw

        // two lands enter at once - one trigger only (see https://github.com/magefree/mage/issues/10088)
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Circuitous Route");
        addTarget(playerB, "Forest^Forest");
        setChoice(playerA, true); // yes to trigger from Gnome
        addTarget(playerA, "Plains");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Forest", 4 + 2);
        assertPermanentCount(playerA, "Plains", 1);
    }

    @Test
    public void test_OwnLandsEnter_NoTrigger() {
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, gnome);
        addCard(Zone.LIBRARY, playerA, "Plains", 3);
        //
        // Search your library for up to two basic land cards and/or Gate cards, put them onto the battlefield tapped, then shuffle.
        addCard(Zone.HAND, playerA, "Circuitous Route"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circuitous Route");
        addTarget(playerA, "Plains^Plains");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
    }
}
