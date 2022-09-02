package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class EntersTheBattlefieldTriggerTest extends CardTestPlayerBase {

    @Test
    public void testDrawCardsAddedCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerA, "Soul Warden");

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Warden");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clever Impersonator");
        setChoice(playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soul Warden", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

    /**
     * Diluvian Primordial is bugged and doesn't trigger upon entering the
     * battlefield
     */
    @Test
    public void testDiluvianPrimordial() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // Flying
        // When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Diluvian Primordial", 1); // {5}{U}{U}

        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diluvian Primordial");
        addTarget(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Diluvian Primordial", 1);

        assertExileCount("Lightning Bolt", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    /**
     * Scion of Vitu-Ghazi if it is NOT cast from the hand, it will still allow
     * the Populate effect. It should only allow these when it is cast from
     * hand.
     */
    @Test
    public void testScionOfVituGhaziConditionNotTrue() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // When Scion of Vitu-Ghazi enters the battlefield, if you cast it from your hand, put a 1/1 white Bird creature token with flying onto the battlefield, then populate.
        addCard(Zone.HAND, playerA, "Scion of Vitu-Ghazi", 1); // 4/4 - {3}{W}{W}
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1); // {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scion of Vitu-Ghazi");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Scion of Vitu-Ghazi");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Reanimate", "Scion of Vitu-Ghazi");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Terror", 1);

        assertGraveyardCount(playerA, "Reanimate", 1);
        assertPermanentCount(playerA, "Scion of Vitu-Ghazi", 1);
        assertPermanentCount(playerA, "Bird Token", 2); // only 2 from cast from hand creation and populate. Populate may not trigger from reanimate

        assertLife(playerA, 15);
        assertLife(playerB, 20);
    }

    /**
     * Dread Cacodemon's abilities should only trigger when cast from hand.
     * <p>
     * Testing when cast from hand abilities take effect. Cast from hand
     * destroys opponents creatures and taps all other creatures owner controls.
     */
    @Test
    public void testDreadCacodemonConditionTrue() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        // When Dread Cacodemon enters the battlefield, if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control.
        addCard(Zone.HAND, playerA, "Dread Cacodemon", 1); // 8/8 - {7}{B}{B}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // Protection from white, first strike
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 2); // {B}{B}
        // Deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Typhoid Rats", 2); // {B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dread Cacodemon");
        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerB, "Typhoid Rats", 0);

        assertPermanentCount(playerA, "Dread Cacodemon", 1);
        assertPermanentCount(playerA, "Black Knight", 2);
        assertTappedCount("Black Knight", true, 2);
        assertTapped("Dread Cacodemon", false);
    }

    /**
     * Dread Cacodemon's abilities should only trigger when cast from hand.
     * <p>
     * Testing when card is not cast from hand, abilities do not take effect.
     * All opponents creatures remain alive and owner's creatures are not
     * tapped.
     */
    @Test
    public void testDreadCacodemonConditionFalse() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        // When Dread Cacodemon enters the battlefield, if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control.
        addCard(Zone.GRAVEYARD, playerA, "Dread Cacodemon", 1); // 8/8 - {7}{B}{B}{B}
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // Protection from white, first strike
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 2); // {B}{B}
        // Deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Typhoid Rats", 2); // {B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Dread Cacodemon");
        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerB, "Typhoid Rats", 2);

        assertGraveyardCount(playerA, "Reanimate", 1);
        assertPermanentCount(playerA, "Dread Cacodemon", 1);
        assertPermanentCount(playerA, "Black Knight", 2);
        assertTappedCount("Black Knight", false, 2);
        assertTapped("Dread Cacodemon", false);

        assertLife(playerA, 10); // loses 10 life from reanimating Dread Cacodemon at 10 CMC
        assertLife(playerB, 20);
    }

    /**
     * Test that the cast from hand condition works for target permanent
     */
    @Test
    public void testWildPair() {
        // Whenever a creature enters the battlefield, if you cast it from your hand,
        // you may search your library for a creature card with the same total power and toughness and put it onto the battlefield.
        // If you do, shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Wild Pair");
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, "Yes"); // Yes for Wild Pair to find another creature
        // Silvercoat Lion is the only other choice, let it be auto-chosen

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
    }

    /**
     * Test self trigger for Noxious Ghoul.
     */
    @Test
    public void testNoxiousGhoul1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        addCard(Zone.HAND, playerA, "Noxious Ghoul", 1); // {3}{B}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Zephyr Falcon", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Scathe Zombies", 1); // 2/2 Zombie

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Noxious Ghoul");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Noxious Ghoul", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 1);
        assertPowerToughness(playerB, "Scathe Zombies", 2, 2);
        assertGraveyardCount(playerB, "Zephyr Falcon", 1);

    }

    /**
     * Test another zombie trigger
     */
    @Test
    public void testNoxiousGhoul2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        addCard(Zone.HAND, playerA, "Noxious Ghoul", 1); // 3/3 Zombie {3}{B}{B}
        addCard(Zone.HAND, playerA, "Scathe Zombies", 1); // 2/2 Zombie {2}{B}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // Changeling (This card is every creature type.)
        // Creatures target player controls get -2/-0 and lose all creature types until end of turn.
        addCard(Zone.HAND, playerB, "Ego Erasure", 1); // {2}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Zephyr Falcon", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Scathe Zombies", 1); // 2/2 Zombie {2}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Noxious Ghoul");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ego Erasure", "targetPlayer=PlayerA", "Whenever");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scathe Zombies");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Noxious Ghoul", 1);
        assertGraveyardCount(playerB, "Ego Erasure", 1);
        assertPowerToughness(playerA, "Noxious Ghoul", -1, 1);// -2/0 from Ego Erasure / -2/0 from the 2 zombies coming into play
        assertPermanentCount(playerA, "Scathe Zombies", 1);
        assertPowerToughness(playerB, "Scathe Zombies", 2, 2);
        assertGraveyardCount(playerB, "Zephyr Falcon", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * Test copy of Noxious Ghoul
     */
    @Test
    public void testCopyNoxiousGhoul() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        addCard(Zone.HAND, playerA, "Noxious Ghoul", 1); // 3/3 Zombie {3}{B}{B}
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1); // 0/0 Shapeshifter {3}{U}

        addCard(Zone.BATTLEFIELD, playerA, "Carnivorous Plant", 1); // 4/5

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // Changeling (This card is every creature type.)
        // Creatures target player controls get -2/-0 and lose all creature types until end of turn.
        addCard(Zone.HAND, playerB, "Ego Erasure", 1); // {2}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Zephyr Falcon", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Carnivorous Plant", 1); // 4/5
        addCard(Zone.BATTLEFIELD, playerB, "Scathe Zombies", 1); // 2/2 Zombie {2}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Noxious Ghoul");
        /*
         * playerA's Carnivorous Plant will get -1/-1 from Noxious Ghoul -> 3/4
         * playerB's Carnivorous Plant will get -1/-1 from Noxious Ghoul -> 3/4
         */

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Noxious Ghoul");
        /*
         * playerA's Carnivorous Plant will get -1/-1 from Clone -> 2/3
         * playerB's Carnivorous Plant will get -1/-1 from Clone -> 2/3
         */
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ego Erasure", "targetPlayer=PlayerA", "Whenever");
        /*
         * playerA' Noxious Ghoul will get -2/0 -> 1/3
         * playerA's Carnivorous Plant will get -2/0 > 0/3
         * playerA' Noxious Ghoul will get -1/-1 from Clone -> 0/2
         * playerA' Noxious Ghoul will get -1/-1 from itself -> -1/1
         * playerA's Carnivorous Plant will get -1/-1 from Noxious Ghoul -> -1/2
         * playerB's Carnivorous Plant will get -1/-1 from Noxious Ghoul -> 1/2
         */
        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, "Noxious Ghoul", 2);
        assertGraveyardCount(playerB, "Ego Erasure", 1);
        assertPowerToughness(playerA, "Noxious Ghoul", -1, 1, Filter.ComparisonScope.All);//  -1/-1 from the second  Noxious Ghoul also if it's no zombie

        assertGraveyardCount(playerB, "Zephyr Falcon", 1);
        assertPowerToughness(playerB, "Carnivorous Plant", 1, 2);
        assertPowerToughness(playerA, "Carnivorous Plant", -1, 2);
    }

    @Test
    public void testHearthcageGiant() {
        // {6}{R}{R} Creature — Giant Warrior
        //When Hearthcage Giant enters the battlefield, put two 3/1 red Elemental Shaman creature tokens onto the battlefield.
        //Sacrifice an Elemental: Target Giant creature gets +3/+1 until end of turn.
        addCard(Zone.HAND, playerA, "Hearthcage Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hearthcage Giant");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hearthcage Giant", 1);
        assertPermanentCount(playerA, "Elemental Shaman Token", 2);
        assertPowerToughness(playerA, "Elemental Shaman Token", 3, 1);
    }

    /**
     * Just had a game with Harmonic Sliver being reanimated or blinked, but
     * never triggered. Only when cast from hand.
     */
    @Test
    public void testReanimateHarmonicSliver() {
        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.GRAVEYARD, playerA, "Harmonic Sliver");
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate"); // Sorcery {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Harmonic Sliver");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Harmonic Sliver", 1);
        assertGraveyardCount(playerA, "Reanimate", 1);
        assertGraveyardCount(playerB, "Juggernaut", 1);
        assertLife(playerA, 17);
    }

    @Test
    public void testReanimateHarmonicSliverOther() {
        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.BATTLEFIELD, playerA, "Harmonic Sliver");
        // Sliver creatures you control get +2/+0.
        addCard(Zone.GRAVEYARD, playerA, "Battle Sliver");
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate"); // Sorcery {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Battle Sliver");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Harmonic Sliver", 3, 1);
        assertPowerToughness(playerA, "Battle Sliver", 5, 3);
        assertGraveyardCount(playerA, "Reanimate", 1);
        assertGraveyardCount(playerB, "Juggernaut", 1);

        assertLife(playerA, 15);
    }

    @Test
    public void test_MultiTriggersAndSpellLKI() {
        // getSpellOrLKIStack can return last triggered ability (e.g. null) instead real spell
        // need to fix it (always return last spell)

        // When Uro enters the battlefield, sacrifice it unless it escaped.
        // Whenever Uro enters the battlefield or attacks, you gain 3 life and draw a card, then you may put a land card from your hand onto the battlefield.
        // Escape-{G}{G}{U}{U}, Exile five other cards from your graveyard. (You may cast this card from your graveyard for its escape cost.)
        addCard(Zone.GRAVEYARD, playerA, "Uro, Titan of Nature's Wrath");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uro, Titan of Nature's Wrath");
        setChoice(playerA, "Balduvian Bears^Balduvian Bears^Balduvian Bears^Balduvian Bears^Balduvian Bears");
        setChoice(playerA, "When {this} enters the battlefield, sacrifice it"); // sacrifice trigger must go first
        setChoice(playerA, false); // do not put land to battlefield

        // sacrifice trigger must found escape ability by getSpellOrLKIStack and keep uro on battlefield

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Uro, Titan of Nature's Wrath", 1);
    }
}
