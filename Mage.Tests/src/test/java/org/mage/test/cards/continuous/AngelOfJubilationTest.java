package org.mage.test.cards.continuous;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.FoodToken;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.AngelOfJubilation Angel of Jubilation}
 * {1}{W}{W}{W}
 * Creature — Angel
 * Flying
 *
 * Other nonblack creatures you control get +1/+1.
 * Players can't pay life or sacrifice creatures to cast spells or activate abilities
 *
 * @author noxx
 */
public class AngelOfJubilationTest extends CardTestPlayerBase {

    public static final String angelOfJubilation = "Angel of Jubilation";

    /**
     * Tests boosting other non black creatures
     */
    @Test
    public void testBoost() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, angelOfJubilation, 3, 3);
        assertPowerToughness(playerA, "Devout Chaplain", 3, 3);
        assertPowerToughness(playerA, "Corpse Traders", 3, 3);
    }

    /**
     * Tests boost disappeared on leaving battlefield
     */
    @Test
    public void testNoBoostOnBattlefieldLeave() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", angelOfJubilation);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, angelOfJubilation, 0);
        assertPowerToughness(playerA, "Devout Chaplain", 2, 2);
        assertPowerToughness(playerA, "Corpse Traders", 3, 3);
    }

    @Test
    public void testOpponentCantSacrificeCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Corpse Traders");

        checkPlayableAbility("Can't sac", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice", false);
//        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice a creature: {this} gets +2/+2 until end of turn.");
//        playerB.addChoice("Corpse Traders");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerB, "Nantuko Husk", 2, 2);
        assertPermanentCount(playerB, "Corpse Traders", 1);
    }

    @Test
    public void testOpponentCanSacrificeNonCreaturePermanents() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        addCard(Zone.BATTLEFIELD, playerB, "Barrin, Master Wizard");
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Food Chain");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, Sacrifice a permanent: Return target creature to its owner's hand.");
        addTarget(playerB, angelOfJubilation); // return to hand
        setChoice(playerB, "Food Chain"); // sacrifice cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, angelOfJubilation, 0);
        assertPermanentCount(playerB, "Food Chain", 0);
    }

    @Test
    public void testOpponentCantSacrificeCreaturesAsPartOfPermanentsOptions() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerB, "Barrin, Master Wizard");
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves", 2);

        checkPlayableAbility("Can't sac", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, Sacrifice", false);
//        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, Sacrifice a permanent you control: Return target creature to its owner's hand.");
//        playerB.addChoice("Nantuko Husk");
//        playerA.addTarget("Angel of Jubilation");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, angelOfJubilation, 1);
        assertPermanentCount(playerB, "Nantuko Husk", 1);
    }

    @Test
    public void testOpponentCantSacrificeAll() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Corpse Traders");
        addCard(Zone.HAND, playerB, "Soulblast");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);

        checkPlayableAbility("Can't sac all", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Soulblast", false);
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Soulblast", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Nantuko Husk", 1);
        assertPermanentCount(playerB, "Corpse Traders", 1);
    }

    @Test
    public void testOpponentCantSacrificeCreatureSource() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerB, "Children of Korlis");

        checkPlayableAbility("Can't sac", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Children of Korlis", 1);
    }

    @Test
    public void testOpponentCanSacrificeAllLands() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerB, "Tomb of Urami");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{B}{B}, {T}, Sacrifice all lands you control: Create Urami, a legendary 5/5 black Demon Spirit creature token with flying.");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Swamp", 0);
    }

    @Test
    public void testOpponentCanSacrificeNonCreatureSource() {
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra");
        addCard(Zone.BATTLEFIELD, playerB, "Wasteland");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Sacrifice {this}: Destroy target nonbasic land.");
        playerB.addTarget("Tundra");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Tundra", 0);
        assertPermanentCount(playerB, "Wasteland", 0);
    }

    /**
     * https://github.com/magefree/mage/issues/3663
     *
     * Angel of Jubilation should just prevent paying life for activating
     * abilities, but currently when it is out the opponent is not prompted to
     * choose whether or not to pay life for Athreos.
     */
    @Test
    public void testAthreosLifePayNotPrevented() {
        setStrictChooseMode(true);
        // Other nonblack creatures you control get +1/+1. 
        // Players can't pay life or sacrifice creatures to cast spells or activate abilities
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Indestructible
        // As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        // Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.        
        addCard(Zone.BATTLEFIELD, playerA, "Athreos, God of Passage");        

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerB, true); // Pay 3 life to prevent that returns to PlayerA's hand?

        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    /**
     * 5/1/2012
     *
     * If a spell or activated ability has a cost that requires a player to pay
     * life (as Griselbrand’s activated ability does) or sacrifice a creature
     * (as Fling does), that spell or ability can’t be cast or activated.
     */
    @Test
    public void testGriselbrandCantPay() {
        setStrictChooseMode(true);
        // Other nonblack creatures you control get +1/+1. 
        // Players can't pay life or sacrifice creatures to cast spells or activate abilities
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);

        // Pay 7 life: Draw seven cards.
        addCard(Zone.BATTLEFIELD, playerB, "Griselbrand");
        
        checkPlayableAbility("activated ability", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Pay 7 life", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCanSacrificeTriggeredAbility() {
        /*
        Unscrupulous Contractor
        {2}{B}
        Creature — Human Assassin
        When this creature enters, you may sacrifice a creature. When you do, target player draws two cards and loses 2 life.
        Plot {2}{B}
        3/2
         */
        String contractor = "Unscrupulous Contractor";
        /*
        Bear Cub
        {1}{G}
        Creature - Bear
        2/2
         */
        String cub = "Bear Cub";

        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.HAND, playerA, contractor);
        addCard(Zone.HAND, playerB, contractor);
        addCard(Zone.BATTLEFIELD, playerA, cub);
        addCard(Zone.BATTLEFIELD, playerB, cub);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, contractor);
        setChoice(playerA, true);
        setChoice(playerA, cub);
        addTarget(playerA, playerA);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, contractor);
        setChoice(playerB, true);
        setChoice(playerB, cub);
        addTarget(playerB, playerB);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 2);
        assertLife(playerA, 20 - 2);
        assertGraveyardCount(playerA, cub, 1);

        assertHandCount(playerB, 1 + 2); //draw + contractor effect
        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerB, cub, 1);
    }

    @Test
    public void canSacToMondrakWithArtifacts() {
        setStrictChooseMode(true);
        //Mondrak, Glory Dominus
        //{2}{W}{W}
        //Legendary Creature — Phyrexian Horror
        //If one or more tokens would be created under your control, twice that many of those tokens are created instead.
        //{1}{W/P}{W/P}, Sacrifice two other artifacts and/or creatures: Put an indestructible counter on Mondrak.
        String mondrak = "Mondrak, Glory Dominus";
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new CreateTokenEffect(new FoodToken(), 2),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("Token-maker", playerA, ability);

        addCard(Zone.BATTLEFIELD, playerA, mondrak);
        addCard(Zone.BATTLEFIELD, playerA, angelOfJubilation);
        addCard(Zone.BATTLEFIELD, playerA, "Bear Cub", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        checkPlayableAbility("Can't activate Mondrak", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/P}{W/P}, Sacrifice", false);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "create two");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/P}{W/P}, Sacrifice");
        setChoice(playerA, "Food Token", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, mondrak, CounterType.INDESTRUCTIBLE, 1);
        assertPermanentCount(playerA, "Bear Cub", 2);
        assertPermanentCount(playerA, "Food Token", 2);
    }
}
