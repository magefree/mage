package org.mage.test.cards.dynamicvalue;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 700.8.
 * Some cards refer to a player’s party. A player’s party consists of up to one Cleric creature that player controls, up to one Rogue creature they control, up to one Warrior creature they control, and up to one Wizard creature they control.
 * <p>
 * 700.8b
 * If a creature has multiple creature types for which it could be the party member, it is counted as the party member for only one of those types. If there are different ways to count such a creature that results in different numbers of creatures in a player’s party, it is counted in such a way to get the highest result.
 * <p>
 * 700.8c
 * A player has a full party if there are four creatures in that player’s party.
 *
 * @author TheElk801
 */
public class PartyCountTest extends CardTestPlayerBase {

    private void makeTester() {
        makeTester(playerA);
    }

    private void makeTester(TestPlayer player) {
        addCustomCardWithAbility(
                "tester", player,
                new SimpleActivatedAbility(
                        new GainLifeEffect(PartyCount.instance), new ManaCostsImpl<>("{0}")
                )
        );
    }

    private void useTester() {
        useTester(playerA);
    }

    private void useTester(TestPlayer player) {
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, player, "{0}:");
    }

    private void makeCreature(String name, SubType... subTypes) {
        makeCreature(name, playerA, subTypes);
    }

    private void makeCreature(String name, TestPlayer player, SubType... subTypes) {
        addCustomCardWithAbility(
                name, player, null, null,
                CardType.CREATURE, "{1}", Zone.BATTLEFIELD, subTypes
        );
    }

    @Test
    public void testNoMembers() {
        makeTester();

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
    }

    @Test
    public void testSingleMember() {
        makeTester();
        makeCreature("crt1", SubType.CLERIC);

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 21);
    }

    @Test
    public void testSingleMember2() {
        makeTester();
        makeCreature("crt1", SubType.CLERIC, SubType.WIZARD);

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 21);
    }

    @Test
    public void testTwoMembers() {
        makeTester();
        makeCreature("crt1", SubType.CLERIC);
        makeCreature("crt2", SubType.WARRIOR);

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 22);
    }

    @Test
    public void testTwoMembers2() {
        makeTester();
        makeCreature("crt1", SubType.CLERIC);
        makeCreature("crt2", SubType.CLERIC);

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 21);
    }

    @Test
    public void testThreeMembers() {
        makeTester();
        makeCreature("crt1", SubType.CLERIC);
        makeCreature("crt2", SubType.WARRIOR);
        makeCreature("crt3", SubType.WIZARD);

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 23);
    }

    @Test
    public void testThreeMembers2() {
        makeTester();
        makeCreature("crt1", SubType.CLERIC, SubType.WARRIOR);
        makeCreature("crt2", SubType.CLERIC, SubType.WARRIOR);
        makeCreature("crt3", SubType.CLERIC, SubType.WARRIOR, SubType.WIZARD);

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 23);
    }

    @Test
    public void testOddCombos() {
        makeTester();
        makeCreature("crt1", SubType.ROGUE, SubType.WIZARD, SubType.WARRIOR);
        makeCreature("crt2", SubType.ROGUE, SubType.CLERIC);
        makeCreature("crt3", SubType.CLERIC, SubType.WIZARD);
        makeCreature("crt4", SubType.WARRIOR, SubType.WIZARD);

        useTester();
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 24);
    }

    @Test
    public void testOpponent() {
        makeTester(playerA);
        makeTester(playerB);
        makeCreature("crt1", playerB, SubType.CLERIC);

        useTester(playerA);
        useTester(playerB);
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 21);
    }

    @Test
    public void testChangelings() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Impostor of the Sixth Pride", 3);

        useTester();

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 23);
    }

    @Test
    public void test_PartyCount_ManyCreaturesWithSameTwoTypes_AlliedAssault() {
        // Up to two target creatures each get +X/+X until end of turn, where X is the number of creatures in your party.
        addCard(Zone.HAND, playerA, "Allied Assault"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Wandering Mage", 2); // Human Cleric Wizard 0/3
        addCard(Zone.BATTLEFIELD, playerA, "Shieldmage Elder"); // Human Cleric Wizard 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Swashbuckler Extraordinaire"); // Dragon Rogue Warrior 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Allied Assault", "Swashbuckler Extraordinaire");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // three creatures share Cleric and Wizard, so only two of them get a place,
        // Swashbuckler Extraordinaire is the Rogue or the Warrior, not both
        assertPowerToughness(playerA, "Swashbuckler Extraordinaire", 2 + 3, 2 + 3);
    }

    @Test
    public void test_PartyCount_FullPartyOnlyByBestCount_ArchpriestOfIona() {
        // Archpriest of Iona's power is equal to the number of creatures in your party.
        // At the beginning of combat on your turn, if you have a full party, target creature gets +1/+1 and gains flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Archpriest of Iona"); // Human Cleric 0/2
        //
        addCard(Zone.BATTLEFIELD, playerA, "Wandering Mage"); // Human Cleric Wizard 0/3
        addCard(Zone.BATTLEFIELD, playerA, "Smirking Spelljacker"); // Djinn Wizard Rogue 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Swashbuckler Extraordinaire"); // Dragon Rogue Warrior 2/2

        addTarget(playerA, "Swashbuckler Extraordinaire"); // full party trigger: +1/+1 and flying

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        // the only full party: Archpriest of Iona as the Cleric, Wandering Mage as the Wizard,
        // Smirking Spelljacker as the Rogue, Swashbuckler Extraordinaire as the Warrior
        assertPowerToughness(playerA, "Archpriest of Iona", 4, 2);
        assertPowerToughness(playerA, "Swashbuckler Extraordinaire", 2 + 1, 2 + 1);
        assertAbility(playerA, "Swashbuckler Extraordinaire", FlyingAbility.getInstance(), true);
    }
}
