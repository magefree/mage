
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Checks if change zone replacement effects work as intended
 *
 * @author LevelX2
 */
public class ZoneChangeReplacementTest extends CardTestPlayerBase {

    // If Darksteel Colossus would be put into a graveyard from anywhere,
    // reveal Darksteel Colossus and shuffle it into its owner's library instead.
    @Test
    public void testFromLibraryZoneChange() {
        addCard(Zone.LIBRARY, playerA, "Darksteel Colossus");
        // Tome Scour - Sorcery - {U}
        // Target player puts the top five cards of their library into their graveyard.
        addCard(Zone.HAND, playerA, "Tome Scour");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tome Scour", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Darksteel Colossus", 0);
        assertGraveyardCount(playerA, 5); // 4 + Tome Scour
    }

    @Test
    public void testFromHandZoneChange() {
        addCard(Zone.HAND, playerA, "Progenitus");
        // Distress - Sorcery - {B}{B}
        // Target player reveals their hand. You choose a nonland card from it. That player discards that card.
        addCard(Zone.HAND, playerA, "Distress");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Distress", playerA);
        setChoice(playerA, "Progenitus");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Progenitus", 0);
        assertGraveyardCount(playerA, 1); // Distress

        assertHandCount(playerA, "Progenitus", 0);
    }

    @Test
    public void checkBridgeDoesWork() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // Diabolic Edict - Instant - {1}{B}
        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Diabolic Edict");
        // Whenever a nontoken creature is put into your graveyard from the battlefield, if Bridge from
        // Below is in your graveyard, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Bridge from Below");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diabolic Edict", playerA);
        // setChoice(playerA, "Silvercoat Lion"); // Only creature they could sacrifice, its auto-chosen

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, 3); // Diabolic Edict + Bridge from Below + Silvercoat Lion
        assertPermanentCount(playerA, "Zombie Token", 1); // Silvercoat Lion goes to graveyard so a Zombie tokes is created
    }

    @Test
    public void testDoesntTriggerDiesTriggeredAbilities() {
        // If Progenitus would be put into a graveyard from anywhere,
        // reveal Progenitus and shuffle it into its owner’s library instead.
        addCard(Zone.BATTLEFIELD, playerA, "Progenitus");
        // Diabolic Edict - Instant - {1}{B}
        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Diabolic Edict");
        // Whenever a nontoken creature is put into your graveyard from the battlefield, if Bridge from
        // Below is in your graveyard, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Bridge from Below");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diabolic Edict", playerA);
        // setChoice(playerA, "Progenitus"); // Only creature they could sacrifice, its auto-chosen

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Progenitus", 0);
        assertGraveyardCount(playerA, 2); // Diabolic Edict + Bridge from Below
        assertPermanentCount(playerA, "Zombie Token", 0); // Progenitus never touches graveyard - so no Zombie tokes is created
        assertLibraryCount(playerA, "Progenitus", 1);
    }

    // Have Progenitus and Humility on the battlefield. Destroy Progenitus. Progenitus should go to the graveyard
    // since it doesn't have any replacement effect. Currently, it gets shuffled into the library.
    @Test
    public void testHumilityDeactivatesReplacementEffectAbilities() {
        // Protection from everything
        // If Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.
        addCard(Zone.BATTLEFIELD, playerA, "Progenitus");
        // Enchantment  {2}{W}{W}
        // All creatures lose all abilities and are 1/1.
        addCard(Zone.BATTLEFIELD, playerA, "Humility");
        // Diabolic Edict - Instant - {1}{B}
        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Diabolic Edict");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diabolic Edict", playerA);
        // setChoice(playerA, "Progenitus"); // Only creature they could sacrifice, its auto-chosen

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Progenitus", 0);
        assertGraveyardCount(playerA, "Progenitus", 1);
        assertGraveyardCount(playerA, 2); // Diabolic Edict + Progenitus
    }

    @Test
    public void testHumilityAndKumano() {
        // Enchantment  {2}{W}{W}
        // All creatures lose all abilities and are 1/1.
        addCard(Zone.BATTLEFIELD, playerA, "Humility");

        // 3/3
        // If a creature dealt damage by Kumano's Pupils this turn would die, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Kumano's Pupils");

        // Instant {1}{G}
        // Target creature gets +1/+1 until end of turn.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Aggressive Urge");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Aggressive Urge", "Kumano's Pupils");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Kumano's Pupils", "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Aggressive Urge", 1);
        assertPermanentCount(playerA, "Kumano's Pupils", 1);
        assertPowerToughness(playerA, "Kumano's Pupils", 2, 2);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

        assertExileCount("Silvercoat Lion", 0);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    // A creature gets damage from Kumano's Pupils and is destroyed after.
    // The creature has to go to exile.
    @Test
    public void testCreatureGetsExiledByKumano() {
        // 3/3
        // If a creature dealt damage by Kumano's Pupils this turn would die, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Kumano's Pupils");
        // 2/4
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        // Terminate - Instant {R}{B}
        // Destroy target creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terminate");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        attack(2, playerB, "Pillarfield Ox");
        block(2, playerA, "Kumano's Pupils", "Pillarfield Ox");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Terminate", "Pillarfield Ox");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Pillarfield Ox", 0);
        assertGraveyardCount(playerA, "Terminate", 1);

        assertExileCount("Pillarfield Ox", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 0);
    }

    // A creature gets damage from Kumano's Pupils and returns to hand after.
    // Then it's cast again. This new permanent instance is destroyed. It may not
    // got to exile because only previous instance was damgaged by Kumano's Pupils.
    @Test
    public void testPermanentNewInstanceAndKumano() {
        // 3/3
        // If a creature dealt damage by Kumano's Pupils this turn would die, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Kumano's Pupils");
        // 2/4
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        // Unsummon - Instant {U}
        // Return target creature to its owner's hand.
        addCard(Zone.HAND, playerA, "Unsummon");
        // Terminate - Instant {R}{B}
        // Destroy target creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terminate");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);

        attack(2, playerB, "Pillarfield Ox");
        block(2, playerA, "Kumano's Pupils", "Pillarfield Ox");

        castSpell(2, PhaseStep.COMBAT_DAMAGE, playerA, "Unsummon", "Pillarfield Ox");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Pillarfield Ox");
        waitStackResolved(2, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Terminate", "Pillarfield Ox");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Pillarfield Ox", 0);
        assertPermanentCount(playerA, "Kumano's Pupils", 1);
        assertPowerToughness(playerA, "Kumano's Pupils", 3, 3);
        assertGraveyardCount(playerA, "Unsummon", 1);
        assertGraveyardCount(playerA, "Terminate", 1);

        assertExileCount("Pillarfield Ox", 0);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
    }

    /**
     * Test that a countered spell of a card that goes always to library back
     * instead of into the graveyard.
     */
    @Test
    public void testCounterAndMoveToLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // Legacy Weapon - Artifact {7}
        // {W}{U}{B}{R}{G}: Exile target permanent.
        // If Legacy Weapon would be put into a graveyard from anywhere, reveal Legacy Weapon and shuffle it into its owner's library instead.
        addCard(Zone.HAND, playerA, "Legacy Weapon");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Counter target spell. At the beginning of your next main phase, add {X}, where X is that spell's converted mana cost.
        addCard(Zone.HAND, playerB, "Mana Drain");
        addCard(Zone.HAND, playerB, "Legacy Weapon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Legacy Weapon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mana Drain", "Legacy Weapon");

        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN); // Let the Mana Drain delayed triggered ability resolve
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Legacy Weapon");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, "Legacy Weapon", 0);
        assertPermanentCount(playerA, "Legacy Weapon", 0);
        assertGraveyardCount(playerA, "Legacy Weapon", 0);

        assertGraveyardCount(playerB, "Mana Drain", 1);
        assertPermanentCount(playerB, "Legacy Weapon", 1);
    }

    /**
     * Test that a returned creature of Whip of Erebos got exiled if it is
     * destroyed by a spell
     */
    @Test
    public void testWhipOfErebos() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terror");

        // {2}{B}{B}, {T}: Return target creature card from your graveyard to the battlefield.
        // It gains haste. Exile it at the beginning of the next end step.
        // If it would leave the battlefield, exile it instead of putting it anywhere else.
        // Activate this ability only any time you could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerB, "Whip of Erebos");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{B}{B}, {T}: Return target creature", "Silvercoat Lion");

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Terror", "Silvercoat Lion");
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Terror", 1);
        assertExileCount("Silvercoat Lion", 1);
    }

    /**
     * Jace, Vryn's Prodigy – Jace, Telepath Unbound
     *
     * You can't whip him back with Whip of Erebos , flip him and then keep him.
     * I think he is considered a new object after being exiled by his own
     * trigger, so whip shouldn't affect him anymore.
     */
    @Test
    public void testWhipOfErebosTransformPlaneswalker() {
        // {2}{B}{B}, {T}: Return target creature card from your graveyard to the battlefield.
        // It gains haste. Exile it at the beginning of the next end step.
        // If it would leave the battlefield, exile it instead of putting it anywhere else.
        // Activate this ability only any time you could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerB, "Whip of Erebos");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.GRAVEYARD, playerB, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerB, "Jace, Vryn's Prodigy");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{B}{B}, {T}: Return target creature", "Jace, Vryn's Prodigy");

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard, exile Jace, Vryn's Prodigy, then return him to the battefield transformed under his owner's control.
        activateAbility(2, PhaseStep.BEGIN_COMBAT, playerB, "{T}:");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "+1: Up to one target creature gets -2/-0 until your next turn");
        setStopAt(3, PhaseStep.UNTAP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Jace, Vryn's Prodigy", 0);
        assertPermanentCount(playerB, "Jace, Telepath Unbound", 1);
        assertCounterCount("Jace, Telepath Unbound", CounterType.LOYALTY, 6);
    }

    /**
     * I was using Anafenza, the Foremost as Commander. She attacked and traded
     * with two creatures. I moved Anafenza to the Command Zone, but the
     * opponent's creatures "when {this} dies" abilities triggered. Since
     * Anafenza and those creatures all received lethal damage at the same time,
     * the creatures should have been exiled due to Anafenza's replacement
     * effect, but I guess since the logic asks if you want to use the Command
     * Zone replacement effect first, that it doesn't see her leaving the
     * battlefield at the same time as the other creatures.
     *
     * http://blogs.magicjudges.org/rulestips/2015/05/anafenza-vs-deathmist-raptor/
     */
    @Test
    public void testAnafenzaExileInCombat() {
        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Anafenza, the Foremost"); // 4/4

        // Reach (This creature can block creatures with flying.)
        addCard(Zone.BATTLEFIELD, playerB, "Skyraker Giant"); // 4/3

        attack(2, playerB, "Skyraker Giant");
        block(2, playerA, "Anafenza, the Foremost", "Skyraker Giant");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertExileCount("Skyraker Giant", 1);
        assertPermanentCount(playerA, "Anafenza, the Foremost", 0);
        assertGraveyardCount(playerA, "Anafenza, the Foremost", 1);
    }
}
