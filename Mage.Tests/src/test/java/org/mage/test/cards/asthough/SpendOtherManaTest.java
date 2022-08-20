package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class SpendOtherManaTest extends CardTestPlayerBase {

    /**
     * Mycosynth Lattice doesn't work for floating mana with activated abillites
     * I was trying to activate Sydri, Galvanic Genius with a floating {C}
     * targeting a mountain when I clicked on the <> icon it wouldn't spend the
     * mana.
     */
    @Test
    public void testColorlessCanBeUsed() {
        // All permanents are artifacts in addition to their other types.
        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        // Players may spend mana as though it were mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Mycosynth Lattice");

        // {U}: Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn.
        // {W}{B}: Target artifact creature gains deathtouch and lifelink until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Sydri, Galvanic Genius");
        //{T}: Add {C}. ( represents colorless mana.)
        // {1}, {T}: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Unknown Shores");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}: Target noncreature artifact becomes an artifact creature with power and toughness", "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Unknown Shores", true);

        assertPermanentCount(playerB, "Mountain", 0);
    }

    /**
     * Tron mana doesn't work with Oath of Nissa. (e.g. can't cast Chandra,
     * Flamecaller with Urza's Tower, Power Plant, and Mine.)
     * <p>
     * AI don't get the Planeswalker as playable card (probably because of the
     * as thought effect)
     */
    @Test
    public void testOathOfNissa() {
        // When Oath of Nissa enters the battlefield, look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        // You may spend mana as though it were mana of any color to cast planeswalker spells.
        addCard(Zone.BATTLEFIELD, playerA, "Oath of Nissa");
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Mine");
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Tower");
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Power Plant");
        // +1: Put two 3/1 red Elemental creature tokens with haste onto the battlefield. Exile them at the beginning of the next end step.
        // 0: Discard all the cards in your hand, then draw that many cards plus one.
        // -X: Chandra, Flamecaller deals X damage to each creature.
        addCard(Zone.HAND, playerA, "Chandra, Flamecaller"); // {4}{R}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra, Flamecaller");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chandra, Flamecaller", 1);
    }

    /**
     * I was unable to cast Nissa, Voice of Zendikar using black mana with Oath
     * of Nissa in play. Pretty sure Oath is working usually, so here were the
     * conditions in my game:
     * <p>
     * 1. Cast Dark Petition with spell mastery
     * 2. Attempt to cast Nissa, Voice of Zendikar using the triple black mana from Dark Petition
     */
    @Test
    public void testOathOfNissaWithDarkPetition() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // When Oath of Nissa enters the battlefield, look at the top three cards of your library.
        // You may reveal a creature, land, or planeswalker card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        // You may spend mana as though it were mana of any color to cast planeswalker spells.
        addCard(Zone.BATTLEFIELD, playerA, "Oath of Nissa");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 2);

        // Search your library for a card and put that card into your hand. Then shuffle your library.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, add {B}{B}{B}.
        addCard(Zone.HAND, playerA, "Dark Petition"); // {3}{B}{B}

        // +1: Create a 0/1 green Plant creature token onto the battlefield.
        // -2: Put a +1/+1 counter on each creature you control.
        // -7: You gain X life and draw X cards, where X is the number of lands you control.
        addCard(Zone.LIBRARY, playerA, "Nissa, Voice of Zendikar"); // {1}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dark Petition");
        addTarget(playerA, "Nissa, Voice of Zendikar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nissa, Voice of Zendikar");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dark Petition", 1);
        assertHandCount(playerA, "Nissa, Voice of Zendikar", 0);
        assertPermanentCount(playerA, "Nissa, Voice of Zendikar", 1);
    }

    @Test
    public void testUseSpendManaAsThoughWithManaFromPool() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature {1}{W}

        // When Hostage Taker enters the battlefield, exile another target artifact or creature until Hostage Taker leaves the battlefield.
        // You may cast that card as long as it remains exiled, and you may spend mana as though it were mana of any type to cast that spell.
        addCard(Zone.HAND, playerA, "Hostage Taker"); // {2}{U}{B}

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}.");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}.");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}.");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}.");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hostage Taker");
        addTarget(playerA, "Silvercoat Lion");

        // red mana must be used as any mana
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {R}."); // red mana to pool
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {R}."); // red mana to pool
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion"); // cast it from exile with red mana from pool

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hostage Taker", 1);
        assertTappedCount("Mountain", true, 4);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void test_QuicksilverElemental_Normal() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // {U}: Quicksilver Elemental gains all activated abilities of target creature until end of turn.
        // You may spend blue mana as though it were mana of any color to pay the activation costs of Quicksilver Elemental’s abilities.
        addCard(Zone.BATTLEFIELD, playerA, "Quicksilver Elemental"); // Creature {1}{W}
        // {R}, {T}: Anaba Shaman deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Anaba Shaman");

        // gain abilities
        checkPlayableAbility("must not have", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}, {T}:", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}:", "Anaba Shaman");

        // use ability
        checkPlayableAbility("must have new ability", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}, {T}:", true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}, {T}:", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_QuicksilverElemental_Flicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // {U}: Quicksilver Elemental gains all activated abilities of target creature until end of turn.
        // You may spend blue mana as though it were mana of any color to pay the activation costs of Quicksilver Elemental’s abilities.
        addCard(Zone.BATTLEFIELD, playerA, "Quicksilver Elemental"); // Creature {1}{W}
        // {R}, {T}: Anaba Shaman deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Anaba Shaman");
        // Exile target nontoken permanent, then return it to the battlefield under its owner’s control.
        addCard(Zone.HAND, playerA, "Flicker"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // gain abilities
        checkPlayableAbility("must not have", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}, {T}:", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}:", "Anaba Shaman");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("must have new ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}, {T}:", true);

        // renew target
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flicker", "Anaba Shaman");

        // use ability
        checkPlayableAbility("must save ability", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}, {T}:", true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}, {T}:", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Flicker", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
    }

    /**
     * Chromatic Orrery allows it's controller to spend mana of any color as
     * though it were mana of any color. With mana from Food Chain I should be
     * able to cast creature spells using abritrary color of mana. But the game
     * still requires to pay appropriate color as though there was no Orrery on
     * my side of battlefield.
     */
    @Test
    public void testFoodChainWithChromaticOrrery() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Adriana, Captain of the Guard", 1); // Creature {3}{R}{W}

        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1); // Creature {3}{W}

        // Exile a creature you control: Add X mana of any one color, where X is the exiled creature's converted mana cost plus one.
        // Spend this mana only to cast creature spells.
        addCard(Zone.BATTLEFIELD, playerA, "Food Chain"); // Enchantment {2}{G}

        // You may spend mana as though it were mana of any color.
        // {T}: Add {C}{C}{C}{C}{C}.
        // {5}, {T}: Draw a card for each color among permanents you control.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Orrery"); // Artifact {7}
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile a creature you control");
        setChoice(playerA, "Pillarfield Ox");
        setChoice(playerA, "Red");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adriana, Captain of the Guard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Assert.assertTrue("Mana pool of conditional mana has to be empty", playerA.getManaPool().getConditionalMana().isEmpty());
        assertExileCount("Pillarfield Ox", 1);
        assertPermanentCount(playerA, "Adriana, Captain of the Guard", 1);
    }

    @Test
    public void testChromaticOrrery() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Adriana, Captain of the Guard", 1); // Creature {3}{R}{W}

        // You may spend mana as though it were mana of any color.
        // {T}: Add {C}{C}{C}{C}{C}.
        // {5}, {T}: Draw a card for each color among permanents you control.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Orrery"); // Artifact {7}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adriana, Captain of the Guard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Adriana, Captain of the Guard", 1);
    }
}
