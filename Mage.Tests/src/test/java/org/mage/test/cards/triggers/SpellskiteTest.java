package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class SpellskiteTest extends CardTestPlayerBase {

    /**
     * Tests that Wild Defiance triggers for Spellskite if spell target is
     * changed to Spellskite
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Wild Defiance", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Spellskite", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U/P}: Change a target of target spell or ability to {this}.", "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Spellskite", 1);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, "Spellskite", 3, 7);

    }

    /**
     * If Spellskite changes controller, its activated ability can activate but
     * doesn't resolve properly.
     *
     * The specific instance was a Spellskite controlled by Vedalken Shackles.
     * Land was targeted by Frost Titan, controller (not owner) of Spellskite
     * paid the redirection cost, ability went on the stack, seemed to resolve,
     * target never changed.
     *
     */
    /**
     * TODO: This test fails sometimes when building the complete Test Project
     * -> Find the reason
     */
    @Test
    public void testAfterChangeOfController() {
        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // {2}, {tap}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Shackles", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        // {U/P}: Change a target of target spell or ability to Spellskite.
        addCard(Zone.BATTLEFIELD, playerB, "Spellskite", 1);
        // {4}{U}{U}
        // Whenever Frost Titan becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays 2.
        // Whenever Frost Titan enters the battlefield or attacks, tap target permanent. It doesn't untap during its controller's next untap step.
        addCard(Zone.HAND, playerB, "Frost Titan", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Gain control", "Spellskite");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Frost Titan");
        addTarget(playerB, "Silvercoat Lion");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{U/P}: Change a target", "stack ability (Whenever {this} enters ");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spellskite", 1);
        assertPermanentCount(playerB, "Frost Titan", 1);

        assertTapped("Spellskite", true);
        // (Battlefield) Tapped state is not equal (Silvercoat Lion) expected:<false> but was:<true>
        assertTapped("Silvercoat Lion", false);

    }

    /**
     * Spellskite fails to redirect Cryptic Command on itself
     */
    @Test
    public void testSpellskite() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Choose two -
        // - Counter target spell;
        // - return target permanent to its owner's hand;
        // - tap all creatures your opponents control;
        // - draw a card.
        addCard(Zone.HAND, playerA, "Cryptic Command");

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Command", "mode=1Lightning Bolt^mode=2Silvercoat Lion", "Lightning Bolt");
        setModeChoice(playerA, "1"); // Counter target spell
        setModeChoice(playerA, "2"); // return target permanent to its owner's hand

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{U/P}: Change a target of target spell or ability to {this}.", "Cryptic Command", "Cryptic Command");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Cryptic Command", 1);

        assertHandCount(playerB, "Spellskite", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }

    /**
     * My opponent cast Cryptic Command tapping all of my creatures and bouncing
     * a Blade Splicer token I had. I activated a Spellskite but got an error
     * stating that Spellskite is not a legal target.
     */
    @Test
    public void testSpellskite2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Choose two -
        // Counter target spell;
        // or return target permanent to its owner's hand;
        // or tap all creatures your opponents control;
        // or draw a card.
        addCard(Zone.HAND, playerA, "Cryptic Command");

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Command", "mode=2Silvercoat Lion");
        setModeChoice(playerA, "2"); // return target permanent to its owner's hand
        setModeChoice(playerA, "3"); // tap all creatures your opponents control

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{U/P}: Change a target of target", "Cryptic Command", "Cryptic Command");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Cryptic Command", 1);

        assertHandCount(playerB, "Spellskite", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertTapped("Silvercoat Lion", true);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }

    /**
     * My opponent cast Lightning Bolt, targeting me. I redirected it to my
     * Spellskite. The log window said Spellskite was an invalid target (though
     * it should be valid). Spellskite still appeared to be targeted and took
     * the damage, so just a log issue I guess.
     */
    @Test
    public void testRedirectBolt() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{U/P}: Change a target", "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }

    /**
     * When an AI opponent casts a spell targeting one of my creatures, Wild
     * Defiance does not trigger. (Tested with Flame Slash, which was able to
     * kill my Spellskite)
     */
    @Test
    public void testWildDefiance() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Flame Slash deals 4 damage to target creature.
        addCard(Zone.HAND, playerA, "Flame Slash"); // {R}

        // Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Wild Defiance", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Spellskite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Slash", "Spellskite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Flame Slash", 1);
        assertPowerToughness(playerB, "Spellskite", 3, 7);
    }

    @Test
    public void testThatSpellSkiteCantBeTargetedTwiceOrMore() {
        /* According to rules, the same object can be a legal target only
           once for each instances of the word “target” in the text
           of a spell or ability.  In this case, the target can't be changed
           due to Spellskite already being a target.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite");
        addCard(Zone.BATTLEFIELD, playerB, "Scute Mob");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Royal Assassin"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pearled Unicorn"); // 2/2
        // Fiery Justice deals 5 damage divided as you choose among any number of target creatures and/or players. Target opponent gains 5 life.
        addCard(Zone.HAND, playerA, "Fiery Justice");

        // Cast Fiery Justice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Justice");
        addTargetAmount(playerA, "Scute Mob", 1); // target 1
        addTargetAmount(playerA, "Spellskite", 4); // target 2
        addTarget(playerA, playerB); // 5 life to B

        // B activate Spellskite, but can't change any targets cause it's already targeted
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{U/P}: Change a target", "Fiery Justice", "Fiery Justice");
        setChoice(playerB, true); // pay 2 life

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 + 5 - 2);
        assertGraveyardCount(playerB, 2);
        assertGraveyardCount(playerB, "Scute Mob", 1);
        assertGraveyardCount(playerB, "Spellskite", 1);
    }

    @Test
    public void testThatSplitDamageCanGetRedirected() {
        /* Standard redirect test
           The Spellskite should die from the 5 damage that was redirected to it
         */
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite");// 0/4 creature
        addCard(Zone.BATTLEFIELD, playerB, "Scute Mob"); // 1/1 creauture
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Royal Assassin"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pearled Unicorn"); // 2/2

        addCard(Zone.HAND, playerA, "Fiery Justice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Justice"); // 5 damage distributed to any number of targets
        addTargetAmount(playerA, "Scute Mob", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{U/P}: Change a target", "Fiery Justice", "Fiery Justice");
        setChoice(playerB, true); // pay 2 life
        setChoice(playerB, true); // retarget

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 + 5 - 2);
        assertGraveyardCount(playerB, 1);
        assertPermanentCount(playerB, "Scute Mob", 1);
    }

    @Test
    public void testThatSplitDamageGetsRedirectedFromTheCorrectChoice() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite");// 0/4 creature
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Royal Assassin"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pearled Unicorn"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        addCard(Zone.HAND, playerA, "Fiery Justice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Justice"); // 5 damage distributed to any number of targets
        addTargetAmount(playerA, "Royal Assassin", 1);
        addTargetAmount(playerA, "Blinking Spirit", 2);
        addTargetAmount(playerA, "Pearled Unicorn", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{U/P}: Change a target", "Fiery Justice", "Fiery Justice");
        setChoice(playerB, true); // pay 2 life
        setChoice(playerB, false); // skip royal
        setChoice(playerB, false); // skip blink
        setChoice(playerB, true); // change pearl

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 + 5 - 2);
        assertGraveyardCount(playerB, "Memnite", 0);
        assertGraveyardCount(playerB, "Royal Assassin", 1);
        assertGraveyardCount(playerB, "Blinking Spirit", 1);
        assertGraveyardCount(playerB, "Pearled Unicorn", 0);
        assertGraveyardCount(playerB, "Spellskite", 0);
    }
}
