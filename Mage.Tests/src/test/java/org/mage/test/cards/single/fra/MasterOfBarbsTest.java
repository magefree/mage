package org.mage.test.cards.single.fra;

import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.m.MasterOfBarbs;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

public class MasterOfBarbsTest extends CardTestMultiPlayerBase {

    /**
     * {@link mage.cards.m.MasterOfBarbs Master of Barbs} {1}{R}
     * Creature — Lizard Bard
     * Menace
     * Whenever one or more opponents are dealt noncombat damage, creatures you control get +1/+0 until end of turn.
     * 2/1
     */
    private static final String MASTER = "Master of Barbs";

    // Creature — Cat 2/2
    private static final String LION = "Silvercoat Lion";

    /**
     * {@link mage.cards.f.FlameRift Flame Rift} {1}{R}
     * Sorcery
     * Flame Rift deals 4 damage to each player.
     */
    private static final String FLAME_RIFT = "Flame Rift";

    /**
     * {@link mage.cards.s.Shock Shock} {R}
     * Instant
     * Shock deals 2 damage to any target.
     */
    private static final String SHOCK = "Shock";

    /**
     * {@link mage.cards.p.ProdigalPyromancer Prodigal Pyromancer} {2}{R}
     * Creature — Human Wizard
     * {T}: This creature deals 1 damage to any target.
     * 1/1
     */
    private static final String PYROMANCER = "Prodigal Pyromancer";

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL,
                MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    private void setupBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, MASTER);
        addCard(Zone.BATTLEFIELD, playerA, LION);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
    }

    private void executeUntilPostcombatMain() {
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testGeneratedRulesText() {
        // The generated trigger must include "one or more opponents", matching the printed rules.
        Card card = new MasterOfBarbs(playerA.getId(), new CardSetInfo(MASTER, "FRA", "88", Rarity.RARE));
        assertTrue(card.getRules().contains("Whenever one or more opponents are dealt noncombat damage, "
                + "creatures you control get +1/+0 until end of turn."));
    }

    @Test
    public void testSimultaneousDamageToMultipleOpponentsTriggersOnce() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, FLAME_RIFT);

        // Flame Rift damages all three opponents simultaneously: one trigger gives +1/+0, not +3/+0.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLAME_RIFT);
        executeUntilPostcombatMain();

        assertLife(playerA, 16);
        assertLife(playerB, 16);
        assertLife(playerC, 16);
        assertLife(playerD, 16);
        assertPowerToughness(playerA, MASTER, 3, 1);
        assertPowerToughness(playerA, LION, 3, 2);
    }

    @Test
    public void testSeparateDamageEventsEachTrigger() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, FLAME_RIFT, 2);

        // Two separate resolutions damage the opponents twice: two triggers give a total of +2/+0.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLAME_RIFT, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLAME_RIFT);
        executeUntilPostcombatMain();

        assertLife(playerA, 12);
        assertLife(playerB, 12);
        assertLife(playerC, 12);
        assertLife(playerD, 12);
        assertPowerToughness(playerA, MASTER, 4, 1);
        assertPowerToughness(playerA, LION, 4, 2);
    }

    @Test
    public void testDamageToControllerDoesNotTrigger() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, SHOCK);

        // Shock damages only Master of Barbs's controller, so no opponent was dealt damage.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SHOCK, playerA);
        executeUntilPostcombatMain();

        assertLife(playerA, 18);
        assertPowerToughness(playerA, MASTER, 2, 1);
        assertPowerToughness(playerA, LION, 2, 2);
    }

    @Test
    public void testCombatDamageDoesNotTrigger() {
        setupBattlefield();

        // An unblocked Lion deals combat damage; Master of Barbs requires noncombat damage.
        attack(1, playerA, LION, playerB);
        executeUntilPostcombatMain();

        assertLife(playerB, 18);
        assertPowerToughness(playerA, MASTER, 2, 1);
        assertPowerToughness(playerA, LION, 2, 2);
    }

    @Test
    public void testOpponentControlledSourceTriggers() {
        setupBattlefield();
        addCard(Zone.BATTLEFIELD, playerB, PYROMANCER);

        // An opponent's Pyromancer damages another opponent; the trigger does not restrict the source's controller.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: ", playerC);
        executeUntilPostcombatMain();

        assertLife(playerC, 19);
        assertPowerToughness(playerA, MASTER, 3, 1);
        assertPowerToughness(playerA, LION, 3, 2);
    }

    @Test
    public void testBoostExpiresAtEndOfTurn() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, FLAME_RIFT);

        // The boost lasts "until end of turn", so both creatures have their original power next turn.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLAME_RIFT);
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 16);
        assertPowerToughness(playerA, MASTER, 2, 1);
        assertPowerToughness(playerA, LION, 2, 2);
    }
}
