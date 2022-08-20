package org.mage.test.cards.abilities.keywords;

import mage.abilities.Ability;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BlitzTest extends CardTestPlayerBase {

    private static final String withBlitz = " with Blitz";
    private static final String decoy = "Riveteers Decoy";
    private static final String underdog = "Tenacious Underdog";
    private static final String will = "Yawgmoth's Will";

    private void assertBlitzed(String cardName, boolean isBlitzed) {
        assertPermanentCount(playerA, cardName, 1);
        Permanent permanent = getPermanent(cardName);
        Assert.assertEquals(
                "Permanent should " + (isBlitzed ? "" : "not ") + "have haste", isBlitzed,
                permanent.hasAbility(HasteAbility.getInstance(), currentGame)
        );
        Assert.assertEquals(
                "Permanent should " + (isBlitzed ? "" : "not ") + "have card draw trigger", isBlitzed,
                permanent
                        .getAbilities(currentGame)
                        .stream()
                        .map(Ability::getRule)
                        .anyMatch("When this creature dies, draw a card."::equals)
        );
    }

    @Test
    public void testBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, decoy);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, decoy + withBlitz);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, decoy, 1);
        assertBlitzed(decoy, true);
    }

    @Test
    public void testBlitzSacrificed() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, decoy);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, decoy + withBlitz);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, decoy, 0);
        assertGraveyardCount(playerA, decoy, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testNoBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, decoy);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, decoy);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, decoy, 1);
        assertBlitzed(decoy, false);
    }

    @Test
    public void testTenaciousUnderdogNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, underdog);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, underdog);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertEquals(
                    "Shouldn't be able to cast normally from graveyard",
                    "Missing CAST/ACTIVATE def for turn 1, step PRECOMBAT_MAIN, PlayerA\n" +
                            "Can't find available command - activate:Cast Tenacious Underdog " +
                            "(use checkPlayableAbility for \"non available\" checks)", e.getMessage()
            );
        }

        assertGraveyardCount(playerA, underdog, 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testTenaciousUnderdogBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.GRAVEYARD, playerA, underdog);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, underdog + withBlitz);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(underdog, true);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void testTenaciousUnderdogYawgmothsWill() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, will);
        addCard(Zone.GRAVEYARD, playerA, underdog);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, will);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, underdog);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(underdog, false);
        assertLife(playerA, 20);
    }
}
