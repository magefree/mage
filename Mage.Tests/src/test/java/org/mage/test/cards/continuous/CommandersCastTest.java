package org.mage.test.cards.continuous;

import mage.abilities.dynamicvalue.common.CommanderCastCountValue;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.AdventureCard;
import mage.constants.CommanderCardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.GameState;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4PlayersWithAIHelps;

/**
 * @author JayDi85
 */
public class CommandersCastTest extends CardTestCommander4PlayersWithAIHelps {

    @Test
    public void test_CastToBattlefieldOneTime() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertTappedCount("Forest", true, 2);
    }

    @Test
    public void test_CastToBattlefieldTwoTimes() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6); // 2 + 4
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // cast 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // destroy commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Balduvian Bears");
        setChoice(playerA, true); // put to command zone again
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerB, playerA, "Balduvian Bears", 0);

        // cast 2
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after cast 2", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertTappedCount("Forest", true, 2 + 4);
    }

    @Test
    public void test_PlayAsLandOneTime() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Academy Ruins", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, "Academy Ruins", 0);
        assertPermanentCount(playerA, "Academy Ruins", 1);
    }

    @Test
    public void test_PlayAsLandTwoTimes() {
        // Player order: A -> D -> C -> B
        addCard(Zone.COMMAND, playerA, "Academy Ruins", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2); // 0 + 2
        //
        addCard(Zone.HAND, playerA, "Pillage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // cast 1
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after play 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins", 1);

        // destroy commander land
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pillage", "Academy Ruins");
        setChoice(playerA, true); // put to command zone again
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins", 0);

        // remove unnecessary mana, only 2 forest need (workaround to remove random mana payments)
        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        // cast 2
        playLand(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Ruins");
        waitStackResolved(5, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Ruins", 1);

        setStopAt(5, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, "Academy Ruins", 0);
        assertPermanentCount(playerA, "Academy Ruins", 1);
        assertGraveyardCount(playerA, "Pillage", 1);
        assertTappedCount("Forest", true, 2);
        assertTappedCount("Mountain", true, 3);
    }

    @Test
    public void test_ModesNormal() {
        // Player order: A -> D -> C -> B

        // Choose four. You may choose the same mode more than once.
        // • Create a 2/2 Citizen creature token that’s all colors.
        // • Return target permanent card from your graveyard to your hand.
        // • Proliferate.
        // • You gain 4 life.
        addCard(Zone.HAND, playerA, "Planewide Celebration", 1); // {5}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);

        // cast (3 tokens + 4 life)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Planewide Celebration");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "4");

        checkPermanentCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Citizen Token", 3);
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 + 4);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ModesCommander() {
        // Player order: A -> D -> C -> B

        // Choose four. You may choose the same mode more than once.
        // • Create a 2/2 Citizen creature token that’s all colors.
        // • Return target permanent card from your graveyard to your hand.
        // • Proliferate.
        // • You gain 4 life.
        addCard(Zone.COMMAND, playerA, "Planewide Celebration", 1); // {5}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);

        // cast (3 tokens + 4 life)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Planewide Celebration");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "4");
        setChoice(playerA, true); // return commander

        checkPermanentCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Citizen Token", 3);
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 + 4);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_CastFromHandWithoutTaxIncrease() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // cast from command for {1}{G}, from hand for {1}{G}, from command for {1}{G}{2}
        //
        // Counter target spell. If that spell is countered this way, put it into its owner’s hand instead of into that player’s graveyard.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Remand", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2); // counter 2 times

        // cast 1 and counter (increase commander tax)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Remand", "Balduvian Bears", "Balduvian Bears");
        setChoice(playerA, false); // move to hand
        checkCommandCardCount("cast 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkHandCardCount("cast 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkPermanentCount("cast 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);

        ///*
        // cast 2 from hand without tax
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerB, "Remand", "Balduvian Bears", "Balduvian Bears");
        setChoice(playerA, true); // move to command zone
        checkCommandCardCount("cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkHandCardCount("cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkPermanentCount("cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);

        // cast 3 from command with tax for 1 play
        castSpell(9, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        checkCommandCardCount("cast 3", 9, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 0);
        checkHandCardCount("cast 3", 9, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 0);
        checkPermanentCount("cast 3", 9, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 1);

        setStrictChooseMode(true);
        setStopAt(9, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AlternativeSpellNormal() {
        // Player order: A -> D -> C -> B

        // Weapon Surge
        // Target creature you control gets +1/+0 and gains first strike until end of turn.
        // Overload {1}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of “target” with “each.”)
        addCard(Zone.HAND, playerA, "Weapon Surge", 1); // {R} or {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2);

        // cast overload
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Weapon Surge with overload");
        checkAbility("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", FirstStrikeAbility.class, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_AlternativeSpellCommander() {
        // Player order: A -> D -> C -> B

        // Weapon Surge
        // Target creature you control gets +1/+0 and gains first strike until end of turn.
        // Overload {1}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of “target” with “each.”)
        addCard(Zone.COMMAND, playerA, "Weapon Surge", 1); // {R} or {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2);

        // cast overload
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Weapon Surge with overload");
        setChoice(playerA, true); // move to command zone
        checkAbility("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", FirstStrikeAbility.class, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/5121
     *      Exiling your commander from your graveyard should give you the option to put it in command zone
     *      We were playing in a restarted-by-Karn game (if that mattered), and a player who exiled their
     *      commander from graveyard via Delve was not given the opportunity to place it in the command zone.
     *      Instead, it went directly to the exiled zone.
     */
    @Test
    public void test_ExileWithDelvePayAndReturn() {
        // disable auto-payment for delve test
        disableManaAutoPayment(playerA);

        // commander
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // {4}{U}{U} creature
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        addCard(Zone.HAND, playerA, "Ethereal Forager", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5); // one from delve

        // prepare commander and put it to graveyard
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        setChoice(playerA, "Green", 2); // pay
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        //
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        setChoice(playerA, "Red"); // pay
        setChoice(playerA, false); // leave in graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // use commander as delve pay
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Forager");
        setChoice(playerA, "Blue", 5); // pay normal
        setChoice(playerA, "Exile a card"); // pay delve
        setChoice(playerA, "Balduvian Bears");
        setChoice(playerA, true); // move to command zone

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCommandZoneCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_SplitCard() {
        // Player order: A -> D -> C -> B

        // use case:
        // cast left side
        // return to command zone
        // cast right side with commander cost

        // Fire, {1}{R}
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        // Ice, {1}{U}
        // Tap target permanent.
        // Draw a card.
        addCard(Zone.COMMAND, playerA, "Fire // Ice", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // both sides are playable
        checkCommandCardCount("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire // Ice", 1);
        checkPlayableAbility("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire", true);
        checkPlayableAbility("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ice", true);

        // turn 1 - cast left
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire");
        addTargetAmount(playerA, playerB, 2);
        setChoice(playerA, true); // return commander
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // can't cast due commander cost added
        checkCommandCardCount("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire // Ice", 1);
        checkPlayableAbility("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire", false);
        checkPlayableAbility("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ice", false);

        // turn 5 - can cost again
        checkPlayableAbility("before second cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire", true);
        checkPlayableAbility("before second cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ice", true);
        // cast right and use all mana (4 = card + commander cost)
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice");
        addTarget(playerA, "Mountain"); // tap target
        setChoice(playerA, true); // move to commander
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        checkCommandCardCount("after second cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire // Ice", 1);
        checkPlayableAbility("after second cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire", false);
        checkPlayableAbility("after second cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ice", false);
        // must used all mana
        checkPermanentTapped("after second cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain", true, 2);
        checkPermanentTapped("after second cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", true, 2);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AdventureCard() {
        // Player order: A -> D -> C -> B

        addCustomEffect_TargetDamage(playerA, 10); // kill creature

        // use case:
        // cast adventure spell from command zone (inc command tax to 1x)
        // return to command zone
        // cast adventure spell from command zone (inc command tax to 2x)
        // return to command zone
        // cast as creature (with 2x command tax)

        // Curious Pair, creature, {1}{G}, 1/3
        // Treats to Share, sorcery, {G}
        // Create a Food token.
        addCard(Zone.COMMAND, playerA, "Curious Pair", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Forest", 3); // for commander cost test

        // commander tax: 0
        // both sides are playable
        checkCommandCardCount("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", 1);
        checkPlayableAbility("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", true);
        checkPlayableAbility("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", true);

        // cast adventure spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setChoice(playerA, true); // return commander
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkCommandCardCount("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", 1);
        checkPermanentCount("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Food Token", 1);
        // commander tax: 1x
        // can't cast due commander cost added (we stil have 2x mana)
        checkPlayableAbility("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);

        // commander tax: 1x
        // play land number 1 and give extra {G}, so total 3x
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        checkPlayableAbility("after mana add", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("after mana add", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", true);

        // play adventure spell, but keep it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setChoice(playerA, false); // do not return commander
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after second cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Food Token", 2);
        checkPlayableAbility("after second cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("after second cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);

        // wait next turn
        // commander tax: 2x BUT it doesn't apply to exile zone (e.g. must use 2x mana instead 6x)
        // it doesn't add commander tax too
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after exile cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", 1);
        checkPermanentTapped("after exile cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest", true, 4 - 2);

        // return commander to command zone
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 10", "Curious Pair");
        setChoice(playerA, true); // return to command zone
        // can't cast - only {2} mana, but need {G} + {2} + {2}
        checkPlayableAbility("after return 2", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("after return 2", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);

        // turn 9
        // commander tax: 2x
        // mana: {G}{G}{G}{G}
        // can't cast adventure spell for {G} + {2} + {2}
        // can't cast creature spell for {G}{G} + {2} + {2}
        runCode("check commander tax 2x", 9, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            AdventureCard card = (AdventureCard) game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY).stream().findFirst().get();
            Assert.assertEquals(2, CommanderCastCountValue.instance.calculate(game, card.getSpellAbility(), null));
            Assert.assertEquals(2, CommanderCastCountValue.instance.calculate(game, card.getSpellCard().getSpellAbility(), null));
        });
        checkPlayableAbility("before last cast 1", 9, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("before last cast 1", 9, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);
        // play land number 2 - can play adventure spell
        playLand(9, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        // commander tax: 2x
        // mana: {G}{G}{G}{G}{G}
        checkPlayableAbility("before last cast 2", 9, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("before last cast 2", 9, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", true);

        // turn 13
        // play land number 3 - can play all parts
        playLand(13, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        // commander tax: 2x
        // mana: {G}{G}{G}{G}{G}{G}
        checkPlayableAbility("before last cast 3", 13, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", true);
        checkPlayableAbility("before last cast 3", 13, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", true);
        // cast creature
        castSpell(13, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        waitStackResolved(13, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after last cast", 13, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair", 1);
        checkPermanentTapped("after last cast", 13, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest", true, 2 + 2 + 2);
        runCode("check commander tax 3x", 13, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            AdventureCard card = (AdventureCard) game.getCard(game.getCommandersIds(player, CommanderCardType.ANY, false).stream().findFirst().get());
            Assert.assertEquals(3, CommanderCastCountValue.instance.calculate(game, card.getSpellAbility(), null));
            Assert.assertEquals(3, CommanderCastCountValue.instance.calculate(game, card.getSpellCard().getSpellAbility(), null));
        });

        setStrictChooseMode(true);
        setStopAt(13, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ModalDoubleFacedCard_1() {
        // Player order: A -> D -> C -> B

        // use case:
        // cast left side as commander
        // return to command zone
        // cast right side as commander with commander cost

        // Tergrid, God of Fright, {3}{B}{B}, creature
        // Tergrid's Lantern, {3}{B}, artifact
        addCard(Zone.COMMAND, playerA, "Tergrid, God of Fright", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        // Exile target creature or enchantment.
        addCard(Zone.HAND, playerA, "Angelic Edict", 1); // {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // both sides are playable
        checkCommandCardCount("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tergrid, God of Fright", 1);
        checkPlayableAbility("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tergrid, God of Fright", true);
        checkPlayableAbility("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tergrid's Lantern", true);

        // cast left side
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tergrid, God of Fright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tergrid, God of Fright", 1);

        // exile and return
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Edict", "Tergrid, God of Fright");
        setChoice(playerA, true); // return to command

        // turn 5 - check commander cost
        checkPlayableAbility("before second cast 1", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tergrid, God of Fright", true);
        checkPlayableAbility("before second cast 1", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tergrid's Lantern", true);

        // turn 5 - remove angelic's mana, so it can cast only one part
        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 5 - 1);
        checkPlayableAbility("before second cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Tergrid, God of Fright", false);
        checkPlayableAbility("before second cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Tergrid's Lantern", true);

        // turn 5 - cast right side
        castSpell(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tergrid's Lantern");
        waitStackResolved(5, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after second cast", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tergrid's Lantern", 1);
        // must used all mana
        checkPermanentTapped("after second cast", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Swamp", true, 5);
        checkPermanentTapped("after second cast", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Plains", true, 5);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ModalDoubleFacedCard_CanReturnAfterKillAndCommanderControlCondition() {
        // Player order: A -> D -> C -> B

        // Cosima, God of the Voyage, {2}{U}, creature, 2/4
        // The Omenkeel, {1}{U}, artifact, vehicle
        addCard(Zone.COMMAND, playerA, "Cosima, God of the Voyage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // If you control a commander, you may cast this spell without paying its mana cost.
        // Counter target noncreature spell.
        addCard(Zone.HAND, playerA, "Fierce Guardianship", 1); // {2}{U}

        // prepare commander on battlefield
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cosima, God of the Voyage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cosima, God of the Voyage", 1);

        // kill and return to command zone
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Cosima, God of the Voyage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Cosima, God of the Voyage");
        // check what commander control condition works with mdf parts
        checkStackSize("must have 2 bolts on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        checkPlayableAbility("must see commander on battle for free cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fierce Guardianship", true);
        //
        setChoice(playerA, true); // return to command zone after kill

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Escape_CantBeCastableFromCommandZone() {
        // Player order: A -> D -> C -> B

        // When Uro enters the battlefield, sacrifice it unless it escaped.
        // Whenever Uro enters the battlefield or attacks, you gain 3 life and draw a card, then you may put a land card from your hand onto the battlefield.
        // Escape-{G}{G}{U}{U}, Exile five other cards from your graveyard.
        addCard(Zone.COMMAND, playerA, "Uro, Titan of Nature's Wrath", 1); // {1}{G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 5);
        addCard(Zone.HAND, playerA, "Swamp", 1);

        checkPlayableAbility("normal cast allowed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Uro, Titan of Nature's Wrath", true);
        checkPlayableAbility("escape cast not allowed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Uro, Titan of Nature's Wrath with Escape", false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uro, Titan of Nature's Wrath");
        setChoice(playerA, "Whenever {this} enters the battlefield or attacks"); // gain life trigger first, sacrifice next
        setChoice(playerA, false); // keep in graveyard
        setChoice(playerA, true); // put land to battlefield
        setChoice(playerA, "Swamp"); // put a Swamp

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
        assertPermanentCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Uro, Titan of Nature's Wrath", 1); // sacrificed
    }

    @Test
    public void test_AI_MustPlayCommander() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // possible bug: wrong copy of commander objects
        runCode("test", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameState copied = game.getState().copy();
            Assert.assertEquals("original commander must have 1 ability", 1, game.getState().getCommand().get(0).getAbilities().size());
            Assert.assertEquals("copied commander must have 1 ability", 1, copied.getCommand().get(0).getAbilities().size());
        });

        // ai must play commander
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCommandZoneCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertTappedCount("Forest", true, 2);
    }
}
