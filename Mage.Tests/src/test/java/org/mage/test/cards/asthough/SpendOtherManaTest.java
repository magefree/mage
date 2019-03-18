
package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
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
     *
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
     *
     * -Cast Dark Petition with spell mastery -Attempt to cast Nissa, Voice of
     * Zendikar using the triple black mana from Dark Petition
     */
    @Test
    public void testOathOfNissaWithDarkPetition() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // When Oath of Nissa enters the battlefield, look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
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
        setChoice(playerA, "Nissa, Voice of Zendikar");
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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hostage Taker");
        setChoice(playerA, "Silvercoat Lion");

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {R}."); // red mana to pool
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {R}."); // red mana to pool
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion"); // cast it from exile with red mana from pool

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hostage Taker", 1);
        assertTappedCount("Mountain", true, 4);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

}
