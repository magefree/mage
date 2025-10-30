package org.mage.test.cards.abilities.keywords;

import mage.ObjectColor;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author LevelX2
 */
public class TransformTest extends CardTestPlayerBase {


    /*
    Silvercoat Lion
    {1}{W}
    Creature - Cat

    2/2
    */
    private static final String silvercoatLion = "Silvercoat Lion";

    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
    */
    private static final String lightningBolt = "Lightning Bolt";

    /*
    Azusa's Many Journeys
    {1}{G}
    Enchantment - Saga
    (As this Saga enters and after your draw step, add a lore counter.)
    I - You may play an additional land this turn.
    II - You gain 3 life.
    III - Exile this Saga, then return it to the battlefield transformed under your control.
    Likeness of the Seeker

    Enchantment Creature - Human Monk
    Whenever Likeness of the Seeker becomes blocked, untap up to three lands you control.
    3/3
    */
    private static final String azusasManyJourneys = "Azusa's Many Journeys";
    private static final String likenessOfTheSeeker = "Likeness of the Seeker";

    /*
    Liliana, Heretical Healer
    {1}{B}{B}
    Legendary Creature - Human Cleric
    Lifelink
    Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
    2/3

    Liliana, Defiant Necromancer
    Legendary Planeswalker - Liliana
    +2: Each player discards a card.
    -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
    -8: You get an emblem with "Whenever a creature you control dies, return it to the battlefield under your control at the beginning of the next end step."
    */
    private static final String lilianaHereticalHealer = "Liliana, Heretical Healer";
    private static final String lilianaDefiantNecromancer = "Liliana, Defiant Necromancer";

    /*
    Languish
    {2}{B}{B}
    Sorcery
    All creatures get -4/-4 until end of turn.
    */
    private static final String languish = "Languish";

    /*
    Nissa, Vastwood Seer
    {2}{G}
    Legendary Creature - Elf Scout
    When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
    Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
    2/2

    Nissa, Sage Animist
    Legendary Planeswalker - Nissa
    +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
    -2: Put a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World onto the battlefield.
    -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
    */
    private static final String nissaVastwoodSeer = "Nissa, Vastwood Seer";
    private static final String nissaSageAnimist = "Nissa, Sage Animist";

    /*
    Fireball
    {X}{R}
    Sorcery
    Fireball deals X damage divided evenly, rounded down, among any number of target creatures and/or players.
    Fireball costs {1} more to cast for each target beyond the first.
    */
    private static final String fireball = "Fireball";

    /*
    Infernal Scarring
    {1}{B}
    Enchantment - Aura
    Enchant creature
    Enchanted creature gets +2/+0 and has "When this creature dies, draw a card."
    */
    private static final String infernalScarring = "Infernal Scarring";

    /*
    Autumnal Gloom
    {2}{G}
    Enchantment
    {B}: Put the top card of your library into your graveyard.
    Delirium - At the beginning of your end step, if there are four or more card types among cards in your graveyard, transform Autumnal Gloom.

    Ancient of the Equinox
    Creature - Treefolk
    Trample, hexproof
    4/4
    */
    private static final String autumnalGloom = "Autumnal Gloom";
    private static final String ancientOfTheEquinox = "Ancient of the Equinox";

    /*
    Huntmaster of the Fells
    {2}{R}{G}
    Creature - Human Werewolf
    Whenever this creature enters the battlefield or transforms into Huntmaster of the Fells, put a 2/2 green Wolf creature token onto the battlefield and you gain 2 life.
    At the beginning of each upkeep, if no spells were cast last turn, transform Huntmaster of the Fells.
    2/2

    Ravager of the Fells
    Creature - Werewolf
    Trample
    Whenever this creature transforms into Ravager of the Fells, it deals 2 damage to target opponent and 2 damage to up to one target creature that player controls.
    At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ravager of the Fells.
    4/4
    */
    private static final String huntmasterOfTheFells = "Huntmaster of the Fells";
    private static final String ravagerOfTheFells = "Ravager of the Fells";

    /*
    Eldrazi Displacer
    {2}{W}
    Creature - Eldrazi
    Devoid
    {2}{C}: Exile another target creature, then return it to the battlefield tapped under its owner's control.
    3/3
    */
    private static final String eldraziDisplacer = "Eldrazi Displacer";

    /*
    Howlpack Piper
    {3}{G}
    Creature - Human Werewolf
    This spell can't be countered.
    {1}{G}, {T}: You may put a creature card from your hand onto the battlefield. If it's a Wolf or Werewolf, untap Howlpack Piper. Activate only as a sorcery.
    Daybound
    2/2

    Wildsong Howler
    Creature - Werewolf
    Whenever this creature enters the battlefield or transforms into Wildsong Howler, look at the top six cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
    Nightbound
    4/4
    */
    private static final String howlpackPiper = "Howlpack Piper";
    private static final String wildsongHowler = "Wildsong Howler";

