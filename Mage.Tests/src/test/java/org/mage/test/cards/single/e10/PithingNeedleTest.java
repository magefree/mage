package org.mage.test.cards.single.e10;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * {@link mage.cards.p.PithingNeedle Pithing Needle}
 * Artifact
 * {1}
 * As Pithing Needle comes into play, name a card.
 * Activated abilities of sources with the chosen name can't be played unless they're mana abilities.
 *
 * @author LevelX2, Alex-Vasile
 */
public class PithingNeedleTest extends CardTestMultiPlayerBase {

    /**
     * Test that it works and it respects range of influence
     */
    @Test
    public void TestPithingNeedle() {
        addCard(Zone.HAND, playerA, "Pithing Needle");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.LIBRARY, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // {2}{U}, {T}: Put target creature on the bottom of its owner's library.
        //              That creature's controller reveals cards from the top of their library until they reveal a creature card.
        //              The player puts that card onto the battlefield and the rest on the bottom of their library in any order.
        //              Activate this ability only any time you could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerB, "Proteus Staff", 1);

        addCard(Zone.BATTLEFIELD, playerC, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerC, "Proteus Staff", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Wall of Air", 1);
        addCard(Zone.LIBRARY, playerC, "Wind Drake", 2);

        addCard(Zone.BATTLEFIELD, playerD, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerD, "Eager Cadet", 1);
        addCard(Zone.LIBRARY, playerD, "Storm Crow", 2);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pithing Needle");
        setChoice(playerA, "Proteus Staff");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerC, "{2}{U}", "Eager Cadet"); // allowed because Needle out of range

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{U}", "Wall of Air"); // not allowed

        setStopAt(4, PhaseStep.END_TURN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: {2}{U}$target=Wall of Air")) {
                Assert.fail("Should have thrown an error about PlayerB not being able to use the staff to target Wall of Air, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerD, "Eager Cadet", 0);
        assertPermanentCount(playerD, "Storm Crow", 1);
        assertPermanentCount(playerC, "Wall of Air", 1);
        assertPermanentCount(playerC, "Wind Drake", 0);
    }
}
