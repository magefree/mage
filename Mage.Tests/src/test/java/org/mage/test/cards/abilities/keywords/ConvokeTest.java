package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class ConvokeTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Playable_NoMana_NoConvoke() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Stoke the Flames", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_Mana_NoConvoke() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Stoke the Flames", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_NoMana_Convoke() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 4); // convoke pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Stoke the Flames", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_Mana_Convoke() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 2); // convoke pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Stoke the Flames", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_ManaPartly_ConvokePartly() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 2 - 1); // convoke pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Stoke the Flames", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_PlayConvoke_Manual() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 2); // convoke pay

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stoke the Flames", playerB);
        setChoice(playerA, "Red"); // pay 1
        setChoice(playerA, "Red"); // pay 2
        setChoice(playerA, "Convoke");
        addTarget(playerA, "Goblin Racketeer^Goblin Racketeer"); // pay 3 and 4 as convoke

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_PlayConvoke_Manual_TwoInstances() {
        // 702.51d "Multiple instances of convoke on the same spell are redundant."
        // {2}{R}{R}, convoke
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul", 1); // {T}: The next spell you cast this turn has convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 2);

        disableManaAutoPayment(playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stoke the Flames", playerB);
        setChoice(playerA, "Red", 2);
        setChoice(playerA, "Convoke"); // one Convoke action pays the {2}, though the spell has convoke twice
        addTarget(playerA, "Goblin Racketeer^Goblin Racketeer");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertTappedCount("Goblin Racketeer", true, 2);
    }

    @Test
    public void test_PlayConvoke_AI_AutoPay() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 2); // convoke pay

        // AI must use special actions to pay as convoke
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stoke the Flames", playerB);

        setStrictChooseMode(false); // AI must choose targets
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_PlayConvoke_AI_AutoPayAsConvoke() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 2); // convoke pay

        // AI must use special actions to pay as convoke
        // Current version uses special mana pay as last, after no normal mana available (it can be changed in the future, see playManaHandling)
        // e.g. it must tap lands 2 times and convoke 2 times
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stoke the Flames", playerB);
        addTarget(playerA, "Goblin Racketeer"); // pay 1 as convoke
        addTarget(playerA, "Goblin Racketeer"); // pay 2 as convoke

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_PlayConvoke_AI_TapsLeastValuableCreatures() {
        // {2}{R}{R}
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 4); // convoke pay
        addCard(Zone.BATTLEFIELD, playerA, "Shivan Dragon", 2); // can pay too, but worth keeping untapped

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stoke the Flames", playerB);

        setStrictChooseMode(false); // AI must choose targets
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertTappedCount("Goblin Racketeer", true, 4);
        assertTappedCount("Shivan Dragon", false, 2);
    }

    @Test
    public void test_PlayConvoke_AI_FullPlay() {
        // {2}{R}{R}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerA, "Stoke the Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Racketeer", 2); // convoke pay

        // AI must use special actions to pay as convoke and play card
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_Other_ConvokeTwoCreatures() {
        // {1}{W}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for or one mana of that creature's color.)
        // Target creature gains indestructible until end of turn. (Damage and effects that say "destroy" don't destroy it.)
        addCard(Zone.HAND, playerA, "Ephemeral Shields");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Oreskos Swiftclaw", 1);

        // AI automaticly use convoke to pay
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemeral Shields", "Silvercoat Lion");
        addTarget(playerA, "Silvercoat Lion"); // pay 1 as convoke
        addTarget(playerA, "Oreskos Swiftclaw"); // pay 2 as convoke

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Ephemeral Shields", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Oreskos Swiftclaw", 1);
    }


    @Test
    public void test_Other_ConvokeProtection() {
        // {1}{W}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for or one mana of that creature's color.)
        // Target creature gains indestructible until end of turn. (Damage and effects that say "destroy" don't destroy it.)
        addCard(Zone.HAND, playerA, "Ephemeral Shields");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Protection from white
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 1);

        // convoke must be able to target card with protection (it's no target)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemeral Shields", "Silvercoat Lion");
        addTarget(playerA, "Silvercoat Lion");
        addTarget(playerA, "Black Knight");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_Other_ConvokeAsGains() {
        // {1}{U}
        // Artifact spells you cast have convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Chief Engineer", 1);
        //
        // {2}
        addCard(Zone.HAND, playerA, "Alpha Myr", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        // Chief Engineer gives convoke to Alpha Myr and xmage must see it as playable before put real spell to stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Myr");
        addTarget(playerA, "Silvercoat Lion");
        addTarget(playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Alpha Myr", 1);
    }

    @Test
    public void test_Other_CantUseConvokeBeforeManaAbilities() {
        // https://github.com/magefree/mage/issues/768
        // Mana abilities come before convoke, so a Scion tapped for convoke can't then be sacrificed for mana.

        // {6}
        // Convoke
        addCard(Zone.HAND, playerA, "Will-Forged Golem", 1);
        //
        // {2}{G}
        // Create two 1/1 colorless Eldrazi Scion creature tokens. They have “Sacrifice this creature: Add {C}.”
        addCard(Zone.HAND, playerA, "Call the Scions", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3 * 2);

        // prepare scions
        disableManaAutoPayment(playerA);
        for (int i = 0; i < 2; i++) {
            activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 3);
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Call the Scions");
            setChoice(playerA, "Green", 3);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        }
        checkPermanentCount("scions", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eldrazi Scion Token", 4);

        // playable, because the playable check doesn't know that convoke must come last
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Will-Forged Golem");
        setChoice(playerA, "Convoke");
        addTarget(playerA, "Eldrazi Scion Token^Eldrazi Scion Token^Eldrazi Scion Token^Eldrazi Scion Token"); // pay 1-4
        setChoice(playerA, "Sacrifice {this}: Add {C}"); // too late for a mana ability
        setChoice(playerA, TestPlayer.SKIP_FAILED_COMMAND);
        setChoice(playerA, TestPlayer.MANA_CANCEL); // nothing can pay the {2} that is left
        setChoice(playerA, TestPlayer.SKIP_FAILED_COMMAND); // remove the failed cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Will-Forged Golem", 1);
        assertPermanentCount(playerA, "Eldrazi Scion Token", 4);
        assertTappedCount("Eldrazi Scion Token", false, 4);
    }

    @Test
    public void test_Other_CastFromGraveayrd_Convoke() {
        // https://github.com/magefree/mage/issues/6680

        // {5}{B/G}{B/G}
        // You can't spend mana to cast this spell.
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        // You may cast Hogaak, Arisen Necropolis from your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Hogaak, Arisen Necropolis", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hogaak, Arisen Necropolis");
        addTarget(playerA, "Balduvian Bears", 7); // convoke pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hogaak, Arisen Necropolis", 1);
    }

    @Test
    public void test_Other_CastFromGraveayrd_ConvokeAndDelve() {
        // https://github.com/magefree/mage/issues/6680

        // {5}{B/G}{B/G}
        // You can't spend mana to cast this spell.
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        // You may cast Hogaak, Arisen Necropolis from your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Hogaak, Arisen Necropolis", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2); // convoke (you can't pay normal mana here)
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 5); // delve

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hogaak, Arisen Necropolis");
        addTarget(playerA, "Balduvian Bears", 2); // convoke pay
        setChoice(playerA, "Balduvian Bears", 5); // delve pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hogaak, Arisen Necropolis", 1);
    }

    @Test
    public void test_Mana_MemoryOverflow() {
        // possible bug: convoke mana calculation can overflow server's memory (too much mana options from too much permanents)
        // https://github.com/magefree/mage/issues/6938

        // Create X 1/1 white Soldier creature tokens with lifelink.
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        addCard(Zone.HAND, playerA, "March of the Multitudes", 1); // {X}{G}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 500);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Multitudes");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Grizzly Bears"); // convoke pay

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 1);
    }
    @Test
    public void test_AncientImperiosaur_Convoke() {
        // Ancient Imperiosaur {5}{G}{G}
        // Convoke, Trample, ward {2}
        // Ancient Imperiosaur enters the battlefield with two +1/+1 counters on it for each creature that convoked it.
        // 6/6
        addCard(Zone.HAND, playerA, "Ancient Imperiosaur", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancient Imperiosaur");
        addTarget(playerA, "Grizzly Bears", 6);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA,"Ancient Imperiosaur",18,18);
    }
    @Test
    public void test_Copy_Counters_Convoke() {
        // Venerated Loxodon {4}{W}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // When Venerated Loxodon enters the battlefield, put a +1/+1 counter on each creature that convoked it.

        //Copying a spell on the stack copies the costs paid, so both the original and the copy should add counters
        addCard(Zone.HAND, playerA, "Venerated Loxodon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);

        addCard(Zone.HAND, playerA, "Double Major", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.HAND, playerB, "Hideous Laughter", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Venerated Loxodon");
        setChoice(playerA, "White", 3); // pay WWW
        setChoice(playerA, "Convoke");
        addTarget(playerA, "Memnite^Grizzly Bears"); // pay 4 and 5 as convoke

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Venerated Loxodon");
        setChoice(playerA, "Blue", 1);
        setChoice(playerA, "Green", 1);

        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Hideous Laughter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertGraveyardCount(playerA,"Memnite",1);
        assertGraveyardCount(playerA,"Grizzly Bears",1);
        assertPowerToughness(playerA,"Memnite", 1, 1);
        assertPowerToughness(playerA,"Grizzly Bears", 2, 2);
        assertPermanentCount(playerA,"Venerated Loxodon",2);
    }
    @Test
    public void test_Clone_Counters_Convoke() {
        // Venerated Loxodon {4}{W}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature’s color.)
        // When Venerated Loxodon enters the battlefield, put a +1/+1 counter on each creature that convoked it.

        // Cloning a creature on the battlefield should not add counters
        addCard(Zone.HAND, playerA, "Venerated Loxodon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Zone.HAND, playerA, "Clone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.HAND, playerB, "Hideous Laughter", 1);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Venerated Loxodon");
        setChoice(playerA, "White", 3); //Pay WWW
        setChoice(playerA, "Convoke");
        addTarget(playerA, "Memnite^Grizzly Bears"); // pay 4 and 5 as convoke

        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Blue", 4);
        setChoice(playerA,true);
        setChoice(playerA,"Venerated Loxodon");

        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Hideous Laughter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertGraveyardCount(playerA,"Memnite",2);
        assertGraveyardCount(playerA,"Grizzly Bears",1);
        assertPowerToughness(playerA,"Grizzly Bears", 1, 1);
        assertPermanentCount(playerA,"Venerated Loxodon",2);
    }

    @Test
    public void test_PlayConvoke_MonoHybrid() {
        // {2/W}{2/W}{2/W}
        // Create three 1/1 white Spirit creature tokens with flying.
        addCard(Zone.HAND, playerA, "Spectral Procession", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul", 1); // {T}: The next spell you cast this turn has convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spectral Procession");
        // a Memnite finishes the {2/W} the Mountain started, the Lion pays one by color, two Memnites pay one as {2}
        addTarget(playerA, "Memnite^Silvercoat Lion^Memnite^Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 3);
        assertTapped("Mountain", true);
        assertTapped("Silvercoat Lion", true);
        assertTappedCount("Memnite", true, 3);
    }

    @Test
    public void test_PlayConvoke_Manual_LeavesHybridUnpaid() {
        // {G}{G/W}{W}
        addCard(Zone.HAND, playerA, "Trostani, Three Whispers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Selesnya Guildmage", 1); // green and white

        disableManaAutoPayment(playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trostani, Three Whispers");
        setChoice(playerA, "Convoke");
        // picked first, the Guildmage still pays {W} so the Bears can pay {G}, leaving the {G/W} for the second Bears
        addTarget(playerA, "Selesnya Guildmage^Grizzly Bears");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, "Convoke");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trostani, Three Whispers", 1);
        assertTapped("Selesnya Guildmage", true);
        assertTappedCount("Grizzly Bears", true, 2);
    }

    @Test
    public void test_PlayConvoke_ColorlessHybrid() {
        // {C/W}{C/U}{C/B}{C/R}{C/G}
        addCard(Zone.HAND, playerA, "Ulalek, Fused Atrocity", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Walking Corpse", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ulalek, Fused Atrocity");
        addTarget(playerA, "Silvercoat Lion^Merfolk of the Pearl Trident^Walking Corpse^Raging Goblin^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ulalek, Fused Atrocity", 1);
    }

    @Test
    public void test_PlayConvoke_CantPayColorless() {
        // {2}{C}
        addCard(Zone.HAND, playerA, "Matter Reshaper", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1); // makes it castable
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 3);

        disableManaAutoPayment(playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Matter Reshaper");
        setChoice(playerA, "Convoke");
        addTarget(playerA, "Memnite^Memnite"); // {2}
        setChoice(playerA, "Convoke"); // only {C} is left
        addTarget(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        try {
            execute();
            Assert.fail("convoke must not pay {C}");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't use choice in play mana: Convoke")) {
                Assert.fail("must not offer convoke for {C}, but got:\n" + e.getMessage());
            }
        }
        assertTappedCount("Memnite", true, 2);
    }

    @Test
    public void test_PlayConvoke_NoManaSpent() {
        // {2}{R/W}
        // Target player can't play land cards this turn if {R} was spent to cast Moonhold and can't play creature
        // cards this turn if {W} was spent to cast it.
        addCard(Zone.HAND, playerA, "Moonhold", 1);
        addCard(Zone.HAND, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Ornithopter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Boros Guildmage", 1); // red and white
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Moonhold", playerA);
        // 702.51a: creatures are tapped "rather than pay that mana", so neither {R} nor {W} was spent
        addTarget(playerA, "Boros Guildmage^Memnite^Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Ornithopter", 1);
    }
}
