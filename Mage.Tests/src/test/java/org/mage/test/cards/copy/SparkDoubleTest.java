package org.mage.test.cards.copy;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SparkDoubleTest extends CardTestPlayerBase {

    private Permanent findDoubleSparkPermanent(Game game) {
        for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
            if (perm.isCopy()) {
                return perm;
            }
        }
        Assert.fail("spark must exist");
        return null;
    }

    @Test
    public void test_CopyCreatureAndGetOneCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin", 1); // 2/2, fly, vig
        //
        addCard(Zone.HAND, playerA, "Spark Double"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Abbey Griffin");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Abbey Griffin", 2);

        Permanent spark = findDoubleSparkPermanent(currentGame);
        Assert.assertEquals("must add 1 counter", 1, spark.getCounters(currentGame).getCount(CounterType.P1P1));
        //
        Assert.assertEquals("must copy p/t", 3, spark.getPower().getValue());
        Assert.assertEquals("must copy p/t", 3, spark.getToughness().getValue());
        Assert.assertTrue("must copy ability", spark.getAbilities().contains(VigilanceAbility.getInstance()));
    }

    @Test
    public void test_CopyPlaneswalkerWithoutLegendaryWithOneCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Ajani, the Greathearted", 1);
        //
        addCard(Zone.HAND, playerA, "Spark Double"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Ajani, the Greathearted");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Ajani, the Greathearted", 2);

        Permanent spark = findDoubleSparkPermanent(currentGame);
        Assert.assertEquals("must add 1 loyalty", 5 + 1, spark.getCounters(currentGame).getCount(CounterType.LOYALTY));
    }

    @Test
    public void test_CopyCreatureAndGetDoubleCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin", 1); // 2/2, fly, vig
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season", 1);
        //
        addCard(Zone.HAND, playerA, "Spark Double"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Abbey Griffin");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Abbey Griffin", 2);

        Permanent spark = findDoubleSparkPermanent(currentGame);
        Assert.assertEquals("must add 2 counter", 2, spark.getCounters(currentGame).getCount(CounterType.P1P1));
    }

    @Test
    public void test_CopyPlaneswalkerWithCreatureActivated() {
        addCard(Zone.BATTLEFIELD, playerA, "Gideon, Ally of Zendikar", 1);
        //
        addCard(Zone.HAND, playerA, "Spark Double"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // activate creature ability
        checkType("planeswalker not creature", 1, PhaseStep.UPKEEP, playerA, "Gideon, Ally of Zendikar", CardType.CREATURE, false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        checkType("planeswalker is creature", 1, PhaseStep.BEGIN_COMBAT, playerA, "Gideon, Ally of Zendikar", CardType.CREATURE, true);

        // copy
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Gideon, Ally of Zendikar");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Gideon, Ally of Zendikar", 2);

        Permanent spark = findDoubleSparkPermanent(currentGame);
        Assert.assertEquals("must add 1 loyalty", 4 + 1, spark.getCounters(currentGame).getCount(CounterType.LOYALTY));
        Assert.assertEquals("must not add creature counter", 0, spark.getCounters(currentGame).getCount(CounterType.P1P1));
    }

    @Test
    @Ignore // TODO: enabled after Blood Moon type changing effect will be fixed
    public void test_CopyPlaneswalkerWithCreatureTypeChangedEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Gideon Blackblade", 1);
        //
        addCard(Zone.HAND, playerA, "Spark Double"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Gideon Blackblade is creature on your turn (by type changing effect)
        checkType("planeswalker is creature", 1, PhaseStep.UPKEEP, playerA, "Gideon Blackblade", CardType.CREATURE, true);

        // copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Gideon Blackblade");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Gideon Blackblade", 2);

        Permanent spark = findDoubleSparkPermanent(currentGame);
        Assert.assertEquals("must add 1 loyalty", 4 + 1, spark.getCounters(currentGame).getCount(CounterType.LOYALTY));
        Assert.assertEquals("must add 1 creature counter", 1, spark.getCounters(currentGame).getCount(CounterType.P1P1));
    }

    @Test
    public void test_CopyOfSparksCopy_BySpell() {

        /*
        Spark Double isn’t legendary if it copies a legendary permanent, and this exception is copiable.
        If something else copies Spark Double later, that copy also won’t be legendary.
        If you control two or more permanents with the same name but only one is legendary, the “legend rule” doesn’t apply. (2019-05-03)

        it's applier copy check
         */
        //
        addCard(Zone.HAND, playerA, "Spark Double"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Akroma, Angel of Wrath", 1); // legendary
        //
        // Create a 1/1 white Bird creature token with flying, then populate. (Create a token that’s a copy of a creature token you control.)
        addCard(Zone.HAND, playerA, "Eyes in the Skies"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        // Create a token that’s a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Quasiduplicate"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // make copy of legendary creature (it's not legendary now)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Akroma, Angel of Wrath");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("must have copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Wrath", 2);

        // make copy of copy by CreateTokenCopyTargetEffect
//        showBattlefield("before last copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Quasiduplicate");
        addTarget(playerA, "Akroma, Angel of Wrath[only copy]");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("must have copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Wrath", 3);

//        showBattlefield("after all", 1, PhaseStep.BEGIN_COMBAT, playerA);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_CopyOfSparksCopy_ByAbility() {
        Ability ability = new SimpleActivatedAbility(new CreateTokenCopyTargetEffect(), new ManaCostsImpl(""));
        ability.addTarget(new TargetPermanent());
        addCustomCardWithAbility("copy", playerA, ability);

        addCard(Zone.HAND, playerA, "Spark Double"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Akroma, Angel of Wrath", 1); // legendary
        //
        // Create a 1/1 white Bird creature token with flying, then populate. (Create a token that’s a copy of a creature token you control.)
        addCard(Zone.HAND, playerA, "Eyes in the Skies"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // make copy of legendary creature (it's not legendary now)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Akroma, Angel of Wrath");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("must have copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Wrath", 2);

        // make copy of copy by CreateTokenCopyTargetEffect
        // showBattlefield("before last copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // showAvaileableAbilities("before last copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "create a token that");
        addTarget(playerA, "Akroma, Angel of Wrath[only copy]");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("must have copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Wrath", 3);

        // showBattlefield("after all", 1, PhaseStep.BEGIN_COMBAT, playerA);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        assertAllCommandsUsed();
    }

}
