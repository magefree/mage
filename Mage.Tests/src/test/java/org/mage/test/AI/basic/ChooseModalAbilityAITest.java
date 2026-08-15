package org.mage.test.AI.basic;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 *
 * @author JayDi85
 */
public class ChooseModalAbilityAITest extends CardTestPlayerBaseWithAIHelps {

    private void setupSingleMode() {
        // Choose one --
        // * Target creature you control deals damage equal to its power to target creature an opponent controls.
        // * Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerA, "It's Clobberin' Time!", 1); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // possible mode 1 target: own creature - negative score
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 1); // 1/1

        // possible mode 1 target: opponent creature, survive, nothing to score
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        // possible mode 2 target: opponent artifact, destroy, gives ~500 score
        addCard(Zone.BATTLEFIELD, playerB, "Manalith", 1);
    }

    @Test
    public void test_SingleModal_Manual() {
        setupSingleMode();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "It's Clobberin' Time!");
        setModeChoice(playerA, "2"); // 2: Destroy target artifact or enchantment
        addTarget(playerA, "Manalith");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "It's Clobberin' Time!", 1);
        assertPermanentCount(playerB, "Manalith", 0);
    }

    @Test
    public void test_SingleModal_AI() {
        setupSingleMode();

        // ai must choose mode 2 due useless mode 1 (1 damage to 2/2 bear is worthless)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "It's Clobberin' Time!", 1); // exile due to Rebound
        assertPermanentCount(playerB, "Manalith", 0);
    }

    private void setupMultiMode() {
        // Fiery Confluence {2}{R}{R} - Choose three. You may choose the same mode more than once.
        // * Fiery Confluence deals 1 damage to each creature.
        // * Fiery Confluence deals 2 damage to each opponent.
        // * Destroy target artifact.
        addCard(Zone.HAND, playerA, "Fiery Confluence", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // no targets on mode 1 = it's useless
        // has targets on mode 2 = so AI must use it
        // no targets on mode 3 = it's useless
    }

    @Test
    public void test_MultiModal_Manual() {
        setupMultiMode();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Confluence");
        // 2: {this} deals 2 damage to each opponent
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Fiery Confluence", 1);
        assertLife(playerB, 20 - 2 * 3);
    }

    @Test
    public void test_MultiModal_AI() {
        setupMultiMode();

        // ai must choose x3 mode 2 due useless mode 1 and 3 (no targets)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Fiery Confluence", 1);
        assertLife(playerB, 20 - 2 * 3);
    }

    private void setupChooseTwoMode() {
        // Kolaghan's Command {1}{B}{R} - Choose two -
        // * Return target creature card from your graveyard to your hand;
        // * Target player discards a card;
        // * Destroy target artifact;
        // * Kolaghan's Command deals 2 damage to any target.
        addCard(Zone.HAND, playerA, "Kolaghan's Command", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // only 2 modes valid here: mode 2 (discard) and mode 4 (damage any target)
    }

    @Test
    public void test_ChooseTwoModal_Manual() {
        setupChooseTwoMode();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kolaghan's Command");
        setModeChoice(playerA, "2"); // Target player discards a card
        addTarget(playerA, playerB);
        setModeChoice(playerA, "4"); // deals 2 damage to any target
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Kolaghan's Command", 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_ChooseTwoModal_AI() {
        setupChooseTwoMode();

        // test case for AI's getAvailableModes - make sure selected modes processing is correct
        // if not then will be error like "Wrong call of addSelectedMode: mode already selected, you can't select it again"
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Kolaghan's Command", 1);
    }

    @Test
    public void test_LimitedUsageModes_AndNoModes_AI() {
        addCustomCardWithAbility(ACTIVATE_ABILITY, playerA, null);
        // make sure no errors on addModeOptions

        // Galadriel, Light of Valinor - Alliance 
        // Whenever another creature you control enters, choose
        // one that hasn't been chosen this turn -"
        // * Add {G}{G}{G}.
        // * Put a +1/+1 counter on each creature you control.
        // * Scry 2, then draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Galadriel, Light of Valinor", 1);

        // x3 triggers to exec all x3 modes
        // x1 trigger to exec 0 modes due limited usage of modes (all x3 modes already used)
        addCard(Zone.HAND, playerA, "Ornithopter", 4); // {0} artifact creature

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Galadriel, Light of Valinor", 1);
    }


    @Test
    public void test_PartialPawPrintSelection_AI() {
        // make sure paw prints allow to select only part of the budget and AI will use it for better score

        // there aren't any real cards (like Season of the Bold) so use custom ability
        // with 2 modes and {P} cost budget = 5, minModes = 0
        // * {P} -- You lose 3 life.                     (always legal, no target, always bad)
        // * {P}{P}{P}{P} -- Each opponent loses 4 life.  (always legal, no target, always good)
        Ability ability = new SimpleActivatedAbility(new LoseLifeSourceControllerEffect(3), new TapSourceCost());
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(5);
        ability.getModes().setMaxPawPrints(5);
        ability.getModes().setMayChooseSameModeMoreThanOnce(true);
        ability.getModes().getMode().withPawPrintValue(1);
 
        Mode mode2 = new Mode(new LoseLifeOpponentsEffect(4));
        mode2.withPawPrintValue(4);
        ability.addMode(mode2);
 
        addCustomCardWithAbility("War Totem", playerA, ability);
 
        // AI must use non-full budget and ignore damage to self (mode 1)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);
 
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
 
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 4);
    }
}
