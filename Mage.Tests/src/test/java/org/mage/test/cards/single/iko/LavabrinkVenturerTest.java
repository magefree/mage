package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Lavabrink Venturer {2}{W} 3/3
 * As Lavabrink Venturer enters the battlefield, choose odd or even.
 * Lavabrink Venturer has protection from each mana value of the chosen quality.
 *
 * @author notgreat, Claude Opus 5
 */
public class LavabrinkVenturerTest extends CardTestPlayerBase {

    /**
     * Both parities in one game, with a flicker in between so the second choice has to displace the
     * first rather than sit alongside it.
     * <p>
     * Having chosen "even" the Venturer shrugs off a mana value 4 attacker and cannot be blocked by
     * one. After Cloudshift returns it and "odd" is chosen, a mana value 3 attacker is the one being
     * shrugged off -- and the mana value 2 burn spell that the first choice would have stopped now
     * targets it and kills it.
     */
    @Test
    public void test_choiceIsRemadeOnReentry() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Lavabrink Venturer");
        addCard(Zone.HAND, playerA, "Cloudshift");             // {W}, exile and return it
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 2);   // {3}{R}, mana value 4, 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Trained Armodon"); // {1}{G}{G}, mana value 3, 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Searing Spear");          // {1}{R}, mana value 2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lavabrink Venturer");
        setChoice(playerA, "even");

        // even chosen: the Giant's damage is prevented, the Venturer's is not
        attack(2, playerB, "Hill Giant");
        block(2, playerA, "Lavabrink Venturer", "Hill Giant");
        checkDamage("unharmed by the Giant", 2, PhaseStep.END_COMBAT, playerA, "Lavabrink Venturer", 0);

        // and the surviving Giant cannot block it either
        attack(3, playerA, "Lavabrink Venturer");
        runCode("even blocker is refused", 3, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent blocker = game.getBattlefield().getAllActivePermanents()
                    .stream()
                    .filter(permanent -> permanent.getName().equals("Hill Giant"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("the second Hill Giant should still be alive"));
            Assert.assertFalse("a mana value 4 creature must not be able to block the Venturer",
                    game.getCombat().getGroups().get(0).canBlock(blocker, game));
        });

        // flicker it and pick the other quality this time
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Lavabrink Venturer");
        setChoice(playerA, "odd");

        // odd chosen: now the mana value 3 attacker is the one that cannot get through
        attack(4, playerB, "Trained Armodon");
        block(4, playerA, "Lavabrink Venturer", "Trained Armodon");
        checkDamage("unharmed by the Armodon", 4, PhaseStep.END_COMBAT, playerA, "Lavabrink Venturer", 0);

        // the old choice is gone, not merely outvoted: an even mana value spell reaches it again
        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Searing Spear", "Lavabrink Venturer");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertGraveyardCount(playerB, "Trained Armodon", 1);
        assertGraveyardCount(playerA, "Lavabrink Venturer", 1);
        assertLife(playerB, 20 - 3); // the unblockable attack got through
    }
}
