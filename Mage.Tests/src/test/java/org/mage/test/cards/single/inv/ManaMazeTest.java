package org.mage.test.cards.single.inv;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JayDi85
 */
public class ManaMazeTest extends CardTestPlayerBase {

    @Test(expected = StackOverflowError.class)
    public void test_DeepCopy_WithSelfReference() {
        // stack overflow bug: https://github.com/magefree/mage/issues/11572
        // proof of self ref reason for stack overflow

        // list
        List<String> sourceList = new ArrayList<>(Arrays.asList("val1", "val2", "val3"));
        List<String> copyList = CardUtil.deepCopyObject(sourceList);
        Assert.assertNotSame(sourceList, copyList);
        Assert.assertEquals(sourceList.size(), copyList.size());
        Assert.assertEquals(sourceList.toString(), copyList.toString());

        // list with self ref
        List<List<Object>> sourceObjectList = new ArrayList<>();
        sourceObjectList.add(new ArrayList<>(Arrays.asList("val1", "val2", "val3")));
        sourceObjectList.add(new ArrayList<>(Arrays.asList(sourceObjectList)));
        CardUtil.deepCopyObject(sourceObjectList);
    }

    @Test
    public void test_DeepCopy_WatcherWithSelfReference() {
        // stack overflow bug: https://github.com/magefree/mage/issues/11572
        // card's watcher can have spell's ref to itself, so deep copy must be able to process it

        // Players can't cast spells that share a color with the spell most recently cast this turn.
        addCard(Zone.HAND, playerA, "Mana Maze", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.HAND, playerB, "Aven Reedstalker", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Aven Reedstalker", true);

        // cast maze and restrict all other blue spells
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mana Maze");
        checkPlayableAbility("after", 1, PhaseStep.END_TURN, playerB, "Cast Aven Reedstalker", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Mana Maze", 1);
        assertPermanentCount(playerB, "Aven Reedstalker", 0);
    }
}
