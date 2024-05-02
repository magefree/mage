package org.mage.test.cards.single.rex;

import mage.abilities.keyword.HexproofFromPlaneswalkersAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jimga150
 */
public class IndominusRexAlphaTests extends CardTestPlayerBase {

    @Test
    public void testIndominusRexAlphaAllAbilties() {

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);
        addCard(Zone.HAND, playerA, "Ornithopter", 1); // Flying
        addCard(Zone.HAND, playerA, "Rograkh, Son of Rohgahh", 1); // First strike, menace, trample, Partner
        addCard(Zone.HAND, playerA, "Adorned Pouncer", 1); // Double strike, Eternalize
        addCard(Zone.HAND, playerA, "Ankle Biter", 1); // Deathtouch
        addCard(Zone.HAND, playerA, "Gladecover Scout", 1); // Hexproof
        addCard(Zone.HAND, playerA, "Banehound", 1); // Lifelink, haste
        addCard(Zone.HAND, playerA, "Bontu the Glorified", 1); // Menace, indestructible
        addCard(Zone.HAND, playerA, "Aerial Responder", 1); // Flying, vigilance, lifelink
        addCard(Zone.HAND, playerA, "Stonecoil Serpent", 1); // Reach, trample, protection from multicolored
        addCard(Zone.HAND, playerA, "Codespell Cleric", 1); // Vigilance

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Ornithopter^Rograkh, Son of Rohgahh^Adorned Pouncer^Ankle Biter^Gladecover Scout" +
                "^Banehound^Bontu the Glorified^Aerial Responder^Stonecoil Serpent^Codespell Cleric");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FIRST_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DOUBLE_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DEATHTOUCH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HEXPROOF, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HASTE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.INDESTRUCTIBLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.LIFELINK, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.MENACE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.REACH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.TRAMPLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.VIGILANCE, 1);

        assertHandCount(playerA, 12);

    }

    @Test
    public void testIndominusRexAlphaHexproofFromX() {

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);
        addCard(Zone.HAND, playerA, "Eradicator Valkyrie", 1); // Flying, lifelink, hexproof from planeswalker

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Eradicator Valkyrie");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HEXPROOF, 0);
        assertCounterCount(playerA, "Indominus Rex, Alpha", HexproofFromPlaneswalkersAbility.getInstance().getRule(), 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.LIFELINK, 1);

        assertHandCount(playerA, 3);

    }

    @Test
    public void testIndominusRexAlphaGraveyardMovement() {

        // When Rest in Peace enters the battlefield, exile all graveyards.
        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace", 1);

        // This test verifies that indominus can check cards that have moved to zones other than the graveyard after
        // they've been discarded with her ability.

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);
        addCard(Zone.HAND, playerA, "Ornithopter", 1); // Flying
        addCard(Zone.HAND, playerA, "Rograkh, Son of Rohgahh", 1); // First strike, menace, trample, Partner
        addCard(Zone.HAND, playerA, "Adorned Pouncer", 1); // Double strike, Eternalize
        addCard(Zone.HAND, playerA, "Ankle Biter", 1); // Deathtouch
        addCard(Zone.HAND, playerA, "Gladecover Scout", 1); // Hexproof
        addCard(Zone.HAND, playerA, "Banehound", 1); // Lifelink, haste
        addCard(Zone.HAND, playerA, "Bontu the Glorified", 1); // Menace, indestructible
        addCard(Zone.HAND, playerA, "Aerial Responder", 1); // Flying, vigilance, lifelink
        addCard(Zone.HAND, playerA, "Stonecoil Serpent", 1); // Reach, trample, protection from multicolored
        addCard(Zone.HAND, playerA, "Codespell Cleric", 1); // Vigilance

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Ornithopter^Rograkh, Son of Rohgahh^Adorned Pouncer^Ankle Biter^Gladecover Scout" +
                "^Banehound^Bontu the Glorified^Aerial Responder^Stonecoil Serpent^Codespell Cleric");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FIRST_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DOUBLE_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DEATHTOUCH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HEXPROOF, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HASTE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.INDESTRUCTIBLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.LIFELINK, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.MENACE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.REACH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.TRAMPLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.VIGILANCE, 1);

        assertHandCount(playerA, 12);

    }

    @Test
    public void testIndominusRexAlphaSubset() {

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);
        addCard(Zone.HAND, playerA, "Ornithopter", 1); // Flying
        addCard(Zone.HAND, playerA, "Rograkh, Son of Rohgahh", 1); // First strike, menace, trample, Partner
        addCard(Zone.HAND, playerA, "Adorned Pouncer", 1); // Double strike, Eternalize
        addCard(Zone.HAND, playerA, "Ankle Biter", 1); // Deathtouch
        addCard(Zone.HAND, playerA, "Banehound", 1); // Lifelink, haste
        addCard(Zone.HAND, playerA, "Bontu the Glorified", 1); // Menace, indestructible
        addCard(Zone.HAND, playerA, "Aerial Responder", 1); // Flying, vigilance, lifelink
        addCard(Zone.HAND, playerA, "Codespell Cleric", 1); // Vigilance

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Ornithopter^Rograkh, Son of Rohgahh^Adorned Pouncer^Ankle Biter" +
                "^Banehound^Bontu the Glorified^Aerial Responder^Codespell Cleric");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FIRST_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DOUBLE_STRIKE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.DEATHTOUCH, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HASTE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.INDESTRUCTIBLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.LIFELINK, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.MENACE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.TRAMPLE, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.VIGILANCE, 1);

        assertHandCount(playerA, 10);

    }

    @Test
    public void testIndominusRexAlphaDiscardReplacement() {

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);

        // If a spell or ability an opponent controls causes you to discard Nullhide Ferox,
        // put it onto the battlefield instead of putting it into your graveyard.
        addCard(Zone.HAND, playerA, "Nullhide Ferox"); // Hexproof

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Nullhide Ferox");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HEXPROOF, 1);

        // Since the ability causing the discard was also owned by player A, Nullhide Ferox should not trigger,
        // and it will be in the graveyard.
        assertPermanentCount(playerA, "Nullhide Ferox", 0);
        assertGraveyardCount(playerA, "Nullhide Ferox", 1);

        assertHandCount(playerA, 1);

    }

    @Test
    public void testIndominusRexAlphaMadness() {

        addCard(Zone.HAND, playerA, "Indominus Rex, Alpha", 1);

        // Flying, haste
        // Madness {B} (If you discard this card, discard it into exile. When you do, cast it for its madness cost
        // or put it into your graveyard.)
        addCard(Zone.HAND, playerA, "Kitchen Imp");

        addCard(Zone.LIBRARY, playerA, "Swamp", 20);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indominus Rex, Alpha", true);

        // Cards to discard
        setChoice(playerA, "Kitchen Imp");

        // Pick madness cast to happen first
        setChoice(playerA, "When this card");

        // Cast for madness
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Indominus Rex, Alpha", CounterType.HASTE, 1);

        // Check that madness resulted in cast
        assertPermanentCount(playerA, "Kitchen Imp", 1);
        assertGraveyardCount(playerA, "Kitchen Imp", 0);

        assertHandCount(playerA, 2);

    }

}