    /*
    Thing in the Ice
    {1}{U}
    Creature - Horror
    Defender
    Thing in the Ice enters the battlefield with four ice counters on it.
    Whenever you cast an instant or sorcery spell, remove an ice counter from Thing in the Ice. Then if it has no ice counters on it, transform it.

    Awoken Horror
    Creature - Kraken Horror
    When this creature transforms into Awoken Horrow, return all non-Horror creatures to their owners' hands.
    7/8
    */
    private static final String thingInTheIce = "Thing in the Ice";
    private static final String awokenHorror = "Awoken Horror";

    /*
    Banners Raised
    {R}
    Instant
    Creatures you control get +1/+0 until end of turn.
    */
    private static final String bannersRaised = "Banners Raised";

    /*
    Phantasmal Image
    {1}{U}
    Creature - Illusion
    You may have Phantasmal Image enter the battlefield as a copy of any creature on the battlefield, except it's an Illusion in addition to its other types and it gains "When this creature becomes the target of a spell or ability, sacrifice it."
    */
    private static final String phantasmalImage = "Phantasmal Image";

    /*
    Delver of Secrets
    {U}
    Creature - Human Wizard
    At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
    1/1

    Insectile Aberration
    Creature - Human Insect
    Flying
    3/2
    */
    private static final String delverOfSecrets = "Delver of Secrets";
    private static final String insectileAberration = "Insectile Aberration";

    /*
    Moonmist
    {1}{G}
    Instant
    Transform all Humans. Prevent all combat damage that would be dealt this turn by creatures other than Werewolves and Wolves. <i>(Only double-faced cards can be transformed.)</i>
    */
    private static final String moonmist = "Moonmist";

    /*
    Maskwood Nexus
    {4}
    Artifact
    Creatures you control are every creature type. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
    {3}, {T}: Create a 2/2 blue Shapeshifter creature token with changeling.
    */
    private static final String maskwoodNexus = "Maskwood Nexus";

    /*
    Dress Down
    {1}{U}
    Enchantment
    Flash
    When Dress Down enters the battlefield, draw a card.
    Creatures lose all abilities.
    At the beginning of the end step, sacrifice Dress Down.
    */
    private static final String dressDown = "Dress Down";

    /*
    Baithook Angler
    {1}{U}
    Creature - Human Peasant
    Disturb {1}{U}
    2/1

    Hook-Haunt Drifter
    Creature - Spirit
    Flying
    If Hook-Haunt Drifter would be put into a graveyard from anywhere, exile it instead.
    1/2
    */
    private static final String baithookAngler = "Baithook Angler";
    private static final String hookHauntDrifter = "Hook-Haunt Drifter";

    /*
    Croaking Counterpart
    {1}{G}{U}
    Sorcery
    Create a token that's a copy of target non-Frog creature, except it's a 1/1 green Frog.
    Flashback {3}{G}{U}
    */
    private static final String croakingCounterpart = "Croaking Counterpart";

    @Test
    public void NissaVastwoodSeerTest() {

        addCard(Zone.LIBRARY, playerA, "Forest");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        // Whenever a land you control enters, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.

        addCard(Zone.HAND, playerA, nissaVastwoodSeer);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        // {G}{G}, Sacrifice Rootrunner: Put target land on top of its owner's library.
        addCard(Zone.BATTLEFIELD, playerB, "Rootrunner"); // {2}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nissaVastwoodSeer, true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{G}{G}", "Swamp");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "+1: Reveal");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Rootrunner", 1);

        assertPermanentCount(playerA, nissaVastwoodSeer, 0);
        assertPermanentCount(playerA, nissaSageAnimist, 1);

        assertCounterCount(nissaSageAnimist, CounterType.LOYALTY, 4);
        assertPermanentCount(playerA, "Forest", 6);
        assertPermanentCount(playerA, "Swamp", 1);
    }

