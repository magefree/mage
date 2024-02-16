package org.mage.test.cards.single.stx;

import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.watchers.common.SpellsCastWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ShowOfConfidenceTest extends CardTestPlayerBase {

    @Test
    public void test_SpellsCastWatcher() {
        // When you cast this spell, copy it for each other instant or sorcery spell you've cast this turn. You may choose new targets for the copies.
        // Put a +1/+1 counter on target creature. It gains vigilance until end of turn.
        addCard(Zone.HAND, playerA, "Show of Confidence"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Each instant and sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Past in Flames", 1); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // prepare NPE error for watcher
        runCode("prepare watcher's NPE", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // possible bug: NPE after wrong computeIfAbsent usage (lists desync)
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            watcher.getSpellsCastThisTurn(player.getId());
        });

        // prepare flashback
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Past in Flames");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // prepare spells count (1x from hand, 1x from grave)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {R}", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast and copy 3x
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Show of Confidence", "Balduvian Bears");
        setChoice(playerA, false); // no change target (copy 1)
        setChoice(playerA, false); // no change target (copy 2)
        setChoice(playerA, false); // no change target (copy 3)

        // test watcher's copy
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("test watcher's copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            SpellsCastWatcher copiedWatcher = watcher.copy();
            Assert.assertEquals("original watcher must see 4 spells", 4, watcher.getSpellsCastThisTurn(player.getId()).size());
            Assert.assertEquals("copied watcher must see 4 spells", 4, copiedWatcher.getSpellsCastThisTurn(player.getId()).size());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Balduvian Bears", CounterType.P1P1, 4);
        assertAbility(playerA, "Balduvian Bears", VigilanceAbility.getInstance(), true);
    }
}
