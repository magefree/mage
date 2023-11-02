package org.mage.test.cards.emblems;

import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.command.emblems.EmblemOfCard;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author artemiswkearney
 */
public class EmblemOfCardTest extends CardTestPlayerBase {

    @Test
    public void testEmblemOfGriselbrand() {
        // Flying, lifelink
        // Pay 7 life: Draw seven cards.
        addEmblem(playerA, new EmblemOfCard(
                CardRepository.instance.findCard("Griselbrand", true).getMockCard()
        ));

        setLife(playerA, 20);

        assertHandCount(playerA, 0);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay 7 life: Draw");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 7);
        assertLife(playerA, 13);
        assertEmblemCount(playerA, 1);
    }
    @Test
    public void testEmblemOfYurlok() {
        // Vigilance
        // A player losing unspent mana causes that player to lose that much life.
        // {1}, {T}: Each player adds {B}{R}{G}.
        addEmblem(playerA, new EmblemOfCard(
                CardRepository.instance.findCard("Yurlok of Scorch Thrash", true).getMockCard()
        ));

        setLife(playerA, 20);

        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        checkManaPool("after tapping Mountain", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);
        checkPlayableAbility("can't tap emblem", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}:", false);

        // wait for mana burn
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        checkLife("takes 1 point of mana burn", 1, PhaseStep.BEGIN_COMBAT, playerA, 19);
        execute();

        assertEmblemCount(playerA, 1);
    }

    @Test
    public void testEmblemOfOmniscience() {
        // You may cast spells from your hand without paying their mana costs.
        addEmblem(playerA, new EmblemOfCard(
                CardRepository.instance.findCard("Omniscience", true).getMockCard()
        ));

        // Colossal Dreadmaw {4}{G}{G}
        // Creature - Dinosaur 6/6
        // Trample
        addCard(Zone.HAND, playerA, "Colossal Dreadmaw");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Colossal Dreadmaw");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Colossal Dreadmaw", 1);
        assertEmblemCount(playerA, 1);
    }
    @Test
    public void testEmblemOfParadoxEngine() {
        // Whenever you cast a spell, untap all nonland permanents you control.
        addEmblem(playerA, new EmblemOfCard(
                CardRepository.instance.findCard("Paradox Engine", true).getMockCard()
        ));

        // {T}: Add {G}.
        addCard(Zone.BATTLEFIELD, playerA, "Mox Emerald");

        // Sol Ring {1}
        // Artifact
        // {T}: Add {C}{C}.
        addCard(Zone.HAND, playerA, "Sol Ring");

        // Basalt Monolith {3}
        // Artifact
        // Basalt Monolith doesn’t untap during your untap step.
        // {T}: Add {C}{C}{C}.
        // {3}: Untap Basalt Monolith.
        addCard(Zone.HAND, playerA, "Basalt Monolith");

        // Book of Rass {6}
        // Artifact
        // {2}, Pay 2 life: Draw a card.
        // (just a dummy artifact to cast and spend the mana with)
        addCard(Zone.HAND, playerA, "Book of Rass");

        setLife(playerA, 20);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sol Ring");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Basalt Monolith");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Book of Rass");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Pay");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Pay");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Pay");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 14);
        assertEmblemCount(playerA, 1);
    }
    @Test
    public void testEmblemOfDoublingSeason() {
        // If an effect would create one or more tokens under your control, it
        // creates twice that many of those tokens instead.
        // If an effect would put one or more counters on a permanent you
        // control, it puts twice that many of those counters on that permanent instead.
        addEmblem(playerA, new EmblemOfCard(
                CardRepository.instance.findCard("Doubling Season", true).getMockCard()
        ));

        // {T}: Add {W}.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        // Elspeth, Sun's Champion {4}{W}{W}
        // Legendary Planeswalker — Elspeth
        // +1: Create three 1/1 white Soldier creature tokens.
        // −3: Destroy all creatures with power 4 or greater.
        // −7: You get an emblem with “Creatures you control get +2/+2 and have flying.”
        // Loyalty: 4
        addCard(Zone.HAND, playerA, "Elspeth, Sun's Champion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elspeth, Sun's Champion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters(
                "Elspeth's loyalty is doubled",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Elspeth, Sun's Champion",
                CounterType.LOYALTY,
                8
        );
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Create");
        checkPlayableAbility("can't still activate Griselbrand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay 7 life:", false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters(
                "+1 is not doubled",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Elspeth, Sun's Champion",
                CounterType.LOYALTY,
                9
        );
        checkPermanentCount(
                "Soldier tokens doubled",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Soldier Token",
                6
        );
        execute();
        assertEmblemCount(playerA, 1);
    }
}