    @Test
    public void LilianaHereticalHealer() {
        addCard(Zone.BATTLEFIELD, playerA, silvercoatLion, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, lilianaHereticalHealer);

        addCard(Zone.HAND, playerB, lightningBolt);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lilianaHereticalHealer);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, lightningBolt, silvercoatLion);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, silvercoatLion, 1);
        assertGraveyardCount(playerB, lightningBolt, 1);

        assertPermanentCount(playerA, lilianaHereticalHealer, 0);
        assertPermanentCount(playerA, lilianaDefiantNecromancer, 1);
        assertCounterCount(lilianaDefiantNecromancer, CounterType.LOYALTY, 3);

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
        addCard(Zone.BATTLEFIELD, playerA, silvercoatLion, 1);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, lilianaHereticalHealer);

        // All creatures get -4/-4 until end of turn.
        addCard(Zone.HAND, playerB, languish);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, languish);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, languish, 1);
        assertPermanentCount(playerA, lilianaDefiantNecromancer, 0);
        assertPermanentCount(playerA, "Zombie Token", 0);

        assertGraveyardCount(playerA, silvercoatLion, 1);
        assertGraveyardCount(playerA, lilianaHereticalHealer, 1);
    }

    @Test
    public void TestEnchantmentToCreature() {
        addCard(Zone.GRAVEYARD, playerA, silvercoatLion, 1);
        addCard(Zone.GRAVEYARD, playerA, lightningBolt, 1);
        addCard(Zone.GRAVEYARD, playerA, fireball, 1);
        addCard(Zone.GRAVEYARD, playerA, infernalScarring, 1);

        // {B}: Put the top card of your library into your graveyard.
        // <i>Delirium</i> &mdash At the beginning of your end step, if there are four or more card types among cards in your graveyard, transform Autumnal Gloom.
        addCard(Zone.BATTLEFIELD, playerA, autumnalGloom);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, autumnalGloom, 0);
        assertPermanentCount(playerA, ancientOfTheEquinox, 1);
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
        setStrictChooseMode(true);
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
        assertAbilityCount(playerA, "Timber Shredder", WerewolfFrontTriggeredAbility.class, 0); // no front face ability
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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

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
    public void testPersistentNightmareTrigger() {
        // Target opponent puts the top thirteen cards of their library into their graveyard.
        // {3}{U}{U}: Put Startled Awake from your graveyard onto the battlefield transformed. Activate this ability only any time you could cast a sorcery.
        addCard(Zone.HAND, playerA, "Startled Awake"); // SORCERY {2}{U}{U}"
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Startled Awake", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}{U}");

        attack(3, playerA, "Persistent Nightmare");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertGraveyardCount(playerB, 13);
        assertGraveyardCount(playerA, "Startled Awake", 0);
        assertPermanentCount(playerA, "Persistent Nightmare", 0); // Night-side card of Startled Awake
        assertHandCount(playerA, "Startled Awake", 1);
    }

    @Test
    public void testStartledAwakeMoonmist() {
        addCard(Zone.HAND, playerA, "Startled Awake");
        addCard(Zone.HAND, playerA, moonmist);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 11);
        addCard(Zone.BATTLEFIELD, playerA, maskwoodNexus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Startled Awake", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}{U}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, moonmist);

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
    public void testTransformCopyTransformed() {
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

        assertLife(playerB, 20 - 2 - 3);

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
        addCard(Zone.BATTLEFIELD, playerA, silvercoatLion);
        addCard(Zone.HAND, playerA, lightningBolt);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // Devoid
        // {2}{C}: Exile another target creature, then return it to the battlefield tapped under its owner's control.
        addCard(Zone.BATTLEFIELD, playerB, eldraziDisplacer, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, silvercoatLion);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{C}", "Archangel Avacyn");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, lightningBolt, 1);
        assertGraveyardCount(playerA, silvercoatLion, 1);

        assertPermanentCount(playerB, eldraziDisplacer, 1);
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
        addCard(Zone.HAND, playerA, huntmasterOfTheFells); // Creature {2}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // Devoid
        // {2}{C}: Exile another target creature, then return it to the battlefield tapped under its owner's control.
        addCard(Zone.HAND, playerB, eldraziDisplacer, 1); // Creature {2}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, huntmasterOfTheFells);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, eldraziDisplacer);

        activateAbility(4, PhaseStep.UPKEEP, playerB, "{2}{C}", huntmasterOfTheFells, "At the beginning of each upkeep");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
        assertPermanentCount(playerA, "Wolf Token", 2);

        assertPermanentCount(playerB, eldraziDisplacer, 1);

        assertPermanentCount(playerA, ravagerOfTheFells, 0);
        assertPermanentCount(playerA, huntmasterOfTheFells, 1);
        assertPowerToughness(playerA, huntmasterOfTheFells, 2, 2);
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
        addCard(Zone.HAND, playerA, huntmasterOfTheFells); // Creature {2}{R}{G}
        addCard(Zone.HAND, playerA, silvercoatLion, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, huntmasterOfTheFells, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, silvercoatLion, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, silvercoatLion);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Wolf Token", 2);
        assertPermanentCount(playerA, silvercoatLion, 2);
        assertPermanentCount(playerA, ravagerOfTheFells, 0);
        assertPermanentCount(playerA, huntmasterOfTheFells, 1);
        assertPowerToughness(playerA, huntmasterOfTheFells, 2, 2);
    }

    @Test
    public void testWildsongHowlerTrigger() {
        // The only Daybound/Nightbound card with a Transforms trigger on the back side
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, howlpackPiper, 2); // Creature {2}{R}{G}
        addCard(Zone.LIBRARY, playerA, silvercoatLion, 50);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, howlpackPiper);
        setChoice(playerA, true); //Transform trigger
        addTarget(playerA, silvercoatLion);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, howlpackPiper);
        setChoice(playerA, true); //ETB trigger
        addTarget(playerA, silvercoatLion);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);

        execute();

        assertPermanentCount(playerA, wildsongHowler, 2);
        assertPermanentCount(playerA, howlpackPiper, 0); // They should be both transformed
        assertHandCount(playerA, silvercoatLion, 3);
        assertHandCount(playerA, 3); //The two Silvercoat Lions from triggers and 1 from natural card draw
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
        addCard(Zone.HAND, playerA, thingInTheIce); // Creature {1}{U}
        // Creatures you control get +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, bannersRaised, 4); // Creature {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // You may have Phantasmal Image enter the battlefield as a copy of any creature
        // on the battlefield, except it's an Illusion in addition to its other types and
        // it has "When this creature becomes the target of a spell or ability, sacrifice it."
        addCard(Zone.HAND, playerB, phantasmalImage, 1); // Creature {1}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, thingInTheIce);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bannersRaised);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bannersRaised);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bannersRaised);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bannersRaised);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, phantasmalImage);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, bannersRaised, 4);
        assertPermanentCount(playerA, thingInTheIce, 0);
        assertPermanentCount(playerA, awokenHorror, 1);
        assertPowerToughness(playerA, awokenHorror, 7, 8);

        assertPermanentCount(playerB, awokenHorror, 1);
        assertPowerToughness(playerB, awokenHorror, 7, 8);

    }

    @Test
    public void testMoonmistDelver() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, delverOfSecrets);
        addCard(Zone.HAND, playerA, moonmist, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, delverOfSecrets);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, moonmist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, delverOfSecrets, 0);
        assertPermanentCount(playerA, insectileAberration, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, moonmist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, delverOfSecrets, 1);
        assertPermanentCount(playerA, insectileAberration, 0);
    }

    @Test
    public void testMoonmistHuntmasterDressdown() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, huntmasterOfTheFells); //Has on-transform triggers
        addCard(Zone.BATTLEFIELD, playerA, maskwoodNexus); //Make back side human


        addCard(Zone.HAND, playerA, dressDown); //Creatures lose all abilities
        addCard(Zone.HAND, playerA, moonmist, 2);

        castSpell(1, PhaseStep.UPKEEP, playerA, dressDown);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, moonmist);
        checkPermanentCount("Huntmaster flipped", 1, PhaseStep.BEGIN_COMBAT, playerA, ravagerOfTheFells, 1);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, moonmist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, dressDown, 1);
        assertPermanentCount(playerA, huntmasterOfTheFells, 1);
        assertPermanentCount(playerA, 6+1+1);
    }

    @Test
    public void testTokenCopyTransformed() {
        addCard(Zone.GRAVEYARD, playerA, baithookAngler);
        addCard(Zone.BATTLEFIELD, playerA, "Breeding Pool", 5);
        addCard(Zone.HAND, playerA, croakingCounterpart);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter using Disturb");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, croakingCounterpart, hookHauntDrifter);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        for (Permanent token : currentGame.getBattlefield().getActivePermanents(playerA.getId(), currentGame)) {
            if (!(token instanceof PermanentToken)) {
                continue;
            }
            assertTrue(token.getSubtype(currentGame).contains(SubType.FROG));
            assertEquals(ObjectColor.GREEN, token.getColor(currentGame));
        }
    }

    @Test
    public void testFrontSaga() {
        addCard(Zone.BATTLEFIELD, playerA, azusasManyJourneys);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, azusasManyJourneys, 0);
        assertPermanentCount(playerA, likenessOfTheSeeker, 1);
        assertPowerToughness(playerA, likenessOfTheSeeker, 3, 3);
        assertAbilityCount(playerA, likenessOfTheSeeker, SagaAbility.class, 0); // does not have saga ability
        assertLife(playerA, 20 + 3);
    }
}
