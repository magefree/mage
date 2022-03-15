package org.mage.test.cards.copy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class EssenceOfTheWildCopyTest extends CardTestPlayerBase {

    // Essence of the Wild {3}{G}{G}{G}
    // Creatures you control enter the battlefield as a copy of Essence of the Wild.
    private Permanent findCopyPermanent(Game game, int num) {
        int currentCopyNumber = 1;
        for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
            if (perm.isCopy()) {
                if (currentCopyNumber == num) {
                    return perm;
                }
                currentCopyNumber++;
            }
        }
        Assert.fail("copy " + num + " must exist");
        return null;
    }

    private Permanent findOriginPermanent(Game game, String permName) {
        for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
            if (!perm.isCopy() && perm.getName().equals(permName)) {
                return perm;
            }
        }
        Assert.fail("can't find origin");
        return null;
    }

    @Test
    public void test_CopyCreature() {
        // essence copy creature
        addCard(Zone.BATTLEFIELD, playerA, "Essence of the Wild", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Essence of the Wild", 2);

        Permanent copy = findCopyPermanent(currentGame, 1);
        Assert.assertEquals("must have 6 p/t", 6, copy.getPower().getValue());
        Assert.assertEquals("must have 6 p/t", 6, copy.getToughness().getValue());
    }

    @Test
    @Ignore // TODO: enable and fix random failes with replace effects
    public void test_CopyCreatureByCopied() {
        // essence copy to creature 1 -> creature 1 copy to creature
        addCard(Zone.BATTLEFIELD, playerA, "Essence of the Wild", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Essence of the Wild", 3);

        Permanent copy1 = findCopyPermanent(currentGame, 1);
        Permanent copy2 = findCopyPermanent(currentGame, 2);
        Assert.assertFalse("copy must be diffent", copy1.equals(copy2));
        Assert.assertEquals("copy 1 must have 6 p/t", 6, copy1.getPower().getValue());
        Assert.assertEquals("copy 1 must have 6 p/t", 6, copy1.getToughness().getValue());
        Assert.assertEquals("copy 2 must have 6 p/t", 6, copy2.getPower().getValue());
        Assert.assertEquals("copy 2 must have 6 p/t", 6, copy2.getToughness().getValue());
    }

    @Test
    @Ignore // TODO: enable and fix random failes with replace effects
    public void test_CopyManyTokens() {
        // https://github.com/magefree/mage/issues/6222
        addCard(Zone.BATTLEFIELD, playerA, "Essence of the Wild", 1);
        //
        // Create X 1/1 white Soldier creature tokens with lifelink.
        addCard(Zone.HAND, playerA, "March of the Multitudes", 1); // {X}{G}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5 + 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // create multiple tokens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Multitudes");
        setChoice(playerA, "X=5");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Soldier Token", 0);
        assertPermanentCount(playerA, "Essence of the Wild", 1 + 5);

        List<Permanent> list = new ArrayList<>();
        list.add(findCopyPermanent(currentGame, 1));
        list.add(findCopyPermanent(currentGame, 2));
        list.add(findCopyPermanent(currentGame, 3));
        list.add(findCopyPermanent(currentGame, 4));
        list.add(findCopyPermanent(currentGame, 5));

        Set<UUID> uniq = list.stream().map(Permanent::getId).collect(Collectors.toSet());
        Assert.assertEquals("All tokens must be copied", list.size(), uniq.size());

        list.stream().forEach(permanent -> {
            Assert.assertEquals("copy must have 6 p/t", 6, permanent.getPower().getValue());
            Assert.assertEquals("copy must have 6 p/t", 6, permanent.getToughness().getValue());
        });
    }

    @Test
    public void test_CopyCreatureWithContinuousEffect() {
        // essence with -1/-1 must copy creature with normal p/t
        addCard(Zone.BATTLEFIELD, playerA, "Essence of the Wild", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Liliana, Death Wielder", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // apply -1/-1 effect (+2: Put a -1/-1 counter on up to one target creature.)
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "+2:", "Essence of the Wild");

        // copy
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Essence of the Wild", 2);

        Permanent origin = findOriginPermanent(currentGame, "Essence of the Wild");
        Permanent copy = findCopyPermanent(currentGame, 1);
        Assert.assertEquals("origin must have 5 p/t", 6 - 1, origin.getPower().getValue());
        Assert.assertEquals("origin must have 5 p/t", 6 - 1, origin.getToughness().getValue());
        Assert.assertEquals("copy must have 6 p/t", 6, copy.getPower().getValue());
        Assert.assertEquals("copy must have 6 p/t", 6, copy.getToughness().getValue());
    }

    @Test
    public void testCopyFromTheCopy() {
        // Creatures you control enter the battlefield as a copy of Essence of the Wild.
        addCard(Zone.BATTLEFIELD, playerA, "Essence of the Wild", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);

        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1); // Instant {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Terror", "Essence of the Wild[no copy]");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Terror", 1);
        assertGraveyardCount(playerA, "Essence of the Wild", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Essence of the Wild", 2);

        assertPowerToughness(playerA, "Essence of the Wild", 6, 6, Filter.ComparisonScope.All);
    }

}
