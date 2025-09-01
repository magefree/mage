package org.mage.test.cards.single.dsk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ValgavothTerrorEaterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.ValgavothTerrorEater Valgavoth, Terror Eater} {6}{B}{B}{B}
     * Legendary Creature — Elder Demon
     * Flying, lifelink
     * Ward—Sacrifice three nonland permanents.
     * If a card you didn’t control would be put into an opponent’s graveyard from anywhere, exile it instead.
     * During your turn, you may play cards exiled with Valgavoth. If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
     * 9/9
     */
    private static final String valgavoth = "Valgavoth, Terror Eater";

    /**
     * Donate {2}{U} Sorcery
     * Target player gains control of target permanent you control.
     */
    private static final String donate = "Donate";
    private static final String vanguard = "Elite Vanguard";
    private static final String piker = "Goblin Piker";
    private static final String bear = "Grizzly Bears";
    /**
     * Lightning Bolt {R} Instant
     * Lightning Bolt deals 3 damage to any target.
     */
    private static final String bolt = "Lightning Bolt";
    /**
     * Fire // Ice
     * Fire {1}{R} Instant
     * Fire deals 2 damage divided as you choose among one or two targets.
     * Ice {1}{U} Instant
     * Tap target permanent.
     * Draw a card.
     */
    private static final String fireice = "Fire // Ice";
    private static final String fire = "Fire";
    private static final String ice = "Ice";
    /**
     * Cloudshift {W} Instant
     * Exile target creature you control, then return that card to the battlefield under your control.
     */
    private static final String cloudshift = "Cloudshift";
    /**
     * Pyroclasm {1}{R} Sorcery
     * Pyroclasm deals 2 damage to each creature.
     */
    private static final String pyroclasm = "Pyroclasm";
    /**
     * Legerdemain {2}{U}{U} Sorcery
     * Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
     */
    private static final String legerdemain = "Legerdemain";
    /**
     * Tome Scour {U} Sorcery
     * Target player mills five cards.
     */
    private static final String scour = "Tome Scour";

    /**
     * Akoum Warrior // Akoum Teeth
     * Akoum Warrior {5}{R}
     * Creature — Minotaur Warrior
     * Trample
     * 4/5
     * Akoum Teeth
     * Land
     * This land enters tapped.
     * {T}: Add {R}.
     */
    private static final String akoum = "Akoum Warrior // Akoum Teeth";
    private static final String warrior = "Akoum Warrior";
    private static final String teeth = "Akoum Teeth";

    @Test
    public void testOwnBolts() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.HAND, playerA, bolt, 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, piker, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, vanguard);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, piker);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerB, vanguard, 1);
        assertGraveyardCount(playerA, bolt, 3);
        assertGraveyardCount(playerA, piker, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testOpponentBolts() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.HAND, playerB, bolt, 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, piker, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, vanguard);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, piker);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerB, vanguard, 1);
        assertExileCount(playerB, bolt, 3);
        assertGraveyardCount(playerA, piker, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testAfterLegerdemainOthersA() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.HAND, playerA, pyroclasm, 1);
        addCard(Zone.HAND, playerA, legerdemain, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 6);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, piker, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, legerdemain, vanguard + "^" + piker);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroclasm);
        // playerA controls vanguard, so it is not exiled
        // piker goes into playerA's graveyard, so it is not exiled either
        // pyroclasm and legerdemain have been cast by playerA, so they are not exiled.

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 0);
        assertExileCount(playerB, 0);
        assertGraveyardCount(playerA, 3);
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testAfterLegerdemainOthersB() {
        addCard(Zone.BATTLEFIELD, playerB, valgavoth, 1);
        addCard(Zone.HAND, playerA, pyroclasm, 1);
        addCard(Zone.HAND, playerA, legerdemain, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 6);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, piker, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, legerdemain, vanguard + "^" + piker);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroclasm);
        // playerB controls piker, so it is not exiled
        // vanguard goes into playerB's graveyard, so it is not exiled either
        // pyroclasm and legerdemain have been cast by playerA, so they are exiled.

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 2);
        assertExileCount(playerB, 0);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testAfterLegerdemainOthersDonate() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.HAND, playerA, donate, 1);
        addCard(Zone.HAND, playerA, pyroclasm, 1);
        addCard(Zone.HAND, playerA, legerdemain, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 9);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, piker, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, donate, playerB);
        addTarget(playerA, valgavoth);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // Donate ends up exiled as valgavoth is in control of playerB as it finishes resolving
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, legerdemain, vanguard + "^" + piker);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroclasm);
        // playerB controls piker, so it is not exiled
        // vanguard goes into playerB's graveyard, so it is not exiled either
        // pyroclasm and legerdemain have been cast by playerA, so they are exiled.

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 3);
        assertExileCount(playerB, 0);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testMillSelf() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.HAND, playerA, scour, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scour, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 0);
        assertExileCount(playerB, 0);
        assertGraveyardCount(playerA, 6);
    }

    @Test
    public void testMillOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.HAND, playerA, scour, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scour, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 0);
        assertExileCount(playerB, 5);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCastTimingAndSplitCard() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.HAND, playerB, fireice);
        addCard(Zone.HAND, playerB, bolt);
        addCard(Zone.BATTLEFIELD, playerB, "Volcanic Island", 6);

        castSpell(1, PhaseStep.UPKEEP, playerB, bolt, playerA);
        checkPlayableAbility("1: Can cast bolt PlA", 1, PhaseStep.END_TURN, playerA, "Cast " + bolt, true);
        checkPlayableAbility("1: Can not cast bolt PlB", 1, PhaseStep.END_TURN, playerB, "Cast " + bolt, false);
        checkPlayableAbility("1: Can not cast fire PlA", 1, PhaseStep.END_TURN, playerA, "Cast " + fire, false);
        checkPlayableAbility("1: Can cast fire PlB", 1, PhaseStep.END_TURN, playerB, "Cast " + fire, true);
        checkPlayableAbility("1: Can not cast ice PlA", 1, PhaseStep.END_TURN, playerA, "Cast " + ice, false);
        checkPlayableAbility("1: Can cast ice PlB", 1, PhaseStep.END_TURN, playerB, "Cast " + ice, true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, fire, playerA);
        addTargetAmount(playerB, playerA, 2);
        checkPlayableAbility("2: Can not cast bolt PlA", 2, PhaseStep.END_TURN, playerA, "Cast " + bolt, false);
        checkPlayableAbility("2: Can not cast bolt PlB", 2, PhaseStep.END_TURN, playerB, "Cast " + bolt, false);
        checkPlayableAbility("2: Can not cast fire PlA", 2, PhaseStep.END_TURN, playerA, "Cast " + fire, false);
        checkPlayableAbility("2: Can not cast fire PlB", 2, PhaseStep.END_TURN, playerB, "Cast " + fire, false);
        checkPlayableAbility("2: Can not cast ice PlA", 2, PhaseStep.END_TURN, playerA, "Cast " + ice, false);
        checkPlayableAbility("2: Can not cast ice PlB", 2, PhaseStep.END_TURN, playerB, "Cast " + ice, false);

        waitStackResolved(3, PhaseStep.UPKEEP);
        checkPlayableAbility("3: Can cast bolt PlA", 3, PhaseStep.END_TURN, playerA, "Cast " + bolt, true);
        checkPlayableAbility("3: Can not cast bolt PlB", 2, PhaseStep.END_TURN, playerB, "Cast " + bolt, false);
        checkPlayableAbility("3: Can cast fire PlA", 3, PhaseStep.END_TURN, playerA, "Cast " + fire, true);
        checkPlayableAbility("3: Can not cast fire PlB", 2, PhaseStep.END_TURN, playerB, "Cast " + fire, false);
        checkPlayableAbility("3: Can cast ice PlA", 3, PhaseStep.END_TURN, playerA, "Cast " + ice, true);
        checkPlayableAbility("3: Can not cast ice PlB", 2, PhaseStep.END_TURN, playerB, "Cast " + ice, false);
        castSpell(3, PhaseStep.END_TURN, playerA, bolt, playerB);
        castSpell(3, PhaseStep.END_TURN, playerA, fire, playerB);
        addTargetAmount(playerA, playerB, 2);

        setStopAt(4, PhaseStep.UPKEEP);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 2);
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 0);

        assertLife(playerA, 20 - 3 - 2 - 1 - 2);
        assertLife(playerB, 20 - 3 - 2);
    }

    @Test
    public void testCastMDFC() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.LIBRARY, playerB, akoum, 5);
        addCard(Zone.HAND, playerA, scour);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scour, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("1: Can cast Akoum Warrior", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + warrior, true);
        checkPlayableAbility("1: Can play Akoum Teeth", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play " + teeth, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, warrior);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, warrior);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, warrior);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPlayableAbility("2: Can not cast Akoum Warrior (not enough life)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + warrior, false);
        checkPlayableAbility("2: Can play Akoum Teeth", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play " + teeth, true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, teeth);
        checkPlayableAbility("3: Can not cast Akoum Warrior (not enough life)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + warrior, false);
        checkPlayableAbility("3: Can not play Akoum Teeth (no land drop left)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play " + teeth, false);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 0);
        assertExileCount(playerB, 1);

        assertLife(playerA, 20 - 6 * 3);
        assertPermanentCount(playerA, warrior, 3);
        assertPermanentCount(playerA, teeth, 1);
    }

    @Test
    public void testWardAndBlink() {
        addCard(Zone.BATTLEFIELD, playerA, valgavoth, 1);
        addCard(Zone.BATTLEFIELD, playerB, bear, 1);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerB, piker, 1);
        addCard(Zone.HAND, playerB, bolt, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerA, cloudshift, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, valgavoth);
        setChoice(playerB, true); // pay for ward?
        setChoice(playerB, bear + "^" + vanguard + "^" + piker); // ward sacrifices

        checkPlayableAbility("1: Can cast bear", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bear, true);
        checkPlayableAbility("1: Can cast vanguard", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + vanguard, true);
        checkPlayableAbility("1: Can cast piker", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + piker, true);
        checkPlayableAbility("1: Can cast bolt", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bolt, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, cloudshift, valgavoth);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        // after blink, nothing can be cast
        checkPlayableAbility("2: Can not cast bear", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bear, false);
        checkPlayableAbility("2: Can not cast vanguard", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + vanguard, false);
        checkPlayableAbility("2: Can not cast piker", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + piker, false);
        checkPlayableAbility("2: Can not cast bolt", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bolt, false);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 0);
        assertExileCount(playerB, 4);
        assertGraveyardCount(playerA, 1);
    }
}
