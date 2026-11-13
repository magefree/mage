package org.mage.test.cards.rules;

import mage.abilities.keyword.DecayedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Effects that put a card onto the battlefield and then work with the new permanent must find that permanent
 * by CardUtil.getPermanentFromCardPutToBattlefield: a double-faced card on the battlefield is a side object
 * with another id
 *
 * @author JayDi85
 */
public class PutDoubleFacedCardOntoBattlefieldTest extends CardTestPlayerBase {

    private static final String kytheon = "Kytheon, Hero of Akros"; // {W}, 2/1, transforming double-faced
    private static final String compass = "Thaumatic Compass"; // {2}, artifact, transforming double-faced

    @Test
    public void test_Aetherplasm() {
        // Whenever this creature blocks a creature, you may return this creature to its owner's hand. If you do,
        // you may put a creature card from your hand onto the battlefield blocking that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Aetherplasm");
        addCard(Zone.HAND, playerA, kytheon);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        attack(2, playerB, "Grizzly Bears", playerA);
        block(2, playerA, "Aetherplasm", "Grizzly Bears");
        setChoice(playerA, true); // return
        setChoice(playerA, true); // put
        setChoice(playerA, kytheon);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1); // killed by the new blocker
    }

    @Test
    public void test_CallOfTheDeathDweller() {
        // Return up to two target creature cards with total mana value 3 or less from your graveyard to the
        // battlefield. Put a deathtouch counter on either of them. Then put a menace counter on either of them.
        addCard(Zone.HAND, playerA, "Call of the Death-Dweller"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Call of the Death-Dweller", kytheon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, kytheon, CounterType.DEATHTOUCH, 1);
        assertCounterCount(playerA, kytheon, CounterType.MENACE, 1);
    }

    @Test
    public void test_DanceOfTheManse() {
        // Return up to X target artifact and/or non-Aura enchantment cards each with mana value X or less from your
        // graveyard to the battlefield. If X is 6 or more, those permanents are 4/4 creatures in addition to their
        // other types.
        addCard(Zone.HAND, playerA, "Dance of the Manse"); // {X}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6 + 2);
        addCard(Zone.GRAVEYARD, playerA, compass);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dance of the Manse");
        setChoice(playerA, "X=6");
        addTarget(playerA, compass);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN); // compass transforms at end step
        execute();

        assertType(compass, CardType.CREATURE, true);
        assertPowerToughness(playerA, compass, 4, 4);
    }

    @Test
    public void test_GhostVacuum() {
        // {T}: Exile target card from a graveyard.
        // {6}, {T}, Sacrifice this artifact: Put each creature card exiled with this artifact onto the battlefield
        // under your control with a flying counter on it. Each of them is a 1/1 Spirit in addition to its other
        // types. Activate only as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Ghost Vacuum");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);
        addCard(Zone.GRAVEYARD, playerB, kytheon);

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile", kytheon);

        // return
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}, {T}, Sacrifice");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, kytheon, 1, 1);
        assertSubtype(kytheon, SubType.SPIRIT);
    }

    @Test
    public void test_LegionsInitiative() {
        // {R}{W}, Exile this enchantment: Exile all creatures you control. At the beginning of the next combat,
        // return those cards to the battlefield under their owner's control and those creatures gain haste until
        // end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Legion's Initiative");
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.BATTLEFIELD, playerA, kytheon);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}{W}, Exile");
        checkAbility("haste", 1, PhaseStep.DECLARE_ATTACKERS, playerA, kytheon, HasteAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Pull() {
        // Put up to two target creature cards from a single graveyard onto the battlefield under your control.
        // They gain haste until end of turn. Sacrifice them at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Push // Pull");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.GRAVEYARD, playerB, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pull", kytheon);
        checkAbility("haste", 1, PhaseStep.BEGIN_COMBAT, playerA, kytheon, HasteAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerB, kytheon, 1); // sacrificed
    }

    @Test
    public void test_SmileAtDeath() {
        // At the beginning of your upkeep, return up to two target creature cards with power 2 or less from your
        // graveyard to the battlefield. Put a +1/+1 counter on each of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Smile at Death");
        addCard(Zone.GRAVEYARD, playerA, kytheon);

        addTarget(playerA, kytheon); // on upkeep

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, kytheon, CounterType.P1P1, 1);
    }

    @Test
    public void test_SuddenSalvation() {
        // Choose up to three target permanent cards in graveyards that were put there from the battlefield this
        // turn. Return them to the battlefield tapped under their owners' control. You draw a card for each
        // opponent who controls one or more of those permanents.
        addCard(Zone.HAND, playerA, "Sudden Salvation"); // {2}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", kytheon);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Salvation", kytheon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, kytheon, 1);
        assertHandCount(playerA, 1); // draw for opponent
    }

    @Test
    public void test_WakeTheDead() {
        // Cast this spell only during combat on an opponent's turn.
        // Return X target creature cards from your graveyard to the battlefield. Sacrifice those creatures at the
        // beginning of the next end step.
        addCard(Zone.HAND, playerA, "Wake the Dead"); // {X}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 2);
        addCard(Zone.GRAVEYARD, playerA, kytheon);

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Wake the Dead");
        setChoice(playerA, "X=1");
        addTarget(playerA, kytheon);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, kytheon, 1); // sacrificed
    }

    @Test
    public void test_StormOfSouls() {
        // Return all creature cards from your graveyard to the battlefield. Each of them is a 1/1 Spirit with flying
        // in addition to its other types. Exile Storm of Souls.
        addCard(Zone.HAND, playerA, "Storm of Souls"); // {4}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.GRAVEYARD, playerA, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storm of Souls");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, kytheon, 1, 1);
        assertSubtype(kytheon, SubType.SPIRIT);
        assertAbility(playerA, kytheon, FlyingAbility.getInstance(), true);
    }

    @Test
    public void test_GhoulsNightOut() {
        // For each player, choose a creature card in that player's graveyard. Put those cards onto the battlefield
        // under your control. They're black Zombies in addition to their other colors and types and they gain
        // decayed.
        addCard(Zone.HAND, playerA, "Ghouls' Night Out"); // {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerA, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ghouls' Night Out");
        addTarget(playerA, kytheon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertSubtype(kytheon, SubType.ZOMBIE);
        assertAbility(playerA, kytheon, new DecayedAbility(), true);
    }

    @Test
    public void test_GisaGloriousResurrector() {
        // If a creature an opponent controls would die, exile it instead.
        // At the beginning of your upkeep, put all creature cards exiled with Gisa onto the battlefield under your
        // control. They gain decayed.
        addCard(Zone.BATTLEFIELD, playerA, "Gisa, Glorious Resurrector");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", kytheon);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, kytheon, 1);
        assertAbility(playerA, kytheon, new DecayedAbility(), true);
    }

    @Test
    public void test_WakeThePast() {
        // Return all artifact cards from your graveyard to the battlefield. They gain haste until end of turn.
        addCard(Zone.HAND, playerA, "Wake the Past"); // {5}{R}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 7);
        addCard(Zone.GRAVEYARD, playerA, compass);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wake the Past");
        checkAbility("haste", 1, PhaseStep.BEGIN_COMBAT, playerA, compass, HasteAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_WakeToSlaughter() {
        // Choose up to two target creature cards in your graveyard. An opponent chooses one of them. Return that card
        // to your hand. Return the other to the battlefield under your control. It gains haste. Exile it at the
        // beginning of the next end step.
        addCard(Zone.HAND, playerA, "Wake to Slaughter"); // {3}{B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 5);
        addCard(Zone.GRAVEYARD, playerA, kytheon);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wake to Slaughter");
        addTarget(playerA, kytheon + "^Grizzly Bears");
        addTarget(playerB, "Grizzly Bears"); // to hand
        checkAbility("haste", 1, PhaseStep.BEGIN_COMBAT, playerA, kytheon, HasteAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, kytheon, 1);
    }
}
