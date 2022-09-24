package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 *
 * @author LevelX2
 */
public class DeliriumTest extends CardTestPlayerBaseWithAIHelps {

    // Delirium - if there are four or more card types among cards in your graveyard
    @Test
    public void noDeliriumTest() {
        /**
         * 4/8/2016 If you have three non-sorcery card types among cards in your
         * graveyard at the time Descend upon the Sinful resolves, you won’t get
         * an Angel token. Descend upon the Sinful isn’t put into your graveyard
         * until after it’s finished resolving.
         */

        // Exile all creatures
        // Delirium - Create a 4/4 white Angel creature token with flying if there are four or more card types among cards in your graveyard.
        addCard(Zone.HAND, playerA, "Descend upon the Sinful", 1); // Sorcery {4}{W}{W}

        addCard(Zone.GRAVEYARD, playerA, "Thought Vessel", 2); // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Tempered Steel", 2); // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2); // Creature

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Descend upon the Sinful");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Balduvian Bears", 4);
        assertGraveyardCount(playerA, "Descend upon the Sinful", 1);

        assertPermanentCount(playerA, "Angel Token", 0);

    }

    @Test
    public void ItsDeliriumTest() {
        /**
         * 4/8/2016 The number of card types matters, not the number of cards.
         * For example, Wicker Witch (an artifact creature) along with Catalog
         * (an instant) and Chaplain’s Blessing (a sorcery) will enable
         * delirium.
         */
        // Exile all creatures
        // Delirium - Create a 4/4 white Angel creature token with flying if there are four or more card types among cards in your graveyard.
        addCard(Zone.HAND, playerA, "Descend upon the Sinful", 1); // Sorcery {4}{W}{W}

        addCard(Zone.GRAVEYARD, playerA, "Wicker Witch", 1); // Artifact Creature
        addCard(Zone.GRAVEYARD, playerA, "Catalog", 1); // Instant
        addCard(Zone.GRAVEYARD, playerA, "Chaplain's Blessing", 1); // Sorcery

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Descend upon the Sinful");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Balduvian Bears", 4);
        assertGraveyardCount(playerA, "Descend upon the Sinful", 1);

        assertPermanentCount(playerA, "Angel Token", 1);

    }

    @Test
    public void noDeliriumWithCreatureEnchantmentTest() {

        /**
         * 4/8/2016 If one of those creatures was enchanted, its Aura won’t be
         * put into a player’s graveyard until after Descend upon the Sinful has
         * finished resolving. If the controller of Descend upon the Sinful
         * owned the Aura, it won’t be in the graveyard in time to be counted
         * for the delirium ability.
         */
        // Exile all creatures
        // Delirium - Create a 4/4 white Angel creature token with flying if there are four or more card types among cards in your graveyard.
        addCard(Zone.HAND, playerA, "Descend upon the Sinful", 1); // Sorcery {4}{W}{W}

        // Enchant creature
        // Enchanted creature gets +1/+1 and has protection from creatures.
        addCard(Zone.HAND, playerA, "Spirit Mantle", 1); // Enchantment Aura {1}{W}

        addCard(Zone.GRAVEYARD, playerA, "Thought Vessel", 2); // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Plains", 2); // Land

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spirit Mantle", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Descend upon the Sinful");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertExileCount(playerA, "Balduvian Bears", 4);
        assertGraveyardCount(playerA, "Spirit Mantle", 1);
        assertGraveyardCount(playerA, "Descend upon the Sinful", 1);

        assertPermanentCount(playerA, "Angel Token", 0);

    }

    @Test
    public void noDeliriumWithDoubleFacedCardsTest() {

        /**
         * 4/8/2016 Because you consider only the characteristics of a
         * double-faced card’s front face while it’s not on the battlefield, the
         * types of its back face won’t be counted for delirium.
         */
        // Exile all creatures
        // Delirium - Create a 4/4 white Angel creature token with flying if there are four or more card types among cards in your graveyard.
        addCard(Zone.HAND, playerA, "Descend upon the Sinful", 1); // Sorcery {4}{W}{W}

        addCard(Zone.GRAVEYARD, playerA, "Elbrus, the Binding Blade", 1); // Artifact (not counted as Creature from Backside)
        addCard(Zone.GRAVEYARD, playerA, "Catalog", 1); // Instant
        addCard(Zone.GRAVEYARD, playerA, "Plains", 1); // Land

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Descend upon the Sinful");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertExileCount(playerA, "Balduvian Bears", 4);
        assertGraveyardCount(playerA, "Descend upon the Sinful", 1);

        assertPermanentCount(playerA, "Angel Token", 0);

    }

    /**
     * 4/8/2016 In some rare cases, you can have a token or a copy of a spell in
     * your graveyard at the moment that an object’s delirium ability counts the
     * card types among cards in your graveyard, before that token or copy
     * ceases to exist. Because tokens and copies of spells are not cards, even
     * if they are copies of cards, their types will never be counted.
     */
    
    // No test added yet
}
