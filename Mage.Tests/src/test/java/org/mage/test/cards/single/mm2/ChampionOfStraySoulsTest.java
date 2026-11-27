package org.mage.test.cards.single.mm2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Champion of Stray Souls
 * {3}{B}{B}, {T}, Sacrifice X other creatures: Return X target creature cards from your graveyard
 * to the battlefield.
 * <p>
 * X is announced before targets are chosen and costs are paid (CR 601.2b, 601.2c, 602.2b). With no
 * other creature to sacrifice X can only be 0 (the Champion is not one of "other creatures"), so
 * nothing is sacrificed, the ability has no targets and it is activatable with no creature card in
 * the graveyard.
 */
public class ChampionOfStraySoulsTest extends CardTestPlayerBase {

    private static final String CHAMPION = "Champion of Stray Souls";
    private static final String ABILITY = "{3}{B}{B}, {T}, Sacrifice X other creatures: Return X target creature cards";

    @Test
    public void testNoCreatureCardActivatesForZero() {
        setStrictChooseMode(true); // X has one possible value: no announce is asked
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, CHAMPION);
        addCard(Zone.GRAVEYARD, playerA, "Swamp", 3); // no creature card
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // not "your graveyard"

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ABILITY);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(CHAMPION, true);
        assertTappedCount("Swamp", true, 5);
        assertPermanentCount(playerA, CHAMPION, 1);
        assertGraveyardCount(playerA, CHAMPION, 0);
        assertGraveyardCount(playerA, 3);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void testOtherPlayersCreatureIsNotSacrificeFodder() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, CHAMPION);
        addCard(Zone.BATTLEFIELD, playerB, "Shield Sphere");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // a creature card, but X can only be 0

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ABILITY);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(CHAMPION, true);
        assertPermanentCount(playerA, CHAMPION, 1);
        assertPermanentCount(playerB, "Shield Sphere", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testOneOtherCreatureStillSacrificesForOne() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, CHAMPION);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // the other creature to sacrifice
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ABILITY);
        setChoice(playerA, "X=1");
        addTarget(playerA, "Grizzly Bears");
        setChoice(playerA, "Memnite"); // the creature to sacrifice

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, CHAMPION, 1);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
    }
}
