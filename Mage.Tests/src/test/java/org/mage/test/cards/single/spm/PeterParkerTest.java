package org.mage.test.cards.single.spm;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapAllEffect;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class PeterParkerTest extends CardTestPlayerBase {

    /*
         Peter Parker
         {1}{W}
         Legendary Creature - Human Scientist Hero
         When Peter Parker enters, create a 2/1 green Spider creature token with reach.
         {1}{G}{W}{U}: Transform Peter Parker. Activate only as a sorcery.
         Amazing Spider-Man
         {1}{G}{W}{U}
         Legendary Creature - Spider Human Hero
         Vigilance, reach
         Each legendary spell you cast that's one or more colors has web-slinging {G}{W}{U}.
         4/4
        */
    private static final String peterParker = "Peter Parker";
    public static final String amazingSpiderMan = "Amazing Spider-Man";


    /*
     Absolute Virtue
     {6}{W}{U}
     Legendary Creature - Avatar Warrior
     This spell can't be countered.
     Flying
     You have protection from each of your opponents.
     8/8
    */
    private static final String absoluteVirtue = "Absolute Virtue";

    /*
     Adelbert Steiner
     {1}{W}
     Legendary Creature - Human Knight
     Lifelink
     Adelbert Steiner gets +1/+1 for each Equipment you control.
     2/1
    */
    private static final String adelbertSteiner = "Adelbert Steiner";

    /*
     Chainer, Nightmare Adept
     {2}{B}{R}
     Legendary Creature - Human Minion
     Discard a card: You may cast a creature card from your graveyard this turn. Activate this ability only once each turn.
         Whenever a nontoken creature enters the battlefield under your control, if you didn't cast it from your hand, it gains haste until your next turn.
     3/2
    */
    private static final String chainerNightmareAdept = "Chainer, Nightmare Adept";

    /*
     Balduvian Bears
     {1}{G}
     Creature - Bear

     2/2
    */
    private static final String balduvianBears = "Balduvian Bears";

    /*
    Unsummon
    {U}
    Instant
    Return target creature to its owner's hand.
    */
    private static final String unsummon = "Unsummon";


    @Test
    public void testAmazingSpiderMan() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("tap all creatures", playerA, new SimpleActivatedAbility(
                new TapAllEffect(new FilterCreaturePermanent(SubType.BEAR, "bears")),
                new ManaCostsImpl<>("")
        ));

        addCard(Zone.HAND, playerA, peterParker);
        addCard(Zone.BATTLEFIELD, playerA, chainerNightmareAdept);
        addCard(Zone.BATTLEFIELD, playerA, balduvianBears,2);
        addCard(Zone.HAND, playerA, adelbertSteiner, 2);
        addCard(Zone.GRAVEYARD, playerA, absoluteVirtue);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, amazingSpiderMan, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "tap all"); // tap bears, addCard command isn't working to set tapped
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, adelbertSteiner + " with Web-slinging", true);
        setChoice(playerA, balduvianBears); // return to hand

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card");
        setChoice(playerA, adelbertSteiner); // discard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("Bear does not have web-slinging", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast " + balduvianBears + " with Web-slinging", false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, absoluteVirtue + " with Web-slinging");
        setChoice(playerA, balduvianBears); // return to hand

        setStopAt(1, PhaseStep.END_TURN);
        execute();

    }

    @Test
    public void testTransform() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("tap all creatures", playerA, new SimpleActivatedAbility(
                new TapAllEffect(new FilterCreaturePermanent(SubType.BEAR, "bears")),
                new ManaCostsImpl<>("")
        ));

        addCard(Zone.HAND, playerA, peterParker);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 8);
        addCard(Zone.BATTLEFIELD, playerA, balduvianBears);
        addCard(Zone.HAND, playerA, adelbertSteiner);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "tap all"); // tap bears, addCard command isn't working to set tapped
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, peterParker, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{G}{W}{U}: Transform");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, adelbertSteiner + " with Web-slinging");
        setChoice(playerA, balduvianBears); // return to hand

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, amazingSpiderMan, 1);
        assertPermanentCount(playerA, adelbertSteiner, 1);
        assertPermanentCount(playerA, balduvianBears, 0);
        assertHandCount(playerA, balduvianBears, 1);
    }

    /**
     * test that MDFC doesn't have static ability from one side after transforming
     */
    @Test
    public void testTransformLosesWebSlinging() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("tap all creatures", playerA, new SimpleActivatedAbility(
                new TapAllEffect(new FilterCreaturePermanent(SubType.BEAR, "bears")),
                new ManaCostsImpl<>("")
        ));
        addCustomEffect_TargetTransform(playerA);

        addCard(Zone.HAND, playerA, peterParker);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 8);
        addCard(Zone.BATTLEFIELD, playerA, balduvianBears);
        addCard(Zone.HAND, playerA, adelbertSteiner);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "tap all"); // tap bears, addCard command isn't working to set tapped
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, peterParker, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{G}{W}{U}: Transform"); // transform to Spider-Man
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("card in hand has web-slinging", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + adelbertSteiner + " with Web-slinging", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", amazingSpiderMan);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("card in hand does not have web-slinging", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + adelbertSteiner + " with Web-slinging", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, amazingSpiderMan, 0);
        assertPermanentCount(playerA, peterParker, 1);
        assertHandCount(playerA, adelbertSteiner, 1);
        assertPermanentCount(playerA, balduvianBears, 1);
    }

    /**
     * test showing if a transformed MDFC gets re-cast, it won't trigger effects from the other face
     */
    @Test
    public void testTransformCastSecondSideDoesntTriggerFront() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("tap all creatures", playerA, new SimpleActivatedAbility(
                new TapAllEffect(new FilterCreaturePermanent(SubType.BEAR, "bears")),
                new ManaCostsImpl<>("")
        ));
        addCustomEffect_TargetTransform(playerA);

        addCard(Zone.HAND, playerA, peterParker);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 8);
        addCard(Zone.BATTLEFIELD, playerA, balduvianBears);
        addCard(Zone.HAND, playerA, adelbertSteiner);
        addCard(Zone.HAND, playerA, unsummon);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "tap all"); // tap bears, addCard command isn't working to set tapped
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, amazingSpiderMan, true);

        checkPlayableAbility("card in hand has web-slinging", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + adelbertSteiner + " with Web-slinging", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", amazingSpiderMan);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("card in hand does not have web-slinging", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + adelbertSteiner + " with Web-slinging", false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, unsummon, peterParker, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, amazingSpiderMan);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, amazingSpiderMan, 1);
        assertPermanentCount(playerA, peterParker, 0);
        assertHandCount(playerA, adelbertSteiner, 1);
        assertPermanentCount(playerA, balduvianBears, 1);
        assertPermanentCount(playerA, "Spider Token", 0);
        currentGame.getState().getTriggers().forEach(
                (key, value) -> logger.info(key + " - " + value)
        );
    }
}