package org.mage.test.cards.cost.alternate;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CastFromHandWithoutPayingManaCostTest extends CardTestPlayerBase {

    @Test
    public void testSpellNoCost() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);

        addCard(Zone.HAND, playerA, "Gray Ogre", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        //Gray Ogre is cast because it is free
        assertPermanentCount(playerA, "Gray Ogre", 1);
    }

    /**
     * Omniscience only lets you cast spells for free from your hand.
     * Haakon lets you cast knights from your graveyard.
     *
     * If you control both, you must still pay costs to cast knights from your graveyard.
     */
    @Test
    public void testSpellHasCostIfCastFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Haakon, Stromgald Scourge", 1);

        addCard(Zone.GRAVEYARD, playerA, "Knight of the White Orchid", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Knight of the White Orchid", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        //Knight of the White Orchid was not cast due to lack of mana
        assertPermanentCount(playerA, "Knight of the White Orchid", 0);
    }

    /**
     * If you cast a card with monocolored hybrid mana with Omniscience's
     * alternate casting cost, you will be asked to pay 1 colorless mana per
     * monocolored hybrid mana in its cost. For example, while casting Beseech
     * the Queen, you are asked to pay {1}{1}{1}.
     */
    @Test
    public void testMonocoloredHybridMana() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);

        // ({2B} can be paid with any two mana or with {B}. This card's converted mana cost is 6.)
        // Search your library for a card with converted mana cost less than or equal to the number of lands you control, reveal it, and put it into your hand. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Beseech the Queen", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beseech the Queen");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Beseech the Queen is cast because it is free
        assertGraveyardCount(playerA, "Beseech the Queen", 1);
    }

    @Test
    public void testColorlessMana() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);

        addCard(Zone.HAND, playerA, "Reality Smasher", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reality Smasher");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Relaity Smasher is cast because it is free
        assertPermanentCount(playerA, "Reality Smasher", 1);
    }

    @Test
    public void testCastingCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        /* player.getPlayable does not take alternate
         casting costs in account, so for the test the mana has to be available
         but won't be used
         */
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Plains", false);
    }

    @Test
    public void testCastingSplitCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        addCard(Zone.HAND, playerA, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire", playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fire // Ice", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

        assertTapped("Island", false);
        assertTapped("Mountain", false);
    }

    @Test
    public void testCastingShrapnelBlast() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        /* player.getPlayable does not take alternate
         casting costs in account, so for the test the mana has to be available
         but won't be used
         */
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);

        addCard(Zone.HAND, playerA, "Shrapnel Blast", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shrapnel Blast");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);

        assertGraveyardCount(playerA, "Ornithopter", 1);
        assertTapped("Mountain", false);
    }

    /**
     * Spell get cast for 0 if Omniscience is being in play. But with
     * Trinisphere it costs at least {3}. Cost/alternate cost (Omniscience) +
     * additional costs - cost reductions + minimum cost (Trinishpere) = total
     * cost.
     */
    @Test
    public void testCastingWithTrinisphere() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // As long as Trinisphere is untapped, each spell that would cost less than three mana
        // to cast costs three mana to cast. (Additional mana in the cost may be paid with any
        // color of mana or colorless mana. For example, a spell that would cost {1}{B} to cast
        // costs {2}{B} to cast instead.)
        addCard(Zone.BATTLEFIELD, playerB, "Trinisphere", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Plains", true); // plains have to be tapped because {3} have to be paid
    }

    /**
     * Omniscience is not allowing me to cast spells for free. I'm playing a
     * Commander game against the Computer, if that helps.
     *
     * Edit: It's not letting me cast fused spells for free. Others seems to be
     * working.
     */
    @Test
    public void testCastingFusedSpell() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        /*
         * Instant
         * Far {1}{U} Return target creature to its owner's hand.
         * Away{2}{B} Target player sacrifices a creature.
         * Fuse (You may cast one or both halves of this card from your hand.)
         */
        addCard(Zone.HAND, playerA, "Far // Away");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Far // Away");
        setChoice(playerA, true); // Cast without paying its mana cost?
        addTarget(playerA, "Silvercoat Lion");
        addTarget(playerA, playerB);
        playerB.addTarget("Pillarfield Ox");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerB, 0);

        assertGraveyardCount(playerA, "Far // Away", 1);

        assertPermanentCount(playerB, "Pillarfield Ox", 0);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
    }
    

    /**
     * Omniscience only lets you cast spells from your hand without paying their mana costs.
     * If another effect (e.g. Future Sight) allows you to cast nonland cards
     * from zones other than your hand, then you still have to pay the costs.
     */
    @Test
    public void testCastingWithFutureSight() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        // Play with the top card of your library revealed.
        // You may play the top card of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Future Sight", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);
        skipInitShuffling();

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Plains", true); // plains have to be tapped because {2} have to be paid
    }

    /**
     * If a spell has an additional cost (optional or mandatory, e.g. Entwine),
     * Omniscience incorrectly allows you cast the spell as if that cost had
     * been paid without paying that spell's mana cost. 117.9d If an alternative
     * cost is being paid to cast a spell, any additional costs, cost increases,
     * and cost reductions that affect that spell are applied to that
     * alternative cost. (See rule 601.2f.)
     */
    @Test
    public void testCastingWithCyclonicRiftWithOverload() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Choose one - Barbed Lightning deals 3 damage to target creature; or Barbed Lightning deals 3 damage to target player.
        // Entwine {2} (Choose both if you pay the entwine cost.)
        addCard(Zone.HAND, playerA, "Barbed Lightning", 1); // {2}{R}

        // Creature - 3/3 Swampwalk
        addCard(Zone.BATTLEFIELD, playerB, "Bog Wraith", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barbed Lightning");
        setChoice(playerA, true); // cast without cost
        setChoice(playerA, true); // pay Entwine
        addTarget(playerA, "Bog Wraith"); // target form mode 1
        addTarget(playerA, playerB); // target for mode 2

        showBattlefield("after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Barbed Lightning", 1);
        assertGraveyardCount(playerB, "Bog Wraith", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertTappedCount("Plains", true, 2); // plains have to be tapped because {2} from Entwine have to be paid
    }

    /**
     * If a spell has an unpayable cost (e.g. Ancestral Vision, which has no mana cost),
     * Omniscience should allow you to cast that spell without paying its mana cost.
     * In the case of Ancestral Vision, for example, Xmage only gives you the option to suspend Ancestral Vision.
     *
     * 118.6a   If an unpayable cost is increased by an effect or an additional cost is imposed,
     *          the cost is still unpayable.
     *          If an alternative cost is applied to an unpayable cost, including an effect that allows a player
     *          to cast a spell without paying its mana cost, the alternative cost may be paid.
     */
    @Test
    public void testCastingUnpayableCost() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        // Suspend 4-{U}
        // Target player draws three cards.
        addCard(Zone.HAND, playerA, "Ancestral Vision", 1);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Vision", playerA);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Ancestral Vision", 1);

        assertHandCount(playerA, 3);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    // Not sure what the exact interaction is, but when Omniscience is on the field with Jodah, 
    // if you say "no" to the Jodah cast option to get to the Omniscience option, then the game will initiate a rollback.     
    
    @Test
    public void test_OmniscienceAndJodah() {
        setStrictChooseMode(true);
       
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        
        // Flying
        // You may pay WUBRG rather than pay the mana cost for spells that you cast.        
        addCard(Zone.BATTLEFIELD, playerA, "Jodah, Archmage Eternal"); // Creature {1}{U}{R}{W} (4/3)

        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.HAND, playerA, "Omniscience"); // Enchantment {7}{U}{U}{U}
        
        // Creature - 3/3 Swampwalk
        addCard(Zone.HAND, playerA, "Bog Wraith", 1); // Creature {3}{B} (3/3)

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Omniscience");
        setChoice(playerA, true); // Pay alternative costs? ({W}{U}{B}{R}{G})   
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bog Wraith");
        // The order of the two alternate casting abilities is not fixed, so it's not clear which ability is asked for first
        setChoice(playerA, false); // Pay alternative costs? ({W}{U}{B}{R}{G})   
        setChoice(playerA, true); // Cast without paying its mana cost?

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Omniscience", 1);
        assertPermanentCount(playerA, "Bog Wraith", 1);

    }

    @Test
    public void testJelevaCastingSavageBeatingFromExile() {

        /*
        Jeleva, Nephalia's Scourge {1}{U}{B}{R}
        Legendary Creature - Vampire Wizard 1/3
        Flying
        When Jeleva, Nephalia's Scourge enters the battlefield, each player exiles the top X cards of their library, where X is the amount of mana spent to cast Jeleva.
        Whenever Jeleva attacks, you may cast an instant or sorcery card exiled with it without paying its mana cost.
         */
        String jeleva = "Jeleva, Nephalia's Scourge";

        /*
        Savage Beating {3}{R}{R}
        Instant
        Cast Savage Beating only during your turn and only during combat.
        Choose one -
        - Creatures you control gain double strike until end of turn.
        - Untap all creatures you control. After this phase, there is an additional combat phase.
        Entwine {1}{R} (Choose both if you pay the entwine cost.)
         */
        String savageBeating = "Savage Beating";

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, savageBeating, 2);
        addCard(Zone.HAND, playerA, jeleva);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jeleva);

        attack(3, playerA, jeleva);
        setChoice(playerA, true); // opt to use Jeleva ability
        setChoice(playerA, savageBeating); // choose to cast Savage Beating for free
        setChoice(playerA, false); // opt not to pay entwine cost
        setModeChoice(playerA, "1"); // use first mode of Savage Beating granting double strike

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, savageBeating, 1);
        assertTapped(jeleva, true);
        assertLife(playerB, 18);
        assertAbility(playerA, jeleva, DoubleStrikeAbility.getInstance(), true);

    }
}
