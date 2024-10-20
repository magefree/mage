package org.mage.test.cards.digital;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

public class PerpetuallyTest extends CardTestCommander3PlayersFFA {

    private static final String familiar = "Plaguecrafter's Familiar";
    private static final String charger = "Veteran Charger";
    private static final String defenses = "Baffling Defenses";
    private static final String simpleCard = "Goblin Striker";
    private static final String gigantosaurus = "Gigantosaurus";

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderDuel_UW.dck"); // Commander = Daxos of Meletis
        return super.createNewGameAndPlayers();
    }

    /**
     * Perpetually effects should be constantly applied in any zone
     * 1. gaining 2 singleton abilities (should stay one)
     * 2. changing PT boosted value
     * 3. setting base power value
     */
    // Gaining 2 singleton abilities (should stay one) - permanent test
    @Test
    public void testGainingSingletonAbilities() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        addCard(Zone.HAND, playerA, familiar, 2);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, "Reanimate");
        addCard(Zone.HAND, playerA, simpleCard);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, simpleCard);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", simpleCard, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, simpleCard, DeathtouchAbility.class,  1);

    }

    // Gaining ability - card test
    @Test
    public void testGainingAbilityForCard() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.HAND, playerA, "Mwonvuli Beast Tracker");
        addCard(Zone.HAND, playerA, "Sudden Setback");
        addCard(Zone.HAND, playerA, simpleCard);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Setback", simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mwonvuli Beast Tracker");
        addTarget(playerA, simpleCard);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // verify that Beast Tracker put Goblin Striker card on the top of the library after shuffling
        Card topCard = playerA.getLibrary().getFromTop(currentGame);
        Assert.assertEquals(topCard.getName(), simpleCard);
    }

    // Changing PT boosted value - permanent test
    @Test
    public void testChangingPTBoostedValue() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, "Reanimate");
        addCard(Zone.HAND, playerA, simpleCard);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", simpleCard, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, simpleCard, 3, 3);

    }

    // Changing PT boosted value - card test
    @Test
    public void testChangingPTBoostedValueForCard() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Gigantosaurus");

        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, "Corpse Explosion");
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, simpleCard);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Corpse Explosion", true);
        setChoice(playerA, simpleCard);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, simpleCard, 1);
        assertDamageReceived(playerA, gigantosaurus, 3);

    }

    // Setting base power value - permanent test
    @Test
    public void testSettingBasePTValue() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        addCard(Zone.HAND, playerA, defenses);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, "Reanimate");
        addCard(Zone.HAND, playerA, simpleCard);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, defenses, simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", simpleCard, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, simpleCard, 0, 1);
        assertBasePowerToughness(playerA, simpleCard, 0, 1);

    }

    // Setting PT in library - card test
    @Test
    public void testSettingPTForCard() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.BATTLEFIELD, playerA, gigantosaurus);
        addCard(Zone.BATTLEFIELD, playerB, simpleCard);

        addCard(Zone.HAND, playerA, defenses);
        addCard(Zone.HAND, playerA, "Sudden Setback");
        addCard(Zone.HAND, playerA, "Imperial Recruiter");
        skipInitShuffling();

        // set Gigantosaurus base power to 0
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, defenses, gigantosaurus, true);

        // put Gigantosaurus on the top of deck
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Setback", gigantosaurus, true);

        // search for Gigantosaurus as for creature with power 2 or less
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Imperial Recruiter");
        addTarget(playerA, gigantosaurus);

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertHandCount(playerA, gigantosaurus, 1);

    }

    /**
     * Perpetually added abilities and boosts of power/toughness should be able to stack
     */
    private static final String bond = "Painful Bond";

    @Test
    public void testGainMultipleAbilities() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.HAND, playerA, bond, 2);
        addCard(Zone.HAND, playerA, simpleCard);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bond);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bond, true); // stack x2
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, "Unearth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true); // lose 2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", simpleCard, true); // lose 2
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth", simpleCard, true); // lose 2

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, simpleCard, CastSourceTriggeredAbility.class,  2);
        assertLife(playerA, 34);
        
    }

    @Test
    public void testGainMultiplePTBoosts() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);

        addCard(Zone.HAND, playerA, charger, 2);
        addCard(Zone.HAND, playerA, simpleCard);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, "Unearth");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true); // +2/+2
        setChoice(playerA, simpleCard);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true); // +2/+2
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true); // 5/5

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", simpleCard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth", simpleCard, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, simpleCard, 5, 5);
        assertBasePowerToughness(playerA, simpleCard, 1, 1);

    }

    /**
     * Perpetual effects behaviour is mostly defined by continuous effects rules
     * 1. Perpetual effects apply in their usual layers
     * 2. Perpetual effects don't change copiable values
     *
     */

    // Perpetual effects apply in their usual layers
    // 1) Creature perpetually gains deathtouch. 2) Humility makes creature losing all abilities, overriding perpetual effect.
    @Test
    public void testLoseAbilitiesAfterGaining() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);

        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.HAND, playerA, simpleCard);
        addCard(Zone.HAND, playerA, "Humility");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Humility");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, simpleCard, DeathtouchAbility.class, 0);

    }

    // 1) Humility enters. 2) Creature enters and loses abilities. 3) Creature gains abilities from Ethereal Grasp.
    // 4) Ethereal Grasp abilities are present because of overriding Humility effect - timestamp order.
    @Test
    public void testGainAbilityAfterLosing() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.HAND, playerA, simpleCard);
        addCard(Zone.HAND, playerA, "Ethereal Grasp");
        addCard(Zone.HAND, playerA, "Humility");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Humility", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", simpleCard, true);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, simpleCard, SimpleActivatedAbility.class, 1);

    }

    // Perpetual effects don't change copiable values.
    @Test
    public void testDontChangeCopiableValues() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);


        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, simpleCard);
        addCard(Zone.HAND, playerA, "Ethereal Grasp");
        addCard(Zone.HAND, playerA, "Cackling Counterpart");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, simpleCard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", simpleCard, true);


        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Permanent token = currentGame.getBattlefield().getAllPermanents().stream()
                .filter(permanent -> permanent instanceof PermanentToken)
                .collect(Collectors.toList()).get(0);

        Assert.assertEquals(1, token.getPower().getValue());
        Assert.assertEquals(1, token.getToughness().getValue());
        Assert.assertFalse(token.hasAbility(
                new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new GenericManaCost(8)), currentGame));

    }

    /**
     * Perpetual effects tests for specific card types
     *
     */

    // Adventure cards test.
    // Perpetual effects affect both sides of adventure cards.

    @Test
    public void testAdventureBothSidesAffected() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);


        addCard(Zone.HAND, playerA, bond);
        addCard(Zone.HAND, playerA, "Mosswood Dreadknight");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bond, true);

        // lose 1 life from Dread Whispers and 1 life from Painful Bond
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dread Whispers", true);

        // lose 1 life from Painful Bond
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mosswood Dreadknight", true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 37);

    }

    // Aftermath cards test.
    // Perpetual effects affect both sides of aftermath cards.

    @Test
    public void testAftermathBothSidesAffected() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bloodsprout Talisman");

        addCard(Zone.HAND, playerA, "Driven // Despair");

        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay 1 life: Choose a nonland card in your hand. It perpetually gains \"This spell costs {1} less to cast.\"");
        setChoice(playerA, "Driven // Despair");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Driven", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Despair", true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Driven // Despair", 1);
    }

    // Craft cards test.
    // Only perpetual effects that affect craft card are applied to the crafted card.
    @Test
    public void testCraftCard() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.HAND, playerA, simpleCard);
        addCard(Zone.HAND, playerA, "Tithing Blade");
        addCard(Zone.HAND, playerA, "Pull of the Mist Moon");
        addCard(Zone.HAND, playerA, "Ethereal Grasp");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", simpleCard, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pull of the Mist Moon");
        setChoice(playerA, true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setChoice(playerA, "Tithing Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tithing Blade",true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft with creature {4}{B}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Consuming Sepulcher", SimpleActivatedAbility.class, 0);
        assertAbilityCount(playerA, "Consuming Sepulcher", EntersBattlefieldTriggeredAbility.class, 1);

    }
    // Transform cards test.
    // Perpetual effects affect both sides of transform cards.
    @Test
    public void testTransformFaceAffected() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);


        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, "Weaver of Blossoms");
        addCard(Zone.HAND, playerA, "Ethereal Grasp");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Weaver of Blossoms");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Weaver of Blossoms", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", "Weaver of Blossoms", true);


        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Weaver of Blossoms", SimpleActivatedAbility.class, 1);
        assertPowerToughness(playerA, "Weaver of Blossoms", 4, 5);

    }

    @Test
    public void testTransformBackAffected() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);


        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, "Weaver of Blossoms");
        addCard(Zone.HAND, playerA, "Ethereal Grasp");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Weaver of Blossoms");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Weaver of Blossoms", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", "Weaver of Blossoms", true);

        // there's night on the 3rd turn
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Blossom-Clad Werewolf", SimpleActivatedAbility.class, 1); // transformed version
        assertPowerToughness(playerA, "Blossom-Clad Werewolf", 5, 6);

    }

    // Meld cards test.
    // 1. Melded card becomes the target of all perpetual effects that affected its meld pair.
    // 2. When melded card goes into any zone except of battlefield, it becomes two cards. These cards have no perpetual effects.

    // 1. Melded card becomes the target of all perpetual effects that affected its meld pair.
    @Test
    public void testMeldedCardCombinesPerpetualEffects() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 15);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);


        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, "Ethereal Grasp");
        addCard(Zone.HAND, playerA, "Mishra, Claimed by Gix");
        addCard(Zone.HAND, playerA, "Phyrexian Dragon Engine");
        addCard(Zone.HAND, playerA, "Burst of Speed");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Mishra, Claimed by Gix");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mishra, Claimed by Gix", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Dragon Engine", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", "Phyrexian Dragon Engine", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{8}: Untap {this}.");


        // meld cards into Mishra, Lost to Phyrexia
        attack(4, playerA, "Phyrexian Dragon Engine", playerB);
        attack(4,  playerA, "Mishra, Claimed by Gix", playerB);
        setChoice(playerA, "Phyrexian Dragon Engine");
        setModeChoice(playerA, "4");
        setModeChoice(playerA, "5");
        setModeChoice(playerA, "6");
        waitStackResolved(4, PhaseStep.DECLARE_ATTACKERS);

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Mishra, Lost to Phyrexia", SimpleActivatedAbility.class, 1);
        assertPowerToughness(playerA, "Mishra, Lost to Phyrexia", 11, 11);
    }

    // 2. When melded card goes into any zone except of battlefield, it becomes two cards. These cards have no perpetual effects.
    @Test
    public void testMeldedCardRemoveEffect() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 15);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);


        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.HAND, playerA, "Ethereal Grasp");
        addCard(Zone.HAND, playerA, "Mishra, Claimed by Gix");
        addCard(Zone.HAND, playerA, "Phyrexian Dragon Engine");
        addCard(Zone.HAND, playerA, "Burst of Speed");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Mishra, Claimed by Gix");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mishra, Claimed by Gix", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Dragon Engine", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", "Phyrexian Dragon Engine", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{8}: Untap {this}.");


        // meld cards into Mishra, Lost to Phyrexia
        attack(4, playerA, "Phyrexian Dragon Engine", playerB);
        attack(4,  playerA, "Mishra, Claimed by Gix", playerB);
        setChoice(playerA, "Phyrexian Dragon Engine");
        setModeChoice(playerA, "4");
        setModeChoice(playerA, "5");
        setModeChoice(playerA, "6");
        waitStackResolved(4, PhaseStep.DECLARE_ATTACKERS);

        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Unsummon", "Mishra, Lost to Phyrexia", true);

        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mishra, Claimed by Gix", true);

        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Phyrexian Dragon Engine", true);

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Phyrexian Dragon Engine", SimpleActivatedAbility.class, 0);
        assertPowerToughness(playerA, "Mishra, Claimed by Gix", 3, 5);
    }

    // Disturb cards test.
    // Perpetual effects affect both sides of disturb cards.
    @Test
    public void testDisturbKeepsEffects() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);


        addCard(Zone.HAND, playerA, charger);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, "Lunarch Veteran");
        addCard(Zone.HAND, playerA, "Ethereal Grasp");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Lunarch Veteran");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lunarch Veteran", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", "Lunarch Veteran", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Lunarch Veteran", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Luminous Phantom using Disturb");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Luminous Phantom", SimpleActivatedAbility.class, 1);
        assertPowerToughness(playerA, "Luminous Phantom", 3, 3);

    }

    // Morph (disguise, etc.) card tests.
    // Perpetual effects applied to the card in any zone are hidden when card is cast as morph.
    // Perpetual effects applied to the morphed permanent affect both sides.

    // Test hidden ability not to show and disguise permanent ability to show
    @Test
    public void testMorphGainAbilityMorph() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.HAND, playerA, "Unyielding Gatekeeper");
        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.HAND, playerA, "Ethereal Grasp");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, "Unyielding Gatekeeper");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unyielding Gatekeeper using Disguise", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), SimpleActivatedAbility.class, 1);
        assertAbilityCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), DeathtouchAbility.class, 0);
    }

    // Test hidden ability and disguise permanent ability to show after turning face up
    @Test
    public void testMorphGainAbilityCard() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.HAND, playerA, "Unyielding Gatekeeper");
        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.HAND, playerA, "Ethereal Grasp");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, "Unyielding Gatekeeper");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unyielding Gatekeeper using Disguise", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Grasp", EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Turn this face-down permanent face up.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Unyielding Gatekeeper", SimpleActivatedAbility.class, 1);
        assertAbilityCount(playerA, "Unyielding Gatekeeper", DeathtouchAbility.class, 1);
    }

    // Test hidden boost not to show
    @Test
    public void testMorphBoostPTMorph() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.HAND, playerA, "Unyielding Gatekeeper");
        addCard(Zone.HAND, playerA, charger);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Unyielding Gatekeeper");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unyielding Gatekeeper using Disguise", true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 2, 2);
    }

    // Test hidden boost to show
    @Test
    public void testMorphBoostPTCard() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.HAND, playerA, "Unyielding Gatekeeper");
        addCard(Zone.HAND, playerA, charger);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Unyielding Gatekeeper");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unyielding Gatekeeper using Disguise", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Turn this face-down permanent face up.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Unyielding Gatekeeper", 5, 4);

    }

    /**
     * Perpetual effects additional rules for Brawl (Freeform Commander here)
     *
     */

    // Perpetual effects on commander can be kept in command zone
    @Test
    public void testCommanderKeepsPerpetualEffects() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.HAND, playerA, charger);

        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Daxos of Meletis");
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, "Daxos of Meletis");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Daxos of Meletis");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Daxos of Meletis", true);
        setChoice(playerA, true);
        setChoice(playerA, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis", true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Daxos of Meletis", DeathtouchAbility.class, 1);
        assertPowerToughness(playerA, "Daxos of Meletis", 4, 4);

    }

    // Perpetual effects on commander can be cleared in command zone
    @Test
    public void testCommanderClearsPerpetualEffects() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.HAND, playerA, charger);

        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Daxos of Meletis");
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar, true);
        setChoice(playerA, "Daxos of Meletis");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, charger, true);
        setChoice(playerA, "Daxos of Meletis");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Daxos of Meletis", true);
        setChoice(playerA, true);
        setChoice(playerA, false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis", true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Daxos of Meletis", DeathtouchAbility.class, 0);
        assertPowerToughness(playerA, "Daxos of Meletis", 2, 2);

    }

}
