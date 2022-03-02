package org.mage.test.commander.duel;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author NinthWorld
 */
public class CommanderMutateTest extends CardTestCommanderDuelBase {
    // Lands
    private static final String ISLAND = "Island";
    private static final String SWAMP = "Swamp";
    private static final String MOUNTAIN = "Mountain";
    private static final String FOREST = "Forest";
    private static final String PLAINS = "Plains";

    // Creatures
    private static final String OTRIMI_THE_EVER_PLAYFUL = "Otrimi, the Ever-Playful"; // Mutate {3}{B}{G}{U} - 6/6 - Trample - (some other stuff...)
    private static final String SNAPDAX_APEX_OF_THE_HUNT = "Snapdax, Apex of the Hunt"; // Mutate {2}{B/R}{W}{W} - 3/5 - Double strike - (other stuff...)
    private static final String BEASTCALLER_SAVANT = "Beastcaller Savant"; // {1}{G} - 1/1 - Haste - {T}: Add mana

    // Spells
    private static final String MURDER = "Murder"; // {1}{B}{B} - Destroy target creature
    private static final String ADVENT_OF_THE_WURM = "Advent of the Wurm"; // {1}{G}{G}{W} - Create 5/5 green Wurm token with trample
    private static final String REGRESS = "Regress"; // {2}{U} - Return target permanent to its owner's hand
    private static final String LEAD_ASTRAY = "Lead Astray"; // {1}{W} - Tap up to 2 target creatures
    private static final String LEGIONS_LANDING = "Legion's Landing";
    private static final String LEADERSHIP_VACUUM = "Leadership Vacuum"; // {2}{U} - Target player returns each commander they control from the battlefield to the command zone.

    // Enchantment
    private static final String OBLIVION_RING = "Oblivion Ring"; // {2}{W} - When ~ enters the battlefield, exile target permanent. When ~ leaves the battlefield, return exiled card to battlefield.

    // Tokens
    private static final String WURM = "Wurm Token"; // 5/5 green Wurm token with trample
    private static final String VAMPIRE = "Vampire Token";

    // Ability
    private static final String USING_MUTATE = " using mutate";

    /**
     * Mutate commander spell can't resolve test
     */
    @Test
    public void testMutateCommanderCardKilledBeforeResolve() {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, MURDER);

