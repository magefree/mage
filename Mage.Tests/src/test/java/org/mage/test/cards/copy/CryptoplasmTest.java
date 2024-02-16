package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Mana cost: 1UU
 * Type: Creature — Shapeshifter
 * Effect of card: At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
 * Power/Toughness: 2/2
 */

public class CryptoplasmTest extends CardTestPlayerBase {

    @Test
    public void testTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Cryptoplasm", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 1);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 2);
    }

    /**
     * I have a Cryptoplasm in play, currently copying a Sigiled Paladin, and I
     * enchant it with a Followed Footsteps. Next turn the aura triggers (the
     * Crypto is still copying the same creature) and places a token on the
     * battlefield, except the token is an untransformed Cryptoplasm, when it
     * should be a Sigiled Paladin with Cryptoplasm's ability (as per rule
     * 706.3), since that's what the enchanted creature currently is.
     *
     * 6/1/2011 If another creature becomes a copy of Cryptoplasm, it will
     * become a copy of whatever Cryptoplasm is currently copying (if anything),
     * plus it will have Cryptoplasm's triggered ability.
     */
    @Test
    public void testFollowedFootsteps() {
        // First strike
        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        addCard(Zone.BATTLEFIELD, playerA, "Sigiled Paladin", 1); //  {W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        // Enchant creature
        // At the beginning of your upkeep, put a token that's a copy of enchanted creature onto the battlefield.
        addCard(Zone.HAND, playerB, "Followed Footsteps", 1); // {3}{U}{U}
        // At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
        addCard(Zone.BATTLEFIELD, playerB, "Cryptoplasm", 1); // {1}{U}{U}

        // turn 2 - prepare (crypto to paladin, footsteps to crypto)
        // crypto:  copy as paladin on upkeep
        setChoice(playerB, true);
        addTarget(playerB, "Sigiled Paladin");
        // footsteps: enchant copy of paladin (crypto)
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Followed Footsteps");
        addTarget(playerB, "Sigiled Paladin[only copy]");

        // turn 4 - ignore crypto ask for new copy
        setChoice(playerB, false);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Followed Footsteps", 1);
        assertPermanentCount(playerB, "Cryptoplasm", 0); // it's a copy
        assertPermanentCount(playerB, "Sigiled Paladin", 2); // crypto as copy + footstep token as copy
        assertPermanentCount(playerA, "Sigiled Paladin", 1); // original
    }

    /**
     * I'm at 8 life, opponent (AI) is at 21. I have a Cryptoplasm currently
     * copying my opponent's Divinity of Pride, the Crypto also has my Followed
     * Footsteps attached to it. Additionally, I have another Cryptoplasm on the
     * battlefield also copying the same Divinity, and a Clever Impersonator
     * that ETB copying the first Cryptoplasm and is currently also a copy of
     * the Divinity.
     *
     * Opponent attacks with their only Divinity of Pride (4/4) and a Serra
     * Avenger (3/3). I block the Divinity with two of my Divinity copies (the
     * Clever Impersonator and unenchanted Cryptoplasm) and the Avenger with the
     * enchanted Divinity (originally a Cryptoplasm). My opponent's Divinity
     * kills my two copies and dies, and then their Avenger dies and kills the
     * Divinity blocking it, also sending my Followed Footsteps down with it.
     *
     * How does any of that add up? Not only should their Divinity only kill one
     * of mine since it was a 4/4 and only becomes an 8/8 after dealing its
     * damage (at which point it should be too late to go back and say the 4
     * damage are now 8, since it was that exact damage that put them at 25
     * life), but even more confusing is how the Serra Avenger, which is a 3/3,
     * somehow kills my 4/4 that had suffered no other damage that turn.
     *
     * No other permanents in play at that moment had any influence in this
     * either, they were only basic lands and a couple of creatures with no
     * relevant abilities.
     *
     * I won't put aside me completely missing something here, but I really
     * can't see any other explanation to this other than a game bug.
     */
    @Test
    public void testDamageLifelink() {
        setLife(playerA, 21);
        setLife(playerB, 8);
        // First strike
        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        addCard(Zone.BATTLEFIELD, playerA, "Divinity of Pride", 1); //  {W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        // At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
        addCard(Zone.BATTLEFIELD, playerB, "Cryptoplasm", 2); // {1}{U}{U}
        addTarget(playerB, "Divinity of Pride");
        addTarget(playerB, "Divinity of Pride");

        attack(3, playerA, "Divinity of Pride");
        block(3, playerB, "Divinity of Pride:0", "Divinity of Pride");
        block(3, playerB, "Divinity of Pride:1", "Divinity of Pride");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Cryptoplasm", 0);

        assertPermanentCount(playerA, "Divinity of Pride", 0);
        assertPermanentCount(playerB, "Divinity of Pride", 1);

        assertLife(playerB, 16);
        assertLife(playerA, 25);
    }

    @Test
    public void testTransformMultipleTime() {
        // At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Cryptoplasm", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // 6/4
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm", 1); // 6/4

        setStrictChooseMode(true);

        // Turn 1
        setChoice(playerA, "Yes");
        addTarget(playerA, "Silvercoat Lion");

        // Turn 3
        setChoice(playerA, "Yes");
        addTarget(playerA, "Craw Wurm");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Craw Wurm", 1);
    }
}
