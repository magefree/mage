package org.mage.test.cards.single.znr;

import mage.cards.decks.Deck;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.y.YasharnImplacableEarth Yasharn, Implacable Earth}
 * When Yasharn enters the battlefield, search your library for a basic Forest card and a basic Plains card, reveal those cards, put them into your hand, then shuffle.
 * Players can’t pay life or sacrifice nonland permanents to cast spells or activate abilities.
 *
 * @author Alex-Vasile
 */
public class YasharnImplacableEarthTest extends CardTestPlayerBase {

    private static final String yasharn = "Yasharn, Implacable Earth";

    /**
     * Test that players can't pay life to cast a spell.
     */
    @Test
    @Ignore
    public void cantPayLifeToCast() {
        // {W}{B}
        // As an additional cost to cast this spell, pay 5 life or sacrifice a creature or enchantment.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Final Payment");
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Final Payment", yasharn);
        setChoice(playerA, "No");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertLife(playerA, 20);
    }

    /**
     * Test that players can't pay life to activate an ability.
     */
    @Test
    public void cantPayLifeToActivate() {
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Flooded Strand");
        addCard(Zone.LIBRARY, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: {T}")) {
                Assert.fail("must throw error about having 0 actions, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * Test that players can't sacrifice a nonland permanent to cast a spell.
     */
    @Test
    @Ignore
    public void cantSacrificeNonlandToCast() {
        // {1}{B}
        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        // Draw two cards and create a Treasure token.
        addCard(Zone.HAND, playerA, "Deadly Dispute");
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deadly Dispute");
        setChoice(playerA, yasharn);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Treasure Token", 0);
    }

    /**
     * Test that players can't sacrifice nonland permanent to activate an ability.
     */
    @Test
    public void cantSacrificeNonlandtoActive() {
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Armillary Sphere");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: {2}")) {
                Assert.fail("must throw error about having 0 actions, but got:\n" + e.getMessage());
            }
        }
        assertHandCount(playerA, 0);
    }

    /**
     * Test that a player can still sacrifice a land to cast a spell.
     */
    @Test
    public void canSacrificeLandToCast() {
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Crop Rotation");
        addCard(Zone.LIBRARY, playerA, "Mountain");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crop Rotation");
        setChoice(playerA, "Forest");
        addTarget(playerA, "Mountain");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Forest", 0);

        assertGraveyardCount(playerA, "Forest", 1);
    }

    /**
     * Test that a player can still sacrifice a land to activate an abiltiy.
     */
    @Test
    public void canSacrificeLandToActivate() {
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Evolving Wilds");
        addCard(Zone.LIBRARY, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 1);
        assertGraveyardCount(playerA, "Evolving Wilds", 1);
    }
}
