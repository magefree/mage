package org.mage.test.cards.single.znr;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.TreasureToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.y.YasharnImplacableEarth Yasharn, Implacable Earth}
 * When Yasharn enters the battlefield, search your library for a basic Forest card and a basic Plains card, reveal those cards, put them into your hand, then shuffle.
 * Players can’t pay life or sacrifice nonland permanents to cast spells or activate abilities.
 *
 * @author Alex-Vasile
 */
public class YasharnImplacableEarthTest extends CardTestPlayerBase {

    private static final String yasharn = "Yasharn, Implacable Earth";

    /**
     * Test that players can't pay life to cast a spell.
     */
    @Test
    public void cantPayLifeToCast() {
        // {W}{B}
        // As an additional cost to cast this spell, pay 5 life or sacrifice a creature or enchantment.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Final Payment");
        //{4}{B/P}{B/P}{B/P}
        //Legendary Creature — Phyrexian Horror Minion
        //2/2
        //Lifelink
        //For each {B} in a cost, you may pay 2 life rather than pay that mana.
        //Whenever you cast a black spell, put a +1/+1 counter on K'rrik.
        addCard(Zone.HAND, playerA, "K'rrik, Son of Yawgmoth");
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        checkPlayableAbility("Can't cast Final Payment", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Final Payment", false);
        checkPlayableAbility("Can't cast K'rrik", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast K'rrik, Son of Yawgmoth", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertLife(playerA, 20);
    }

    /**
     * Test that players can't pay life to activate an ability.
     */
    @Test
    public void cantPayLifeToActivate() {
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Flooded Strand");
        addCard(Zone.LIBRARY, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: {T}")) {
                Assert.fail("must throw error about having 0 actions, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * Test that players can't sacrifice a nonland permanent to cast a spell.
     */
    @Test
    public void cantSacrificeNonlandToCast() {
        // {1}{B}
        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        // Draw two cards and create a Treasure token.
        addCard(Zone.HAND, playerA, "Deadly Dispute");
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Bear Cub");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setStrictChooseMode(true);
        checkPlayableAbility("Can't cast Deadly Dispute", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Deadly Dispute", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    /**
     * Test that players can't sacrifice nonland permanent to activate an ability.
     */
    @Test
    public void cantSacrificeNonlandtoActive() {
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Armillary Sphere");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: {2}")) {
                Assert.fail("must throw error about having 0 actions, but got:\n" + e.getMessage());
            }
        }
        assertHandCount(playerA, 0);
    }

    /**
     * Test that a player can still sacrifice a land to cast a spell.
     */
    @Test
    public void canSacrificeLandToCast() {
        //Crop Rotation
        //{G}
        //As an additional cost to cast this spell, sacrifice a land.
        //Search your library for a land card, put that card onto the battlefield, then shuffle.
        addCard(Zone.HAND, playerA, "Crop Rotation");
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Mountain");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crop Rotation");
        setChoice(playerA, "Forest");
        addTarget(playerA, "Mountain");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Forest", 0);

        assertGraveyardCount(playerA, "Forest", 1);
    }

    /**
     * Test that a player can still sacrifice a land to activate an abiltiy.
     */
    @Test
    public void canSacrificeLandToActivate() {
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Evolving Wilds");
        addCard(Zone.LIBRARY, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 1);
        assertGraveyardCount(playerA, "Evolving Wilds", 1);
    }

    /**
     * Test that a player cannot sacrifice artifacts or creatures to activate abilities
     */
    @Test
    public void cantSacToMondrakWithArtifacts() {
        setStrictChooseMode(true);
        //Mondrak, Glory Dominus
        //{2}{W}{W}
        //Legendary Creature — Phyrexian Horror
        //If one or more tokens would be created under your control, twice that many of those tokens are created instead.
        //{1}{W/P}{W/P}, Sacrifice two other artifacts and/or creatures: Put an indestructible counter on Mondrak.
        String mondrak = "Mondrak, Glory Dominus";

        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new CreateTokenEffect(new TreasureToken(), 2),
                new ManaCostsImpl<>("")
        );
        ability.addEffect(new CreateTokenEffect(new FoodToken(), 2));

        addCustomCardWithAbility("Token-maker", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, mondrak);
        addCard(Zone.BATTLEFIELD, playerA, yasharn);
        addCard(Zone.BATTLEFIELD, playerA, "Bear Cub", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        checkPlayableAbility("Can't activate Mondrak with creatures", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/P}{W/P}, Sacrifice", false);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "create two");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("Can't activate Mondrak with creatures or artifacts", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/P}{W/P}, Sacrifice", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Bear Cub", 2);
        assertPermanentCount(playerA, "Treasure Token", 4);
        assertPermanentCount(playerA, "Food Token", 4);
    }
}
