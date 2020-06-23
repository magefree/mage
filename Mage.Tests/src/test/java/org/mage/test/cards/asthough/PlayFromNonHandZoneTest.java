package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class PlayFromNonHandZoneTest extends CardTestPlayerBase {

    @Test
    public void testWorldheartPhoenixNormal() {
        // Creature - Phoenix {3}{R}
        // Flying
        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Worldheart Phoenix");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldheart Phoenix"); // can only be cast by {W}{U}{B}{R}{G}

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Worldheart Phoenix", 2, 2);

    }

    @Test
    public void testWorldheartPhoenixNoMana() {
        // Creature - Phoenix {3}{R}
        // Flying
        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        addCard(Zone.GRAVEYARD, playerA, "Worldheart Phoenix");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldheart Phoenix"); // can only be cast by {W}{U}{B}{R}{G}

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Worldheart Phoenix", 0);

    }

    @Test
    public void testWorldheartPhoenix() {
        // Creature - Phoenix {3}{R}
        // Flying
        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        addCard(Zone.GRAVEYARD, playerA, "Worldheart Phoenix");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldheart Phoenix"); // can only be cast by {W}{U}{B}{R}{G}

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Worldheart Phoenix", 1);

    }

    @Test
    public void testNarsetEnlightenedMaster() {
        // First strike
        // Hexproof
        // Whenever Narset, Enlightented Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Narset, Enlightened Master", 1);

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerB, "Abzan Banner");
        // Ferocious - If you control a creature with power 4 or greater, you may cast Dragon Grip as though it had flash. (You may cast it any time you could cast an instant.)
        // Enchant creature
        // Enchanted creature gets +2/+0 and has first strike.
        addCard(Zone.LIBRARY, playerB, "Dragon Grip");
        // You gain 2 life for each creature you control.
        addCard(Zone.LIBRARY, playerB, "Peach Garden Oath");
        addCard(Zone.LIBRARY, playerB, "Plains");

        attack(2, playerB, "Narset, Enlightened Master");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Silvercoat Lion"); // can't be cast from exile
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Abzan Banner"); // can be cast from exile
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Dragon Grip", "Narset, Enlightened Master"); // can be cast from exile
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Peach Garden Oath"); // can be cast from exile

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Abzan Banner", 1);
        assertGraveyardCount(playerB, "Peach Garden Oath", 1);
        assertExileCount(playerB, "Dragon Grip", 0);
        assertGraveyardCount(playerB, "Dragon Grip", 0);

        assertPowerToughness(playerB, "Narset, Enlightened Master", 5, 2);

        assertHandCount(playerB, "Plains", 1);
        assertLife(playerA, 17);
        assertLife(playerB, 22);

        assertPermanentCount(playerB, "Dragon Grip", 1);

    }

    @Test
    public void testNarsetEnlightenedMasterAdditionalCost() {
        // First strike
        // Hexproof
        // Whenever Narset, Enlightented Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Narset, Enlightened Master", 1);
        addCard(Zone.HAND, playerB, "Swamp");
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerB, "Cathartic Reunion");
        addCard(Zone.LIBRARY, playerB, "Forest");

        attack(2, playerB, "Narset, Enlightened Master");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cathartic Reunion");
        setChoice(playerB, "Swamp^Forest");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, 3);
        assertGraveyardCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Swamp", 1);
        assertGraveyardCount(playerB, "Cathartic Reunion", 1);
        assertGraveyardCount(playerB, 3);
        assertExileCount(playerB, "Plains", 3);
        assertExileCount(playerB, 3);

    }

    /**
     * Kess, Dissident Mage and Fire/Ice - When you cast fire/ice from your
     * graverayr with kess it doesn't exile it self
     */
    @Test
    public void testKessWithSpliCard() {
        // Flying
        // During each of your turns, you may cast an instant or sorcery card from your graveyard. If a card cast this way would be put into your graveyard this turn, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Kess, Dissident Mage", 1);

        // Fire {1}{R}
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        // Ice {1}{U}
        // Tap target permanent.
        // Draw a card.
        addCard(Zone.GRAVEYARD, playerA, "Fire // Ice");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertExileCount(playerA, "Fire // Ice", 1);

    }

    /**
     * Can't cast Karn's Temporal Sundering when exiled with Golos, Tireless
     * Pilgrim
     */
    @Test
    public void castSunderingWithGolosTest() {
        // When Golos, Tireless Pilgrim enters the battlefield, you may search your library for a land card, put that card onto the battlefield tapped, then shuffle your library.
        // {2}{W}{U}{B}{R}{G}: Exile the top three cards of your library. You may play them this turn without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Golos, Tireless Pilgrim", 1);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        // Target player takes an extra turn after this one. Return up to one target nonland permanent to its owner's hand. Exile Karn's Temporal Sundering.
        addCard(Zone.LIBRARY, playerA, "Karn's Temporal Sundering"); // Sorcery
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{W}{U}{B}{R}{G}: Exile");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn's Temporal Sundering");
        addTarget(playerA, playerA);
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Mountain", 2);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertExileCount(playerA, "Karn's Temporal Sundering", 1);

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertExileCount(playerA, 1);

        assertActivePlayer(playerA);
    }

    /**
     * Can't cast Karn's Temporal Sundering when exiled with Golos, Tireless
     * Pilgrim
     */
    @Test
    public void castSunderingWithGolos2Test() {
        // When Golos, Tireless Pilgrim enters the battlefield, you may search your library for a land card, put that card onto the battlefield tapped, then shuffle your library.
        // {2}{W}{U}{B}{R}{G}: Exile the top three cards of your library. You may play them this turn without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Golos, Tireless Pilgrim", 1);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        // Target player takes an extra turn after this one. Return up to one target nonland permanent to its owner's hand. Exile Karn's Temporal Sundering.
        addCard(Zone.LIBRARY, playerA, "Karn's Temporal Sundering"); // Sorcery
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{W}{U}{B}{R}{G}: Exile");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn's Temporal Sundering");
        addTarget(playerA, playerA);
        addTarget(playerA, "Golos, Tireless Pilgrim"); // Return to hand

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Mountain", 2);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertExileCount(playerA, "Karn's Temporal Sundering", 1);

        assertHandCount(playerA, "Golos, Tireless Pilgrim", 1);
        assertExileCount(playerA, 1);

        assertActivePlayer(playerA);
    }

    /**
     * #6580
     * Fallen Shinobi - In the second log, when Tormenting Voice is cast first,
     * the discard was required. In the first log, when it was cast after
     * Angelic Purge the discard was not required.
     */

    @Test
    public void castFromExileButWithAdditionalCostTest() {
        // Ninjutsu {2}{U}{B}
        // Whenever Fallen Shinobi deals combat damage to a player, that player exiles the top two cards 
        // of their library. Until end of turn, you may play those cards without paying their mana cost.
        addCard(Zone.BATTLEFIELD, playerB, "Fallen Shinobi", 1); // Creature 5/4
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.HAND, playerB, "Pillarfield Ox");

        addCard(Zone.LIBRARY, playerB, "Pillarfield Ox"); // Card to draw on turn 2
        // As an additional cost to cast Tormenting Voice, discard a card.
        // Draw two cards.        
        addCard(Zone.LIBRARY, playerA, "Tormenting Voice"); // Sorcery {1}{R}        
        // As an additional cost to cast this spell, sacrifice a creature.
        // Flying, Trample        
        addCard(Zone.LIBRARY, playerA, "Demon of Catastrophes"); // Creature {2}{B}{B}  6/6

        skipInitShuffling();

        attack(2, playerB, "Fallen Shinobi");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Tormenting Voice");
        setChoice(playerB, "Pillarfield Ox"); // Discord for Tormenting Voice

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Demon of Catastrophes");
        setChoice(playerB, "Silvercoat Lion"); // Sacrifice for Demon

        setStopAt(2, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 15);
        assertPermanentCount(playerB, "Fallen Shinobi", 1);

        assertGraveyardCount(playerA, "Tormenting Voice", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);  // Discarded for Tormenting Voice


        assertPermanentCount(playerB, "Demon of Catastrophes", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);  // sacrificed for Demon

        assertHandCount(playerB, "Pillarfield Ox", 1);
        assertHandCount(playerB, 3); // 2 from Tormenting Voice + 1 from Turn 2
        assertExileCount(playerA, 0); // Both exiled cards are cast
    }


    @Test
    public void castFromExileButWithAdditionalCost2Test() {
        // Ninjutsu {2}{U}{B}
        // Whenever Fallen Shinobi deals combat damage to a player, that player exiles the top two cards 
        // of their library. Until end of turn, you may play those cards without paying their mana cost.
        addCard(Zone.BATTLEFIELD, playerB, "Fallen Shinobi", 1); // Creature 5/4
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.HAND, playerB, "Pillarfield Ox");

        addCard(Zone.BATTLEFIELD, playerA, "Amulet of Kroog"); // Just to exile for Angelic Purge

        // As an additional cost to cast Tormenting Voice, discard a card.
        // Draw two cards.        
        addCard(Zone.LIBRARY, playerA, "Tormenting Voice"); // Sorcery {1}{R}        

        // As an additional cost to cast Angelic Purge, sacrifice a permanent.
        // Exile target artifact, creature, or enchantment.      
        addCard(Zone.LIBRARY, playerA, "Angelic Purge"); // Sorcery {2}{W}

        skipInitShuffling();

        attack(2, playerB, "Fallen Shinobi");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Purge");
        setChoice(playerB, "Silvercoat Lion"); // Sacrifice for Purge
        addTarget(playerB, "Amulet of Kroog"); // Exile with Purge

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Tormenting Voice");
        setChoice(playerB, "Pillarfield Ox"); // Discord for Tormenting Voice

        setStopAt(2, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 15);
        assertPermanentCount(playerB, "Fallen Shinobi", 1);

        assertGraveyardCount(playerA, "Angelic Purge", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);  // sacrificed for Purge

        assertGraveyardCount(playerA, "Tormenting Voice", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);  // Discarded for Tormenting Voice

        assertHandCount(playerB, 3); // 2 from Tormenting Voice + 1 from Turn 2 draw

        assertExileCount(playerA, 1); // Both exiled cards are cast
        assertExileCount(playerA, "Amulet of Kroog", 1); // Exiled with Purge
    }

    @Test
    public void castAdventureWithFallenShinobiTest() {
        // Ninjutsu {2}{U}{B}
        // Whenever Fallen Shinobi deals combat damage to a player, that player exiles the top two cards 
        // of their library. Until end of turn, you may play those cards without paying their mana cost.
        addCard(Zone.BATTLEFIELD, playerB, "Fallen Shinobi", 1); // Creature 5/4
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerA, "Amulet of Kroog"); // Just to exile for Angelic Purge

        /* Curious Pair {1}{G}
         * Creature — Human Peasant
         * 1/3
         * ----
         * Treats to Share {G}
         * Sorcery — Adventure
         * Create a Food token.
         */
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        // As an additional cost to cast Angelic Purge, sacrifice a permanent.
        // Exile target artifact, creature, or enchantment.      
        addCard(Zone.LIBRARY, playerA, "Angelic Purge"); // Sorcery {2}{W}

        skipInitShuffling();

        attack(2, playerB, "Fallen Shinobi");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Purge");
        setChoice(playerB, "Silvercoat Lion"); // Sacrifice for Purge
        addTarget(playerB, "Amulet of Kroog"); // Exile with Purge

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Treats to Share");

        setStopAt(2, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 15);
        assertPermanentCount(playerB, "Fallen Shinobi", 1);

        assertGraveyardCount(playerA, "Angelic Purge", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);  // sacrificed for Purge

        assertPermanentCount(playerB, "Food", 1);
        assertExileCount(playerA, "Curious Pair", 1);

        assertHandCount(playerB, 1); // 1 from Turn 2 draw

        assertExileCount(playerA, 2); // Both exiled cards are cast 
        assertExileCount(playerA, "Amulet of Kroog", 1); // Exiled with Purge        
    }

    @Test
    public void test_ActivateFromOpponentCreature() {
        // Players can’t search libraries. Any player may pay {2} for that player to ignore this effect until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Leonin Arbiter", 1);
        //
        // {3}{U}{U}
        // Search target opponent’s library for an artifact card and put that card onto the battlefield under your control. Then that player shuffles their library.
        addCard(Zone.HAND, playerA, "Acquire", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5 * 2 + 2);
        addCard(Zone.LIBRARY, playerB, "Alpha Myr", 1);

        // first cast -- can't search library
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Acquire", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // second cast -- unlock library and search
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Any player may");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        //
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Acquire", playerB);
        addTarget(playerA, "Alpha Myr");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Alpha Myr", 1);
    }
}
