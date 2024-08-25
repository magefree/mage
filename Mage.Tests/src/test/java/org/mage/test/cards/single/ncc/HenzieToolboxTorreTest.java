package org.mage.test.cards.single.ncc;

import mage.abilities.Ability;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * {@link mage.cards.h.HenzieToolboxTorre Henzie "Toolbox" Torre}
 * {B}{R}{G}
 * Each creature spell you cast with mana value 4 or greater has blitz.
 * The blitz cost is equal to its mana cost.
 *      (You may choose to cast that spell for its blitz cost.
 *       If you do, it gains haste and “When this creature dies, draw a card.”
 *       Sacrifice it at the beginning of the next end step.)
 * Blitz costs you pay cost {1} less for each time you’ve cast your commander from the command zone this game.
 *
 * @author Alex-Vasile
 */
public class HenzieToolboxTorreTest extends CardTestCommander4Players {

    private static final String henzieToolboxTorre = "Henzie \"Toolbox\" Torre";
    // 2/4
    // {2}{R}{R}{R}
    private static final String aerathiBerserker = "Aerathi Berserker";
    private static final String commandTower = "Command Tower";
    private static final String embrace = "Embrace the Unknown";
    private static final String withBlitz = " with Blitz";

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

    /**
     * Test that the card gets blitz and is properly sacrificed.
     */
    @Test
    public void gainsBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, aerathiBerserker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }

    /**
     * Test that it's properly being discount for each time the commander was cast.
     * Bonded Construct should be playable for free with its blitz costs.
     */
    @Test
    public void commanderCastDiscount() {
        addCard(Zone.COMMAND, playerA, henzieToolboxTorre);
        // Henzie costs 3 mana, and berserker costs 5 mana reduced to 4
        addCard(Zone.BATTLEFIELD, playerA, commandTower, 3 + 4);
        addCard(Zone.HAND, playerA, aerathiBerserker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, henzieToolboxTorre, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }

    /**
     * Test that you can cast your commander with the blitz cost
     */
    @Test
    @Ignore // TODO: see #12188
    public void commanderCastBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.COMMAND, playerA, aerathiBerserker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }

    /**
     * Test that you can cast your own card from exile
     */
    @Test
    public void exileCastBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, henzieToolboxTorre);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 5);
        addCard(Zone.HAND, playerA, embrace);
        addCard(Zone.LIBRARY, playerA, aerathiBerserker);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, embrace, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerathiBerserker + withBlitz, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBlitzed(aerathiBerserker, true);
    }
}
