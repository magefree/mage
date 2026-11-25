package org.mage.test.cards.single.tdc;

import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 400.7
 * An object that moves from one zone to another becomes a new object with no memory of, 
 * or relation to, its previous existence. This rule has the following exceptions.
 * <p>
 * 607.2a
 * If an object has an activated or triggered ability printed on it that instructs a player 
 * to exile one or more cards and an ability printed on it that refers either 
 * to “the exiled cards” or to cards “exiled with [this object],” these abilities are linked. 
 * The second ability refers only to cards in the exile zone that were put there as a result 
 * of an instruction to exile them in the first ability.
 *
 * @author JayDi85
 */
public class StewardOfTheHarvestTest extends CardTestPlayerBase {

    @Test
    public void test_GainActivatedAbilities() {
        // When this creature enters, exile up to three target land cards from your graveyard.
        // Creatures you control have all activated abilities of all land cards exiled with this creature.
        addCard(Zone.HAND, playerA, "Steward of the Harvest"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Forest"); // {T}: Add {G}
        addCard(Zone.GRAVEYARD, playerA, "Island"); // {T}: Add {U}
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steward of the Harvest");
        addTarget(playerA, "Forest^Island"); // exile both

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 2);
        assertAbility(playerA, "Grizzly Bears", new GreenManaAbility(), true);
        assertAbility(playerA, "Grizzly Bears", new BlueManaAbility(), true);
        assertAbility(playerA, "Steward of the Harvest", new GreenManaAbility(), true);
        assertAbility(playerA, "Steward of the Harvest", new BlueManaAbility(), true);
        assertAbility(playerB, "Balduvian Bears", new GreenManaAbility(), false);
        assertAbility(playerB, "Balduvian Bears", new BlueManaAbility(), false);
    }

    @Test
    public void test_ExiledLandLeftExile_LoseItsAbilities() {
        // When this creature enters, exile up to three target land cards from your graveyard.
        // Creatures you control have all activated abilities of all land cards exiled with this creature.
        addCard(Zone.HAND, playerA, "Steward of the Harvest"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        // Put target face-up exiled card into its owner's graveyard.
        addCard(Zone.HAND, playerA, "Pull from Eternity"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Forest"); // {T}: Add {G}
        addCard(Zone.GRAVEYARD, playerA, "Island"); // {T}: Add {U}
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 4); // pay Steward by forests only
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steward of the Harvest");
        addTarget(playerA, "Forest^Island"); // exile both
        checkAbility("forest", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", GreenManaAbility.class, true);
        checkAbility("island", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", BlueManaAbility.class, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Pull from Eternity", "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Forest", 1);
        assertAbility(playerA, "Grizzly Bears", new BlueManaAbility(), true);
        assertAbility(playerA, "Grizzly Bears", new GreenManaAbility(), false);
    }

    @Test
    public void test_NewObject_OnlyLandsExiledWithIt() {
        addCustomEffect_TargetBlink(playerA);

        // When this creature enters, exile up to three target land cards from your graveyard.
        // Creatures you control have all activated abilities of all land cards exiled with this creature.
        addCard(Zone.HAND, playerA, "Steward of the Harvest"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Forest"); // {T}: Add {G}
        addCard(Zone.GRAVEYARD, playerA, "Island"); // {T}: Add {U}
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steward of the Harvest");
        addTarget(playerA, "Forest"); // exile with the first object
        addTarget(playerA, TestPlayer.TARGET_SKIP); // only one land
        checkAbility("first object: forest", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", GreenManaAbility.class, true);

        // the forest stays in exile, but it was exiled with the old object
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Steward of the Harvest");
        addTarget(playerA, "Island"); // exile with the new object

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Forest", 1);
        assertExileCount(playerA, "Island", 1);
        assertAbility(playerA, "Grizzly Bears", new BlueManaAbility(), true);
        assertAbility(playerA, "Grizzly Bears", new GreenManaAbility(), false);
    }

    @Test
    public void test_Rollback_OnlyLandsOfRestoredGame() {
        addCustomEffect_TargetBlink(playerA);

        // When this creature enters, exile up to three target land cards from your graveyard.
        // Creatures you control have all activated abilities of all land cards exiled with this creature.
        addCard(Zone.HAND, playerA, "Steward of the Harvest"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Forest"); // {T}: Add {G}
        addCard(Zone.GRAVEYARD, playerA, "Island"); // {T}: Add {U}
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steward of the Harvest");
        addTarget(playerA, "Forest"); // exile with the first object
        addTarget(playerA, TestPlayer.TARGET_SKIP); // only one land
        checkAbility("first object: forest", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", GreenManaAbility.class, true);

        // new object exiles another land
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Steward of the Harvest");
        addTarget(playerA, "Island"); // exile with the new object
        checkAbility("new object: island", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", BlueManaAbility.class, true);
        checkAbility("new object: no forest", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", GreenManaAbility.class, false);

        // back to the start of turn 3: the first object with the forest only
        rollbackTurns(3, PhaseStep.END_TURN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerA, "Island", 1);
        assertAbility(playerA, "Grizzly Bears", new GreenManaAbility(), true);
        assertAbility(playerA, "Grizzly Bears", new BlueManaAbility(), false);
    }
}
