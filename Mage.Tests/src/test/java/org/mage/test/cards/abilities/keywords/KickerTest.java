package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward, LevelX2, JayDi85
 */
public class KickerTest extends CardTestPlayerBase {

    /**
     * 702.32. Kicker 702.32a Kicker is a static ability that functions while
     * the spell with kicker is on the stack. “Kicker [cost]” means “You may pay
     * an additional [cost] as you cast this spell.” Paying a spell's kicker
     * cost(s) follows the rules for paying additional costs in rules 601.2b and
     * 601.2e–g. 702.32b The phrase “Kicker [cost 1] and/or [cost 2]” means the
     * same thing as “Kicker [cost 1], kicker [cost 2].” 702.32c Multikicker is
     * a variant of the kicker ability. “Multikicker [cost]” means “You may pay
     * an additional [cost] any number of times as you cast this spell.” A
     * multikicker cost is a kicker cost. 702.32d If a spell's controller
     * declares the intention to pay any of that spell's kicker costs, that
     * spell has been “kicked.” If a spell has two kicker costs or has
     * multikicker, it may be kicked multiple times. See rule 601.2b. 702.32e
     * Objects with kicker or multikicker have additional abilities that specify
     * what happens if they are kicked. These abilities are linked to the kicker
     * or multikicker abilities printed on that object: they can refer only to
     * those specific kicker or multikicker abilities. See rule 607, “Linked
     * Abilities.” 702.32f Objects with more than one kicker cost have abilities
     * that each correspond to a specific kicker cost. They contain the phrases
     * “if it was kicked with its [A] kicker” and “if it was kicked with its [B]
     * kicker,” where A and B are the first and second kicker costs listed on
     * the card, respectively. Each of those abilities is linked to the
     * appropriate kicker ability. 702.32g If part of a spell's ability has its
     * effect only if that spell was kicked, and that part of the ability
     * includes any targets, the spell's controller chooses those targets only
     * if that spell was kicked. Otherwise, the spell is cast as if it did not
     * have those targets. See rule 601.2c.
     *
     */

    /**
     * Aether Figment Creature — Illusion 1/1, 1U (2) Kicker {3} (You may pay an
     * additional {3} as you cast this spell.) Aether Figment can't be blocked.
     * If Aether Figment was kicked, it enters the battlefield with two +1/+1
     * counters on it.
     */
    @Test
    public void test_Use_Manual() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Aether Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Figment");
        setChoice(playerA, "Yes"); // with Kicker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Aether Figment", 1);
        assertCounterCount("Aether Figment", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Aether Figment", 3, 3);
    }

    @Test
    @Ignore
    // TODO: enable test after replicate ability will be supported by AI (don't forget about multikicker support too)
    public void test_Use_AI() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Aether Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Figment");
        //setChoice(playerA, "Yes"); // with Kicker - AI must choose

        //setStrictChooseMode(true); - AI must choose
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Aether Figment", 1);
        assertCounterCount("Aether Figment", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Aether Figment", 3, 3);
    }

    @Test
    public void test_DontUse_Manual() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Aether Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Figment");
        setChoice(playerA, "No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Aether Figment", 1);
        assertCounterCount("Aether Figment", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Aether Figment", 1, 1);
    }

