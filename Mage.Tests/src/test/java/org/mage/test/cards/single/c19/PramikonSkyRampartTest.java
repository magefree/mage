package org.mage.test.cards.single.c19;

import mage.abilities.effects.common.continuous.PlayerCanOnlyAttackInDirectionRestrictionEffect;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * @author Susucr
 */
public class PramikonSkyRampartTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    /**
     * Pramikon, Sky Rampart
     * {U}{R}{W}
     * Legendary Creature — Wall
     * <p>
     * Flying, defender
     * <p>
     * As Pramikon, Sky Rampart enters the battlefield, choose left or right.
     * <p>
     * Each player may attack only the nearest opponent in the chosen direction and planeswalkers controlled by that opponent.
     * <p>
     * 1/5
     */
    private static String pramikon = "Pramikon, Sky Rampart";

    private static String ancients = "Indomitable Ancients";
    private static String bogstomper = "Bogstomper";
    private static String crocodile = "Catacomb Crocodile";
    private static String devil = "Hulking Devil";

    @Test
    public void chooseLeft() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, pramikon);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerA, ancients);
        addCard(Zone.BATTLEFIELD, playerB, bogstomper);
        addCard(Zone.BATTLEFIELD, playerC, crocodile);
        addCard(Zone.BATTLEFIELD, playerD, devil);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pramikon);
        setChoice(playerA, PlayerCanOnlyAttackInDirectionRestrictionEffect.ALLOW_ATTACKING_LEFT);

        // A has pramikon, and chose left.
        //
        //  D <----> C
        //  ^        |
        //  |        |
        //  |        v
        //  A <----- B

        checkMayAttackDefender("Attack left possible",
                1, playerA, ancients, playerD, true);
        checkMayAttackDefender("Attack out of range impossible",
                1, playerA, ancients, playerC, false);
        checkMayAttackDefender("Attack right impossible",
                1, playerA, ancients, playerB, false);
        checkMayAttackDefender("Attack self impossible",
                1, playerA, ancients, playerA, false);

        checkMayAttackDefender("Attack left possible",
                2, playerD, devil, playerC, true);
        checkMayAttackDefender("Attack out of range impossible",
                2, playerD, devil, playerB, false);
        checkMayAttackDefender("Attack right impossible",
                2, playerD, devil, playerA, false);
        checkMayAttackDefender("Attack self impossible",
                2, playerD, devil, playerD, false);

        checkMayAttackDefender("Attack left possible -- not in range of Pramikon",
                3, playerC, crocodile, playerB, true);
        checkMayAttackDefender("Attack out of range impossible",
                3, playerC, crocodile, playerA, false);
        checkMayAttackDefender("Attack right possible -- not in range of Pramikon",
                3, playerC, crocodile, playerD, true);
        checkMayAttackDefender("Attack self impossible",
                3, playerC, crocodile, playerC, false);

        checkMayAttackDefender("Attack left possible",
                4, playerB, bogstomper, playerA, true);
        checkMayAttackDefender("Attack out of range impossible",
                4, playerB, bogstomper, playerD, false);
        checkMayAttackDefender("Attack right impossible",
                4, playerB, bogstomper, playerC, false);
        checkMayAttackDefender("Attack self impossible",
                4, playerB, bogstomper, playerB, false);

        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void chooseRight() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, pramikon);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerA, ancients);
        addCard(Zone.BATTLEFIELD, playerB, bogstomper);
        addCard(Zone.BATTLEFIELD, playerC, crocodile);
        addCard(Zone.BATTLEFIELD, playerD, devil);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pramikon);
        setChoice(playerA, PlayerCanOnlyAttackInDirectionRestrictionEffect.ALLOW_ATTACKING_RIGHT);

        // A has pramikon, and chose right.
        //
        //  D <----- C
        //  |        ^
        //  |        |
        //  v        v
        //  A -----> B

        checkMayAttackDefender("Attack left impossible",
                1, playerA, ancients, playerD, false);
        checkMayAttackDefender("Attack out of range impossible",
                1, playerA, ancients, playerC, false);
        checkMayAttackDefender("Attack right possible",
                1, playerA, ancients, playerB, true);
        checkMayAttackDefender("Attack self impossible",
                1, playerA, ancients, playerA, false);

        checkMayAttackDefender("Attack left impossible",
                2, playerD, devil, playerC, false);
        checkMayAttackDefender("Attack out of range impossible",
                2, playerD, devil, playerB, false);
        checkMayAttackDefender("Attack right possible",
                2, playerD, devil, playerA, true);
        checkMayAttackDefender("Attack self impossible",
                2, playerD, devil, playerD, false);

        checkMayAttackDefender("Attack left possible -- not in range of Pramikon",
                3, playerC, crocodile, playerB, true);
        checkMayAttackDefender("Attack out of range impossible",
                3, playerC, crocodile, playerA, false);
        checkMayAttackDefender("Attack right possible -- not in range of Pramikon",
                3, playerC, crocodile, playerD, true);
        checkMayAttackDefender("Attack self impossible",
                3, playerC, crocodile, playerC, false);

        checkMayAttackDefender("Attack left impossible",
                4, playerB, bogstomper, playerA, false);
        checkMayAttackDefender("Attack out of range impossible",
                4, playerB, bogstomper, playerD, false);
        checkMayAttackDefender("Attack right possible",
                4, playerB, bogstomper, playerC, true);
        checkMayAttackDefender("Attack self impossible",
                4, playerB, bogstomper, playerB, false);

        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void doublePramikon() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, pramikon);
        addCard(Zone.HAND, playerD, pramikon);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerA, ancients);
        addCard(Zone.BATTLEFIELD, playerB, bogstomper);
        addCard(Zone.BATTLEFIELD, playerC, crocodile);
        addCard(Zone.BATTLEFIELD, playerD, devil);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pramikon);
        setChoice(playerA, PlayerCanOnlyAttackInDirectionRestrictionEffect.ALLOW_ATTACKING_RIGHT);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, pramikon);
        setChoice(playerD, PlayerCanOnlyAttackInDirectionRestrictionEffect.ALLOW_ATTACKING_LEFT);

        // A has pramikon, and chose right.
        // D has pramikon, and chose left.
        //
        //  D x----x C
        //  x        ^
        //  |        |
        //  x        v
        //  A x----x B

        checkMayAttackDefender("Attack left impossible",
                2, playerD, devil, playerC, false);
        checkMayAttackDefender("Attack out of range impossible",
                2, playerD, devil, playerB, false);
        checkMayAttackDefender("Attack right impossible",
                2, playerD, devil, playerA, false);
        checkMayAttackDefender("Attack self impossible",
                2, playerD, devil, playerD, false);

        checkMayAttackDefender("Attack left possible -- not in range of A's Pramikon",
                3, playerC, crocodile, playerB, true);
        checkMayAttackDefender("Attack out of range impossible",
                3, playerC, crocodile, playerA, false);
        checkMayAttackDefender("Attack right impossible",
                3, playerC, crocodile, playerD, false);
        checkMayAttackDefender("Attack self impossible",
                3, playerC, crocodile, playerC, false);

        checkMayAttackDefender("Attack left impossible",
                4, playerB, bogstomper, playerA, false);
        checkMayAttackDefender("Attack out of range impossible",
                4, playerB, bogstomper, playerD, false);
        checkMayAttackDefender("Attack right possible -- not in range of D's Pramikon",
                4, playerB, bogstomper, playerC, true);
        checkMayAttackDefender("Attack self impossible",
                4, playerB, bogstomper, playerB, false);

        checkMayAttackDefender("Attack left impossible",
                5, playerA, ancients, playerD, false);
        checkMayAttackDefender("Attack out of range impossible",
                5, playerA, ancients, playerC, false);
        checkMayAttackDefender("Attack right impossible",
                5, playerA, ancients, playerB, false);
        checkMayAttackDefender("Attack self impossible",
                5, playerA, ancients, playerA, false);

        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void doublePramikonOther() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, pramikon);
        addCard(Zone.HAND, playerD, pramikon);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerA, ancients);
        addCard(Zone.BATTLEFIELD, playerB, bogstomper);
        addCard(Zone.BATTLEFIELD, playerC, crocodile);
        addCard(Zone.BATTLEFIELD, playerD, devil);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pramikon);
        setChoice(playerA, PlayerCanOnlyAttackInDirectionRestrictionEffect.ALLOW_ATTACKING_LEFT);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, pramikon);
        setChoice(playerD, PlayerCanOnlyAttackInDirectionRestrictionEffect.ALLOW_ATTACKING_RIGHT);

        // A has pramikon, and chose left.
        // D has pramikon, and chose right.
        //
        //  D <----x C
        //  x        x
        //  |        |
        //  x        x
        //  A <----x B

        checkMayAttackDefender("Attack left impossible",
                2, playerD, devil, playerC, false);
        checkMayAttackDefender("Attack out of range impossible",
                2, playerD, devil, playerB, false);
        checkMayAttackDefender("Attack right impossible",
                2, playerD, devil, playerA, false);
        checkMayAttackDefender("Attack self impossible",
                2, playerD, devil, playerD, false);

        checkMayAttackDefender("Attack left impossible",
                3, playerC, crocodile, playerB, false);
        checkMayAttackDefender("Attack out of range impossible",
                3, playerC, crocodile, playerA, false);
        checkMayAttackDefender("Attack right possible -- not in range of A's Pramikon",
                3, playerC, crocodile, playerD, true);
        checkMayAttackDefender("Attack self impossible",
                3, playerC, crocodile, playerC, false);

        checkMayAttackDefender("Attack left possible -- not in range of D's Pramikon",
                4, playerB, bogstomper, playerA, true);
        checkMayAttackDefender("Attack out of range impossible",
                4, playerB, bogstomper, playerD, false);
        checkMayAttackDefender("Attack right impossible",
                4, playerB, bogstomper, playerC, false);
        checkMayAttackDefender("Attack self impossible",
                4, playerB, bogstomper, playerB, false);

        checkMayAttackDefender("Attack left impossible",
                5, playerA, ancients, playerD, false);
        checkMayAttackDefender("Attack out of range impossible",
                5, playerA, ancients, playerC, false);
        checkMayAttackDefender("Attack right impossible",
                5, playerA, ancients, playerB, false);
        checkMayAttackDefender("Attack self impossible",
                5, playerA, ancients, playerA, false);

        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }
}