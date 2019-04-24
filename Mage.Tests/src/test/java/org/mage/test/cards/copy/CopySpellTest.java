package org.mage.test.cards.copy;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class CopySpellTest extends CardTestPlayerBase {

    @Test
    public void copyChainOfVapor() {
        // Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy.
        addCard(Zone.HAND, playerA, "Chain of Vapor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 10);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 10);

        // start chain from A - return pillar to hand
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chain of Vapor", "Pillarfield Ox");
        // chain 1 - B can return
        addTarget(playerB, "Island"); // select a land to sacrifice
        setChoice(playerB, "Yes"); // want to copy spell
        setChoice(playerB, "Yes"); // want to change target
        addTarget(playerB, "Silvercoat Lion"); // new target after copy
        // chain 2 - A can return
        addTarget(playerA, "Island"); // select a land to sacrifice
        setChoice(playerA, "Yes"); // want to copy spell
        setChoice(playerA, "Yes"); // want to change target
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

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Blessing", "Zada, Hedron Grinder");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Angelic Blessing", 1);
        assertPowerToughness(playerA, "Pillarfield Ox", 5, 7);
        assertAbility(playerA, "Pillarfield Ox", FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, "Zada, Hedron Grinder", 6, 6);
        assertAbility(playerA, "Zada, Hedron Grinder", FlyingAbility.getInstance(), true);

        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertAbility(playerB, "Silvercoat Lion", FlyingAbility.getInstance(), false);
    }

    /**
     * Reported bug: "Silverfur Partisan and fellow wolves did not trigger off
     * of copies of Strength of Arms made by Zada, Hedron Grinder. Not sure
     * about other spells, but I imagine similar results."
     */
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

        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Village Messenger");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Zada, Hedron Grinder");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Giant Growth", 1);
        assertPowerToughness(playerA, "Silverfur Partisan", 5, 5);
        assertPowerToughness(playerA, "Zada, Hedron Grinder", 6, 6);
        assertPermanentCount(playerA, "Wolf", 1); // created from Silverfur ability
    }

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
     * player wishes to splice any cards onto the spell (see rule 702.46), he or
     * she reveals those cards in their hand. 706.10. To copy a spell,
     * activated ability, or triggered ability means to put a copy of it onto
     * the stack; a copy of a spell isn't cast and a copy of an activated
     * ability isn't activated. A copy of a spell or ability copies both the
     * characteristics of the spell or ability and all decisions made for it,
     * including modes, targets, the value of X, and additional or alternative
     * costs. (See rule 601, “Casting Spells.”)
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
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Into the Fray", 1);
        assertHandCount(playerA, "Evermind", 1);
        assertHandCount(playerA, 3); // Evermind + 1 card from Evermind spliced on cast Into the fray and 1 from the copied spell with splice
    }

    /**
     * {4}{U} Enchantment (Enchant Player) Whenever enchanted player casts an
     * instant or sorcery spell, each other player may copy that spell and may
     * choose new targets for the copy he or she controls.
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
        setChoice(playerA, "Yes");
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
        addCard(Zone.BATTLEFIELD, playerA, "Atraxa, Praetors' Voice", 4);
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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Walking Ballista");
        setChoice(playerA, "X=1");
        setChoice(playerA, "Walking Ballista"); // for proliferate

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flame Slash", "Atraxa, Praetors' Voice");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Dualcaster Mage");
        addTarget(playerB, "Flame Slash"); // original target
        setChoice(playerB, "Yes");
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
}
