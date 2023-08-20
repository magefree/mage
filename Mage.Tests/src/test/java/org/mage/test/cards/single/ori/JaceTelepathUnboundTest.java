package org.mage.test.cards.single.ori;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.j.JaceTelepathUnbound Jace, Telepath Unbound}
 * +1: Up to one target creature gets -2/-0 until your next turn.
 * −3: You may cast target instant or sorcery card from your graveyard this turn. If that spell would be put into your graveyard, exile it instead.
 * −9: You get an emblem with “Whenever you cast a spell, target opponent mills five cards.”
 *
 * @author Alex-Vasile
 */
public class JaceTelepathUnboundTest extends CardTestPlayerBase {
    private static final String jace = "Jace, Telepath Unbound";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9365
     *      Jace's -3 ability doesn't work for MDFC cards
     */
    @Test
    public void castMDFC() {
        skipInitShuffling();
        // {4}{G}{G}{G}
        // Sorcery
        // Look at the top seven cards of your library.
        // You may put a creature card from among them onto the battlefield.
        // If that card has mana value 3 or less, it enters with three additional +1/+1 counters on it.
        // Put the rest on the bottom of your library in a random order.
        String turntimberSymbiosis = "Turntimber Symbiosis";
        addCard(Zone.GRAVEYARD, playerA, turntimberSymbiosis);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);

        String lion = "Silvercoat Lion";
        addCard(Zone.LIBRARY, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, jace);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: ", turntimberSymbiosis);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, turntimberSymbiosis);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertPowerToughness(playerA,  lion, 5, 5);
        assertExileCount(playerA, turntimberSymbiosis, 1);
    }

    /**
     * If the front is a sorcery/instant and the back a land, make sure that the land cannot be played
     */
    @Test
    public void castMDFCFrontOnly() {
        // {4}{G}{G}{G}
        // Sorcery
        // Back: Turntimber, Serpentine Wood
        String turntimberSymbiosis = "Turntimber Symbiosis";
        addCard(Zone.GRAVEYARD, playerA, turntimberSymbiosis);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);

        String lion = "Silvercoat Lion";
        addCard(Zone.LIBRARY, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, jace);

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: ", turntimberSymbiosis);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Turntimber, Serpentine Wood");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Turntimber, Serpentine Wood", 0);
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9607
     *      Jace's -3 ability doesn't work for split cards
     */
    @Test
    public void castSplitCardLeftHalf() {
        String fireIce = "Fire // Ice";
        addCard(Zone.GRAVEYARD, playerA, fireIce);
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: ");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, fireIce, 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void castSplitCardRightHalf() {
        String fireIce = "Fire // Ice";
        addCard(Zone.GRAVEYARD, playerA, fireIce);
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: ");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice");
        addTarget(playerA, "Island");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, fireIce, 1);
        assertHandCount(playerA, 1);
    }
}
