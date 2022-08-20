package org.mage.test.cards.copy;

import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.*;

/**
 * @author noxx
 * <p>
 * Card: You may have {this} enter the battlefield as a copy of any creature on
 * the battlefield, except it's an Illusion in addition to its other types and
 * it gains "When this creature becomes the target of a spell or ability,
 * sacrifice it."
 */
public class PhantasmalImageTest extends CardTestPlayerBase {

    @Test
    public void testCopyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Image");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    /**
     * Tests that copy effect will copy EntersBattlefieldTriggeredAbility and it
     * will be applied.
     */
    @Test
    public void testCopyEntersBattlefieldTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Image");
        addCard(Zone.BATTLEFIELD, playerB, "Howling Banshee");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Howling Banshee", 1);
        assertPermanentCount(playerB, "Howling Banshee", 1);

        assertLife(playerA, 17);
        assertLife(playerB, 17);
    }

    /**
     * Tests that copy won't have level up counters and will have zero level.
     */
    @Test
    public void testCopyCreatureWithLevelUpAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 12);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Phantasmal Image");

        for (int i = 0; i < 12; i++) {
            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Level up {1}");
        }

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Transcendent Master", 1);
        assertPermanentCount(playerB, "Transcendent Master", 1);

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Permanent masterCopied = getPermanent("Transcendent Master", playerB.getId());

        // Original master should be upgraded to 3rd level
        assertEquals("Power different", 9, master.getPower().getValue());
        assertEquals("Toughness different", 9, master.getToughness().getValue());
        assertTrue(master.getAbilities().contains(LifelinkAbility.getInstance()));
        assertTrue(master.getAbilities().containsRule(IndestructibleAbility.getInstance()));

        // But copied one should not
        assertEquals("Power different", 3, masterCopied.getPower().getValue());
        assertEquals("Toughness different", 3, masterCopied.getToughness().getValue());
        assertFalse(masterCopied.getAbilities().contains(LifelinkAbility.getInstance()));
        assertFalse(masterCopied.getAbilities().containsRule(IndestructibleAbility.getInstance()));
    }

    /**
     * Tests copying creature with BecomesTargetTriggeredAbility
     */
    @Test
    public void testCopyBecomesTargetTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Phantasmal Image", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Illusionary Servant");

        setChoice(playerA, "Illusionary Servant");
        setChoice(playerA, "Illusionary Servant-M10");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, "Illusionary Servant", 3);
    }

    //  PhantasmalImageTest.testCopyAlreadyTransformed:143->
    //  CardTestPlayerAPIImpl.assertPowerToughness:351->CardTestPlayerAPIImpl.assertPowerToughness:337
    // There is no such creature under player's control with specified power&toughness, player=PlayerA,
    // cardName=Ravager of the Fells (found similar: 1, one of them: power=8 toughness=8)

    /**
     * Tests copying already transformed creature Makes sure it still has "When
     * this creature becomes the target of a spell or ability, sacrifice it"
     */
    @Test
    public void testCopyAlreadyTransformed() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerB, "Phantasmal Image");
        // Target creature gets +4/+4 until end of turn.
        addCard(Zone.HAND, playerB, "Titanic Growth");

        // Enchantment - Creatures you control have hexproof.
        addCard(Zone.HAND, playerA, "Asceticism");

        // Whenever this creature enters the battlefield or transforms into
        // Huntmaster of the Fells, put a 2/2 green Wolf creature token onto
        // the battlefield and you gain 2 life.
        // At the beginning of each upkeep, if no spells were cast last turn, transform Huntmaster of the Fells. ==> Ravager of the Fells
        addCard(Zone.BATTLEFIELD, playerA, "Huntmaster of the Fells");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image"); // copy target: Ravergers of the Fells
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Asceticism");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Titanic Growth", "Ravager of the Fells");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);
        // check opponent's creature wasn't chosen as a target for Titanic Growth
        assertPowerToughness(playerA, "Ravager of the Fells", 4, 4);
        assertGraveyardCount(playerB, "Titanic Growth", 1);
        // check playerB's creature was sacrificed
        assertGraveyardCount(playerB, "Phantasmal Image", 1);
        assertPermanentCount(playerB, "Ravager of the Fells", 0);

    }

    /**
     * Tests that copy of Geralf's Messenger also enters tapped Geralf's
     * Messenger: Geralf's Messenger enters the battlefield tapped
     */
    @Test
    public void testCopyEntersTapped() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // You may have Phantasmal Image enter the battlefield as a copy of any creature
        // on the battlefield, except it's an Illusion in addition to its other types and
        // it gains "When this creature becomes the target of a spell or ability, sacrifice it."
        addCard(Zone.HAND, playerA, "Phantasmal Image");
        addCard(Zone.BATTLEFIELD, playerB, "Geralf's Messenger");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent copy = getPermanent("Geralf's Messenger", playerA.getId());
        Assert.assertNotNull(copy);
        Assert.assertTrue("Should be tapped", copy.isTapped());

        // Tests: When Geralf's Messenger enters the battlefield, target opponent loses 2 life.
        assertLife(playerB, 18);
    }

    /**
     * Tests that copy effect will copy AsEntersBattlefieldAbility and will
     * choose another color. As there is no permanent of the second color, copy
     * of Lurebound Scarecrow will be sacrificed.
     */
    @Test
    public void testCopyAsEntersBattlefieldAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Phantasmal Image");

        // As Lurebound Scarecrow enters the battlefield, choose a color.
        // When you control no permanents of the chosen color, sacrifice Lurebound Scarecrow.
        addCard(Zone.HAND, playerA, "Lurebound Scarecrow");

        setChoice(playerA, "Green");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lurebound Scarecrow");
        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lurebound Scarecrow", 1);
    }

    /**
     * Tests that copy effect will copy AsEntersBattlefieldAbility and will
     * choose another color. Both Lurebound Scarecrow cards should stay on
     * battlefield.
     */
    @Test
    public void testCopyAsEntersBattlefieldAbility2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Phantasmal Image");
        addCard(Zone.HAND, playerA, "Lurebound Scarecrow");

        setChoice(playerA, "Green");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lurebound Scarecrow");
        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lurebound Scarecrow", 2);
    }

    @Test
    public void testCopiedFlyingWorks() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Image");
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        addCard(Zone.BATTLEFIELD, playerB, "Azure Drake");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        attack(1, playerA, "Azure Drake");
        block(1, playerB, "Llanowar Elves", "Azure Drake");

        attack(2, playerB, "Azure Drake");
        block(2, playerA, "Elite Vanguard", "Azure Drake");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);
        assertLife(playerA, 18);
    }

    /**
     * I attack with a Phantasmal Image of Steel Hellkite. It deals damage. I
     * activate it for zero. A.I. has Chalice of the Void set to one counter.
     * The Chalice should be destroyed I think as in play it has a converted
     * mana cost of zero but it is not.
     */
    @Test
    public void testCopiedSteelHellkite() {
        addCard(Zone.BATTLEFIELD, playerA, "Steel Hellkite");
        addCard(Zone.HAND, playerA, "Chalice of the Void");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Phantasmal Image");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=0");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image");
        setChoice(playerB, "Steel Hellkite");

        attack(4, playerB, "Steel Hellkite");

        activateAbility(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "{X}:");
        setChoice(playerB, "X=0");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Steel Hellkite", 1);
        assertPermanentCount(playerB, "Steel Hellkite", 1);

        assertLife(playerB, 20);
        assertLife(playerA, 15);

        assertPermanentCount(playerA, "Chalice of the Void", 0);
        assertGraveyardCount(playerA, "Chalice of the Void", 1);

    }

    /**
     * I cast Phantasmal Image copying a Frost Titan and the image did not have
     * the "When this creature becomes the target of a spell or ability,
     * sacrifice it." ability. I did not pay attention to see if it failed to
     * become an illusion too.
     */
    @Test
    public void testCopiedFrostTitan() {
        // Whenever Frost Titan becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}.
        // Whenever Frost Titan enters the battlefield or attacks, tap target permanent. It doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerA, "Frost Titan");
        addCard(Zone.HAND, playerA, "Terror");
        // {1}{U} - Target creature gains shroud until end of turn and can't be blocked this turn.
        addCard(Zone.HAND, playerA, "Veil of Secrecy");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Phantasmal Image");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image"); // not targeted
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Veil of Secrecy", "Frost Titan"); // so it's no longer targetable
        setChoice(playerB, "Frost Titan");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Terror", "Frost Titan"); // of player Bs Phantasmal Image copying Frost Titan
        // should be countered if not paying {2}

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Veil of Secrecy", 1);
        assertGraveyardCount(playerA, "Terror", 1);

        assertLife(playerB, 20);
        assertLife(playerA, 20);

        assertPermanentCount(playerA, "Frost Titan", 1);

        assertGraveyardCount(playerB, "Phantasmal Image", 1); // if triggered ability did not work, the Titan would be in the graveyard instaed

    }

    // I've casted a Phantasmal Image targeting opponent's Wurmcoil Engine
    // When my Phantasmal Image died, it didn't triggered the Wurmcoil Engine's last ability
    // (When Wurmcoil Engine dies, put a 3/3 colorless Wurm artifact creature token with deathtouch and
    // a 3/3 colorless Wurm artifact creature token with lifelink onto the battlefield.)
    @Test
    public void testDiesTriggeredAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Wurmcoil Engine");
        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Phantasmal Image");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image"); // not targeted
        setChoice(playerB, "Wurmcoil Engine");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Public Execution", "Wurmcoil Engine"); // of player Bs Phantasmal Image copying Frost Titan
        // should be countered if not paying {2}

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Public Execution", 1);

        assertLife(playerB, 20);
        assertLife(playerA, 20);

        assertPermanentCount(playerA, "Wurmcoil Engine", 1);

        assertGraveyardCount(playerB, "Phantasmal Image", 1);
        assertPermanentCount(playerB, "Phyrexian Wurm Token", 2); // if triggered ability did not work, the Titan would be in the graveyard instaed

    }

    /**
     * Phantasmal Image is not regestering Leave the battlefield triggers,
     * persist and undying triggers
     */
    @Test
    public void testLeavesTheBattlefieldTriggeredAbilities() {
        // Shadow (This creature can block or be blocked by only creatures with shadow.)
        // When Thalakos Seer leaves the battlefield, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Thalakos Seer");

        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Phantasmal Image");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image"); // not targeted
        setChoice(playerB, "Thalakos Seer");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Public Execution", "Thalakos Seer");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Public Execution", 1);

        assertLife(playerB, 20);
        assertLife(playerA, 20);

        assertPermanentCount(playerA, "Thalakos Seer", 1);

        assertGraveyardCount(playerB, "Phantasmal Image", 1);

        assertHandCount(playerB, 2); // 1 from draw turn 2 and 1 from Thalakos Seer leaves the battlefield trigger
    }

    /**
     * Action Game State 1 -----------------> Game State 2 (On 'field) (Move to
     * GY) (In graveyard)
     * <p>
     * LTB abilities such as Persist are expceptional in that they trigger based
     * on their existence and state of objects before the event (Game State 1,
     * when the card is on the battlefield) rather than after (Game State 2,
     * when the card is in the graveyard). It doesn't matter that the LTB
     * ability doesn't exist in Game State 2. [CR 603.6d]
     * <p>
     * 603.6d Normally, objects that exist immediately after an event are
     * checked to see if the event matched any trigger conditions. Continuous
     * effects that exist at that time are used to determine what the trigger
     * conditions are and what the objects involved in the event look like.
     * However, some triggered abilities must be treated specially.
     * Leaves-the-battlefield abilities, abilities that trigger when a permanent
     * phases out, abilities that trigger when an object that all players can
     * see is put into a hand or library, abilities that trigger specifically
     * when an object becomes unattached, abilities that trigger when a player
     * loses control of an object, and abilities that trigger when a player
     * planeswalks away from a plane will trigger based on their existence, and
     * the appearance of objects, prior to the event rather than afterward. The
     * game has to “look back in time” to determine if these abilities trigger.
     * <p>
     * Example: Two creatures are on the battlefield along with an artifact that
     * has the ability “Whenever a creature dies, you gain 1 life.” Someone
     * plays a spell that destroys all artifacts, creatures, and enchantments.
     * The artifact's ability triggers twice, even though the artifact goes to
     * its owner's graveyard at the same time as the creatures.
     */
    @Test
    public void testPersist() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // When Kitchen Finks enters the battlefield, you gain 2 life.
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        addCard(Zone.HAND, playerA, "Kitchen Finks");

        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        // You may have Phantasmal Image enter the battlefield as a copy of any creature
        // on the battlefield, except it's an Illusion in addition to its other types and
        // it gains "When this creature becomes the target of a spell or ability, sacrifice it."
        addCard(Zone.HAND, playerB, "Phantasmal Image");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitchen Finks");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image"); // not targeted
        setChoice(playerB, "Kitchen Finks");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Public Execution", "Kitchen Finks");
        setChoice(playerB, "Kitchen Finks");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Public Execution", 1);

        assertLife(playerA, 22);
        assertLife(playerB, 24);

        assertPermanentCount(playerA, "Kitchen Finks", 1);

        assertHandCount(playerB, "Phantasmal Image", 0);
        assertGraveyardCount(playerB, "Phantasmal Image", 0);
        assertPermanentCount(playerB, "Kitchen Finks", 1);
        assertPowerToughness(playerB, "Kitchen Finks", 2, 1);
    }

    @Test
    public void testUndying() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.HAND, playerA, "Butcher Ghoul");

        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        // You may have Phantasmal Image enter the battlefield as a copy of any creature
        // on the battlefield, except it's an Illusion in addition to its other types and
        // it gains "When this creature becomes the target of a spell or ability, sacrifice it."
        addCard(Zone.HAND, playerB, "Phantasmal Image");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Butcher Ghoul");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image"); // not targeted
        setChoice(playerB, "Butcher Ghoul");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Public Execution", "Butcher Ghoul");
        setChoice(playerB, "Butcher Ghoul");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Public Execution", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Butcher Ghoul", 1);

        assertHandCount(playerB, "Phantasmal Image", 0);
        assertGraveyardCount(playerB, "Phantasmal Image", 0);
        assertPermanentCount(playerB, "Butcher Ghoul", 1);
        assertPowerToughness(playerB, "Butcher Ghoul", 2, 2);
    }

    /**
     * 12:29: Attacker: Wurmcoil Engine [466] (6/6) blocked by Wurmcoil Engine
     * [4ed] (6/6) 12:29: yespair gains 6 life 12:29: HipSomHap gains 6 life
     * 12:29: Wurmcoil Engine [4ed] died 12:29: Ability triggers: Wurmcoil
     * Engine [4ed] - When Wurmcoil Engine [4ed] dies, put a a 3/3 colorless
     * Wurm artifact creature token with deathtouch onto the battlefield. Put a
     * a 3/3 colorless Wurm artifact creature token with lifelink onto the
     * battlefield. 12:29: Phantasmal Image [466] died 12:29: HipSomHap puts a
     * Wurm [7d0] token onto the battlefield 12:29: HipSomHap puts a Wurm [186]
     * token onto the battlefield
     * <p>
     * To the best of my knowledge, the Phantasmal Image [466], which entered
     * the battlefield as a Wurmcoil Engine, should grant tokens through the
     * Dies-trigger as well, right?
     */
    @Test
    public void testDiesTriggered2() {
        addCard(Zone.BATTLEFIELD, playerB, "Wurmcoil Engine");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Image");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image"); // not targeted
        setChoice(playerA, "Wurmcoil Engine");

        attack(2, playerB, "Wurmcoil Engine");
        block(2, playerA, "Wurmcoil Engine", "Wurmcoil Engine");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 26);
        assertLife(playerA, 26);

        assertGraveyardCount(playerA, "Phantasmal Image", 1);
        assertGraveyardCount(playerB, "Wurmcoil Engine", 1);

        assertPermanentCount(playerA, "Phyrexian Wurm Token", 2);
        assertPermanentCount(playerB, "Phyrexian Wurm Token", 2);
    }

    /**
     * A Phantasmal Image that was copying a Voice of Resurgence died and left
     * no token behind.
     */
    @Test
    public void testVoiceOfResurgence() {
        // Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies,
        // put a green and white Elemental creature token onto the battlefield with
        // "This creature's power and toughness are each equal to the number of creatures you control."
        addCard(Zone.BATTLEFIELD, playerB, "Voice of Resurgence");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Image");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image"); // not targeted
        setChoice(playerA, "Voice of Resurgence");

        attack(2, playerB, "Voice of Resurgence");
        block(2, playerA, "Voice of Resurgence", "Voice of Resurgence");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20);
        assertLife(playerA, 20);

        assertGraveyardCount(playerA, "Phantasmal Image", 1);
        assertGraveyardCount(playerB, "Voice of Resurgence", 1);

        assertPermanentCount(playerB, "Elemental Token", 1);
        assertPermanentCount(playerA, "Elemental Token", 1);
    }

    @Test
    public void testAnimatedArtifact() {
        addCard(Zone.BATTLEFIELD, playerB, "Chimeric Staff");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Image");

        setChoice(playerB, "X=1");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{X}");

        setChoice(playerA, "Chimeric Staff");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Permanent staffA = getPermanent("Chimeric Staff", playerA);
        assertTrue("Phantasmal Image should be an artifact", staffA.isArtifact(currentGame));
        assertTrue("Phantasmal Image should not be a creature", !staffA.isCreature(currentGame));
        assertTrue("Phantasmal Image should not be an Illusion", !staffA.hasSubtype(SubType.ILLUSION, currentGame));
        assertTrue("Phantasmal Image should not be a Construct", !staffA.hasSubtype(SubType.CONSTRUCT, currentGame));
        assertTrue("Phantasmal Image should have the sacrifice trigger", staffA.getAbilities(currentGame).containsClass(BecomesTargetTriggeredAbility.class));

        Permanent staffB = getPermanent("Chimeric Staff", playerB);
        assertTrue("Chimeric Staff should be an artifact", staffB.isArtifact(currentGame));
        assertTrue("Chimeric Staff should be a creature", staffB.isCreature(currentGame));
        assertTrue("Chimeric Staff should be a Construct", staffB.hasSubtype(SubType.CONSTRUCT, currentGame));
    }

    @Test
    public void testAnimatedTribal() {
        addCard(Zone.BATTLEFIELD, playerB, "Cloak and Dagger");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Karn's Touch");
        addCard(Zone.HAND, playerA, "Phantasmal Image");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn's Touch", "Cloak and Dagger");

        setChoice(playerA, "Cloak and Dagger");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Permanent cloakA = getPermanent("Cloak and Dagger", playerA);
        assertTrue("Phantasmal Image should be an artifact", cloakA.isArtifact(currentGame));
        assertTrue("Phantasmal Image should be tribal", cloakA.isTribal(currentGame));
        assertTrue("Phantasmal Image should not be a creature", !cloakA.isCreature(currentGame));
        assertTrue("Phantasmal Image should be a Rogue", cloakA.hasSubtype(SubType.ROGUE, currentGame));
        assertTrue("Phantasmal Image should be an Illusion", cloakA.hasSubtype(SubType.ILLUSION, currentGame));
        assertTrue("Phantasmal Image should be an Equipment", cloakA.hasSubtype(SubType.EQUIPMENT, currentGame));
        assertTrue("Phantasmal Image should have the sacrifice trigger", cloakA.getAbilities(currentGame).containsClass(BecomesTargetTriggeredAbility.class));

        Permanent cloakB = getPermanent("Cloak and Dagger", playerB);
        assertTrue("Cloak and Dagger should be an artifact", cloakB.isArtifact(currentGame));
        assertTrue("Cloak and Dagger should be a creature", cloakB.isCreature(currentGame));
        assertTrue("Cloak and Dagger should be tribal", cloakB.isTribal(currentGame));
        assertTrue("Cloak and Dagger should be a Rogue", cloakB.hasSubtype(SubType.ROGUE, currentGame));
        assertTrue("Cloak and Dagger should be an Equipment", cloakB.hasSubtype(SubType.EQUIPMENT, currentGame));
    }
}