        addCard(Zone.COMMAND, playerA, OTRIMI_THE_EVER_PLAYFUL);
        addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OTRIMI_THE_EVER_PLAYFUL + USING_MUTATE, BEASTCALLER_SAVANT);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, MURDER, BEASTCALLER_SAVANT);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertPermanentCount(playerA, BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 1);
        assertGraveyardCount(playerA, BEASTCALLER_SAVANT, 1);
    }

    /**
     * Basic mutate commander tests - Removes from command zone, P/T of OVER, Shared Abilities, Keeps Counters, Stays Tapped
     */
    public void setupTestMutateCommanderBasic(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.COMMAND, playerA, SNAPDAX_APEX_OF_THE_HUNT);
        addCard(Zone.HAND, playerA, LEAD_ASTRAY);
        if (withToken) {
            addCard(Zone.HAND, playerA, LEGIONS_LANDING);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, LEGIONS_LANDING);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }
        addCounters(1, PhaseStep.BEGIN_COMBAT, playerA, withToken ? VAMPIRE : BEASTCALLER_SAVANT, CounterType.TIME, 1);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, LEAD_ASTRAY, withToken ? VAMPIRE : BEASTCALLER_SAVANT);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, SNAPDAX_APEX_OF_THE_HUNT + USING_MUTATE, withToken ? VAMPIRE : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, SNAPDAX_APEX_OF_THE_HUNT, 0);
        assertPermanentCount(playerA, withToken ? VAMPIRE : BEASTCALLER_SAVANT, mutateUnder ? 1 : 0);
        assertPermanentCount(playerA, SNAPDAX_APEX_OF_THE_HUNT, mutateUnder ? 0 : 1);
        String creature = mutateUnder ? withToken ? VAMPIRE : BEASTCALLER_SAVANT : SNAPDAX_APEX_OF_THE_HUNT;
        assertAbility(playerA, creature, withToken ? LifelinkAbility.getInstance() : HasteAbility.getInstance(), true);
        assertAbility(playerA, creature, DoubleStrikeAbility.getInstance(), true);
        assertPowerToughness(playerA, creature, mutateUnder ? withToken ? 1 : 1 : 3, mutateUnder ? withToken ? 1 : 1 : 5);
        assertCounterCount(playerA, creature, CounterType.TIME, 1);
        assertTapped(creature, true);
    }
    @Test
    public void testMutateCommanderUnderCardBasic() {
        setupTestMutateCommanderBasic(false, true);
    }
    @Test
    public void testMutateCommanderOverCardBasic() {
        setupTestMutateCommanderBasic(false, false);
    }
    @Test
    public void testMutateCommanderUnderTokenBasic() {
        setupTestMutateCommanderBasic(true, true);
    }
    @Test
    public void testMutateCommanderOverTokenBasic() {
        setupTestMutateCommanderBasic(true, false);
    }

    /**
     * Change zones tests - Commander killed
     */
    public void setupTestMutateCommanderKilled(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);
        setupLands(playerB);

        addCard(Zone.HAND, playerB, MURDER);

        addCard(Zone.COMMAND, playerA, OTRIMI_THE_EVER_PLAYFUL);
        if (withToken) {
            addCard(Zone.HAND, playerA, LEGIONS_LANDING);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, LEGIONS_LANDING);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OTRIMI_THE_EVER_PLAYFUL + USING_MUTATE, withToken ? VAMPIRE : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, MURDER);
        addTarget(playerB, mutateUnder ? withToken ? VAMPIRE : BEASTCALLER_SAVANT : OTRIMI_THE_EVER_PLAYFUL);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, withToken ? VAMPIRE : BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertGraveyardCount(playerA, withToken ? VAMPIRE : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertGraveyardCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertCommandZoneCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 1);
    }
    @Test
    public void testMutateCommanderUnderCardKilled() {
        setupTestMutateCommanderKilled(false, true);
    }
    @Test
    public void testMutateCommanderOverCardKilled() {
        setupTestMutateCommanderKilled(false, false);
    }
    @Test
    public void testMutateCommanderUnderTokenKilled() {
        setupTestMutateCommanderKilled(true, true);
    }
    @Test
    public void testMutateCommanderOverTokenKilled() {
        setupTestMutateCommanderKilled(true, false);
    }

    /**
     * Change zones tests - Commander exiled and back
     */
    public void setupTestMutateCommanderToExileAndBack(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.HAND, playerA, OBLIVION_RING);
        addCard(Zone.HAND, playerA, REGRESS);
        addCard(Zone.COMMAND, playerA, OTRIMI_THE_EVER_PLAYFUL);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OTRIMI_THE_EVER_PLAYFUL + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, OBLIVION_RING);
        addTarget(playerA, mutateUnder ? withToken ? WURM : BEASTCALLER_SAVANT : OTRIMI_THE_EVER_PLAYFUL);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        assertPermanentCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertExileCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertExileCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertCommandZoneCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, REGRESS);
        addTarget(playerA, OBLIVION_RING);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, withToken ? 0 : 1);
        assertPermanentCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertExileCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
    }
    @Test
    public void testMutateCommanderUnderCardToExileAndBack() {
        setupTestMutateCommanderToExileAndBack(false, true);
    }
    @Test
    public void testMutateCommanderOverCardToExileAndBack() {
        setupTestMutateCommanderToExileAndBack(false, false);
    }
    @Test
    public void testMutateCommanderUnderTokenToExileAndBack() {
        setupTestMutateCommanderToExileAndBack(true, true);
    }
    @Test
    public void testMutateCommanderOverTokenToExileAndBack() {
        setupTestMutateCommanderToExileAndBack(true, false);
    }

    /**
     * Q: What happens if a mutated pile containing a commander gets hit with Leadership Vacuum?
     *
     * A: The whole pile goes to the command zone. However, since you can only cast your commander from the command zone, the rest of the cards will be stuck there forever.
     */
    public void setupTestMutateCommanderLeadershipVacuum(boolean withToken, boolean mutateUnder) {
        setupLands(playerA);

        addCard(Zone.COMMAND, playerA, OTRIMI_THE_EVER_PLAYFUL);
        addCard(Zone.HAND, playerA, LEADERSHIP_VACUUM);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OTRIMI_THE_EVER_PLAYFUL + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, mutateUnder);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, LEADERSHIP_VACUUM, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertGraveyardCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 0);
        assertCommandZoneCount(playerA, OTRIMI_THE_EVER_PLAYFUL, 1);

        assertPermanentCount(playerA, withToken ? WURM : BEASTCALLER_SAVANT, 0);
        if (!withToken) {
            assertGraveyardCount(playerA, BEASTCALLER_SAVANT, 0);
            assertCommandZoneCount(playerA, BEASTCALLER_SAVANT, 1);
        }
    }
    @Test
    public void testMutateCommanderUnderCardLeadershipVacuum() {
        setupTestMutateCommanderLeadershipVacuum(false, true);
    }
    @Test
    public void testMutateCommanderOverCardLeadershipVacuum() {
        setupTestMutateCommanderLeadershipVacuum(false, false);
    }
    @Test
    public void testMutateCommanderUnderTokenLeadershipVacuum() {
        setupTestMutateCommanderLeadershipVacuum(true, true);
    }
    @Test
    public void testMutateCommanderOverTokenLeadershipVacuum() {
        setupTestMutateCommanderLeadershipVacuum(true, false);
    }

    /**
     * Q: How does mutate work with commander damage? Does it only deal commander damage if it is the top card or even when it is one of the lower cards?
     * A: If any card in the merged permanent is a commander, the whole thing is your commander.
     */
    public void setupTestMutateCommanderDamage(boolean withToken) {
        setupLands(playerA);

        setLife(playerB, 100);
        addCard(Zone.COMMAND, playerA, OTRIMI_THE_EVER_PLAYFUL);
        if (withToken) {
            addCard(Zone.HAND, playerA, ADVENT_OF_THE_WURM);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ADVENT_OF_THE_WURM);
        } else {
            addCard(Zone.BATTLEFIELD, playerA, BEASTCALLER_SAVANT);
        }
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, withToken ? WURM : BEASTCALLER_SAVANT, CounterType.P1P1, 21);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OTRIMI_THE_EVER_PLAYFUL + USING_MUTATE, withToken ? WURM : BEASTCALLER_SAVANT);
        setChoice(playerA, true);

        attack(3, playerA, withToken ? WURM : BEASTCALLER_SAVANT, playerB);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLostTheGame(playerB);
    }
    @Test
    public void testMutateCommanderUnderCardCommanderDamage() {
        setupTestMutateCommanderDamage(false);
    }
    @Test
    public void testMutateCommanderUnderTokenCommanderDamage() {
        setupTestMutateCommanderDamage(true);
    }

    private void setupLands(TestPlayer player) {
        addCard(Zone.BATTLEFIELD, player, ISLAND, 20);
        addCard(Zone.BATTLEFIELD, player, PLAINS, 20);
        addCard(Zone.BATTLEFIELD, player, FOREST, 20);
        addCard(Zone.BATTLEFIELD, player, MOUNTAIN, 20);
        addCard(Zone.BATTLEFIELD, player, SWAMP, 20);
    }
}