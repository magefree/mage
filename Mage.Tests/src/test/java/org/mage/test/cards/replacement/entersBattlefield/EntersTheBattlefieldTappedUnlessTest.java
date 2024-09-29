package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class EntersTheBattlefieldTappedUnlessTest extends CardTestPlayerBase {

    /**
     * Deathcap Glade enters the battlefield tapped unless you control two or more other lands.
     */
    private static final String glade = "Deathcap Glade";

    @Test
    public void test_SlowLand_NotMet() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, glade);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, glade);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(glade, true);
    }

    @Test
    public void test_SlowLand_Met() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, glade);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, glade);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(glade, false);
    }

    /**
     * Blackcleave Cliffs enters the battlefield tapped unless you control two or fewer other lands.
     */
    private static final String cliffs = "Blackcleave Cliffs";

    @Test
    public void test_FastLand_NotMet() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, cliffs);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, cliffs);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(cliffs, true);
    }

    @Test
    public void test_FastLand_Met() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, cliffs);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, cliffs);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(cliffs, false);
    }

    /**
     * Castle Ardenvale enters the battlefield tapped unless you control a Plains.
     */
    private static final String castle = "Castle Ardenvale";

    @Test
    public void test_Castle_NotMet() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, castle);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, castle);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(castle, true);
    }

    @Test
    public void test_Castle_Met() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, castle);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, castle);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(castle, false);
    }

    /**
     * Canopy Vista enters the battlefield tapped unless you control two or more basic lands.
     */
    private static final String vista = "Canopy Vista";

    @Test
    public void test_Tango_NotMet() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, vista);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, vista);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(vista, true);
    }

    @Test
    public void test_Tango_Met() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, vista);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, vista);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(vista, false);
    }

    /**
     * Clifftop Retreat enters the battlefield tapped unless you control a Mountain or a Plains.
     */
    private static final String retreat = "Clifftop Retreat";

    @Test
    public void test_CheckLand_NotMet() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, retreat);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, retreat);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(retreat, true);
    }

    @Test
    public void test_CheckLand_Met() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, retreat);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, retreat);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(retreat, false);
    }

    /**
     * Dwarven Mine enters the battlefield tapped unless you control three or more other Mountains.
     */
    private static final String mine = "Dwarven Mine";

    @Test
    public void test_EldraineAdamantLands_NotMet() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mine);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, mine);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(mine, true);
    }

    @Test
    public void test_EldraineAdamantLands_Met() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mine);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, mine);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(mine, false);
    }

    /**
     * Barad-dûr enters the battlefield tapped unless you control a legendary creature.
     */
    private static final String barad = "Barad-dur";

    @Test
    public void test_LTRLegendary_NotMet() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, barad);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, barad);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(barad, true);
    }

    @Test
    public void test_LTRLegendary_Met() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, barad);
        addCard(Zone.BATTLEFIELD, playerA, "Six");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, barad);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(barad, false);
    }
}
