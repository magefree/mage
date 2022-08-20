package org.mage.test.cards.abilities.keywords;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class TransformTest extends CardTestPlayerBase {

    @Test
    public void NissaVastwoodSeerTest() {

        addCard(Zone.LIBRARY, playerA, "Forest");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        // Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.

        addCard(Zone.HAND, playerA, "Nissa, Vastwood Seer");

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        // {G}{G}, Sacrifice Rootrunner: Put target land on top of its owner's library.
        addCard(Zone.BATTLEFIELD, playerB, "Rootrunner"); // {2}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nissa, Vastwood Seer");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{G}{G}", "Swamp");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "+1: Reveal");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Rootrunner", 1);

        assertPermanentCount(playerA, "Nissa, Vastwood Seer", 0);
        assertPermanentCount(playerA, "Nissa, Sage Animist", 1);

        assertCounterCount("Nissa, Sage Animist", CounterType.LOYALTY, 4);
        assertPermanentCount(playerA, "Forest", 6);
        assertPermanentCount(playerA, "Swamp", 1);
    }

    @Test
    public void LilianaHereticalHealer() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Liliana, Heretical Healer");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana, Heretical Healer");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertPermanentCount(playerA, "Liliana, Heretical Healer", 0);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertCounterCount("Liliana, Defiant Necromancer", CounterType.LOYALTY, 3);

        assertPermanentCount(playerA, "Zombie Token", 1);
    }

    /**
     * The creature-Liliana and another creature was out, Languish is cast
     * killing both, Liliana comes back transformed and no zombie. I'm fairly
     * certain she's not supposed to come back due to her exile trigger
     * shouldn't be able to exile her cos she's dead.
     */
    @Test
    public void LilianaHereticalHealer2() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Liliana, Heretical Healer");

        // All creatures get -4/-4 until end of turn.
        addCard(Zone.HAND, playerB, "Languish");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Languish");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Languish", 1);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 0);
        assertPermanentCount(playerA, "Zombie Token", 0);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Liliana, Heretical Healer", 1);
    }

    @Test
    public void TestEnchantmentToCreature() {
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);
        addCard(Zone.GRAVEYARD, playerA, "Fireball", 1);
        addCard(Zone.GRAVEYARD, playerA, "Infernal Scarring", 1);

        // {B}: Put the top card of your library into your graveyard.
        // <i>Delirium</i> &mdash At the beginning of your end step, if there are four or more card types among cards in your graveyard, transform Autumnal Gloom.
        addCard(Zone.BATTLEFIELD, playerA, "Autumnal Gloom");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Autumnal Gloom", 0);
        assertPermanentCount(playerA, "Ancient of the Equinox", 1);
    }

    /**
     * 4G Creature - Human Shaman Whenever a permanent you control transforms
     * into a non-Human creature, put a 2/2 green Wolf creature token onto the
     * battlefield.
     * <p>
     * Reported bug: "It appears to trigger either when a non-human creature
     * transforms OR when a creature transforms from a non-human into a human
     * (as in when a werewolf flips back to the sun side), rather than when a
     * creature transforms into a non-human, as is the intended function and
     * wording of the card."
     */
    @Test
    public void testCultOfTheWaxingMoon() {
        // Whenever a permanent you control transforms into a non-Human creature, put a 2/2 green Wolf creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Cult of the Waxing Moon");
        // {1}{G} - Human Werewolf
        // At the beginning of each upkeep, if no spells were cast last turn, transform Hinterland Logger.
        addCard(Zone.BATTLEFIELD, playerA, "Hinterland Logger");

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Timber Shredder.
        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "Cult of the Waxing Moon", 1);
        assertPermanentCount(playerA, "Timber Shredder", 1); // Night-side card of Hinterland Logger, Werewolf (non-human)
        assertPermanentCount(playerA, "Wolf Token", 1); // wolf token created
    }

    /**
     * Yeah, it sounds like the same thing. When Startled Awake is in the
     * graveyard, you can pay CMC 5 to return it, flipped, to the battlefield as
     * a 1/1 creature. However, after paying the 5 it returns unflipped and just
     * stays on the battlefield as a sorcery, of which it can't be interacted
     * with at all wording of the card."
     */
    @Test
    public void testStartledAwake() {
        // Target opponent puts the top thirteen cards of their library into their graveyard.
        // {3}{U}{U}: Put Startled Awake from your graveyard onto the battlefield transformed. Activate this ability only any time you could cast a sorcery.
        addCard(Zone.HAND, playerA, "Startled Awake"); // SORCERY {2}{U}{U}"
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Startled Awake", playerB);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}{U}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 13);
        assertGraveyardCount(playerA, "Startled Awake", 0);
        assertPermanentCount(playerA, "Persistent Nightmare", 1); // Night-side card of Startled Awake
        assertType("Persistent Nightmare", CardType.CREATURE, true);
        assertType("Persistent Nightmare", CardType.SORCERY, false);
    }

    @Test
    public void testStartledAwakeMoonmist() {
        addCard(Zone.HAND, playerA, "Startled Awake");
        addCard(Zone.HAND, playerA, "Moonmist");
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 11);
        addCard(Zone.BATTLEFIELD, playerA, "Maskwood Nexus");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Startled Awake", playerB);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}{U}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Moonmist");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, 13);
        assertGraveyardCount(playerA, "Startled Awake", 0);
        assertPermanentCount(playerA, "Persistent Nightmare", 1); // Night-side card of Startled Awake
        assertType("Persistent Nightmare", CardType.CREATURE, true);
        assertType("Persistent Nightmare", CardType.SORCERY, false);
    }

    /**
     * When copy token of Lambholt Pacifist transforms with "its transform
     * ability", I see below error. Then rollback.
     * <p>
     * 701.25a Only permanents represented by double-faced cards can transform.
     * (See rule 711, “Double-Faced Cards.”) If a spell or ability instructs a
     * player to transform any permanent that isn‘t represented by a
     * double-faced card, nothing happens.
     */
    @Test
    public void testTransformCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // Lambholt Pacifist can't attack unless you control a creature with power 4 or greater.
        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Pacifist.
        addCard(Zone.HAND, playerA, "Lambholt Pacifist"); // {1}{G}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerB, "Clone"); // {3}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lambholt Pacifist");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, "Lambholt Pacifist");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Lambholt Butcher", 1);

        assertPermanentCount(playerB, "Lambholt Pacifist", 1);
    }

    /**
     * Mirror Mockery copies the front face of a Transformed card rather than
     * the current face.
     * <p>
     * It's worth pointing out that my opponent cast Mirror Mockery the previous
     * turn - after it had transformed. I should have included the part of the
     * log that showed that Mirror Mockery was applied to the Unimpeded
     * Trespasser.
     */
    @Test
    public void testTransformCopyrnansformed() {
        // Skulk (This creature can't be blocked by creatures with greater power.)
        // When Uninvited Geist deals combat damage to a player, transform it.
        addCard(Zone.BATTLEFIELD, playerA, "Uninvited Geist"); // Creature 2/2 {2}{U}
        // Transformed side: Unimpeded Trespasser - Creature 3/3
        // Unimpeded Trespasser can't be blocked.

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Enchant creature
        // Whenever enchanted creature attacks, you may put a token onto the battlefield that's a copy of that creature. Exile that token at the end of combat.
        addCard(Zone.HAND, playerB, "Mirror Mockery"); // {1}{U}

        attack(1, playerA, "Uninvited Geist");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mirror Mockery", "Unimpeded Trespasser");

        attack(3, playerA, "Unimpeded Trespasser");

        setStopAt(3, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertLife(playerB, 15);

        assertPermanentCount(playerB, "Mirror Mockery", 1);
        assertPermanentCount(playerA, "Unimpeded Trespasser", 1);
        assertPermanentCount(playerB, "Unimpeded Trespasser", 1);
        assertPowerToughness(playerB, "Unimpeded Trespasser", 3, 3);
    }

    /**
     * Archangel Avacyn still transforms after being bounced by an Eldrazi
     * Displacer with her trigger on the stack.
     */
    @Test
    public void testTransformArchangelAvacyn() {
        // Flash, Flying, Vigilance
        // When Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.
        // When a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.
        addCard(Zone.BATTLEFIELD, playerA, "Archangel Avacyn"); // Creature 4/4
        // Transformed side: Avacyn, the Purifier - Creature 6/5
        // Flying
        // When this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // Devoid
        // {2}{C}: Exile another target creature, then return it to the battlefield tapped under its owner's control.
        addCard(Zone.BATTLEFIELD, playerB, "Eldrazi Displacer", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{C}", "Archangel Avacyn");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertPermanentCount(playerB, "Eldrazi Displacer", 1);
        assertPermanentCount(playerA, "Avacyn, the Purifier", 0);
        assertPermanentCount(playerA, "Archangel Avacyn", 1);
    }

    /**
     * Cards that transform if no spells cast last turn should not transform if
     * the cards were added on turn 1. This would happen with tests and cheat
     * testing.
     */
    @Test
    public void testNoSpellsCastLastTurnTransformDoesNotTriggerTurn1() {

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hinterland Logger.
        addCard(Zone.BATTLEFIELD, playerA, "Hinterland Logger");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hinterland Logger", 1);
    }

    /**
     * I had Huntmaster of the Fells in play. Opponent had Eldrazi Displacer.
     * Huntmaster triggered to transform during my opponent's upkeep. While this
     * was on stack, my opponent used Displacer's ability targeting Huntmaster.
     * That ability resolved and Huntmaster still transformed like it never left
     * the battlefield.
     * <p>
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=20014&p=210533#p210513
     * <p>
     * The transform effect on the stack should fizzle. The card brought back
     * from Exile should be a new object unless I am interpreting the rules
     * incorrectly. The returned permanent uses the same GUID.
     */
    @Test
    public void testHuntmaster() {
        // Whenever this creature enters the battlefield or transforms into Huntmaster of the Fells, create a 2/2 green Wolf creature token and you gain 2 life.
        // At the beginning of each upkeep, if no spells were cast last turn, transform Huntmaster of the Fells.
        // Ravager of the Fells
        // Whenever this creature transforms into Ravager of the Fells, it deals 2 damage to target opponent and 2 damage to up to one target creature that player controls.
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ravager of the Fells.
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells"); // Creature {2}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // Devoid
        // {2}{C}: Exile another target creature, then return it to the battlefield tapped under its owner's control.
        addCard(Zone.HAND, playerB, "Eldrazi Displacer", 1); // Creature {2}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Eldrazi Displacer");

        activateAbility(4, PhaseStep.UPKEEP, playerB, "{2}{C}", "Huntmaster of the Fells", "At the beginning of each upkeep");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
        assertPermanentCount(playerA, "Wolf Token", 2);

        assertPermanentCount(playerB, "Eldrazi Displacer", 1);

        assertPermanentCount(playerA, "Ravager of the Fells", 0);
        assertPermanentCount(playerA, "Huntmaster of the Fells", 1);
        assertPowerToughness(playerA, "Huntmaster of the Fells", 2, 2);
        assertTappedCount("Plains", true, 2);
        assertTappedCount("Wastes", true, 1);

    }

    @Test
    public void testHuntmasterTransformed() {
        // Whenever this creature enters the battlefield or transforms into Huntmaster of the Fells, create a 2/2 green Wolf creature token and you gain 2 life.
        // At the beginning of each upkeep, if no spells were cast last turn, transform Huntmaster of the Fells.
        // Ravager of the Fells
        // Whenever this creature transforms into Ravager of the Fells, it deals 2 damage to target opponent and 2 damage to up to one target creature that player controls.
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ravager of the Fells.
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells"); // Creature {2}{R}{G}
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Wolf Token", 2);
        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertPermanentCount(playerA, "Ravager of the Fells", 0);
        assertPermanentCount(playerA, "Huntmaster of the Fells", 1);
        assertPowerToughness(playerA, "Huntmaster of the Fells", 2, 2);


    }

    /**
     * Having cast Phantasmal Image copying my opponent's flipped Thing in the
     * Ice, I was left with a 0/4 Awoken Horror.
     * <p>
     * https://github.com/magefree/mage/issues/5893
     * <p>
     * The transform effect on the stack should fizzle. The card brought back
     * from Exile should be a new object unless I am interpreting the rules
     * incorrectly. The returned permanent uses the same GUID.
     */
    @Test
    public void testCopyTransformedThingInTheIce() {
        // Defender
        // Thing in the Ice enters the battlefield with four ice counters on it.
        // Whenever you cast an instant or sorcery spell, remove an ice counter from Thing in the Ice.
        // Then if it has no ice counters on it, transform it.
        addCard(Zone.HAND, playerA, "Thing in the Ice"); // Creature {1}{U}
        // Creatures you control get +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Banners Raised", 4); // Creature {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // You may have Phantasmal Image enter the battlefield as a copy of any creature
        // on the battlefield, except it's an Illusion in addition to its other types and
        // it has "When this creature becomes the target of a spell or ability, sacrifice it."
        addCard(Zone.HAND, playerB, "Phantasmal Image", 1); // Creature {1}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thing in the Ice");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Banners Raised");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Banners Raised");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Banners Raised");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Banners Raised");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Banners Raised", 4);
        assertPermanentCount(playerA, "Thing in the Ice", 0);
        assertPermanentCount(playerA, "Awoken Horror", 1);
        assertPowerToughness(playerA, "Awoken Horror", 7, 8);

        assertPermanentCount(playerB, "Awoken Horror", 1);
        assertPowerToughness(playerB, "Awoken Horror", 7, 8);

    }

    @Test
    public void testMoonmistDelver() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Delver of Secrets");
        addCard(Zone.HAND, playerA, "Moonmist", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Delver of Secrets");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Moonmist");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Delver of Secrets", 0);
        assertPermanentCount(playerA, "Insectile Aberration", 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Moonmist");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Delver of Secrets", 1);
        assertPermanentCount(playerA, "Insectile Aberration", 0);
    }
}