    @Test
    @Ignore
    // TODO: enable test after replicate ability will be supported by AI (don't forget about multikicker support too)
    public void test_DontUse_AI() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5 - 1); // haven't all mana
        addCard(Zone.HAND, playerA, "Aether Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Figment");
        //setChoice(playerA, "No"); - AI must choose

        //setStrictChooseMode(true); - AI must choose
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Aether Figment", 1);
        assertCounterCount("Aether Figment", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Aether Figment", 1, 1);
    }

    /**
     * Apex Hawks Creature — Bird 2/2, 2W (3) Multikicker {1}{W} (You may pay an
     * additional {1}{W} any number of times as you cast this spell.) Flying
     * Apex Hawks enters the battlefield with a +1/+1 counter on it for each
     * time it was kicked.
     */
    @Test
    public void test_Multikicker_UseOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Apex Hawks");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apex Hawks");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Apex Hawks", 1);
        assertCounterCount("Apex Hawks", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Apex Hawks", 3, 3);

    }

    @Test
    public void test_Multikicker_UseTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Apex Hawks");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apex Hawks");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Apex Hawks", 1);
        assertCounterCount("Apex Hawks", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Apex Hawks", 4, 4);
    }

    @Test
    public void test_Multikicker_DontUse() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Apex Hawks");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apex Hawks");
        setChoice(playerA, "No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Apex Hawks", 1);
        assertCounterCount("Apex Hawks", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Apex Hawks", 2, 2);
    }

    @Test
    public void test_AndOr_UseOr() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        // Kicker {1}{G} and/or {2}{U}
        // When {this} enters the battlefield, if it was kicked with its {1}{G} kicker, destroy target creature with flying.
        // When {this} enters the battlefield, if it was kicked with its {2}{U} kicker, draw two cards.
        addCard(Zone.HAND, playerA, "Sunscape Battlemage", 1); // 2/2  {2}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sunscape Battlemage");
        setChoice(playerA, "No");  // not use kicker {1}{G}
        setChoice(playerA, "Yes"); // use kicker {2}{U}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Sunscape Battlemage", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_AndOr_UseAnd() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // Kicker {1}{G} and/or {2}{U}
        // When {this} enters the battlefield, if it was kicked with its {1}{G} kicker, destroy target creature with flying.
        // When {this} enters the battlefield, if it was kicked with its {2}{U} kicker, draw two cards.
        addCard(Zone.HAND, playerA, "Sunscape Battlemage", 1); // 2/2  {2}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Birds of Paradise", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sunscape Battlemage");
        setChoice(playerA, "Yes"); // use kicker {1}{G}
        setChoice(playerA, "Yes"); // use kicker {2}{U}
        setChoice(playerA, "When "); // two triggers from two kicker options
        addTarget(playerA, "Birds of Paradise");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Birds of Paradise", 1);
        assertPermanentCount(playerA, "Sunscape Battlemage", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_Conditional_MustWorkWithMultipleKickerOptions() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // Kicker {1}{G} and/or {2}{U}
        // When {this} enters the battlefield, if it was kicked with its {1}{G} kicker, destroy target creature with flying.
        // When {this} enters the battlefield, if it was kicked with its {2}{U} kicker, draw two cards.
        addCard(Zone.HAND, playerA, "Sunscape Battlemage", 1); // 2/2  {2}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Birds of Paradise", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Counter target spell if it was kicked.
        addCard(Zone.HAND, playerB, "Ertai's Trickery", 1);

        // cast with kicker
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sunscape Battlemage");
        setChoice(playerA, "Yes");  // use kicker {1}{G} - destroy target creature with flying
        setChoice(playerA, "Yes"); // use kicker {2}{U} - draw two cards
        // spell must be countered, so no chooses
        //setChoice(playerA, "When "); // two triggers rised: When {this} enters the battlefield, if it was kicked...
        //addTarget(playerA, "Birds of Paradise"); // target for {1}{G} trigger

        // counter kicked spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ertai's Trickery", "Sunscape Battlemage", "Sunscape Battlemage");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Birds of Paradise", 1);
        assertGraveyardCount(playerB, "Ertai's Trickery", 1);
        assertGraveyardCount(playerA, "Sunscape Battlemage", 1);
    }

    @Test
    public void test_ZCC_ReturnedPermanentMustNotBeKicked() {
        // bug:
        // If a creature is cast with kicker, dies, and is then returned to play
        // from graveyard, it still behaves like it were kicked. I noticed this
        // while testing some newly implemented cards, but it can be reproduced for
        // example by Zombifying a Gatekeeper of Malakir.

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // Kicker {B} (You may pay an additional {B} as you cast this spell.)
        // When Gatekeeper of Malakir enters the battlefield, if it was kicked, target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Gatekeeper of Malakir", 1); // 2/2  {B}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Birds of Paradise", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Return target permanent to its owner's hand.
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        // first cast with kicker
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gatekeeper of Malakir");
        setChoice(playerA, "Yes");  // use kicker
        addTarget(playerA, playerB); // trigger's target
        addTarget(playerB, "Birds of Paradise"); // sacrifice

        // return to hand
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Boomerang", "Gatekeeper of Malakir");

        // second cast without kicker
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gatekeeper of Malakir");
        setChoice(playerA, "No");  // no kicker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertGraveyardCount(playerB, "Birds of Paradise", 1);
        assertPermanentCount(playerB, "Birds of Paradise", 1);
        assertPermanentCount(playerA, "Gatekeeper of Malakir", 1);
    }

    @Test
    public void test_ZCC_CopiedSpellMustKeepKickerStatus() {
        // https://github.com/magefree/mage/issues/7192
        // bug: Krark, the thumbless and a copy of him are on the field, and I cast Rite of replication kicked.
        // The first coinflip fails and returns it to my hand, and the second coinflip wins and copies it,
        // but does not copy the kicked part. I believe I did this before in another game and the first flip
        // won then it would be a kicked copy.

        // Whenever you cast an instant or sorcery spell, you may copy that spell. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Swarm Intelligence", 1);
        //
        // Kicker {1}{R}
        // Destroy target nonblack creature. It can't be regenerated.
        // If Agonizing Demise was kicked, it deals damage equal to that creature's power to the creature's controller.
        addCard(Zone.HAND, playerA, "Agonizing Demise", 1); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears@bear1", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears@bear2", 1); // 2/2

        // cast spell with kicker and copy it (kicker status must be saved)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agonizing Demise", "@bear1");
        setChoice(playerA, "Yes"); // use kicker
        checkStackSize("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // spell + trigger
        checkStackObject("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ago", 1);
        checkStackObject("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 1);
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        setChoice(playerA, "Yes"); // copy spell
        setChoice(playerA, "Yes"); // new target
        addTarget(playerA, "@bear2");
        checkStackSize("after copy trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // spell + copy
        checkStackObject("after copy trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ago", 2);
        checkStackObject("after copy trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 2 * 2);
    }

    @Test
    public void test_ZCC_CopiedSpellMustHaveIndependentZCC_InSpell() {
        // reason: copied spell must have access to kicker status

        // Whenever you cast an instant or sorcery spell, you may copy that spell. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Swarm Intelligence", 1);
        //
        // Kicker {1}{R}
        // Destroy target nonblack creature. It can't be regenerated.
        // If Agonizing Demise was kicked, it deals damage equal to that creature's power to the creature's controller.
        addCard(Zone.HAND, playerA, "Agonizing Demise", 1); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears@bear1", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears@bear2", 1); // 2/2
        //
        // Counter target spell. You gain 3 life.
        addCard(Zone.HAND, playerA, "Absorb", 1); // {W}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // cast spell with kicker and copy it (kicker status must be saved)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agonizing Demise", "@bear1");
        setChoice(playerA, "Yes"); // use kicker
        checkStackSize("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // spell + trigger
        checkStackObject("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ago", 1);
        checkStackObject("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 1);
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        setChoice(playerA, "Yes"); // copy spell
        setChoice(playerA, "Yes"); // new target
        addTarget(playerA, "@bear2");
        checkStackSize("after copy trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // spell + copy
        checkStackObject("after copy trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ago", 2);
        checkStackObject("after copy trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 0);
        //
        // counter first spell (non copy) to change original card's ZCC
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Absorb", "Agonizing Demise[no copy]", "Agonizing Demise", StackClause.WHILE_COPY_ON_STACK);
        checkStackSize("before counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 4); // spell + copy + trigger + counter
        checkStackObject("before counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ago", 2);
        checkStackObject("before counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 1);
        checkStackObject("before counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Absorb", 1);
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true); // trigger
        setChoice(playerA, "No"); // do not copy counter spell
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true); // counter
        checkStackSize("after counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1); // copy
        checkStackObject("after counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ago", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // cast spell - countered
        // copied spell - resolved (2 damage)
        // counter - resolved (3 life)
        // possible bug: kicker status for copied spell was lost and no 2 damage
        assertLife(playerA, 20 - 2 + 3);
    }

    @Test
    public void test_ZCC_CopiedSpellMustHaveIndependentZCC_InStaticAbility() {
        // reason: static ability from copied spell's permanent must have access to kicker status

        // {4}, {T}: Copy target permanent spell you control.
        addCard(Zone.BATTLEFIELD, playerA, "Lithoform Engine", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        // Kicker {4}
        // If Academy Drake was kicked, it enters the battlefield with two +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Academy Drake", 1); // {2}{U}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4 + 4 + 3);
        //
        // Counter target spell. You gain 3 life.
        addCard(Zone.HAND, playerA, "Absorb", 1); // {W}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // cast spell with kicker
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Drake");
        setChoice(playerA, "Yes"); // use kicker

        // copy spell
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}", "Academy Drake", "Academy Drake");
        checkStackObject("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Academy Drake", 1);
        checkStackObject("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}", 1);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackObject("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Academy Drake", 2);

        // counter first spell (non copy) to change original card's ZCC
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Absorb", "Academy Drake[no copy]", "Academy Drake", StackClause.WHILE_COPY_ON_STACK);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();


        // cast spell - countered
        // copied spell - +2 counters
        // counter - resolved (3 life)
        // possible bug: kicker status for copied spell was lost and no 2 counters added
        assertPermanentCount(playerA, "Academy Drake", 1);
        assertCounterCount(playerA, "Academy Drake", CounterType.P1P1, 2);
    }

    @Test
    public void test_Single_OrimsChants() {
        // bug:
        // When I cast Orim's Chant with Kicker cost, the player can play spells
        // anyway during the turn. It seems like the kicker cost trigger an
        // "instead" creatures can't attack.

        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 1); // Haste   1/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        // Target player can't cast spells this turn.
        // If Orim's Chant was kicked, creatures can't attack this turn.
        addCard(Zone.HAND, playerA, "Orim's Chant");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Orim's Chant", playerB);
        setChoice(playerA, "Yes");

        attack(1, playerA, "Raging Goblin");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        // attack must be restricted, so no attack commands available
        //setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        //assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Orim's Chant", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_Single_BloodhuskRitualist() {
        // bug:
        // Bloodhusk Ritualist's discard trigger does nothing if the Ritualist
        // leaves the battlefield before the trigger resolves.

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Fireball", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Bloodhusk Ritualist", 1); // 2/2  {2}{B}

        // Multikicker (You may pay an additional {B} any number of times as you cast this spell.)
        // When Bloodhusk Ritualist enters the battlefield, target opponent discards a card for each time it was kicked.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodhusk Ritualist");
        setChoice(playerA, "Yes", 2); // 2 x Multikicker
        setChoice(playerA, "No"); // stop the kicking
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Bloodhusk Ritualist");
        addTarget(playerA, playerB); // target for kicker's trigger (discard cards)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        Assert.assertEquals("All mana has to be used", "[]", playerA.getManaAvailable(currentGame).toString());
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Bloodhusk Ritualist", 1);
        assertGraveyardCount(playerB, "Fireball", 2);

        assertHandCount(playerB, 0);
    }

    @Test
    public void test_Single_MarshCasualties() {
        // bug:
        // Paying the Kicker on "Marsh Casualties" has no effect. Target player's
        // creatures still only get -1/-1 instead of -2/-2. Was playing against AI.
        // It was me who cast the spell.

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // Kicker {3}
        // Creatures target player controls get -1/-1 until end of turn. If Marsh Casualties was kicked, those creatures get -2/-2 until end of turn instead.
        addCard(Zone.HAND, playerA, "Marsh Casualties", 1); // 2/2  {2}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser", 1); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Marsh Casualties", playerB);
        setChoice(playerA, "Yes");  // Pay Kicker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertTappedCount("Swamp", true, 5);
        assertGraveyardCount(playerA, "Marsh Casualties", 1);
        assertPowerToughness(playerB, "Centaur Courser", 1, 1);
    }
}
