package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Checks that dies triggered ability works when sacrificed
 * Made in an attempt to replicate #12195 issue.
 *
 * @author Susucr, JayDi85
 */
public class SacrificeDiesTriggerTest extends CardTestPlayerBase {

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This test passed 10k times without failure.
    @Test
    public void test_DiesTrigger_ResponseOfSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Footlight Fiend {B/R}
        // Creature — Devil
        // When Footlight Fiend dies, it deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Footlight Fiend", 1);
        // Phyrexian Vault {3}
        // Artifact
        // {2}, {T}, Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Vault", 1);

        setChoice(playerA, "Footlight Fiend");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}");
        addTarget(playerA, playerB); // Fiend trigger

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, "Footlight Fiend", 1);
        assertTappedCount("Phyrexian Vault", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);

        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // Trying to change Su-Chi's zcc to see if that changes anything.
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam_BlinkBefore() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);

        addCard(Zone.HAND, playerA, "Ephemerate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        castSpell(1, PhaseStep.UPKEEP, playerA, "Ephemerate", "Su-Chi");

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // Trying to change Su-Chi's zcc (cast from hand) to see if that changes anything.
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam_BlinkBefore_SameStep() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);

        addCard(Zone.HAND, playerA, "Ephemerate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", "Su-Chi");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // Trying to change Su-Chi's zcc to see if that changes anything.
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam_Cast() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.HAND, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Su-Chi", true);

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // Trying to trigger a potential bug with shortlived lki
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam_FlameSlash_SacInResponse() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.HAND, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);
        addCard(Zone.HAND, playerA, "Flame Slash");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Su-Chi", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Slash", "Su-Chi");

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // Trying to trigger a potential bug with shortlived lki
    // This test passed 10k times without failure.
    @Test
    public void test_SuChi_SageOfLatNam_FlameSlashSage_SacInResponse() {
        setStrictChooseMode(true);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.HAND, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);
        addCard(Zone.HAND, playerA, "Flame Slash");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Su-Chi", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Slash", "Sage of Lat-Nam");

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertGraveyardCount(playerA, "Sage of Lat-Nam", 1);
    }

    // Bug: reports of Dies trigger not triggering on sacrifice.
    // This pair was specifically mentionned to not trigger the Su-Chi trigger.
    // Trying to trigger a potential bug when other triggers are in the state.
    // This test passed 10k times without failure.
    // (tried with 100 triggers 100 times, no fail either, at 500ms each test, didn't feel like going to 10k or add it as regular test)
    @Test
    public void test_SuChi_SageOfLatNam_10OtherTriggers() {
        setStrictChooseMode(true);

        // Whenever a nontoken creature you control dies, create a 1/1 green Saproling creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Golgari Germination", 10);

        // Su-Chi {4}
        // Artifact Creature — Construct
        // When Su-Chi dies, add {C}{C}{C}{C}.
        // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Su-Chi", 1);
        // Sage of Lat-Nam {1}{U}
        // Creature — Human Artificer
        // {T}, Sacrifice an artifact: Draw a card.
        // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Sage of Lat-Nam", 1);
        addCard(Zone.HAND, playerA, "Anvilwrought Raptor"); // Cost {4}

        setChoice(playerA, "Su-Chi");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Whenever a nontoken creature you control dies, create a 1/1 green Saproling creature token.", 10); // stack triggers

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anvilwrought Raptor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Anvilwrought Raptor", 1);
        assertGraveyardCount(playerA, "Su-Chi", 1);
        assertTappedCount("Sage of Lat-Nam", true, 1);
    }

    @Test
    public void test_MultiModesDiesTrigger_ByDamage() {
        addCustomEffect_BlinkTarget(playerA);

        // When Junji, the Midnight Sky dies, choose one —
        // • Each opponent discards two cards and loses 2 life.
        // • Put target non-Dragon creature card from a graveyard onto the battlefield under your control. You lose 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Junji, the Midnight Sky", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        //
        // Govern the Storm deals 5 damage to target creature.
        addCard(Zone.HAND, playerA, "Command the Storm", 1); // {4}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // blink before
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Junji, the Midnight Sky");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // kill junji
        checkStackSize("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Command the Storm", "Junji, the Midnight Sky");
        setModeChoice(playerA, "2"); // choose put target non-dragon
        addTarget(playerA, "Grizzly Bears"); // put to battlefield

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Junji, the Midnight Sky", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_MultiModesDiesTrigger_BySacrificeCost() {
        addCustomEffect_BlinkTarget(playerA);

        // When Junji, the Midnight Sky dies, choose one —
        // • Each opponent discards two cards and loses 2 life.
        // • Put target non-Dragon creature card from a graveyard onto the battlefield under your control. You lose 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Junji, the Midnight Sky", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        //
        // {2}, {T}, Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Vault", 1); // {3}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // blink before
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Junji, the Midnight Sky");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // sacrifice junji
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}, Sacrifice");
        setChoice(playerA, "Junji, the Midnight Sky"); // sacrifice
        setModeChoice(playerA, "2"); // choose put target non-dragon
        addTarget(playerA, "Grizzly Bears"); // put to battlefield

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Junji, the Midnight Sky", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_DiesTriggerWhileMultiStepsEffect_ShortLKI() {
        // see details on shortLKI problems in isInUseableZoneDiesTrigger

        skipInitShuffling();

        // When Junji, the Midnight Sky dies, choose one —
        // • Each opponent discards two cards and loses 2 life.
        // • Put target non-Dragon creature card from a graveyard onto the battlefield under your control. You lose 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Junji, the Midnight Sky", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        //
        // At the beginning of your end step, you may sacrifice a creature. If you do, look at the top X cards of your library,
        // where X is that creature's mana value. You may put a creature card from among them onto the battlefield.
        // Put the rest on the bottom of your library in a random order.
        addCard(Zone.BATTLEFIELD, playerA, "Industrial Advancement", 1);
        addCard(Zone.LIBRARY, playerA, "Augmenting Automaton", 1);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);

        // end step trigger
        setChoice(playerA, "Junji, the Midnight Sky"); // sacrifice on end step
        setChoice(playerA, "Silvercoat Lion"); // put to battlefield
        // dies trigger
        setModeChoice(playerA, "2"); // choose put target non-dragon
        addTarget(playerA, "Grizzly Bears"); // put to battlefield

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // from dies trigger
        assertGraveyardCount(playerA, "Junji, the Midnight Sky", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        // from end step trigger
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    // bug #9688
    @Test
    public void testIndustrialAdvancement() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Industrial Advancement");
        // At the beginning of your end step, you may sacrifice a creature. If you do, look at the top X cards of your
        // library, where X is that creature’s mana value. You may put a creature card from among them onto the
        // battlefield. Put the rest on the bottom of your library in a random order.
        addCard(Zone.BATTLEFIELD, playerA, "Guardian Automaton"); // 3/3 gain 3 life when it dies
        addCard(Zone.LIBRARY, playerA, "Lone Missionary"); // etb gain 4 life
        addCard(Zone.LIBRARY, playerA, "Horned Turtle");
        addCard(Zone.LIBRARY, playerA, "Maritime Guard");
        addCard(Zone.LIBRARY, playerA, "Kraken Hatchling");

        setChoice(playerA, "Guardian Automaton"); // sacrifice on end step
        setChoice(playerA, "Lone Missionary"); // put onto battlefield
        setChoice(playerA, "When {this} dies"); // choose trigger order

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Lone Missionary", 1);
        assertGraveyardCount(playerA, "Guardian Automaton", 1);
        assertLife(playerA, 27);

    }

}
