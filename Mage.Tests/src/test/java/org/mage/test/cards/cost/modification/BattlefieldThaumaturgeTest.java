package org.mage.test.cards.cost.modification;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Battlefield Thaumaturge:
 *   Creature - Human Wizard
 *   Each instant and sorcery spell you cast costs {1} less to cast for each creature it targets.
 *   Heroic - Whenever you cast a spell that targets Battlefield Thaumaturge, Battlefield Thaumaturge gains hexproof until end of turn.
 *
 * @author Simown
 */
public class BattlefieldThaumaturgeTest extends CardTestPlayerBase {

    @Test
    public void testSingleTargetReduction() {

        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Thaumaturge");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Lightning Strike");

        addCard(Zone.BATTLEFIELD, playerB, "Akroan Skyguard");

        // Lightning Strike - {1}{R} -  Lightning Strike deals 3 damage to any target.
        // Because Battlefield Thaumaturge is on the battlefield, and the creature is targeted by the
        // Lightning Strike it will be payable with {R}.
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Strike", "Akroan Skyguard");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // PlayerA still has the Battlefield Thaumaturge
        assertPermanentCount(playerA, "Battlefield Thaumaturge", 1);
        // The Akroan Skyguard has been killed by the Lightning Strike
        assertPermanentCount(playerB, "Akroan Skyguard", 0);
        assertGraveyardCount(playerB, "Akroan Skyguard", 1);

