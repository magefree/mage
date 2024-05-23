package org.mage.test.cards.single.mh3;

import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GristVoraciousLarvaTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GristVoraciousLarva Grist, Voracious Larva} {G}
     * Legendary Creature — Insect
     * Deathtouch
     * Whenever Grist, Voracious Larva or another creature enters the battlefield under your control, if it entered from your graveyard or you cast it from your graveyard, you may pay {G}. If you do, exile Grist, then return it to the battlefield transformed under its owner’s control.
     * 1/2
     * // {@link mage.cards.g.GristThePlagueSwarm Grist, the Plague Swarm}
     * Legendary Planeswalker — Grist
     * +1: Create a 1/1 black and green Insect creature token, then mill two cards. Put a deathtouch counter on the token if a black card was milled this way.
     * −2: Destroy target artifact or enchantment.
     * −6: For each creature card in your graveyard, create a token that’s a copy of it, except it’s a 1/1 black and green Insect.
     * Loyalty: 3
     */
    private static final String grist = "Grist, Voracious Larva";
    private static final String gristPW = "Grist, the Plague Swarm";

    @Test
    public void test_Unearth_Trigger_NoMana() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Unearth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth", grist);
        setChoice(playerA, false); // can't pay for the trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, grist, 1);
    }

    @Test
    public void test_Unearth_Trigger_Pay() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Unearth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth", grist);
        setChoice(playerA, true); // pay for the trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, gristPW, 1);
    }

    @Test
    public void test_Unearth_Trigger_NoPay() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Unearth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth", grist);
        setChoice(playerA, false); // don't pay for the trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, grist, 1);
    }

    @Test
    public void test_Bloodghast_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, grist);
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.GRAVEYARD, playerA, "Bloodghast");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        setChoice(playerA, true); // yes to Bloodghast's trigger
        setChoice(playerA, true); // pay for Grist's trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, gristPW, 1);
    }

    @Test
    public void test_CastBloodghast_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3);
        addCard(Zone.HAND, playerA, "Bloodghast");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodghast");
        // no Grist's trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, grist, 1);
        assertPermanentCount(playerA, "Bloodghast", 1);
    }

    @Test
    public void test_CastGravecrawler_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Gravecrawler");
        addCard(Zone.GRAVEYARD, playerA, "Gravecrawler");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gravecrawler");
        setChoice(playerA, true); // pay for Grist's trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, gristPW, 1);
        assertPermanentCount(playerA, "Gravecrawler", 2);
    }

    @Test
    public void test_Cast_NonCreature_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide"); // During each of your turns, you may play a land and cast a permanent spell of each permanent type from your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Mox Jet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Jet");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, grist, 1);
        assertPermanentCount(playerA, "Mox Jet", 1);
    }

    @Test
    public void test_Play_DryadArbor_FromGraveyard_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, grist);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide"); // During each of your turns, you may play a land and cast a permanent spell of each permanent type from your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Dryad Arbor");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dryad Arbor");
        setChoice(playerA, "Land"); // Choose to consume the Land part of Muldrotha, "Creature" would have not worked anyway since Dryad Arbor can not be cast.
        setChoice(playerA, true); // pay for Grist's trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, gristPW, 1);
        assertPermanentCount(playerA, "Dryad Arbor", 1);
    }

    @Test
    public void test_PlusOne_NoLibrary() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, gristPW);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, gristPW, 1);
        assertCounterCount(playerA, gristPW, CounterType.LOYALTY, 3 + 1);
        assertPermanentCount(playerA, "Insect Token", 1);
        assertCounterCount(playerA, "Insect Token", CounterType.DEATHTOUCH, 0);
    }

    @Test
    public void test_PlusOne_MillNonBlack() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, gristPW);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, gristPW, 1);
        assertCounterCount(playerA, gristPW, CounterType.LOYALTY, 3 + 1);
        assertPermanentCount(playerA, "Insect Token", 1);
        assertCounterCount(playerA, "Insect Token", CounterType.DEATHTOUCH, 0);
    }

    @Test
    public void test_PlusOne_MillBlack() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, gristPW);
        addCard(Zone.LIBRARY, playerA, "Blood Artist", 1);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, gristPW, 1);
        assertCounterCount(playerA, gristPW, CounterType.LOYALTY, 3 + 1);
        assertPermanentCount(playerA, "Insect Token", 1);
        assertCounterCount(playerA, "Insect Token", CounterType.DEATHTOUCH, 1);
    }

    @Test
    public void test_PlusOne_MillBlack_Chatterfang() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, gristPW);
        addCard(Zone.BATTLEFIELD, playerA, "Chatterfang, Squirrel General"); // If one or more tokens would be created under your control, those tokens plus that many 1/1 green Squirrel creature tokens are created instead.
        addCard(Zone.LIBRARY, playerA, "Blood Artist", 1);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, gristPW, 1);
        assertCounterCount(playerA, gristPW, CounterType.LOYALTY, 3 + 1);
        assertPermanentCount(playerA, "Insect Token", 1);
        assertCounterCount(playerA, "Insect Token", CounterType.DEATHTOUCH, 1);
        assertPermanentCount(playerA, "Squirrel Token", 1);
        assertCounterCount(playerA, "Squirrel Token", CounterType.DEATHTOUCH, 1);
    }

    @Test
    public void test_Minus6() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, gristPW);
        addCard(Zone.GRAVEYARD, playerA, "Dryad Arbor", 1);
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom", 1);
        addCard(Zone.GRAVEYARD, playerA, "Taiga", 1);
        addCard(Zone.GRAVEYARD, playerA, "Baneslayer Angel", 1);
        addCard(Zone.GRAVEYARD, playerA, "Keranos, God of Storms", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grist, the Hunger Tide", 1);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, gristPW, CounterType.LOYALTY, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-6");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 6); // cards are still there
        assertPermanentCount(playerA, 1 + 4);
        assertCounterCount(playerA, gristPW, CounterType.LOYALTY, 1);

        // None of those are creature in graveyard
        assertPermanentCount(playerA, "Bitterblossom", 0);
        assertPermanentCount(playerA, "Taiga", 0);

        // All of those are creature in graveyard
        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPermanentCount(playerA, "Baneslayer Angel", 1);
        assertPermanentCount(playerA, "Keranos, God of Storms", 1);
        assertPermanentCount(playerA, "Grist, the Hunger Tide", 1);

        // Dryad Arbor is still a Land Forest, but no longer Dryad
        assertPowerToughness(playerA, "Dryad Arbor", 1, 1);
        assertType("Dryad Arbor", CardType.CREATURE, true);
        assertType("Dryad Arbor", CardType.LAND, true);
        assertSubtype("Dryad Arbor", SubType.INSECT);
        assertNotSubtype("Dryad Arbor", SubType.DRYAD);
        assertSubtype("Dryad Arbor", SubType.FOREST);
        assertColor(playerA, "Dryad Arbor", "{G}{B}", true);

        // Baneslayer Angel is now a Baneslayer Insect
        assertPowerToughness(playerA, "Baneslayer Angel", 1, 1);
        assertType("Baneslayer Angel", CardType.CREATURE, true);
        assertSubtype("Baneslayer Angel", SubType.INSECT);
        assertNotSubtype("Baneslayer Angel", SubType.ANGEL);
        assertColor(playerA, "Baneslayer Angel", "{G}{B}", true);
        assertColor(playerA, "Baneslayer Angel", ObjectColor.WHITE, false);

        // Keranos token is just an Enchantment
        assertType("Keranos, God of Storms", CardType.CREATURE, false);
        assertType("Keranos, God of Storms", CardType.ENCHANTMENT, true);
        assertNotSubtype("Keranos, God of Storms", SubType.INSECT);
        assertColor(playerA, "Keranos, God of Storms", "{G}{B}", true);
        assertColor(playerA, "Baneslayer Angel", "{R}{U}", false);

        // Grist, the Hunger Tide is not a Creature (It would become a 1/1 Insect if animated somehow with an effect preserving its p/t/subtype)
        assertType("Grist, the Hunger Tide", CardType.CREATURE, false);
        assertType("Grist, the Hunger Tide", CardType.PLANESWALKER, true);
        assertNotSubtype("Grist, the Hunger Tide", SubType.INSECT);
        assertSubtype("Grist, the Hunger Tide", SubType.GRIST);
        assertColor(playerA, "Grist, the Hunger Tide", "{G}{B}", true);
    }
}
