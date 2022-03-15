package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class FlashbackTest extends CardTestPlayerBase {

    @Test
    public void testNormalWildHunger() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Target creature gets +3/+1 and gains trample until end of turn.
        // Flashback {3}{R}
        addCard(Zone.GRAVEYARD, playerA, "Wild Hunger");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 5, 3);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);
        assertExileCount("Wild Hunger", 1);
    }

    /**
     * Fracturing Gust is bugged. In a match against Affinity, it worked
     * properly when cast from hand. When I cast it from graveyard c/o
     * Snapcaster Mage flashback, it destroyed my opponent's Darksteel Citadels,
     * which it did not do when cast from my hand.
     */
    @Test
    public void testSnapcasterMageWithFracturingGust() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.GRAVEYARD, playerA, "Fracturing Gust");

        addCard(Zone.BATTLEFIELD, playerA, "Berserkers' Onslaught", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel", 1);

        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        setChoice(playerA, "Fracturing Gust");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {2}{G/W}{G/W}{G/W}"); // now snapcaster mage is dead so -13/-13

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertGraveyardCount(playerA, "Berserkers' Onslaught", 1);

        assertPermanentCount(playerB, "Darksteel Citadel", 1);

        assertExileCount("Fracturing Gust", 1);
    }

    /**
     * Test Granting Flashback to spells with X in manacost which have targeting
     * requirements depending on the choice of X
     * <p>
     * Specific instance: Snapcaster Mage granting Flashback to Repeal
     */
    @Test
    public void testSnapcasterMageWithRepeal() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);
        addCard(Zone.GRAVEYARD, playerA, "Repeal", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Repeal");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "X=2");
        addTarget(playerA, "Snapcaster Mage");

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 0);
        assertGraveyardCount(playerA, "Repeal", 0);
        assertExileCount("Repeal", 1);
    }

    /**
     * Test Granting Flashback to spells with X in mana cost, where X has no
     * influence on targeting requirements
     * <p>
     * Specific instance: Snapcaster Mage granting Flashback to Blaze
     */
    @Test
    public void testSnapcasterMageWithBlaze() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // you still need extra red mana in case the Snapcaster Mage is paid for via UR, X=1 using R, etc

        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);
        addCard(Zone.GRAVEYARD, playerA, "Blaze", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Blaze");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Snapcaster Mage");

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 0);
        assertGraveyardCount(playerA, "Blaze", 0);
        assertExileCount("Blaze", 1);
    }

    /**
     * My opponent put Iona on the battlefield using Unburial Rites, but my game
     * log didn't show me the color they chose.
     */
    @Test
    public void testUnburialRites() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        // Return target creature card from your graveyard to the battlefield.
        // Flashback {3}{W}
        addCard(Zone.HAND, playerA, "Unburial Rites", 1); // Sorcery - {4}{B}

        // Flying
        // As Iona, Shield of Emeria enters the battlefield, choose a color.
        // Your opponents can't cast spells of the chosen color.
        addCard(Zone.GRAVEYARD, playerA, "Iona, Shield of Emeria");

        // As Lurebound Scarecrow enters the battlefield, choose a color.
        // When you control no permanents of the chosen color, sacrifice Lurebound Scarecrow.
        addCard(Zone.GRAVEYARD, playerA, "Lurebound Scarecrow"); // Enchantment - {2}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unburial Rites", "Iona, Shield of Emeria");
        setChoice(playerA, "Red");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {3}{W}");
        addTarget(playerA, "Lurebound Scarecrow");
        setChoice(playerA, "White");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Iona, Shield of Emeria", 1);
        assertPermanentCount(playerA, "Lurebound Scarecrow", 1);

        assertHandCount(playerB, "Lightning Bolt", 1);

        assertExileCount("Unburial Rites", 1);
    }

    /**
     *
     */
    @Test
    public void testFlashbackWithConverge() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        // Converge - Create a 1/1 white Kor Ally creature token for each color of mana spent to cast Unified Front.
        addCard(Zone.GRAVEYARD, playerA, "Unified Front"); // {3}{W}

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        setChoice(playerA, "Unified Front");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {3}{W}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertPermanentCount(playerA, "Kor Ally Token", 4);
        assertExileCount("Unified Front", 1);
    }

    /**
     * Conflagrate flashback no longer works. Requires mana payment but never
     * allows target selection before resolving.
     */
    @Test
    public void testConflagrate() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        // Conflagrate deals X damage divided as you choose among any number of target creatures and/or players.
        // Flashback-{R}{R}, Discard X cards.
        addCard(Zone.HAND, playerA, "Conflagrate", 1); // Sorcery {X}{X}{R}

        addCard(Zone.HAND, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conflagrate");
        setChoice(playerA, "X=2");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);

        assertExileCount("Conflagrate", 1);
    }

    /**
     * Ancestral Vision has no casting cost (this is different to a casting cost
     * of {0}). Snapcaster Mage, for example, is able to give it flashback
     * whilst it is in the graveyard.
     * <p>
     * However the controller should not be able to cast Ancestral Visions from
     * the graveyard for {0} mana.
     */
    @Test
    public void testFlashbackAncestralVision() {
        // Suspend 4-{U}
        // Target player draws three cards.
        addCard(Zone.GRAVEYARD, playerA, "Ancestral Vision", 1);

        // Flash
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Ancestral Vision");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        addTarget(playerA, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Snapcaster Mage", 0);
        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertGraveyardCount(playerA, "Ancestral Vision", 1);
        assertHandCount(playerA, 0);
    }

    /**
     * I cast Runic Repetition targeting a Silent Departure in exile, and
     * afterwards I cast the Silent Departure from my hand. When it resolves, it
     * goes back to exile instead of ending up in my graveyard. Looks like a
     * problem with Runic Repetition?
     */
    @Test
    public void testFlashbackReturnToHandAndCastAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        // Return target creature to its owner's hand.
        // Flashback {4}{U}
        addCard(Zone.GRAVEYARD, playerA, "Silent Departure", 1); // {U}
        addCard(Zone.HAND, playerA, "Runic Repetition", 1);// {2}{U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Runic Repetition");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silent Departure", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 2);
        assertExileCount("Silent Departure", 0);
        assertGraveyardCount(playerA, "Silent Departure", 1);
        assertGraveyardCount(playerA, "Runic Repetition", 1);
    }

    @Test
    public void testAltarsReap() {

        addCard(Zone.LIBRARY, playerA, "Island", 2);
        // As an additional cost to cast Altar's Reap, sacrifice a creature.
        // Draw two cards.
        addCard(Zone.GRAVEYARD, playerA, "Altar's Reap", 1); // Instant {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        // Flash
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn.
        // The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        setChoice(playerA, "Altar's Reap");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "Snapcaster Mage");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Snapcaster Mage", 1);
        assertExileCount(playerA, "Altar's Reap", 1);
    }

    /**
     * Fracturing Gust is bugged. In a match against Affinity, it worked
     * properly when cast from hand. When I cast it from graveyard c/o
     * Snapcaster Mage flashback, it destroyed my opponent's Darksteel Citadels,
     * which it did not do when cast from my hand.
     */
    @Test
    public void testSnapcasterMageWithIcefallRegent() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // Destroy target creature. It can't be regenerated.
        addCard(Zone.GRAVEYARD, playerA, "Terminate");

        addCard(Zone.BATTLEFIELD, playerA, "Berserkers' Onslaught", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Icefall Regent", 1);

        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Terminate");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback"); // Flashback Terminate
        addTarget(playerA, "Icefall Regent");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertGraveyardCount(playerB, "Icefall Regent", 1);
        assertExileCount("Terminate", 1);

        assertTappedCount("Mountain", true, 2);
        assertTappedCount("Island", true, 2);
        assertTappedCount("Swamp", true, 2);
    }

    @Test
    public void testSnapcasterMageWithBuyback() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        // Buyback {5}(You may pay an additional {5} as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Draw a card.
        addCard(Zone.GRAVEYARD, playerA, "Whispers of the Muse", 1); // {U}

        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        setChoice(playerA, "Whispers of the Muse");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback"); // Flashback Whispers of the Muse
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertGraveyardCount(playerA, "Whispers of the Muse", 0);
        assertExileCount("Whispers of the Muse", 1);
        assertHandCount(playerA, 1);

    }

    /**
     * Deep Analysis doesn't cost mana when flashbacked.
     */
    @Test
    public void testCombinedFlashbackCosts() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Target player draws two cards.
        // Flashback-{1}{U}, Pay 3 life.
        addCard(Zone.GRAVEYARD, playerA, "Deep Analysis", 1); // Sorcery {3}{U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback"); // Flashback Deep Analysis
        addTarget(playerA, playerA);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Deep Analysis", 0);
        assertLife(playerA, 17);
        assertLife(playerB, 20);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 2);
    }

    /*
     * Bug: Firecat Blitz when cast via Flashback requests sacrificing mountains twice
     */
    @Test
    public void firecatBlitzFlashback() {

        /*
        Firecat Blitz {X}{R}{R}
         Sorcery
        Create X 1/1 red Elemental Cat creature tokens with haste. Exile them at the beginning of the next end step.
        Flashback—{R}{R}, Sacrifice X Mountains.
         */
        String fCatBlitz = "Firecat Blitz";
        String mountain = "Mountain";

        addCard(Zone.GRAVEYARD, playerA, fCatBlitz);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 6);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback"); // Flashback blitz

        setChoice(playerA, "X=1");
        addTarget(playerA, mountain);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, fCatBlitz, 1);
        assertPermanentCount(playerA, "Elemental Cat Token", 1);
        assertGraveyardCount(playerA, mountain, 1);
    }

    /*
     * Reported bug: Battle Screech doesn't flashback (i get the pop up to choose flashback, tap the creatures and nothing happens)
     */
    @Test
    public void battleScreechFlashback() {

        /*
        Battle Screech {2}{W}{W}
        Sorcery
        Create two 1/1 white Bird creature tokens with flying.
        Flashback—Tap three untapped white creatures you control.
         */
        String bScreech = "Battle Screech";
        String eVanguard = "Elite Vanguard"; // {W} 2/1
        String yOx = "Yoked Ox"; // {W} 0/4
        String wKnight = "White Knight"; // {W}{W} 2/2

        addCard(Zone.GRAVEYARD, playerA, bScreech);
        addCard(Zone.BATTLEFIELD, playerA, eVanguard);
        addCard(Zone.BATTLEFIELD, playerA, yOx);
        addCard(Zone.BATTLEFIELD, playerA, wKnight);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback"); // Flashback Battle Screech
        addTarget(playerA, eVanguard + '^' + yOx + '^' + wKnight);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(eVanguard, true);
        assertTapped(yOx, true);
        assertTapped(wKnight, true);
        assertExileCount(playerA, bScreech, 1); // this fails, but the creatures are tapped as part of paying the cost
        assertPermanentCount(playerA, "Bird Token", 2); // if you comment out the above line, this is failing as well
    }

    /*
     Reported bug: tried to flashback Dread Return, it allowed me to sac the creatures but the spell did not resolve after the costs had been paid.
     It did not allow me to select a creature to return from yard to board.
     */
    @Test
    public void dreadReturnFlashback() {

        /*
        Dread Return {2}{B}{B}
        Sorcery
        Return target creature card from your graveyard to the battlefield.
        Flashback—Sacrifice three creatures
         */
        String dReturn = "Dread Return";
        String yOx = "Yoked Ox"; // {W} 0/4
        String eVanguard = "Elite Vanguard"; // {W} 2/1
        String memnite = "Memnite"; // {0} 1/1
        String bSable = "Bronze Sable"; // {2} 2/1

        addCard(Zone.GRAVEYARD, playerA, dReturn);
        addCard(Zone.GRAVEYARD, playerA, bSable);
        addCard(Zone.BATTLEFIELD, playerA, yOx);
        addCard(Zone.BATTLEFIELD, playerA, eVanguard);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback"); // Flashback Dread Return
        addTarget(playerA, bSable); // return to battlefield
        addTarget(playerA, yOx + '^' + eVanguard + '^' + memnite); // sac 3 creatures

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, eVanguard, 1);
        assertGraveyardCount(playerA, yOx, 1);
        assertGraveyardCount(playerA, memnite, 1);
        assertExileCount(playerA, dReturn, 1);
        assertPermanentCount(playerA, bSable, 1);
    }

    /**
     * I can play Force of Will with flashback paying his alternative mana cost.
     * The ruling say no to it, because we only can choose one alternative cost
     * to a spell, and the flashback cost is already an alternative cost.
     */
    @Test
    public void testSnapcasterMageSpellWithAlternateCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn.
        // The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 2); // Creature{1}{U}

        // You may pay 1 life and exile a blue card from your hand rather than pay Force of Will's mana cost.
        // Counter target spell.
        addCard(Zone.GRAVEYARD, playerA, "Force of Will");

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Force of Will");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Snapcaster Mage");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", null, "Lightning Bolt");
        addTarget(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 0);
        assertGraveyardCount(playerA, "Snapcaster Mage", 1);

        assertGraveyardCount(playerA, "Force of Will", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertLife(playerA, 20);

    }

    /**
     * Test cost reduction with mixed flashback costs
     */
    @Test
    public void testReduceMixedFlashbackCosts() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");// 2/2

        // Target player draws two cards.
        // Flashback-{1}{U}, Pay 3 life.
        addCard(Zone.HAND, playerA, "Deep Analysis"); // {3}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deep Analysis");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Deep Analysis", 0);
        assertExileCount(playerA, "Deep Analysis", 1);
        assertHandCount(playerA, 4);

        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);

        assertLife(playerA, 17);
    }

    @Test
    public void test_SplitCards_EachPartMustGainFlashback() {
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains
        // flashback until end of turn. The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Fire, {1}{R}
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        // Ice, {1}{U}
        // Tap target permanent.
        // Draw a card.
        addCard(Zone.GRAVEYARD, playerA, "Fire // Ice", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire", false);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ice", false);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{R}{1}{U}", false);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{R}", false);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{U}", false);

        // cast mage and give flashback
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Fire // Ice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire", false);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ice", false);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{R}{1}{U}", false); // no fuse
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{R}", true);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{U}", true);
        runCode("test", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            long flashbackCount = player.getPlayable(game, true).stream()
                    .filter(ability -> ability instanceof FlashbackAbility)
                    .count();
            Assert.assertEquals("must have only two playable abilities without duplicates", 2, flashbackCount);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();
    }
}