        // Check {R} has been used to pay, and the other land remains untapped
        assertTappedCount("Mountain", true, 1);
        assertTappedCount("Island", false, 1);
    }

    @Test
    public void testStriveTargetingReduction1() {

        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Thaumaturge");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Pharika's Chosen");
        addCard(Zone.HAND, playerA, "Silence the Believers");

        addCard(Zone.BATTLEFIELD, playerB, "Battlewise Hoplite");
        /**
         * Silence the Believers - {2}{B}{B}
         * Exile any number of target creatures and all Auras attached to them
         * Strive - Silence the Believers costs {2}{B} more to cast for each target beyond the first.
         * Targetting a creature on both sides of the battlefield.
         */
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Silence the Believers", "Pharika's Chosen^Battlewise Hoplite");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // Both creatures were exiled
        assertExileCount("Pharika's Chosen", 1);
        assertExileCount("Battlewise Hoplite", 1);
        // Not still on the battlefield or in the graveyard
        assertPermanentCount(playerA, "Pharika's Chosen", 0);
        assertPermanentCount(playerB, "Battlewise Hoplite", 0);
        assertGraveyardCount(playerA, "Pharika's Chosen", 0);
        assertGraveyardCount(playerB, "Battlewise Hoplite", 0);

        /* Cost to exile 2 permanents will be:
         * + {2}{B}{B} for the base spell
         * + {2}{B}    for an additional target
         * - {2}       for Battlefield Thaumaturge cost reducing effect
         * = {2}{B}{B}{B} to pay.
         */
        // Check 3 Swamps have been tapped to pay the reduced cost
        assertTappedCount("Swamp", true, 3);
        // And 2 Forests for the colorless mana
        assertTappedCount("Forest", true, 2);
        // And the rest of the Forests remain untapped
        assertTappedCount("Forest", false, 2);
    }

    @Test
    public void testStriveTargetingReduction2() {

        String [] creatures = {"Battlefield Thaumaturge", "Agent of Horizons", "Blood-Toll Harpy", "Anvilwrought Raptor", "Fleshmad Steed" };
        for(String creature : creatures) {
            addCard(Zone.BATTLEFIELD, playerA, creature);
        }
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Launch the Fleet");

        // Launch the Fleet - {W}
        // Strive - Launch the Fleet costs {1} more to cast for each target beyond the first.
        // Until end of turn, any number of target creatures each gain "Whenever this creature attacks, put a 1/1 white Soldier token onto the battlefield tapped and attacking."
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Launch the Fleet", createTargetingString(creatures));
        for(String creature : creatures) {
            attack(3, playerA, creature);
        }
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // 5 initial creatures + 5 soldier tokens + 6 lands
        assertPermanentCount(playerA, 16);
        // The initial creatures exist
        for(String creature : creatures) {
            assertPermanentCount(playerA, creature, 1);
        }
        // Each has a solider token generated while attacking
        assertPermanentCount(playerA, "Soldier Token", 5);
        // Battlefield Thaumaturge will have hexproof from heroic trigger
        Permanent battlefieldThaumaturge = getPermanent("Battlefield Thaumaturge", playerA.getId());
        Assert.assertTrue("Battlefield Thaumaturge must have hexproof", battlefieldThaumaturge.getAbilities().contains(HexproofAbility.getInstance()));

        assertLife(playerA, 20);
        // 5 initial creatures + 5 soldier tokens => 16 damage
        assertLife(playerB, 4);
        /* Cost to have 5 attackers generate soldier tokens
         * + {W} for the base spell
         * + {4}    for an additional targets
         * - {4}    for Battlefield Thaumaturge cost reducing effect (reduce {1} per target)
         * = {W} to pay.
         */
        assertTappedCount("Plains", true, 1);
        // No other mana has been tapped to pay costs
        assertTappedCount("Island", false, 5);
    }

    @Test
    public void testVariableCostReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Thaumaturge");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Curse of the Swine");

        String [] opponentsCreatures = {"Fleecemane Lion", "Sedge Scorpion", "Boon Satyr", "Bronze Sable"};
        for(String creature: opponentsCreatures) {
            addCard(Zone.BATTLEFIELD, playerB, creature);
        }

        /* Curse of the Swine - {X}{U}{U}
         * Exile X target creatures. For each creature exiled this way,
         * its controller puts a 2/2 green Boar creature token onto the battlefield
         */
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of the Swine");
        setChoice(playerA, "X=4");
        addTarget(playerA, createTargetingString(opponentsCreatures));
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // All the opponents creatures have been exiled from the battlefield
        for(String creature: opponentsCreatures) {
            assertPermanentCount(playerB, creature, 0);
            assertGraveyardCount(playerB, creature, 0);
            assertExileCount(creature, 1);
        }

        // All 4 creatures have been replaced by boars
        assertPermanentCount(playerB, "Boar Token", 4);

        /* Cost to target 4 permanents will be:
         * + {4}{U}{U} for the base spell with X = 4
         * - {4}       for Battlefield Thaumaturge cost reducing effect as 4 creatures are targetted
         * = {U}{U} to pay.
         */
        // Check 2 islands have been tapped to pay the reduced cost
        assertTappedCount("Island", true, 2);
        // And the rest of the lands remain untapped
        assertTappedCount("Plains", false, 4);
    }

    @Test
    public void testMutipleTargetReduction() {

        String [] playerACreatures = {"Battlefield Thaumaturge", "Sedge Scorpion", "Boon Satyr"};
        String [] playerBCreatures = {"Agent of Horizons", "Blood-Toll Harpy", "Anvilwrought Raptor"};

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // Creatures for player A
        for(String creature: playerACreatures) {
            addCard(Zone.BATTLEFIELD, playerA, creature);
        }
        addCard(Zone.HAND, playerA, "Descent of the Dragons");
        // Creatures for player B
        for(String creature: playerBCreatures) {
            addCard(Zone.BATTLEFIELD, playerB, creature);
        }
        /* Descent of the Dragons - {4}{R}{R}
         *  Destroy any number of target creatures.
         *  For each creature destroyed this way, its controller puts a 4/4 red Dragon creature token with flying onto the battlefield.
         *  Battlefield Thaumaturge should reduce the cost of the spell when cast, before he is destroyed and replaced with a dragon.
         */
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Descent of the Dragons", createTargetingString(playerACreatures) + '^' + createTargetingString(playerBCreatures));
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // All creatures have been put in the graveyard
        for(String creature: playerACreatures) {
            assertPermanentCount(playerA, creature, 0);
            assertGraveyardCount(playerA, creature, 1);
        }
        for(String creature: playerBCreatures) {
            assertPermanentCount(playerB, creature, 0);
            assertGraveyardCount(playerB, creature, 1);
        }

        // And each player has 3 dragons
        assertPermanentCount(playerA, "Dragon Token", 3);
        assertPermanentCount(playerB, "Dragon Token", 3);

        /* Cost to target 6 creatures will be
         * + {4}{R}{R} for the fixed cost base spell
         * - {4}       for Battlefield Thaumaturge cost reducing effect
         *             each creature targeted will reduce the cost by {1} so the cost
         *             can only be reduced by {4} maximum using Battlefield Thaumaturge
         *             even though 6 creatures are targeted.
         * = {R}{R} to pay.
         */
        // Check 2 mountains have been tapped to pay the reduced cost
        assertTappedCount("Mountain", true, 2);
        // And the rest of the lands remain untapped
        assertTappedCount("Swamp", false, 4);
    }

    @Test
    public void testTargetNonCreature() {

        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Thaumaturge");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Fade into Antiquity");

        addCard(Zone.BATTLEFIELD, playerB, "Heliod, God of the Sun");

        // Fade into Antiquity - Sorcery - {2}{G} -  Exile target artifact or enchantment.
        // Battlefield Thaumaturge only reduces the cost instants and sorceries where the target is a creature
        // No cost reduction for targeting an enchantment (devotion is too low for Heliod to be a creature)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fade into Antiquity", "Heliod, God of the Sun");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // PlayerA still has the Battlefield Thaumaturge
        assertPermanentCount(playerA, "Battlefield Thaumaturge", 1);
        // Heliod has been exiled
        assertPermanentCount(playerB, "Heliod, God of the Sun", 0);
        assertGraveyardCount(playerB, "Heliod, God of the Sun", 0);
        assertExileCount("Heliod, God of the Sun", 1);

        // Expect full amount paid, i.e. all lands are tapped. Cost was not reduced by Battlefield Thaumaturge, no creature was targeted.
        assertTappedCount("Forest", true, 3);
    }

    @Test
    public void testTargetWithAura() {

        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Thaumaturge");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Spectra Ward");

        // Spectra Ward - {3}{W}{W} - Aura
        // Enchanted creature gets +2/+2 and has protection from all colors. This effect doesn't remove auras.
        // Battlefield Thaumaturge only reduces the cost instants and sorceries targeting creatures.
        // No cost reduction for targeting a creature with an Aura.
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Spectra Ward", "Battlefield Thaumaturge");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Battlefield Thaumaturge", 1);
        assertPermanentCount(playerA, "Spectra Ward", 1);
        // Battlefield Thaumaturge will have hexproof from heroic trigger
        Permanent battlefieldThaumaturge = getPermanent("Battlefield Thaumaturge", playerA.getId());
        Assert.assertTrue("Battlefield Thaumaturge must have hexproof", battlefieldThaumaturge.getAbilities().contains(HexproofAbility.getInstance()));

        // No cost reduction from Battlefield Thaumaturge, full amount paid
        assertTappedCount("Plains", true, 5);
    }

    private String createTargetingString(String [] targets) {
        StringBuilder targetBuilder = new StringBuilder();
        for (String target : targets) {
            if (targetBuilder.length() > 0) {
                targetBuilder.append('^');
            }
            targetBuilder.append(target);
        }
        return targetBuilder.toString();
    }

}
