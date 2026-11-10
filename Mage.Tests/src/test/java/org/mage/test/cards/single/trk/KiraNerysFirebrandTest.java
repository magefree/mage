package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class KiraNerysFirebrandTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KiraNerysFirebrand}
     * <p>
     * {1}{R}{W} Legendary Creature - Bajoran Soldier 4/3<br>
     * Trample<br>
     * If a creature you control would enter while you control an attacking creature, instead it enters tapped and attacking.<br>
     * Whenever another creature you control enters, it gets +1/+1 until end of turn.
     */
    private static final String KIRA = "Kira Nerys, Firebrand";

    @Test
    public void test_EnterAttacking_AmbushViper() {
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Flash
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", true);
        assertLife(playerB, 20 - 2 - (2 + 1));
    }

    @Test
    public void test_EnterAttacking_CollectedCompany() {
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Look at the top six cards of your library. Put up to two creature cards with mana value 3 or less
        // from among them onto the battlefield. Put the rest on the bottom of your library in any order.
        addCard(Zone.HAND, playerA, "Collected Company"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.LIBRARY, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.LIBRARY, playerA, "Island"); // top of library, for the draw of turn 3
        skipInitShuffling();

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Collected Company");
        addTarget(playerA, "Silvercoat Lion^Elite Vanguard"); // creatures to put onto the battlefield
        setChoice(playerA, "Whenever another creature you control enters"); // order of Kira's two +1/+1 triggers

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Silvercoat Lion", true);
        assertTapped("Elite Vanguard", true);
        assertLife(playerB, 20 - 2 - (2 + 1) - (2 + 1));
    }

    @Test
    public void test_EnterAttacking_RaiseTheAlarm() {
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Raise the Alarm");
        setChoice(playerA, "Whenever another creature you control enters"); // order of Kira's two +1/+1 triggers

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Soldier Token", true, 2);
        assertLife(playerB, 20 - 2 - (1 + 1) - (1 + 1));
    }

    @Test
    public void test_EnterAttacking_CacklingCounterpart() {
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Create a token that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Cackling Counterpart"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Cackling Counterpart", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertTappedCount("Grizzly Bears", true, 2);
        assertLife(playerB, 20 - 2 - (2 + 1));
    }

    @Test
    public void test_EnterAttacking_DoubleMajor() {
        // the copy of a creature spell becomes a token, so the card and the token enter
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Flash
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // Copy target creature spell you control
        addCard(Zone.HAND, playerA, "Double Major"); // {G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        attack(3, playerA, "Grizzly Bears", playerB);

        activateManaAbility(3, PhaseStep.DECLARE_ATTACKERS, playerA, "{T}: Add {G}", 2);
        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Ambush Viper");

        activateManaAbility(3, PhaseStep.DECLARE_ATTACKERS, playerA, "{T}: Add {G}", 1);
        activateManaAbility(3, PhaseStep.DECLARE_ATTACKERS, playerA, "{T}: Add {U}", 1);
        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Double Major", "Ambush Viper", "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ambush Viper", 2);
        assertTappedCount("Ambush Viper", true, 2);
        assertLife(playerB, 20 - 2 - (2 + 1) - (2 + 1));
    }

    @Test
    public void test_EnterAttacking_HanweirGarrison() {
        // the tokens are tapped and attacking by their own text too
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        // Whenever this creature attacks, create two 1/1 red Human creature tokens that are tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerA, "Hanweir Garrison"); // 2/3

        attack(3, playerA, "Hanweir Garrison", playerB);
        setChoice(playerA, "Whenever another creature you control enters"); // order of Kira's two +1/+1 triggers

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Human Token", true, 2);
        assertLife(playerB, 20 - 2 - (1 + 1) - (1 + 1));
    }

    @Test
    public void test_NotEnterAttacking_MainPhase() {
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        // Flash
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertTapped("Ambush Viper", false);
        assertLife(playerB, 20);
    }

    @Test
    public void test_NotEnterAttacking_BeginningOfCombat() {
        // no attacking creature before attackers are declared
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Flash
        addCard(Zone.HAND, playerA, "Ambush Viper"); // {1}{G}, 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(3, PhaseStep.BEGIN_COMBAT, playerA, "Ambush Viper");

        attack(3, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", false);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_NotEnterAttacking_OpponentCreature() {
        addCard(Zone.BATTLEFIELD, playerA, KIRA);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        // Flash
        addCard(Zone.HAND, playerB, "Ambush Viper"); // {1}{G}, 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerB, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Ambush Viper", false);
        assertLife(playerB, 20 - 2);
    }
}
