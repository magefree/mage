package org.mage.test.cards.copy;

import mage.abilities.MageSingleton;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.SplitCard;
import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class CopySpellTest extends CardTestPlayerBase {

    @Test
    public void copyChainOfVapor() {
        // Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, they may copy this spell and may choose a new target for that copy.
        addCard(Zone.HAND, playerA, "Chain of Vapor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 10);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 10);

        // start chain from A - return pillar to hand
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chain of Vapor", "Pillarfield Ox");
        // chain 1 - B can return
        addTarget(playerB, "Island"); // select a land to sacrifice
        setChoice(playerB, true); // want to copy spell
        setChoice(playerB, true); // want to change target
        addTarget(playerB, "Silvercoat Lion"); // new target after copy
        // chain 2 - A can return
        addTarget(playerA, "Island"); // select a land to sacrifice
        setChoice(playerA, true); // want to copy spell
        setChoice(playerA, true); // want to change target
        addTarget(playerA, "Pillarfield Ox"); // new target after copy
        // stop the chain by B
        addTarget(playerB, TestPlayer.TARGET_SKIP);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Pillarfield Ox", 2);
        assertPermanentCount(playerA, "Silvercoat Lion", 10 - 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 10 - 2);
        assertGraveyardCount(playerA, "Island", 1);
        assertGraveyardCount(playerB, "Island", 1);
    }

    @Test
    public void ZadaHedronGrinderBoost() {
        // Target creature gets +3/+3 and gains flying until end of turn.
        addCard(Zone.HAND, playerA, "Angelic Blessing", 1); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder,
        // copy that spell for each other creature you control that the spell could target.
        // Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1); // 2/4
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // 2/2

        // cast boost and copy it for another target (lion will not get boost cause can't be targeted)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Blessing", "Zada, Hedron Grinder");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Angelic Blessing", 1);
        // original target
        assertPowerToughness(playerA, "Zada, Hedron Grinder", 3 + 3, 3 + 3);
        assertAbility(playerA, "Zada, Hedron Grinder", FlyingAbility.getInstance(), true);
        // copied target
        assertPowerToughness(playerA, "Pillarfield Ox", 2 + 3, 4 + 3);
        assertAbility(playerA, "Pillarfield Ox", FlyingAbility.getInstance(), true);
        // can't target lion, so no boost
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertAbility(playerB, "Silvercoat Lion", FlyingAbility.getInstance(), false);
    }

    @Test
    public void BonecrusherGiantChangeTargets_BoneTargetBoth() {
        // Whenever Bonecrusher Giant becomes the target of a spell, Bonecrusher Giant deals 2 damage to that spell’s controller.
        addCard(Zone.BATTLEFIELD, playerA, "Bonecrusher Giant");
        //
        // Target creature gets +2/+2 until end of turn.
        // Conspire (As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose a new target for the copy.)
        addCard(Zone.HAND, playerA, "Barkshell Blessing");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Barkshell Blessing");
        setChoice(playerA, true); // use Conspire
        addTarget(playerA, "Bonecrusher Giant"); // target bone
        setChoice(playerA, "Grizzly Bears"); // pay for conspire
        setChoice(playerA, "Savannah Lions"); // pay for conspire
        setChoice(playerA, "When you pay"); // Put Conspire on the stack first.
        setChoice(playerA, false); // both spells target bone

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, "Bonecrusher Giant", 4 + 2 * 2, 3 + 2 * 2);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        assertPowerToughness(playerA, "Savannah Lions", 2, 1);
        assertLife(playerA, 20 - 2 * 2); // bone trigger from both spells
    }

    @Test
    public void BonecrusherGiantChangeTargets_BoneTargetFirst() {
        // Whenever Bonecrusher Giant becomes the target of a spell, Bonecrusher Giant deals 2 damage to that spell’s controller.
        addCard(Zone.BATTLEFIELD, playerA, "Bonecrusher Giant");
        //
        // Target creature gets +2/+2 until end of turn.
        // Conspire (As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose a new target for the copy.)
        addCard(Zone.HAND, playerA, "Barkshell Blessing");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Barkshell Blessing");
        setChoice(playerA, true); // use Conspire
        addTarget(playerA, "Bonecrusher Giant"); // target bone
        setChoice(playerA, "Grizzly Bears"); // pay for conspire
        setChoice(playerA, "Savannah Lions"); // pay for conspire
        setChoice(playerA, "When you pay"); // Put Conspire on the stack first.
        setChoice(playerA, true); // new target for copy: bear
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, "Bonecrusher Giant", 4 + 2, 3 + 2);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
        assertPowerToughness(playerA, "Savannah Lions", 2, 1);
        assertLife(playerA, 20 - 2); // one trigger
    }

    @Test
    public void BonecrusherGiantChangeTargets_BoneTargetSecond() {
        // Whenever Bonecrusher Giant becomes the target of a spell, Bonecrusher Giant deals 2 damage to that spell’s controller.
        addCard(Zone.BATTLEFIELD, playerA, "Bonecrusher Giant");
        //
        // Target creature gets +2/+2 until end of turn.
        // Conspire (As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose a new target for the copy.)
        addCard(Zone.HAND, playerA, "Barkshell Blessing");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Barkshell Blessing");
        setChoice(playerA, true); // use Conspire
        addTarget(playerA, "Grizzly Bears"); // target bear
        setChoice(playerA, "Grizzly Bears"); // pay for conspire
        setChoice(playerA, "Savannah Lions"); // pay for conspire
        setChoice(playerA, true); // new target for copy: bone
        addTarget(playerA, "Bonecrusher Giant");
        // setChoice(playerA, "When {this} becomes the target of a spell"); // must be one trigger from bone, not two

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, "Bonecrusher Giant", 4 + 2, 3 + 2);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
        assertPowerToughness(playerA, "Savannah Lions", 2, 1);
        assertLife(playerA, 20 - 2); // one trigger
    }

    /*
     * Reported bug: "Silverfur Partisan and fellow wolves did not trigger off
     * of copies of Strength of Arms made by Zada, Hedron Grinder. Not sure
     * about other spells, but I imagine similar results."
    
    // Perhaps someone knows the correct implementation for this test.
    // Just target the Silverfur Partisan and hit done
    // This test works fine in game.  The @Ignore would not work for me either.
    @Test
    public void ZadaHedronSilverfurPartisan() {

        // {2}{G}
        // Trample
        // Whenever a Wolf or Werewolf you control becomes the target of an instant or sorcery spell, put a 2/2 green Wolf creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Silverfur Partisan"); // 2/2 Wolf Warrior

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1);

        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth", 1); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Zada, Hedron Grinder");
        addTarget(playerA, "Silverfur Partisan");

        assertGraveyardCount(playerA, "Giant Growth", 1);
        assertPowerToughness(playerA, "Silverfur Partisan", 5, 5);
        assertPowerToughness(playerA, "Zada, Hedron Grinder", 6, 6);
        assertPermanentCount(playerA, "Wolf Token", 1); // created from Silverfur ability
    }
    */


    @Test
    public void ZadaHedronGrinderBoostWithCharm() {
        // Choose two -
        // • Counter target spell.
        // • Return target permanent to its owner's hand.
        // • Tap all creatures your opponents control.
        // • Draw a card.
        addCard(Zone.HAND, playerA, "Cryptic Command", 1); // {2}{U}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Command", "mode=2Zada, Hedron Grinder");
        setModeChoice(playerA, "2"); // Return target permanent to its owner's hand
        setModeChoice(playerA, "4"); // Draw a card

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);

        assertGraveyardCount(playerA, "Cryptic Command", 1);
        assertPermanentCount(playerA, "Zada, Hedron Grinder", 0);
        assertPermanentCount(playerA, "Pillarfield Ox", 0);
        assertHandCount(playerA, 4); // 2 draw + 2 creatures returned to hand
    }

    /**
     * Not to be a bother, but I posted earlier about how Zada, Hedron Grinder
     * 's interaction with splice is broken. I didn't get a response on whether
     * or not the problem was being looked at. Zada SHOULD copy spliced effects
     * (and currently doesn't) because spliced effects are put onto the spell
     * before it is cast and therefore before Zada's ability triggers, e.g.
     * Desperate Ritual spliced onto Into the Fray should generate 3 red mana
     * for every creature i control.
     * <p>
     * 702.46a Splice is a static ability that functions while a card is in your
     * hand. “Splice onto [subtype] [cost]” means “You may reveal this card from
     * your hand as you cast a [subtype] spell. If you do, copy this card's text
     * box onto that spell and pay [cost] as an additional cost to cast that
     * spell.” Paying a card's splice cost follows the rules for paying
     * additional costs in rules 601.2b and 601.2e–g. 601.2b If the spell is
     * modal the player announces the mode choice (see rule 700.2). If the
     * player wishes to splice any cards onto the spell (see rule 702.46), they
     * reveal those cards in their hand. 706.10. To copy a spell, activated
     * ability, or triggered ability means to put a copy of it onto the stack; a
     * copy of a spell isn't cast and a copy of an activated ability isn't
     * activated. A copy of a spell or ability copies both the characteristics
     * of the spell or ability and all decisions made for it, including modes,
     * targets, the value of X, and additional or alternative costs. (See rule
     * 601, “Casting Spells.”)
     */
    @Test
    public void ZadaHedronGrinderAndSplicedSpell() {
        // Draw a card.
        // Splice onto Arcane {1}{U}
        addCard(Zone.HAND, playerA, "Evermind", 1); // no costs
        // Target creature attacks this turn if able.
        // Splice onto Arcane {R}
        addCard(Zone.HAND, playerA, "Into the Fray", 1); // Instant - Arcane - {U}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder,
        // copy that spell for each other creature you control that the spell could target.
        // Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Into the Fray", "Zada, Hedron Grinder");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Into the Fray", 1);
        assertHandCount(playerA, "Evermind", 1);
        assertHandCount(playerA, 3); // Evermind + 1 card from Evermind spliced on cast Into the fray and 1 from the copied spell with splice
    }

    /**
     * {4}{U} Enchantment (Enchant Player) Whenever enchanted player casts an
     * instant or sorcery spell, each other player may copy that spell and may
     * choose new targets for the copy they control.
     * <p>
     * Reported bug: "A player with Curse of Echoes attached to them played
     * Bribery and the player who controlled the curse had control of all 3
     * copies. This seems to be the case for all spells."
     */
    @Test
    public void testCurseOfEchoes() {

        addCard(Zone.HAND, playerA, "Curse of Echoes");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Echoes");
        addTarget(playerA, playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt");
        addTarget(playerB, playerA); // original target
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertLife(playerA, 17); // still takes original spell's damage
        assertLife(playerB, 17); // copy redirected
    }

    /**
     * What happened was my opponent had an Atraxa, Praetors' Voice and a
     * Walking Ballista with 2 counters in play. On my turn, I cast Flame Slash
     * targeting Atraxa and holding priority, then I cast Dualcaster Mage. I
     * change the target of the Flame Slash copy to Walking Ballista. My
     * opponent removes the counters from Ballista to kill a 2/2 creature of
     * mine. Game log says both Flame Slashes fizzle, and Atraxa ends up still
     * being in play at the end of it all. Only the Flame Slash targeting
     * Walking Ballista should have fizzled.
     */
    @Test
    public void testOnlyCopyFizzles() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Flying, vigilance, deathtouch, lifelink
        // At the beginning of your end step, proliferate.
        addCard(Zone.BATTLEFIELD, playerA, "Atraxa, Praetors' Voice", 1);
        // Walking Ballista enters the battlefield with X +1/+1 counters on it.
        // {4}: Put a +1/+1 counter on Walking Ballista.
        // Remove a +1/+1 counter from Walking Ballista: It deals 1 damage to any target.
        addCard(Zone.HAND, playerA, "Walking Ballista"); // {X}{X}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        // Flame Slash deals 4 damage to target creature.
        addCard(Zone.HAND, playerB, "Flame Slash"); // Sorcery {R}
        // Flash
        // When Dualcaster Mage enters the battlefield, copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerB, "Dualcaster Mage"); // Creature {1}{R}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Walking Ballista");
        setChoice(playerA, "X=1");
        setChoice(playerA, "Walking Ballista"); // for proliferate

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flame Slash", "Atraxa, Praetors' Voice");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Dualcaster Mage");
        addTarget(playerB, "Flame Slash"); // original target
        setChoice(playerB, true);
        addTarget(playerB, "Walking Ballista");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove a", "Silvercoat Lion", "Flame Slash", StackClause.WHILE_COPY_ON_STACK);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove a", "Silvercoat Lion", "Flame Slash", StackClause.WHILE_COPY_ON_STACK);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Dualcaster Mage", 1);
        assertPermanentCount(playerA, "Atraxa, Praetors' Voice", 0);
        assertPermanentCount(playerA, "Walking Ballista", 0);
        assertGraveyardCount(playerB, "Flame Slash", 1);

    }

    /**
     * Sevinne's Reclamation is almost unique in that the original spell resolves before the copy.
     * As a result when resolving the original the copy was being removed from the stack instead.
     */
    @Test
    public void testSevinnesReclamation() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield.
        // If this spell was cast from a graveyard, you may copy this spell and may choose a new target for the copy.
        // Flashback 4W
        addCard(Zone.GRAVEYARD, playerA, "Sevinne's Reclamation");
        addCard(Zone.GRAVEYARD, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        addTarget(playerA, "Mountain");
        setChoice(playerA, true); // Copy
        setChoice(playerA, true); // Choose new target
        addTarget(playerA, "Island");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Island", 1);
    }

    @Test
    public void test_AllowsMultipleInstancesOfGainedTriggers() {
        // bug:  multiple copies of Imoti, Celebrant of Bounty only giving cascade once
        // reason: gained ability used same id, so only one trigger were possible (now it uses new ids)
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        // Spells you cast with converted mana cost 6 or greater have cascade.
        // Cascade
        // (When you cast this spell exile cards from the top of your library until you exile a
        // nonland card whose converted mana cost is less than this spell's converted mana cost. You may cast
        // that spell without paying its mana cost if its converted mana cost is less than this spell's
        // converted mana cost. Then put all cards exiled this way that weren't cast on the bottom of
        // your library in a random order.)
        addCard(Zone.BATTLEFIELD, playerA, "Imoti, Celebrant of Bounty", 1); // {3}{G}{U}
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        //
        // You may have Spark Double enter the battlefield as a copy of a creature or planeswalker you control,
        // except it enters with an additional +1/+1 counter on it if it’s a creature, it enters with an
        // additional loyalty counter on it if it’s a planeswalker, and it isn’t legendary if that
        // permanent is legendary.
        addCard(Zone.HAND, playerA, "Spark Double", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.HAND, playerA, "Alpha Tyrranax", 1); // {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        // cast spark and make imoti's copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Imoti, Celebrant of Bounty"); // copy of imoti
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Imoti, Celebrant of Bounty", 2);

        // cast big spell and catch cascade 2x times (from two copies)
        // possible bug: cascade activates only 1x times
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Tyrranax");
        checkStackSize("afer big spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);
        setChoice(playerA, "cascade"); // choice between 2x gained cascades
        setChoice(playerA, true); // cast first bolt by first cascade
        addTarget(playerA, playerB); // target for first bolt
        setChoice(playerA, true); // cast second bold by second cascade
        addTarget(playerA, playerB); // target for second bolt

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 20 - 3 * 2); // 2x bolts from 2x cascades
    }

    @Test
    public void test_CopiedSpellsMustUseIndependentCards() {
        // possible bug: copied spell on stack depends on the original spell/card
        // https://github.com/magefree/mage/issues/7634

        // Return any number of cards with different converted mana costs from your graveyard to your hand.
        // Put Seasons Past on the bottom of its owner's library.
        addCard(Zone.HAND, playerA, "Seasons Past", 1); // {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 5); // for return
        //
        // Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Fork", 1); // {R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // cast season and make copy of it on stack
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Seasons Past");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fork", "Seasons Past", "Cast Seasons Past");
        checkStackSize("after copy cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // season + fork
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after copy resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // season + copied season
        checkStackObject("after copy resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Seasons Past", 2);

        // resolve copied season (possible bug: after copied resolve it will return an original card too, so original spell will be fizzled)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        setChoice(playerA, "Grizzly Bears"); // return to hand
        checkStackSize("after copied resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        // resolve original season
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        setChoice(playerA, "Grizzly Bears"); // return to hand
        checkStackSize("after original resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_CopiedSpellsAndX_1() {
        // testing:
        // 1. x in copied instant spell (copy X)
        // 2. x in copied creature (X=0)

        // test use case with rules:
        // https://tappedout.net/mtg-questions/copying-a-creature-with-x-in-its-mana-cost/#c3561513
        // 107.3f If a card in any zone other than the stack has an {X} in its mana cost, the value of {X} is
        // treated as 0, even if the value of X is defined somewhere within its text.

        // Whenever you cast an instant or sorcery spell, you may pay {U}{R}. If you do, copy that spell. You may choose new targets for the copy.
        // Whenever another nontoken creature enters the battlefield under your control, you may pay {G}{U}. If you do, create a token that’s a copy of that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Riku of Two Reflections", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // Banefire deals X damage to any target.
        addCard(Zone.HAND, playerA, "Banefire", 1); // {X}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        // 0/0
        // Capricopian enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Capricopian", 1); // {X}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // 1
        // cast banefire and make copy
        // announced X=2 must be copied
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banefire", playerB);
        setChoice(playerA, "X=2");
        setChoice(playerA, true); // make copy
        setChoice(playerA, false); // keep target same
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("after spell copy", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 2 * 2);

        // 2
        // cast creature and copy it as token
        // token must have x=0 (dies)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Capricopian");
        setChoice(playerA, "X=1");
        setChoice(playerA, true); // make copy
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after creature copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Capricopian", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_CopiedSpellsHasntETB() {
        // testing:
        // - x in copied creature spell (copy x)
        // - copied spells enters as tokens and it hasn't ETB, see rules below

        // 0/0
        // Capricopian enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Capricopian", 1); // {X}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Grenzo, Dungeon Warden enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Grenzo, Dungeon Warden", 1);// {X}{B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        // (A copy of a creature spell becomes a token.)
        addCard(Zone.HAND, playerA, "Double Major", 2); // {G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // 1. Capricopian
        // cast and put on stack
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Capricopian");
        setChoice(playerA, "X=2");
        // copy of spell
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Capricopian", "Capricopian");

        // 2. Grenzo, Dungeon Warden
        // cast and put on stack
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grenzo, Dungeon Warden");
        setChoice(playerA, "X=2");
        // copy of spell
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Grenzo, Dungeon Warden", "Grenzo, Dungeon Warden");

        // ETB triggers will not trigger here due not normal cast. From rules:
        // - The token that a resolving copy of a spell becomes isn’t said to have been “created.” (2021-04-16)
        // - A nontoken permanent “enters the battlefield” when it’s moved onto the battlefield from another zone.
        //   A token “enters the battlefield” when it’s created. See rules 403.3, 603.6a, 603.6d, and 614.12.
        //
        // So both copies enters without counters:
        // - Capricopian copy must die
        // - Grenzo, Dungeon Warden must have default PT

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Capricopian", 1); // copy dies
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grenzo, Dungeon Warden", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // counters checks
        int originalCounters = currentGame.getBattlefield().getAllActivePermanents().stream()
                .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                .filter(p -> !p.isCopy())
                .mapToInt(p -> p.getCounters(currentGame).getCount(CounterType.P1P1))
                .sum();
        int copyCounters = currentGame.getBattlefield().getAllActivePermanents().stream()
                .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                .filter(p -> p.isCopy())
                .mapToInt(p -> p.getCounters(currentGame).getCount(CounterType.P1P1))
                .sum();
        Assert.assertEquals("original grenzo must have 2x counters", 2, originalCounters);
        Assert.assertEquals("copied grenzo must have 0x counters", 0, copyCounters);
    }

    @Test
    public void test_SimpleCopy_Card() {
        Card sourceCard = CardRepository.instance.findCard("Grizzly Bears").getCard();
        Card originalCard = CardRepository.instance.findCard("Grizzly Bears").getCard();
        prepareZoneAndZCC(originalCard);
        Card copiedCard = currentGame.copyCard(originalCard, null, playerA.getId());
        // main
        Assert.assertNotEquals("main - id must be different", originalCard.getId(), copiedCard.getId());
        Assert.assertEquals("main - rules must be same", originalCard.getRules(), copiedCard.getRules());
        abilitySourceMustBeSame(sourceCard, "main source");
        abilitySourceMustBeSame(originalCard, "main original"); // original card can be broken after copyCard call
        abilitySourceMustBeSame(copiedCard, "main copied");
        //cardsMustHaveSameZoneAndZCC(originalCard, copiedCard, "main");
    }

    @Test
    public void test_SimpleCopy_SplitCard() {
        SplitCard sourceCard = (SplitCard) CardRepository.instance.findCard("Alive // Well").getCard();
        SplitCard originalCard = (SplitCard) CardRepository.instance.findCard("Alive // Well").getCard();
        prepareZoneAndZCC(originalCard);
        SplitCard copiedCard = (SplitCard) currentGame.copyCard(originalCard, null, playerA.getId());
        // main
        Assert.assertNotEquals("main - id must be different", originalCard.getId(), copiedCard.getId());
        Assert.assertEquals("main - rules must be same", originalCard.getRules(), copiedCard.getRules());
        abilitySourceMustBeSame(sourceCard, "main source");
        abilitySourceMustBeSame(originalCard, "main original");
        abilitySourceMustBeSame(copiedCard, "main copied");
        //cardsMustHaveSameZoneAndZCC(originalCard, copiedCard, "main");
        // left
        Assert.assertNotEquals("left - id must be different", originalCard.getLeftHalfCard().getId(), copiedCard.getLeftHalfCard().getId());
        Assert.assertEquals("left - rules must be same", originalCard.getLeftHalfCard().getRules(), copiedCard.getLeftHalfCard().getRules());
        Assert.assertEquals("left - parent ref", copiedCard.getLeftHalfCard().getParentCard().getId(), copiedCard.getId());
        abilitySourceMustBeSame(originalCard.getLeftHalfCard(), "left original");
        abilitySourceMustBeSame(copiedCard.getLeftHalfCard(), "left copied");
        //cardsMustHaveSameZoneAndZCC(originalCard.getLeftHalfCard(), copiedCard.getLeftHalfCard(), "left");
        // right
        Assert.assertNotEquals("right - id must be different", originalCard.getRightHalfCard().getId(), copiedCard.getRightHalfCard().getId());
        Assert.assertEquals("right - rules must be same", originalCard.getRightHalfCard().getRules(), copiedCard.getRightHalfCard().getRules());
        Assert.assertEquals("right - parent ref", copiedCard.getRightHalfCard().getParentCard().getId(), copiedCard.getId());
        abilitySourceMustBeSame(originalCard.getRightHalfCard(), "right original");
        abilitySourceMustBeSame(copiedCard.getRightHalfCard(), "right copied");
        //cardsMustHaveSameZoneAndZCC(originalCard.getRightHalfCard(), copiedCard.getRightHalfCard(), "right");
    }

    @Test
    public void test_SimpleCopy_AdventureCard() {
        AdventureCard sourceCard = (AdventureCard) CardRepository.instance.findCard("Animating Faerie").getCard();
        AdventureCard originalCard = (AdventureCard) CardRepository.instance.findCard("Animating Faerie").getCard();
        prepareZoneAndZCC(originalCard);
        AdventureCard copiedCard = (AdventureCard) currentGame.copyCard(originalCard, null, playerA.getId());
        // main
        Assert.assertNotEquals("main - id must be different", originalCard.getId(), copiedCard.getId());
        Assert.assertEquals("main - rules must be same", originalCard.getRules(), copiedCard.getRules());
        abilitySourceMustBeSame(sourceCard, "main source");
        abilitySourceMustBeSame(originalCard, "main original");
        abilitySourceMustBeSame(copiedCard, "main copied");
        //cardsMustHaveSameZoneAndZCC(originalCard, copiedCard, "main");
        // right (spell)
        Assert.assertNotEquals("right - id must be different", originalCard.getSpellCard().getId(), copiedCard.getSpellCard().getId());
        Assert.assertEquals("right - rules must be same", originalCard.getSpellCard().getRules(), copiedCard.getSpellCard().getRules());
        Assert.assertEquals("right - parent ref", copiedCard.getSpellCard().getParentCard().getId(), copiedCard.getId());
        abilitySourceMustBeSame(originalCard.getSpellCard(), "right original");
        abilitySourceMustBeSame(copiedCard.getSpellCard(), "right copied");
        //cardsMustHaveSameZoneAndZCC(originalCard.getSpellCard(), copiedCard.getSpellCard(), "right");
    }

    @Test
    public void test_SimpleCopy_MDFC() {
        ModalDoubleFacesCard sourceCard = (ModalDoubleFacesCard) CardRepository.instance.findCard("Agadeem's Awakening").getCard();
        ModalDoubleFacesCard originalCard = (ModalDoubleFacesCard) CardRepository.instance.findCard("Agadeem's Awakening").getCard();
        prepareZoneAndZCC(originalCard);
        ModalDoubleFacesCard copiedCard = (ModalDoubleFacesCard) currentGame.copyCard(originalCard, null, playerA.getId());
        // main
        Assert.assertNotEquals("main - id must be different", originalCard.getId(), copiedCard.getId());
        Assert.assertEquals("main - rules must be same", originalCard.getRules(), copiedCard.getRules());
        abilitySourceMustBeSame(sourceCard, "main source");
        abilitySourceMustBeSame(originalCard, "main original");
        abilitySourceMustBeSame(copiedCard, "main copied");
        //cardsMustHaveSameZoneAndZCC(originalCard, copiedCard, "main");
        // left
        Assert.assertNotEquals("left - id must be different", originalCard.getLeftHalfCard().getId(), copiedCard.getLeftHalfCard().getId());
        Assert.assertEquals("left - rules must be same", originalCard.getLeftHalfCard().getRules(), copiedCard.getLeftHalfCard().getRules());
        Assert.assertEquals("left - parent ref", copiedCard.getLeftHalfCard().getParentCard().getId(), copiedCard.getId());
        abilitySourceMustBeSame(originalCard.getLeftHalfCard(), "left original");
        abilitySourceMustBeSame(copiedCard.getLeftHalfCard(), "left copied");
        //cardsMustHaveSameZoneAndZCC(originalCard.getLeftHalfCard(), copiedCard.getLeftHalfCard(), "left");
        // right
        Assert.assertNotEquals("right - id must be different", originalCard.getRightHalfCard().getId(), copiedCard.getRightHalfCard().getId());
        Assert.assertEquals("right - rules must be same", originalCard.getRightHalfCard().getRules(), copiedCard.getRightHalfCard().getRules());
        Assert.assertEquals("right - parent ref", copiedCard.getRightHalfCard().getParentCard().getId(), copiedCard.getId());
        abilitySourceMustBeSame(originalCard.getRightHalfCard(), "right original");
        abilitySourceMustBeSame(copiedCard.getRightHalfCard(), "right copied");
        //cardsMustHaveSameZoneAndZCC(originalCard.getRightHalfCard(), copiedCard.getRightHalfCard(), "right");
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/7655
     * Thieving Skydiver is kicked and then copied, but the copied version does not let you gain control of anything.
     */
    @Test
    @Ignore
    public void copySpellWithKicker() {
        // When Thieving Skydiver enters the battlefield, if it was kicked, gain control of target artifact with mana value X or less.
        // If that artifact is an Equipment, attach it to Thieving Skydiver.
        addCard(Zone.HAND, playerA, "Thieving Skydiver");
        // Copy target creature spell you control, except it isn’t legendary if the spell is legendary.
        addCard(Zone.HAND, playerA, "Double Major");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3); // Original price, + 1 kicker, + 1 for Double Major
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring", 2);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thieving Skydiver");
        setChoice(playerA, "Yes");
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Thieving Skydiver", "Thieving Skydiver");
        addTarget(playerA, "Sol Ring"); // Choice for copy
        addTarget(playerA, "Sol Ring"); // Choice for original

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Sol Ring", 2); // 1 taken by original, one by copy
        assertPermanentCount(playerB, "Sol Ring", 0);
    }

    private void abilitySourceMustBeSame(Card card, String infoPrefix) {
        Set<UUID> partIds = CardUtil.getObjectParts(card);

        card.getAbilities(currentGame).forEach(ability -> {
            // ability can refs to part or main card only
            if (!partIds.contains(ability.getSourceId())) {
                if (ability instanceof MageSingleton) {
                    // sourceId don't work with MageSingleton abilities
                    return;
                }

                Assert.fail(infoPrefix + " - " + "ability source must be same: " + ability);
            }
        });
    }

    private void prepareZoneAndZCC(Card originalCard) {
        // prepare custom zcc and zone for copy testing
        originalCard.setZoneChangeCounter(5, currentGame);
        originalCard.setZone(Zone.STACK, currentGame);
    }

    private void cardsMustHaveSameZoneAndZCC(Card originalCard, Card copiedCard, String infoPrefix) {
        // zcc and zone are not copied, so you don't need it here yet
        Zone zone1 = currentGame.getState().getZone(originalCard.getId());
        Zone zone2 = currentGame.getState().getZone(copiedCard.getId());
        int zcc1 = currentGame.getState().getZoneChangeCounter(originalCard.getId());
        int zcc2 = currentGame.getState().getZoneChangeCounter(copiedCard.getId());
        if (zone1 != zone2 || zcc1 != zcc2) {
            Assert.fail(infoPrefix + " - " + "cards must have same zone and zcc: " + zcc1 + " - " + zone1 + " != " + zcc2 + " - " + zone2);
        }
    }
}
