package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */

public class RegenerateTest extends CardTestPlayerBase {

    @Test
    public void test_GainedByEnchantment() {
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");

        addCard(Zone.BATTLEFIELD, playerB, "Sagu Archer");
        addCard(Zone.HAND, playerB, "Molting Snakeskin");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Molting Snakeskin", "Sagu Archer");
        attack(2, playerB, "Sagu Archer");
        block(2, playerA, "Underworld Cerberus", "Sagu Archer");
        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerB, "{2}{B}: Regenerate {this}.");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // attacker has to regenerate because of damage

        assertPermanentCount(playerA, "Underworld Cerberus", 1);
        assertPermanentCount(playerB, "Sagu Archer", 1);
        assertPermanentCount(playerB, "Molting Snakeskin", 1);
    }

    @Test
    public void test_Source_Normal() {
        // {B}: Regenerate Drudge Skeletons. (The next time this creature would be destroyed this turn, it isn’t.
        // Instead tap it, remove all damage from it, and remove it from combat.)
        addCard(Zone.BATTLEFIELD, playerA, "Drudge Skeletons", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // add multiple regen shields
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: Regenerate");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: Regenerate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // damage 1 - regen
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Drudge Skeletons");
        setChoice(playerA, "Drudge Skeletons"); // two replacement effects
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drudge Skeletons", 1);

        // damage 2 - regen
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Drudge Skeletons");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drudge Skeletons", 1);

        // damage 3 - die
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Drudge Skeletons");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drudge Skeletons", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Source_Blinked() {
        // {B}: Regenerate Drudge Skeletons. (The next time this creature would be destroyed this turn, it isn’t.
        // Instead tap it, remove all damage from it, and remove it from combat.)
        addCard(Zone.BATTLEFIELD, playerA, "Drudge Skeletons", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift", 1); // {W} instant
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // add multiple regen shields
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: Regenerate");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: Regenerate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // blink and reset regens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Drudge Skeletons");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive blinked", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drudge Skeletons", 1);

        // damage - die (no regens)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Drudge Skeletons");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drudge Skeletons", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Target_Normal() {
        // {G}, {T}, Discard a card: Regenerate target creature.
        addCard(Zone.BATTLEFIELD, playerA, "Rushwood Herbalist", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Forest", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // add multiple regen shields
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}, Discard", "Grizzly Bears");
        setChoice(playerA, "Forest"); // discard cost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}, Discard", "Grizzly Bears");
        setChoice(playerA, "Forest"); // discard cost
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // damage 1 - regen
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        setChoice(playerA, "Rushwood Herbalist"); // two replacement effects
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // damage 2 - regen
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // damage 3 - die
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Target_Blinked() {
        // {G}, {T}, Discard a card: Regenerate target creature.
        addCard(Zone.BATTLEFIELD, playerA, "Rushwood Herbalist", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Forest", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift", 1); // {W} instant
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // add multiple regen shields
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}, Discard", "Grizzly Bears");
        setChoice(playerA, "Forest"); // discard cost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}, Discard", "Grizzly Bears");
        setChoice(playerA, "Forest"); // discard cost
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // blink and reset regens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive blinked", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // damage - die (no regens)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Attached_Normal() {
        // Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)
        // {G}: Regenerate enchanted creature. (The next time that creature would be destroyed this turn, it isn’t.
        // Instead tap it, remove all damage from it, and remove it from combat.)
        addCard(Zone.HAND, playerA, "Regeneration", 1); // {1}{G} aura
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 2); // cast + 2 activates
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // attach
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Regeneration", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // add multiple regen shields
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // damage 1 - regen
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        setChoice(playerA, "Regeneration"); // two replacement effects
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // damage 2 - regen
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // damage 3 - die
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Attached_Blinked() {
        // Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)
        // {G}: Regenerate enchanted creature. (The next time that creature would be destroyed this turn, it isn’t.
        // Instead tap it, remove all damage from it, and remove it from combat.)
        addCard(Zone.HAND, playerA, "Regeneration", 1); // {1}{G} aura
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 2); // cast + 2 activates
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift", 1); // {W} instant
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // attach
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Regeneration", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // add multiple regen shields
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // blink and reset regens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive blinked", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // damage - die (no regens)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Attached_Moved() {
        // regen shields must be accumulated in permanent, not aura

        // Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)
        // {G}: Regenerate enchanted creature. (The next time that creature would be destroyed this turn, it isn’t.
        // Instead tap it, remove all damage from it, and remove it from combat.)
        addCard(Zone.HAND, playerA, "Regeneration", 1); // {1}{G} aura
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 2); // cast + 2 activates
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2 permanent 1
        addCard(Zone.BATTLEFIELD, playerA, "Kitesail Corsair", 1); // 2/1 permanent 2
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        // Attach target Aura you control to target creature.
        addCard(Zone.HAND, playerA, "Aura Finesse", 1); // {U} instant
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // attach to first
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Regeneration", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // add multiple regen shields
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}: Regenerate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // re-attach to second
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aura Finesse");
        addTarget(playerA, "Regeneration"); // aura to move
        addTarget(playerA, "Kitesail Corsair"); // attach to permanent 2

        // damage to one - have regens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        setChoice(playerA, "Regeneration"); // two replacement effects
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("alive", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // damage to second -- die (no regens)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Kitesail Corsair");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Corsair", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Attached_Sacrifice() {
        // if you sacrifice aura to enable regen then it must works

        // Enchant creature
        // Enchanted creature gets +0/+2.
        // Sacrifice Carapace: Regenerate enchanted creature.
        addCard(Zone.HAND, playerA, "Carapace", 1); // {G} aura
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // attach
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Carapace", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPT("boosted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 2, 2 + 2);

        // try to kill (boost lost after regen activate, so need only 1 bolt)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        // activate regen before kill
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice", TestPlayer.NO_TARGET, "Cast Lightning Bolt", StackClause.WHILE_ON_STACK);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // permanent must be alive
        checkGraveyardCount("aura sacrificed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Carapace", 1);
        checkPermanentCount("permanent alive", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
