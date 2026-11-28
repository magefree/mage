package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801, JayDi85
 */
public class WaterbendTest extends CardTestPlayerBase {

    private static final String waterbender = "Flexible Waterbender";

    @Test
    public void testJustMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, waterbender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, waterbender, 5, 2);
        assertTapped(waterbender, false);
        assertTapped("Island", true);
    }

    private static final String relic = "Darksteel Relic";

    @Test
    public void testNoMana() {
        addCard(Zone.BATTLEFIELD, playerA, relic, 2);
        addCard(Zone.BATTLEFIELD, playerA, waterbender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, waterbender);
        setChoice(playerA, relic, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, waterbender, 5, 2);
        assertTapped(waterbender, true);
        assertTapped(relic, true);
    }

    @Test
    public void testManaAndCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, waterbender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, waterbender);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, waterbender, 5, 2);
        assertTapped(waterbender, true);
        assertTapped("Island", true);
    }

    private static final String katara = "Katara, Water Tribe's Hope";

    @Test
    public void testX() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, relic, 2);
        addCard(Zone.BATTLEFIELD, playerA, katara);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, "X=4");
        setChoice(playerA, relic, 2);
        setChoice(playerA, katara);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, katara, 4, 4);
        assertTapped(relic, true);
        assertTapped(katara, true);
        assertTapped("Island", true);
    }

    private static final String spirit = "Benevolent River Spirit";

    @Test
    public void testSpellCost() {
        removeAllCardsFromLibrary(playerA); // removes need to make scry choices
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, relic, 3);
        addCard(Zone.HAND, playerA, spirit);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spirit);
        setChoice(playerA, relic, 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Island", true);
        assertTapped(relic, true);
        assertTapped(spirit, false);
    }

    @Test
    public void testCostReduction() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Arcane Melee");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Spirit Water Revival");
        addCard(Zone.HAND, playerA, "Grizzly Bears", 7);

        // cost should be reduced from {1}{U}{U}{waterbend(6)} -> {U}{U}{waterbend(5)} with bears paying for 1
        // so 6 free mana needed
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spirit Water Revival");
        setChoice(playerA, true);
        setChoice(playerA, "Balduvian Bears");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // we drew 7 cards
        assertHandCount(playerA, 14);
    }

    @Test
    public void test_WaterbendX_CastWithOmniscience_StillAnnouncesAndPaysWaterbendX() {
        // You may cast spells from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        // As an additional cost to cast this spell, waterbend {X}.
        // Exile X target creatures you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Waterbender's Restoration"); // {U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // 2 creatures to exile & tap for waterbend {2}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Waterbender's Restoration");
        setChoice(playerA, "Cast without paying its mana cost"); // Omniscience alternative cost
        setChoice(playerA, "X=2"); // announce X for additional cost waterbend {X}
        addTarget(playerA, "Grizzly Bears", 2); // exile 2 creatures
        setChoice(playerA, "Grizzly Bears", 2); // tap 2 creatures to pay waterbend {2}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Waterbender's Restoration", 1);
        assertExileCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_WaterbendX_CastWithMiracle_StillPaysWaterbendX() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // As an additional cost to cast this spell, waterbend {X}.
        // Exile X target creatures you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step.
        addCard(Zone.LIBRARY, playerA, "Waterbender's Restoration"); // {U}{U} -> miracle {2} + waterbend {X}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // pays miracle {2}
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // tapped for waterbend {2}, then exiled

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Waterbender's Restoration for miracle
        setChoice(playerA, true); // cast for miracle {2}
        setChoice(playerA, "X=2"); // announce X for additional cost waterbend {X}
        addTarget(playerA, "Grizzly Bears", 2); // exile 2 creatures
        setChoice(playerA, "Grizzly Bears", 2); // tap 2 creatures to pay waterbend {2}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Waterbender's Restoration", 1);
        assertExileCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertTappedCount("Plains", true, 2);
    }
}
