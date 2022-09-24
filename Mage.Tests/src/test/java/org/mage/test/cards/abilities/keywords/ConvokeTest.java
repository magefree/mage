package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
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
        addTarget(playerA, "Goblin Racketeer"); // pay 3 as convoke
        setChoice(playerA, "Convoke");
        addTarget(playerA, "Goblin Racketeer"); // pay 4 as convoke

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
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

        //setStrictChooseMode(true); AI must choose targets
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
    @Ignore
    // I don't know how to test it by framework - manual test works fine for HumanPlayer
    // (he get warning message and can't activate mana abilities after convoke)
    public void test_Other_CantUseConvokeBeforeManaAbilities() {
        // https://github.com/magefree/mage/issues/768

        // {6}
        // Convoke
        addCard(Zone.HAND, playerA, "Will-Forged Golem", 1);
        //
        // {2}{G}
        // Create two 1/1 colorless Eldrazi Scion creature tokens. They have “Sacrifice this creature: Add {C}.”
        addCard(Zone.HAND, playerA, "Call the Scions", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3 * 2);

        // prepare scions
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Call the Scions");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Call the Scions");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("scions", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eldrazi Scion Token", 4);

        // test case 1 - playable abilities must not show it as playable (not work, cause we don't known real payment order before payment)
        //checkPlayableAbility("can't use convoke", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Will-Forged Golem", false);

        // test case 2 - it's in playable list, but mana abilities can't be activated after convoke pay
        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Will-Forged Golem");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
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
}