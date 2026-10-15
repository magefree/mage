package org.mage.test.cards.abilities.keywords;

import mage.abilities.SpellAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.AssistAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class AssistTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Playable_NoMana_NoAssist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Charging Binox", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_Mana_NoAssist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_NoMana_Assist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_Mana_Assist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_ManaPartly_AssistPartly() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_PlayAssist_Manual() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8 - 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        // disabled auto-payment and prepare mana pool to control payment
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 6);
        // cast and assist
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Charging Binox");
        setChoice(playerA, "Green", 6); // normal pay x6
        setChoice(playerA, "Assist"); // activate assist
        addTarget(playerA, playerB); // player to assist
        setChoice(playerB, "X=2"); // B pays {2} of A's cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Charging Binox", 1);
    }

    private static final String creatureManaOnly = "Assisted Superion";

    /**
     * {4} artifact with assist and Myr Superion's "Spend only mana produced by creatures to cast this spell."
     */
    private void addCreatureManaOnlyAssistCard() {
        SpellAbility spell = new SpellAbility(new ManaCostsImpl<>("{4}"), creatureManaOnly);
        spell.getManaCosts().setSourceFilter(StaticFilters.FILTER_PERMANENT_CREATURES);
        spell.getManaCostsToPay().setSourceFilter(StaticFilters.FILTER_PERMANENT_CREATURES);
        addCustomCardWithSpell(playerA, spell, new AssistAbility(), CardType.ARTIFACT);
    }

    @Test
    public void test_PlayAssist_SpendRestriction_HelperPaysWithCreatureMana() {
        addCreatureManaOnlyAssistCard();
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves", 2); // assist pay

        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creatureManaOnly);
        setChoice(playerA, "Green", 2);
        setChoice(playerA, "Assist");
        addTarget(playerA, playerB);
        setChoice(playerB, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, creatureManaOnly, 1);
        assertTappedCount("Llanowar Elves", true, 4);
    }

    @Test
    public void test_PlayAssist_SpendRestriction_HelperCantPayWithLandMana() {
        addCreatureManaOnlyAssistCard();
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, creatureManaOnly);
        setChoice(playerA, "Assist");
        addTarget(playerA, playerB);
        setChoice(playerB, "X=2"); // B's Forest mana can't be spent on it, so B pays nothing
        setChoice(playerA, TestPlayer.SKIP_FAILED_COMMAND); // B's payment fails, so Assist is rolled back
        setChoice(playerA, "Green", 4); // A still pays all of it

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, creatureManaOnly, 1);
        assertTappedCount("Forest", false, 2);
    }

    @Test
    public void test_PlayAssist_ManaRestriction_HelperPaysForArtifactSpell() {
        String artifact = "Assisted Construct";
        addCustomCardWithSpell(playerA, new SpellAbility(new ManaCostsImpl<>("{4}"), artifact),
                new AssistAbility(), CardType.ARTIFACT);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {T}: Add {C}{C}{C}. Spend this mana only to cast artifact spells.
        addCard(Zone.BATTLEFIELD, playerB, "Mishra's Workshop", 1);

        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, artifact);
        setChoice(playerA, "Assist");
        addTarget(playerA, playerB);
        setChoice(playerB, "X=3"); // B's mana is spent to cast A's artifact spell
        setChoice(playerA, "Green");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, artifact, 1);
        assertTapped("Mishra's Workshop", true);
    }

    @Test
    public void test_PlayAssist_ManaRestriction_HelperCantPayForNonartifactSpell() {
        // {7}{G}
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        // {T}: Add {C}{C}. This mana can't be spent to cast nonartifact spells.
        addCard(Zone.BATTLEFIELD, playerB, "The Mightstone and Weakstone", 1);

        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 8);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Charging Binox");
        setChoice(playerA, "Assist");
        addTarget(playerA, playerB);
        setChoice(playerB, "X=2"); // B's only mana can't be spent on a nonartifact spell
        setChoice(playerA, TestPlayer.SKIP_FAILED_COMMAND); // B's payment fails, so Assist is rolled back
        setChoice(playerA, "Green", 8); // A still pays all of it

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Charging Binox", 1);
        assertTapped("The Mightstone and Weakstone", false);
    }

    @Test
    public void test_PlayAssist_HelperPaysGenericBeforeMonoHybrid() {
        String monoHybridFirst = "Assisted Procession";
        addCustomCardWithSpell(playerA, new SpellAbility(new ManaCostsImpl<>("{2/W}{1}"), monoHybridFirst),
                new AssistAbility(), CardType.ARTIFACT);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1); // assist pay

        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, monoHybridFirst);
        setChoice(playerA, "Assist");
        addTarget(playerA, playerB);
        setChoice(playerB, "X=1"); // pays the {1}, so the {2/W} can still be paid with one {W}
        setChoice(playerA, "White");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, monoHybridFirst, 1);
    }

    @Test
    public void test_PlayAssist_HelperManaIsSpent() {
        String greenMatters = "Assisted Growth";
        SpellAbility spell = new SpellAbility(new ManaCostsImpl<>("{2}"), greenMatters);
        spell.addEffect(new ConditionalOneShotEffect(new GainLifeEffect(3), ManaWasSpentCondition.GREEN));
        addCustomCardWithSpell(playerA, spell, new AssistAbility(), CardType.SORCERY);
        // Whenever you cast an instant or sorcery spell, Aberrant Manawurm gets +X/+0 until end of turn, where X is
        // the amount of mana spent to cast that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Aberrant Manawurm", 1); // 2/5
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        disableManaAutoPayment(playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, greenMatters);
        setChoice(playerA, "Assist");
        addTarget(playerA, playerB);
        setChoice(playerB, "X=2"); // B's {G}{G} is spent to cast A's spell

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3);
        assertPowerToughness(playerA, "Aberrant Manawurm", 2 + 2, 5);
        assertTappedCount("Forest", true, 2);
    }

    @Test
    public void test_PlayAssist_AI_MustIgnoreAssist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8 - 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        // AI must ignore assist
        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Charging Binox", 1);
        assertTappedCount("Forest", false, 8); // no mana used
    }
}